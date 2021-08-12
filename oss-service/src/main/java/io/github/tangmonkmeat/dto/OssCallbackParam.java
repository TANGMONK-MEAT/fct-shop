package io.github.tangmonkmeat.dto;

import java.io.Serializable;

/**
 * Description: 回调接口设置, 当OSS上传成功后，会根据该配置参数来回调对应接口。
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/17 下午7:19
 */
public class OssCallbackParam implements Serializable {

    /**
     * 请求的回调地址
     */
    private String callbackUrl;

    /**
     * 回调时，传入request中的参数
     */
    private String callbackBody;

    /**
     * 回调时, 传入参数的格式，比如表单提交形式 application/x-www-form-urlencoded
     */
    private String callbackBodyType;

    @Override
    public String toString() {
        return "OssCallbackParam{" +
                "callbackUrl='" + callbackUrl + '\'' +
                ", callbackBody='" + callbackBody + '\'' +
                ", callbackBodyType='" + callbackBodyType + '\'' +
                '}';
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackBody() {
        return callbackBody;
    }

    public void setCallbackBody(String callbackBody) {
        this.callbackBody = callbackBody;
    }

    public String getCallbackBodyType() {
        return callbackBodyType;
    }

    public void setCallbackBodyType(String callbackBodyType) {
        this.callbackBodyType = callbackBodyType;
    }
}
