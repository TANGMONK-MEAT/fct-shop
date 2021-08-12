package io.github.tangmonkmeat.constant;

/**
 * Description: 商品相关信息常量
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 上午8:47
 */
public final class GoodsConstant {

    /**
     * 商品详情 过期时间 15分钟
     *
     */
    public static final int VIEW_PRODUCT_KEY_EXPIRE = 3 * 24 * 60 * 60;

    /**
     * 首页，推荐商品的过期时间，15分钟
     *
     */
    public static final int INDEX_GOODS_LIST_EXPIRE = 15 * 60;

    /**
     * 分类商品的分页信息过期时间，15分钟
     *
     */
    public static final int CATEGORY_GOODS_PAGE_EXPIRE = 15 * 60;

}
