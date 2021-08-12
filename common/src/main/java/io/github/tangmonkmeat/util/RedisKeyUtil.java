package io.github.tangmonkmeat.util;

import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import org.springframework.util.StringUtils;

/**
 * redis key 构建工具类, 以 系统-模块-方法-参数 这样的规则定义 key
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 下午3:38
 */
public class RedisKeyUtil {

    /**
     * 主数据系统标识
     */
    public static final String KEY_PREFIX = "fct_shop";
    /**
     * 分割字符，默认[:]，使用:可用于 fct_shop分组查看
     */
    private static final String KEY_SPLIT_CHAR = ":";

    /**
     * redis的key键规则定义
     *
     * @param module 模块名称
     * @param func 方法名称
     * @param args 参数..
     * @return key
     */
    public static String keyBuilder(String module, String func, String... args) {
        return keyBuilder(null, module, func, args);
    }

    /**
     * redis的key键规则定义
     *
     * @param module 模块名称
     * @param func 方法名称
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(String module, String func, String objStr) {
        return keyBuilder(null, module, func, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     * @param prefix 项目前缀
     * @param module 模块名称
     * @param func 方法名称
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String func, String objStr) {
        if (StringUtils.isEmpty(objStr)){
            return keyBuilder(prefix, module, func);
        }
        return keyBuilder(prefix, module, func, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     * @param prefix 项目前缀
     * @param module 模块名称
     * @param func 方法名称
     * @param args 参数..
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String func, String... args) {
        // 项目前缀
        if (prefix == null) {
            prefix = KEY_PREFIX;
        }
        StringBuilder key = new StringBuilder(prefix);
        // KEY_SPLIT_CHAR 为分割字符
        key.append(KEY_SPLIT_CHAR).append(module).append(KEY_SPLIT_CHAR).append(func);
        for (String arg : args) {
            key.append(KEY_SPLIT_CHAR).append(arg);
        }
        return key.toString();
    }

    /**
     * redis的key键规则定义
     * @param redisEnum 枚举对象
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(RedisKeyEnum redisEnum, String objStr) {
        return keyBuilder(redisEnum.getKeyPrefix(), redisEnum.getModule(), redisEnum.getFunc(), objStr);
    }
}
