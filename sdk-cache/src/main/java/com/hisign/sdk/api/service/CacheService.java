/**
 * 
 */
package com.hisign.sdk.api.service;

import com.hisign.sdk.api.entity.CacheKey;

/**
 * 缓存访问接口
 * @author chailiangzhi
 * @date 2015-9-19
 * 
 */
public interface CacheService {

	/**
	 * 存放到缓存
	 * @param key
	 * @param value
	 * @param t
	 * @return
	 */
	public <T> boolean put(CacheKey key, T value, Class<T> t);

	/**
	 * 从缓存取数据
	 * @param key
	 * @param t
	 * @return
	 */
	public <T> T get(CacheKey key, Class<T> t);
}
