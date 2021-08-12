package io.github.tangmonkmeat.model.vo;


import java.io.Serializable;
import java.util.List;

/**
 * Description: 搜索主页
 *
 * @author zwl
 * @date 2021/7/21 下午3:57
 * @version 1.0
 */
public class SearchPageVo implements Serializable {

    /**搜索历史*/
    private List<String> historyKeywordList;

    /**热门关键字*/
    private List<String> hotKeywordList;

    public SearchPageVo() {
    }

    public SearchPageVo(List<String> historyKeywordList, List<String> hotKeywordList) {
        this.historyKeywordList = historyKeywordList;
        this.hotKeywordList = hotKeywordList;
    }

    @Override
    public String toString() {
        return "SearchPageVo{" +
                "historyKeywordList=" + historyKeywordList +
                ", hotKeywordList=" + hotKeywordList +
                '}';
    }

    public List<String> getHistoryKeywordList() {
        return historyKeywordList;
    }

    public void setHistoryKeywordList(List<String> historyKeywordList) {
        this.historyKeywordList = historyKeywordList;
    }

    public List<String> getHotKeywordList() {
        return hotKeywordList;
    }

    public void setHotKeywordList(List<String> hotKeywordList) {
        this.hotKeywordList = hotKeywordList;
    }
}
