package io.github.tangmonkmeat.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.util.Date;
import java.util.List;

/**
 * Description: 消息首页全部数据
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午10:56
 */
public class ChatIndex {

    /**各个消息*/
    private List<ChatIndexEle> chats;

    /**最后一条消息的发送时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date offsetTime;

    @Override
    public String toString() {
        return "ChatIndex{" +
                "chats=" + chats +
                ", offsetTime=" + offsetTime +
                '}';
    }

    public List<ChatIndexEle> getChats() {
        return chats;
    }

    public void setChats(List<ChatIndexEle> chats) {
        this.chats = chats;
    }

    public Date getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(Date offsetTime) {
        this.offsetTime = offsetTime;
    }
}
