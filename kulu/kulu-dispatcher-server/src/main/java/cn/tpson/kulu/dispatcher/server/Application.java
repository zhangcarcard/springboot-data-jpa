package cn.tpson.kulu.dispatcher.server;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Zhangka in 2018/08/07
 */
@SpringBootApplication
public class Application {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }
}
