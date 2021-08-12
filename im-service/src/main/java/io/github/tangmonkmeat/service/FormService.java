package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.model.History;
import io.github.tangmonkmeat.model.vo.ChatForm;

import java.util.Date;
import java.util.List;

/**
 * Description: 聊天框服务接口
 *
 * @author zwl
 * @date 2021/7/27 下午2:42
 * @version 1.0
 */
public interface FormService {

    /**
     * 聊天框内的信息
     */
    ChatForm showForm(int chatId, String openId, int size, Date offsetTime);

    List<History> flushUnread(int chatId, String openId);

    void delAndClearChat(int chatId);
}
