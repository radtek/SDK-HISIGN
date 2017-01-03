/**
 * 
 */
package com.hisign.sdk.common.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 锁工具类
 * @author chailiangzhi
 * @date 2016-11-30
 * 
 */
public class LockUtils {

	/**
	 * 获取zookeeper共享锁
	 * @param zkAddress
	 * @param path
	 * @return
	 */
	public static InterProcessMutex zkLock(String zkAddress, String path) {
		CuratorFramework client = CuratorFrameworkFactory.newClient(zkAddress, new ExponentialBackoffRetry(2000, 3));
		client.start();
		InterProcessMutex lock = new InterProcessMutex(client, path);
		return lock;
	}
}
