package top.ccxh.world.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ccxh.reptile.weather.constants.ZoneTypeEnum;
import top.ccxh.reptile.weather.pojo.WeatherCode;
import top.ccxh.reptile.weather.service.WeatherService;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 天气接口
 * @author admin
 */
@RestController
@RequestMapping("weather")
@Validated
public class WeatherController {
    @Autowired
    private WeatherService weatherService;
    @GetMapping("/province")
    public void getProvince(){
        List<WeatherCode> weatherCodes = weatherService.selectWeatherCodeByZoneType(ZoneTypeEnum.PROVINCE);
    }
    @GetMapping("/next")
    public void nextLevelLocation(@NotNull() Integer parent){
        String s = weatherService.selectWeatherCoderByParent(parent);
    }
}
