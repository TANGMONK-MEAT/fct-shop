package io.github.tangmonkmeat.service.impl;

import com.github.pagehelper.PageHelper;
import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.dto.SimpleGoods;
import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.goods.GoodsClientHandler;
import io.github.tangmonkmeat.mapper.ChatMapper;
import io.github.tangmonkmeat.mapper.HistoryMapper;
import io.github.tangmonkmeat.model.History;
import io.github.tangmonkmeat.model.HistoryExample;
import io.github.tangmonkmeat.model.vo.ChatIndex;
import io.github.tangmonkmeat.model.vo.ChatIndexEle;
import io.github.tangmonkmeat.model.vo.WsMessage;
import io.github.tangmonkmeat.service.IndexService;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.user.UserClientHandler;
import io.github.tangmonkmeat.util.ListUtil;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/27 下午2:43
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private ChatMapper chatMapper;

    @Resource
    private HistoryMapper historyMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserClientHandler userClientHandler;

    @Autowired
    private GoodsClientHandler goodsClientHandler;

    private final String UNREAD_WS_MESSAGE_LIST_KEY
            = RedisKeyUtil.keyBuilder(RedisKeyEnum.UNREAD_WS_MESSAGE_LIST_KEY,"chatId");

    @Override
    public ChatIndex showIndex(String openId, int size, Date offsetTime) {

        // 获取当前用户参与的所有对话的chatId
        List<Integer> chatIds = chatMapper.getChatIdByUser(openId);

        // 获取缓存中（未读）和db中（已读）获取 size个条读消息
        List<String> keys = chatIds.stream()
                .map(id -> UNREAD_WS_MESSAGE_LIST_KEY + ":" + id)
                .collect(Collectors.toList());

        List<List<WsMessage>> unreadWsMessage = new ArrayList<>(keys.size());
        keys.forEach(key -> {
            List<WsMessage> item = (List<WsMessage>)redisService.lrange(key, 0, -1);
            unreadWsMessage.add(item);
        });

        List<ChatIndexEle> unread = getDisPlayUnread(openId, size, offsetTime, unreadWsMessage);

        List<Integer> unreadChatIds = unreadWsMessage.stream()
                .filter(unreadList -> unreadList != null && unreadList.size() > 0)
                .map(unreadList -> unreadList.get(0).getChatId())
                .collect(Collectors.toList());

        List<ChatIndexEle> read = getDisPlayRead(openId, size, offsetTime, unreadChatIds);

        // 排序，删除超出size的
        List<ChatIndexEle> sortAndLimit = sortAndLimit(unread, read, size);

        // 添加用户和商品信息
        List<ChatIndexEle> chatIndexEleList = setGoodsAndUser(sortAndLimit);

        ChatIndex vo = new ChatIndex();
        vo.setChats(chatIndexEleList);
        if(!ObjectUtils.isEmpty(chatIds) && chatIndexEleList.size() > 0){
            vo.setOffsetTime(ListUtil.getLast(chatIndexEleList).getLastChat().getSendTime());
        }

        return vo;
    }

    private List<ChatIndexEle> getDisPlayRead(String curOpenId,int size,Date offsetTime,List<Integer> unreadChatIds){

        PageHelper.offsetPage(0,size);
        List<HistoryExample> readHistory = historyMapper.getLastReadChat(unreadChatIds, curOpenId, offsetTime);


        return readHistory.stream()
                .map(history -> {
                    ChatIndexEle chatIndexEle = new ChatIndexEle();
                    chatIndexEle.setGoodsId((long)history.getGoodsId());

                    if (curOpenId.equals(history.getU1())){
                        chatIndexEle.setUserId(history.getU2());
                    }else{
                        chatIndexEle.setUserId(history.getU1());
                    }

                    // 设置未读数为 0
                    chatIndexEle.setUnreadCount(0);

                    // 设置最后一条消息
                    History lastHistory = new History();
                    BeanUtils.copyProperties(history,lastHistory);
                    chatIndexEle.setLastChat(lastHistory);
                    return chatIndexEle;
                }).collect(Collectors.toList());
    }

    private List<ChatIndexEle> getDisPlayUnread(String curOpenId,int size,Date offsetTime,List<List<WsMessage>> unread){

        return unread.stream()
                .filter(msgList -> !ObjectUtils.isEmpty(msgList)
                        && offsetTime.compareTo(ListUtil.getLast(msgList).getSendTime()) > 0)
                .sorted((a, b) -> ListUtil.getLast(b).getSendTime().compareTo(ListUtil.getLast(a).getSendTime()))
                .limit(size)
                .map(msgList -> {
                    WsMessage lastMsg = ListUtil.getLast(msgList);

                    ChatIndexEle chatIndexEle = new ChatIndexEle();
                    chatIndexEle.setGoodsId((long)lastMsg.getGoodsId());

                    // 如果是自己发送的，不设置未读消息个数
                    if (lastMsg.getSenderId().equals(curOpenId)) {
                        chatIndexEle.setUnreadCount(0);
                        chatIndexEle.setUserId(lastMsg.getReceiverId());
                    } else {
                        long count = msgList.stream().filter(message -> message.getReceiverId().equals(curOpenId)).count();
                        chatIndexEle.setUnreadCount((int) count);
                        chatIndexEle.setUserId(lastMsg.getSenderId());
                    }

                    // 设置最后一条消息
                    History lastHistory = new History();
                    BeanUtils.copyProperties(lastMsg, lastHistory);
                    chatIndexEle.setLastChat(lastHistory);

                    return chatIndexEle;
                }).collect(Collectors.toList());
    }

    private List<ChatIndexEle> sortAndLimit(List<ChatIndexEle> unread, List<ChatIndexEle> read, int size) {
        List<ChatIndexEle> list = new ArrayList<>();
        list.addAll(unread);
        list.addAll(read);

        List<ChatIndexEle> groupList = new ArrayList<>();

        // 相同goodsId的未读和已读集合合并,找到其中最后一条消息
        list.stream()
                .collect(Collectors.groupingBy(ChatIndexEle::getGoodsId))
                .forEach((goodsId,eleList) ->{
                    Optional<ChatIndexEle> lastEle = eleList.stream()
                            .max((a, b) -> -b.getLastChat().getSendTime().compareTo(a.getLastChat().getSendTime()));
                    lastEle.ifPresent(groupList::add);
                });

        return groupList.stream()
                .sorted((a, b) -> b.getLastChat().getSendTime().compareTo(a.getLastChat().getSendTime()))
                .limit(size).collect(Collectors.toList());
    }

    private List<ChatIndexEle> setGoodsAndUser(List<ChatIndexEle> chatIndexEles){
        final Set<Long> goodsIds = new HashSet<>();
        final Set<String> openIds = new HashSet<>();

        chatIndexEles.forEach(ele ->{
            goodsIds.add(ele.getGoodsId());
            openIds.add(ele.getUserId());
        });

        // 从 【user-service】 获取用户信息
        Map<String, SimpleUser> simpleUserList = userClientHandler.getSimpleUserList(new ArrayList<>(openIds));

        // 从 【goods-service】 获取商品的信息
        Map<Long, SimpleGoods> simpleGoodsList = goodsClientHandler.getSimpleGoodsList(new ArrayList<>(goodsIds));

        chatIndexEles.forEach(ele ->{

            String openId = ele.getUserId();
            SimpleUser simpleUser = simpleUserList.get(openId);
            if (simpleUser == null){
                simpleUser = SimpleUser.unknownUser();
            }else{
                ele.setOtherSide(simpleUser);
            }

            Long goodsId = ele.getGoodsId();
            SimpleGoods simpleGoods = simpleGoodsList.get(goodsId);
            if (simpleGoods == null){
                simpleGoods = SimpleGoods.unknownGoods();
            }else{
                ele.setGoods(simpleGoods);
            }
        });
        return chatIndexEles;
    }
}
