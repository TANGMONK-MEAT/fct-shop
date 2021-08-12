package io.github.tangmonkmeat.token.injection;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.constant.TokenConstant;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.exception.JwtException;
import io.github.tangmonkmeat.token.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token 校验拦截器
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 下午8:41
 */
public class AuthInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);

    @Resource
    private TokenManager tokenManager;

    /**
     * 完成token 的签名、过期校验，token的续约
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod parameter = (HandlerMethod)handler;

        // 不存在 Auth注解，或者 Auth的required==false
        Auth auth = parameter.getMethodAnnotation(Auth.class);
        if (auth == null || !auth.required()){
            return true;
        }

        String reqToken = request.getHeader(TokenConstant.AUTHORIZATION);
        if (StringUtils.isEmpty(reqToken)){
            LOGGER.info("用户的{}为空",TokenConstant.AUTHORIZATION);
            throw new JwtException(ResponseStatus.TOKEN_IS_EMPTY);
        }

        JwtUser user = tokenManager.getUserFromCache(reqToken);
        if (user == null){
            LOGGER.info("无效的token：{}",reqToken);
            throw new JwtException(ResponseStatus.TOKEN_IS_WRONG);
        }else {
            try {
                // 校验token
                DecodedJWT jwt = tokenManager.verify(reqToken);
                // 续约？？？
                tokenManager.renewToken(jwt,reqToken);
            }catch (TokenExpiredException e){
                LOGGER.info("jwt已过期，过期时间: {}",e.getMessage());
                if (parameter.getMethodAnnotation(Auth.class).checkExpired()){
                    throw new JwtException(ResponseStatus.TOKEN_IS_EXPIRED);
                }
            }catch (Exception e){
                LOGGER.info("jwt解析失败, 无效的token：{}",reqToken,e);
                throw new JwtException(ResponseStatus.TOKEN_IS_WRONG);
            }
        }
        return true;
    }
}