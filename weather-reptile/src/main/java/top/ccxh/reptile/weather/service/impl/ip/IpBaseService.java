package top.ccxh.reptile.weather.service.impl.ip;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;
import top.ccxh.reptile.weather.pojo.IpLocation;
import top.ccxh.reptile.weather.service.IpLocationService;

import java.io.IOException;

/**
 * 查询ip基础类
 *
 * @author admin
 */
public abstract class IpBaseService implements IpLocationService {
    private Document getDocument(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public IpLocation selectIpLocation(String ip) {
        String url = this.connectUrl(ip);
        IpLocation ipLocation = new IpLocation();
        ipLocation.setIp(ip);
        if (url != null && !"".equals(url)) {
            ipLocation.setSource(url);
            Document document = this.getDocument(url);
            String location = this.parseDocument(document);
            if (!StringUtils.isEmpty(location)){
                ipLocation.setLocation(   location.trim().replaceAll("\\s",""));
            }
            ipLocation.quickTime();
        }
        return ipLocation;
    }
}
