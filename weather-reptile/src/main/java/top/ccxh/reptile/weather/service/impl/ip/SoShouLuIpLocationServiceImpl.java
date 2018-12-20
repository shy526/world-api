package top.ccxh.reptile.weather.service.impl.ip;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import top.ccxh.reptile.weather.service.impl.ip.IpBaseService;

/**
 * 收录网ip查询
 * @author admin
 */
@Service("soShouLuIpLocationService")
public class SoShouLuIpLocationServiceImpl extends IpBaseService {
    private final static String IP_URL="http://ip.soshoulu.com/ajax/shoulu.ashx?_type=ipsearch&ip=%s";
    private final static String REMOVE_FLAG="$";
    @Override
    public String parseDocument(Document document) {
        String text = document.body().text();
        if (text.contains(REMOVE_FLAG)){
           text= text.substring(0,text.indexOf(REMOVE_FLAG));
        }
        return text;
    }

    @Override
    public String connectUrl(String ip) {
        return String.format(IP_URL, ip);
    }
}
