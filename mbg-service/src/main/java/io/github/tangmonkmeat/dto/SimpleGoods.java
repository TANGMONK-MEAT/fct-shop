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

    private Integer id;
    private String name;
    private String primaryPicUrl;
    private Double price;

    public SimpleGoods(){}

    public static SimpleGoods unknownGoods(){
        SimpleGoods simpleGoods = new SimpleGoods();
        simpleGoods.setName(DEFAULT_GOODS_NAME);
        return simpleGoods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
