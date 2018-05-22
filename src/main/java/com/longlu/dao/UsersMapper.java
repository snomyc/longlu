package com.longlu.dao;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.Users;

public interface UsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
    
	/**
	* 方法名称: selectUsersBySelective
	* 方法描述： 根据条件查询用户列表
	* 参数 :@param paramsMap
	* 参数 :@return
	* 返回类型: List<Map<String,Object>>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2017年1月6日 下午10:30:51     
	*/
	public List<Map<String,Object>> selectUsersBySelective(Map<String, Object> paramsMap);
}