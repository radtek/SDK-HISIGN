package com.hisign.sdk.msg.util.zookeeper;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






/**
 * @Title:
 *   Zookeeper操作客户单
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 14, 2013  10:37:25 AM
 * @version 1.0
 */
public class ZookeeperClient{
	
	//日志
	private static Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);
	
	private CuratorFramework client;
	
	//zookeeper地址
	private String zk_connect;
	
	//统一命名空间
	private String namespace;
	
	//连接失败重试次数,默认3次
	private int retryCount = 3;
	
	//连接失败，重试时间间隔，默认1秒
	private int retryInterval = 1000;
	
	//字符集编码
	private String charSet = "utf-8";
	
	/**
	 * @param zk_connect  zookeeper地址
	 * @param namespace  命名空间
	 */
	public ZookeeperClient(String zk_connect,String namespace) throws Exception{
		this.zk_connect = zk_connect;
		this.namespace = namespace;
		this.open(zk_connect);
	}
	
	/**
	 * 打开zookeeper连接
	 * @param zk_connect zookeeper服务器地址例如:192.168.98.141:2181,192.168.98.142:2181
	 * @throws Exception
	 */
	public void open(String zk_connect) throws Exception{
		initZookeeper(zk_connect);
	}
	
	
	/**
	 * 获取指定路径数据
	 * @param path 树形结构路径以/分隔
	 * @return
	 * @throws Exception
	 */
	public String getData(String path) throws Exception{
		Stat stat = this.client.checkExists().forPath(path);
		if (stat == null){ //如果路径不存在，则返回空
			return null;
		}
		
		byte []dataBytes = this.client.getData().forPath(path);
		if(dataBytes != null){
			return new String(dataBytes,charSet);
		}
		return null;
	}

	/**
	 * 获取指定路径数据
	 * @param path 树形结构路径以/分隔
	 * @return
	 * @throws Exception
	 */
	public byte[] getDataBytes(String path) throws Exception{
		Stat stat = this.client.checkExists().forPath(path);
		if (stat == null){ //如果路径不存在，则返回空
			return null;
		}
		return this.client.getData().forPath(path);
	}
	
	/**
	 * 获取子节点列表
	 * @param parentPath
	 * @return
	 * @throws Exception
	 */
	public List<String> getChildren(String parentPath) throws Exception{
	    Stat stat = this.client.checkExists().forPath(parentPath);
        if (stat == null){ //如果路径不存在，则返回空
            return null;
        }
	    List<String> list =this.client.getChildren().forPath(parentPath);
	    return list;
	}

	/**
	 * 删除指定路径数据
	 * @param path 树形结构路径以/分隔
	 * @throws Exception
	 */
	public void delData(String path) throws Exception{
		this.client.delete().forPath(path);
	}

	/**
	 * 写入数据到指定路径中去
	 * @param path  树形结构路径以/分隔
	 * @param data  数据
	 * @throws Exception
	 */
	public void writeData(String path, String data) throws Exception{
		logger.debug("begin writeData path {},data {}",path,data);
		Stat stat = this.client.checkExists().forPath(path);
		if (stat == null){
			String crePath = this.client.create().creatingParentsIfNeeded().forPath(path);
			logger.debug("path {} not exist,create, data={}", crePath, data);
		}
		stat = this.client.setData().forPath(path, data.getBytes(charSet));
		if (stat == null){
			logger.error("write data error");
			throw new RuntimeException("write data error");
		}
		logger.debug("writeData path {},data {} cmpletely!",path,data);
	}

	/**
	 * 初始化zookeeper连接
	 * @param conf
	 * @throws Exception
	 */
	private void initZookeeper(String connectString) throws Exception{
		this.client = CuratorFrameworkFactory.builder().namespace(namespace).connectString(connectString).retryPolicy(new RetryNTimes(retryCount, retryInterval)).build();
		this.client.start();
	}
	
	/**
	 * 关闭zookeeper连接
	 */
	public void close() throws Exception{
		this.client.close();
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
