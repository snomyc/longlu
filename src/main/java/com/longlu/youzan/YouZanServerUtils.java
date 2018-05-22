package com.longlu.youzan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.longlu.pojo.TradeDetail;
import com.longlu.pojo.TradeOrder;
import com.longlu.util.Arith;
import com.longlu.util.DateFormatHelper;
import com.longlu.util.DateTool;
import com.longlu.util.HttpClientHelper;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanLogisticsOnlineConfirm;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanShopGet;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradeGet;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradesSoldGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanLogisticsOnlineConfirmParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanLogisticsOnlineConfirmResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult.TradeDetailV2;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult.TradeOrderV2;
public class YouZanServerUtils {
	
	/**
	 * 通过店铺授权code,获取店铺token等信息
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static String youzanAuthToken(String code) throws Exception {
		//获取token请求地址
		String url = "https://open.youzan.com/oauth/token";
		
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("client_id", "bd610ddc096cb03c89");
		parameters.put("client_secret", "7129651ae12f8059d0c66859a520f8ed");
		parameters.put("grant_type", "authorization_code");
		parameters.put("code", code);
		parameters.put("redirect_uri", "http://snomyc.51mypc.cn/innerbuy/youzan_auth.do");
		String jsonResult = HttpClientHelper.httpPost(url, parameters);
		return jsonResult;
	}
	
	/**
	 * 刷新token信息
	 * @param refreshToken
	 * @return
	 * @throws Exception
	 */
	public static String youzanRefreshToken(String refreshToken) throws Exception {
		String url = "https://open.youzan.com/oauth/token";
		
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("grant_type", "refresh_token");
		parameters.put("refresh_token", refreshToken);
		parameters.put("client_id", "bd610ddc096cb03c89");
		parameters.put("client_secret", "7129651ae12f8059d0c66859a520f8ed");
		String jsonResult = HttpClientHelper.httpPost(url, parameters);
		//返回json
		//access_token	String	是	可用于调用API的 access_token
		//expires_in	Number	是	access_token 的有效时长，单位：秒（过期时间：7天）
		//token_type	String	否	令牌类型
		//scope	String	否	access_token 最终的访问范围
		//refresh_token	String	否	用于刷新令牌 access_token 的 refresh_token（过期时间：28 天）
		return jsonResult;
	}
	
	/**
	 * 获取店铺基础信息
	 * @param token
	 * @return
	 */
	public static YouzanShopGetResult youzanShopGet(String token){
		YZClient client = new DefaultYZClient(new Token(token));
		YouzanShopGetParams youzanShopGetParams = new YouzanShopGetParams();
		YouzanShopGet youzanShopGet = new YouzanShopGet();
		youzanShopGet.setAPIParams(youzanShopGetParams);
		YouzanShopGetResult result = client.invoke(youzanShopGet);
		return result;
	}
	
	
	/**
	 * @param params
	 * @return
	 * 方法说明:youzan.logistics.online.confirm
	 *       确认发货的目的是让交易流程继续走下去，确认发货后交易状态会由【买家已付款】变为【卖家已发货】
	 * 创立日期:2017-12-25  下午12:34:11
	 * 创建人:yangcan
	 */
	public static boolean youzanLogisticsOnlineConfirm(YZClient client,HashMap<String, String> params) {
		YouzanLogisticsOnlineConfirmParams youzanLogisticsOnlineConfirmParams = new YouzanLogisticsOnlineConfirmParams();

		//tid 交易订单号
		youzanLogisticsOnlineConfirmParams.setTid(params.get("tid"));
		//oids 如果需要拆单发货，使用该字段指定要发货的商品交易明细编号，多个明细编号用半角逗号“,”分隔； 不需要拆单发货，则该字段不传或值为空；
		youzanLogisticsOnlineConfirmParams.setOids(params.get("oids"));
		//out_stype 物流公司编号，可以通过请求 youzan.logistics.express.get 该接口获得
		youzanLogisticsOnlineConfirmParams.setOutStype(params.get("out_stype"));
		//out_sid 快递单号（具体一个物流公司的真实快递单号）
		youzanLogisticsOnlineConfirmParams.setOutSid(params.get("out_sid"));

		YouzanLogisticsOnlineConfirm youzanLogisticsOnlineConfirm = new YouzanLogisticsOnlineConfirm();
		youzanLogisticsOnlineConfirm.setAPIParams(youzanLogisticsOnlineConfirmParams);
		YouzanLogisticsOnlineConfirmResult result = client.invoke(youzanLogisticsOnlineConfirm);
		return result.getIsSuccess();
	}
	
