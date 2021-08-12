package io.github.tangmonkmeat.service;

import io.github.tangmonkmeat.model.Ad;
import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.Channel;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.vo.CatalogPageVo;
import io.github.tangmonkmeat.model.vo.IndexPageVo;

import java.util.List;

/**
 * Description: 首页显示
 *
 * @author zwl
 * @date 2021/7/22 上午10:27
 * @version 1.0
 */
public interface IndexService {


    IndexPageVo getIndex(int page, int size);

    List<Ad> getAdList();

    List<Channel> getChannelList();

    List<Goods> getIndexMore(int page, int size);

    CatalogPageVo getCatalogIndex();

    List<Category> getSubCatalogById(int id);
}
