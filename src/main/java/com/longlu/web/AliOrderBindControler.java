package com.longlu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.longlu.pojo.AliOrderCode;
import com.longlu.pojo.AliSendGoods;
import com.longlu.pojo.ExpressManager;
import com.longlu.pojo.TradeDetail;
import com.longlu.service.AliOrderCodeService;
import com.longlu.service.AliSendGoodsService;
import com.longlu.service.ExpressManagerService;
import com.longlu.service.TradeDetailService;
import com.longlu.service.YouzanShopConfigService;
import com.longlu.util.OrderSendGoodsExcelResolve;
import com.longlu.util.pagination.Pagination;
import com.longlu.youzan.YouZanServerUtils;
import com.youzan.open.sdk.client.core.YZClient;

@RequestMapping("/ali")
@Controller
public class AliOrderBindControler extends BaseControler{

	@Autowired
	private AliOrderCodeService aliOrderCodeService;
	
	@Autowired
	private AliSendGoodsService aliSendGoodsService;
	
	@Autowired
	private TradeDetailService tradeDetailService;
	
	@Autowired
	private ExpressManagerService expressManagerService;
	
	@Autowired
	private YouzanShopConfigService youzanShopConfigService;
	
	@RequestMapping("/list")
	public String aliOrderCodeList(@RequestParam HashMap<String, Object> paramsMap, Pagination pagination,HashMap<String, Object> resultMap) {
		resultMap.put("outerCode", paramsMap.get("outerCode"));
		List<Map<String,Object>> aliList = aliOrderCodeService.selectBySelective(paramsMap);
		resultMap.put("aliList", aliList);
		resultMap.put("total", aliList.size());
		return "ali/list";
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public Map<String, Object> create(AliOrderCode aliOrderCode,Map<String, Object> resultMap) throws Exception {
		if(!aliOrderCodeService.saveOrUpdate(aliOrderCode)) {
			resultMap.put("success", false);
			resultMap.put("msg", aliOrderCode.getOuterCode()+"已存在,新增失败!");
		}else {
			resultMap.put("success", true);
			resultMap.put("msg", "新增成功!");
		}
		return resultMap;
	}
	
	@RequestMapping("/createFrom")
	public String createFrom(@RequestParam HashMap<String, String> paramsMap,HashMap<String, Object> resultMap) {
		if(StringUtils.isNotBlank(paramsMap.get("id"))) {
			//根据用户id查询用户信息
			AliOrderCode record = aliOrderCodeService.selectByPrimaryKey(Long.valueOf(paramsMap.get("id")));
			resultMap.put("record", record);
		}
		return "ali/create";
	}
	
	@RequestMapping("/delete")
	public String delete(AliOrderCode record) throws Exception {
		if(aliOrderCodeService.deleteByPrimaryKey(record.getId()) == 0) {
			throw new Exception("删除商品URL失败!");
		}
		return "redirect:/ali/list.do";
	}
	
	@RequestMapping("/addGoodsCode")
	@ResponseBody
	public Map<String, Object> addGoodsDetailCode(Map<String, Object> resultMap) throws Exception {
		Pagination pagination = new Pagination();
		pagination.setRows(50);
		pagination.setPage(1);
		aliOrderCodeService.addGoodsDetailCode(pagination);
		resultMap.put("success", true);
		resultMap.put("msg", "新增有赞商品成功!");
		return resultMap;
	}
	
	/*** 下面是阿里巴巴订单发货管理业务************************************************/
	@RequestMapping("/aliSendList")
	public String aliSendGoodsList(@RequestParam HashMap<String, Object> paramsMap, Pagination pagination,HashMap<String, Object> resultMap) {
		List<Map<String,Object>> aliSendList = aliSendGoodsService.selectBySelective(paramsMap);
		resultMap.put("aliSendList", aliSendList);
		resultMap.put("total", aliSendList.size());
		return "ali/aliSendList";
	}
	
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public void uploadAliSendGoods(@RequestParam MultipartFile file,HttpServletRequest request) throws Exception {
		//注意myfileseee要和表单的file标签的name属性名一样
		//注意使用layer上传插件的时候 默认name属性名是file,如果不是file则会报一下错误
		//org.springframework.web.bind.MissingServletRequestParameterException: Required MultipartFile parameter 'file1' is not present
//	   System.out.println("文件长度: " + file.getSize());  
//       System.out.println("文件类型: " + file.getContentType());  
//       System.out.println("文件名称: " + file.getName());  
//       System.out.println("文件原名: " + file.getOriginalFilename()); 
       new OrderSendGoodsExcelResolve().excelResolve(file.getInputStream(),aliSendGoodsService);
       //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
       //String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload"); 
	}
	
	
	@RequestMapping("/deleteAliSend")
	public String deleteAliSend(AliSendGoods record) throws Exception {
		if(aliSendGoodsService.deleteByPrimaryKey(record.getId()) == 0) {
			throw new Exception("删除阿里巴巴订单信息失败!");
		}
		return "redirect:/ali/aliSendList.do";
	}
	
	@RequestMapping("/aliSendFrom")
	public String aliSend(@RequestParam HashMap<String, String> paramsMap,HashMap<String, Object> resultMap) {
		if(StringUtils.isNotBlank(paramsMap.get("id"))) {
			//根据 id 查询AliSendGoods信息
			AliSendGoods record = aliSendGoodsService.selectByPrimaryKey(Long.valueOf(paramsMap.get("id")));
			//通过收货人手机号查询有赞该手机用户需要发货的订单列表
			
			//增加查询条件
			resultMap.put("status", "WAIT_SELLER_SEND_GOODS");
			//供应商信息
			resultMap.put("supplierName", this.getUserSupplierName());
			//手机
			resultMap.put("receiver_mobile", record.getReceiverMobile());
			List<TradeDetail> tradeDetailList =  tradeDetailService.findTradeDetailListByOrders(resultMap, 1);
			
			//通过阿里物流公司，查询有赞对应的物流公司和物流编号
			ExpressManager  expressManager = expressManagerService.selectByAliCompany(record.getExpressCompany());
			
			resultMap.put("expressManager", expressManager);
			resultMap.put("tradeDetailList", tradeDetailList);
			resultMap.put("record", record);
		}
		return "ali/aliSend";
	}
	
	
	/**
	 * 根据阿里巴巴订单批量发货
	 * @param paramsMap
	 * @param resultMap
	 * @return
	 */
	@RequestMapping("/aliSendExpress")
	@ResponseBody
	public Map<String, Object> aliSendExpress(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
		
		String orderIds = (String)paramsMap.get("orderIds");
		String expressCompany = (String)paramsMap.get("expressCompany");
		String expressNumber = (String)paramsMap.get("expressNumber");
		String aliSendGoodsId = (String)paramsMap.get("aliSendGoodsId");
		
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(orderIds)) {
			String[] tradeIds = orderIds.split(",");
			
			//每个子单开始校验
			for (int i = 0; i < tradeIds.length; i++) {
				//财务审核通过
				String[] tids = tradeIds[i].split("-");
				String id = tids[0];
				String tid = tids[1];
				String oid = tids[2];
				String shopId = tids[3];
				if(!tradeDetailService.checkOrderFeedback(tid, oid,shopId)) {
					sb.append("订单"+tid+",客户发起了维权,请处理后发货!");
					resultMap.put("success", false);
					resultMap.put("msg", sb.toString());
					return resultMap;
				}
				
				//校验完成，进行发货业务处理
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
				
				//更新表中发货状态
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ids", id.split(","));
				params.put("orderReview", 2);
				params.put("expressCompany", expressCompany);
				params.put("expressNumber", expressNumber);
				tradeDetailService.batchUpdateByTradeOrderSelectMap(params);
			}
			
			//删除阿里巴巴订单发货表中的已发货记录
			aliSendGoodsService.deleteByPrimaryKey(Long.valueOf(aliSendGoodsId));
			resultMap.put("success", true);
			resultMap.put("msg", "阿里巴巴批量发货成功!");
		}else {
			resultMap.put("success", false);
			resultMap.put("msg", "请勾选待审核订单!");
		}
		
		return resultMap;
	}
	
//	@RequestMapping("/aliSendExpress")
//	@ResponseBody
//	public Map<String, Object> aliSendExpress_bak(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
//		
//		String orderIds = (String)paramsMap.get("orderIds");
//		String expressCompany = (String)paramsMap.get("expressCompany");
//		String expressNumber = (String)paramsMap.get("expressNumber");
//		String aliSendGoodsId = (String)paramsMap.get("aliSendGoodsId");
//		
//		StringBuffer sb = new StringBuffer();
//		StringBuffer idSb = new StringBuffer();
//		StringBuffer oidSb = new StringBuffer();
//		if(StringUtils.isNotBlank(orderIds)) {
//			String[] tradeIds = orderIds.split(",");
//			//获得第一个订单的订单号，如果后面的订单号和第一个订单的订单号不同则不能发货
//			String firstTid = tradeIds[0].split("-")[1];
//			
//			//每个子单开始校验
//			for (int i = 0; i < tradeIds.length; i++) {
//				//财务审核通过
//				String[] tids = tradeIds[i].split("-");
//				String id = tids[0];
//				String tid = tids[1];
//				String oid = tids[2];
//				
//				if(!tid.equals(firstTid)) {
//					sb.append("发货只能针对一个订单的多个子单,不能多个订单共一个快递单号!");
//					resultMap.put("success", false);
//					resultMap.put("msg", sb.toString());
//					return resultMap;
//				}else if(!tradeDetailService.checkOrderFeedback(tid, oid)) {
//					sb.append("订单"+tid+",客户发起了维权,请处理后发货!");
//					resultMap.put("success", false);
//					resultMap.put("msg", sb.toString());
//					return resultMap;
//				}
//				idSb.append(id).append(",");
//				oidSb.append(oid).append(",");
//			}
//			
//			//校验完成，进行发货业务处理
//			HashMap<String, String> paramsConfirm = new HashMap<String, String>();
//			paramsConfirm.put("tid", firstTid);
//			paramsConfirm.put("oids", oidSb.substring(0,oidSb.length()-1));
//			//快递ID
//			paramsConfirm.put("out_stype", expressCompany);
//			//快递单号
//			paramsConfirm.put("out_sid", expressNumber);
//			boolean flag = KdtApiUtils.onlineConfirm(paramsConfirm);
//			if(!flag) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "订单"+firstTid+":发货失败!");
//				return resultMap;
//			}
//			//更新表中发货状态
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("ids", idSb.toString().split(","));
//			params.put("orderReview", 2);
//			params.put("expressCompany", paramsMap.get("expressCompany"));
//			params.put("expressNumber", paramsMap.get("expressNumber"));
//			tradeDetailService.batchUpdateByTradeOrderSelectMap(params);
//			
//			//删除阿里巴巴订单发货表中的已发货记录
//			aliSendGoodsService.deleteByPrimaryKey(Long.valueOf(aliSendGoodsId));
//			resultMap.put("success", true);
//			resultMap.put("msg", "阿里巴巴批量发货成功!");
//		}else {
//			resultMap.put("success", false);
//			resultMap.put("msg", "请勾选待审核订单!");
//		}
//		
//		return resultMap;
//	}
	
	
	/**
	 * 一键去除阿里巴巴导入的无用订单，即导入的订单中有赞没有对应的发货订单
	 * @param paramsMap
	 * @param resultMap
	 * @return
	 */
	@RequestMapping("/deleteNoUseAliOrder")
	@ResponseBody
	public Map<String, Object> deleteNoUseAliOrder(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
		try {
			//查询所有的阿里巴巴导入订单
			List<Map<String,Object>> aliSendList = aliSendGoodsService.selectBySelective(paramsMap);
			
			if(CollectionUtils.isNotEmpty(aliSendList)) {
			
				//增加查询条件
				resultMap.put("status", "WAIT_SELLER_SEND_GOODS");
				//供应商信息
				resultMap.put("supplierName", this.getUserSupplierName());
				
				//遍历每一个阿里巴巴订单，查询是否有对应的有赞发货订单
				for (Map<String, Object> map : aliSendList) {
					//获取阿里巴巴订单手机信息查询有赞对应的发货订单
					String receiverMobile = map.get("receiverMobile").toString();
					
					//手机
					resultMap.put("receiver_mobile", receiverMobile);
					List<TradeDetail> tradeDetailList =  tradeDetailService.findTradeDetailListByOrders(resultMap, 1);
					
					if(CollectionUtils.isEmpty(tradeDetailList)) {
						//删除阿里巴巴订单发货表中的已发货记录
						aliSendGoodsService.deleteByPrimaryKey(Long.valueOf(map.get("id").toString()));
					}
				}
			}
			resultMap.put("success", true);
			return resultMap;
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("msg", "一键去除无用订单失败!");
			return resultMap;
		}
	}

}
