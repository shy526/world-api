package top.ccxh.reptile.weather.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.ccxh.reptile.weather.pojo.WeatherInfo;

/**
 * 天气数据操作
 * @author ccxh
 */
@Mapper
public interface WeatherInfoMapper extends BaseMapper<WeatherInfo> {

    /**
     * 查询某个地区天气信息最新的信息
     * @param weatherCodeId
     * @return
     */
    @Select("select * from t_weather_info where id=(select max(id) from t_weather_info where weather_coder_id=#{weatherCodeId} )")
    WeatherInfo selectWeatherInfoByCoderId(Integer weatherCodeId);
}
