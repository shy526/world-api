package top.ccxh.reptile.weather.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.ccxh.reptile.weather.pojo.WeatherCode;
import top.ccxh.reptile.weather.pojo.WeatherInfo;

import java.util.List;

/**
 * 天气数据操作
 * @author ccxh
 */
@Mapper
public interface WeatherInfoMapper extends BaseMapper<WeatherInfo> {

}
