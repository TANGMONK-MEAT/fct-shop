---
--- 缓存用户收藏和缓存收藏过的用户
--- Created by zwl.
--- DateTime: 2021/7/23 下午8:44
---
local userSetKey=KEYS[1]
local collectKey=KEYS[2]

local goodsId=ARGV[1]
local type=ARGV[2]
local increment=ARGV[3]
local openId=ARGV[4]

redis.call("HSET",collectKey,goodsId,type)
redis.call("ZINCRBY",userSetKey,increment,openId)

return 1