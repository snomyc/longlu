package com.longlu.service;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.AliOrderCode;
import com.longlu.util.pagination.Pagination;

public interface AliOrderCodeService {
	int deleteByPrimaryKey(Long id);

    int insert(AliOrderCode record);

    int insertSelective(AliOrderCode record);

    AliOrderCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AliOrderCode record);

    int updateByPrimaryKey(AliOrderCode record);
    
    public List<Map<String,Object>> selectBySelective(Map<String, Object> paramsMap);
    
    public AliOrderCode selectByOuterCode(String outerCode);
    
    public boolean saveOrUpdate(AliOrderCode record);
    
    //新增有赞商品编码对应表
    public void addGoodsDetailCode(Pagination pagination);
}
