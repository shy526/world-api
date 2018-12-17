package top.ccxh.reptile.weather.service;

import top.ccxh.reptile.weather.pojo.IpLocation;

/**
 * 聚合接口
 * @author admin
 */
public interface IpAggregationService {
    /**
     * 查询ip 归属地
     * @param ip ip地址
     * @return IpLocation
     */
    IpLocation selectIpLocation(String ip);

    /**
     * 随机访问一个员
     * @param ip ip地址
     * @return IpLocation
     */
    IpLocation selectIpLocationRandomSource(String ip);
}
