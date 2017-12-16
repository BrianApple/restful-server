package com.tl.cdz.server.filters.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UtilDaoImpl implements IUtilDao{
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Override
	public boolean hasUrlName(String methodUrlName) {
		String sqlString="select count(IF_ID) cun"
				+ " from i_config"
				+ " where IF_FF_NAME=?";
		Object result=jdbcTemplate.queryForMap(sqlString,methodUrlName).get("cun")+"";
		if("0".equals(result)){
			return false;
		}
		return true;
	}
	@Override
	public String existsToken(String token) {
		String sql="select OPERATOR_SECRET"
				+ " from i_pf_conf"
				+ " where STATE='01' and OPERATOR_TOKEN=?"
				+ " and EXP_TIME > now() ";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql,token);
		if(list.size()>0){
			return list.get(0).get("OPERATOR_SECRET")+"";
		}
		return null;
	}
}
