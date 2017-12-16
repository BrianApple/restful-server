package com.tl.cdz.server.test.domain;

import java.io.Serializable;

/**
 * 用于封装用户信息
 * @author yangcheng
 * @date 2017年12月15日 
 * @version V1.0
 */
public class User implements Serializable{
	private static final long serialVersionUID = 3629697696633810038L;
	private String userId;
	private String userName;
	private String age;
	//失效时间（缓存中value值存的是User对象 通过对象获取失效时间）
	private String expireTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
