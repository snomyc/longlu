package com.longlu.dao;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.YouzanShopConfig;

public interface YouzanShopConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(YouzanShopConfig record);

    int insertSelective(YouzanShopConfig record);

    YouzanShopConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YouzanShopConfig record);

    int updateByPrimaryKey(YouzanShopConfig record);
    
    public List<Map<String,Object>> selectBySelective(Map<String, Object> paramsMap);
    
    public List<YouzanShopConfig> findAll();
    
    public List<YouzanShopConfig> findAllUseShop();
    
    public YouzanShopConfig selectByShopName(String shopName);
}