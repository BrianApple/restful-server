package com.tl.rest.server.filters;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sun.java_cup.internal.runtime.virtual_parse_stack;
import com.tl.rest.server.filters.service.UtilService;
import com.tl.rest.server.uitl.commonutils.CommonResult;
import com.tl.rest.server.uitl.commonutils.HMacMD5;
import com.tl.rest.server.uitl.commonutils.RequestParam;

/**
 * 验证签名等一系列操作
 * @author yangcheng
 * @date 2017年12月5日 
 * @version V1.0
 */
@Component
public class PreFilter implements HandlerInterceptor{
	
	@Autowired
	UtilService utilService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//从请求头中d的Authorization中获取token
		String token=request.getHeader("Authorization");
		
		 /**
		  * 通过json形式传递过来的参数采用流的形式读取参数内容（控制层可以采用注解@requestParam获取）
		  *  request.getInputStream()在拦截器中执行一次只有  控制层无法再通过该方法获取参数了，因此流只能被读取一次
		  */
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        // 获取参数
        String json = sb.toString();
        json= URLDecoder.decode(json, "UTF-8");
        RequestParam requestParam=JSON.parseObject(json, RequestParam.class);
		System.out.println("************************拦截成功，token="+token);
		//获取请求方法
		String servletPath = request.getRequestURL().toString();
		String methodUrlName = servletPath.substring(servletPath.lastIndexOf("/") + 1);
		if ("query_token".equals(methodUrlName)) {
			//如果是访问申请token的接口  那么直接放行
			request.setAttribute("paramString", json);
			return true;
		}else{
			//判断token是否正确（包括失效时间），如果token正确  那么返回客户对应的密钥
			String key="09127847211==";//utilService.judgeTokenAndGetKey(token);
			if(key != null){
				//判断访问的接口名是否存在
				boolean flag=true;//utilService.hasUrlName(methodUrlName);
				if (flag) {
					//判断必要参数是否都带来了
					boolean isuni=utilService.isUniform(json);
					if (isuni) {
						//参数完整之后验证签名
						Map<String, String> paramMap=new LinkedHashMap<String, String>();
						String operatorID=requestParam.getOperatorID();
						String data=requestParam.getData();
						String timeStamp=requestParam.getTimeStamp();
						String seq=requestParam.getSeq();
						String sigfString=requestParam.getSig();
						paramMap.put("operatorID", operatorID);
						paramMap.put("data", data);
						paramMap.put("timeStamp", timeStamp);
						paramMap.put("seq", seq);
						
				        String sigString=JSON.toJSONString(paramMap);
				        //验证签名 （机密信息中的所有的‘+’全部被变成了‘ ’，因此需要替换过来）
				        String sig=HMacMD5.getHmacMd5Str(key,sigString.replace(' ', '+') );
				        if (sig.equals(sigfString)) {
				        	//重新生成的签名与传递过来的旧的签名如果相等的话  那么验签成功！
							System.out.println("验签成功！");
							request.setAttribute("paramString", json);
							return true;
						}else {
							response.setContentType("application/json;charset=UTF-8");
							ObjectMapper mapper = new ObjectMapper();
							String jsonString=mapper.writeValueAsString(CommonResult.build(4001, "签名验证失败"));
							response.getWriter().write(jsonString);
						}
						
					} else {
						response.setContentType("application/json;charset=UTF-8");
						ObjectMapper mapper = new ObjectMapper();
						String jsonString=mapper.writeValueAsString(CommonResult.build(4003, "参数不合法"));
						response.getWriter().write(jsonString);
					}
				
				}else {
					//不存在该接口
					response.setContentType("application/json;charset=UTF-8");
					ObjectMapper mapper = new ObjectMapper();
					String jsonString=mapper.writeValueAsString(CommonResult.build(4004, "您所访问的接口不存在"));
					response.getWriter().write(jsonString);
				}
			}else {
				response.setContentType("application/json;charset=UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				String jsonString=mapper.writeValueAsString(CommonResult.build(4002, "token有误"));
				response.getWriter().write(jsonString);
			}
			System.err.println("未放行！");
			return false;
		}
		
		
		
	}
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}



}
