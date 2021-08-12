package io.github.tangmonkmeat.token.injection;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {

    /**
     * 是否需要获取token中的JwtUser，默认需要
     */
    boolean required() default true;

    /**
     * 检查过期，默认要检查
     *
     * 如果过期，将会抛出 {@link io.github.tangmonkmeat.exception.JwtException}
     *
     */
    boolean checkExpired() default true;
}
