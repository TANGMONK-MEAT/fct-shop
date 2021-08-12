package io.github.tangmonkmeat.service.impl;

import io.github.tangmonkmeat.constant.MessageType;
import io.github.tangmonkmeat.mapper.ChatMapper;
import io.github.tangmonkmeat.mapper.HistoryMapper;
import io.github.tangmonkmeat.model.Chat;
import io.github.tangmonkmeat.model.History;
import io.github.tangmonkmeat.service.ImService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/29 上午11:17
 */
@Service
public class ImServiceImpl implements ImService {

    @Resource
    private ChatMapper chatMapper;

    @Resource
    private HistoryMapper historyMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int createChat(Chat chat,String senderId,String receiverId) {
        // 确认是否已存在对话
        Integer chatId = chatMapper.getChatIdByChat(chat);
        if (chatId == null) {
            chatMapper.insert(chat);
            chatId = chat.getId();
            //创建一条聊天记录,让展示消息列表时能取得到
            History history = new History();
            history.setChatId(chatId);
            history.setU1ToU2(true);
            history.setMessageType(MessageType.ESTABLISH_CHAT);
            history.setSendTime(new Date());
            historyMapper.insert(history);
        }
        return chatId;
    }
}
