package top.ccxh.reptile.weather.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 基础
 * @author admin
 */
@Getter
@Setter
@ToString
public class BasePojo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新时间
     */
    private Long updateTime;


    /**
     * 快速更新开始时间和创建时间
     */
    public void quickTime(){
        this.createTime=System.currentTimeMillis();
        this.updateTime=this.createTime;
    }
}
