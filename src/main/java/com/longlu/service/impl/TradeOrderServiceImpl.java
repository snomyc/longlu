package com.longlu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longlu.dao.TradeOrderMapper;
import com.longlu.service.TradeOrderService;

/**
 * @author yangcan
 * 订单详情服务类
 */
@Service
public class TradeOrderServiceImpl  implements TradeOrderService{

	@Autowired
	private TradeOrderMapper tradeOrderMapper;

	public List<Map<String, Object>> getAllOrderSupplierName() {
		return tradeOrderMapper.getAllOrderSupplierName();
	}

	public Long selectTradeOrderBySelectiveCount(Map<String, Object> paramsMap) {
		return tradeOrderMapper.selectTradeOrderBySelectiveCount(paramsMap);
	}

	public int updateTradeOrderBySelectiveCount(Map<String, Object> paramsMap) {
		return tradeOrderMapper.updateTradeOrderBySelectiveCount(paramsMap);
	}
}
