package top.ccxh.reptile.weather.mapper;


import org.apache.ibatis.annotations.Mapper;
import top.ccxh.reptile.weather.pojo.WeatherCode;

import java.util.List;

@Mapper
public interface WeatherCodeMapper extends BaseMapper<WeatherCode> {
    /**
     * 根据地区类型查询
     * @param code 地区类型
     * @return  List<WeatherCode>
     */
    List<WeatherCode> selectWeatherCodeByZoneType(Integer code);
}
