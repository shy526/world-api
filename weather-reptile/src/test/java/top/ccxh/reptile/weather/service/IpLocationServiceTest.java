package top.ccxh.reptile.weather.service;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.ccxh.BaseTest;
import top.ccxh.reptile.weather.pojo.IpLocation;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class IpLocationServiceTest extends BaseTest {
    @Autowired
    IpLocationService soShouLuIpLocationService;
    @Autowired
    IpLocationService ipLocationService;
    @Autowired
    IpLocationService ipIpLocationService;
    @Test
    public void selectIpLocation() {
        IpLocation ipLocation = ipIpLocationService.selectIpLocation("183.157.87.75");
        System.out.println("ipLocation = " + ipLocation);
    }

    @Test
    public void javascript() {
        /*动漫之家爬取测试*/
        long begin=System.currentTimeMillis();
        try {
            Document document = Jsoup.connect("https://manhua.dmzj.com/heishihuidcnlnr/72104.shtml#@page=1").get();
            String text = document.head().select("script").eq(0).html();
            text="function ae"+begin+"() {"+text+ " return arr_pages}";
            ScriptEngineManager m = new ScriptEngineManager();
            ScriptEngine engine = m.getEngineByName("JavaScript");
            engine.eval(text);
            if(engine instanceof Invocable) {
                Invocable invoke = (Invocable)engine;
                ScriptObjectMirror getImages =(ScriptObjectMirror) invoke.invokeFunction("ae"+begin);
                Set<Map.Entry<String, Object>> entries = getImages.entrySet();
                for (Map.Entry<String,Object> item:entries){
                    System.out.println(item.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-begin);
    }
}