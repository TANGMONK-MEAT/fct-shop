package io.github.tangmonkmeat.controller;

import io.github.tangmonkmeat.dto.OssCallbackResult;
import io.github.tangmonkmeat.dto.OssPolicyResult;
import io.github.tangmonkmeat.service.Impl.OssServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/17 下午8:12
 */
@Controller
@RequestMapping(value = "/aliyun/oss")
public class TestOssController {

    @Autowired
    private OssServiceImpl ossService;

    /**
     * oss上传签名生成
     */
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> policy() {
        OssPolicyResult result = ossService.policy();
        Map<String,Object> res = new HashMap<>(5);
        res.put("data",result);
        res.put("errno",0);
        res.put("errmsg","");
        return ResponseEntity.ok(res);
    }

    /**
     * oss上传成功回调
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> callback(HttpServletRequest request) {
        Map<String,Object> res = new HashMap<>(5);
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        res.put("data",ossCallbackResult);
        res.put("errno",0);
        res.put("errmsg","");
        return ResponseEntity.ok(res);
    }
}
