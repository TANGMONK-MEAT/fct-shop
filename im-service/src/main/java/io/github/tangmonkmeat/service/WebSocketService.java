package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.exception.BusinessException;

/**
 * Description: websocket 服务接口
 *
 * @author zwl
 * @date 2021/7/27 下午2:41
 * @version 1.0
 */
public interface WebSocketService {

    int getUnreadCount(String userId);

    void OnMessage(String senderId, String message);

}
