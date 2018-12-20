package top.ccxh.world.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Hello world!
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan({"top.ccxh.reptile.weather","top.ccxh.world.web"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
