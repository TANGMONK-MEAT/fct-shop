package io.github.tangmonkmeat;

import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.mapper.CategoryMapper;
import io.github.tangmonkmeat.mapper.UserMapper;
import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.User;
import io.github.tangmonkmeat.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    private UserService userService;

    @Resource
    CategoryMapper categoryMapper;

    @Test
    public void test1(){
        Category category = categoryMapper.selectByPrimaryKey(12);

        System.out.println("");
    }


    @Resource
    UserMapper userMapper;

    @Test
    public void getSimpleUserListTest() {
        //List<Object> l = new ArrayList<>();
        //for (int i = 0; i <= 20; i++) {
        //    l.add(i);
        //}
        //
        //Map<String, Object> userList = userService.getSimpleUserList(l);
        //
        //System.out.println(userList);

    }

    @Test
    public void getSimpleUserTest(){
        SimpleUser simpleUser = userService.getSimpleUser("");

        SimpleUser simpleUser1 = userService.getSimpleUser("0");

        SimpleUser simpleUser2 = userService.getSimpleUser("1");

        SimpleUser simpleUser3 = userService.getSimpleUser("100");

        //User user = userMapper.selectByOpenId("100");

        System.out.println("========");
    }
}
