package com.mercury.chat.user.service.storer.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {
	
	private static JedisPool pool;
   
    private static void createJedisPool() {

        JedisPoolConfig config = new JedisPoolConfig();
        
        config.setMaxTotal(100);

        config.setMaxWaitMillis(1000);

        config.setMaxIdle(10);

        pool = new JedisPool(config, "192.168.1.104", 6379);

    }

    private static synchronized void poolInit() {
        if (pool == null)
            createJedisPool();
    }

    public static Jedis getJedis() {
        if (pool == null)
            poolInit();
        return pool.getResource();
    }

    public static void returnRes(Jedis jedis) {
        pool.returnResource(jedis);
    }
}
