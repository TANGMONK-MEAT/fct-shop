package io.github.tangmonkmeat.token.injection;

import java.lang.annotation.*;

/**
 * JwtUser参数解析
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/13 下午10:39
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Jwt {

    /**
     * 是否需要获取token中的JwtUser，默认需要
     */
    boolean required() default true;

}
