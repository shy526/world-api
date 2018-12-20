package top.ccxh.world.web.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


/**
 天气接口
 * @author admin
 */
@RestController
@RequestMapping("weather")
@Validated
public class WeatherController {
    //private WeatherService weatherService;
    @GetMapping("/province")
    public void getProvince(){
      //  List<WeatherCode> weatherCodes = weatherService.selectWeatherCodeByZoneType(ZoneTypeEnum.PROVINCE);
    }
    @GetMapping("/next")
    public void nextLevelLocation(@NotNull() Integer father){
        System.out.println("father = " + father);
       // String s = weatherService.selectWeatherCoderByParent(father);
    }
}
