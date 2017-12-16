package com.tl.rest.server.filters.dao;

public interface IUtilDao {
	/**
	 * 判断是否包含被访问的接口名
	 * @Title: hasUrlName 
	 * @author yangcheng
	 * @param methodUrlName
	 * @return 包含返回true  反之返回false
	 */
	boolean hasUrlName(String methodUrlName);
	/**
	 * 查询指定token是否存在同时判断令牌是否失效
	 * @Title: existsToken 
	 * @author yangcheng
	 * @param token
	 * @return
	 */
	String existsToken(String token);

}
