package com.tl.cdz.server.test.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tl.cdz.server.test.domain.User;
import com.tl.cdz.server.test.service.TestService;
import com.tl.cdz.server.uitl.commonutils.AESUtil;
import com.tl.cdz.server.uitl.commonutils.RequestParam;
/**
 * 测试入口:
 *  地址 http://localhost:8080/mysmallservice/evcs/v0.1/test/hello
 *  测试框架是否搭建成功
 *  测试事务是否起作用
 * @author yangcheng
 * @date 2017年12月4日 
 * @version V1.0
 */
@RequestMapping("/test")
@Controller
public class TestController {
	@Autowired
	TestService testService;
	
	/**
	 * 获取用户（若缓存中存在就从缓存中获取 不存在就从数据库获取）
	 * @Title: test 
	 * @author yangcheng
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getUser",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> test(HttpServletRequest request, HttpServletResponse response){
		String auth=request.getHeader("Authorization");
		
		String paramString=(String) request.getAttribute("paramString");
		RequestParam requestParam=JSON.parseObject(paramString, RequestParam.class);
		String data=requestParam.getData();
		String data_decrypt="";
		try {
			//解密数据
			data_decrypt = AESUtil.Decrypt(data);
		} catch (Exception e) {
			//如有异常表示解密失败，请做相应处理
			e.printStackTrace();
		}
		System.out.println("获取到加密之后的data解密之后的值为："+data_decrypt);
		/*
		 * 获取模拟表单方式传递过来的参数
		 * Enumeration en = request.getParameterNames();  
	        while (en.hasMoreElements()) {  
	            String paramName = (String) en.nextElement();  
	            String paramValue = request.getParameter(paramName);  
	            if("data".equals(paramName)){
	            	try {
	            		//解密被加密的数据（未被加密的数据不需要解密）
						String data_decrypt=AESUtil.Decrypt(paramValue);
						System.out.println("加密之后的数据解密完成："+paramName+":"+data_decrypt);
			            Map m=JSON.parseObject(data_decrypt);
			            System.out.println("获取JSON转map对象中指定key的值"+m.get("LastQueryTime"));
						continue;
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }
	            
	        }
	      */
		User user=JSON.parseObject(data_decrypt, User.class);
		/**
		 * 根据用户id获取用户
		 */
		User cUser= testService.getUser(user.getUserId());
		System.out.println("控制层方法执行完成！");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("data", cUser);
		return map;
	}
	/**
	 * 无论缓存中是否存在  都执行查数据库的方法 并将查询结果存放到缓存中
	 * 如果已经存在对应userId的缓存  覆盖之
	 * @Title: putCache 
	 * @author yangcheng
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/putCache")
	@ResponseBody
	public String putCache(HttpServletRequest request){
		String paramString=(String) request.getAttribute("paramString");
		RequestParam requestParam=JSON.parseObject(paramString, RequestParam.class);
		String data=requestParam.getData();
		String data_decrypt="";
		try {
			//解密数据
			data_decrypt = AESUtil.Decrypt(data);
		} catch (Exception e) {
			//如有异常表示解密失败，请做相应处理
			e.printStackTrace();
		}
		System.out.println("获取到data"+data_decrypt);
		User user=JSON.parseObject(data_decrypt, User.class);
		/**
		 * 添加/修改数据库中用户信息 并将其信息存到缓存中
		 */
		User cUser= testService.putCache(user);
		return "success";
	}
	/**
	 * 清除缓存中对应userId的信息
	 * @Title: clearCache 
	 * @author yangcheng
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/clearCache")
	@ResponseBody
	public String clearCache(HttpServletRequest request){
		String paramString=(String) request.getAttribute("paramString");
		RequestParam requestParam=JSON.parseObject(paramString, RequestParam.class);
		String data=requestParam.getData();
		String data_decrypt="";
		try {
			//解密数据
			data_decrypt = AESUtil.Decrypt(data);
		} catch (Exception e) {
			//如有异常表示解密失败，请做相应处理
			e.printStackTrace();
		}
		System.out.println("获取到data"+data_decrypt);
		User user=JSON.parseObject(data_decrypt, User.class);
		/**
		 * 清空指定用户id对应的缓存
		 */
		User cUser= testService.clearCache(user.getUserId());
		return "success";
	}
}
