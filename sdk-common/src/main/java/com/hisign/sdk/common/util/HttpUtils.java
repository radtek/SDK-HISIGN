/**
 * 
 */
package com.hisign.sdk.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.Constant;

/**
 * HTTP工具类
 * @author chailiangzhi
 * @date 2016-12-7
 * 
 */
public class HttpUtils {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/**  
	* 向指定URL发送POST方法的请求  
	*   
	* @param url  
	*            发送请求的URL  
	* @param param  
	*            请求参数。  
	* @return URL所代表远程资源的响应  
	*/
	public static String sendPost(String url, String param) {
		OutputStream out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接  
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性  
			//			conn.setRequestProperty("accept", "*/*");
			//			conn.setRequestProperty("connection", "Keep-Alive");
			//			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行  
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流  
			out = conn.getOutputStream();
			// 发送请求参数  
			out.write(param.getBytes(Constant.CHARSET_NAME));
			// flush输出流的缓冲  
			out.flush();
			// 读取URL的响应  
			byte[] buffer = toByteArray(conn.getInputStream());
			result = new String(buffer, Constant.CHARSET_NAME);
		} catch (Exception e) {
			logger.error("发送POST请求出现异常！", e);
		} finally {
			// 使用finally块来关闭输出流、输入流  
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("关闭流异常！");
			}
		}
		return result;
	}

	/**  
	* 向指定URL发送GET方法的请求  
	*   
	* @param url  
	*            发送请求的URL  
	
	* @return URL所代表远程资源的响应  
	*/
	public static String sendGet(String url) {
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接  
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性  
			//			conn.setRequestProperty("accept", "*/*");
			//			conn.setRequestProperty("connection", "Keep-Alive");
			//			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送GET请求设置如下
			conn.setDoInput(true);

			// 读取URL的响应  
			byte[] buffer = toByteArray(conn.getInputStream());
			result = new String(buffer, Constant.CHARSET_NAME);
		} catch (Exception e) {
			logger.error("发送GET请求出现异常！", e);
		} finally {
			// 使用finally块来关闭输出流、输入流  
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error("关闭流异常！");
			}
		}
		return result;
	}

	/**
	 * @param input
	 * @return
	 * @throws IOException
	 */
	private static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int n = 0;
		byte[] buffer = new byte[1024];
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}

	/**
	 * 指定方法访问URL
	 * @param urlStr
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public static String simpleHttp(String urlStr, String method) throws Exception {
		// 统一资源
		URL url = new URL(urlStr);
		// 连接类的父类，抽象类
		URLConnection urlConnection = url.openConnection();
		// http的连接类
		HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
		// 设定请求的方法，默认是GET
		httpURLConnection.setRequestMethod(method);
		// 设置字符编码
		httpURLConnection.setRequestProperty("Charset", "UTF-8");
		// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
		httpURLConnection.connect();
		InputStream in = httpURLConnection.getInputStream();
		byte[] buf = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = in.read(buf);
		while (len != -1) {
			baos.write(buf, 0, len);
			len = in.read(buf);
		}
		in.close();
		return baos.toString("UTF-8");
	}
}
