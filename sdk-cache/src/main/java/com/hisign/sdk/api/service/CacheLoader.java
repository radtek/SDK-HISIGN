/**
 * 
 */
package com.hisign.sdk.api.service;

import java.util.concurrent.Callable;

import com.hisign.sdk.api.entity.CacheKey;

/**
 * 从其他介质加载数据
 * @author chailiangzhi
 * @date 2016-6-13
 * 
 */
public interface CacheLoader extends Callable<Object>, Cloneable {

	/**
	 * 初始化加载类
	 * @param key
	 * @param realKey
	 */
	public void init(CacheKey key, String realKey);
}
