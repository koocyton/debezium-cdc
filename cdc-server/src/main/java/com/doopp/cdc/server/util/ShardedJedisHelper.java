package com.doopp.youlin.server.util;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class ShardedJedisHelper {

    private final ShardedJedisPool shardedJedisPool;

    private final JdkSerializationRedisSerializer redisSerializer = new JdkSerializationRedisSerializer();

    public ShardedJedisHelper(String redisServers, JedisPoolConfig jedisPoolConfig) {
        this(redisServers.split(","), jedisPoolConfig);
    }

    public ShardedJedisHelper(String[] redisServers, JedisPoolConfig jedisPoolConfig) {
        List<JedisShardInfo> jedisInfoList = new ArrayList<>(redisServers.length);
        for (String redisServer : redisServers) {
            JedisShardInfo jedisShardInfo = new JedisShardInfo(redisServer);
            jedisShardInfo.setConnectionTimeout(2000);
            jedisShardInfo.setSoTimeout(2000);
            jedisInfoList.add(jedisShardInfo);
        }
        this.shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, jedisInfoList);
    }

    public void setex(String key, int seconds, String value) {
        this.executeJedis((shardedJedis)->{
            shardedJedis.setex(key, seconds, value);
            return null;
        });
    }

    public void set(String key, String value) {
        this.executeJedis((shardedJedis)->{
            shardedJedis.set(key, value);
            return null;
        });
    }

    public String get(String key) {
        return this.executeJedis((shardedJedis)-> shardedJedis.get(key));
    }

    public void del(String... keys) {
        this.executeJedis((shardedJedis)->{
            for (String key : keys) {
                shardedJedis.del(key);
            }
            return null;
        });
    }

    public void setex(byte[] key, int seconds, Object object) {
        this.executeJedis((shardedJedis)->{
            byte[] _object = redisSerializer.serialize(object);
            return shardedJedis.setex(key, seconds, _object);
        });
    }

    public void set(byte[] key, Object object) {
        this.executeJedis((shardedJedis)->{
            byte[] _object = redisSerializer.serialize(object);
            shardedJedis.set(key, _object);
            return null;
        });
    }

    public <T> T get(byte[] key, Class<T> clazz) {
        return this.executeJedis((shardedJedis)->{
            byte[] res = shardedJedis.get(key);
            return byteToObject(res, clazz);
        });
    }

    private <T> T byteToObject(byte[] objectByte, Class<T> clazz) {
        if (objectByte == null) {
            return null;
        }
        Object desObj;
        try {
            desObj = redisSerializer.deserialize(objectByte);
        }
        catch(Exception e) {
            return null;
        }
        return clazz.cast(desObj);
    }

    public void del(byte[]... keys) {
        this.executeJedis((shardedJedis)->{
            for (byte[] key : keys) {
                shardedJedis.del(key);
            }
            return null;
        });
    }

    public String type(String key) {
        return this.executeJedis((shardedJedis)-> shardedJedis.type(key));
    }

    public String type(byte[] key) {
        return this.executeJedis((shardedJedis)-> shardedJedis.type(key));
    }

    public <T> T lindex(byte[] key, long index, Class<T> clazz) {
        return this.executeJedis((shardedJedis)-> {
            byte[] _object = shardedJedis.lindex(key, index);
            return byteToObject(_object, clazz);
        });
    }

    public void lset(byte[] key, long index, Object object) {
        this.executeJedis((shardedJedis)-> {
            byte[] _object = redisSerializer.serialize(object);
            return shardedJedis.lset(key, index, _object);
        });
    }

    public void ltrim(byte[] key, long start, long stop) {
        this.executeJedis((shardedJedis)-> {
            return shardedJedis.ltrim(key, start, stop);
        });
    }

    public void rpush(String key, String string) {
        this.executeJedis((shardedJedis)-> {
            return shardedJedis.rpush(key, string);
        });
    }

    public void rpush(byte[] key, Object object) {
        this.executeJedis((shardedJedis)-> {
            byte[] _object = redisSerializer.serialize(object);
            return shardedJedis.rpush(key, _object);
        });
    }

    public List<String> lrange(String key, long start, long stop) {
        return this.executeJedis((shardedJedis) -> {
            return shardedJedis.lrange(key, start, stop);
        });
    }

    public Long llen(byte[] key) {
        return this.executeJedis((shardedJedis) -> {
            return shardedJedis.llen(key);
        });
    }

    public Long llen(String key) {
        return this.executeJedis((shardedJedis) -> {
            return shardedJedis.llen(key);
        });
    }

    public <T> List<T> lrange(byte[] key, long start, long stop, Class<T> clazz) {
        return this.executeJedis((shardedJedis) -> {
            List<byte[]> lrangeList = shardedJedis.lrange(key, start, stop);
            List<T> lrangeObjectList = new ArrayList<>();
            for (byte[] lrange : lrangeList) {
                T object = byteToObject(lrange, clazz);
                lrangeObjectList.add(object);
            }
            return lrangeObjectList;
        });
    }

    public void hset(String key, String field, String value) {
        this.executeJedis((shardedJedis)-> shardedJedis.hset(key, field, value));
    }

    public void hdel(String key, String field) {
        this.executeJedis((shardedJedis)-> shardedJedis.hdel(key, field));
    }

    public Long hlen(String key) {
        return this.executeJedis((shardedJedis)-> shardedJedis.hlen(key));
    }

    public String hget(String key, String field) {
        return this.executeJedis((shardedJedis)-> shardedJedis.hget(key, field));
    }

    public Long incr(String key) {
        return this.executeJedis((shardedJedis)-> shardedJedis.incr(key));
    }

    public Long decr(String key) {
        return this.executeJedis((shardedJedis)-> shardedJedis.decr(key));
    }

    private <T> T executeJedis(Function<ShardedJedis, T> function) {
        try (ShardedJedis shardedJedis = shardedJedisPool.getResource()) {
            T obj = function.apply(shardedJedis);
            shardedJedis.close();
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }
}
