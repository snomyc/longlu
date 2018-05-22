package com.longlu.util.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangcan
 * 公用需要映射的键值对map工厂类
 */
public class ExcelMapFactory {

	/**
	* 方法名称: getTradeDetailStatus
	* 方法描述：获得交易状态转换属性
	* 参数 :@return
	* 返回类型: Map<String,Object>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月26日 下午9:49:54     
	*/
	public static Map<String,Object> getTradeDetailStatus() {
		Map<String,Object> statusMap = new HashMap<String,Object>();
		statusMap.put("WAIT_BUYER_PAY", "买家待付款");
		statusMap.put("WAIT_SELLER_SEND_GOODS", "买家已付款");
		statusMap.put("TRADE_BUYER_SIGNED", "交易成功");
		return statusMap;
	}
	
	
	/**
	* 方法名称: getTradeDetailPayType
	* 方法描述：支付类型
	* 参数 :@return
	* 返回类型: Map<String,Object>
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月26日 下午9:53:05     
	*/
	public static Map<String,Object> getTradeDetailPayType() {
		Map<String,Object> payMap = new HashMap<String,Object>();
		payMap.put("WEIXIN", "微信自有支付");
		payMap.put("WEIXIN_DAIXIAO", "微信代销支付");
		payMap.put("ALIPAY", "支付宝支付");
		payMap.put("BANKCARDPAY", "银行卡支付");
		payMap.put("PEERPAY", "代付");
		payMap.put("CODPAY", "货到付款");
		payMap.put("BAIDUPAY", "百度钱包支付");
		payMap.put("PRESENTTAKE", "直接领取赠品");
		payMap.put("COUPONPAY", "优惠券/码全额抵扣");
		payMap.put("BULKPURCHASE", "来自分销商的采购");
		payMap.put("MERGEDPAY", "合并付货款");
		payMap.put("ECARD", "有赞E卡支付");
		payMap.put("NO_PAY", "未支付");
		return payMap;
	}
	
}
