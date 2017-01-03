/**
 * 
 */
package com.hisign.sdk.common.util;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.aes.AESCoder;

/**
 * 加解密工具类
 * @author chailiangzhi
 * @date 2016-5-24
 * 
 */
public class EncryptUtil {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger("");

	/**
	 * 加密
	 * @param reqMsg
	 */
	public static String encryptMsg(JSONObject reqJson, String secretKey) throws IOException {
		// 加密
		String encryptField = "rspBody";
		logger.info("secretKey is:{}", secretKey);
		String reqBodyPlain = reqJson.optString(encryptField);
		String reqBodyEn = encryptMsg(reqBodyPlain, secretKey);
		logger.info("reqBodyEn is:{}", reqBodyEn);
		reqJson.put(encryptField, reqBodyEn);
		String reqMsg = reqJson.toString();
		return reqMsg;
	}

	/**
	 * 加密
	 * @param reqMsg
	 */
	public static String encryptMsg(String msgPlain, String secretKey) throws IOException {
		// 加密
		logger.info("secretKey is:{}", secretKey);
		if (StringUtils.isNull(secretKey)) {
			return msgPlain;
		}
		if (StringUtils.isNull(msgPlain)) {
			return msgPlain;
		}
		String msgEn = AESCoder.encrypt(msgPlain, secretKey);
		logger.info("msgEn is:{}", msgEn);
		return msgEn;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
