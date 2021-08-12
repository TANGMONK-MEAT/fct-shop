package io.github.tangmonkmeat.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.constant.TokenConstant;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.token.injection.Jwt;
import io.github.tangmonkmeat.util.JsonUtil;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import io.github.tangmonkmeat.vo.AuthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * token 管理器
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 上午9:42
 */
@Component
public class TokenManager {

    /**
     * token_key:123
     *
     */
    public static final String TOKEN_USER_INFO_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.TOKEN_USER_INFO_KEY,"token");
    public static final String OPENID_USER_INFO_KEY = RedisKeyUtil.keyBuilder(RedisKeyEnum.OPENID_USER_INFO_KEY,"openId");

    @Autowired
    private RedisService redisService;

    @Value("${jwt.pub-key-file-name}")
    private String pubFile;

    @Value("${jwt.pvt-key-file-name}")
    private String pvtFile;

    @Value("${jwt.duration}")
    private Duration duration;

    public RSAKeyProvider keyProvider = new RSAKeyProvider() {

        RSAPublicKey key1;

        RSAPrivateKey key2;

        @Override
        public RSAPublicKey getPublicKeyById(String s) {
            return key1 == null ? RsaKeysReader.readRsaPub(pubFile) : key1;
        }

        @Override
        public RSAPrivateKey getPrivateKey() {
            return key2 == null ? RsaKeysReader.readRsaPvt(pvtFile) : key2;
        }

        @Override
        public String getPrivateKeyId() {
            return null;
        }
    };

    /**
     * 从缓存中获取 JwtUser
     *
     * @param token token
     * @return 不存在 返回 null
     */
    public JwtUser getUserFromCache(String token){
        final String key = TOKEN_USER_INFO_KEY + ":" +token;
        if (!redisService.hasKey(key)){
            return null;
        }
        return redisService.get(key);
    }

    public AuthVO getAuthVoFromCache(String openId){
        final String key = OPENID_USER_INFO_KEY + ":" + openId;
        if (!redisService.hasKey(key)){
            return null;
        }
        return redisService.get(key);
    }

    /**
     * 根据 openId 确定是否 缓存 userInfo
     *
     * @param openId openId
     * @return true代表有
     */
    public boolean hasCacheByOpenId(String openId){
        final String key = OPENID_USER_INFO_KEY + ":" + openId;
        return redisService.hasKey(key);
    }

    /**
     * 生成token
     *
     * @param user {@link JwtUser}
     * @return token
     */
    public String generateToken(JwtUser user){
        Algorithm algorithm = Algorithm.RSA256(keyProvider);
        JWTCreator.Builder builder = JWT.create();

        // 设置 claim
        for(Field field : user.getClass().getDeclaredFields()){
            try{
                field.setAccessible(true);
                String value = (String) field.get(user);
                builder.withClaim(field.getName(),value);
            }catch (Exception e){
            }
        }
        Instant instant = Instant.now().plus(duration);
        Date expire = Date.from(instant);

        return builder.withExpiresAt(expire).sign(algorithm);
    }

    /**
     * JWT Token转JWTUser
     */
    public JwtUser solve(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        token = token.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.RSA256(keyProvider);
        //会自动验证过期时间
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        JwtUser jwtUser = new JwtUser();

        // 通过反射构造JWTUser对象
        for (Field field : jwtUser.getClass().getDeclaredFields()) {
            String value = decodedJWT.getClaim(field.getName()).asString();
            field.setAccessible(true);
            field.set(jwtUser, value);
        }
        return jwtUser;
    }

    /**
     * 校验 token
     *
     * @param token 令牌
     * @return JwtUser
     * @throws AlgorithmMismatchException – 如果令牌标头中声明的算法不等于JWTVerifier定义的JWTVerifier 。
     * @throws SignatureVerificationException – 如果签名无效。
     * @throws TokenExpiredException – 如果令牌已过期。
     * @throws InvalidClaimException – 如果令牌包含与预期不同的值。
     */
    public DecodedJWT verify(String token) throws JWTVerificationException{
        token = token.replace("Bearer ","");
        Algorithm algorithm = Algorithm.RSA256(keyProvider);
        // 校验token
        return JWT.require(algorithm).build().verify(token);
    }

    /**
     * 刷新token
     *
     * @param decodedJWT jwt
     * @param oldToken old token
     * @return 如果距离过期时间 <= {@link TokenConstant#NEED_RENEW_TOKEN_SECOND},则返回新token
     */
    public String renewToken(DecodedJWT decodedJWT,String oldToken){
        long exAt = decodedJWT.getClaim(PublicClaims.EXPIRES_AT).asDate().getTime();
        long now = System.currentTimeMillis();
        // 刷新token
        if (exAt - now <= TokenConstant.NEED_RENEW_TOKEN_SECOND){
            JwtUser user = getUserFromCache(oldToken);
            String newToken = generateToken(user);
            // 缓存
            cacheTokenAndUser(newToken,user);
            cacheOpenIdAndUserInfo(user.getOpenId(),new AuthVO(newToken,user));

            List<Integer> objects = new ArrayList<>();
            return newToken;
        }
        return oldToken;
    }

    /**
     * 获取 JwtUser
     *
     * @param decodedJWT jwt
     * @return JwtUser
     */
    public JwtUser getJwtUser(DecodedJWT decodedJWT){
        JwtUser user = new JwtUser();
        // 构造 JwtUser
        for(Field field : JwtUser.class.getDeclaredFields()){
            String value = decodedJWT.getClaim(field.getName()).asString();
            field.setAccessible(true);
            try {
                field.set(user,value);
            }catch (Exception e){
            }
        }
        return user;
    }

    /**
     * 缓存token: JwtUser
     *
     * @param token token
     * @param user JwtUser
     */
    public void cacheTokenAndUser(String token,Object user){
        redisService.setEx(TOKEN_USER_INFO_KEY + ":" + token,user,duration.getSeconds());
    }

    /**
     * 缓存openId: AuthVo
     *
     * @param openId openId
     * @param userInfo AuthVO
     */
    public void cacheOpenIdAndUserInfo(String openId, Object userInfo){
        redisService.setEx(OPENID_USER_INFO_KEY + ":" +openId,userInfo,duration.getSeconds());
    }
}
