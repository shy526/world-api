package top.ccxh.world.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;


/**
 *启动类
 * @author ccxh
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan({"top.ccxh.reptile.weather","top.ccxh.world.web","top.ccxh.reptile.weather.mapper"})
public class WorldWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorldWebApplication.class, args);
    }
}