	/**
	 * 获取正在出售的商品列表 youzan.items.onsale.get
	 * @param pagination
	 * @param goodsName
	 * @return
	 */
//	public static ItemListOpenModel[] youzanItemsOnsaleGet(Pagination pagination,String goodsName) {
//		//增加查询条件
//		YouzanItemsOnsaleGetParams youzanItemsOnsaleGetParams = new YouzanItemsOnsaleGetParams();
//		youzanItemsOnsaleGetParams.setPageSize((long)pagination.getRows());
//		youzanItemsOnsaleGetParams.setPageNo((long)pagination.getPage());
//		youzanItemsOnsaleGetParams.setQ(goodsName);
//		//商品分类排序 0普通商品 10分销商品
//		//kdtItemsOnsaleGetParams.setOrderBy("num");
//		youzanItemsOnsaleGetParams.setOrderBy("item_type:asc");
//		
//		YouzanItemsOnsaleGet youzanItemsOnsaleGet = new YouzanItemsOnsaleGet();
//		youzanItemsOnsaleGet.setAPIParams(youzanItemsOnsaleGetParams);
//		YouzanItemsOnsaleGetResult result = client.invoke(youzanItemsOnsaleGet);
//		return result.getItems();
//	}
	
	
	/**
	 * 更新商品信息 youzan.item.update
	 * @param numIid
	 * @param quantity
	 * @param resultMap
	 * @return
	 */
//	public static Map<String, Object> youzanItemUpdate(String numIid,String quantity,Map<String, Object> resultMap) {
//		
//		YouzanItemUpdateParams youzanItemUpdateParams = new YouzanItemUpdateParams();
//		youzanItemUpdateParams.setItemId(Long.valueOf(numIid));
//		youzanItemUpdateParams.setQuantity(Long.valueOf(quantity));
//		
//		YouzanItemUpdate youzanItemUpdate = new YouzanItemUpdate();
//		youzanItemUpdate.setAPIParams(youzanItemUpdateParams);
//		YouzanItemUpdateResult result = client.invoke(youzanItemUpdate);
//		if(result.getIsSuccess()) {
//			resultMap.put("success", true);
//			resultMap.put("msg", "更新商品信息成功!");
//		}else {
//			resultMap.put("success", false);
//			resultMap.put("msg", "更新商品信息失败,请联系管理员!");
//		}
//		return resultMap;
//	}
	
	/**
	 * 更新SKU youzan.item.sku.update
	 * @param numIid
	 * @param skuId
	 * @param quantity
	 * @param resultMap
	 * @return
	 */
//	public static Map<String, Object> youzanItemSkuUpdate(String numIid,String skuId,String quantity,Map<String, Object> resultMap) {
//		
//		YouzanItemSkuUpdateParams youzanItemSkuUpdateParams = new YouzanItemSkuUpdateParams();
//		youzanItemSkuUpdateParams.setItemId(Long.valueOf(numIid));
//		youzanItemSkuUpdateParams.setSkuId(Long.valueOf(skuId));
//		youzanItemSkuUpdateParams.setQuantity(quantity);
//
//		YouzanItemSkuUpdate youzanItemSkuUpdate = new YouzanItemSkuUpdate();
//		youzanItemSkuUpdate.setAPIParams(youzanItemSkuUpdateParams);
//		YouzanItemSkuUpdateResult result = client.invoke(youzanItemSkuUpdate);
//		
//		if(result.getIsSuccess()) {
//			resultMap.put("success", true);
//			resultMap.put("msg", "更新商品信息成功!");
//		}else {
//			resultMap.put("success", false);
//			resultMap.put("msg", "更新商品信息失败,请联系管理员!");
//		}
//		return resultMap;
//	}
	
	
	/**
	 * https://www.youzanyun.com/apilist/detail/group_trade/trade/youzan.trade.get
	 * 获取单笔交易的信息  youzan.trade.get
	 * @param youzanTradeGetParams
	 * @return
	 * @author yangcan
	 */
	public static com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetResult.TradeDetailV2 youzanTradeGet(YZClient client,YouzanTradeGetParams youzanTradeGetParams) {
		YouzanTradeGet youzanTradeGet = new YouzanTradeGet();
		youzanTradeGet.setAPIParams(youzanTradeGetParams);
		YouzanTradeGetResult result = client.invoke(youzanTradeGet);
		return result.getTrade();
	}
	
	
	
