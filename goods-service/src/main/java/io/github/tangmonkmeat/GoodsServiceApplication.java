package io.github.tangmonkmeat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/17 下午9:10
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan(excludeFilters
        = @ComponentScan.Filter(type = FilterType.REGEX,pattern = "io.github.tangmonkmeat.goods.*"))
@MapperScan(basePackages = "io.github.tangmonkmeat.mapper")
@EnableEurekaClient
@EnableFeignClients(basePackages = {"io.github.tangmonkmeat.user", "io.github.tangmonkmeat.im"})
public class GoodsServiceApplication {
    public static void main( String[] args ) {
        SpringApplication.run(GoodsServiceApplication.class,args);
    }
}
