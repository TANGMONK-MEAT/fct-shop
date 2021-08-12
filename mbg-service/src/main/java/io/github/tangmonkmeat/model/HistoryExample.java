package io.github.tangmonkmeat.model;

public class HistoryExample extends History{

    private String u1;
    private String u2;
    private Integer goodsId;

    @Override
    public String toString() {
        return "HistoryExample{" +
                "u1='" + u1 + '\'' +
                ", u2='" + u2 + '\'' +
                ", goodsId=" + goodsId +
                '}';
    }

    public String getU1() {
        return u1;
    }

    public void setU1(String u1) {
        this.u1 = u1;
    }

    public String getU2() {
        return u2;
    }

    public void setU2(String u2) {
        this.u2 = u2;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
}