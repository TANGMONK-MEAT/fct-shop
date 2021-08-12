package io.github.tangmonkmeat.model.vo;


import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.model.GoodsComment;

import java.util.List;

/**
 * 商品评论
 *
 * @author nnkwrik
 * @date 18/11/23 16:09
 */
public class CommentVo extends GoodsComment {

    /**评论用户信息*/
    private SimpleUser simpleUser;

    /**回复的用户昵称*/
    private String replyUserName;

    /**回复该评论的评论列表*/
    private List<CommentVo> replyList;

    @Override
    public String toString() {
        return "CommentVo{" +
                "simpleUser=" + simpleUser +
                ", replyUserName='" + replyUserName + '\'' +
                ", replyList=" + replyList +
                '}';
    }

    public CommentVo() {
    }

    public CommentVo(SimpleUser simpleUser, String replyUserName, List<CommentVo> replyList) {
        this.simpleUser = simpleUser;
        this.replyUserName = replyUserName;
        this.replyList = replyList;
    }

    public SimpleUser getSimpleUser() {
        return simpleUser;
    }

    public void setSimpleUser(SimpleUser simpleUser) {
        this.simpleUser = simpleUser;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public List<CommentVo> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<CommentVo> replyList) {
        this.replyList = replyList;
    }
}
