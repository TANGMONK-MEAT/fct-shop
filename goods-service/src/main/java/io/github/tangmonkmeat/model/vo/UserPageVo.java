package io.github.tangmonkmeat.model.vo;


import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.model.Goods;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/24 上午11:03
 * @version 1.0
 */
public class UserPageVo implements Serializable {

    /**用户基本信息*/
    private SimpleUser user;

    /**用户历史*/
    private LinkedHashMap<String, List<Goods>> userHistory;

    /**出售过几件商品*/
    private Integer soldCount;

    public UserPageVo() {
    }

    public UserPageVo(SimpleUser user, LinkedHashMap<String, List<Goods>> userHistory, Integer soldCount) {
        this.user = user;
        this.userHistory = userHistory;
        this.soldCount = soldCount;
    }

    @Override
    public String toString() {
        return "UserPageVo{" +
                "user=" + user +
                ", userHistory=" + userHistory +
                ", soldCount=" + soldCount +
                '}';
    }

    public SimpleUser getUser() {
        return user;
    }

    public void setUser(SimpleUser user) {
        this.user = user;
    }

    public LinkedHashMap<String, List<Goods>> getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(LinkedHashMap<String, List<Goods>> userHistory) {
        this.userHistory = userHistory;
    }

    public Integer getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Integer soldCount) {
        this.soldCount = soldCount;
    }
}
