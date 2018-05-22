package com.longlu.service;

import java.util.List;
import java.util.Map;
import com.longlu.pojo.TradeDetail;

/**     
* 类描述：   订单管理服务接口
* 创建人：yagncan   
* 创建时间：2016年12月17日 上午11:55:37     
*/
public interface TradeDetailService{
	
	/**
	* 方法名称: findTradeDetailList
	* 方法描述：根据条件查询订单列表，条件不能查订单详情
	* 参数 :@param whereSql
	* 参数 :@return
	* 返回类型: List<TradeDetail>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月18日 下午6:19:00     
	*/
	public List<TradeDetail> findTradeDetailList(Map<String, Object> paramsMap);
	
	/**
	* 方法名称: findTradeDetailListByOrders
	* 方法描述：根据TradeDetail和TradeOrders两表共同的条件查询订单列表
	* 参数 :@param paramsMap
	* 参数 :@param orderReview
	* 参数 :@return
	* 返回类型: List<TradeDetail>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月24日 下午4:36:39     
	*/
	public List<TradeDetail> findTradeDetailListByOrders(Map<String, Object> paramsMap,int orderReview);
	
	/**
	* 方法名称: updateTradeOrderReview
	* 方法描述：更新订单审核流程状态 财务审核
	* 参数 :@param paramsMap
	* 参数 :@param orderReview
	* 参数 :@return
	* 返回类型: Map<String,Object>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月29日 下午9:26:40     
	*/
	public Map<String, Object> updateTradeOrderReview(Map<String, Object> paramsMap,int orderReview);
	
	
	public void getTradeSuccessOrdersToYouZan(String startUpdateTime,String endUpdateTime,String shopId);
	
	public boolean checkOrderFeedback(String tid,String oid,String shopId);
	
	/**
	 * 批量发货数据更新
	 * @param paramsMap
	 * @return
	 */
	public int batchUpdateByTradeOrderSelectMap(Map<String, Object> paramsMap);
	
	/**
	 * 新增或更新已交易成功订单列表入库
	 * @param tradeDetail
	 * @author yangcan
	 */
	public void saveOrUpdateTradeBuyerSigned(List<TradeDetail> tradeDetail);
	
	/**
	 * @throws Exception
	 * 方法说明:一键同步有赞已发货订单
	 * 创立日期:2017-9-29 下午7:39:43
	 * 创建人:yangcan
	 */
	public void synchroYouZanTrade() throws Exception;
	
}
