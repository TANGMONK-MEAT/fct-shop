package io.github.tangmonkmeat.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import io.github.tangmonkmeat.dto.SimpleGoods;
import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.model.History;

import java.util.Date;
import java.util.List;

/**
 * Description: 聊天信息框内的聊天信息数据
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午10:50
 */
public class ChatForm {

    /**对方用户信息*/
    private SimpleUser otherSide;

    /**与本次聊天相关的商品的信息*/
    private SimpleGoods goods;

    /**当前用户是否时u1*/
    private Boolean isU1;

    /**聊天记录*/
    private List<History> historyList;

    /**
     * 本次展示的聊天记录中最早一条的发送时间,
     * 用于上拉获取更早之前的聊天记录时使用.设置为ISO8601能传递更准确的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date offsetTime;

    @Override
    public String toString() {
        return "ChatForm{" +
                "otherSide=" + otherSide +
                ", goods=" + goods +
                ", isU1=" + isU1 +
                ", historyList=" + historyList +
                ", offsetTime=" + offsetTime +
                '}';
    }

    public SimpleUser getOtherSide() {
        return otherSide;
    }

    public void setOtherSide(SimpleUser otherSide) {
        this.otherSide = otherSide;
    }

    public SimpleGoods getGoods() {
        return goods;
    }

    public void setGoods(SimpleGoods goods) {
        this.goods = goods;
    }

    public Boolean getU1() {
        return isU1;
    }

    public void setU1(Boolean u1) {
        isU1 = u1;
    }

    public List<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }

    public Date getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(Date offsetTime) {
        this.offsetTime = offsetTime;
    }
}
