package io.github.tangmonkmeat.config;

import io.github.tangmonkmeat.token.injection.AuthInterceptor;
import io.github.tangmonkmeat.token.injection.JwtResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 上午10:46
 */
@Configuration
public class ResolverConfig implements WebMvcConfigurer {

    @Bean
    public JwtResolver jwtResolver(){
        return new JwtResolver();
    }

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**").excludePathPatterns("/wxLogin");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jwtResolver());
    }
}
