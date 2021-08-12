package io.github.tangmonkmeat.im;

import io.github.tangmonkmeat.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午8:59
 */
@Component
public class ImClientHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImClientHandler.class);

    @Autowired
    private ImClient imClient;

    public Integer createChat(Long goodsId, String senderId, String receiverId) {
        LOGGER.info("【外部服务】调用消息服务创建对话");
        Response<Integer> response = imClient.createChat(goodsId, senderId, receiverId);
        if (response.getErrno() != 0) {
            LOGGER.info("消息服务创建对话失败,errno={},原因={}", response.getErrno(), response.getErrmsg());
            return null;
        }
        return response.getData();
    }
}
