# fct-shop

校园二手交易平台小程序

# 技术点

前端：微信小程序的Mini框架

后台：Java8，SpringBoot，SpringCloud，Redis，MySQL，RabbitMQ

其他：Maven，docker，阿里云OSS对象存储

# 完成功能

* 首页 : 广告banner,推荐分类,推荐商品
* 分类功能 : 2级分类,分类浏览商品
* 搜索功能 : 关键字搜索,搜索历史,热门关键字,搜索辅助,
* 浏览商品详情 : 商品信息,2级评论,卖家信息,相关商品,标记为收藏,标记为想要
* 发布商品 : 上传商品图，阿里云OSS对象存储
* 聊天功能 : 基于WebSocket
* 其他功能 : 用户主页,查看自己发布,收藏,卖出,买到的商品

# 配置

1. common模块 配置小程序的appid，redis，rabbitmq
2. mbg模块配置mysql
3. oss模块配置阿里云OSS对象存储的配置

# 后端服务拆分

[![fgSpTA.png](https://z3.ax1x.com/2021/08/15/fgSpTA.png)](https://imgtu.com/i/fgSpTA)


