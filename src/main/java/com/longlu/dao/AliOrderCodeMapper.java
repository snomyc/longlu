package com.longlu.dao;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.AliOrderCode;

public interface AliOrderCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AliOrderCode record);

    int insertSelective(AliOrderCode record);

    AliOrderCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AliOrderCode record);

    int updateByPrimaryKey(AliOrderCode record);
    
    public List<Map<String,Object>> selectBySelective(Map<String, Object> paramsMap);
    
    public AliOrderCode selectByOuterCode(String outerCode);
}