package io.github.tangmonkmeat.controller;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.model.vo.ChatForm;
import io.github.tangmonkmeat.model.vo.ChatIndex;
import io.github.tangmonkmeat.service.FormService;
import io.github.tangmonkmeat.service.IndexService;
import io.github.tangmonkmeat.token.injection.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午10:57
 */
@RestController
@RequestMapping(value = "/chat")
public class ChatIndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatIndexController.class);

    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private IndexService indexService;

    @Autowired
    private FormService formService;

    /**
     * 消息首页接口
     *
     */
    @GetMapping("/index")
    public Response<ChatIndex> getChatIndex(@Jwt JwtUser user,
                                            @RequestParam(value = "offsetTime", required = false)
                                            @DateTimeFormat(pattern = TIME_PATTERN) Date offsetTime,
                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        if (user == null){
            return Response.fail(ResponseStatus.USER_IS_NOT_EXIST);
        }
        if (offsetTime == null){
            offsetTime = new Date();
        }
        ChatIndex vo = indexService.showIndex(user.getOpenId(), size, offsetTime);
        LOGGER.info("展示消息一览,展示{} 条信息.用户id = {},用户昵称 = {}", vo.getChats().size(), user.getOpenId(), user.getNickName());
        return Response.ok(vo);
    }

    /**
     * 展示聊天框内的消息
     *
     */
    @GetMapping("/form/{chatId}")
    public Response<ChatForm> getChatForm(@PathVariable("chatId") int chatId,
                                          @Jwt JwtUser user,
                                          @RequestParam(value = "offsetTime", required = false)
                                          @DateTimeFormat(pattern = TIME_PATTERN) Date offsetTime,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        // 展示在offsetTime之前收到的消息
        if (offsetTime == null) {
            offsetTime = new Date();
        }
        ChatForm vo = formService.showForm(chatId, user.getOpenId(), size, offsetTime);
        int num = vo.getHistoryList() != null ? vo.getHistoryList().size() : 0;
        LOGGER.info("用户openId={}获取与用户openId={}的聊天记录,展示 {} 条记录",
                user.getOpenId(), vo.getOtherSide().getOpenId(), num);
        return Response.ok(vo);
    }

    /**
     * 用户在这个chat中的所有未读消息设为已读.
     * 通过ws在聊天框中实时阅读到消息时使用
     *
     */
    @PostMapping("/flushUnread/{chatId}")
    public Response<Object> flushUnread(@PathVariable("chatId") int chatId,
                                @Jwt JwtUser user) {
        formService.flushUnread(chatId, user.getOpenId());
        LOGGER.info("用户openId={}chatId={}的所有未读消息设为已读", user.getOpenId(), chatId);
        return Response.ok(null);
    }

    /**
     * 删除指定 chatID的chat
     *
     */
    @PostMapping("/del/{chatId}")
    public Response<Object> delChat(@PathVariable("chatId") int chatId,@Jwt JwtUser user){
        formService.delAndClearChat(chatId);
        LOGGER.info("用户openId={}chatId={}的所有消息已被清空",user.getOpenId(),chatId);
        return Response.ok(null);
    }
}