package com.doopp.youlin.server.configuration;

import com.doopp.youlin.server.util.ShardedJedisHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfiguration {

    @Bean(name = "redisCache")
    public ShardedJedisHelper redisCache(@Value("${redis.cache.servers}") String redisServers, JedisPoolConfig jedisPoolConfig) {
        return new ShardedJedisHelper(redisServers, jedisPoolConfig);
    }

    @Bean(name = "sessionCache")
    public ShardedJedisHelper sessionCache(@Value("${redis.session.servers}") String redisServers, JedisPoolConfig jedisPoolConfig) {
        return new ShardedJedisHelper(redisServers, jedisPoolConfig);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPassword("");
        jedisConnectionFactory.setDatabase(7);
        jedisConnectionFactory.setTimeout(2000);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory connectionFactory)  {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(86400L);
        return redisCacheManager;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(@Value("${redis.pool.maxTotal}") int maxTotal,
                                           @Value("${redis.pool.maxIdle}") int maxIdle,
                                           @Value("${redis.pool.minIdle}") int minIdle,
                                           @Value("${redis.pool.maxWaitMillis}") int maxWaitMillis,
                                           @Value("${redis.pool.lifo}") boolean lifo,
                                           @Value("${redis.pool.testOnBorrow}") boolean testOnBorrow) {
        // Jedis池配置
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大分配的对象数
        config.setMaxTotal(maxTotal);
        // 最大能够保持idel状态的对象数
        config.setMaxIdle(maxIdle);
        // 最小空闲的对象数。2.5.1以上版本有效
        config.setMinIdle(minIdle);
        // 当池内没有返回对象时，最大等待时间
        config.setMaxWaitMillis(maxWaitMillis);
        // 是否启用Lifo。如果不设置，默认为true。2.5.1以上版本有效
        config.setLifo(lifo);
        // 当调用borrow Object方法时，是否进行有效性检查
        config.setTestOnBorrow(testOnBorrow);
        // return
        return config;
    }
}
