package io.github.tangmonkmeat.websocket;

import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.constant.MessageType;
import io.github.tangmonkmeat.constant.TokenConstant;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.model.vo.WsMessage;
import io.github.tangmonkmeat.service.WebSocketService;
import io.github.tangmonkmeat.token.TokenManager;
import io.github.tangmonkmeat.util.JsonUtil;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Description: websocket Server Endpoint
 * <p>
 * 每建立一个新连接，都会产生一个 ServerEndpoint,
 * <p>
 * 并且，ServerEndpoint 最终是由 tomcat 统一管理的, 所以无法 使用SpringIOC管理，注入其他bean
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午2:49
 */
@Component
@ServerEndpoint(value = "/ws/{openId}", configurator = ChatEndpointConfigure.class)
public class ChatEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatEndpoint.class);

    private final static ConcurrentHashMap<String,Session> SESSION_MAP = new ConcurrentHashMap<>();

    private static WebSocketService webSocketService;

    private static TokenManager tokenManager;


    public static void setWebSocketService(WebSocketService webSocketService) {
        ChatEndpoint.webSocketService = webSocketService;
    }

    public static void setTokenManager(TokenManager tokenManager) {
        ChatEndpoint.tokenManager = tokenManager;
    }

    /**
     * 客户端创建ws连接后的回调
     */
    @OnOpen
    public void onOpen(@PathParam("openId") String openId, Session session, EndpointConfig config) throws Exception {
        // 校验token
        String token = (String) config.getUserProperties().get(TokenConstant.AUTHORIZATION);
        JwtUser user = tokenManager.solve(token);
        if (user == null || !openId.equals(user.getOpenId())) {
            LOGGER.info("websocket消息】token检验失败,拒绝连接, openId = [{}]", openId);
            rejectConnect(session);
            session.close();
            SESSION_MAP.remove(openId);
            return;
        }

        // 缓存 session
        SESSION_MAP.put(openId, session);

        // 发送唯独消息给客户端
        int unreadCount = webSocketService.getUnreadCount(openId);
        WsMessage wsMessage = new WsMessage();
        wsMessage.setMessageType(MessageType.UNREAD_NUM);
        wsMessage.setMessageBody(unreadCount + "");
        wsMessage.setSendTime(new Date());
        sendMessage(openId, Response.ok(wsMessage));

        LOGGER.info("【websocket消息】有新的连接, openId = [{}],用户昵称= [{}],未读消息数={}", openId, user.getNickName(), "unreadCount");
    }

    /**
     * 接受客户端消息后的回调
     */
    @OnMessage
    public void onMessage(@PathParam("openId") String openId, String message) {
        LOGGER.info("【websocket消息】收到客户端发来的消息:openId = [{}],消息内容 = [{}]", openId, message);
        webSocketService.OnMessage(openId, message);
    }

    /**
     * 客户端关闭 ws连接后的回调
     */
    @OnClose
    public void onClose(@PathParam("openId") String openId, Session session) throws IOException {
        session.close();
        SESSION_MAP.remove(openId);
        LOGGER.info("【websocket消息】连接断开, openId = [{}]", openId);
    }

    private void rejectConnect(Session session) {
        LOGGER.info("【拒绝连接】通知客户端关闭ws连接");
        Response<Object> response = Response.fail(ResponseStatus.TOKEN_IS_WRONG);
        try {
            session.getBasicRemote().sendText(JsonUtil.toJson(response));
            LOGGER.info("【拒绝连接】通知发送成功");
        } catch (IOException e) {
            LOGGER.info("【拒绝连接】通知发送失败", e);
        }
    }

    public static void sendMessage(String openId, Response<?> response) {
        Session session = SESSION_MAP.get(openId);
        if (session == null) {
            LOGGER.info("消息发送失败,不存在该session:openId = [{}],消息内容 = [{}]", openId, response);
        } else {
            try {
                session.getBasicRemote().sendText(JsonUtil.toJson(response));
                LOGGER.info("消息发送成功:openId = [{}],消息内容 = [{}]", openId, response);
            } catch (IOException e) {
                LOGGER.info("消息发送失败:openId = [{}],消息内容 = [{}]", openId, response, e);
            }
        }
    }

    public static boolean isOnline(String openId) {
        return SESSION_MAP.containsKey(openId);
    }
}