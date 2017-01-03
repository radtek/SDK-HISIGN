/**
 * 
 */
package com.hisign.sdk.common;

/**
 * 系统常量
 * @author chailiangzhi
 * @date 2015-10-13
 * 
 */
public class Constant {
	
	public enum ErrorCode {
		// 利用构造函数传参
		ISNULL(10000, "不能为空"), //不能为空
		FORMATE_ERR(10001, "参数格式不正确"), //参数格式不正确
		OUT_MAXLENGTH(10002, "长度超出最大长度"), //长度超出最大长度
		SN_EXIST(11000, "流水号重复"), //流水号重复
		IMEI_NOT_ALLOW(11001, "imei不合法"), //imei不合法
		SYSCODE_NOT_ALLOW(11002, "授权码不合法"), //授权码不合法
		TOKEN_NOT_ALLOW(11003, "token不合法"), //token不合法
		TOKEN_TIMEOUT(11004, "token失效"), //token失效
		USER_ALLOW(11005, "非法接口访问，没有授权该接口"), //非法接口访问，没有授权该接口
		SUCCESS(100, "成功"),//成功
		SYSTEM_ERROR(20000, "系统内部错误");//系统内部错误
		

		// 错误代码
		private int errCode;

		//错误描述
		private String errDesc;

		public int getErrCode() {
			return errCode;
		}

		public void setErrCode(int errCode) {
			this.errCode = errCode;
		}

		public String getErrDesc() {
			return errDesc;
		}

		public void setErrDesc(String errDesc) {
			this.errDesc = errDesc;
		}

		// 构造函数，枚举类型只能为私有
		private ErrorCode(int _nCode, String errDesc) {
			this.errCode = _nCode;
			this.errDesc = errDesc;
		}
	}

	/**
	 * 用户开放标记
	 */
	public static final String USER_OPEN = "1";

	/**
	 * 客户端系统功能JSP目录
	 */
	public static final String JSP_SYS_DIR = "WEB-INF/jsp/sys/";

	/**
	 * 分页起始行
	 */
	public static final String SQL_PAGE_START_KEY = "start";

	/**
	 * 每页数据条数
	 */
	public static final String SQL_PAGE_SIZE_KEY = "size";
	
	/**
	 * 山东省行政区划代码
	 */
	public static final String SHANDONG_PRi="370000";
	
	/**
	 * 动态数据源-默认数据源
	 */
	public static final String DEFAULT_DATA_SOURCE_NAME = "dataSource";

	/**
	 * ORACLE数据库驱动
	 */
	public static final String ORACLE_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";

	/**
	 * 是否以WebService方式查询警综信息的配置键值
	 */
	public static final String KEY_QRY_JZ_BY_WEB_SERVICE = "qryJzByWebService";

	/**
	 * 获取警综信息的WebService实现类的配置键值
	 */
	public static final String KEY_JZ_WEB_SERVICE_IMPL_BEAN = "jzWebServiceImplBean";

	static public final byte HEX_DIGITS[] = {
        (byte) '0', (byte) '1', (byte) '2', (byte) '3',
        (byte) '4', (byte) '5', (byte) '6', (byte) '7',
        (byte) '8', (byte) '9', (byte) 'a', (byte) 'b',
        (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'
    };
	
	/**
	 * 新增现场
	 */
	public static final String SCENE_DATA_ADD="1";
	
	/**
	 * 修改现场
	 */
	public static final String SCENE_DATA_UPDATE="0";
	
	/**
	 * 响应失败
	 */
	public static final String RETURN_FAIL="0";
	
	/**
	 * 响应成功
	 */
	public static final String RETURN_SUCCESS="0";
	
    public static final String DELETE_FLAG="0";
	
	public static final String SAVE_FLAG="0";
	
	public static final String QUALIFIED_FLAG="0";
	
	public static final String FINISH_FLAG="0";
	
    public static final String MODIGY_FLAG="1";
	
	public static final String SECRECY="1";
	
	public static final String ITERATION_NO="0";
	
	/**
	 * 系统字符集编码
	 */
	public static final String CHARSET_NAME = "UTF-8";
	
	/**
	 * 图片默认缓存目录
	 */
	public static final String PIC_CACHE_DIR = "commPicCache";
	
}
