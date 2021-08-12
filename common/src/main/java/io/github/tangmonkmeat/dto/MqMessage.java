package io.github.tangmonkmeat.dto;

import java.io.Serializable;

/**
 * Description: 发布mq的消息的统一格式
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 下午9:03
 */
public class MqMessage<T> implements Serializable {

    private int type;

    private T data;

    private String remark;

    public MqMessage(){}

    public MqMessage(int type,T data,String remark){
        this.type = type;
        this.data = data;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MqMessage{" +
                "type=" + type +
                ", data=" + data +
                ", remark='" + remark + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
