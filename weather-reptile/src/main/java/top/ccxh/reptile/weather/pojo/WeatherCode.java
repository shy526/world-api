package top.ccxh.reptile.weather.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

/**
 *天气代码
 * @author ccxh
 */
@Getter
@Setter
@Table(name = "t_weather_code")
public class WeatherCode extends BasePojo {

    /**
     * 名称
     */
    private String name;
    /**
     * 代码
     */
    private String code;
    /**
     * 父类
     */
    private Integer parent;

    /**
     * 地区类型
     */
    private Integer zoneType;

}
