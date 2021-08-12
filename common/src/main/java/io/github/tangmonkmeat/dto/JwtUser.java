package io.github.tangmonkmeat.dto;

import java.io.Serializable;

/**
 * 微信授权登录成功后的响应数据 or 构造token的必要数据
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/13 下午7:44
 */
public class JwtUser implements Serializable {

    /**
     * 微信的openid
     */
    private String openId;

    /**
     * 微信号昵称
     */
    private String nickName;

    /**
     * 微信头像链接
     */
    private String avatarUrl;

    public JwtUser(String openId, String nickName, String avatarUrl) {
        this.openId = openId;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
    }

    public JwtUser() {
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
