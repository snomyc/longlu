package com.longlu.dao;

import java.util.List;
import java.util.Map;

import com.longlu.pojo.TradeDetail;

public interface TradeDetailMapper {
	
	public int deleteByPrimaryKey(Long id);

	public int insert(TradeDetail record);

	public int insertSelective(TradeDetail record);

	public TradeDetail selectByTradeDetail(Long id);

	public int updateByPrimaryKeySelective(TradeDetail record);

	public int updateByPrimaryKey(TradeDetail record);
	
	/**
	* 方法名称: selectTradeDetailByTid
	* 方法描述：通过tid查询集合
	* 参数 :@param tid
	* 参数 :@return
	* 返回类型: TradeDetail
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月23日 下午10:37:53     
	*/
	public TradeDetail selectTradeDetailByTid(String tid);
	
	/**
	* 方法名称: selectTradeDetailBySelective
	* 方法描述： 通过条件查询集合
	* 参数 :@param paramsMap
	* 参数 :@return
	* 返回类型: List<TradeDetail>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月23日 下午10:38:02     
	*/
	public List<TradeDetail> selectTradeDetailBySelective(Map<String, Object> paramsMap);
	
	
	/**
	* 方法名称: selectTradeDetailByReview
	* 方法描述：只查询TradeDetail表，财务，仓库使用
	* 参数 :@param paramsMap
	* 参数 :@return
	* 返回类型: List<TradeDetail>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月24日 下午4:32:28     
	*/
	public List<TradeDetail> selectTradeDetailByReview(Map<String, Object> paramsMap);
}