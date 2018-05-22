package com.longlu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longlu.dao.RoleMapper;
import com.longlu.pojo.Role;
import com.longlu.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper roleMapper;

	public List<Role> selectRoleBySelective(Map<String, Object> paramsMap) {
		return roleMapper.selectRoleBySelective(paramsMap);
	}

	public boolean saveOrUpdateRole(Role role) {
		int count = 0;
		if(role.getId() != null) {
			//更新用户信息
			count = roleMapper.updateByPrimaryKeySelective(role);
		}else {
			//新增用户信息
			count = roleMapper.insert(role);
		}
		return count > 0 ? true : false;
	}

	public boolean deleteRole(Role role) {
		int count = 0;
		count = roleMapper.deleteByPrimaryKey(role.getId());
		return count > 0 ? true : false;
	}

	public Role selectRoleById(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}
	
}
