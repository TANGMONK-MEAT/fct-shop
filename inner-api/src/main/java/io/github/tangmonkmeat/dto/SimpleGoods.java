package io.github.tangmonkmeat.dto;

import java.io.Serializable;

/**
 * 简单的商品信息
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/13 下午8:43
 */
public class SimpleGoods implements Serializable {
    private static final String DEFAULT_GOODS_NAME = "unknown";

    private Long id;
    private String name;
    private String primaryPicUrl;
    private Double price;

    @Override
    public String toString() {
        return "SimpleGoods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", primaryPicUrl='" + primaryPicUrl + '\'' +
                ", price=" + price +
                '}';
    }

    public SimpleGoods(){}

    public static SimpleGoods unknownGoods(){
        SimpleGoods simpleGoods = new SimpleGoods();
        simpleGoods.setName(DEFAULT_GOODS_NAME);
        return simpleGoods;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryPicUrl() {
        return primaryPicUrl;
    }

    public void setPrimaryPicUrl(String primaryPicUrl) {
        this.primaryPicUrl = primaryPicUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
