/**
 * 
 */
package com.hisign.sdk.fdfs.service;

import java.io.InputStream;

/**
 * FastDFS服务
 * @author chailiangzhi
 * @date 2016-6-23
 * 
 */
public interface FdfsService {

	/**
	 * 上传文件的方法
	 * @param in
	 * @param size
	 * @return
	 */
	public String upload(InputStream in, long size) throws Exception;

	/**
	 * 生成通过NGINX访问FastDFS文件的TOKEN
	 * @param fileNameRemote
	 * @return
	 */
	public String genToken(String fileNameRemote) throws Exception;
}
