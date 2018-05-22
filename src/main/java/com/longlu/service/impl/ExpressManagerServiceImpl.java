package com.longlu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longlu.dao.ExpressManagerMapper;
import com.longlu.pojo.ExpressManager;
import com.longlu.service.ExpressManagerService;
@Service
public class ExpressManagerServiceImpl implements ExpressManagerService{

	@Autowired
	private ExpressManagerMapper expressManagerMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return expressManagerMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ExpressManager record) {
		// TODO Auto-generated method stub
		return expressManagerMapper.insert(record);
	}

	@Override
	public int insertSelective(ExpressManager record) {
		// TODO Auto-generated method stub
		return expressManagerMapper.insertSelective(record);
	}

	@Override
	public ExpressManager selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return expressManagerMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ExpressManager record) {
		// TODO Auto-generated method stub
		return expressManagerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ExpressManager record) {
		// TODO Auto-generated method stub
		return expressManagerMapper.updateByPrimaryKey(record);
	}
	
	public boolean saveOrUpdate(ExpressManager record){
		int count = 0;
		try{
			if(record.getId() != null) {
				//更新用户信息
				count = expressManagerMapper.updateByPrimaryKeySelective(record);
			}else {
				//新增用户信息
				count = expressManagerMapper.insert(record);
			}
			return count > 0 ? true : false;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Map<String, Object>> selectBySelective(
			Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return expressManagerMapper.selectBySelective(paramsMap);
	}

	@Override
	public ExpressManager selectByAliCompany(String aliCompany) {
		// TODO Auto-generated method stub
		return expressManagerMapper.selectByAliCompany(aliCompany);
	}

}
