package com.longlu.web;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longlu.kdt.api.KdtApiUtils;
import com.longlu.pojo.TradeDetail;
import com.longlu.pojo.TradeOrder;
import com.longlu.pojo.YouzanShopConfig;
import com.longlu.service.TradeDetailService;
import com.longlu.service.TradeOrderService;
import com.longlu.service.YouzanShopConfigService;
import com.longlu.util.ExcelUtilXSSF;
import com.longlu.util.excel.ExcelFormat;
import com.longlu.util.excel.ExcelMapFactory;
import com.longlu.util.excel.ExcelMapperFormat;
import com.longlu.youzan.YouZanServerUtils;
import com.youzan.open.sdk.client.core.YZClient;

/**     
* 类描述：   仓库管理
* 创建人：yagncan   
* 创建时间：2016年12月24日 下午8:16:07     
*/
@RequestMapping("/deport")
@Controller
public class DepotControler extends BaseControler{

	@Autowired
	private TradeDetailService tradeDetailService;
	@Autowired
	private TradeOrderService tradeOrderService;
	@Autowired
	private YouzanShopConfigService youzanShopConfigService;
	
	@RequestMapping("/deportOrders")
	public String deportOrdersList(@RequestParam HashMap<String, String> paramsMap,HashMap<String, Object> resultMap) {
		
		//查找已付款，财务已审核的订单列表
		//增加查询条件
		resultMap.put("status", "WAIT_SELLER_SEND_GOODS");
		//订单编号查询
		if(StringUtils.isNotBlank(paramsMap.get("tid"))) {
			resultMap.put("tid", paramsMap.get("tid").trim());
		}
		//店铺ID
		if(StringUtils.isNotBlank(paramsMap.get("shopId"))) {
			resultMap.put("shopId", paramsMap.get("shopId").trim());
		}
		//收件人查询
		if(StringUtils.isNotBlank(paramsMap.get("name"))) {
			resultMap.put("receiver_name", paramsMap.get("name").trim());
		}
		//排序
		if(StringUtils.isNotBlank(paramsMap.get("orderBy"))) {
			resultMap.put("orderBy", paramsMap.get("orderBy").trim());
		}
//		//收件人手机查询
//		if(StringUtils.isNotBlank(paramsMap.get("mobile"))) {
//			resultMap.put("receiver_mobile", paramsMap.get("mobile").trim());
//		}
		//支付时间
		resultMap.put("payTimeStart", paramsMap.get("payTimeStart"));
		//支付时间
		resultMap.put("payTimeEnd", paramsMap.get("payTimeEnd"));
		//供应商信息
		resultMap.put("supplierName", this.getUserSupplierName());
		
		//快递列表
//		resultMap.put("expressList", KdtApiUtils.expressGet());
		//前台
//		<#list expressList! as express>
//			<option value="${express.id!}">${express.name!}</option>
//		</#list>
		
		List<TradeDetail> tradeDetailList =  tradeDetailService.findTradeDetailListByOrders(resultMap, 1);
		//初始化分页信息
		//pagination.init(tradeDetailList.size(), pagination.getRows());
		resultMap.put("tradeDetailList", tradeDetailList);
		
		//获取审核总数
//		Map<String, Object> params = new HashMap<String,Object>();
//		params.put("supplierNames", resultMap.get("supplierName"));
//		params.put("orderReview", "1");
//		Long count = tradeOrderService.selectTradeOrderBySelectiveCount(params);
		resultMap.put("total", tradeDetailList.size());
		
		//获得店铺配置列表
		List<YouzanShopConfig> shopConfig = youzanShopConfigService.findAllUseShop();
		resultMap.put("shopConfig", shopConfig);
		return "deport/deportOrders";
	}
	
