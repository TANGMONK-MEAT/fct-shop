package io.github.tangmonkmeat.im;

import io.github.tangmonkmeat.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description: 提供对外服务
 *
 * @author zwl
 * @date 2021/7/27 下午9:00
 * @version 1.0
 */
@FeignClient(value = "im-service")
@RequestMapping(value = "/chat-service")
public interface ImClient {

    /**
     * 创建 对话
     *
     */
    @PostMapping("/createChat/{goodsId}/{senderId}/{receiverId}")
    Response<Integer> createChat(@PathVariable("goodsId") Long goodsId,
                                        @PathVariable("senderId") String senderId,
                                        @PathVariable("receiverId") String receiverId);

}
