package com.tl.cdz.server.filters.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tl.cdz.server.filters.dao.IUtilDao;
import com.tl.cdz.server.filters.dao.UtilDaoImpl;
import com.tl.cdz.server.filters.domain.Constants;
@Service
public class UtilService {
	@Autowired
	IUtilDao iUtilDao;
	
	/**
	 * 判断是否包含被访问的接口名
	 * @Title: hasUrlName 
	 * @author yangcheng
	 * @param methodUrlName
	 * @return 包含返回true  反之返回false
	 */
	public boolean hasUrlName(String methodUrlName) {
		return iUtilDao.hasUrlName(methodUrlName);
	}
	/**
	 * 判断客户端传递过来的参数是否正确
	 * @Title: isUniform 
	 * @author yangcheng
	 * @param json
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public boolean isUniform(String jsonString) throws Exception {

		
		String errMsg = "";
		if(!jsonString.contains(Constants.OperatorID)){
			errMsg = Constants.OperatorID;
		}
		if(!jsonString.contains(Constants.Data)){
			errMsg += "".equals(errMsg) ? Constants.Data : ("," + Constants.Data);
		}
		if(!jsonString.contains(Constants.TimeStamp)){
			errMsg += "".equals(errMsg) ? Constants.TimeStamp : ("," + Constants.TimeStamp);
		}
		if(!jsonString.contains(Constants.Seq)){
			errMsg += "".equals(errMsg) ? Constants.Seq : ("," + Constants.Seq);
		}
		if(!jsonString.contains(Constants.Sig)){
			errMsg += "".equals(errMsg) ? Constants.Sig : ("," + Constants.Sig);
		}
		if(!"".equals(errMsg)){
			errMsg = "POST参数不合法,缺少必须的" + errMsg;
			return false;
		}else {
			return true;
		}

	}
	/**
	 * 查询指定token是否存在 如果存在且未失效那么返回对应商户密钥
	 * @Title: existsToken 
	 * @author yangcheng
	 * @param token
	 * @return
	 */
	public String judgeTokenAndGetKey(String token) {
		return iUtilDao.existsToken(token);
	}
}
