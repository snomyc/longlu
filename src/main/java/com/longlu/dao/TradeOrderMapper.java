package com.longlu.dao;

import java.util.List;
import java.util.Map;
import com.longlu.pojo.TradeOrder;

public interface TradeOrderMapper {
	
	public int deleteByPrimaryKey(Long id);

	public int insert(TradeOrder record);

	public int insertSelective(TradeOrder record);

	public TradeOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeOrder record);

    int updateByPrimaryKey(TradeOrder record);
	
	/**
	* 方法名称: updateByTradeOrderSelectMap
	* 方法描述： 通过map方法更新
	* 参数 :@param paramsMap
	* 参数 :@return
	* 返回类型: int
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月25日 下午10:49:16     
	*/
	public int updateByTradeOrderSelectMap(Map<String, Object> paramsMap);
	
	/**
	 * 批量更新
	 * @param paramsMap
	 * @return
	 */
	public int batchUpdateByTradeOrderSelectMap(Map<String, Object> paramsMap);
	
	public int updateByTradeOrder(TradeOrder record);
	
	/**
	* 方法名称: selectTradeOrderByTid
	* 方法描述：通过tid查询TradeOrder集合
	* 参数 :@param tid
	* 参数 :@return
	* 返回类型: List<TradeOrder>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月23日 下午10:40:56     
	*/
	public List<TradeOrder> selectTradeOrderByTid(String tid);
	
	/**
	* 方法名称: selectTradeOrderBySelective
	* 方法描述： 通过条件查询TradeOrder集合
	* 参数 :@param paramsMap
	* 参数 :@return
	* 返回类型: List<TradeOrder>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月23日 下午10:42:03     
	*/
	public List<TradeOrder> selectTradeOrderBySelective(Map<String, Object> paramsMap);
	
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
	 * 根据条件更新TradeOrder集合总数发送邮件状态
	 * @param paramsMap
	 * @return
	 */
	public int updateTradeOrderBySelectiveCount(Map<String, Object> paramsMap);
	
}