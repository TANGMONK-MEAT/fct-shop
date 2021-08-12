package io.github.tangmonkmeat.service;

import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 操作service，key使用String，value统一使用json
 *
 * @author zwl
 * @date 2021/7/14 上午10:23
 * @version 1.0
 */
public interface RedisService {

    /**
     * 存储数据
     */
    void set(String key, Object value);

    void del(String key);

    void setEx(String key, Object value, long ex);

    boolean hasKey(String key);

    void hset(String key, String hashKey,Object value);

    Long hmset(String key, Map<?, ?> values);

    Object hget(String key, String hashKey);

    String hgetStr(String key,String hashKey);

    Boolean hasHashKey(String key,String hashKey);

    void hdel(String key,Object... field);

    Object hmget(String key);

    Map<Object, Object> hmgetStr(String key);

    Object multiGet(String key, List hashKeys);

    long incr(String key);

    void zadd(String key,double score,String member);

    Long zcount(String key,double min,double max);

    void zremByRange(String key,long start,long end);

    Double zincrby(String key,long inc,String member);

    Long zsize(String key);

    Set<String> zrange(String key, long start, long end);

    Set<String> zrevrange(String key,long start,long end);

    <T> T eval(RedisScript<T> script, List<String> keys, Object... args);

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     *
     * @param count
     * <p>
     * count 的值可以是以下几种：
     * <p>
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值。
     * @param value value
     * @return 被移除元素的数量。因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0 。
     */
    Long lrem(final String key, long count, String value);

    Long lpush(final String key, final String value);

    <T> Long lmpush(final String key,List<T> values);

    Long rmpush(final String key,List<Object> values);

    Long rpush(final String key, Object value);

    /**
     * LTRIM操作
     * <p>
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * <p>
     * 举个例子，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。
     * <p>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * <p>
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * <p>
     * 当 key 不是列表类型时，返回一个错误。
     */
    void lTrim(final String key, final long start, final long end);

    Object lrange(final String key, final long start, final long end);


    Long llen(String key);

    /**
     * 获取多条数据
     */
    Object multiGet(List<String> keys);

    /**
     * 获取数据
     */
    <T> T get(final String key);

    /**
     * 设置超期时间
     */
    Boolean expire(final String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     * @param delta 自增步长
     */
    Long increment(String key, long delta);
}
