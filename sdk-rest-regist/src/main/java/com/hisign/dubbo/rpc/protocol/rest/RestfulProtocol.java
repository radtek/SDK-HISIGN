/**
 * 
 */
package com.hisign.dubbo.rpc.protocol.rest;

import org.jboss.resteasy.spi.metadata.ResourceBuilder;
import org.jboss.resteasy.spi.metadata.ResourceClass;
import org.jboss.resteasy.spi.metadata.ResourceMethod;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.rest.RestProtocol;

/**
 * @author chailiangzhi
 * @date 2016-6-22
 * 
 */
public class RestfulProtocol extends RestProtocol {

	/**
	 * 
	 */
	private final org.slf4j.Logger log4j = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see com.alibaba.dubbo.rpc.protocol.rest.RestProtocol#doExport(java.lang.Object, java.lang.Class, com.alibaba.dubbo.common.URL)
	 */
	@Override
	protected <T> Runnable doExport(T impl, Class<T> type, URL url) throws RpcException {
		// TODO Auto-generated method stub
		String addr = url.getIp() + ":" + url.getPort();
		Class restClass = type;
		ResourceClass resourceClass = ResourceBuilder.rootResourceFromAnnotations(restClass);
		for (ResourceMethod method : resourceClass.getResourceMethods()) {
			String contextPath = getContextPath(url);
			String reqPath = method.getFullpath();
			String fullPath = contextPath + "/" + reqPath;
			String serviceCode = reqPath.replaceAll("/", ".");
			String reqUrl = "http://" + addr + "/" + fullPath;
			System.err.println("reqUrl=" + reqUrl + ",serviceCode=" + serviceCode + "");
			log4j.info("reqUrl={},serviceCode={}", reqUrl, serviceCode);
			//	         processMethod(method);
		}
		return super.doExport(impl, type, url);
	}

}
