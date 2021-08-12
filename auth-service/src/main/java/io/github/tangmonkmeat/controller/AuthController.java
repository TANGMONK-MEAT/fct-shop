package io.github.tangmonkmeat.controller;

import io.github.tangmonkmeat.dto.AuthDTO;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.service.AuthService;
import io.github.tangmonkmeat.vo.AuthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 上午11:25
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 微信后台登录
     *
     * @param authDTO code : jsCode(通过wx.login()获取)
     *                detail : userInfo(通过open-type="getUserInfo"或wx.wx.getUserInfo()获取)
     * @return JWT Token和自定义的userInfo
     */
    @PostMapping("/loginByWeixin")
    public Response<AuthVO> wxLogin(@RequestBody AuthDTO authDTO){
        return authService.wxLogin(authDTO);
    }
}
