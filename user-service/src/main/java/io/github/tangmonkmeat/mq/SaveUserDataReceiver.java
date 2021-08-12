package io.github.tangmonkmeat.mq;

import io.github.tangmonkmeat.Enum.MQueueEnum;
import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.constant.MqConstant;
import io.github.tangmonkmeat.dto.MqMessage;
import io.github.tangmonkmeat.exception.UserRegisterRetryOutException;
import io.github.tangmonkmeat.model.User;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.service.UserService;
import io.github.tangmonkmeat.token.TokenManager;
import io.github.tangmonkmeat.util.JsonUtil;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import io.github.tangmonkmeat.vo.AuthVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * 处理消息队列中的用户注册信息
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 上午9:57
 */
@Component
public class SaveUserDataReceiver {

    private final Logger LOGGER = LoggerFactory.getLogger(SaveUserDataReceiver.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenManager tokenManager;

    @Value("${spring.rabbitmq.listener.direct.retry.enabled}")
    private boolean retryEnable;

    @Value("${spring.rabbitmq.listener.direct.retry.max-attempts}")
    private int maxRetry;

    final String key = RedisKeyUtil.keyBuilder(RedisKeyEnum.USER_REGISTER_RETRY_COUNT_KEY,"retry_count");

    /**
     * 接收 [auth-service] 通知的用户消息，save 到数据库, 手动ACK。
     *
     * 如果中途发生异常，将重试 maxRetry 次数 ，超过最大次数，消息将会被转发到 死信队列
     *
     * @param userData {@link io.github.tangmonkmeat.model.User}
     */
    @RabbitListener(queues = MqConstant.USER_REGISTER_SAVE_QUEUE)
    public void handle(String userData, Channel channel,
                       @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws UserRegisterRetryOutException, IOException {
        final int MAX_RETRY = maxRetry;
        final boolean RETRY_ENABLE = retryEnable;
        try {
            LOGGER.info("从 [ auth-service ] 收到 [ 用户注册 ]的消息");

            MqMessage message = JsonUtil.fromJson(userData, MqMessage.class);
            User user = JsonUtil.fromJson((String) message.getData(), User.class);
            String openId = user.getOpenId();
            AuthVO cache = tokenManager.getAuthVoFromCache(openId);
            // 用户已经存在
            if (cache != null || userService.isRegistered(openId)) {
                LOGGER.info("用户已经存在，无需重复注册");
                return;
            }
            // 保存到 DB
            LOGGER.info("新用户：用户名 = [{}],城市 = [{}]", user.getNickName(), user.getCity());
            // 注册
            userService.register(user);
            channel.basicAck(deliveryTag, false);
            LOGGER.info("新用户注册成功");
        } catch (Exception e) {
            LOGGER.info("用户注册异常，重试");
            // 达到最大重试次数，拒绝消息，进入死信队列
            if (RETRY_ENABLE && redisService.incr(key) > MAX_RETRY){
                LOGGER.error("用户注册异常，到达最大重试次数，" +
                        "转发到死信队列：{}" + MQueueEnum.DLX_USER_REGISTER_RETRY_OUT.getQueueName());
                channel.basicReject(deliveryTag,false);
                redisService.remove(key);
            }else {
                throw new UserRegisterRetryOutException(ResponseStatus.USER_REGISTER_WRONG.errno(),
                        ResponseStatus.USER_REGISTER_WRONG.errmsg());
            }
        }
    }
}