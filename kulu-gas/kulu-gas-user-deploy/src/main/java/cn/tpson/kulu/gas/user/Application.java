package cn.tpson.kulu.gas.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Zhangka in 2018/07/11
 */
@SpringBootApplication
@ComponentScan("cn.tpson.kulu.gas")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
