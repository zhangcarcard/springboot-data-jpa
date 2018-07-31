package cn.tpson.kulu.gas.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Zhangka in 2018/07/11
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@ComponentScan("cn.tpson.kulu.gas")
public class Application {
    public static void main(String[] args) {
        /*new SpringApplicationBuilder()
                .sources(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);*/
        SpringApplication.run(Application.class, args);
    }
}
