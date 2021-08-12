package io.github.tangmonkmeat.controller;

import com.netflix.discovery.converters.Auto;
import io.github.tangmonkmeat.Enum.ResponseStatus;
import io.github.tangmonkmeat.dto.JwtUser;
import io.github.tangmonkmeat.dto.Response;
import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.im.ImClientHandler;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.vo.UserPageVo;
import io.github.tangmonkmeat.service.GoodsService;
import io.github.tangmonkmeat.service.UserService;
import io.github.tangmonkmeat.token.injection.Jwt;
import io.github.tangmonkmeat.user.UserClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Description: 用户相关的API
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/23 下午8:19
 */
@RestController
@RequestMapping(value = "/goodsUser")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserClientHandler userClientHandler;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ImClientHandler imClientHandler;


    /**
     * 收藏或取消收藏某个商品
     *
     * @param goodsId 商品ID
     * @param hasCollect 是否收藏
     * @param user JwtUser
     * @return ok
     */
    @PostMapping("/collect/addordelete/{goodsId}/{userHasCollect}")
    public Response<Object> collectAddOrDelete(@PathVariable("goodsId") int goodsId,
                                       @PathVariable("userHasCollect") boolean hasCollect,
                                       @Jwt JwtUser user) {
        userService.cacheCollectAddOrDelete(goodsId,user.getOpenId(),hasCollect);
        LOGGER.info("用户【{}】添加或删除收藏商品，商品id={}，是否是添加?{}", user.getNickName(), goodsId, !hasCollect);
        return Response.ok(null);
    }

    /**
     * 获取用户收藏的商品列表
     *
     * @param user
     * @return
     */
    @GetMapping("/collect")
    public Response<List<Goods>> getCollectList(@Jwt JwtUser user,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        List<Goods> vo = userService.getUserCollectList(user.getOpenId(), page, size);
        LOGGER.info("用户【{}】查询收藏的商品,总数:{}", user.getNickName(), vo.size());
        return Response.ok(vo);
    }

    /**
     * 获取用户买过的商品列表
     *
     * @param user
     * @return
     */
    @GetMapping("/bought")
    public Response<List<Goods>> getUserBought(@Jwt JwtUser user,
                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        List<Goods> vo = userService.getUserBought(user.getOpenId(), page, size);
        LOGGER.info("用户【{}】查询买过的商品,总数:{}", user.getNickName(), vo.size());
        return Response.ok(vo);
    }

    /**
     * 获取用户卖出的商品列表
     *
     * @param user
     * @return
     */
    @GetMapping("/sold")
    public Response<List<Goods>> getUserSold(@Jwt JwtUser user,
                                             @RequestParam(value = "page", defaultValue = "1") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        List<Goods> vo = userService.getUserSold(user.getOpenId(), page, size);
        LOGGER.info("用户【{}】查询卖出的商品,总数:{}", user.getNickName(), vo.size());
        return Response.ok(vo);
    }

    /**
     * 获取用户发布但还没卖出的商品列表
     *
     * @param user
     * @return
     */
    @GetMapping("/posted")
    public Response<List<Goods>> getUserPosted(@Jwt JwtUser user,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        List<Goods> vo = userService.getUserPosted(user.getOpenId(), page, size);
        LOGGER.info("用户【{}】查询发布的商品,总数:{}", user.getNickName(), vo.size());
        return Response.ok(vo);
    }

    /**
     * 用户主页
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/user/{userId}")
    public Response<UserPageVo> getUserPage(@PathVariable("userId") String userId,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
        SimpleUser user = userClientHandler.getSimpleUser(userId);
        if (user == null) {
            LOGGER.info("搜索goodsId = 【{}】的详情时出错", userId);
            return Response.fail(ResponseStatus.USER_IS_NOT_EXIST);
        }

        Integer soldCount = goodsService.getSellerHistory(userId);
        LinkedHashMap<String, List<Goods>> userHistory = userService.getUserHistoryList(userId, page, size);
        UserPageVo vo = new UserPageVo(user, userHistory, soldCount);
        LOGGER.info("浏览用户id=[{}],昵称=[{}]的首页,搜索到{}天的记录", user.getOpenId(), user.getNickName(), userHistory == null ? 0 : userHistory.size());

        return Response.ok(vo);
    }

    /**
     * 用户主页,获取更多
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/user/more/{userId}")
    public Response<LinkedHashMap<String, List<Goods>>> getUserPageMore(@PathVariable("userId") String userId,
                                                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                                                        @RequestParam(value = "size", defaultValue = "10") int size) {

        LinkedHashMap<String, List<Goods>> userHistory = userService.getUserHistoryList(userId, page, size);
        LOGGER.info("浏览用户id=[{}]的首页,搜索到{}天的记录", userId, userHistory.size());

        return Response.ok(userHistory);
    }

    /**
     * 标记为想要,创建chat
     *
     */
    @PostMapping("/want/{goodsId}/{sellerId}")
    public Response<Integer> want(@Jwt JwtUser user,
                                  @PathVariable("goodsId") int goodsId,
                                  @PathVariable("sellerId") String sellerId) {

        userService.addWant(goodsId,sellerId);
        // 创建对话
        Integer charId = imClientHandler.createChat((long) goodsId, user.getOpenId(), sellerId);
        LOGGER.info("用户id=[{}],将商品id=[{}]标记为想要.并创建对话,chatId=[{}]", user.getOpenId(), goodsId, charId);
        return Response.ok(charId);
    }

}
