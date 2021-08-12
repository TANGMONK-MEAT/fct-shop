package io.github.tangmonkmeat.exception.handler;

import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.exception.BusinessException;
import io.github.tangmonkmeat.exception.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;

/**
 * 全局异常拦截器
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/13 下午8:57
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(BusinessException e) {
        LOGGER.error("发生BusinessException异常，errno = {},errmsg = {}", e.getErrno(), e.getErrmsg());
        return Response.fail(e.getErrno(), e.getErrmsg());
    }

    @ExceptionHandler(JwtException.class)
    public Object handleJwtException(JwtException e) {
        LOGGER.error("发生JwTException，errno = {},errmsg = {}", e.getErrno(), e.getErrmsg());
        return Response.fail(e.getErrno(), e.getErrmsg());
    }
    /**
     * 集中 处理 springmvc 相关异常，可以参见 {@link ResponseEntityExceptionHandler}
     *
     */
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class,
            NoHandlerFoundException.class,
            AsyncRequestTimeoutException.class
    })
    public Object handlerException(Exception ex){

        LOGGER.error("MVC 异常",ex);

        if (ex instanceof HttpRequestMethodNotSupportedException) {
            HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof HttpMediaTypeNotSupportedException) {
            HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof MissingPathVariableException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof MissingServletRequestParameterException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof ServletRequestBindingException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof ConversionNotSupportedException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof TypeMismatchException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof HttpMessageNotReadableException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof HttpMessageNotWritableException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof MethodArgumentNotValidException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof MissingServletRequestPartException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof BindException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof NoHandlerFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        else if (ex instanceof AsyncRequestTimeoutException) {
            HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
            return Response.fail(status.value(),status.getReasonPhrase());
        }
        return handleException(ex);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e){
        LOGGER.info("发生unknown异常：", e);
        return Response.fail(ResponseStatus.INTERNAL_SERVER_ERROR,null);
    }
}
