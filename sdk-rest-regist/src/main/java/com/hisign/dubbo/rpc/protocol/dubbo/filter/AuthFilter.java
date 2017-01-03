/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hisign.dubbo.rpc.protocol.dubbo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * AuthFilter
 * 
 * @author william.liangf
 */
@Activate(group = Constants.PROVIDER)
public class AuthFilter implements Filter {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	/* (non-Javadoc)
	 * @see com.alibaba.dubbo.rpc.Filter#invoke(com.alibaba.dubbo.rpc.Invoker, com.alibaba.dubbo.rpc.Invocation)
	 */
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		long start = System.currentTimeMillis();
		URL url = invocation.getInvoker().getUrl();
		String interfaceName = url.getServiceInterface();
		String methods = url.getParameter("methods");
		String method = invocation.getMethodName();
		logger.info("clz,interfaceName:{},methods:{},method:{}", interfaceName, methods, method);
//		String urlStr = url.toString();
		Result result = invoker.invoke(invocation);
		long end = System.currentTimeMillis();
		logger.info("clz,start:{},end:{}", start, end);
		return result;
	}

}