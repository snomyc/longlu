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
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longlu.pojo.AliOrderCode;
import com.longlu.pojo.TradeDetail;
import com.longlu.pojo.TradeOrder;
import com.longlu.pojo.YouzanShopConfig;
import com.longlu.service.AliOrderCodeService;
import com.longlu.service.TradeDetailService;
import com.longlu.service.UsersService;
import com.longlu.service.YouzanShopConfigService;
import com.longlu.util.ExcelUtilXSSF;
import com.longlu.util.SendMail;
import com.longlu.util.excel.ExcelFormat;
import com.longlu.util.excel.ExcelMapFactory;
import com.longlu.util.excel.ExcelMapperFormat;
import com.longlu.util.pagination.Pagination;
import com.longlu.util.vo.SendEmailVo;

/**     
* 类描述：   订单管理模块
* 创建人：yagncan   
* 创建时间：2016年12月17日 上午11:54:11     
*/
@RequestMapping("/trade")
@Controller
public class TradeDetailControler extends BaseControler{
	
	@Autowired
	private TradeDetailService tradeDetailService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AliOrderCodeService aliOrderCodeService;
	
	@Autowired
	private YouzanShopConfigService youzanShopConfigService;
	
	/**
	* 方法名称: getPaymentOrders
	* 方法描述：订单流程跟踪
	* 参数 :@return
	* 返回类型: String
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月17日 下午12:02:22     
	*/
	@RequestMapping("/paymentOrders")
	public String getPaymentOrdersList(@RequestParam HashMap<String, String> paramsMap, Pagination pagination,HashMap<String, Object> resultMap) {
		resultMap.put("status", "WAIT_SELLER_SEND_GOODS");
		resultMap.put("pagination", pagination);
		//增加查询条件
		//订单编号查询
		if(StringUtils.isNotBlank(paramsMap.get("tid"))) {
			resultMap.put("tid", paramsMap.get("tid").trim());
		}
		//店铺ID
		if(StringUtils.isNotBlank(paramsMap.get("shopId"))) {
			resultMap.put("shopId", paramsMap.get("shopId").trim());
		}
		//支付时间
		resultMap.put("payTimeStart", paramsMap.get("payTimeStart"));
		//支付时间
		resultMap.put("payTimeEnd", paramsMap.get("payTimeEnd"));
		List<TradeDetail> tradeDetailList = tradeDetailService.findTradeDetailList(resultMap);
		resultMap.put("tradeDetailList", tradeDetailList);
		//获得店铺配置列表
		List<YouzanShopConfig> shopConfig = youzanShopConfigService.findAllUseShop();
		resultMap.put("shopConfig", shopConfig);
		return "order/paymentOrders";
	}
	
	
	/**
	* 方法名称: getPaymentSuccessOrdersList
	* 方法描述：交易完成订单列表
	* 参数 :@param paramsMap
	* 参数 :@param resultMap
	* 参数 :@return
	* 返回类型: String
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月19日 下午9:39:01     
	*/
	@RequestMapping("/paymentSuccessOrders")
	public String getPaymentSuccessOrdersList(@RequestParam HashMap<String, String> paramsMap, Pagination pagination,HashMap<String, Object> resultMap) {
		
		resultMap.put("status", "TRADE_BUYER_SIGNED");
		resultMap.put("pagination", pagination);
		//增加查询条件
		//订单编号查询
		if(StringUtils.isNotBlank(paramsMap.get("tid"))) {
			resultMap.put("tid", paramsMap.get("tid").trim());
		}
		//店铺ID
		if(StringUtils.isNotBlank(paramsMap.get("shopId"))) {
			resultMap.put("shopId", paramsMap.get("shopId").trim());
		}
		//支付时间
		resultMap.put("payTimeStart", paramsMap.get("payTimeStart"));
		//支付时间
		resultMap.put("payTimeEnd", paramsMap.get("payTimeEnd"));
		List<TradeDetail> tradeDetailList = tradeDetailService.findTradeDetailList(resultMap);
		resultMap.put("tradeDetailList", tradeDetailList);
		//获得店铺配置列表
		List<YouzanShopConfig> shopConfig = youzanShopConfigService.findAllUseShop();
		resultMap.put("shopConfig", shopConfig);
		return "order/paymentSuccessOrders";
	}
	
	
	/**
	* 方法名称: expessOrderList
	* 方法描述： 订单快递录入列表
	* 参数 :@return
	* 返回类型: String
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月29日 下午11:03:32     
	*/
	@RequestMapping("/expessOrders")
	public String expessOrdersList(@RequestParam HashMap<String, String> paramsMap,HashMap<String, Object> resultMap) {
		
		//增加查询条件
		resultMap.put("status", "WAIT_SELLER_SEND_GOODS");
		//订单编号查询
		resultMap.put("tid", paramsMap.get("tid"));
		//支付时间
		resultMap.put("payTimeStart", paramsMap.get("payTimeStart"));
		//支付时间
		resultMap.put("payTimeEnd", paramsMap.get("payTimeEnd"));
		//订单快递录入列表
		List<TradeDetail> tradeDetailList =  tradeDetailService.findTradeDetailListByOrders(resultMap, 2);
		//初始化分页信息
		//pagination.init(tradeDetailList.size(), pagination.getRows());
		resultMap.put("tradeDetailList", tradeDetailList);
		resultMap.put("total", tradeDetailList.size());
		return "order/expessOrders";
	}
	
