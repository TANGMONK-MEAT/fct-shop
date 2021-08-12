package io.github.tangmonkmeat.websocket;

import io.github.tangmonkmeat.constant.TokenConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

/**
 * Description: 获取websocket header中的token 添加到 websocket的userProperties中
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午2:50
 */
public class ChatEndpointConfigure extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        Map<String, List<String>> headers = request.getHeaders();
        List<String> tokenHeader = headers.get(TokenConstant.AUTHORIZATION);
        if (!CollectionUtils.isEmpty(tokenHeader)){
            String token = tokenHeader.get(0);
            sec.getUserProperties().put(TokenConstant.AUTHORIZATION,token);
        }else{
            sec.getUserProperties().put(TokenConstant.AUTHORIZATION,"");
        }
    }
}