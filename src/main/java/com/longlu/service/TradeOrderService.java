package com.longlu.service;

import java.util.List;
import java.util.Map;

/**
 * @author yangcan
 * 订单详情接口
 */
public interface TradeOrderService {
	
	/**
	 * 获取所有供应商名称
	 * @return
	 */
	public List<Map<String, Object>> getAllOrderSupplierName();
	
	/**
	 * 通过条件查询TradeOrder总数
	 * @param paramsMap
	 * @return
	 */
	public Long selectTradeOrderBySelectiveCount(Map<String, Object> paramsMap);
	
	/**
	 * @param paramsMap
	 * @return
	 */
	public int updateTradeOrderBySelectiveCount(Map<String, Object> paramsMap);

}
