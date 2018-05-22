package com.longlu.kdt.api;

/**     
* 类描述：   有赞api方法汇总
* 创建人：yagncan   
* 创建时间：2016年12月13日 下午9:32:13     
*/
public class KdtApiItem {

	/******************交易接口*******************/
	//查询卖家已卖出的交易列表
	public static String TRADES_SOLD_GET = "kdt.trades.sold.get";
	
	//获得单笔交易列表
	public static String TRADES_GET = "kdt.trade.get";
	
	/******************商品接口*******************/
	//新增一个商品
	public static String ITEM_ADD = "kdt.item.add";
	
	public static final String APP_ID = "4397f02f143e73474e"; //有赞app_id
	public static final String APP_SECRET = "b6d8b4a7f91ddc9041086747d5e895c1"; //有赞app_secret
}
