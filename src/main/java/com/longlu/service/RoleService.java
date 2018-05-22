package com.longlu.service;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.Role;
import com.longlu.pojo.Users;

public interface RoleService {

	public List<Role> selectRoleBySelective(Map<String, Object> paramsMap);
	
	public boolean saveOrUpdateRole(Role role);
	
	public boolean deleteRole(Role role);
	
	public Role selectRoleById(Long id);
}
