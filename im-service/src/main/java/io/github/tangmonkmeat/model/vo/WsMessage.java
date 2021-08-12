package io.github.tangmonkmeat.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午4:17
 */
public class WsMessage implements Serializable {

    private Integer chatId;
    private String senderId;
    private String receiverId;
    private Integer goodsId;

    /**{@link io.github.tangmonkmeat.constant.MessageType}*/
    private Integer messageType;
    private String messageBody;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss", locale = "CHINA", timezone = "Asia/Shanghai")
    private Date sendTime;

    @Override
    public String toString() {
        return "WsMessage{" +
                "chatId=" + chatId +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", goodsId=" + goodsId +
                ", messageType=" + messageType +
                ", messageBody='" + messageBody + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
