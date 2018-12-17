package top.ccxh.reptile.weather.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import top.ccxh.reptile.weather.pojo.IpLocation;
import top.ccxh.reptile.weather.service.IpAggregationService;
import top.ccxh.reptile.weather.service.IpLocationService;

/**
 * 聚合接口实现类
 *
 * @author ccxh
 */
public class IpAggregationServiceImpl implements IpAggregationService {
    @Autowired
    private IpLocationService soShouLuIpLocationService;
    @Autowired
    private IpLocationService ipLocationService;
    @Autowired
    private IpLocationService ipIpLocationService;

    @Override
    public IpLocation selectIpLocation(String ip) {
        IpLocation ipLocation = soShouLuIpLocationService.selectIpLocation(ip);
        if (assertLocation(ipLocation)) {
            ipLocation = ipLocationService.selectIpLocation(ip);
        }
        if (assertLocation(ipLocation)) {
            ipLocation = ipIpLocationService.selectIpLocation(ip);
        }
        return ipLocation;
    }

    @Override
    public IpLocation selectIpLocationRandomSource(String ip) {
       int random= (int)Math.random()*(10-1)+1;
        IpLocation ipLocation;
        if (random<4){
             ipLocation = soShouLuIpLocationService.selectIpLocation(ip);
        }else if (random<7){
            ipLocation = ipLocationService.selectIpLocation(ip);
        }else {
            ipLocation = ipIpLocationService.selectIpLocation(ip);
        }
        return ipLocation;
    }

    private boolean assertLocation(IpLocation ipLocation) {
        return StringUtils.isEmpty(ipLocation.getLocation());
    }
}
