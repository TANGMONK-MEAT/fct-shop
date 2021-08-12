package io.github.tangmonkmeat.service.impl;

import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.constant.MessageType;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.exception.BusinessException;
import io.github.tangmonkmeat.mapper.ChatMapper;
import io.github.tangmonkmeat.model.vo.WsMessage;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.service.WebSocketService;
import io.github.tangmonkmeat.util.JsonUtil;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import io.github.tangmonkmeat.websocket.ChatEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午2:43
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServiceImpl.class);

    @Resource
    private ChatMapper chatMapper;

    @Autowired
    private RedisService redisService;

    private final String UNREAD_WS_MESSAGE_LIST_KEY
            = RedisKeyUtil.keyBuilder(RedisKeyEnum.UNREAD_WS_MESSAGE_LIST_KEY,"chatId");

    @Override
    public int getUnreadCount(String openId) {
        // 去查userId参与的chat的id
        List<Integer> chatIds = chatMapper.getChatIdByUser(openId);
        List<List<WsMessage>> unreadIds = (List<List<WsMessage>>)redisService.multiGet(chatIds.stream()
                .map(id -> id + "").collect(Collectors.toList()));

        // 过滤自己发送的
        return (int)unreadIds.stream()
                .filter(wxMessageList -> !ObjectUtils.isEmpty(wxMessageList))
                .flatMap(Collection::stream)
                .filter(message -> message.getReceiverId().equals(openId))
                .count();
    }

    @Override
    public void OnMessage(String openId, String message) {
        WsMessage wsMessage;
        try {
            wsMessage = castWsMessage(message);
        } catch (BusinessException e) {
            ChatEndpoint.sendMessage(openId, Response.fail(e.getErrno(),e.getErrmsg()));
            return;
        }

        // 缓存未读消息
        updateUnreadWsMessageList(wsMessage);

        // 如果是第一次发送消息，设置双方可见
        if (wsMessage.getMessageType() == MessageType.FIRST_CHAT){
            chatMapper.updateShowToBoth(wsMessage.getChatId());
        }

        // 如果对方在线，发送消息
        String receiverId = wsMessage.getReceiverId();
        if(ChatEndpoint.isOnline(receiverId)){
            ChatEndpoint.sendMessage(receiverId,Response.ok(wsMessage));
        }
    }

    public void updateUnreadWsMessageList(WsMessage wsMessage){
        Integer chatId = wsMessage.getChatId();
        if (chatId == null){
            return;
        }
        redisService.rpush(UNREAD_WS_MESSAGE_LIST_KEY + ":" + chatId,wsMessage);
    }

    private WsMessage castWsMessage(String rawData) throws BusinessException {
        WsMessage wsMessage = JsonUtil.fromJson(rawData, WsMessage.class);
        if (wsMessage == null) {
            LOGGER.info("消息反序列化失败");
            throw new BusinessException(ResponseStatus.MESSAGE_FORMAT_IS_WRONG.errno(),
                    ResponseStatus.MESSAGE_FORMAT_IS_WRONG.errmsg());
        }
        if (ObjectUtils.isEmpty(wsMessage.getChatId()) ||
                StringUtils.isEmpty(wsMessage.getSenderId()) ||
                StringUtils.isEmpty(wsMessage.getReceiverId()) ||
                ObjectUtils.isEmpty(wsMessage.getGoodsId()) ||
                ObjectUtils.isEmpty(wsMessage.getMessageType()) ||
                ObjectUtils.isEmpty(wsMessage.getMessageBody())) {
            LOGGER.info("消息不完整");
            throw new BusinessException(ResponseStatus.MESSAGE_IS_INCOMPLETE.errno(),
                    ResponseStatus.MESSAGE_IS_INCOMPLETE.errmsg());
        }

        if (wsMessage.getSendTime() == null) {
            wsMessage.setSendTime(new Date());
        }
        return wsMessage;
    }
}
