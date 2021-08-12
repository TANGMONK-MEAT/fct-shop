package io.github.tangmonkmeat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis 配置
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 上午9:44
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private int database;

    @Bean("cacheGoodsMapScript")
    public RedisScript<Long> cacheGoodsMapScript(){
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("/script/setExGoodsMap.lua"));
        script.setResultType(Long.class);
        return script;
    }

    @Bean("cacheCollectAndUserScript")
    public RedisScript<Long> cacheCollectAndUserScript(){
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("/script/setCollectAndUser.lua"));
        script.setResultType(Long.class);
        return script;
    }

    @Bean("cacheGoodsListScript")
    public RedisScript<Long> cacheGoodsListScript(){
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("/script/setExGoodsList.lua"));
        script.setResultType(Long.class);
        return script;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration){
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // 单机版的redis，必须这样设置密码，否则会显示 unauth
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        return redisStandaloneConfiguration;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        // 设置序列化方式
        //Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 设置 string
        template.setKeySerializer(stringRedisSerializer);
        //template.setValueSerializer(jackson2JsonRedisSerializer);
        // 设置 hash
        template.setHashKeySerializer(stringRedisSerializer);
        //template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }
}
