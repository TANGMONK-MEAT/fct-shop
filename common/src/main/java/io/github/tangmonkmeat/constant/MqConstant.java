package io.github.tangmonkmeat.constant;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 上午11:12
 */
public final class MqConstant {

    /*
     * 微信登录：通知【user-service】保存用户信息到 DB
     */

    public static final String USER_REGISTER_SAVE_DIRECT = "fct.user.register.save.direct";
    public static final String USER_REGISTER_SAVE_QUEUE = "fct.user.register.save.queue";
    public static final String USER_REGISTER_SAVE_KEY = "fct.user.register.save.queue";

    public static final String USER_REGISTER_RETRY_OUT_DIRECT = "fct.user.register.retry-out.direct.dlx";
    public static final String USER_REGISTER_RETRY_OUT_QUEUE = "fct.user.register.retry-out.queue.dlx";
    public static final String USER_REGISTER_RETRY_OUT_KEY = "fct.user.register.retry-out.queue.dlx";

    /*
     * 商品消息es保存：通知【goods-search-service】
     */

    public static final String ES_GOODS_SAVE_DIRECT = "fct.goods.save.direct";
    public static final String ES_GOODS_SAVE_QUEUE = "fct.goods.save.queue";
    public static final String ES_GOODS_SAVE_KEY = "fct.goods.save.queue";

    public static final String ES_GOODS_SAVE_DIRECT_DLX = "fct.goods.save.direct.dlx";
    public static final String ES_GOODS_SAVE_QUEUE_DLX = "fct.goods.save.queue.dlx";
    public static final String ES_GOODS_SAVE_KEY_DLX = "fct.goods.save.queue.dlx";

    /*
     * 商品消息es删除：通知【goods-search-service】
     */

    public static final String ES_GOODS_DEL_DIRECT = "fct.goods.del.direct";
    public static final String ES_GOODS_DEL_QUEUE = "fct.goods.del.queue";
    public static final String ES_GOODS_DEL_KEY = "fct.goods.del.queue";

    public static final String ES_GOODS_DEL_DIRECT_DLX = "fct.goods.del.direct.dlx";
    public static final String ES_GOODS_DEL_QUEUE_DLX = "fct.goods.del.queue.dlx";
    public static final String ES_GOODS_DEL_KEY_DLX = "fct.goods.del.queue.dlx";
}
