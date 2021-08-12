package io.github.tangmonkmeat.service.impl;

import com.github.pagehelper.PageHelper;
import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.mapper.GoodsMapper;
import io.github.tangmonkmeat.mapper.UserMapper;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.GoodsExample;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.service.UserService;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/23 下午8:23
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Resource
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisScript<Long> cacheCollectAndUserScript;

    private final String VIEW_PRODUCT_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.VIEW_PRODUCT_KEY, "goodsInfoMap");

    private final String USER_COLLECT_GOODS_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.USER_COLLECT_GOODS_KEY,"openId");

    private final String COLLECT_GOODS_USER_SET_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.COLLECT_GOODS_USER_SET_KEY,"userSet");

    @Override
    public void cacheCollectAddOrDelete(long goodsId, String openId, boolean hasCollect) {
        final String key = USER_COLLECT_GOODS_KEY + ":" + openId;
        if (hasCollect){
            redisService.hset(key,goodsId + "","0");
        }else {
            List<String> keys = new ArrayList<>();
            keys.add(COLLECT_GOODS_USER_SET_KEY);
            keys.add(key);
            redisService.eval(cacheCollectAndUserScript,keys,goodsId+"","1","1",openId);
        }
    }

    @Override
    public void collectAddOrDelete(long goodsId, String openId, boolean hasCollect) {
        if (hasCollect) {
            userMapper.deleteUserCollect(openId, goodsId);
        } else {
            userMapper.setUserCollect(openId, goodsId);
        }
    }

    @Override
    public void collectAdd(String openId, List<Long> goodsIdList) {
        goodsIdList.forEach(goodsId ->{
            collectAddOrDelete(goodsId,openId,false);
        });
    }

    @Override
    public void collectDelete(String openId, List<Long> goodsIdList) {
        goodsIdList.forEach(goodsId ->{
            collectAddOrDelete(goodsId,openId,true);
        });
    }

    @Override
    public boolean userHasCollect(String openId,long goodsId) {
        final String key = USER_COLLECT_GOODS_KEY + ":" + openId;
        String type = redisService.hgetStr(key, goodsId + "");
        if ("1".equals(type)){
            return true;
        }
        else if ("0".equals(type)){
            return false;
        } else {
            return userMapper.userHasCollect(openId,goodsId);
        }
    }

    @Override
    public List<Goods> getUserBought(String openId, int page, int size) {
        PageHelper.startPage(page, size);
        return userMapper.getUserBought(openId);
    }

    @Override
    public List<Goods> getUserSold(String openId, int page, int size) {
        PageHelper.startPage(page, size);
        return userMapper.getUserSold(openId);
    }

    @Override
    public void addWant(int goodsId, String sellerId) {
        if (!userMapper.userHasWant(sellerId, goodsId)) {
            userMapper.setUserWant(sellerId, goodsId);
        }
    }

    @Override
    public LinkedHashMap<String, List<Goods>> getUserHistoryList(String userId, int page, int size) {
        PageHelper.startPage(page, size);
        // 发布和卖出的商品信息
        List<GoodsExample> userHistoryList = userMapper.getUserHistoryList(userId);
        if (userHistoryList.size() < 1) {
            return null;
        }

        // 把数据分割成 K=日期,V=商品List 的map
        LinkedHashMap<String, List<Goods>> result = new LinkedHashMap<>();

        LocalDate lastDay = getDay(userHistoryList.get(0).getTime());
        List<Goods> lastValue = new ArrayList<>();

        for (GoodsExample goods : userHistoryList) {
            if (!lastDay.equals(getDay(goods.getTime()))) {
                // 不是同一天操作的商品.把之前的加入map,设置为新的key
                String key = dateFormat(lastDay);
                if (result.containsKey(key)){
                    result.get(key).addAll(lastValue);
                }else {
                    result.put(key, lastValue);
                }
                lastDay = getDay(goods.getTime());
                lastValue = new ArrayList<>();
            }
            lastValue.add(goods);
        }
        String key = dateFormat(lastDay);
        if (result.containsKey(key)) {
            result.get(key).addAll(lastValue);
        }else{
            result.put(key,lastValue);
        }
        return result;
    }

    private LocalDate getDay(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String dateFormat(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = localDate.format(formatter);
        return formattedString;
    }

    @Override
    public List<Goods> getUserPosted(String openId, int page, int size) {
        PageHelper.startPage(page, size);
        return userMapper.getUserPosted(openId);
    }

    @Override
    public List<Goods> getUserCollectList(String openId, int page, int size) {
        PageHelper.startPage(page, size);
        List<Goods> userCollect0 = userMapper.getUserCollect(openId);

        final String key = USER_COLLECT_GOODS_KEY + ":" + openId;
        Map<Object,Object> map = redisService.hmgetStr(key);
        List<Long> goodsIds = new ArrayList<>();
        for(Map.Entry<Object,Object> entry : map.entrySet()){
            if ("1".equals(entry.getValue())){
                goodsIds.add(Long.valueOf(entry.getKey().toString()));
            }
        }

        if (goodsIds.size() > 0) {
            PageHelper.startPage(page, size);
            List<Goods> userCollect1 = goodsMapper.selectSimpleGoodsList(goodsIds);
            userCollect0.addAll(userCollect1);
        }

        return userCollect0;
    }
}