package io.github.tangmonkmeat.mq;

import io.github.tangmonkmeat.Enum.MQueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 保存用户信息的发送者
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 上午9:54
 */
@Component
public class SaveUserDataSender {

    private final Logger LOGGER = LoggerFactory.getLogger(SaveUserDataSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String userData){
        rabbitTemplate.convertAndSend(
                MQueueEnum.USER_REGISTER_SAVE.getExchange(),
                MQueueEnum.USER_REGISTER_SAVE.getRouteKey(),userData);
        LOGGER.info("send userData:{}",userData);
    }
}
