package io.github.tangmonkmeat.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Goods implements Serializable {
    private Long id;

    private Integer categoryId;

    private String sellerId;

    private String buyerId;

    private String name;

    private Double price;

    private Double marketPrice;

    private Double postage;

    private String primaryPicUrl;

    private Integer regionId;

    private String region;

    private Integer wantCount;

    private Integer browseCount;

    private Boolean isSelling;

    private Boolean isDelete;

    private Boolean ableExpress;

    private Boolean ableMeet;

    private Boolean ableSelfTake;

    private Date postTime;

    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date lastEdit;

    private Date soldTime;

    private String desc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public String getPrimaryPicUrl() {
        return primaryPicUrl;
    }

    public void setPrimaryPicUrl(String primaryPicUrl) {
        this.primaryPicUrl = primaryPicUrl == null ? null : primaryPicUrl.trim();
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public Integer getWantCount() {
        return wantCount;
    }

    public void setWantCount(Integer wantCount) {
        this.wantCount = wantCount;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public Boolean getIsSelling() {
        return isSelling;
    }

    public void setIsSelling(Boolean isSelling) {
        this.isSelling = isSelling;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getAbleExpress() {
        return ableExpress;
    }

    public void setAbleExpress(Boolean ableExpress) {
        this.ableExpress = ableExpress;
    }

    public Boolean getAbleMeet() {
        return ableMeet;
    }

    public void setAbleMeet(Boolean ableMeet) {
        this.ableMeet = ableMeet;
    }

    public Boolean getAbleSelfTake() {
        return ableSelfTake;
    }

    public void setAbleSelfTake(Boolean ableSelfTake) {
        this.ableSelfTake = ableSelfTake;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Date lastEdit) {
        this.lastEdit = lastEdit;
    }

    public Date getSoldTime() {
        return soldTime;
    }

    public void setSoldTime(Date soldTime) {
        this.soldTime = soldTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", sellerId=").append(sellerId);
        sb.append(", buyerId=").append(buyerId);
        sb.append(", name=").append(name);
        sb.append(", price=").append(price);
        sb.append(", marketPrice=").append(marketPrice);
        sb.append(", postage=").append(postage);
        sb.append(", primaryPicUrl=").append(primaryPicUrl);
        sb.append(", regionId=").append(regionId);
        sb.append(", region=").append(region);
        sb.append(", wantCount=").append(wantCount);
        sb.append(", browseCount=").append(browseCount);
        sb.append(", isSelling=").append(isSelling);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", ableExpress=").append(ableExpress);
        sb.append(", ableMeet=").append(ableMeet);
        sb.append(", ableSelfTake=").append(ableSelfTake);
        sb.append(", postTime=").append(postTime);
        sb.append(", lastEdit=").append(lastEdit);
        sb.append(", soldTime=").append(soldTime);
        sb.append(", desc=").append(desc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}