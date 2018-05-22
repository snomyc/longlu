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

import com.longlu.pojo.TradeDetail;
import com.longlu.pojo.TradeOrder;
import com.longlu.pojo.YouzanShopConfig;
import com.longlu.service.AliOrderCodeService;
import com.longlu.service.TradeDetailService;
import com.longlu.service.TradeOrderService;
import com.longlu.service.YouzanShopConfigService;
import com.longlu.util.ExcelUtilXSSF;
import com.longlu.util.excel.ExcelFormat;
import com.longlu.util.excel.ExcelMapFactory;
import com.longlu.util.excel.ExcelMapperFormat;

/**     
* 类描述：   财务管理
* 创建人：yagncan   
* 创建时间：2016年12月24日 下午3:59:54     
*/
@RequestMapping("/finance")
@Controller
public class FinanceControler extends BaseControler{

	@Autowired
	private TradeDetailService tradeDetailService;
	@Autowired
	private TradeOrderService tradeOrderService;
	
	@Autowired
	private YouzanShopConfigService youzanShopConfigService;
	
	@RequestMapping("/financeOrderReview")
	public String financeOrderReviewList(@RequestParam HashMap<String, String> paramsMap,HashMap<String, Object> resultMap) {
		
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
		//收件人,手机查询
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
		
		
		//查找已付款，财务未审核的订单列表
		List<TradeDetail> tradeDetailList =  tradeDetailService.findTradeDetailListByOrders(resultMap, 0);
		//初始化分页信息
		//pagination.init(tradeDetailList.size(), pagination.getRows());
		resultMap.put("tradeDetailList", tradeDetailList);
		
		//获取审核总数
//		Map<String, Object> params = new HashMap<String,Object>();
//		params.put("supplierNames", resultMap.get("supplierName"));
//		params.put("orderReview", "0");
//		Long count = tradeOrderService.selectTradeOrderBySelectiveCount(params);
		resultMap.put("total", tradeDetailList.size());
		
		//获得店铺配置列表
		List<YouzanShopConfig> shopConfig = youzanShopConfigService.findAllUseShop();
		resultMap.put("shopConfig", shopConfig);
		return "finance/orderReview";
	}
	
