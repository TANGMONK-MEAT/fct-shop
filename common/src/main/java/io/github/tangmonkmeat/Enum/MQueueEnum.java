package io.github.tangmonkmeat.Enum;

import io.github.tangmonkmeat.constant.MqConstant;

/**
 * 消息队列的 queueName，Exchange，routingKey
 *
 * @author zwl
 * @date 2021/7/15 上午9:05
 * @version 1.0
 */
public enum MQueueEnum {

    /*
     * 微信登录：通知【user-service】保存用户信息到 DB
     */
    // 消费通知队列
    USER_REGISTER_SAVE(MqConstant.USER_REGISTER_SAVE_DIRECT,MqConstant.USER_REGISTER_SAVE_QUEUE,MqConstant.USER_REGISTER_SAVE_KEY),
    // 用户注册 死信队列
    DLX_USER_REGISTER_RETRY_OUT("fct.user.register.retry.out.direct.dlx","fct.user.register.retry.out.dlx","fct.user.register.retry.out.dlx"),

    /*
     * 商品信息删除
     */
    ES_PRODUCT_SAVE(MqConstant.ES_GOODS_SAVE_DIRECT,MqConstant.ES_GOODS_SAVE_QUEUE,MqConstant.ES_GOODS_SAVE_KEY),
    DLX_ES_PRODUCT_SAVE(MqConstant.ES_GOODS_SAVE_DIRECT_DLX,MqConstant.ES_GOODS_SAVE_QUEUE_DLX,MqConstant.ES_GOODS_SAVE_KEY_DLX),

    ES_PRODUCT_DEL(MqConstant.ES_GOODS_DEL_DIRECT,MqConstant.ES_GOODS_DEL_QUEUE,MqConstant.ES_GOODS_DEL_KEY),
    DLX_ES_PRODUCT_DEL(MqConstant.ES_GOODS_DEL_DIRECT_DLX,MqConstant.ES_GOODS_DEL_QUEUE_DLX,MqConstant.ES_GOODS_DEL_KEY_DLX),
    ;

    /**
     * 交换名称
     */
    final String exchange;
    /**
     * 队列名称
     */
    final String queueName;
    /**
     * 路由键
     */
    final String routeKey;

    public final String getExchange() {
        return exchange;
    }

    public final String getQueueName() {
        return queueName;
    }

    public final String getRouteKey() {
        return routeKey;
    }

    MQueueEnum(String exchange, String queueName, String routeKey) {
        this.exchange = exchange;
        this.queueName = queueName;
        this.routeKey = routeKey;
    }
}
