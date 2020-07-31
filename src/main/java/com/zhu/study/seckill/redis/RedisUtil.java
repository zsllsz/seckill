package com.zhu.study.seckill.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


/**
 * Redis工具类
 * @author zhusl 
 * @date 2020年7月22日  下午2:55:11
 *
 */
@Component
@SuppressWarnings("unchecked")
public class RedisUtil {
	
	private RedisUtil() {}

	@SuppressWarnings("rawtypes")
	private static RedisTemplate redisTemplate;
	
	// 静态注入
	@Autowired
	private void setRedisTemplate(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
		/**
		 * 主要的序列化类：
		 * StringRedisSerializer()：序列化成字符串
		 * GenericJackson2JsonRedisSerializer()：引用类型会序列化成json，get时可直接用对应的Bean接收
		 * Jackson2JsonRedisSerializer()：引用类型会序列化成json，get时只能用LinkHashMap接收
		 * JdkSerializationRedisSerializer()：会进行编码后再存储，即在redis-cli中用命令get到的是一堆乱码。在java中get时可用对应对象接收，不过该对象需要实现序列化接口
		 */
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		RedisUtil.redisTemplate = redisTemplate;
	}
	
	
	
	/**
	 * 给key设置value，value可以是任意类型，不管什么类型都会序列化成字符串后进行保存
	 * @param key String类型
	 * @param value 任意类型
	 */
	public static void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	
	/**
	 * 给key设置value，并设置过期时间
	 * @param key String类型
	 * @param value 任意类型
	 * @param timeout 过期时间
	 * @param timeunit 时间单位
	 */
	public static void set(String key, Object value, Long timeout, TimeUnit timeunit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeunit);
	}
	
	/**
	 * 自减操作
	 * @param key
	 * @return
	 */
	public static Long decr(String key) {
		return redisTemplate.opsForValue().decrement(key);
	}
	
	
	/**
	 * 获取key-value
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public static boolean isExist(String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	 * 删除key
	 * @param key
	 * @return
	 */
	public static boolean delKey(String key) {
		return redisTemplate.delete(key);
	}

}
