/**
 * 
 */
package com.hisign.sdk.api.entity;

import java.io.Serializable;

import com.hisign.sdk.api.enums.CacheKeyType;

/**
 * 应用使用的缓存键
 * @author chailiangzhi
 * @date 2016-5-12
 * 
 */
public class CacheKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 缓存键值
	 */
	private String key;

	/**
	 * 缓存键类型
	 */
	private CacheKeyType type;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the type
	 */
	public CacheKeyType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(CacheKeyType type) {
		this.type = type;
	}

}
