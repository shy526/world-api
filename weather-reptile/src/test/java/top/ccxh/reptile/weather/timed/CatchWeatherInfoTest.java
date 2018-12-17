package top.ccxh.reptile.weather.timed;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.ccxh.BaseTest;

import static org.junit.Assert.*;

public class CatchWeatherInfoTest extends BaseTest {
    @Autowired
    CatchWeatherInfo catchWeatherInfo;
    @Test
    public void run() {
        catchWeatherInfo.run();
    }
}