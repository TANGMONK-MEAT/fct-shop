---
--- 缓存商品信息列表，数据结构 list，过期时间 7天
--- Created by zwl.
--- DateTime: 2021/7/22 下午5:28
---

local key=KEYS[1]
local goodsInfo=ARGV[1]
local ex=ARGV[2]

local res=redis.call("RPUSH",key,goodsInfo)

if res>0 then
    redis.call("EXPIRE",key,ex)
    redis.call("RPOP",key)
    return 1
else
    return 0
end