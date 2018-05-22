package com.longlu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.longlu.dao.TradeDetailMapper;
import com.longlu.dao.TradeOrderMapper;
import com.longlu.pojo.AliOrderCode;
import com.longlu.pojo.TradeDetail;
import com.longlu.pojo.TradeOrder;
import com.longlu.pojo.YouzanShopConfig;
import com.longlu.service.AliOrderCodeService;
import com.longlu.service.TradeDetailService;
import com.longlu.service.YouzanShopConfigService;
import com.longlu.util.DateFormatHelper;
import com.longlu.youzan.YouZanServerUtils;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetResult.TradeDetailV2;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetResult.TradeOrderV2;

/**     
* 类描述：   单管理服务实现类
* 创建人：yagncan   
* 创建时间：2016年12月17日 上午11:55:34     
*/
@Service
public class TradeDetailServiceImpl implements TradeDetailService{

	private static Logger logger = LoggerFactory.getLogger(TradeDetailServiceImpl.class);
	@Autowired
	private TradeDetailMapper tradeDetailMapper;
	
	@Autowired
	private TradeOrderMapper tradeOrderMapper;
	
	@Autowired
	private AliOrderCodeService aliOrderCodeService;
	
	@Autowired
	private YouzanShopConfigService youzanShopConfigService;

	/** 
	* @Title: findTradeDetailListByPay 
	* @Description: TODO 根据条件查询订单信息，条件mybatis里面有子查询查询子单
	* @param @param paramsMap
	* @param @return 设定文件
	* @authoer yangcan
	* @throws 
	*/ 
	public List<TradeDetail> findTradeDetailList(Map<String, Object> paramsMap) {
		return tradeDetailMapper.selectTradeDetailBySelective(paramsMap);
	}

	/**
	* 方法名称: findTradeDetailListByOrders
	* 方法描述：根据TradeDetail和TradeOrders两表共同的条件查询订单列表 
	* 参数 :@param paramsMap
	* 参数 :@param orderReview
	* 参数 :@return
	* 返回类型: List<TradeDetail>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月24日 下午4:36:22     
	*/
	public List<TradeDetail> findTradeDetailListByOrders(Map<String, Object> paramsMap,int orderReview) {
		List<TradeDetail> tradeDetailList = new ArrayList<TradeDetail>();
		List<TradeDetail> dList = tradeDetailMapper.selectTradeDetailByReview(paramsMap);
		if(CollectionUtils.isNotEmpty(dList)) {
			Map<String, Object> params = new HashMap<String,Object>();
			for (TradeDetail detail : dList) {
				params.put("tid", detail.getTid());
				params.put("orderReview", orderReview);
				params.put("supplierNames", paramsMap.get("supplierName"));
				List<TradeOrder> tOrder = tradeOrderMapper.selectTradeOrderBySelective(params);
				for (TradeOrder tradeOrder : tOrder) {
					
					//只有订单审核的时候才需要查询阿里巴巴商品URL地址
					if(orderReview == 0) {
						//查询该商品是不是阿里巴巴的商品，如果是则取出阿里巴巴商品URL地址
						List<Map<String,Object>> aliList = null;
						if(StringUtils.isNotBlank(tradeOrder.getOuter_sku_id())) {
							params.clear();
							params.put("outerCode", tradeOrder.getOuter_sku_id());
							aliList = aliOrderCodeService.selectBySelective(params);
						}else if(StringUtils.isNotBlank(tradeOrder.getOuter_item_id())) {
							params.clear();
							params.put("outerCode", tradeOrder.getOuter_item_id());
							aliList = aliOrderCodeService.selectBySelective(params);
						}
						if(CollectionUtils.isNotEmpty(aliList)) {
							tradeOrder.setAlibabaUrl((String)aliList.get(0).get("alibabaUrl"));
							tradeOrder.setAliSupplierName((String)aliList.get(0).get("supplierName"));
							tradeOrder.setAliBz((String)aliList.get(0).get("bz"));
						}
					}
				}
				
				if(CollectionUtils.isNotEmpty(tOrder)) {
					detail.setOrders(tOrder);
					tradeDetailList.add(detail);
				}
			}
		}
		return tradeDetailList;
	}

