package com.humanvoyage.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.HashMap;

import javax.annotation.Resource;

public class BaseDao extends SqlSessionDaoSupport {
	protected HashMap<String, Object> paramsMap() {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		return paramsMap;
	}
	
}
