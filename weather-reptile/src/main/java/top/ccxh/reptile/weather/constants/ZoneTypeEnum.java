package top.ccxh.reptile.weather.constants;

/**
 * 地区枚举
 * @author admin
 */

public enum ZoneTypeEnum {
    /**
     * 省
     */
    PROVINCE(0),
    /**
     * 市
     */
    CITY(1),
    /**
     * 县
     */
    COUNTY(1),
    /**
     * 天气代码
     */
    WEATHER_CODE(3);
    private Integer code;

    ZoneTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
