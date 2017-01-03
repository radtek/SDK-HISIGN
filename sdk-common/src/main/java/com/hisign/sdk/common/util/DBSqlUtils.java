package com.hisign.sdk.common.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Title:
 *   数据库sql工具类
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2013年10月12日  下午4:43:42
 * @version 1.0
 */
public class DBSqlUtils {
	
	/** boolean型数据的数据库值 */
	public static int FALSE = 0;
	public static int TRUE = 1;
	
	/**
	 * 用数字代替Boolean类型,设置PreparedStatement的字段
	 * 
	 */
	public static void setBoolean(PreparedStatement stmt, int parameterIndex, boolean value) throws Exception {
		if (value == true) {
			stmt.setInt(parameterIndex, TRUE);
		} else {
			stmt.setInt(parameterIndex, FALSE);
		}
	}
	
	/**
	 * 按参数名称取出PreparedStatement的用整数代替的boolean类型值
	 * 
	 * @param rst
	 * @param parameterName
	 * @return
	 * @throws Exception
	 */
	public static boolean getBoolean(ResultSet resultSet, String parameterName) throws Exception {
		int value = resultSet.getInt(parameterName);
		if (value == TRUE) {
			return true;
		}
		return false;
	}

}