	@RequestMapping("/checkOrderExpress")
	@ResponseBody
	public Map<String, Object> checkOrderExpress(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
		if(paramsMap.get("tid") != null) {
			//仓库审核通过 发货
			resultMap = tradeDetailService.updateTradeOrderReview(paramsMap,2);
		}else {
			//仓库审核不通过 取消
			resultMap = tradeDetailService.updateTradeOrderReview(paramsMap,-2);
		}
		return resultMap;
	}
	
	
	/**
	 * 批量发货 如果后面需要不同店铺不同订单共用一个快递号需改动此方法
	 * @param paramsMap
	 * @param resultMap
	 * @return
	 */
	@RequestMapping("/batchSendGoods")
	@ResponseBody
	public Map<String, Object> batchSendGoods(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
		
		String orderIds = (String)paramsMap.get("orderIds");
		String expressCompany = (String)paramsMap.get("expressCompany");
		String expressNumber = (String)paramsMap.get("expressNumber");
		
		StringBuffer sb = new StringBuffer();
		StringBuffer idSb = new StringBuffer();
		StringBuffer oidSb = new StringBuffer();
		if(StringUtils.isNotBlank(orderIds)) {
			String[] tradeIds = orderIds.split(",");
			//获得第一个订单的订单号，如果后面的订单号和第一个订单的订单号不同则不能发货
			String firstTid = tradeIds[0].split("-")[1];
			String shopId = "";
			//每个子单开始校验
			for (int i = 0; i < tradeIds.length; i++) {
				//财务审核通过
				String[] tids = tradeIds[i].split("-");
				String id = tids[0];
				String tid = tids[1];
				String oid = tids[2];
				shopId = tids[3];
				
				if(!tid.equals(firstTid)) {
					sb.append("发货只能针对一个订单的多个子单,不能多个订单共一个快递单号!");
					resultMap.put("success", false);
					resultMap.put("msg", sb.toString());
					return resultMap;
				}else if(!tradeDetailService.checkOrderFeedback(tid, oid,shopId)) {
					sb.append("订单"+tid+",客户发起了维权,请处理后发货!");
					resultMap.put("success", false);
					resultMap.put("msg", sb.toString());
					return resultMap;
				}
				idSb.append(id).append(",");
				oidSb.append(oid).append(",");
			}
			
			//校验完成，进行发货业务处理
			HashMap<String, String> paramsConfirm = new HashMap<String, String>();
			paramsConfirm.put("tid", firstTid);
			paramsConfirm.put("oids", oidSb.substring(0,oidSb.length()-1));
			//快递ID
			paramsConfirm.put("out_stype", expressCompany);
			//快递单号
			paramsConfirm.put("out_sid", expressNumber);
			
			//通过shopId 获取店铺配置信息 如果后面需要不同店铺不同订单共用一个快递号需改动此方法
			YZClient client = youzanShopConfigService.getYzClient(Long.valueOf(shopId));
			boolean flag = YouZanServerUtils.youzanLogisticsOnlineConfirm(client,paramsConfirm);
			//boolean flag = KdtApiUtils.onlineConfirm(paramsConfirm);
			if(!flag) {
				resultMap.put("success", false);
				resultMap.put("msg", "订单"+firstTid+":发货失败!");
				return resultMap;
			}
			//更新表中发货状态
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ids", idSb.toString().split(","));
			params.put("orderReview", 2);
			params.put("expressCompany", paramsMap.get("expressCompany"));
			params.put("expressNumber", paramsMap.get("expressNumber"));
			tradeDetailService.batchUpdateByTradeOrderSelectMap(params);
			resultMap.put("success", true);
			resultMap.put("msg", "批量审核成功!");
		}else {
			resultMap.put("success", false);
			resultMap.put("msg", "请勾选待审核订单!");
		}
		
		return resultMap;
	}
	
	
	@RequestMapping("/synchroYouZanTrade")
	@ResponseBody
	public Map<String, Object> synchroYouZanTrade(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
		try {
			tradeDetailService.synchroYouZanTrade();
			resultMap.put("success", true);
			return resultMap;
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("msg", "一键同步失败!");
			return resultMap;
		}
	}
	
	
	/**
	* 方法名称: exprotDeportOrders
	* 方法描述：导出仓库审核订单
	* 参数 :
	* 返回类型: void
	 * @throws Exception 
	* @throws
	* 创建人：yagncan
	* 创建时间：2016年12月17日 下午11:33:03     
	*/
	@RequestMapping("/exprotDeportOrders")
	public void exprotDeportOrders(@RequestParam HashMap<String, Object> paramsMap ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//增加查询条件
		paramsMap.put("status", "WAIT_SELLER_SEND_GOODS");
		//订单编号查询
		paramsMap.put("tid", paramsMap.get("tid"));
		//收件人查询
		paramsMap.put("receiver_name", paramsMap.get("name"));
		//收件人手机查询
		paramsMap.put("receiver_mobile", paramsMap.get("mobile"));
		//支付时间
		paramsMap.put("payTimeStart", paramsMap.get("payTimeStart"));
		//支付时间
		paramsMap.put("payTimeEnd", paramsMap.get("payTimeEnd"));
		//供应商信息
		paramsMap.put("supplierName", this.getUserSupplierName());
		
		//查找已付款，财务未审核的订单列表
		List<TradeDetail> tradeDetailList =  tradeDetailService.findTradeDetailListByOrders(paramsMap, 1);
		
		if(CollectionUtils.isNotEmpty(tradeDetailList)) {
			List<TradeOrder> tradeOrderList = new ArrayList<TradeOrder>();
			for (TradeDetail t :tradeDetailList) {
				List<TradeOrder> torderList =  t.getOrders();
				for (TradeOrder tradeOrder : torderList) {
					tradeOrder.setBz(t.getBuyer_message());
					//收货信息
					tradeOrder.setReceiver_name(t.getReceiver_name());
					tradeOrder.setReceiver_mobile(t.getReceiver_mobile());
					tradeOrder.setReceiver_address(t.getReceiver_state()+" "+t.getReceiver_city()+" "+t.getReceiver_district()+" "+t.getReceiver_address());
				}
				tradeOrderList.addAll(torderList);
			}
			
			List<List<? extends Object>> exportList = new ArrayList<List<? extends Object>>();
			exportList.add(tradeDetailList);
			exportList.add(tradeOrderList);
			
			List<String> title = new ArrayList<String>();
			title.add("订单编号,交易状态,付款方式,支付时间,实付金额(元),收货人,运费");
			//title.add("客户,商品编号,商品规格,商品条码,商品名称,数量,实付总金额(元),备注(订单号),收货地址,订单支付时间,留言");
			title.add("客户,商品编号,商品条码,商品名称,商品规格,数量,实付总金额(元),备注(订单号),收货人姓名,收货人电话,收货地址,留言,订单支付时间");
			
			List<String> field = new ArrayList<String>();
			field.add("tid,status,pay_type,pay_time,payment,receiver_name,post_fee");
			//field.add("supplierName,num_iid,sku_properties_name,goodsNumber,title,num,payment,tid,payAddress,payTime,bz");
			field.add("supplierName,num_iid,goodsNumber,title,sku_properties_name,num,payment,tid,receiver_name,receiver_mobile,receiver_address,bz,payTime");
			
			Map<String,ExcelFormat> changeAttr = new HashMap<String,ExcelFormat>();
			//交易状态
			changeAttr.put("status", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailStatus()));
			//付款方式
			changeAttr.put("pay_type", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailPayType()));
			//调用导出工具类
			ByteArrayOutputStream bos = ExcelUtilXSSF.getInstance().exportExcelOutputStreamByReflect(exportList, title, field, changeAttr);
	        response.reset(); // 非常重要
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=deportOrder.xlsx;");
	        OutputStream out = response.getOutputStream();
	        out.write(bos.toByteArray());
	        out.flush();
	        out.close();
		}else {
			//重定向到列表页面
			response.sendRedirect("/innerbuy/deport/deportOrders.do");
		}
	}
}
