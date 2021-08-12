package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.dto.AuthDTO;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.vo.AuthVO;

/**
 * 授权业务
 *
 * @author zwl
 * @date 2021/7/15 上午11:35
 * @version 1.0
 */
public interface AuthService {

    String setOpenId4Data(String rawData,String openId);

    Response<AuthVO> wxLogin(AuthDTO authDTO);

    void cacheUserInfo(AuthVO vo,String openId);

    AuthVO createToken(String userData);

}
