package io.github.tangmonkmeat;

import io.github.tangmonkmeat.mapper.CategoryMapper;
import io.github.tangmonkmeat.mapper.EsProductMapper;
import io.github.tangmonkmeat.model.CategoryMenu;
import io.github.tangmonkmeat.model.EsProduct;
import io.github.tangmonkmeat.service.EsProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    EsProductService esProductService;

    @Resource
    EsProductMapper esProductMapper;

    @Resource
    CategoryMapper categoryMapper;

    @Test
    public void testCategoryMapper(){
        CategoryMenu menu = categoryMapper.selectParentCategoryMenu(12);

        System.out.println(menu);
    }

    @Test
    public void testMapper(){
        EsProduct esProductByPrimaryId = esProductMapper.getEsProductByPrimaryId(12);

        System.out.println();
    }

    @Test
    public void test(){
        esProductService.importAll();
    }

    @Test
    public void get(){
        Page<EsProduct> esProducts = esProductService.search("手机", 0, 10);

        System.out.println("");
    }

    @Test
    public void testRe(){

        Page<EsProduct> esProducts = esProductService.recommend((long) 8, 0, 10);

        System.out.println("");

    }


}
