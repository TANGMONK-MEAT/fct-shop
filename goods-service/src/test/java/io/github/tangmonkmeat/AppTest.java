package io.github.tangmonkmeat;

import io.github.tangmonkmeat.constant.GoodsConstant;
import io.github.tangmonkmeat.mapper.GoodsMapper;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.service.GoodsService;
import io.github.tangmonkmeat.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    private RedisScript<Long> cacheGoodsMapScript;

    @Autowired
    private RedisScript<Long> cacheGoodsListScript;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void test() {
        List<String> keys = new ArrayList<>();
        keys.add("testLus");

        Long res = redisTemplate.execute(cacheGoodsMapScript, keys, 123,new Goods(),360);
        System.out.println(res);
    }

    @Test
    public void test_redis_map_list(){

        //ArrayList<Goods> list = new ArrayList<>();
        //
        //list.add(new Goods());
        //
        //list.add(new Goods());
        //
        //redisTemplate.opsForHash().put("goodsPage","1_10",list);

        List<Goods> goodsPage = (List<Goods>)redisService.hget("goodsPage", "1_10");

        //
        //System.out.println(goodsPage.getClass());

        System.out.println();
    }

    @Test
    public void test_1(){
        // 设置过期时间，前提不存在
        List<String> keys = new ArrayList<>(1);
        keys.add("test");

        //redisTemplate.execute(cacheGoodsListScript,keys,goods,GoodsConstant.GOODS_INFO_EXPIRE);

        redisService.eval(cacheGoodsListScript, keys, "", "360");
    }

    @Test
    public void test_2(){
        //List<Goods> goodsList = goodsService.getGoodsListByCateId(44, 1, 10);

        List<Goods> goodsList1 = goodsMapper.selectGoodsByCateId(44);

        System.out.println(goodsList1);
    }
}
