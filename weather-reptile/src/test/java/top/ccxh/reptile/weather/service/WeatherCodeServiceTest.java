package top.ccxh.reptile.weather.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import top.ccxh.BaseTest;
import top.ccxh.reptile.weather.mapper.WeatherCodeMapper;
import top.ccxh.reptile.weather.pojo.WeatherCode;
import top.ccxh.reptile.weather.pojo.WeatherHistory;
import top.ccxh.reptile.weather.pojo.WeatherInfo;

import java.util.List;

public class WeatherCodeServiceTest extends BaseTest {
    @Autowired
    private WeatherService weatherCodeService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WeatherCodeMapper weatherCodeMapper;
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Test
    public void initeWetherCode() {
        List<WeatherCode> provinceList = this.addWetherCodeList("", 0, null);
        for (WeatherCode province : provinceList) {
            //获取城市
            List<WeatherCode> cityList = this.addWetherCodeList(province.getCode(),1,province.getId());
            for (WeatherCode city : cityList) {
                //获取县
                List<WeatherCode> countyList = this.addWetherCodeList(city.getCode(), 2, city.getId());
                for (WeatherCode  county:countyList){
                    //地区
                    this.addWetherCodeList(county.getCode(),3,county.getId());
                }
            }
        }
    }

    private List<WeatherCode> addWetherCodeList(String code,Integer zoneType,Integer id) {
        List<JSONObject> parse = weatherCodeService.parse(code);
        List<WeatherCode> list = JSON.parseArray(JSON.toJSONString(parse), WeatherCode.class);
        for (WeatherCode item : list) {
            item.setZoneType(zoneType);
            item.quickTime();
            item.setParent(id);
        }
     //   weatherCodeMapper.insertList(list);
        System.out.println(JSON.toJSONString(list));
        return list;
    }

    @Test
    public void getWeatherInfo() {
        WeatherInfo weatherInfo = weatherCodeService.getWeatherInfo("101010200");
    }

    @Test
    public void getHistoryWeatherInfo() {
        WeatherHistory historyWeatherInfo = weatherCodeService.getHistoryWeatherInfo("1214", "101210101");
        System.out.println("historyWeatherInfo = " + historyWeatherInfo);
    }

    @Test
    public void getCustomWeatherInfo() {
        JSONObject customWeatherInfo = weatherCodeService.getCustomWeatherInfo("101210101");
        System.out.println("customWeatherInfo = " + customWeatherInfo);

    }
}