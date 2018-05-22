package com.longlu.dao;

import java.util.List;
import java.util.Map;
import com.longlu.pojo.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    /**
	* 方法名称: selectRoleBySelective
	* 方法描述： 根据条件查询角色列表
	* 参数 :@param paramsMap
	* 参数 :@return
	* 返回类型: List<Role>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2017年1月6日 下午10:30:51     
	*/
	public List<Role> selectRoleBySelective(Map<String, Object> paramsMap);
}