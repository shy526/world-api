package top.ccxh.reptile.weather.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import top.ccxh.reptile.weather.constants.ZoneTypeEnum;
import top.ccxh.reptile.weather.mapper.WeatherCodeMapper;
import top.ccxh.reptile.weather.mapper.WeatherInfoMapper;
import top.ccxh.reptile.weather.pojo.WeatherCode;
import top.ccxh.reptile.weather.pojo.WeatherHistory;
import top.ccxh.reptile.weather.pojo.WeatherInfo;
import top.ccxh.reptile.weather.service.WeatherService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现类
 *
 * @author admin
 */
@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    private final static String CITY_URL = "http://www.weather.com.cn/data/list3/city%s.xml";
    private final static String WEATHER_URL = "http://d1.weather.com.cn/sk_2d/%s.html?_=%s";
    private final static String WEATHER_XML_URL = "http://wthrcdn.etouch.cn/WeatherApi?citykey=%s";
    private final static String WEATHER_HISTORY_URL = "http://d1.weather.com.cn/history/%s/%s.html?_=%s";
    private final static String WEATHER_CUSTOM_URL = "http://d1.weather.com.cn/dingzhi/%s.html?_=%s";
    private final static String SPACE_MARK = ",";
    private final static String SEPARATE = "\\|";
    private final static String REDNIK = ";";
    private final static SimpleDateFormat YYYYMMDDHH = new SimpleDateFormat("yyyyMMddHHmmss");
    private final static String CODE = "code";
    private final static String NAME = "name";
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WeatherInfoMapper weatherInfoMapper;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WeatherCodeMapper weatherCodeMapper;

    @Override
    public List<JSONObject> parse(String code) {
        List<JSONObject> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(String.format(CITY_URL, code)).get();
            if (doc != null) {
                String docStr = doc.toString();
                String[] nodes = docStr.split(SPACE_MARK);
                for (String item : nodes) {
                    String[] split = item.split(SEPARATE);
                    JSONObject province = new JSONObject();
                    province.put(CODE, split[0]);
                    province.put(NAME, split[1]);
                    list.add(province);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public WeatherInfo getWeatherInfo(String code) {
        JSONObject info = this.getJsonInfo(String.format(WEATHER_URL, code, String.valueOf(System.currentTimeMillis())));
        WeatherInfo weatherInfo = JSON.toJavaObject(info, WeatherInfo.class);
        weatherInfo.setWd(info.getString("WD"));
        weatherInfo.setWeather(info.getString("weather"));
        weatherInfo.setAqiPm25(info.getInteger("aqi_pm25"));
        return supplementWeatherData(code, weatherInfo);
    }


    /**
     * 补充天气数据
     *
     * @param code        天气代码
     * @param weatherInfo 实体
     * @return WeatherInfo
     */

    private WeatherInfo supplementWeatherData(String code, WeatherInfo weatherInfo) {
        JSONObject customWeatherInfo = this.getCustomWeatherInfo(code);
        JSONObject dingzhi = customWeatherInfo.getJSONObject("weatherinfo");
        if (dingzhi != null) {
            weatherInfo.setMaxTemp(dingzhi.getInteger("temp"));
            weatherInfo.setMinTemp(dingzhi.getInteger("tempn"));
            String wd = dingzhi.getString("wd");
            if (!StringUtils.isEmpty(wd)) {
                weatherInfo.setWd(wd);
            }
            weatherInfo.setWs(dingzhi.getString("ws"));
            String weather = dingzhi.getString("weather");
            if (!StringUtils.isEmpty(weather)) {
                weatherInfo.setWeather(weather);
            }
            String fctime = dingzhi.getString("fctime");
            if (fctime.length() < 14) {
                weatherInfo.setWatchTime(Long.valueOf(fctime));
            } else {
                try {
                    if (!StringUtils.isEmpty(fctime)) {
                        weatherInfo.setWatchTime(YYYYMMDDHH.parse(fctime.toString()).getTime());
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        return weatherInfo;
    }

    private JSONObject getJsonInfo(String url) {
        JSONObject parse = null;
        try {
            Document document = Jsoup.connect(url)
                    .header("Referer", " http://www.weather.com.cn/weathern/101210101.shtml").get();
            String text = document.body().text();
            if (text.contains("404")) {
                throw new RuntimeException(url + " \n无法访问");
            }
            if (!StringUtils.isEmpty(text)) {
                String jsonStr = text.substring(text.indexOf("{"));
                if (jsonStr.indexOf(REDNIK) != -1) {
                    jsonStr = jsonStr.substring(0, jsonStr.indexOf(";")).replace("℃", "");
                }
                if (!StringUtils.isEmpty(jsonStr)) {
                    parse = JSON.parseObject(jsonStr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parse;
    }

    @Override
    public WeatherHistory getHistoryWeatherInfo(String mmdd, String code) {
        JSONObject info = this.getJsonInfo(String.format(WEATHER_HISTORY_URL, mmdd, code, String.valueOf(System.currentTimeMillis())));
        WeatherHistory weatherHistory = JSON.toJavaObject(info, WeatherHistory.class);
        weatherHistory.setMaxTemp(info.getInteger("max"));
        weatherHistory.setMinTemp(info.getInteger("min"));
        weatherHistory.setRainfall(info.getString("raingl"));
        weatherHistory.setMmdd(mmdd);
        return weatherHistory;
    }

    @Override
    public Integer addWeatherInfo(WeatherInfo weatherInfo) {
        weatherInfo.quickTime();
        return weatherInfoMapper.insertSelective(weatherInfo);
    }

    @Override
    public Integer addWeatherInfoList(List<WeatherInfo> weatherInfoList) {
        if (weatherInfoList != null && weatherInfoList.size() > 0) {
            return weatherInfoMapper.insertList(weatherInfoList);
        }
        return 0;
    }

    @Override
    public List<WeatherCode> selectWeatherCodeByZoneType(ZoneTypeEnum zoneTypeEnum) {
        return weatherCodeMapper.selectWeatherCodeByZoneType(zoneTypeEnum.getCode());
    }

    @Override
    public String selectWeatherCoderByParent(Integer parent) {
        return weatherCodeMapper.selectWeatherCoderByParent(parent);
    }

    @Override
    public WeatherInfo selectWeatherInfoCache(String code) {
        return this.selectWeatherInfo(code);
    }

    @Override
    public WeatherInfo selectWeatherInfo(String code) {
        return null;
    }

    @Override
    public JSONObject getCustomWeatherInfo(String code) {
        return this.getJsonInfo(String.format(WEATHER_CUSTOM_URL, code, String.valueOf(System.currentTimeMillis())));
    }


}
