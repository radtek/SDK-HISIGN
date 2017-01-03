package com.hisign.sdk.config.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hisign.sdk.config.datasource.SQLUtil;

/**
 * @Title:
 *  数据库连接抽象类
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 20, 2013 10:31:16 AM
 * @version 1.0
 */
public class AbstractDAO {

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 * @throws SQLException
	 */
	protected void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (Exception e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 取得数据库接连
	 */
	protected Connection getConnection() {
		return SQLUtil.getInstance().getConnection();
	}

	/**
	 * 判断某张表是否存在
	 * 
	 * @param tablename
	 * @return
	 */
	protected boolean hasTable(String tablename) {
		if (tablename == null || tablename.length() == 0)
			return false;
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sqlStr = "SELECT * FROM  " + tablename.trim();

		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sqlStr);

			rs = stmt.executeQuery();
			result = true;
		} catch (Exception e) {
		} finally {
			this.closeConnection(conn, stmt, rs);
		}
		return result;
	}

}