	/**
	 * 查询卖家已卖出的交易列表(根据条件获得订单列表)  youzan.trades.sold.get
	 * @param paramMap
	 * @return
	 */
	public static List<TradeDetail>  youzanTradesSoldGet(YZClient client,Map<String, Object> params,String shopId,String shopName) {
		
		YouzanTradesSoldGetParams youzanTradesSoldGetParams = new YouzanTradesSoldGetParams();

		//设置默认分页
		youzanTradesSoldGetParams.setPageSize(300L);
		//设置默认页码 从1开始
		youzanTradesSoldGetParams.setPageNo(1L);
		if(params != null) {
			//设置 订单状态
			youzanTradesSoldGetParams.setStatus(params.get("status").toString());
			
			//设置订单的交易创建时间，结束时间
			youzanTradesSoldGetParams.setStartCreated((Date)params.get("start_created"));
			//设置订单的交易结束时间
			youzanTradesSoldGetParams.setEndCreated((Date)params.get("end_created"));
			
			//设置订单交易状态更新的开始时间
			youzanTradesSoldGetParams.setStartUpdate((Date)params.get("start_update"));
			//设置订单交易状态更新的结束时间
			youzanTradesSoldGetParams.setEndUpdate((Date)params.get("end_update"));
		}

		YouzanTradesSoldGet youzanTradesSoldGet = new YouzanTradesSoldGet();
		youzanTradesSoldGet.setAPIParams(youzanTradesSoldGetParams);
		YouzanTradesSoldGetResult result = client.invoke(youzanTradesSoldGet);
		TradeDetailV2[] detailV2 = result.getTrades();
		System.out.println(result.getTotalResults());
		return youzanTradeToTradeDetail(detailV2,shopId,shopName);
	}
	
	
	/**
	 * 查询卖家已卖出的交易列表(已付款待发货订单列表)  youzan.trades.sold.get WAIT_SELLER_SEND_GOODS
	 * @param paramMap
	 * @return
	 */
	public static List<TradeDetail>  youzanTradesSoldGetWaitSendGoods(YZClient client,String shopId,String shopName) {
		YouzanTradesSoldGetParams youzanTradesSoldGetParams = new YouzanTradesSoldGetParams();

		//设置默认分页
		youzanTradesSoldGetParams.setPageSize(100L);
		//设置默认页码 从1开始
		youzanTradesSoldGetParams.setPageNo(1L);
		
		//设置 订单状态
		youzanTradesSoldGetParams.setStatus("WAIT_SELLER_SEND_GOODS");

		YouzanTradesSoldGet youzanTradesSoldGet = new YouzanTradesSoldGet();
		youzanTradesSoldGet.setAPIParams(youzanTradesSoldGetParams);
		YouzanTradesSoldGetResult result = client.invoke(youzanTradesSoldGet);
		TradeDetailV2[] detailV2 = result.getTrades();
		return youzanTradeToTradeDetail(detailV2,shopId,shopName);
	}
	
