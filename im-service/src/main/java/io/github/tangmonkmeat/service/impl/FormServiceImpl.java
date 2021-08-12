package io.github.tangmonkmeat.service.impl;

import com.github.pagehelper.PageHelper;
import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.dto.SimpleGoods;
import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.goods.GoodsClientHandler;
import io.github.tangmonkmeat.mapper.ChatMapper;
import io.github.tangmonkmeat.mapper.HistoryMapper;
import io.github.tangmonkmeat.model.Chat;
import io.github.tangmonkmeat.model.History;
import io.github.tangmonkmeat.model.vo.ChatForm;
import io.github.tangmonkmeat.model.vo.WsMessage;
import io.github.tangmonkmeat.service.FormService;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.user.UserClientHandler;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午2:44
 */
@Service
public class FormServiceImpl implements FormService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormServiceImpl.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private RedisService redisService;

    private final String UNREAD_WS_MESSAGE_LIST_KEY
            = RedisKeyUtil.keyBuilder(RedisKeyEnum.UNREAD_WS_MESSAGE_LIST_KEY,"chatId");

    @Resource
    private HistoryMapper historyMapper;

    @Resource
    private ChatMapper chatMapper;

    @Autowired
    private UserClientHandler userClientHandler;

    @Autowired
    private GoodsClientHandler goodsClientHandler;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAndClearChat(int chatId) {
        final String key = UNREAD_WS_MESSAGE_LIST_KEY + ":" + chatId;
        // 将未读消息设置为已读，入库
        Chat chat = chatMapper.selectByPrimaryKey(chatId);
        flushUnread(chatId,chat.getU1());
        flushUnread(chatId,chat.getU2());

        // 删除DB的chat
        int row = chatMapper.updateChatStatus(chatId, true);
        if (row > 0){
            // 删除DB的history
            int row1 = historyMapper.updateHistoryStatus(chatId, true);
            if (row1 > 0){
                // 删除缓存
                redisService.del(key);
            }
        }
    }

    @Override
    public ChatForm showForm(int chatId, String openId, int size, Date offsetTime) {
        ChatForm chatForm = new ChatForm();

        // 查询商品和商品发布者的简单信息
        Chat chat = chatMapper.selectByPrimaryKey(chatId);
        SimpleGoods simpleGoods = goodsClientHandler.getSimpleGoods((long) chat.getGoodsId());
        chatForm.setGoods(simpleGoods);
        if (chat.getU1().equals(openId)){
            SimpleUser simpleUser = userClientHandler.getSimpleUser(chat.getU2());
            chatForm.setOtherSide(simpleUser);
            chatForm.setU1(true);
        }else{
            SimpleUser simpleUser = userClientHandler.getSimpleUser(chat.getU1());
            chatForm.setOtherSide(simpleUser);
            chatForm.setU1(false);
        }

        LocalDateTime now = LocalDateTime.now();

        // 从DB中，获取已读的聊天记录
        PageHelper.offsetPage(0,size);
        List<History> chatHistory = historyMapper.selectChatHistory(chatId,now.format(formatter));

        // 从缓存中，获取自己或对方未读的消息, 并把自己未读的设为已读并入库
        List<History> unreadList = flushUnread(chatId, openId);

        if (!ObjectUtils.isEmpty(unreadList) && unreadList.size() > 0) {
            chatHistory.addAll(unreadList);
        }

        // 个数和发送时间过滤，倒序，获取最新的
        chatHistory = chatHistory.stream()
                .filter(a -> offsetTime.compareTo(a.getSendTime()) > 0)
                .sorted((a, b) -> b.getSendTime().compareTo(a.getSendTime()))
                .limit(size)
                .collect(Collectors.toList());


        if (!ObjectUtils.isEmpty(chatHistory) && chatHistory.size() > 0) {
            chatHistory = chatHistory.stream()
                    .sorted((a, b) -> -b.getSendTime().compareTo(a.getSendTime()))
                    .collect(Collectors.toList());
            chatForm.setHistoryList(chatHistory);
            chatForm.setOffsetTime(chatHistory.get(0).getSendTime());
        }

        return chatForm;
    }

    /**
     * 返回自己或对方未读的消息，设置自己未读的消息为已读
     *
     */
    @Override
    public List<History> flushUnread(int chatId,String openId){
        final String key = UNREAD_WS_MESSAGE_LIST_KEY + ":" + chatId;
        List<WsMessage> unreadMsgList = (List<WsMessage>)redisService.lrange(key, 0, -1);

        if (!ObjectUtils.isEmpty(unreadMsgList) && unreadMsgList.size() > 0) {

            // 筛选出自己未读的消息列表
            List<WsMessage> myUnreadMsgList = unreadMsgList.stream()
                    .filter(unread -> unread.getReceiverId().equals(openId))
                    .collect(Collectors.toList());

            if (!ObjectUtils.isEmpty(myUnreadMsgList) && myUnreadMsgList.size() > 0) {
                LOGGER.info("把chatId={}设为已读消息", chatId);
                Chat chat = chatMapper.selectByPrimaryKey(chatId);

                // 标记为已读
                List<History> myUnreadHistory = wsListToHisList(chat,myUnreadMsgList);
                // 入库
                if(myUnreadHistory != null && myUnreadHistory.size() > 0){
                    historyMapper.insertHistoryList(myUnreadHistory);
                }

                // 删除缓存
                List<WsMessage> newUnreadList = new ArrayList<>(unreadMsgList);
                newUnreadList.removeAll(myUnreadMsgList);
                if (newUnreadList.size() > 0) {
                    redisService.del(key);
                    redisService.lmpush(key, newUnreadList);
                }
            }
            return unreadMsgList.stream().map(msg -> {
                            History history = new History();
                            BeanUtils.copyProperties(msg, history);
                            if (msg.getSenderId().equals(openId)){
                                history.setU1ToU2(true);
                            }
                            return history;
                        }).collect(Collectors.toList());
        }
        return null;
    }

    private List<History> wsListToHisList(Chat chat,List<WsMessage> wsMessageList){
        if (ObjectUtils.isEmpty(wsMessageList) || wsMessageList.size() == 0) {
            return null;
        }
        WsMessage message = wsMessageList.get(0);
        boolean u1ToU2 = message.getSenderId().equals(chat.getU1());

        List<History> historyList = new ArrayList<>();
        wsMessageList.forEach(msg -> {
            History history = new History();
            BeanUtils.copyProperties(msg, history);
            history.setU1ToU2(u1ToU2);
            historyList.add(history);
        });
        return historyList;
    }
}