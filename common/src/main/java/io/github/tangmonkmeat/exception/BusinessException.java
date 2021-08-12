package io.github.tangmonkmeat.exception;

/**
 * 业务异常，更多具体的异常也将继承此类
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/13 下午8:52
 */
public class BusinessException extends Exception{

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

    private int errno;
    private String errmsg;

    public BusinessException(int errno, String errmsg) {
        this.errno = errno;
        this.errmsg = errmsg;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
