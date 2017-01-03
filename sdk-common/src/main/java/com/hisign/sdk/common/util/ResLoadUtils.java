package com.hisign.sdk.common.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ResLoadUtils
 * @Description:
 * @author LIJIANYE
 * @date 2013年10月9日 下午3:40:40
 * 
 */
public class ResLoadUtils {

	private static final Logger log = LoggerFactory.getLogger(ResLoadUtils.class);

	/**
	 * 获取字段名
	 * 
	 * @param rs
	 * @return
	 */
	public static ArrayList<String> getColNameList(ResultSet rs) {
		ArrayList<String> colNameList = new ArrayList<String>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnNameSize = rsmd.getColumnCount();
			for (int j = 1; j <= columnNameSize; j++) {
				String colname = rsmd.getColumnLabel(j);
				colNameList.add(colname);
			}
		} catch (Exception ex) {
			log.error("get fieldName error", ex);
		}
		return colNameList;
	}

	/**
	 * 获取一条记录
	 * 
	 * @param rs
	 * @param colNameList
	 * @return
	 */
	public static Map<String, String> getColNameValueMap(ResultSet rs, ArrayList<String> colNameList) {
		Map<String, String> rowValueMap = new HashMap<String, String>();
		try {
			for (int j = 0, colsize = colNameList.size(); j < colsize; j++) {
				String colname = colNameList.get(j);
				String colstr = rs.getString(colname);
				if (colstr==null) {
					colstr = "";
				}
				rowValueMap.put(colname, colstr);
			}
		} catch (Exception ex) {
			log.error("get a row data error", ex);
		}
		return rowValueMap;
	}
}
