package top.ccxh.reptile.weather.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 天气历史数据集合
 * @author admin
 */
@Setter
@Getter
@ToString
public class WeatherHistory extends BasePojo {
    /**
     * 最大温度
     */
    private Integer maxTemp;
    /**
     * 最小温度
     */
    private Integer minTemp;

    /**
     * 降水概率
     */
    private String rainfall;

    /**
     * 月份加日期
     * MMdd
     */
    private String mmdd;

}
