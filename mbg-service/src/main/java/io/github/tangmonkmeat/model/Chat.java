package io.github.tangmonkmeat.model;

import java.io.Serializable;

public class Chat implements Serializable {
    private Integer id;

    private String u1;

    private String u2;

    private Integer goodsId;

    private Boolean showToU1;

    private Boolean showToU2;

    private Boolean isDeleted;

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getU1() {
        return u1;
    }

    public void setU1(String u1) {
        this.u1 = u1 == null ? null : u1.trim();
    }

    public String getU2() {
        return u2;
    }

    public void setU2(String u2) {
        this.u2 = u2 == null ? null : u2.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Boolean getShowToU1() {
        return showToU1;
    }

    public void setShowToU1(Boolean showToU1) {
        this.showToU1 = showToU1;
    }

    public Boolean getShowToU2() {
        return showToU2;
    }

    public void setShowToU2(Boolean showToU2) {
        this.showToU2 = showToU2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", u1=").append(u1);
        sb.append(", u2=").append(u2);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", showToU1=").append(showToU1);
        sb.append(", showToU2=").append(showToU2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}