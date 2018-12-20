package top.ccxh.world.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ccxh.reptile.weather.constants.ZoneTypeEnum;
import top.ccxh.reptile.weather.pojo.WeatherCode;
import top.ccxh.reptile.weather.pojo.WeatherInfo;
import top.ccxh.reptile.weather.service.WeatherService;
import top.ccxh.world.web.common.WebResult;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 天气接口
 *
 * @author admin
 */
@RestController
@RequestMapping("weather")
@Validated
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/province")
    public WebResult<List<WeatherCode>> getProvince() {
        return WebResult.succeed(weatherService.selectWeatherCodeByZoneType(ZoneTypeEnum.PROVINCE));
    }

    @GetMapping("/next")
    public WebResult<String> nextLevelLocation(@NotNull Integer parent) {
        return WebResult.succeed(weatherService.selectWeatherCoderByParent(parent));
    }

    public WebResult<WeatherInfo> getWeatherInfo(@NotEmpty String code){
        return WebResult.succeed(weatherService.selectWeatherInfoCache(code));
    }
}
