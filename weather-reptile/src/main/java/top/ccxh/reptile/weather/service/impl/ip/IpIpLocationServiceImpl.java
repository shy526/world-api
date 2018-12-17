package top.ccxh.reptile.weather.service.impl.ip;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * 实现
 * @author admin
 */
@Service("ipIpLocationService")
public class IpIpLocationServiceImpl extends IpBaseService {
    private final static String IP_URL="https://www.ipip.net/ip/%s.html";
    private final static String SELECT_KEY=".ipSearch .inner";
    private final static String LOCATION_KEY="tr:eq(2) span:eq(0)";
    private final static String OPERATOR_KEY="tr:eq(3) span:eq(0)";
    @Override
    public String parseDocument(Document document) {
        Elements table = document.body().select(SELECT_KEY);
        return ""+table.select(LOCATION_KEY).text()+table.select(OPERATOR_KEY).text();
    }

    @Override
    public String connectUrl(String ip) {
        return String.format(IP_URL,ip);
    }
}
