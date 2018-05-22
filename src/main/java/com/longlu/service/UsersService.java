package com.longlu.service;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.Users;

public interface UsersService {

	public List<Map<String,Object>> selectUsersBySelective(Map<String, Object> paramsMap);
	
	public boolean saveOrUpdateUsers(Users user);
	
	public boolean deleteUsers(Users user);
	
	public Users selectUsersById(Long id);
	
	public int updateByPrimaryKeySelective(Users record);
}
