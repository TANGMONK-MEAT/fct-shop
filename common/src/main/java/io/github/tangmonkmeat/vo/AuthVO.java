package io.github.tangmonkmeat.vo;

import io.github.tangmonkmeat.dto.JwtUser;

import java.io.Serializable;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 上午11:23
 */
public class AuthVO implements Serializable {
    private String token;
    private JwtUser userInfo;

    public AuthVO(){}

    public AuthVO(String token, JwtUser userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "AuthVO{" +
                "token='" + token + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(JwtUser userInfo) {
        this.userInfo = userInfo;
    }
}
