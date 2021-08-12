package io.github.tangmonkmeat.Enum;

/**
 * redis key枚举类
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 下午3:42
 */
public enum  RedisKeyEnum {

    // ==============用户注册相关=======================

    // token 缓存
    // token --> AuthVo(包含有token)，30分钟
    // openId --> AuthVo(包含有token)，30分钟
    TOKEN_USER_INFO_KEY("fct-shop","auth-service","cacheUserInfo","token"),
    OPENID_USER_INFO_KEY("fct-shop","auth-service","cacheUserInfo","openId"),

    // 注册用户重试次数的key
    USER_REGISTER_RETRY_COUNT_KEY("fct-shop","user-service","register","user register retry count"),

    // ==============商品信息相关=======================

    // 商品浏览量 在缓存中key, 使用 zset
    // key(goodsId) score(时间戳) member（ip）
    GOODS_BROWSE_NUM_KEY("fct-shop","goods-service","cacheGoodsBrowseNum","cache goods browse number"),

    // 商品一个月段内的浏览量的key，使用 hash
    // 每天 23：50 分
    // key(商品id) field(last_1,last_2...,last30) value(浏览量)
    GOODS_BROWSE_NUM_OF_MONTH_KEY("fct-shop","goods-service","cacheGoodsBrowseNumOfMonth","cache goods browse num of month"),

    // 搜索关键字，数据结构 zset, 用于 热门搜索和每个用户的搜索历史
    // key() score(搜索次数) member(keyword)
    GOODS_SEARCH_KEYWORD_KEY("fct-shop","goods-service","cacheGoodsSearchKeyword","cache goods search keyword"),

    // 当天有搜索历史的用户列表, 数据结构 zset, 配合GOODS_SEARCH_KEYWORD_KEY 使用，用于定时任务
    // key() score(搜索次数) member(用户openId)
    GOODS_SEARCH_USER_LIST_KEY("fct-shop","goods-service","cacheGoodsSearchKeyword","cache user search history"),

    // 商品详情，数据结构 hash， 过期时间 3天
    // key() field(商品id) value(商品信息json)
    VIEW_PRODUCT_KEY("fct-shop","goods-service","cacheGoodsInfo","cache goods info"),

    // ==============地址信息===========================

    // 相同级别的地域信息索引，数据结构 hash
    // key() field(地域id) value(地域信息json)
    LEVEL_REGION_KEY("fct-shop","goods-service","cacheRegionMap","cache region map"),

    // ==============商品分类相关========================

    // 最高级别0的分类信息索引，数据结构 hash
    // key(0) field(id) value(分类信息)
    MAIN_CATEGORY_KEY("fct-shop","goods-service","cacheMainCategoryMap","cache main category map"),

    // 主分类下的子分类，数据结构 hash
    // key(parentId) field(id) member(子分类信息)
    SUB_CATEGORY_KEY("fct-shop","goods-service","cacheSubCategoryMap","cache sub category map"),

    // =============首页的推荐，轮播图，总分类=============

    // goods 列表信息，数据结构 list, 过期时间 15分钟，用于首页
    INDEX_GOODS_LIST_KEY("fct-shop","goods-service","cacheGoodsInfoList","cache goods list"),

    // 首页 AD列表，数据结构 list
    INDEX_AD_LIST_KEY("fct-shop","goods-service","cacheIndexAdList","cache index ad list"),

    // 首页 banner列表，数据结构 list
    INDEX_BANNER_LIST_KEY("fct-shop","goods-service","cacheIndexBannerList","cache index banner list"),

    // 分类下的商品分页信息，数据结构 hash， 过期时间 15分钟，用于分类
    // key(categoryId) field(page_size) value(List<Goods>)
    CATEGORY_GOODS_PAGE_KEY("fct-shop","goods-service","cacheCategoryGoodsPage","cache category goods page"),

    // =================用户收藏相关=====================

    // 用户收藏的商品，数据结构 hash
    // key(用户id) field(goodsId) value(type=1（收藏）；type=0（取消）)
    USER_COLLECT_GOODS_KEY("fct-shop","goods-service","cacheUserCollectGoods","cache user collect goods"),

    // 收藏商品的用户，数据结构zset, 和USER_COLLECT_GOODS_KEY对应，用于定时任务
    // key() score(收藏的个数) member(openId)
    COLLECT_GOODS_USER_SET_KEY("fct-shop","goods-service","cacheUserOfCollectGoods","cache user of collect goods"),


    // =====================im 相关=====================

    // 缓存的未读消息，数据结构 List
    UNREAD_WS_MESSAGE_LIST_KEY("fct-shop","im-service","cacheUnreadWsMessageList","cache unread ws message list"),

    // im-service 需要的 SimpleUser-list的key，数据结构 hash
    // key() field(openId) value(SimpleUser)
    SIMPLE_USER_KEY("fct-shop","user-service","getSimpleUser","simple user");

    /**
     * 系统标识
     */
    private String keyPrefix;
    /**
     * 模块名称
     */
    private String module;
    /**
     * 方法名称
     */
    private String func;
    /**
     * 描述
     */
    private String remark;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    RedisKeyEnum(String keyPrefix, String module, String func, String remark) {
        this.keyPrefix = keyPrefix;
        this.module = module;
        this.func = func;
        this.remark = remark;
    }
}
