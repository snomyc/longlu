package com.longlu.youzan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.longlu.enums.TradeTimeEnum;
import com.longlu.kdt.api.KdtApiItem;
import com.longlu.pojo.TradeDetail;
import com.longlu.pojo.TradeOrder;
import com.longlu.util.Arith;
import com.longlu.util.DateFormatHelper;
import com.longlu.util.DateTool;
import com.longlu.util.pagination.Pagination;
import com.youzan.open.sdk.client.auth.Sign;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanLogisticsExpressGet;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanLogisticsOnlineConfirm;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradeGet;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradesSoldGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanItemsOnsaleGetResult.ItemListOpenModel;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanLogisticsExpressGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanLogisticsExpressGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanLogisticsOnlineConfirmParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanLogisticsOnlineConfirmResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult.TradeDetailV2;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult.TradeOrderV2;
import com.youzan.open.sdk.model.Response;

/**
 * @author Administrator
 * 有赞第三方v3.0接口测试
 */
public class YouZanTest {
	
//	public static void getGoodsDetail(Pagination pagination) {
//		System.out.println("开始查询第"+pagination.getPage()+"页");
//		ItemListOpenModel[]  detail = YouZanServerUtils.youzanItemsOnsaleGet(pagination, null);
//		if(detail != null && detail.length >0) {
//			//业务处理
//			for (ItemListOpenModel model : detail) {
////				if(goodsDetail.getSkus() !=null && goodsDetail.getSkus().length >0) {
////					for (GoodsSku goodsSku :goodsDetail.getSkus()) {
////						System.out.println(goodsDetail.getTitle()+"-"+goodsSku.getOuterId());
////					}
////				}else {
////					System.out.println(goodsDetail.getTitle()+"-"+goodsDetail.getOuterId());
////				}
//				System.out.println("商品id:"+model.getItemId()+",商品别名:"+model.getAlias()+",商品标题:"+model.getTitle());
//			}
//			//继续查找下一页数据
//			pagination.setPage(pagination.getPage()+1);
//			getGoodsDetail(pagination);
//		}
//	}

	public static void main(String[] args) {
//		String appKey = "4397f02f143e73474e";
//		String appSecret = "b6d8b4a7f91ddc9041086747d5e895c1";
//		
//		YZClient client = new DefaultYZClient(new Sign(appKey, appSecret));
		
		//获取快递公司的列表
//		YouzanLogisticsExpressGetParams youzanLogisticsExpressGetParams = new YouzanLogisticsExpressGetParams();
//		YouzanLogisticsExpressGet youzanLogisticsExpressGet = new YouzanLogisticsExpressGet();
//		youzanLogisticsExpressGet.setAPIParams(youzanLogisticsExpressGetParams);
//		YouzanLogisticsExpressGetResult result = client.invoke(youzanLogisticsExpressGet);
//		System.out.println(result);
		//确认发货的目的是让交易流程继续走下去，确认发货后交易状态会由【买家已付款】变为【卖家已发货】
//		YouzanLogisticsOnlineConfirmParams youzanLogisticsOnlineConfirmParams = new YouzanLogisticsOnlineConfirmParams();
//		YouzanLogisticsOnlineConfirm youzanLogisticsOnlineConfirm = new YouzanLogisticsOnlineConfirm();
//		youzanLogisticsOnlineConfirm.setAPIParams(youzanLogisticsOnlineConfirmParams);
//		YouzanLogisticsOnlineConfirmResult result = client.invoke(youzanLogisticsOnlineConfirm);
		
		//查询正在出售的商品列表
//		Pagination pagination = new Pagination();
//		pagination.setRows(50);
//		pagination.setPage(0);
//		YouZanTest.getGoodsDetail(pagination);
		
		//查询卖家已卖出的交易列表 已付款待发货订单
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("status", "TRADE_CLOSED");
//		map.put("start_update", DateTool.getBeforeDate(-30));
//		map.put("end_update", new Date());
//		String app_id = "9f543932371a653ba6";
//		String app_app_secret ="341a0b2b2ccec6a0ac39d335f5c88e47";
//		List<TradeDetail> detail = YouZanServerUtils.youzanTradesSoldGet(app_id,app_app_secret,map);
//		for (TradeDetail td : detail) {
//			System.out.println("父订单:"+td.getTid());
//			for (TradeOrder order : td.getOrders()) {
//				System.out.println("\t子订单:"+order.getTid()+"-"+order.getTitle());
//			}
//			
//		}
		
//		List<TradeDetail> detail = YouZanServerUtils.youzanTradesSoldGetTradeBuyerSigned();
//		for (TradeDetail td : detail) {
//			System.out.println("父订单:"+td.getTid());
//			for (TradeOrder order : td.getOrders()) {
//				System.out.println("\t子订单:"+order.getTid()+"-"+order.getTitle());
//			}
//			
//		}
		
		YZClient client = client = new DefaultYZClient(new Sign("cd891980d153be63ba","1c9b20679e4b267f1f87c239a6aaf042"));
		
		//获取已支付待发货订单
		List<TradeDetail> tradeDetail = YouZanServerUtils.youzanTradesSoldGetWaitSendGoods(client,"2","test");
		System.out.println(tradeDetail.size());
		
		
//		YouzanTradeGetParams youzanTradeGetParams = new YouzanTradeGetParams();
//		youzanTradeGetParams.setTid("E20180312153645025700003");
//		YZClient client = new DefaultYZClient(new Token("f40711c921e53ab393a2d0bb63324729"));
//		com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetResult.TradeDetailV2 detail= YouZanServerUtils.youzanTradeGet(client,youzanTradeGetParams);
//		
//		//订单类型
//		System.out.println(detail.getRelationType());
//		
//		for (com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradeGetResult.TradeOrderV2 to : detail.getOrders()) {
//			System.out.println(to.getPayment());
//			System.out.println(Arith.setScale(to.getPayment().toString(), 2));
//			
//			System.out.println(to.getPayment().doubleValue());
//			if(to.getItemType() == 10L) {
//				System.out.println("分销商品");
//			}else {
//				System.out.println("非分销商品");
//			}
//			
//		}
		
		
	}
}
