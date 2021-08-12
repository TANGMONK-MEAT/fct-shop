package io.github.tangmonkmeat.mq;

import io.github.tangmonkmeat.Enum.MQueueEnum;
import io.github.tangmonkmeat.dto.MqMessage;
import io.github.tangmonkmeat.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午9:02
 */
@Component
public class EsProductSender {

    private final Logger LOGGER = LoggerFactory.getLogger(EsProductSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 通知【goods-search-service】保存指定的商品信息
     *
     * @param message MqMessage-商品id
     */
    public void sendSaveMessage(MqMessage<Long> message){
        String json = JsonUtil.toJson(message);
        rabbitTemplate.convertAndSend(MQueueEnum.ES_PRODUCT_SAVE.getExchange(),
                MQueueEnum.ES_PRODUCT_SAVE.getRouteKey(),json);
        LOGGER.info("send to es for save product message: {}, goodsId: {}",json,message.getData());
    }

    /**
     * 通知【goods-search-service】删除指定的商品信息
     *
     * @param message MqMessage-商品id
     */
    public void sendDelMessage(MqMessage<Integer> message){
        String json = JsonUtil.toJson(message);
        rabbitTemplate.convertAndSend(MQueueEnum.ES_PRODUCT_DEL.getExchange(),
                MQueueEnum.ES_PRODUCT_DEL.getRouteKey(),json);
        LOGGER.info("send to es for del product message: {}, goodsId: {}",json,message.getData());
    }
}