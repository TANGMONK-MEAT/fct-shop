package io.github.tangmonkmeat.model.vo;

import io.github.tangmonkmeat.model.Ad;
import io.github.tangmonkmeat.model.Channel;
import io.github.tangmonkmeat.model.Goods;

import java.io.Serializable;
import java.util.List;

/**
 * 首页数据
 *
 * @author nnkwrik
 * @date 18/11/14 20:57
 */

public class IndexPageVo implements Serializable {

    /**首页推荐商品*/
    private List<Goods> indexGoodsList;

    /**广告banner*/
    private List<Ad> banner;

    /**首页展示分类*/
    private List<Channel> channel;

    public IndexPageVo() {
    }

    public IndexPageVo(List<Goods> indexGoodsList, List<Ad> banner, List<Channel> channel) {
        this.indexGoodsList = indexGoodsList;
        this.banner = banner;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "IndexPageVo{" +
                "indexGoodsList=" + indexGoodsList +
                ", banner=" + banner +
                ", channel=" + channel +
                '}';
    }

    public List<Goods> getIndexGoodsList() {
        return indexGoodsList;
    }

    public void setIndexGoodsList(List<Goods> indexGoodsList) {
        this.indexGoodsList = indexGoodsList;
    }

    public List<Ad> getBanner() {
        return banner;
    }

    public void setBanner(List<Ad> banner) {
        this.banner = banner;
    }

    public List<Channel> getChannel() {
        return channel;
    }

    public void setChannel(List<Channel> channel) {
        this.channel = channel;
    }
}