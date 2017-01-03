package com.hisign.sdk.msg.util.zookeeper;

import java.util.Iterator;
import java.util.List;

import com.hisign.sdk.common.util.StringUtils;


/**
 * @Title:
 *  zookeeper连接串组装器
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 14, 2013  11:17:29 PM
 * @version 1.0
 */
public class ZkConnectStringAssemble {
	
	/**
	 * 获取zookeeper连接串
	 * @param zkServers
	 * @param zkPort
	 * @return
	 * @throws Exception
	 */
	public static String getZkConnectString(List<String> zkServers,String zkPort){
		if(zkServers == null || zkServers.size() <= 0 || StringUtils.isEmpty(zkPort)){
			return null;
		}
		
		Iterator<String> it = zkServers.iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()){
			sb.append(it.next());
			sb.append(":");
			sb.append(zkPort);
			if (it.hasNext()){
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
