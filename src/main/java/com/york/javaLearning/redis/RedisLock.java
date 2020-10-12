package com.york.javaLearning.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author york
 * @create 2020-10-12 17:09
 **/
public class RedisLock {

    private JedisPool jedisPool;

    public RedisLock() {
        jedisPool = new JedisPool(new GenericObjectPoolConfig(), "127.0.0.1", 6379, 10, "123456");
    }

    public boolean lock(String key) {
        Jedis jedis = jedisPool.getResource();
        boolean locked = jedis.set(key, "lock", "nx", "ex", 5L) != null;
        // 释放资源，否则会死锁
        jedis.close();
        return locked;
    }

    public void unlock(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }

    public static void main(String[] args) throws InterruptedException {
        RedisLock redisLock = new RedisLock();
        Thread thread2 = new Thread(() -> {
            boolean locked = redisLock.lock("lock1");
            if (locked) {
                System.out.println(Thread.currentThread() + "---获取到锁");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisLock.unlock("lock1");
                System.out.println(Thread.currentThread() + "---释放锁");
            }

        });

        Thread thread1 = new Thread(() -> {
            boolean locked = redisLock.lock("lock1");
            while (!locked) {
                System.out.println(Thread.currentThread() + "---获取不到锁");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                locked = redisLock.lock("lock1");
            }
            System.out.println(Thread.currentThread() + "---获取到锁");
            redisLock.unlock("lock1");
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
