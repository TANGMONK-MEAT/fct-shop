package io.github.tangmonkmeat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/18 下午2:53
 */
@Document(indexName = "pms",shards = 1,replicas = 0)
public class EsProduct implements Serializable {

    @Id
    private Integer id;

    private Integer categoryId;

    @Field(type = FieldType.Nested)
    private CategoryMenu categoryMenus;

    private String sellerId;

    private String buyerId;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String name;

    private BigDecimal price;

    private BigDecimal marketPrice;

    private BigDecimal postage;

    private String primaryPicUrl;

    private Integer regionId;

    @Field(type = FieldType.Keyword)
    private String region;

    private Integer wantCount;

    private Integer browseCount;

    private Boolean isSelling;

    private Boolean isDelete;

    private Boolean ableExpress;

    private Boolean ableMeet;

    private Boolean ableSelfTake;

    private Date postTime;

    private Date lastEdit;

    private Date soldTime;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String desc;

    @Override
    public String toString() {
        return "EsProduct{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryMenus + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", marketPrice=" + marketPrice +
                ", postage=" + postage +
                ", primaryPicUrl='" + primaryPicUrl + '\'' +
                ", regionId=" + regionId +
                ", region='" + region + '\'' +
                ", wantCount=" + wantCount +
                ", browseCount=" + browseCount +
                ", isSelling=" + isSelling +
                ", isDelete=" + isDelete +
                ", ableExpress=" + ableExpress +
                ", ableMeet=" + ableMeet +
                ", ableSelfTake=" + ableSelfTake +
                ", postTime=" + postTime +
                ", lastEdit=" + lastEdit +
                ", soldTime=" + soldTime +
                ", desc='" + desc + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryMenu getCategoryMenus() {
        return categoryMenus;
    }

    public void setCategoryMenus(CategoryMenu categoryMenus) {
        this.categoryMenus = categoryMenus;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public String getPrimaryPicUrl() {
        return primaryPicUrl;
    }

    public void setPrimaryPicUrl(String primaryPicUrl) {
        this.primaryPicUrl = primaryPicUrl;
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
        this.region = region;
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

    public Boolean getSelling() {
        return isSelling;
    }

    public void setSelling(Boolean selling) {
        isSelling = selling;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
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
        this.desc = desc;
    }

}
