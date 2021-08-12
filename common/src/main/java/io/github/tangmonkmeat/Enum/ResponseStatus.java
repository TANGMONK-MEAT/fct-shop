package io.github.tangmonkmeat.Enum;

/**
 * 响应状态码及其对应的描述信息
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/13 下午7:53
 */
public enum ResponseStatus {

    // user 模块异常
    USER_IS_NOT_EXIST(2001,"用户不存在"),
    USER_REGISTER_WRONG(2002,"用户注册送失败"),
    USER_NOT_ABOUT_GOODS(2003,"无法搜索到商品卖家的信息"),

    // auth
    WRONG_JS_CODE(3001,"jscode 无效"),
    CHECK_USER_WITH_SESSION_FAIL(3002,"userInfo 和 sessionKey中的不一样"),
    TOKEN_IS_EMPTY(3003,"token 不存在"),
    TOKEN_IS_EXPIRED(3004,"token 已过期"),
    TOKEN_IS_WRONG(3005,"无效的token"),

    // goods 模块异常
    OPEN_ID_IS_EMPTY(4001,"openId 不存在"),
    COMMENT_INFO_INCOMPLETE(4002,"用户发表评论失败，信息不完整"),
    POST_INFO_INCOMPLETE(4003,"用户发布商品失败，信息不完整"),
    SELLER_AND_GOODS_IS_NOT_MATCH(4004,"删除商品失败.当前用户信息和卖家信息不匹配"),
    GOODS_IN_NOT_EXIST(4005,"不存在的商品"),
    SAVE_COLLECT_GOODS_WRONG(4006,"用户收藏数据入库失败"),

    // im 模块异常
    MESSAGE_FORMAT_IS_WRONG(5001,"消息序列化错误"),
    MESSAGE_IS_INCOMPLETE(5002,"消息不完整"),
    SENDER_AND_WS_IS_NOT_MATCH(5003,"发送者和ws连接中的不一致，消息发送失败"),
    UPDATE_HISTORY_TO_SQL_FAIL(5004,"更新消息记录到数据库失败"),

    // goods-search
    CATEGORY_ID_WRONG(6001,"分类参数错误"),
    SORT_WRONG(6002,"排序参数错误"),
    PAGINATION_WRONG(6003,"分页错误"),

    // 服务器未知异常
    INTERNAL_SERVER_ERROR(500,"internal server error"),
    ;

    /**
     * 响应状态码
     */
    int errno;

    /**
     * 描述信息
     */
    String errmsg;

    public int errno() {
        return errno;
    }

    public String errmsg() {
        return errmsg;
    }

    ResponseStatus(int errno, String errmsg){
        this.errno = errno;
        this.errmsg = errmsg;
    }
}
