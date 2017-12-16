package com.tl.rest.client.httpclientTest;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tl.rest.server.uitl.commonutils.AESUtil;
import com.tl.rest.server.uitl.commonutils.HMacMD5;
import com.tl.rest.server.uitl.commonutils.HttpClientUtil;

/**
 * 用于测试带参数的httpclient客户端
 * @author yangcheng
 * @date 2017年12月6日 
 * @version V1.0
 */
public class HttpClientPost {
	 public static void main(String[] args) throws Exception {

		 Map<String, String> param=new LinkedHashMap<String, String>();
		 param.put("operatorID", "project");
		 //data数据需要加密
		 param.put("data", AESUtil.Encrypt("{'userId':'121','userName':'ff','age':'10'}"));
		 param.put("timeStamp", "project");
		 param.put("seq", "java");
		 
		 //签名
		 String dataString=JSON.toJSONString(param);
		 String sig=HMacMD5.getHmacMd5Str("09127847211==", dataString);
		 param.put("sig", sig);
		 String token="hahaheheheihei==";
//		 HttpClientUtil.doPostJson("http://localhost/mysmallservice/CD/V2.0/test/putCache", token, JSON.toJSONString(param));
		 String msgString=HttpClientUtil.doPostJson("http://localhost/mysmallservice/CD/V2.0/test/getUser", token, JSON.toJSONString(param));
//		 String msgString=HttpClientUtil.doPostJson("http://localhost/mysmallservice/CD/V2.0/test/clearCache", token, JSON.toJSONString(param));
		 System.out.println(msgString);
	    }
}
