package io.github.tangmonkmeat.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * RabbitMq 消息投递到 exchange的回调
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 上午10:19
 */
public class ConfirmCallbackService implements RabbitTemplate.ConfirmCallback {

    private final Logger LOGGER = LoggerFactory.getLogger(ConfirmCallbackService.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId();
        Message message = correlationData.getReturnedMessage();
        if (ack) {
            // 消息发送成功
            LOGGER.info("Mq发送消息失败，id：{}，message: {}", id, message);
            return;
        }
        // 消息发送失败
        LOGGER.info("Mq发送消息失败，id: {}, message: {}, cause: {}",id,message,cause);
        // 保存到 redis，由定时任务处理
    }
}
