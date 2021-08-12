package io.github.tangmonkmeat.dto;

import java.io.Serializable;

/**
 * Description: 前端上传文件所需必要参数
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/17 下午7:32
 */
public class OssPolicyResult implements Serializable {

    /**
     * 访问身份验证中用到用户标识
     */
    private String accessKeyId;

    /**
     * 用户表单上传的策略,经过base64编码过的字符串
     */
    private String policy;

    /**
     * 对policy签名后的字符串
     */
    private String signature;

    /**
     * 上传文件夹路径前缀
     */
    private String dir;

    /**
     * oss对外服务的访问域名
     */
    private String host;

    /**
     * 上传成功后的回调接口设置
     */
    private String callback;

    @Override
    public String toString() {
        return "OssPolicyResult{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", policy='" + policy + '\'' +
                ", signature='" + signature + '\'' +
                ", dir='" + dir + '\'' +
                ", host='" + host + '\'' +
                ", callback='" + callback + '\'' +
                '}';
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
