package io.github.tangmonkmeat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/18 上午10:45
 * @version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "io.github.tangmonkmeat.mapper")
@EnableEurekaClient
public class GoodsSearchServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(GoodsSearchServiceApplication.class,args);
    }
}
