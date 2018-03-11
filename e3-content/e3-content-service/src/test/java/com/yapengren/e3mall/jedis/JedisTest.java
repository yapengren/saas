package com.yapengren.e3mall.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

/**
 * @author renyapeng
 */
public class JedisTest {

    /**
     * 测试 jedis 单机版
     */
    @Test
    public void testJedis() {
        // 创建一个 jedis 对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 直接使用 jedis 对象管理redis
        jedis.set("name", "zhangsan");
        String name = jedis.get("name");
        System.out.println(name);
        // 关闭 jedis
        jedis.close();
    }

    /**
     * 测试 jedis 连接单机版使用连接池
     */
    @Test
    public void testJedisPool() {
        // 创建一个 jedisPool 对象，需要指定服务端的 ip 及端口
        JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
        // 从 jedisPool 中获得 jedis 对象
        Jedis jedis = jedisPool.getResource();
        // 使用 jedis 操作 redis 服务器
        jedis.set("age", "10");
        String age = jedis.get("age");
        System.out.println(age);
        // 关闭 jedis 对象
        jedis.close();
        // 关闭 jedisPool 对象
        jedisPool.close();
    }

    /**
     * 测试 redis 集群版
     */
    @Test
    public void testJedisCluster() {
        HashSet<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("192.168.25.128", 7001));
        hostAndPorts.add(new HostAndPort("192.168.25.128", 7002));
        hostAndPorts.add(new HostAndPort("192.168.25.128", 7003));
        hostAndPorts.add(new HostAndPort("192.168.25.128", 7004));
        hostAndPorts.add(new HostAndPort("192.168.25.128", 7005));
        hostAndPorts.add(new HostAndPort("192.168.25.128", 7006));

        // 使用 jedisCluster 对象操作 redis，在系统中单例存在
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        jedisCluster.set("name", "wangwu");
        String name = jedisCluster.get("name");

        // 打印结果
        System.out.println(name);
        // 系统关闭，关闭 jedisCluster 对象
        jedisCluster.close();
    }
}
