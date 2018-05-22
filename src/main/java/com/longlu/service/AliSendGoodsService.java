package com.longlu.service;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.AliSendGoods;

public interface AliSendGoodsService {
	
	int deleteByPrimaryKey(Long id);

    int insert(AliSendGoods record);

    int insertSelective(AliSendGoods record);

    AliSendGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AliSendGoods record);

    int updateByPrimaryKey(AliSendGoods record);
    
    public List<Map<String,Object>> selectBySelective(Map<String, Object> paramsMap);

}
