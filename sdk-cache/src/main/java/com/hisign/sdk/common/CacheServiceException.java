/**
 * 
 */
package com.hisign.sdk.common;

/**
 * 异常类,便于调用者区分业务异常来源
 * @author chailiangzhi
 * @date 2015-9-29
 * 
 */
public class CacheServiceException extends RuntimeException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @param message
	 */
	public CacheServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数
	 * @param message
	 * @param cause
	 */
	public CacheServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
