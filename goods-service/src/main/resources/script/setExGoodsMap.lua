---
--- 缓存商品信息，数据结构 hash，过期时间 7天
--- Created by zwl.
--- DateTime: 2021/7/22 下午5:28
---
local key=KEYS[1]
local field=ARGV[1]
local value=ARGV[2]
local ex=ARGV[3]

local res=redis.call("HSET",key,field,value)

if res>0 then
    redis.call("EXPIRE",key,ex)
    redis.call("HDEL",key,field)
    return 1
else
    return 0
end