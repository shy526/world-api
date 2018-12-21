package top.ccxh.reptile.weather.timed;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.ccxh.reptile.weather.constants.ZoneTypeEnum;
import top.ccxh.reptile.weather.pojo.WeatherCode;
import top.ccxh.reptile.weather.pojo.WeatherInfo;
import top.ccxh.reptile.weather.service.WeatherService;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时获取天气信息
 *
 * @author admin
 */
@Component
@Slf4j
public class CatchWeatherInfo {
    @Autowired
    private WeatherService weatherService;

    @Scheduled(cron = "0 */50 * * * ?")
    public void run() {
        long begin = System.currentTimeMillis();
        List<WeatherCode> weatherCodes = weatherService.selectWeatherCodeByZoneType(ZoneTypeEnum.WEATHER_CODE);
        List<WeatherInfo> weatherInfoList = new ArrayList<>();
        Long error=0L;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("succeed",0);
        jsonObject.put("failure",0);
        for (WeatherCode item : weatherCodes) {
            WeatherInfo weatherInfo = null;
            try {
                weatherInfo = weatherService.getWeatherInfo(item.getName());
            } catch (Exception e) {
                error++;
            }
            if (weatherInfo != null) {
                weatherInfo.setWeatherCoderId(item.getId());
                weatherInfo.quickTime();
                weatherInfoList.add(weatherInfo);
            }
            this.batchAddWeatherInfo(weatherInfoList, 200,jsonObject);

        }
        this.batchAddWeatherInfo(weatherInfoList, 0,jsonObject);
        log.error("total:{},error:{},succeed:{},failure:{},consuming:{}ms",weatherCodes.size(),error,
                jsonObject.getLongValue("succeed"),jsonObject.getLongValue("failure"),System.currentTimeMillis()-begin);
    }

    /**
     * 批量插入
     *
     * @param weatherInfoList
     * @param valve
     */
    private void batchAddWeatherInfo(List<WeatherInfo> weatherInfoList, int valve, JSONObject jsonObject) {
        if (weatherInfoList.size() > valve) {
            Integer result = weatherService.addWeatherInfoList(weatherInfoList);
            jsonObject.put("succeed",jsonObject.getLongValue("succeed")+result);
            jsonObject.put("failure",jsonObject.getLongValue("failure")+(weatherInfoList.size()-result));
            weatherInfoList.clear();
        }
    }
}
