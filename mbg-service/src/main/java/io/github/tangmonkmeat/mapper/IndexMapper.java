package io.github.tangmonkmeat.mapper;

import io.github.tangmonkmeat.model.Ad;
import io.github.tangmonkmeat.model.Channel;

import java.util.List;

/**
 * Description: 首页数据
 *
 * @author zwl
 * @date 2021/7/22 上午10:45
 * @version 1.0
 */
public interface IndexMapper {

    List<Channel> findChannel(int count);

    List<Ad> findAd(int count);

}
