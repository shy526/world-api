package top.ccxh.reptile.weather.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.ccxh.reptile.weather.pojo.BasePojo;

import javax.persistence.Table;

/**
 * 天气数据实体
 * @author admin
 */
@Getter
@Setter
@ToString
@Table(name = "t_weather_info")
public class WeatherInfo extends BasePojo {


    private Integer weatherCoderId;
    /**
     * 当前温度
     */
    private Integer temp;
    /**
     * 最高温度
     */
    private Integer maxTemp;
    /**
     * 最低温度
     */
    private Integer minTemp;
    /**
     * 风向
     */
    private String wd;
    /**
     * 风速
     */
    private String ws;
    /**
     * 相对湿度
     */
    private String sd;

    /**
     * api
     */
    private Integer aqi;

    /**
     * api 2.5
     */
    private Integer aqiPm25;

    /**
     * 风力
     */
    private String wse;

    /**
     * 监控时间
     */
    private Long watchTime;

    /**
     * 降水
     */
    private Double rain;
    /**
     * 小时降水
     */
    private Double rain24h;

    /**
     * 天气
     */
    private String weather;
}
