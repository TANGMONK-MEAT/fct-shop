package io.github.tangmonkmeat.service.impl;

import com.github.pagehelper.PageHelper;
import io.github.tangmonkmeat.dto.SimpleUser;
import io.github.tangmonkmeat.mapper.CategoryMapper;
import io.github.tangmonkmeat.mapper.GoodsCommentMapper;
import io.github.tangmonkmeat.mapper.GoodsMapper;
import io.github.tangmonkmeat.model.Category;
import io.github.tangmonkmeat.model.Goods;
import io.github.tangmonkmeat.model.GoodsComment;
import io.github.tangmonkmeat.model.GoodsGallery;
import io.github.tangmonkmeat.model.vo.CommentVo;
import io.github.tangmonkmeat.service.GoodsService;
import io.github.tangmonkmeat.user.UserClientHandler;
import io.github.tangmonkmeat.util.BeanListUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description: 商品业务操作
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/20 上午8:53
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private GoodsCommentMapper goodsCommentMapper;

    @Autowired
    private UserClientHandler userClientHandler;

    @Override
    public List<Integer> getGoodsIdList() {
        return goodsMapper.selectGoodsIds();
    }

    @Override
    public void saveGoodsBrowseNum(int num, int id) {
        goodsMapper.updateGoodsBrowseCount(num,id);
    }


    /**
     * 获取与该商品相同子分类下的商品列表
     *
     * @param goodsId
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<Goods> getGoodsRelated(Long goodsId, int page, int size) {
        PageHelper.startPage(page,size);
        List<Goods> goodsList = goodsMapper.selectSimpleGoodsInSameCate(goodsId);

        // 如果相同子分类的商品小于10，就获取相同父分类下的
        if (goodsList.size() < 10){
            PageHelper.startPage(page,size);
            goodsList = goodsMapper.selectSimpleGoodsInSameParentCate(goodsId);
        }
        return goodsList;
    }

    @Override
    public Goods getGoodsByGoodsId(Long goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId.intValue());
    }

    @Override
    public List<Goods> getGoodsListByCateId(int cateId,int page,int size) {
        PageHelper.startPage(page,size);
        return goodsMapper.selectGoodsByCateId(cateId);
    }

    @Override
    public List<Category> getBrotherCategoryByCateId(int cateId) {
        return categoryMapper.selectBrotherCategory(cateId);
    }

    @Override
    public int getParentIdByCateId(int cateId) {
        return categoryMapper.selectParentIdById(cateId);
    }

    @Override
    public int getSellerHistory(String sellerId) {
        return goodsMapper.selectSellerHistory(sellerId);
    }

    @Override
    public List<GoodsGallery> getGoodsGallery(Long goodsId) {
        return goodsMapper.selectGalleryByGoodsId(goodsId);
    }

    /**
     * 获取goodsId商品的评论(2级)
     *
     * @param goodsId 商品id
     * @return CommentVo
     */
    @Override
    public List<CommentVo> getGoodsComment(Long goodsId) {
        List<GoodsComment> goodsComments = goodsMapper.selectBaseCommentById(goodsId);
        if (goodsComments == null || goodsComments.size() <= 0){
            return null;
        }

        List<CommentVo> baseComment = BeanListUtil.copyListProperties(goodsComments, CommentVo.class);
        final Set<String> userIdSet = new HashSet<>();

        // 把回复评论的评论加入baseComment
        baseComment.forEach(base -> {
            userIdSet.add(base.getUserId());
            // 查找回复评论的评论
            List<GoodsComment> replyListPo = goodsMapper.selectReplyComment(base.getId());
            List<CommentVo> replyList = BeanListUtil.copyListProperties(replyListPo, CommentVo.class);
            replyList.forEach(reply -> userIdSet.add(reply.getUserId()));
            base.setReplyList(replyList);
        });

        // 去用户服务查找评论用户的信息
        Map<String, SimpleUser> simpleUserMap
                = userClientHandler.getSimpleUserList(new ArrayList<>(userIdSet));

        // 把用户信息加到评论中
        baseComment.stream().map(base -> setUser4Comment(simpleUserMap, base).getReplyList())
                .flatMap(List::stream)
                .forEach(reply -> setUser4Comment(simpleUserMap, reply));
        return baseComment;
    }

    /**
     * 添加评论
     *
     */
    @Override
    public void addComment(GoodsComment goodsComment) {
        goodsCommentMapper.insertSelective(goodsComment);
    }

    private CommentVo setUser4Comment(Map<String, SimpleUser> simpleUserMap, CommentVo comment) {
        SimpleUser userDTO = simpleUserMap.get(comment.getUserId());
        if (userDTO == null) {
            comment.setSimpleUser(SimpleUser.unknownUser());
        } else {
            comment.setSimpleUser(userDTO);
        }
        SimpleUser replyUserDTO = simpleUserMap.get(comment.getReplyUserId());
        if (replyUserDTO == null) {
            comment.setReplyUserName("用户不存在");
        } else {
            comment.setReplyUserName(replyUserDTO.getNickName());
        }
        return comment;
    }
}
