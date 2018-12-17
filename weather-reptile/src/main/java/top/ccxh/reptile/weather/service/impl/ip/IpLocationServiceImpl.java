package top.ccxh.reptile.weather.service.impl.ip;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * www.ip.cn实现类
 * @author admin
 */
@Service("ipLocationService")
public class IpLocationServiceImpl extends IpBaseService {
    private final static String IP_URL="https://www.ip.cn/index.php?ip=%s";
    private final static String IP_URL_SHOULU="http://ip.soshoulu.com/ajax/shoulu.ashx?_type=ipsearch&ip=%s&px=1";


    @Override
    public String parseDocument(Document document) {
        Elements location= document.select(".well p:eq(1)");
        return location.select("code").text();
    }

    @Override
    public String connectUrl(String ip) {
        return String.format(IP_URL, ip);
    }
}
