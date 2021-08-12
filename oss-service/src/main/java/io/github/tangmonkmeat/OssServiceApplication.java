package io.github.tangmonkmeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/21 上午11:06
 */
@SpringBootApplication
@EnableEurekaClient
public class OssServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssServiceApplication.class,args);
    }
}
