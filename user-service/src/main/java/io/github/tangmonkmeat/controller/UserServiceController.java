package io.github.tangmonkmeat.controller;

import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.service.UserService;
import io.github.tangmonkmeat.token.injection.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * UseServiceController
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/16 下午8:59
 */
@RestController
@RequestMapping("/user-service")
public class UserServiceController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UserService userService;

    /**
     * 查询用户基本信息
     *
     * @param openId 微信openId
     * @return {@link SimpleUser}
     */
    @GetMapping("/simpleUser/{openId}")
    //@Auth
    Response<SimpleUser> getSimpleUser(@PathVariable("openId") String openId) {
        SimpleUser dto = userService.getSimpleUser(openId);
        LOGGER.info("其他服务通过openId: [{}] 查询用户基本信息，查询结果：{}", openId, dto);
        if (dto == null) {
            return Response.fail(ResponseStatus.USER_IS_NOT_EXIST);
        }
        return Response.ok(dto);
    }

    /**
     * 查询用户基本信息列表
     *
     * @param openIdList openId-list
     * @return SimpleUser-list
     */
    @GetMapping("/simpleUserList")
    //@Auth
    Response<Map<String, SimpleUser>> getSimpleUserList(@RequestParam List<String> openIdList) {
        Map<String, SimpleUser> dtoMap = userService.getSimpleUserList(openIdList);
        LOGGER.info("其他服务通过openId : [{}] 查询用户基本信息，查询结果：{}", openIdList, dtoMap);
        if (ObjectUtils.isEmpty(dtoMap)) {
            return Response.fail(ResponseStatus.USER_IS_NOT_EXIST);
        }
        return Response.ok(dtoMap);
    }
}