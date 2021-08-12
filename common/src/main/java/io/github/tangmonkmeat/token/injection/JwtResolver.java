package io.github.tangmonkmeat.token.injection;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.tangmonkmeat.constant.TokenConstant;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.exception.JwtException;
import io.github.tangmonkmeat.token.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 解析 {@link Jwt} 注解，对 token 进行校验
 *
 * <br/>
 *     <table>
 *         <thead>
 *         <tr>
 *             <th align="left">时间</th>
 *             <th align="left">Token类型</th>
 *             <th align="left">说明</th>
 *         </tr>
 *         </thead>
 *         <tbody>
 *         <tr>
 *             <td align="left">0-10分钟</td>
 *             <td align="left">正常Token</td>
 *             <td align="left">正常访问</td>
 *         </tr>
 *         <tr>
 *             <td align="left">10-15分钟</td>
 *             <td align="left">濒死Token</td>
 *             <td align="left">正常访问，返回新Token，建议使用新Token</td>
 *         </tr>
 *         <tr>
 *             <td align="left">大于15分钟</td>
 *             <td align="left">过期Token</td>
 *             <td align="left">需校验<strong>是否正常过期</strong>。正常过期则能访问，并返回新Token；<br>非过期Token拒绝访问</td>
 *         </tr>
 *         </tbody>
 *     </table>
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 上午10:49
 */
public class JwtResolver implements HandlerMethodArgumentResolver {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtResolver.class);

    @Resource
    private TokenManager tokenManager;

    /**
     * 如果 controller 标注有 @Jwt 注解，并且 @Jwt(required = true),
     * 就将进行执行 {@link JwtResolver#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)}
     * 对 token 进行校验
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Jwt jwt = parameter.getParameterAnnotation(Jwt.class);
        return jwt != null && jwt.required();
    }

    /**
     * 从请求头获取 token，如果token不存在，抛出 JwtException
     *
     * 如果token不存在缓存，为无效token，抛出 JwtException
     *
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String reqToken = webRequest.getHeader(TokenConstant.AUTHORIZATION);
        if (StringUtils.isEmpty(reqToken)){
            LOGGER.info("用户的{}为空",TokenConstant.AUTHORIZATION);
            throw new JwtException(ResponseStatus.TOKEN_IS_EMPTY);
        }

        JwtUser jwtUser = tokenManager.getUserFromCache(reqToken);

        if (jwtUser == null){
            throw new JwtException(ResponseStatus.TOKEN_IS_WRONG);
        }

        return jwtUser;
    }
}
