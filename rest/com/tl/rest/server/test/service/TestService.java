package com.tl.rest.server.test.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tl.rest.server.test.dao.TestDao;
import com.tl.rest.server.test.domain.User;

@Service
public class TestService {
	@Autowired
	TestDao testDao;
	
	@Transactional
	public Map<String, Object> test() {
		// TODO Auto-generated method stub
		testDao.update();
//		int i=1/0;
		return testDao.test();
	}
	/**
	 * 获取用户
	 * @Title: getUser 
	 * @author yangcheng
	 * @param userId
	 * @return 
	 */
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		
		User user=testDao.getUser(userId);
		return user;
	}
	/**
	 * 添加/修改数据库中用户信息 并将其信息存到缓存中
	 * @Title: putCache 
	 * @author yangcheng
	 */
	@Transactional
	public User putCache(User user) {
		testDao.putCache(user);
		return null;
	}
	/**
	 * 清空指定用户id对应的缓存信息
	 * @Title: clearCache 
	 * @author yangcheng
	 * @param userId
	 * @return
	 */
	public User clearCache(String userId) {
		testDao.clearCache(userId);
		return null;
	}

}
