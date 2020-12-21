package com.york.javaLearning.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author york
 * @create 2020-10-14 11:32
 **/
public class RedisRateLimit {

    private JedisPool jedisPool;

    public RedisRateLimit() {
        jedisPool = new JedisPool(new GenericObjectPoolConfig(), "127.0.0.1", 6379, 10, "123456");
    }

    public void test() {
        jedisPool.getResource().publish("cc","ss");
        Jedis jedis = jedisPool.getResource();
        jedis.substr("cc",0,-1);

    }
}
