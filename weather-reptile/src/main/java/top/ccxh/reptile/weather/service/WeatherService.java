package top.ccxh.reptile.weather.service;

import com.alibaba.fastjson.JSONObject;
import top.ccxh.reptile.weather.constants.ZoneTypeEnum;
import top.ccxh.reptile.weather.pojo.WeatherCode;
import top.ccxh.reptile.weather.pojo.WeatherHistory;
import top.ccxh.reptile.weather.pojo.WeatherInfo;


import java.util.List;

/**
 * 天气
 * @author ccxh
 */
public interface WeatherService {

    /**
     * 获取所有省,市,县,地区的code
     * @param code 获取省时传入"",其他为对应上一级的code
     * @return  List<JSONObject>
     */
    List<JSONObject> parse(String code);

    /**
     * 获取订制信息
     * @param code
     * @return
     */
    JSONObject getCustomWeatherInfo(String code);

    /**
     * 获取天气情况
     * @param code 天气代码
     * @return WeatherInfo
     */
    WeatherInfo getWeatherInfo(String code);



    /**
     * 获取历史数据
     * @param mmdd
     * @param code
     * @return JSONObject
     */
    WeatherHistory getHistoryWeatherInfo(String mmdd, String code);

    /**
     * 添加
     * @param weatherInfo 实体
     * @return  Integer
     */
    Integer addWeatherInfo(WeatherInfo weatherInfo);

    /**
     * 批量添加
     * @param weatherInfoList List<WeatherInfo>
     * @return Integer
     */
    Integer addWeatherInfoList(List<WeatherInfo> weatherInfoList);

    /**
     * 批量查询
     * @param zoneTypeEnum 类型
     * @return  List<WeatherCode>
     */
    List<WeatherCode> selectWeatherCodeByZoneType(ZoneTypeEnum zoneTypeEnum);

    /**
     * 查询具体的天气代码
     * @param parent 父Id
     * @return String
     */
    String selectWeatherCoderByParent(Integer parent);
}
