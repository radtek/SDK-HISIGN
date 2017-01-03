package com.hisign.sdk.msg.util.zookeeper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.StringUtils;

/**
 * @Title:
 *   Zookeeper客户端工厂类
 * @description:
 * 
 * @author lnj 
 * @create time：2016年7月13日  上午10:16:53
 */
public class ZookeeperClientFactory {
	
	//日志
	private static Logger log = LoggerFactory.getLogger(ZookeeperClientFactory.class);
	
	//zookeeper客户端端操作缓存
	private Map<String,ZookeeperClient> clientMap = new ConcurrentHashMap<String,ZookeeperClient>();
	
	private static ZookeeperClientFactory instance = new ZookeeperClientFactory();
	
	
	public static ZookeeperClientFactory getInstace(){
		return instance;
	}
	
	private void ZookeeperClientFactory(){
	}
	
	/**
	 * 获取zookeeper客户端操作对象
	 * @param zk_connect
	 * @param namespace
	 * @return
	 */
	public ZookeeperClient getZKClient(String namespace){
		String zkConnect = System.getProperty("zkconnect");
		return this.getZKClient(zkConnect, namespace);
	}
	
	
	/**
	 * 获取zookeeper客户端操作对象
	 * @param zk_connect
	 * @param namespace
	 * @return
	 */
	public ZookeeperClient getZKClient(String zk_connect,String namespace){
		if(StringUtils.isEmpty(zk_connect)){
			throw new RuntimeException("zk_connect can't be null");
		}
		
		if(StringUtils.isEmpty(namespace)){
			throw new RuntimeException("namespace can't be null");
		}
		
		String key = this.getKey(zk_connect, namespace);
		if(!this.clientMap.containsKey(key)){ //如果之前没创建过
			this.buildZKClient(zk_connect, namespace);
		}
		
		return this.clientMap.get(key);
	}
	
	
	/**
	 * 构建Zookeeper客户端
	 * @param zk_connect
	 * @param namespace
	 */
	private synchronized void buildZKClient(String zk_connect,String namespace){
		try {
			ZookeeperClient zkClient =  new ZookeeperClient(zk_connect, namespace);
			String key = this.getKey(zk_connect, namespace);
			this.clientMap.put(key, zkClient);
		} catch (Throwable tr) {
			log.error("buildZKClient zk_connect="+zk_connect+",namespace="+namespace+" catch an exception",tr);
			throw new RuntimeException(tr);
		}
		
	}
	
	
	/**
	 * 组装key值
	 * @param zk_connect
	 * @param namespace
	 * @return
	 */
	private String getKey(String zk_connect,String namespace){
		String key = zk_connect+"/"+namespace;
		key = key.toLowerCase();
		return key;
	}

}