	/**
	 * 查询卖家已卖出的交易列表(最近5天交易成功订单列表)  youzan.trades.sold.get  TRADE_BUYER_SIGNED
	 * @param paramMap
	 * @return
	 */
	public static List<TradeDetail>  youzanTradesSoldGetTradeBuyerSigned(YZClient client,String shopId,String shopName) {
		YouzanTradesSoldGetParams youzanTradesSoldGetParams = new YouzanTradesSoldGetParams();

		//设置默认分页
		youzanTradesSoldGetParams.setPageSize(100L);
		//设置默认页码 从1开始
		youzanTradesSoldGetParams.setPageNo(1L);
		
		//设置 订单状态 交易成功订单
		youzanTradesSoldGetParams.setStatus("TRADE_BUYER_SIGNED");
		
		//设置交易更新时间，及最后一次的更新时间(交易成功时间) ,获取最近3天的订单信息
		youzanTradesSoldGetParams.setStartUpdate(DateTool.getBeforeDate(-3));
		youzanTradesSoldGetParams.setEndUpdate(new Date());

		YouzanTradesSoldGet youzanTradesSoldGet = new YouzanTradesSoldGet();
		youzanTradesSoldGet.setAPIParams(youzanTradesSoldGetParams);
		YouzanTradesSoldGetResult result = client.invoke(youzanTradesSoldGet);
		TradeDetailV2[] detailV2 = result.getTrades();
		System.out.println(result.getTotalResults());
		return youzanTradeToTradeDetail(detailV2,shopId,shopName);
	}
	
	
	/**
	 * 将对象转成内购平台TradeDetail对象
	 * @param detailV2
	 * @return
	 */
	public static List<TradeDetail> youzanTradeToTradeDetail(TradeDetailV2[] detailV2,String shopId,String shopName) {
		//将对象转成内购平台TradeDetail对象
		List<TradeDetail> detailList = new ArrayList<TradeDetail>();
		StringBuffer sb = new StringBuffer();
		for (TradeDetailV2 td : detailV2) {
			//只有无维权和维权结束的付款订单才写入DB
    		if(td.getFeedback() == 0 || td.getFeedback() == 110) {
    			TradeDetail t = new TradeDetail();
    			t.setBuyer_area(td.getBuyerArea());
    			t.setNum(td.getNum().intValue());
    			t.setType(td.getType());
    			t.setTid(td.getTid());
    			t.setFeedback(td.getFeedback().intValue());
    			t.setPrice(Arith.setScale(td.getPrice().toString(), 2));
    			t.setTotal_fee(Arith.setScale(td.getTotalFee().toString(), 2));
    			t.setPayment(Arith.setScale(td.getPayment().toString(), 2));
    			t.setBuyer_message(td.getBuyerMessage());
    			t.setCreated(DateFormatHelper.parseDate(td.getCreated(), DateFormatHelper.YYYY_MM_DD_HH_MM_SS));
    			t.setPay_time(DateFormatHelper.parseDate(td.getPayTime(), DateFormatHelper.YYYY_MM_DD_HH_MM_SS));
    			t.setRefund_state(td.getRefundState());
    			t.setStatus(td.getStatus());
    			t.setPost_fee(Arith.setScale(td.getPostFee().toString(), 2));
    			t.setPic_thumb_path(td.getPicThumbPath());
    			t.setRefunded_fee(Arith.setScale(td.getRefundedFee().toString(), 2));
    			//item_id 该字段返参为null
    			//t.setNum_iid(td.getItemId().toString());
    			t.setTitle(td.getTitle());
    			t.setReceiver_zip(td.getReceiverZip());
    			t.setPay_type(td.getPayType());
    			t.setPic_path(td.getPicPath());
    			t.setReceiver_mobile(td.getReceiverMobile());
    			t.setSign_time(DateFormatHelper.parseDate(td.getSignTime(), DateFormatHelper.YYYY_MM_DD_HH_MM_SS));
    			t.setOuter_tid(td.getOuterTid());
    			t.setShipping_type(td.getShippingType());
    			//收货信息可能出现隐藏\n数据库的情况
    			t.setReceiver_state(td.getReceiverState().replace("\n", ""));
    			t.setReceiver_city(td.getReceiverCity().replace("\n", ""));
    			t.setReceiver_district(td.getReceiverDistrict().replace("\n", ""));
    			t.setReceiver_address(td.getReceiverAddress().replace("\n", ""));
    			t.setReceiver_name(td.getReceiverName().replace("\n", ""));
    			
    			//添加店铺，锁定订单归属
    			t.setShopId(shopId);
    			t.setShopName(shopName);
    			
    			//子订单
    			//如果该父订单下面有子订单
    			List<TradeOrderV2> orderV2 = Arrays.asList(td.getOrders());
    			if( CollectionUtils.isNotEmpty(orderV2) ) {
    				List<TradeOrder> orderList = new ArrayList<TradeOrder>();
    				for (TradeOrderV2 to : orderV2) {
    					TradeOrder o = new TradeOrder();
    					o.setTid(t.getTid());
    					o.setPic_thumb_path(to.getPicThumbPath());
    					o.setNum(to.getNum().intValue());
    					o.setRefunded_fee(Arith.setScale(to.getRefundedFee().toString(), 2));
    					//暂时废弃
    					//o.setNum_iid(to.getItemId().toString());
    					o.setOid(String.valueOf(to.getOid()));
    					o.setTitle(to.getTitle());
    					o.setFenxiao_payment(Arith.setScale(to.getFenxiaoPayment().toString(), 2));
    					o.setDiscount_fee(Arith.setScale(to.getDiscountFee().toString(), 2));
    					o.setPrice(Arith.setScale(to.getPrice().toString(), 2));
    					o.setFenxiao_price(Arith.setScale(to.getFenxiaoPrice().toString(), 2));
    					o.setTotal_fee(Arith.setScale(to.getTotalFee().toString(), 2));
    					o.setPayment(Arith.setScale(to.getPayment().toString(), 2));
    					o.setSku_unique_code(to.getSkuUniqueCode());
    					o.setSku_id(String.valueOf(to.getSkuId()));
    					o.setSku_properties_name(to.getSkuPropertiesName());
    					o.setPic_path(to.getPicPath());
    					o.setItem_refund_state(to.getItemRefundState());
    					o.setState_str(to.getStateStr());
    					o.setAllow_send(to.getAllowSend().intValue());
    					o.setSeller_nick(to.getSellerNick());
    					o.setOuter_item_id(to.getOuterItemId());
    					o.setOuter_sku_id(to.getOuterSkuId());
    					
    					//添加店铺，锁定订单归属
    	    			o.setShopId(shopId);
    	    			o.setShopName(shopName);
    					
    					//判断是否是分销订单，如果是分销订单的话，那么供应商-商品编码 为 分销-00000
    					if(to.getItemType() == 10L) {
    						//供应商信息
	        				o.setSupplierName("分销");
        					//写入商品编码
        					o.setGoodsNumber("00000");
    					}else {
	    					//供应商信息，商品编码
	    					//解析商品编码获取供应商信息
	        				String outer_sku_id = to.getOuterSkuId().trim();
	        				
	        				if(StringUtils.isNotBlank(outer_sku_id)) {
	        					String[] supplier = outer_sku_id.split("-",2);
	        					if(supplier.length > 1) {
		        					//供应商信息
	    	        				o.setSupplierName(supplier[0]);
		        					//写入商品编码
		        					o.setGoodsNumber(supplier[1]);
		        				}else {
		        					//从outer_item_id中获取供应商，商品条码信息
		        					String outer_item_id = to.getOuterItemId().trim();
		        					if(StringUtils.isNotBlank(outer_item_id)) {
		        						supplier = outer_item_id.split("-",2);
	    	        					
	        	        				if(supplier.length > 1) {
	        	        					//供应商信息
	            	        				o.setSupplierName(supplier[0]);
	        	        					//写入商品编码
	        	        					o.setGoodsNumber(supplier[1]);
	        	        				}else {
	        	        					//供应商信息
	            	        				o.setSupplierName("分销");
	        	        					//写入商品编码
	        	        					o.setGoodsNumber("00000");
	        	        				}
		        					} else {
		        						//供应商信息
	        	        				o.setSupplierName("分销");
	    	        					//写入商品编码
	    	        					o.setGoodsNumber("00000");
		        					}
		        				}
	        					
	        				}else {
		        				//从outer_item_id中获取供应商，商品条码信息
	        					String outer_item_id = to.getOuterItemId().trim();
	        					if(StringUtils.isNotBlank(outer_item_id)) {
	        						String[] supplier = outer_item_id.split("-",2);
		        					
	    	        				if(supplier.length > 1) {
	    	        					//供应商信息
	        	        				o.setSupplierName(supplier[0]);
	    	        					//写入商品编码
	    	        					o.setGoodsNumber(supplier[1]);
	    	        				}else {
	    	        					//供应商信息
	        	        				o.setSupplierName("分销");
	    	        					//写入商品编码
	    	        					o.setGoodsNumber("00000");
	    	        				}
	        					} else {
	        						//供应商信息
	    	        				o.setSupplierName("分销");
		        					//写入商品编码
		        					o.setGoodsNumber("00000");
	        					}
	        				}
    					}
    					
    					//获得订单送货地址
        				sb.append(t.getReceiver_state()).append("-");
        				sb.append(t.getReceiver_city()).append("-");
        				sb.append(t.getReceiver_district()).append("-");
        				sb.append(t.getReceiver_address()).append("-");
        				sb.append(t.getReceiver_name()).append("-");
        				sb.append(t.getReceiver_mobile()).append("-");
        				o.setPayAddress(sb.toString());
        				sb.delete(0, sb.length()-1);
    					
    					orderList.add(o);
					}
    				//添加子订单
    				t.setOrders(orderList);
    			}
    			detailList.add(t);
    		}
		}
		return detailList;
	}
	
}
