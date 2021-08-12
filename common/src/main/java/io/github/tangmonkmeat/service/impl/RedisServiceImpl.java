package io.github.tangmonkmeat.service.impl;

import io.github.tangmonkmeat.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 上午10:24
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void hset(String key, String hashKey, Object value){
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Long hmset(String key, Map<?, ?> values) {
        redisTemplate.opsForHash().putAll(key,  values);
        return 1L;
    }

    @Override
    public Object hget(String key,String hashKey) {
        return redisTemplate.opsForHash().get(key,hashKey);
    }

    @Override
    public String hgetStr(String key, String hashKey) {
        return (String) stringRedisTemplate.opsForHash().get(key,hashKey);
    }

    @Override
    public void hdel(String key, Object... field) {
        stringRedisTemplate.opsForHash().delete(key,field);
    }

    @Override
    public Object hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Map<Object, Object> hmgetStr(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    @Override
    public Boolean hasHashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key,hashKey);
    }

    @Override
    public Long zcount(String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().count(key,min,max);
    }

    @Override
    public Object multiGet(String key, List hashKeys){
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    @Override
    public void lTrim(String key, long start, long end) {
        stringRedisTemplate.opsForList().trim(key,start,end);
    }

    @Override
    public long incr(String key) {
        return stringRedisTemplate.opsForValue().increment(key,1);
    }

    @Override
    public Double zincrby(String key, long inc, String member) {
        return stringRedisTemplate.opsForZSet().incrementScore(key,member,inc);
    }

    @Override
    public void zremByRange(String key, long start, long end) {
        stringRedisTemplate.opsForZSet().removeRange(key,start,end);
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().range(key,start,end);
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().reverseRange(key,start,end);
    }

    @Override
    public <T> T eval(RedisScript<T> script, List<String> keys, Object... args) {
        return stringRedisTemplate.execute(script,keys,args);
    }

    @Override
    public void zadd(String key, double score, String member) {
        //stringRedisTemplate.opsForList().
        stringRedisTemplate.opsForZSet().add(key,member,score);
    }

    @Override
    public Long zsize(String key) {
        return stringRedisTemplate.opsForZSet().size(key);
    }

    @Override
    public Long lpush(String key, String value) {
        return stringRedisTemplate.opsForList().leftPush(key,value);
    }

    @Override
    public Object lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key,start,end);
    }

    @Override
    public <T> Long lmpush(String key, List<T> values) {
        return redisTemplate.opsForList().leftPushAll(key,values);
    }

    @Override
    public Long rmpush(String key, List<Object> values) {
        return redisTemplate.opsForList().rightPushAll(key,values);
    }

    @Override
    public Long llen(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    @Override
    public Long lrem(String key, long count, String value) {
        return stringRedisTemplate.opsForList().remove(key,count,value);
    }

    @Override
    public Long rpush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key,value);
    }

    @Override
    public boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void del(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setEx(String key, Object value, long ex) {
        redisTemplate.opsForValue().set(key,value,ex,TimeUnit.SECONDS);
    }

    @Override
    public Object multiGet(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public Boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key,delta);
    }
}
