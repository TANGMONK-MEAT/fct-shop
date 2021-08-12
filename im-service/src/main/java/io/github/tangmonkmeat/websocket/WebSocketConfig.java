package io.github.tangmonkmeat.websocket;

import io.github.tangmonkmeat.service.WebSocketService;
import io.github.tangmonkmeat.token.TokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Description: WebSocket 配置
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午3:40
 */
@Configuration
public class WebSocketConfig {

    /**
     * ServerEndpointExporter 用于扫描和注册所有携带 ServerEndPoint 注解的实例，
     *
     * 注意：若部署到外部容器 则无需提供此类。
     *
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(WebSocketService webSocketService,
                                                         TokenManager tokenManager) {
        ChatEndpoint.setTokenManager(tokenManager);
        ChatEndpoint.setWebSocketService(webSocketService);
        return new ServerEndpointExporter();
    }
}
