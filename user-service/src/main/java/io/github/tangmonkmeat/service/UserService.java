package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.model.User;

import java.util.List;
import java.util.Map;

/**
 * Description: User 表操作
 *
 * @author zwl
 * @date 2021/7/17 上午8:30
 * @version 1.0
 */
public interface UserService {

    /**
     * 保存用户信息到 DB
     *
     * @param user {@link User}
     */
    void register(User user);

    /**
     * 判断用户是否已经注册
     *
     * @param openId 微信的openid
     * @return true代表已经注册
     */
    boolean isRegistered(String openId);

    /**
     * 获取 {@link SimpleUser}
     *
     * @param openId 微信openid
     * @return SimpleUser
     */
    SimpleUser getSimpleUser(String openId);

    /**
     * 获取 {@link SimpleUser} 列表
     *
     * @param openIdList 微信openId 列表
     * @return SimpleUser 列表
     */
    Map<String,SimpleUser> getSimpleUserList(List<String> openIdList);
}
