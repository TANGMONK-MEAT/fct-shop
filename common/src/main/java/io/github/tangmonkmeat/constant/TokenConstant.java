package io.github.tangmonkmeat.constant;

/**
 * Description: redis key的命名规则 系统:模块:方法:参数
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 上午8:22
 */
public class TokenConstant {

    // http========================================================

    public static final String AUTHORIZATION = "Authorization";

    // redis=======================================================

    /**
     * 需要对token renew的最小时间，最后5分钟内 60 * 5
     */
    public static final long NEED_RENEW_TOKEN_SECOND = 5 * 60;
}
