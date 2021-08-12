package io.github.tangmonkmeat.dto;

import io.github.tangmonkmeat.Enum.ResponseStatus;

/**
 * 统一响应数据格式
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/13 下午7:52
 */
public class Response<T> {

    private int errno;

    private String errmsg;

    private T data;

    public Response(T data){
        this.data = data;
    }

    public Response(){}

    public Response(ResponseStatus status){
        this.errmsg = status.errmsg();
        this.errno = status.errno();
    }

    public Response(ResponseStatus status,T data){
        this.errmsg = status.errmsg();
        this.errno = status.errno();
        this.data = data;
    }

    public Response(int errno, String errmsg) {
        this.errno = errno;
        this.errmsg = errmsg;
    }

    public Response(int errno, String errmsg, T data) {
        this.errno = errno;
        this.errmsg = errmsg;
        this.data = data;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Response<?> ok(){
        return new Response<>();
    }

    public static <T> Response<T> ok(T data) {
        return new Response<T>(data);
    }

    public static Response<?> fail(int errno,String errmsg){
        return new Response<>(errno,errmsg);
    }

    public static <T> Response<T> fail(int errno,String errmsg,T data){
        return new Response<>(errno,errmsg,data);
    }

    public static <T> Response<T> fail(ResponseStatus status,T data){
        return new Response<>(status,data);
    }

    public static <T> Response<T> fail(ResponseStatus status){
        return new Response<>(status,null);
    }


}
