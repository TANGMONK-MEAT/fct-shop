package io.github.tangmonkmeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/24 下午8:49
 * @version 1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {
    public static void main( String[] args ) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
