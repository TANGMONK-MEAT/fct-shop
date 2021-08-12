package io.github.tangmonkmeat.exception;

import io.github.tangmonkmeat.Enum.ResponseStatus;

/**
 * 授权凭证异常
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/13 下午8:54
 */
public class JwtException extends RuntimeException{

    private int errno;

    private String errmsg;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public JwtException(int errno, String errmsg){
        this.errno = errno;
        this.errmsg = errmsg;
    }

    public JwtException(ResponseStatus status){
        this.errno = status.errno();
        this.errmsg = status.errmsg();
    }

    public JwtException() {
        super();
    }

    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtException(Throwable cause) {
        super(cause);
    }

    protected JwtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