	/**
	* 方法名称: checkPayOrderExpessInput
	* 方法描述： 订单快递录入审核
	* 参数 :@return
	* 返回类型: String
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月29日 下午10:59:56     
	*/
//	@RequestMapping("/checkPayOrderExpessInput")
//	@ResponseBody
//	public Map<String, Object> checkPayOrderExpessInput(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
//		if(paramsMap.get("tid") != null) {
//			//录入完成
//			resultMap = tradeDetailService.updateTradeOrderReview(paramsMap,3);
//		}else {
//			//取消录入
//			resultMap = tradeDetailService.updateTradeOrderReview(paramsMap,-3);
//		}
//		return resultMap;
//	}
	
	
	/**
	* 方法名称: exportPayTradeDetail
	* 方法描述：导出已支付订单信息
	* 参数 :
	* 返回类型: void
	 * @throws Exception 
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月17日 下午11:33:03     
	*/
	@RequestMapping("/exportPayTradeDetail")
	public void exportPayTradeDetail(@RequestParam HashMap<String, Object> paramsMap ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//增加查询条件
		paramsMap.put("status", "WAIT_SELLER_SEND_GOODS");
		//订单编号查询
		paramsMap.put("tid", paramsMap.get("tid"));
		//支付时间
		paramsMap.put("payTimeStart", paramsMap.get("payTimeStart"));
		//支付时间
		paramsMap.put("payTimeEnd", paramsMap.get("payTimeEnd"));
		List<TradeDetail> tradeDetailList = tradeDetailService.findTradeDetailList(paramsMap);
		
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
			title.add("店铺,订单编号,交易状态,付款方式,支付时间,实付金额(元),收货人");
			title.add("店铺,客户,商品编号,商品规格,商品条码,商品名称,数量,实付总金额(元),备注(订单号),收货地址,订单支付时间,留言");
			List<String> field = new ArrayList<String>();
			field.add("shopName,tid,status,pay_type,pay_time,payment,receiver_name");
			field.add("shopName,supplierName,num_iid,sku_properties_name,goodsNumber,title,num,payment,tid,payAddress,payTime,bz");
			
			Map<String,ExcelFormat> changeAttr = new HashMap<String,ExcelFormat>();
			//交易状态
			changeAttr.put("status", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailStatus()));
			//付款方式
			changeAttr.put("pay_type", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailPayType()));
			//调用导出工具类
			ByteArrayOutputStream bos = ExcelUtilXSSF.getInstance().exportExcelOutputStreamByReflect(exportList, title, field, changeAttr);
	        response.reset(); // 非常重要
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=tradedetail.xlsx;");
	        OutputStream out = response.getOutputStream();
	        out.write(bos.toByteArray());
	        out.flush();
	        out.close();
		}else {
			//重定向到列表页面
			response.sendRedirect("/innerbuy/trade/paymentOrders.do");
		}
	}
	
	
	/**
	* 方法名称: exportTradeSuccess
	* 方法描述：导出交易完成订单信息
	* 参数 :
	* 返回类型: void
	 * @throws Exception 
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月17日 下午11:33:03     
	*/
	@RequestMapping("/exportTradeSuccess")
	public void exportTradeSuccess(@RequestParam HashMap<String, Object> paramsMap ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//增加查询条件
		paramsMap.put("status", "TRADE_BUYER_SIGNED");
		//订单编号查询
		paramsMap.put("tid", paramsMap.get("tid"));
		//支付时间
		paramsMap.put("payTimeStart", paramsMap.get("payTimeStart"));
		//支付时间
		paramsMap.put("payTimeEnd", paramsMap.get("payTimeEnd"));
		List<TradeDetail> tradeDetailList = tradeDetailService.findTradeDetailList(paramsMap);
		
		if(CollectionUtils.isNotEmpty(tradeDetailList)) {
			List<TradeOrder> tradeOrderList = new ArrayList<TradeOrder>();
			for (TradeDetail t :tradeDetailList) {
				List<TradeOrder> torderList =  t.getOrders();
				for (TradeOrder tradeOrder : torderList) {
					tradeOrder.setBz(t.getBuyer_message());
					if(StringUtils.isEmpty(tradeOrder.getCost())) {
						//通过商品编码查询商品的成本信息
						AliOrderCode aliOrderCode = aliOrderCodeService.selectByOuterCode(tradeOrder.getSupplierName()+"-"+tradeOrder.getGoodsNumber());
						if(aliOrderCode != null) {
							tradeOrder.setAlicost(aliOrderCode.getCost());
						}
					}
				}
				tradeOrderList.addAll(torderList);
			}
			
			List<List<? extends Object>> exportList = new ArrayList<List<? extends Object>>();
			exportList.add(tradeDetailList);
			exportList.add(tradeOrderList);
			
			List<String> title = new ArrayList<String>();
			title.add("店铺,订单编号,交易状态,付款方式,支付时间,实付金额(元),收货人,运费");
			title.add("店铺,客户,商品编号,商品规格,商品条码,商品名称,数量,实付总金额(元),订单成本,采购成本,备注(订单号),收货地址,订单支付时间,留言");
			List<String> field = new ArrayList<String>();
			field.add("shopName,tid,status,pay_type,pay_time,payment,receiver_name,post_fee");
			field.add("shopName,supplierName,num_iid,sku_properties_name,goodsNumber,title,num,payment,cost,alicost,tid,payAddress,payTime,bz");
			
			Map<String,ExcelFormat> changeAttr = new HashMap<String,ExcelFormat>();
			//交易状态
			changeAttr.put("status", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailStatus()));
			//付款方式
			changeAttr.put("pay_type", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailPayType()));
			//调用导出工具类
			ByteArrayOutputStream bos = ExcelUtilXSSF.getInstance().exportExcelOutputStreamByReflect(exportList, title, field, changeAttr);
	        response.reset(); // 非常重要
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ordersuccess.xlsx;");
	        OutputStream out = response.getOutputStream();
	        out.write(bos.toByteArray());
	        out.flush();
	        out.close();
		}else {
			//重定向到列表页面
			response.sendRedirect("/innerbuy/trade/paymentSuccessOrders.do");
		}
	}

	@RequestMapping("/sengEmailToApprover")
	@ResponseBody
	public Map<String, Object> sengEmailToApprover(@RequestParam Map<String, String> paramsMap,Map<String, Object> resultMap) throws Exception {
		String supplierName = paramsMap.get("supplierName");
		String orderReview = paramsMap.get("orderReview");
		if(StringUtils.isNotBlank(supplierName) && StringUtils.isNotBlank(orderReview)) {
			//通过供应商找到对应的用户
			resultMap.put("f0", supplierName);
			List<Map<String, Object>> userList = usersService.selectUsersBySelective(resultMap);
			resultMap.clear();
			if(CollectionUtils.isNotEmpty(userList)) {
				String content = "";
				if(orderReview.equals("0")) {
					content = "您在龙路仓内购平台有待审批订单需要审批,请点击 http://118.178.193.88:8888/innerbuy/index.html 进行操作!";
				}else if(orderReview.equals("1")) {
					content = "您在龙路仓内购平台有待审批发货订单需要审批,请点击 http://118.178.193.88:8888/innerbuy/index.html 进行操作!";
				}
				
				SendEmailVo sendEmailVo = new SendEmailVo();
				sendEmailVo.setSubject("您有待办订单提醒,请登录龙路仓内购平台进行操作!");
				sendEmailVo.setContent(content);;
				StringBuffer sb = new StringBuffer();
				for (Map<String, Object> user : userList) {
					String email  = user.get("email").toString();
					sb.append(email).append(",");
					sendEmailVo.setToEmail(email);
					SendMail.createSimpleMail(sendEmailVo);
				}
				//通过订单审批状态发送邮件内容
				resultMap.put("success", true);
				resultMap.put("msg", "已向"+sb.toString()+"成功发送邮件!");
				return resultMap;
			}
			resultMap.put("success", false);
			resultMap.put("msg", "没有找到该供应商对应的用户!");
			return resultMap;
		
		}
		resultMap.put("success", false);
		resultMap.put("msg", "参数错误!");
		return resultMap;
	}
	
}
