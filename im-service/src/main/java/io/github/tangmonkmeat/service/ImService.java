package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.model.Chat;

public interface ImService {

    /**
     * 如果不存在聊天记录，则添加
     *
     * @param chat Chat
     * @param senderId 发送者的openId
     * @param receiverId 接受者的openId
     * @return chatId
     */
    int createChat(Chat chat,String senderId,String receiverId);

}
