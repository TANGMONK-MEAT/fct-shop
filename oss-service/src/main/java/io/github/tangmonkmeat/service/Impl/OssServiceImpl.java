package io.github.tangmonkmeat.service.Impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tangmonkmeat.dto.OssCallbackParam;
import io.github.tangmonkmeat.dto.OssCallbackResult;
import io.github.tangmonkmeat.dto.OssPolicyResult;
import io.github.tangmonkmeat.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: OSS 文件管理业务实现
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/17 下午7:46
 */
@Service
public class OssServiceImpl implements OssService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);

    @Value("${aliyun.oss.policy.expire}")
    private int ALIYUN_OSS_EXPIRE;
    @Value("${aliyun.oss.maxSize}")
    private int ALIYUN_OSS_MAX_SIZE;
    @Value("${aliyun.oss.callback}")
    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;

    @Autowired
    private OSSClient ossClient;

    @Override
    public OssPolicyResult policy() {
        // 存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = ALIYUN_OSS_DIR_PREFIX+sdf.format(new Date());
        // 签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);
        // 文件大小
        long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
        // 回调
        OssCallbackParam callback = new OssCallbackParam();
        callback.setCallbackUrl(ALIYUN_OSS_CALLBACK);
        callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        callback.setCallbackBodyType("application/x-www-form-urlencoded");
        // 提交节点
        String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;

        PolicyConditions policyConds = new PolicyConditions();
        // 设置文件大小限制
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
        // 设置文件保存路径
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        // 生成文件上传策略
        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String policy = BinaryUtil.toBase64String(binaryData);
        // 生成策略签名
        String signature = ossClient.calculatePostSignature(postPolicy);

        // base64 回调接口设置
        String calbackJson = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            calbackJson = mapper.writeValueAsString(callback);
        }catch (JsonProcessingException e){
        }
        String callbackData = BinaryUtil.toBase64String(calbackJson.getBytes(StandardCharsets.UTF_8));

        // 返回结果
        OssPolicyResult result = new OssPolicyResult();
        result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
        result.setPolicy(policy);
        result.setSignature(signature);
        result.setDir(dir);
        result.setCallback(callbackData);
        result.setHost(action);
        return result;
    }

    @Override
    public OssCallbackResult callback(HttpServletRequest request) {
        String filename = request.getParameter("filename");
        filename = "http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".")
                .concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);

        OssCallbackResult result= new OssCallbackResult();
        result.setFilename(filename);
        result.setSize(request.getParameter("size"));
        result.setMimeType(request.getParameter("mimeType"));
        result.setWidth(request.getParameter("width"));
        result.setHeight(request.getParameter("height"));
        return result;
    }
}
