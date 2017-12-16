package com.tl.cdz.server.test.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tl.cdz.server.test.domain.User;
/**
 * 
 * 注意：
 *  1.缓存是通过 ConcurrentHashMap实现的（可换为其它的）
 *  2.当需要往缓存中存数据时  key是通过在注解的参数中通过springEL表达式指定的
 *  而value值就是方法的返回值——因此如果方法返回值为void 则缓存中的value值就是null
 * @author yangcheng
 * @date 2017年12月15日 
 * @version V1.0
 */
@Repository
public class TestDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Map<String, Object> test() {
		String sqlString="select count(*) from classes";
		
		System.out.println("chenggong............");
		return jdbcTemplate.queryForMap(sqlString);
	}


	public void update() {
		jdbcTemplate.update("insert into classes values(123,'002')");
		
	}

	/**
	 * 根据用户id获取用户名称
	 * 
	 * '@Cacheable(value="userCache")'，这个注释的意思是:
	 *  当调用这个方法的时候，会从一个名叫 userCache的缓存中查询(缓存的配置在配置文件中指定)，如果没有
	 *  则执行实际的方法（即查询数据库），并将执行的结果存入缓存中，否则返回缓存中的对象。
	 * ——通过将结果存到缓存中 这样子不用每次都到数据库中获取了！！！ 
	 */
	@Cacheable(value="userCache")
	public User getUser(String userId) {
		User user =new User();
		Map<String, Object> map=jdbcTemplate.queryForMap("select * from user where userId=?", userId);
		System.out.println("从数据库中获取了....");
		user.setUserId(map.get("userId").toString());
		user.setUserName(map.get("name").toString());
		user.setAge(map.get("age").toString());
		return user;
	}
	/**
	 * 添加/修改数据库中用户信息 并将其信息存到缓存中
	 * 
	 * '@Cacheable(value="userCache")'注解标注的方法  如果查询的内容在缓存中存在的话  方法中的业务代码不会再次执行的
	 * 如果需要被标注的方法无论缓存中是否存在相应值 方法中的业务代码都执行，就需要使用@CachePut注解！
	 * 
	 */
	@CachePut(value="userCache",key="#user.userId")
	public User putCache(User user){
		/**
		 * 方法内部业务每次都会执行
		 * 无论用户userId的值在缓存key中是否存在  方法内部业务都会执行，执行完之后结果又会放到缓存中（存在的会被覆盖）
		 */
		System.out.println("@CachePut标注的方法执行！");
		int i=jdbcTemplate.queryForList("select * from user where userId=?", user.getUserId()).size();
		if (i==0) {
			//当不存在该用户 插入用户信息
			jdbcTemplate.update("insert into  user (userId,name,age) values (?,?,?)",user.getUserId(), user.getUserName(),user.getAge());
		} else {
			//当存在该用户 修改相应信息
			jdbcTemplate.update("update user set name=?,age=? where userId=?", user.getUserName(),user.getAge(),user.getUserId());
		}
		
		return user;
		
	}
	
	
	
	/**
	 * 注解CacheEvict中  value属性指定缓存名称,key值通过springEL表达式指定  ‘#’后面指定属性
	 * Key 是用来指定缓存中的 key(缓存是通过ConcurrentHashMap实现的)的
	 * @Title: clearCache 
	 * @author yangcheng
	 * @param userId
	 */
	//@CacheEvict(value="accountCache",allEntries=true)// '清空'accountCache 缓存
	@CacheEvict(value="userCache",key="#userId")
	public void clearCache(String userId) {
		
	}
}
