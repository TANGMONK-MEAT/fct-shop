package io.github.tangmonkmeat.dto;


import java.io.Serializable;

/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/15 上午11:07
 * @version 1.0
 */
public class AuthDTO implements Serializable {
    private String code;
    private DetailAuthDTO detail;

    @Override
    public String toString() {
        return "AuthDTO{" +
                "code='" + code + '\'' +
                ", detail=" + detail +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DetailAuthDTO getDetail() {
        return detail;
    }

    public void setDetail(DetailAuthDTO detail) {
        this.detail = detail;
    }
}
