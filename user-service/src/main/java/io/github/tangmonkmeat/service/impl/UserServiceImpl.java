package io.github.tangmonkmeat.service.impl;

import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.mapper.UserMapper;
import io.github.tangmonkmeat.model.User;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.service.UserService;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/16 下午8:57
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final String SIMPLE_USER_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.SIMPLE_USER_KEY,"field");

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public void register(User user) {
        user.setRegisterTime(new Date());
        user.setStatus(1);
        userMapper.insertSelective(user);
        LOGGER.info("用户注册信息保存成功，user：{}",user);
    }

    @Override
    public boolean isRegistered(String openId) {
        if (StringUtils.isEmpty(openId)){
            return false;
        }
        User user = userMapper.selectByOpenId(openId);
        return user != null;
    }

    @Override
    public SimpleUser getSimpleUser(String openId) {
        if (StringUtils.isEmpty(openId)){
            return null;
        }
        // 查缓存
        Object obj =  redisService.hget(SIMPLE_USER_KEY, openId);
        if (obj != null){
            return (SimpleUser) obj;
        }

        SimpleUser simpleUser;
        // 查DB
        User user = userMapper.selectByOpenId(openId);
        if (ObjectUtils.isEmpty(user)){
            return null;
        }
        simpleUser = new SimpleUser();
        BeanUtils.copyProperties(user,simpleUser);
        // 缓存
        redisService.hset(SIMPLE_USER_KEY,openId,simpleUser);
        return simpleUser;
    }

    @Override
    public Map<String, SimpleUser> getSimpleUserList(List<String> openIdList) {
        if (openIdList == null || openIdList.isEmpty()){
            return null;
        }
        // 查缓存
        // 即使没有查到数据，也会返回一个 ArrayList ,并且有一个 null元素
        Object obj =  redisService.multiGet(SIMPLE_USER_KEY, openIdList);

        if (obj != null){
            List<SimpleUser> simpleUserList = (List<SimpleUser>) obj;
            // 数据不对称？
            if (simpleUserList.get(0) != null && simpleUserList.size() == openIdList.size()) {
                return listToMap(simpleUserList);
            }
        }
        // 查DB
        List<SimpleUser> simpleUsers = userMapper.selectByOpenIdList(openIdList);
        // 缓存 openId ---> SimpleUser
        int cap = (int) (simpleUsers.size() / 0.75 + 1);
        Map<String, SimpleUser> simpleUserMap = new HashMap<>(cap);
        for(SimpleUser user : simpleUsers){
            simpleUserMap.put(user.getOpenId(),user);
        }
        redisService.hmset(SIMPLE_USER_KEY,simpleUserMap);
        return simpleUserMap;
    }

    private Map<String,SimpleUser> listToMap(List<SimpleUser> simpleUsers){
        int size = simpleUsers.size();
        int cap = (int)(size / 0.75) + 1;
        Map<String,SimpleUser> map = new HashMap<>(cap);
        for(SimpleUser user : simpleUsers){
            map.put(user.getOpenId(),user);
        }
        return map;
    }
}
