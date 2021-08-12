package io.github.tangmonkmeat.config;

import io.github.tangmonkmeat.Enum.MQueueEnum;
import io.github.tangmonkmeat.mq.ConfirmCallbackService;
import io.github.tangmonkmeat.mq.ReturnCallbackService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMq 消息队列的声明和绑定
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 上午9:06
 */
@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtual;

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPassword(password);
        factory.setUsername(username);
        factory.setPort(port);
        factory.setVirtualHost(virtual);
        return factory;
    }

    @Bean
    public ConfirmCallbackService confirmCallbackService(){
        return new ConfirmCallbackService();
    }

    @Bean
    public ReturnCallbackService returnCallbackService(){
        return new ReturnCallbackService();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConfirmCallbackService confirmCallbackService,
                                         ReturnCallbackService returnCallbackService,
                                         CachingConnectionFactory cachingConnectionFactory){
        RabbitTemplate template = new RabbitTemplate();
        template.setConfirmCallback(confirmCallbackService);
        template.setReturnCallback(returnCallbackService);
        template.setConnectionFactory(cachingConnectionFactory);
        return template;
    }

    // 用户消息绑定

    @Bean
    public DirectExchange userDataDirect(){
        return ExchangeBuilder
                .directExchange(MQueueEnum.USER_REGISTER_SAVE.getExchange())
                // 持久化
                .durable(true)
                .build();
    }

    @Bean
    public DirectExchange userDateDirectDlx(){
        return ExchangeBuilder
                .directExchange(MQueueEnum.DLX_USER_REGISTER_RETRY_OUT.getExchange())
                .durable(true)
                .build();
    }

    @Bean
    public Queue userDataQueue(){
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", MQueueEnum.DLX_USER_REGISTER_RETRY_OUT.getExchange());
        args.put("x-dead-letter-routing-key", MQueueEnum.DLX_USER_REGISTER_RETRY_OUT.getRouteKey());
        return QueueBuilder
                // 持久化
                .durable(MQueueEnum.USER_REGISTER_SAVE.getQueueName())
                // 指定死信队列
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue userDataQueueDlx(){
        return QueueBuilder
                .durable(MQueueEnum.DLX_USER_REGISTER_RETRY_OUT.getQueueName())
                .build();
    }

    @Bean
    public Binding userDataBinding(){
        return BindingBuilder
                .bind(userDataQueue())
                .to(userDataDirect())
                .with(MQueueEnum.USER_REGISTER_SAVE.getRouteKey());
    }

    @Bean
    public Binding userDataBindingDlx(){
        return BindingBuilder
                .bind(userDataQueueDlx())
                .to(userDateDirectDlx())
                .with(MQueueEnum.DLX_USER_REGISTER_RETRY_OUT.getRouteKey());
    }

    // 商品信息保存

    @Bean
    public DirectExchange productDataSaveDirect(){
        return ExchangeBuilder
                .directExchange(MQueueEnum.ES_PRODUCT_SAVE.getExchange())
                // 持久化
                .durable(true)
                .build();
    }

    @Bean
    public DirectExchange productDataSaveDirectDlx(){
        return ExchangeBuilder
                .directExchange(MQueueEnum.DLX_ES_PRODUCT_SAVE.getExchange())
                .durable(true)
                .build();
    }

    @Bean
    public Queue productDataSaveQueue(){
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", MQueueEnum.DLX_ES_PRODUCT_SAVE.getExchange());
        args.put("x-dead-letter-routing-key", MQueueEnum.DLX_ES_PRODUCT_SAVE.getRouteKey());
        return QueueBuilder
                // 持久化
                .durable(MQueueEnum.ES_PRODUCT_SAVE.getQueueName())
                // 指定死信队列
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue productDataSaveQueueDlx(){
        return QueueBuilder
                .durable(MQueueEnum.DLX_ES_PRODUCT_SAVE.getQueueName())
                .build();
    }

    @Bean
    public Binding productDataSaveBinding(){
        return BindingBuilder
                .bind(productDataSaveQueue())
                .to(productDataSaveDirect())
                .with(MQueueEnum.ES_PRODUCT_SAVE.getRouteKey());
    }

    @Bean
    public Binding productDataSaveBindingDlx(){
        return BindingBuilder
                .bind(productDataSaveQueueDlx())
                .to(productDataSaveDirectDlx())
                .with(MQueueEnum.DLX_ES_PRODUCT_SAVE.getRouteKey());
    }

    // 商品信息保存

    @Bean
    public DirectExchange productDataDelDirect(){
        return ExchangeBuilder
                .directExchange(MQueueEnum.ES_PRODUCT_DEL.getExchange())
                // 持久化
                .durable(true)
                .build();
    }

    @Bean
    public DirectExchange productDataDelDirectDlx(){
        return ExchangeBuilder
                .directExchange(MQueueEnum.DLX_ES_PRODUCT_DEL.getExchange())
                .durable(true)
                .build();
    }

    @Bean
    public Queue productDataDelQueue(){
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", MQueueEnum.DLX_ES_PRODUCT_DEL.getExchange());
        args.put("x-dead-letter-routing-key", MQueueEnum.DLX_ES_PRODUCT_DEL.getRouteKey());
        return QueueBuilder
                // 持久化
                .durable(MQueueEnum.ES_PRODUCT_DEL.getQueueName())
                // 指定死信队列
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue productDataDelQueueDlx(){
        return QueueBuilder
                .durable(MQueueEnum.DLX_ES_PRODUCT_DEL.getQueueName())
                .build();
    }

    @Bean
    public Binding productDataDelBinding(){
        return BindingBuilder
                .bind(productDataDelQueue())
                .to(productDataDelDirect())
                .with(MQueueEnum.ES_PRODUCT_DEL.getRouteKey());
    }

    @Bean
    public Binding productDataDelBindingDlx(){
        return BindingBuilder
                .bind(productDataDelQueueDlx())
                .to(productDataDelDirectDlx())
                .with(MQueueEnum.DLX_ES_PRODUCT_DEL.getRouteKey());
    }
}
