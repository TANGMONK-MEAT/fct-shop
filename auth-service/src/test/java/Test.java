import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.tangmonkmeat.AuthServiceApplication;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.token.TokenManager;
import io.github.tangmonkmeat.token.injection.Auth;
import io.github.tangmonkmeat.util.JsonUtil;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Key;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 下午5:03
 */
@SpringBootTest(classes = AuthServiceApplication.class)
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    TokenManager tokenManager;

    @Autowired
    RedisService redisService;

    private String token;


    @org.junit.Test
    public void bef(){
        JwtUser user = new JwtUser();
        user.setOpenId("123");
        user.setNickName("123");
        user.setAvatarUrl("httpsasa");
        token = tokenManager.generateToken(user);
        tokenManager.cacheTokenAndUser(token,user);
    }

    @org.junit.Test
    public void test(){
        // 校验
        DecodedJWT jwt = tokenManager.verify(token);

        //// 续约？？？
        tokenManager.renewToken(jwt,token);

        System.out.println(token);
    }
}
