package io.github.tangmonkmeat.controller;

import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.model.Chat;
import io.github.tangmonkmeat.service.ImService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午8:53
 */
@RestController
@RequestMapping(value = "/chat-service")
public class ImServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImServiceController.class);

    @Autowired
    private ImService imService;

    /**
     * 创建 对话
     *
     */
    @PostMapping("/createChat/{goodsId}/{senderId}/{receiverId}")
    public Response<Integer> createChat(@PathVariable("goodsId") Integer goodsId,
                                        @PathVariable("senderId") String senderId,
                                        @PathVariable("receiverId") String receiverId) {
        // u1 < u2
        Chat chat = new Chat();

        chat.setGoodsId(goodsId);
        chat.setU1(senderId);
        chat.setU2(receiverId);
        chat.setShowToU1(true);
        chat.setShowToU2(false);
        //if (senderId.compareTo(receiverId) < 0) {
        //    chat.setU1(senderId);
        //    chat.setU2(receiverId);
        //    chat.setShowToU1(true);
        //    chat.setShowToU2(false);
        //} else {
        //    chat.setU1(receiverId);
        //    chat.setU2(senderId);
        //    chat.setShowToU1(false);
        //    chat.setShowToU2(true);
        //}

        int chatId = imService.createChat(chat, senderId, receiverId);
        LOGGER.info("创建聊天chatId={},发起人id={},接收方id={}", chatId, senderId, receiverId);
        return Response.ok(chatId);
    }
}
