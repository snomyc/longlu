package com.longlu.dao;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.ExpressManager;


public interface ExpressManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressManager record);

    int insertSelective(ExpressManager record);

    ExpressManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressManager record);

    int updateByPrimaryKey(ExpressManager record);
    
    public List<Map<String,Object>> selectBySelective(Map<String, Object> paramsMap);
    
    ExpressManager selectByAliCompany(String aliCompany);
}