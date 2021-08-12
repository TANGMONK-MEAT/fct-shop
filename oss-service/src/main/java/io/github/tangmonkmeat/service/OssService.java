package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.dto.OssCallbackResult;
import io.github.tangmonkmeat.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Description: OSS 文件管理业务接口
 *
 * @author zwl
 * @date 2021/7/17 下午7:43
 * @version 1.0
 */
public interface OssService {

    /**
     * 生成 oss上传策略，用于 文件上传时的权限校验
     *
     * @return {@link OssPolicyResult}
     */
    OssPolicyResult policy() throws UnsupportedEncodingException;

    /**
     * oss上传成功后的回调, request 中有上传文件的信息
     *
     * @param  request {@link HttpServletRequest}
     * @return  {@link OssCallbackResult}
     */
    OssCallbackResult callback(HttpServletRequest request);

}
