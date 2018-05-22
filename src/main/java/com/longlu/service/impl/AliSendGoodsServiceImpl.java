package com.longlu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longlu.dao.AliSendGoodsMapper;
import com.longlu.pojo.AliSendGoods;
import com.longlu.service.AliSendGoodsService;

@Service
public class AliSendGoodsServiceImpl implements AliSendGoodsService{
	
	@Autowired
	private AliSendGoodsMapper aliSendGoodsMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return aliSendGoodsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AliSendGoods record) {
		// TODO Auto-generated method stub
		return aliSendGoodsMapper.insert(record);
	}

	@Override
	public int insertSelective(AliSendGoods record) {
		// TODO Auto-generated method stub
		return aliSendGoodsMapper.insertSelective(record);
	}

	@Override
	public AliSendGoods selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return aliSendGoodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AliSendGoods record) {
		// TODO Auto-generated method stub
		return aliSendGoodsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(AliSendGoods record) {
		// TODO Auto-generated method stub
		return aliSendGoodsMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map<String, Object>> selectBySelective(
			Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return aliSendGoodsMapper.selectBySelective(paramsMap);
	}

}
