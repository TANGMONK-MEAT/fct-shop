package io.github.tangmonkmeat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 简单的用户信息
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/13 下午8:33
 */
public class SimpleUser implements Serializable {
    private static final String DEFAULT_AVATAR_URL = "https://i.postimg.cc/RVbDV5fN/anonymous.png";
    private static final String DEFAULT_NICK_NAME = "unknown";

    private String openId;
    private String nickName;
    private String avatarUrl;

    public SimpleUser(){}

    public SimpleUser(String nickName, String avatarUrl) {
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
    }

    @JsonFormat(pattern = StdDateFormat.DATE_FORMAT_STR_ISO8601)
    private Date registerTime;

    public static SimpleUser unknownUser(){
        return new SimpleUser(DEFAULT_NICK_NAME, DEFAULT_AVATAR_URL);
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

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
