package io.github.tangmonkmeat.user;

import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.dto.SimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/23 下午2:21
 */
@Component
public class UserClientHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(UserClientHandler.class);

    @Autowired
    private UserClient userClient;

    public SimpleUser getSimpleUser(String openId){
        LOGGER.info("从【user-service】查询用户的简单信息");
        Response<SimpleUser> response = userClient.getSimpleUser(openId);
        if (response.getErrno() != 0) {
            LOGGER.info("从【user-service】获取用户信息列表失败,errno={},原因={}", response.getErrno(), response.getErrmsg());
            return SimpleUser.unknownUser();
        }
        return response.getData();
    }

    public Map<String,SimpleUser> getSimpleUserList(List<String> openIdList){
        LOGGER.info("从用户服务查询用户的简单信息");
        if (openIdList == null || openIdList.size() < 1) {
            LOGGER.info("用户idList为空,返回空的结果");
            return new HashMap<>(0);
        }
        Response<Map<String, SimpleUser>> response = userClient.getSimpleUserList(openIdList);
        if (response.getErrno() != 0) {
            LOGGER.info("从用户服务获取用户信息列表失败,errno={},原因={}", response.getErrno(), response.getErrmsg());
            return new HashMap<>(0);
        }
        return response.getData();
    }

}
