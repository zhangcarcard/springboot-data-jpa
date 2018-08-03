package cn.tpson.kulu.dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Zhangka in 2018/08/01
 */
@SpringBootApplication(scanBasePackages = {"cn.tpson.kulu.dispatcher", "cn.tpson.kulu.common"})
public class Application {
    public static void main(String[] args) {
        /*new SpringApplicationBuilder()
                .sources(Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);*/
        SpringApplication.run(Application.class, args);
    }
}