	/** 
	* @Title: updateTradeOrderReview 
	* @Description: 财务审核
	* @param @param paramsMap
	* @param @param orderReview
	* @param @return 设定文件
	* @authoer yangcan
	* @throws 
	*/ 
	public Map<String, Object> updateTradeOrderReview(Map<String, Object> paramsMap,int orderReview) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//审核状态 pass审核通过,否则审核不通过
		Map<String, Object> params = new HashMap<String, Object>();
		
		String tid = (String)paramsMap.get("tid");
		String id = (String)paramsMap.get("id");
		String oid = (String)paramsMap.get("oid");
		String shopId = (String)paramsMap.get("shopId");
		
		String expressCompany = (String)paramsMap.get("expressCompany");
		String expressNumber = (String)paramsMap.get("expressNumber");
		
		//审核通过
		if(StringUtils.isNotBlank(tid)) {
			
			if(StringUtils.isBlank(oid)) {
				resultMap.put("success", false);
				resultMap.put("msg", "订单"+tid+":审批失败, 该订单没有oid信息,请联系管理员!");
				return resultMap;
			}
			
			//审核的时候判断订单是否处于维权状态，无维权则审核通过
			if(!this.checkOrderFeedback(tid,oid,shopId)) {
				resultMap.put("success", false);
				resultMap.put("msg", "订单"+tid+":审批失败,客户发起了维权!");
				return resultMap;
			}
			params.put("id", id);
			params.put("orderReview", orderReview);
			
			//仓库需要快递单号
			if(StringUtils.isNotBlank(expressCompany)) {
				params.put("expressCompany", paramsMap.get("expressCompany"));
			}
			if(StringUtils.isNotBlank(expressNumber)) {
				params.put("expressNumber", paramsMap.get("expressNumber"));
			}
			//调用有赞接口发货
			if(orderReview == 2) {
				HashMap<String, String> paramsConfirm = new HashMap<String, String>();
				paramsConfirm.put("tid", tid);
				paramsConfirm.put("oids", oid);
				//快递ID
				paramsConfirm.put("out_stype", expressCompany);
				//快递单号
				paramsConfirm.put("out_sid", expressNumber);
				
				//通过shopId 获取店铺配置信息
				YZClient client = youzanShopConfigService.getYzClient(Long.valueOf(shopId));
				
				boolean flag = YouZanServerUtils.youzanLogisticsOnlineConfirm(client,paramsConfirm);
				//boolean flag = KdtApiUtils.onlineConfirm(paramsConfirm);
				if(!flag) {
					resultMap.put("success", false);
					resultMap.put("msg", "订单"+tid+":发货失败!");
					return resultMap;
				}
			}
			
		}else {
			//审核不通过
			params.put("id", id);
			params.put("orderReview", orderReview);
			params.put("bz", paramsMap.get("bz"));
		}
		int flag = tradeOrderMapper.updateByTradeOrderSelectMap(params);
		if(flag > 0) {
			resultMap.put("success", true);
			resultMap.put("msg", "订单:"+tid+",审核通过!");
		}else {
			resultMap.put("success", false);
			resultMap.put("msg", "订单:"+tid+",审核失败!");
		}
		return resultMap;
	}
	
	
	/** 
	* @Title: checkOrderFeedback 
	* @Description: 审核的时候判断订单是否处于维权状态，无维权则为true,有维权则为false 
	* @param @param tid
	* @param @return 设定文件
	* @authoer yangcan
	* @throws 
	*/ 
	public boolean checkOrderFeedback_bak(String tid,String oid) {
		boolean flag = false;
		//通过订单编号查询订单无维权及商品待发货状态才算审核通过
		//tradeDetail feedback(0,10) tradeOrder state_str(待发货) 才算审核通过
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("tid", tid);
//		String strResult = kdtApiService.resultKdtApiToGet(KdtApiItem.TRADES_GET, params);
//    	if(StringUtils.isNotBlank(strResult.toString())) {
//        	JSONObject jo = JSONObject.fromObject(strResult.toString());
//        	JSONObject root = jo.getJSONObject("response");
//        	Gson gson = new Gson();
//        	TradeDetail tradeDetail = gson.fromJson(root.get("trade").toString(), new TypeToken<TradeDetail>() {}.getType());
//			if(tradeDetail != null) {
//				//已付款订单 并且无维权
//				//if(tradeDetail.getStatus().equals("WAIT_SELLER_SEND_GOODS") && (tradeDetail.getFeedback() == 0 || tradeDetail.getFeedback() == 10)) {
//				if(tradeDetail.getStatus().equals("WAIT_SELLER_SEND_GOODS") && (tradeDetail.getFeedback() == 0 || tradeDetail.getFeedback() == 110)) {
//					//如果订单详情表中商品的状态不是待发货则审核不通过
//					List<TradeOrder> tOrderList = tradeDetail.getOrders();
//					for (TradeOrder tradeOrder : tOrderList) {
//						if(tradeOrder.getOid().equals(oid)) {
//							//如果该商品是待发货状态，并且无退款，无维权
//							if(tradeOrder.getState_str().equals("待发货") && tradeOrder.getItem_refund_state().equals("NO_REFUND")) {
//								flag = true;
//								return flag;
//							}
//						}
//					}
//				}
//			}
//    	}
		return flag;
	}
	
	
	/** 
	* @Title: checkOrderFeedback 
	* @Description: 审核的时候判断订单是否处于维权状态，无维权则为true,有维权则为false 
	* @param @param tid
	* @param @return 设定文件
	* @authoer yangcan
	* @throws 
	*/ 
	public boolean checkOrderFeedback(String tid,String oid,String shopId) {
		boolean flag = false;
		//通过订单编号查询订单无维权及商品待发货状态才算审核通过
		YouzanTradeGetParams youzanTradeGetParams = new YouzanTradeGetParams();
		youzanTradeGetParams.setTid(tid);
		
		YZClient client = youzanShopConfigService.getYzClient(Long.valueOf(shopId));
		
		TradeDetailV2 detail= YouZanServerUtils.youzanTradeGet(client,youzanTradeGetParams);
		if(detail != null) {
			//已付款订单 并且无维权
			//if(tradeDetail.getStatus().equals("WAIT_SELLER_SEND_GOODS") && (tradeDetail.getFeedback() == 0 || tradeDetail.getFeedback() == 10)) {
			if(detail.getStatus().equals("WAIT_SELLER_SEND_GOODS") && (detail.getFeedback() == 0 || detail.getFeedback() == 110)) {
				//如果订单详情表中商品的状态不是待发货则审核不通过
				TradeOrderV2[] tOrderList = detail.getOrders();
				for (TradeOrderV2 tradeOrder : tOrderList) {
					if(String.valueOf(tradeOrder.getOid()).equals(oid)) {
						//如果该商品是待发货状态，并且无退款，无维权
						if(tradeOrder.getStateStr().equals("待发货") && tradeOrder.getItemRefundState().equals("NO_REFUND")) {
							flag = true;
							return flag;
						}
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 手动调用 从有赞获取已交易成功的订单信息,便于财务对账防止从有赞那边数据遗漏
	 * @param startUpdateTime 交易订单更新起始时间
	 * @param endUpdateTime   交易订单更新结束时间
	 * @param shopId 店铺配置表店铺ID
	 * @author yangcan
	 */
	public void getTradeSuccessOrdersToYouZan(String startUpdateTime,String endUpdateTime,String shopId) {
		logger.error("start:从有赞API获取已交易成功订单列表 手动调用...");
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("status", "TRADE_BUYER_SIGNED");
		params.put("start_update", DateFormatHelper.formatDate(startUpdateTime, DateFormatHelper.YYYY_MM_DD_HH_MM_SS));
		params.put("end_update", DateFormatHelper.formatDate(endUpdateTime, DateFormatHelper.YYYY_MM_DD_HH_MM_SS));
		
		//通过shopId 获取店铺配置信息
		YZClient client = youzanShopConfigService.getYzClient(Long.valueOf(shopId));
		YouzanShopConfig youzanShopConfig = youzanShopConfigService.selectByPrimaryKey(Long.valueOf(shopId));
		
		List<TradeDetail> tradeDetail = YouZanServerUtils.youzanTradesSoldGet(client, params,shopId,youzanShopConfig.getShopName());
		this.saveOrUpdateTradeBuyerSigned(tradeDetail);
		logger.error("end:从有赞API获取已交易成功订单列表 手动调用...");
	}
	
	
	/**
	 * 新增或更新已交易成功订单列表入库
	 * @param tradeDetail
	 * @author yangcan
	 */
	public void saveOrUpdateTradeBuyerSigned(List<TradeDetail> tradeDetail) {
		if(CollectionUtils.isNotEmpty(tradeDetail)) {
			for (TradeDetail t : tradeDetail) {
    			TradeDetail tdb= tradeDetailMapper.selectTradeDetailByTid(t.getTid());
    			//数据库中没有相同的订单记录
    			if(tdb == null) {
    				tradeDetailMapper.insert(t);
    				//如果有子订单
	        		if( CollectionUtils.isNotEmpty(t.getOrders()) ) {
	        			for (TradeOrder tradeOrder : t.getOrders()) {
	        				//获取成本表中成本信息
	        				//通过商品编码查询商品的成本信息
	    					AliOrderCode aliOrderCode = aliOrderCodeService.selectByOuterCode(tradeOrder.getSupplierName()+"-"+tradeOrder.getGoodsNumber());
	    					if(aliOrderCode != null) {
	    						tradeOrder.setCost(aliOrderCode.getCost());
	    					}
	        				//写表tradeorder
	        				tradeOrderMapper.insertSelective(tradeOrder);
	        			}
	        		}
    			}else {
    				
//    				//如果有相同记录则更改该订单记录的状态及子订单的所有信息
    				t.setId(tdb.getId());
    				tradeDetailMapper.updateByPrimaryKey(t);
    				
    				//因为交易完成后商品和付款的商品信息
    				//更改子单的详细信息
    				//注意以后增加的物流等信息需要set到新的交易完成对象中
    				List<TradeOrder> orderDBList = tdb.getOrders();
    				List<TradeOrder> orderList = t.getOrders();
    				
    				for (TradeOrder orderDB : orderDBList) {
    					for (TradeOrder order : orderList) {
    						//交易明细编号区分明细信息
    						if(order.getOid().equals(orderDB.getOid())) {
    							TradeOrder update = new TradeOrder();
    							update.setId(orderDB.getId());
    							update.setState_str(order.getState_str());
    							update.setAllow_send(order.getAllow_send());
    							//更新表tradeorder
    							tradeOrderMapper.updateByPrimaryKeySelective(update);
    						}
						}
					}
    			}
			}
		}
	}

	public int batchUpdateByTradeOrderSelectMap(Map<String, Object> paramsMap) {
		return tradeOrderMapper.batchUpdateByTradeOrderSelectMap(paramsMap);
	}
	
	public void synchroYouZanTrade() throws Exception{
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("status", "WAIT_SELLER_SEND_GOODS");
		List<TradeDetail> tradeDetailList =  findTradeDetailListByOrders(resultMap, 1);
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//开始一个个比对订单，如果订单已发货则取消该订单，备注订单在有赞已发货
		for (TradeDetail tradeDetail : tradeDetailList) {
			for (TradeOrder tradeOrder : tradeDetail.getOrders()) {
				
				YouzanTradeGetParams youzanTradeGetParams = new YouzanTradeGetParams();
				youzanTradeGetParams.setTid(tradeOrder.getTid());
				
				//通过shopId 获取店铺配置信息
				YZClient client = youzanShopConfigService.getYzClient(Long.valueOf(tradeOrder.getShopId()));
				
				TradeDetailV2 detail= YouZanServerUtils.youzanTradeGet(client,youzanTradeGetParams);
				if(detail != null) {
					TradeOrderV2[] tOrderList = detail.getOrders();
					for (TradeOrderV2 tradeOrderV2 : tOrderList) {
						if(String.valueOf(tradeOrder.getOid()).equals(tradeOrder.getOid())) {
							
							if(!tradeOrderV2.getStateStr().equals("待发货")) {
								//如果订单不是待发货的订单则取消该订单，备注订单在有赞已发货
								//审核不通过
								params.put("id", tradeOrder.getId());
								params.put("orderReview", -2);
								params.put("bz", "该订单"+tradeOrderV2.getStateStr());
								tradeOrderMapper.updateByTradeOrderSelectMap(params);
							}
						}
					}
				}
			}
		}
	}

}
