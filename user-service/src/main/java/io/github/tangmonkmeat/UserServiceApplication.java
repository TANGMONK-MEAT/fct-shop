package io.github.tangmonkmeat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/16 下午8:56
 */
@SpringBootApplication
@MapperScan(basePackages = "io.github.tangmonkmeat.mapper")
@EnableEurekaClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class,args);
    }
}
