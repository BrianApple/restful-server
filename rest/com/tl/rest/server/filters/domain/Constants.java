package com.tl.rest.server.filters.domain;

/**
 * @author chance
 * @date 2017年10月26日上午11:52:02
 */
public class Constants {

	/**
	 * 参数字段对应的名称
	 */
	public final static String OperatorID = "operatorID";
	public final static String Data = "data";
	public final static String TimeStamp = "timeStamp";
	public final static String Seq = "seq";
	public final static String Sig = "sig";
	
	public final static String Ret = "ret";
	public final static String Msg = "msg";
	
	/**
	 * 充点设备名称设备前缀
	 */
	public final static String GunNamePrefix = "枪";
	/**
	 * 坐标精度
	 */
	public final static String CoordinateFormat = "0.000000000";
	
	public final static int CoordinateScale = 9;

	public final static String DEFAULT_CREATER = "system";
	
	/**
	 * 数据库表参数
	 * 
	 * @author chance
	 *
	 */
	public interface Db {

		/**
		 * 接口类型，01-发送，02-接收
		 */
		public final static String I_CONFIG_TYPE_SEND = "01";
		public final static String I_CONFIG_TYPE_CALL = "02";
	}

}

