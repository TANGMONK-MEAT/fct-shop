package io.github.tangmonkmeat.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.tangmonkmeat.Enum.MqMsgType;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.config.WxMaConfiguration;
import io.github.tangmonkmeat.dto.*;
import io.github.tangmonkmeat.mq.SaveUserDataSender;
import io.github.tangmonkmeat.service.AuthService;
import io.github.tangmonkmeat.token.TokenManager;
import io.github.tangmonkmeat.util.JsonUtil;
import io.github.tangmonkmeat.vo.AuthVO;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 上午11:31
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private SaveUserDataSender saveUserDataSender;

    @Autowired
    private TokenManager tokenManager;

    @Override
    public String setOpenId4Data(String rawData, String openId) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode node = null;
        try {
            node = (ObjectNode)mapper.readTree(rawData);
        } catch (JsonProcessingException e) {
        }
        assert node != null;
        node.remove("openId");
        node.put("openId",openId);
        return node.toString();
    }

    @Override
    public Response<AuthVO> wxLogin(AuthDTO authDTO) {
        // 获取 openId、sessionKey、unionId
        WxMaUserService wxMaUserService = WxMaConfiguration.getWxMaService().getUserService();
        WxMaJscode2SessionResult sessionInfo = null;

        // 验证jscode
        String jscode = authDTO.getCode();
        try {
            sessionInfo = wxMaUserService.getSessionInfo(jscode);
        } catch (WxErrorException e) {
            LOGGER.info("jscode无效，无法登录, jscode: {}", jscode);
            return Response.fail(ResponseStatus.WRONG_JS_CODE);
        }

        // 校验用户信息
        String sessionKey = sessionInfo.getSessionKey();
        DetailAuthDTO detail = authDTO.getDetail();
        if(!(wxMaUserService.checkUserInfo(sessionKey,detail.getRawData(),detail.getSignature()))){
            LOGGER.info("用户信息错误，userInfo: {}, sessionKey: {}", detail,sessionKey);
            return Response.fail(ResponseStatus.CHECK_USER_WITH_SESSION_FAIL);
        }

        String userData = setOpenId4Data(detail.getRawData(), sessionInfo.getOpenid());

        String openid = sessionInfo.getOpenid();
        AuthVO vo = tokenManager.getAuthVoFromCache(openid);
        if (ObjectUtils.isEmpty(vo)){
            MqMessage<String> message = new MqMessage<>(MqMsgType.USER_REGISTER.getType(), userData, "");
            // 异步通知【user-service】保存用户信息
            saveUserDataSender.sendMessage(JsonUtil.toJson(message));
            // 生成 token
            vo = createToken(userData);
            // 缓存用户信息
            cacheUserInfo(vo,openid);
        }
        LOGGER.info("用户登录成功，userInfo：{}", userData);
        return Response.ok(vo);
    }

    @Override
    public void cacheUserInfo(AuthVO vo,String openId) {
        tokenManager.cacheTokenAndUser(vo.getToken(),vo.getUserInfo());
        tokenManager.cacheOpenIdAndUserInfo(openId,vo);
    }

    @Override
    public AuthVO createToken(String userData) {
        Map<String, Object> map = JsonUtil.fromJson(userData, JsonUtil.simpleJsonMap);
        JwtUser user = new JwtUser();

        for(Field f : user.getClass().getDeclaredFields()){
            f.setAccessible(true);
            Object value = map.get(f.getName());
            try {
                f.set(user,value);
            } catch (IllegalAccessException e) {
            }
        }

        String token = tokenManager.generateToken(user);
        return new AuthVO(token,user);
    }
}
