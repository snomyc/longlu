package com.longlu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longlu.dao.UsersMapper;
import com.longlu.pojo.Users;
import com.longlu.service.UsersService;
import com.longlu.util.MD5Utils;

@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	private UsersMapper usersMapper;

	public List<Map<String,Object>> selectUsersBySelective(Map<String, Object> paramsMap) {
		return usersMapper.selectUsersBySelective(paramsMap);
	}

	public boolean saveOrUpdateUsers(Users user) {
		int count = 0;
		if(user.getId() != null) {
			//更新用户信息
			count = usersMapper.updateByPrimaryKeySelective(user);
		}else {
			//新增用户信息
			//设置初始密码
			user.setPassword(MD5Utils.MD5("123456"));
			count = usersMapper.insert(user);
		}
		return count > 0 ? true : false;
	}

	public boolean deleteUsers(Users user) {
		int count = 0;
		count = usersMapper.deleteByPrimaryKey(user.getId());
		return count > 0 ? true : false;
	}

	public Users selectUsersById(Long id) {
		return usersMapper.selectByPrimaryKey(id);
	}
	
	public int updateByPrimaryKeySelective(Users record) {
		return usersMapper.updateByPrimaryKeySelective(record);
	}
	
}
