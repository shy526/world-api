package top.ccxh.reptile.weather.service;

import org.jsoup.nodes.Document;
import top.ccxh.reptile.weather.pojo.IpLocation;

/**
 * ip 查询
 * @author admin
 */
public interface IpLocationService {
    /**
     * 查询ip地址
     * @param ip
     * @return
     */
   IpLocation selectIpLocation(String ip);

    /**
     * 解析document 成为地址
     * @param document
     * @return
     */
     String parseDocument(Document document);

    /**
     * 返回连接url
     * @return
     */
    String connectUrl(String ip);
}
