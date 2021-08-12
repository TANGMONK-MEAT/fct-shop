package io.github.tangmonkmeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 * @author zwl
 * @date 2021/7/14 下午1:29
 * @version 1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class AuthServiceApplication {
    public static void main( String[] args ) {
        SpringApplication.run(AuthServiceApplication.class);
    }
}
