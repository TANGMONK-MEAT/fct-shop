package io.github.tangmonkmeat.exception;

/**
 * Description: 用户注册重试超过阀值异常
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/17 上午10:04
 */
public class UserRegisterRetryOutException extends BusinessException{


    public UserRegisterRetryOutException(int errno, String errmsg) {
        super(errno,errmsg);
    }

    public UserRegisterRetryOutException() {
        super();
    }

    public UserRegisterRetryOutException(String message) {
        super(message);
    }

    public UserRegisterRetryOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRegisterRetryOutException(Throwable cause) {
        super(cause);
    }

    protected UserRegisterRetryOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
