import io.github.tangmonkmeat.AuthServiceApplication;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/15 下午4:28
 */
@SpringBootTest(classes = AuthServiceApplication.class)
@RunWith(SpringRunner.class)
public class TestMq {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    Queue testQueue(){
        return new Queue("test",true,false,false,null);
    }

    @org.junit.Test
    public void test_simple(){
        System.out.println(rabbitTemplate);
        rabbitTemplate.convertAndSend("", "test","hello simple");
    }
}
