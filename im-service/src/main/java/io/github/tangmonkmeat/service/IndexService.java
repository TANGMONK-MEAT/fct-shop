package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.model.vo.ChatIndex;

import java.util.Date;

/**
 * Description: 消息列表服务接口
 *
 * @author zwl
 * @date 2021/7/27 下午2:41
 * @version 1.0
 */
public interface IndexService {

    /**
     * 获取用户消息首页数据
     */
    ChatIndex showIndex(String openId, int size, Date offsetTime);
}
