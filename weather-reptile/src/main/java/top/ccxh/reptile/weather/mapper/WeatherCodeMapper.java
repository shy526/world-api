package top.ccxh.reptile.weather.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

    /**
     * 查询具体的天气代码
     * @param parent 父Id
     * @return
     */
    @Select("SELECT name FROM t_weather_code WHERE parent=#{parent} AND zone_type=3")
    String selectWeatherCoderByParent(Integer parent);
}
