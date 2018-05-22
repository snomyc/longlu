package com.longlu.service;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.ExpressManager;

public interface ExpressManagerService {

	public int deleteByPrimaryKey(Long id);

	public int insert(ExpressManager record);

	public int insertSelective(ExpressManager record);

	public ExpressManager selectByPrimaryKey(Long id);

	public int updateByPrimaryKeySelective(ExpressManager record);

	public int updateByPrimaryKey(ExpressManager record);
	
	public boolean saveOrUpdate(ExpressManager record);
	
	public List<Map<String,Object>> selectBySelective(Map<String, Object> paramsMap);
	
	public ExpressManager selectByAliCompany(String aliCompany);
}
