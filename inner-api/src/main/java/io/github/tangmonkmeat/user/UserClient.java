package io.github.tangmonkmeat.user;


import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.dto.SimpleUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Description: 服务内部调用的API
 *
 * @author zwl
 * @date 2021/7/23 下午2:14
 * @version 1.0
 */
@FeignClient(name = "user-service")
@RequestMapping(value = "/user-service")
public interface UserClient {

    /**
     * 查询用户基本信息
     *
     * @param openId 微信openId
     * @return {@link SimpleUser}
     */
    @GetMapping("/simpleUser/{openId}")
    Response<SimpleUser> getSimpleUser(@PathVariable("openId") String openId);

    /**
     * 查询用户基本信息列表
     *
     * @param openIdList openId-list
     * @return SimpleUser-list
     */
    @GetMapping("/simpleUserList")
    Response<Map<String, SimpleUser>> getSimpleUserList(@RequestParam List<String > openIdList);
}
