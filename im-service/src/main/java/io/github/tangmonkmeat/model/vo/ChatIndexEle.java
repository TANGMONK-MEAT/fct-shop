package io.github.tangmonkmeat.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.tangmonkmeat.dto.SimpleGoods;
import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.model.History;

/**
 * Description: 消息首页，消息列表页面的各个消息.
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午10:52
 */
public class ChatIndexEle {

    /**未读数*/
    private Integer unreadCount;

    /**对方用户信息*/
    private SimpleUser otherSide;

    /**与本次聊天相关的商品的信息*/
    private SimpleGoods goods;

    /**最后一条聊天记录*/
    private History lastChat;

    /**方便调用其他服务进行查询,不传前端*/
    @JsonIgnore
    private String userId;

    @JsonIgnore
    private Long goodsId;

    @Override
    public String toString() {
        return "ChatIndexEle{" +
                "unreadCount=" + unreadCount +
                ", otherSide=" + otherSide +
                ", goods=" + goods +
                ", lastChat=" + lastChat +
                ", userId='" + userId + '\'' +
                ", goodsId=" + goodsId +
                '}';
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
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

    public History getLastChat() {
        return lastChat;
    }

    public void setLastChat(History lastChat) {
        this.lastChat = lastChat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
