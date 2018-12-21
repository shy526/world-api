package top.ccxh.reptile.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Hello world!
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class WeatherReptileApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeatherReptileApplication.class, args);
    }


}
