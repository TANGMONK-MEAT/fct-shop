package io.github.tangmonkmeat;

import com.github.pagehelper.PageHelper;
import io.github.tangmonkmeat.Enum.RedisKeyEnum;
import io.github.tangmonkmeat.mapper.HistoryMapper;
import io.github.tangmonkmeat.model.History;
import io.github.tangmonkmeat.model.HistoryExample;
import io.github.tangmonkmeat.model.vo.WsMessage;
import io.github.tangmonkmeat.service.RedisService;
import io.github.tangmonkmeat.util.RedisKeyUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Resource
    HistoryMapper historyMapper;

    @Resource
    private RedisService redisService;


    private final String UNREAD_WS_MESSAGE_LIST_KEY
            = RedisKeyUtil.keyBuilder(RedisKeyEnum.UNREAD_WS_MESSAGE_LIST_KEY,"chatId");

    @Test
    public void test2(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //sd

        //PageHelper.offsetPage(0,10);
        List<History> chatHistory = historyMapper.selectChatHistory(25,sdf.format(new Date()));

        System.out.println(chatHistory.size());
    }

    @Test
    public void test_multGet(){
        List<Integer> list = new ArrayList<>();
        list.add(25);
        List<String> keys = list.stream()
                .map(id -> UNREAD_WS_MESSAGE_LIST_KEY + ":" + id)
                .collect(Collectors.toList());

        List<List<WsMessage>> ll = new ArrayList<>(keys.size());
        keys.forEach(key -> {
            List<WsMessage> item = (List<WsMessage>)redisService.lrange(key, 0, -1);
            ll.add(item);
        });
        System.out.println(ll);
    }

    @Test
    public void shouldAnswerWithTrue() {
        List<Integer> unreads = new ArrayList<>();
        //unreads.add(1);
        List<HistoryExample> lastReadChat = historyMapper.getLastReadChat(unreads, "oCCR55OYO5ZOCLgz9aszxRLN21BA", new Date());
        System.out.println();
    }


}