	@RequestMapping("/orderCheckPass")
	@ResponseBody
	public Map<String, Object> orderCheckPass(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
		if(paramsMap.get("tid") != null) {
			//财务审核通过
			resultMap = tradeDetailService.updateTradeOrderReview(paramsMap,1);
		}else {
			//财务审核不通过
			resultMap = tradeDetailService.updateTradeOrderReview(paramsMap,-1);
		}
		return resultMap;
	}
	
	
	@RequestMapping("/batchOrderCheckPass")
	@ResponseBody
	public Map<String, Object> batchOrderCheckPass(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
		String orderIds = (String)paramsMap.get("orderIds");
		if(StringUtils.isNotBlank(orderIds)) {
			String[] tradeIds = orderIds.split(",");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < tradeIds.length; i++) {
				//财务审核通过
				String[] tids = tradeIds[i].split("-");
				paramsMap.put("id", tids[0]);
				paramsMap.put("tid", tids[1]);
				paramsMap.put("oid", tids[2]);
				paramsMap.put("shopId", tids[3]);
				resultMap = tradeDetailService.updateTradeOrderReview(paramsMap,1);
				if(!(Boolean)resultMap.get("success")) {
					sb.append("<br/>");
					sb.append(resultMap.get("msg"));
				}
			}
			
			if(StringUtils.isNotBlank(sb.toString())) {
				resultMap.put("success", false);
				resultMap.put("msg", sb.toString());
			} else {
				resultMap.put("success", true);
				resultMap.put("msg", "批量审核成功!");
			}
			
		}else {
			resultMap.put("success", false);
			resultMap.put("msg", "请勾选待审核订单!");
		}
		return resultMap;
	}
	
	@RequestMapping("/batchOrderCheckNoPass")
	@ResponseBody
	public Map<String, Object> batchOrderCheckNoPass(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
		String orderIds = (String)paramsMap.get("orderIds");
		if(StringUtils.isNotBlank(orderIds)) {
			String[] tradeIds = orderIds.split(",");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < tradeIds.length; i++) {
				//财务审核通过
				String[] tids = tradeIds[i].split("-");
				paramsMap.put("id", tids[0]);
				resultMap = tradeDetailService.updateTradeOrderReview(paramsMap,-1);
				if(!(Boolean)resultMap.get("success")) {
					sb.append("<br/>");
					sb.append(resultMap.get("msg"));
				}
			}
			
			if(StringUtils.isNotBlank(sb.toString())) {
				resultMap.put("success", false);
				resultMap.put("msg", sb.toString());
			} else {
				resultMap.put("success", true);
				resultMap.put("msg", "批量审核成功!");
			}
			
		}else {
			resultMap.put("success", false);
			resultMap.put("msg", "请勾选待审核订单!");
		}
		return resultMap;
	}
	
	
	
	/**
	* 方法名称: exprotFinanceOrders
	* 方法描述：导出财务审核订单
	* 参数 :
	* 返回类型: void
	 * @throws Exception 
	* @throws
	* 创建人：yagncan
	* 创建时间：2016年12月17日 下午11:33:03     
	*/
	@RequestMapping("/exprotFinanceOrders")
	public void exprotFinanceOrders(@RequestParam HashMap<String, Object> paramsMap ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//增加查询条件
		paramsMap.put("status", "WAIT_SELLER_SEND_GOODS");
		//订单编号查询
		paramsMap.put("tid", paramsMap.get("tid"));
		
		//收件人,手机查询
		if(StringUtils.isNotBlank(paramsMap.get("name").toString())) {
			paramsMap.put("receiver_name", paramsMap.get("name"));
		}
//		//收件人手机查询
//		paramsMap.put("receiver_mobile", paramsMap.get("mobile"));
		//支付时间
		paramsMap.put("payTimeStart", paramsMap.get("payTimeStart"));
		//支付时间
		paramsMap.put("payTimeEnd", paramsMap.get("payTimeEnd"));
		//供应商信息
		paramsMap.put("supplierName", this.getUserSupplierName());
		
		//查找已付款，财务未审核的订单列表
		List<TradeDetail> tradeDetailList =  tradeDetailService.findTradeDetailListByOrders(paramsMap, 0);
		
		if(CollectionUtils.isNotEmpty(tradeDetailList)) {
			List<TradeOrder> tradeOrderList = new ArrayList<TradeOrder>();
			for (TradeDetail t :tradeDetailList) {
				List<TradeOrder> torderList =  t.getOrders();
				for (TradeOrder tradeOrder : torderList) {
					tradeOrder.setBz(t.getBuyer_message());
				}
				tradeOrderList.addAll(torderList);
			}
			
			List<List<? extends Object>> exportList = new ArrayList<List<? extends Object>>();
			exportList.add(tradeDetailList);
			exportList.add(tradeOrderList);
			
			List<String> title = new ArrayList<String>();
			title.add("订单编号,交易状态,付款方式,支付时间,实付金额(元),收货人,运费");
			title.add("客户,商品编号,商品规格,商品条码,商品名称,数量,实付总金额(元),备注(订单号),收货地址,订单支付时间,留言");
			List<String> field = new ArrayList<String>();
			field.add("tid,status,pay_type,pay_time,payment,receiver_name,post_fee");
			field.add("supplierName,num_iid,sku_properties_name,goodsNumber,title,num,payment,tid,payAddress,payTime,bz");
			
			Map<String,ExcelFormat> changeAttr = new HashMap<String,ExcelFormat>();
			//交易状态
			changeAttr.put("status", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailStatus()));
			//付款方式
			changeAttr.put("pay_type", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailPayType()));
			//调用导出工具类
			ByteArrayOutputStream bos = ExcelUtilXSSF.getInstance().exportExcelOutputStreamByReflect(exportList, title, field, changeAttr);
	        response.reset(); // 非常重要
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=financeOrder.xlsx;");
	        OutputStream out = response.getOutputStream();
	        out.write(bos.toByteArray());
	        out.flush();
	        out.close();
		}else {
			//重定向到列表页面
			response.sendRedirect("/innerbuy/finance/financeOrderReview.do");
		}
	}
}
