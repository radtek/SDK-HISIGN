package com.hisign.sdk.cache.ercache;

import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

import com.hisign.sdk.cache.redis.RedisClient;

/**
 * 两级缓存，一级:ehcache,二级为redisCache
 * 
 * @author yulin
 *
 */
@Component("ehRedisCache")
public class EhRedisCache implements Cache {

	private static final Logger log = LoggerFactory.getLogger(EhRedisCache.class);

	private String name;

	/*** 一定容量的LRU队列 */
	private net.sf.ehcache.Cache ehCache;

	@Autowired
	private RedisClient redisClient;

	private int activeCount = 10;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		return this;
	}

	@Override
	public ValueWrapper get(Object key) {
		String keyStr = key.toString();
		Element value = ehCache.get(keyStr);
		log.info("Cache L1 (ehcache) :{}={}", keyStr, value);
		if (value != null) {
			// TODO 访问10次EhCache 强制访问一次redis 使得数据不失效
			if (value.getHitCount() < activeCount) {
				return (value != null ? new SimpleValueWrapper(value.getObjectValue()) : null);
			} else {
				value.resetAccessStatistics();
			}
		}

		Object obj = this.redisClient.getObj(keyStr);
		this.ehCache.put(new Element(key, obj));// 取出来之后缓存到本地
		log.info("Cache L2 (redis) :{}={}", key, obj);
		
		return (obj != null ? new SimpleValueWrapper(obj) : null);
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		String keyStr = key.toString();
		Element value = ehCache.get(keyStr);
		log.info("Cache L1 (ehcache) :{}={}", keyStr, value);
		if (value != null) {
			// TODO 访问10次EhCache 强制访问一次redis 使得数据不失效
			if (value.getHitCount() < activeCount) {
				return (value != null ? (T) value.getObjectValue() : null);
			} else {
				value.resetAccessStatistics();
			}
		}

		T obj = this.redisClient.getObj(keyStr, type);
		this.ehCache.put(new Element(key, obj));// 取出来之后缓存到本地
		log.info("Cache L2 (redis) :{}={}", key, obj);
		return obj;
	}

	@Override
	public void put(Object key, Object value) {
		log.info("Cache put :{}={}", key, value);
		final String keyStr = key.toString();
		this.redisClient.setObj(keyStr, value);
		this.ehCache.put(new Element(keyStr, value));
	}

	@Override
	public void evict(Object key) {
		log.info("Cache evict :{}", key);
		final String keyStr = key.toString();
		this.ehCache.remove(keyStr);
	}

	@Override
	public void clear() {
		ehCache.removeAll();
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}

	public net.sf.ehcache.Cache getEhCache() {
		return ehCache;
	}

	public void setEhCache(net.sf.ehcache.Cache ehCache) {
		this.ehCache = ehCache;
	}

}
