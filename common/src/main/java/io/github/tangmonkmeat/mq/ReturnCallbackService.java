package io.github.tangmonkmeat.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * RabbitMq 消息未投递到 queue的回调
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 上午10:38
 */
public class ReturnCallbackService implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

    }
}
