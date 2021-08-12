package io.github.tangmonkmeat.service;


/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/21 下午4:31
 * @version 1.0
 */
public interface SearchService {

    /**
     * 更新搜索历史
     *
     * @param openId
     * @param keyword
     */
    void updateUserHistory(String openId, String keyword);
}
