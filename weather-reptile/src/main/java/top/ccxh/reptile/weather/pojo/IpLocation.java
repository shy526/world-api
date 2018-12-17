package top.ccxh.reptile.weather.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IpLocation extends BasePojo {
    /**
     * ip
     */
    private String ip;
    /**
     * 位置
     */
    private String location;
    /**
     * 数据来源
     */
    private String source;
}
