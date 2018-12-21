package top.ccxh.reptile.weather.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
;
import org.springframework.data.redis.core.RedisTemplate;
import top.ccxh.BaseTest;
import top.ccxh.reptile.weather.pojo.WeatherInfo;


import javax.annotation.Resource;


public class WeatherServiceTest extends BaseTest {
    @Autowired
    WeatherService weatherService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Test
    public void selectWeatherInfoByCodeIdCache() {
        WeatherInfo weatherInfo = weatherService.selectWeatherInfoByCodeIdCache(3186);

        String test = redisTemplate.opsForValue().get("test");
        System.out.println("test = " + test);
    }
}