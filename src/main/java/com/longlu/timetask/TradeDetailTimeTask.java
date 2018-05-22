package com.longlu.timetask;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.longlu.dao.TradeDetailMapper;
import com.longlu.dao.TradeOrderMapper;
import com.longlu.enums.TradeTimeEnum;
import com.longlu.pojo.AliOrderCode;
import com.longlu.pojo.TradeDetail;
import com.longlu.pojo.TradeOrder;
import com.longlu.pojo.YouzanShopConfig;
import com.longlu.service.AliOrderCodeService;
import com.longlu.service.TradeDetailService;
import com.longlu.service.YouzanShopConfigService;
import com.longlu.util.DateFormatHelper;
import com.longlu.util.DateTool;
import com.longlu.util.ExcelUtilXSSF;
import com.longlu.util.HttpClientHelper;
import com.longlu.util.SendMail;
import com.longlu.util.excel.ExcelFormat;
import com.longlu.util.excel.ExcelMapFactory;
import com.longlu.util.excel.ExcelMapperFormat;
import com.longlu.util.vo.SendEmailVo;
import com.longlu.youzan.YouZanServerUtils;
import com.youzan.open.sdk.client.auth.Sign;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetResult;

/**     
* 类描述：   定期从有赞获得已付款订单信息定时任务类
* 创建人：yagncan   
* 创建时间：2016年12月18日 下午3:02:58     
*/
public class TradeDetailTimeTask {
	
	private static Logger logger = LoggerFactory.getLogger(TradeDetailTimeTask.class);
	
	@Autowired
	private TradeDetailMapper tradeDetailMapper;
	
	@Autowired
	private TradeOrderMapper tradeOrderMapper;
	
	@Autowired
	private AliOrderCodeService aliOrderCodeService;
	
	@Autowired
	private TradeDetailService tradeDetailService;
	
	@Autowired
	private YouzanShopConfigService youzanShopConfigService;
	
	/**
	* 方法名称: getPaymentOrdersToYouZan
	* 方法描述： 从有赞获取已付款的订单信息并写入数据到福利平台DB
	* 参数 :
	* 返回类型: void
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月18日 下午3:08:23     
	*/
	public void getPaymentOrdersToYouZan() {
		
		logger.error("start:从有赞API获取已支付订单列表...");
		//遍历所有店铺,获取所有店铺订单信息
		List<YouzanShopConfig> configList = youzanShopConfigService.findAllUseShop();
		if(CollectionUtils.isNotEmpty(configList)) {
			for (YouzanShopConfig youzanShopConfig : configList) {
				
				try{
					
					YZClient client = null;
					if(StringUtils.isNotBlank(youzanShopConfig.getKdtId())) {
						client = new DefaultYZClient(new Token(youzanShopConfig.getToken()));
					}else {
						client = new DefaultYZClient(new Sign(youzanShopConfig.getClientId(), youzanShopConfig.getClientSecret()));
					}
					
					//获取已支付待发货订单
					List<TradeDetail> tradeDetail = YouZanServerUtils.youzanTradesSoldGetWaitSendGoods(client,String.valueOf(youzanShopConfig.getId()),youzanShopConfig.getShopName());
					if(CollectionUtils.isNotEmpty(tradeDetail)) {
						for (TradeDetail t : tradeDetail) {
							//判断数据库中是否已有相同的付款订单信息，如果有则不新增
		        			TradeDetail tdb= tradeDetailMapper.selectTradeDetailByTid(t.getTid());
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
		        			}
						}
					}
				}catch (Exception e) {
					//token失效，更换token
					if(StringUtils.isNotBlank(youzanShopConfig.getRefreshToken())) {
						refreshShopToken(youzanShopConfig);
					}else if(StringUtils.isNotBlank(youzanShopConfig.getKdtId())) {
						replaceShopToken(youzanShopConfig);
					}
					
					//获取有赞接口订单数据异常，发送邮件报警
					sendSystemErrorEmail(youzanShopConfig.getShopName()+"|获取有赞API已支付订单接口失败,请立即查看!Exception:"+e.getMessage());
				}
			}
		}
		logger.error("end:从有赞API获取已支付订单列表...");
	}
	
	/**
	* 方法名称: getTradeSuccessOrdersToYouZan
	* 方法描述：从有赞获取已交易成功的订单信息,便于财务对账
	* 参数 :
	* 返回类型: void
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月18日 下午3:15:04     
	*/
	public void getTradeSuccessOrdersToYouZan() {
		logger.error("start:从有赞API获取已交易成功订单列表...");
		//遍历所有店铺,获取所有店铺订单信息
		List<YouzanShopConfig> configList = youzanShopConfigService.findAllUseShop();
		if(CollectionUtils.isNotEmpty(configList)) {
			for (YouzanShopConfig youzanShopConfig : configList) {
				try{
					
					YZClient client = null;
					if(StringUtils.isNotBlank(youzanShopConfig.getKdtId())) {
						client = new DefaultYZClient(new Token(youzanShopConfig.getToken()));
					}else {
						client = new DefaultYZClient(new Sign(youzanShopConfig.getClientId(), youzanShopConfig.getClientSecret()));
					}
					//获取交易成功订单 获取最近5天的订单信息
					List<TradeDetail> tradeDetail = YouZanServerUtils.youzanTradesSoldGetTradeBuyerSigned(client,String.valueOf(youzanShopConfig.getId()),youzanShopConfig.getShopName());
					tradeDetailService.saveOrUpdateTradeBuyerSigned(tradeDetail);
				}catch (Exception e) {
					//token失效，更换token
					if(StringUtils.isNotBlank(youzanShopConfig.getRefreshToken())) {
						refreshShopToken(youzanShopConfig);
					}else if(StringUtils.isNotBlank(youzanShopConfig.getKdtId())) {
						replaceShopToken(youzanShopConfig);
					}
					//获取有赞接口订单数据异常，发送邮件报警
					sendSystemErrorEmail(youzanShopConfig.getShopName()+"|获取有赞API已交易成功订单接口失败,请立即查看!Exception:"+e.getMessage());
				}
			}
		}
		logger.error("end:从有赞API获取已交易成功订单列表...");
	}
	
	
	/**
	* 方法名称: getOtherYouZanTradeDetailToExcel
	* 方法描述：易多多第三方有赞获取未支付订单信息和已支付订单信息并发送邮件
	* 参数 : 订单信息格式 未付款和已付款订单时间，收货人，收货地址，订单号，商品名称，数量，金额（应付金额）
	* 返回类型: void
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月27日 下午9:05:38     
	*/
	public void getOtherYouZanTradeDetailToExcel() {
		
		//易多多
		String app_id = "9f543932371a653ba6";
		String app_app_secret ="341a0b2b2ccec6a0ac39d335f5c88e47";
		
		//容易内购
//		String app_id = "4397f02f143e73474e";
//		String app_app_secret ="b6d8b4a7f91ddc9041086747d5e895c1";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		List<TradeDetail> tradeDetailList = null;
		StringBuffer sb = new StringBuffer();
		//分段获取交易创建时间订单
		DateTool date = new DateTool();
		int hour = date.getHour();
		//根据时间段设置交易创建时间订单
		if(6< hour && hour <= 10) {
			//System.out.println(date.getDate()+TradeTimeEnum.ONE.getTimeStr());
			params.put("start_created", DateFormatHelper.formatDate(DateTool.getChineseFormatTime()+TradeTimeEnum.ONE.getTimeStr(), DateFormatHelper.YYYY_MM_DD_HH_MM_SS));
		}else if(10 < hour && hour <= 14) {
			//System.out.println(date.getDate()+TradeTimeEnum.TWO.getTimeStr());
			params.put("start_created", DateFormatHelper.formatDate(DateTool.getChineseFormatTime()+TradeTimeEnum.TWO.getTimeStr(), DateFormatHelper.YYYY_MM_DD_HH_MM_SS));
		}else if(14 < hour && hour <= 18) {
			//System.out.println(date.getDate()+TradeTimeEnum.THREE.getTimeStr());
			params.put("start_created", DateFormatHelper.formatDate(DateTool.getChineseFormatTime()+TradeTimeEnum.THREE.getTimeStr(), DateFormatHelper.YYYY_MM_DD_HH_MM_SS));
		}else if(18 < hour && hour <= 24) {
			//System.out.println(date.getDate()+TradeTimeEnum.FOUR.getTimeStr());
			params.put("start_created", DateFormatHelper.formatDate(DateTool.getChineseFormatTime()+TradeTimeEnum.FOUR.getTimeStr(), DateFormatHelper.YYYY_MM_DD_HH_MM_SS));
		}
		//必须设置结束时间 成对出现不然报错
		params.put("end_created",new Date());
    	logger.error("start:易多多,从有赞API获取已支付订单列表...");
    	
    	YZClient client = new DefaultYZClient(new Sign(app_id, app_app_secret));
    	
    	//已支付订单
        params.put("status", "WAIT_SELLER_SEND_GOODS");
    	tradeDetailList = YouZanServerUtils.youzanTradesSoldGet(client,params,"999","易多多");
    	
    	logger.error("end:易多多,从有赞API获取已支付订单列表...");
    	logger.error("start:易多多,从有赞API获取交易关闭订单列表...");
    	//已支付订单
        params.put("status", "ALL_CLOSED");
        List<TradeDetail> tlist = YouZanServerUtils.youzanTradesSoldGet(client,params,"999","易多多");
    	tradeDetailList.addAll(tlist);
    	logger.error("end:易多多,从有赞API获取交易关闭订单列表...");
    	
    	//只有无维权和维权结束的付款订单才导出excel
		if(CollectionUtils.isNotEmpty(tradeDetailList)) {
			logger.error("start:开始解析订单数据并生成excel...");
			List<TradeDetail> tDetailList = new ArrayList<TradeDetail>();
			List<TradeOrder> tOrderList = new ArrayList<TradeOrder>();
			for (TradeDetail t :tradeDetailList) {
				if(t.getFeedback() == 0 || t.getFeedback() == 110) {
					
    				//如果有子订单
	        		if( CollectionUtils.isNotEmpty(t.getOrders()) ) {
	        			List<TradeOrder> orderList = t.getOrders();
	        			for (TradeOrder tradeOrder : orderList) {
	        				//将订单编号放入订单详细表
	        				tradeOrder.setTid(t.getTid());
	        				//将订单支付时间放入订单详情表
	        				tradeOrder.setPayTime(t.getCreated());
	        				
	        				//获得订单送货地址
	        				sb.append(t.getReceiver_state()).append("-");
	        				sb.append(t.getReceiver_city()).append("-");
	        				sb.append(t.getReceiver_district()).append("-");
	        				sb.append(t.getReceiver_address()).append("-");
	        				sb.append(t.getReceiver_name()).append("-");
	        				sb.append(t.getReceiver_mobile()).append("-");
	        				tradeOrder.setPayAddress(sb.toString());
	        				sb.delete(0, sb.length());
						}
	        			if(StringUtils.isEmpty(t.getPay_type())) {
	        				t.setPay_type("NO_PAY");
	        			}
	        			tOrderList.addAll(orderList);
						tDetailList.add(t);
	        		}
					
				}
			}
			
			//组装excel所需字段
			List<List<? extends Object>> exportList = new ArrayList<List<? extends Object>>();
			exportList.add(tDetailList);
			exportList.add(tOrderList);
			
			List<String> title = new ArrayList<String>();
			title.add("订单编号,交易状态,付款方式,下单时间,实付金额(元),收货人,留言");
			title.add("订单编号,商品名称,数量,实付金额,交易状态,收货地址,下单时间");
			List<String> field = new ArrayList<String>();
			field.add("tid,status,pay_type,created,payment,receiver_name,buyer_message");
			field.add("tid,title,num,payment,state_str,payAddress,payTime");
			
			Map<String,ExcelFormat> changeAttr = new HashMap<String,ExcelFormat>();
			//交易状态
			changeAttr.put("status", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailStatus()));
			//付款方式
			changeAttr.put("pay_type", new ExcelMapperFormat(ExcelMapFactory.getTradeDetailPayType()));
			//调用导出工具类
			ByteArrayOutputStream bos = ExcelUtilXSSF.getInstance().exportExcelOutputStreamByReflect(exportList, title, field, changeAttr);
			
			try {
				//如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
//              String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
				FileOutputStream out = new FileOutputStream("C:/tradeDetail.xlsx");
				bos.writeTo(out);
				bos.close();
				//关闭文件流
				out.close();
				logger.error("end:解析数据完成并生成excel完成...");
				//生成excel完成，发送邮件
				SendEmailVo sendEmailVo = new SendEmailVo();
				sendEmailVo.setToEmail("149342700@qq.com,156025864@qq.com");
//				sendEmailVo.setToEmail("458508804@qq.com");
				sendEmailVo.setSubject("有赞已支付订单,交易关闭订单excel文件");
				sendEmailVo.setContent("有赞已支付订单,交易关闭订单excel文件,请查收...");;
				sendEmailVo.setAttachStrings("C:\\tradeDetail.xlsx");
				//发送多人
				logger.error("start:开始发送邮件!");
				SendMail.createAttachMail(sendEmailVo);
				logger.error("end：发送邮件成功!");
			} catch (IOException e) {
				logger.error("生成excel文件失败!");
			} catch (Exception e) {
				logger.error("发送邮件失败!");
			}
		}
	}
	
	/**
	 * 发送系统异常报警邮件
	 * @param content
	 */
	public void sendSystemErrorEmail(String content) {
		try {
			SendEmailVo sendEmailVo = new SendEmailVo();
			sendEmailVo.setToEmail("458508804@qq.com");
			sendEmailVo.setSubject("容易内购|异常邮件提醒...");
			sendEmailVo.setContent(content);
			//发送多人
			SendMail.createAttachMail(sendEmailVo);
		} catch (Exception e) {
			logger.error("容易内购|发送系统异常报警邮件失败!");
			e.printStackTrace();
		}
	}
	
	/**
	 * 刷新店铺token信息
	 * @param youzanShopConfig
	 */
	public void refreshShopToken(YouzanShopConfig youzanShopConfig) {
		//通过刷新令牌调用有赞刷新token接口
		try {
			String jsonResult = YouZanServerUtils.youzanRefreshToken(youzanShopConfig.getRefreshToken());
			
			JSONObject json = JSONObject.fromObject(jsonResult);
			//过期时间7天
			String token = json.get("access_token").toString();
			//用于刷新令牌 access_token 的 refresh_token 过期时间28天
			String refresh_token = json.get("refresh_token").toString();
			
			logger.error("店铺："+youzanShopConfig.getShopName()+"new token="+token);
			logger.error("店铺："+youzanShopConfig.getShopName()+"new refresh_token="+refresh_token);
			
			//更新数据库种的店铺token信息
	    	youzanShopConfig.setToken(token);
	    	//更新数据库店铺的刷新token信息
	    	youzanShopConfig.setRefreshToken(refresh_token);
	    	youzanShopConfigService.updateByPrimaryKey(youzanShopConfig);
		} catch (Exception e) {
			logger.error("店铺："+youzanShopConfig.getShopName()+"刷新token信息失败!");
		}
	}
	
	/**
	 * 更换店铺token信息
	 */
	public void replaceShopToken(YouzanShopConfig youzanShopConfig) {
		
		String client_id = youzanShopConfig.getClientId();
		String client_secret = youzanShopConfig.getClientSecret();
		String kdt_id = youzanShopConfig.getKdtId();
		
		StringBuffer sb = new StringBuffer();
		sb.append("https://open.youzan.com/oauth/token?");
		sb.append("client_id=").append(client_id).append("&");
		sb.append("client_secret=").append(client_secret).append("&");
		sb.append("grant_type=silent&");
		sb.append("kdt_id=").append(kdt_id);
		
		String result = HttpClientHelper.httpGet(sb.toString());
		//转换成object类型
    	JSONObject jo = JSONObject.fromObject(result);
    	//获得access_token节点
    	String token = jo.get("access_token").toString();
    	
    	//更新数据库种的店铺TOKEN信息
    	youzanShopConfig.setToken(token);
    	youzanShopConfigService.updateByPrimaryKey(youzanShopConfig);
	}
	
}
/**
 * @author yangcan
 * 定时任务服务类
 * 第一步:编写任务类(TimeTaskService)
 * 第二步:配置作业类(spring.xml配置)
 * <bean id="TimeTaskService"  
	class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">  
	<property name="targetObject">  
		<bean class="com.crm.timetask.TimeTaskService" />  
	</property>  
	<property name="targetMethod" value="sendTodayOnlineSum" />  执行定时任务方法
	<property name="concurrent" value="false" /><!-- 作业不并发调度 -->  
	</bean>
	
	第三步：配置作业调度的触发方式（触发器）
	只支持按照一定频度调用任务:
	<bean id="simpleTrigger" class="org.springframework.scheduling.quartz.SimpleTriggerBean">  
		<property name="jobDetail" ref="TimeTaskService" />  
		<property name="startDelay" value="0" /><!-- 调度工厂实例化后，经过0秒开始执行调度 -->  
		<property name="repeatInterval" value="2000" /><!-- 每2秒调度一次 -->  
	</bean>
	支持到指定时间运行一次：
	<bean id="cronTrigger" class="org.springframework.scheduling.quartz.CronTriggerBean">  
		<property name="jobDetail" ref="TimeTaskService" />  
		<!—每天12:00运行一次 -->  
		<property name="cronExpression" value="0 0 12 * * ?" />  
	</bean>  
	第四步：配置调度工厂
	<bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">  
		<property name="triggers">  
		<list>  
			<ref bean="simpleTrigger" />
			<ref bean="cronTrigger" />
		</list>  
	</property>  
	</bean> 
	
	//规则
* 一个cronExpression表达式有至少6个（也可能是7个）由空格分隔的时间元素。从左至右，这些元素的定义如下：
	1．秒（0–59）
	2．分钟（0–59）
	3．小时（0–23）
	4．月份中的日期（1–31）
	5．月份（1–12或JAN–DEC）
	6．星期中的日期（1–7或SUN–SAT）
	7．年份（1970–2099）
	秒0-59 , - * /
	分0-59 , - * /
	小时0-23 , - * /
	日1-31 , - * ? / L W C
	月1-12 or JAN-DEC , - * /
	周几1-7 or SUN-SAT , - * ? / L C #
	年(可选字段) empty, 1970-2099 , - * /
	
	
	“*”——字符可以用于所有字段，在“分”字段中设为"*"表示"每一分钟"的含义。
	“?”——字符可以用在“日”和“周几”字段.它用来指定'不明确的值'.这在你需要指定这两个字段中的某一个值而不是另外一个的时候会被用到。在后面的例子中可以看到其含义。
	“-”——字符被用来指定一个值的范围，比如在“小时”字段中设为"10-12"表示"10点到12点"。
	“,”——字符指定数个值。比如在“周几”字段中设为"MON,WED,FRI"表示"the days Monday, Wednesday, and Friday"。
	“/”——字符用来指定一个值的的增加幅度.比如在“秒”字段中设置为"0/15"表示"第0, 15, 30,和45秒"。而"5/15"则表示"第5, 20, 35,和50".在'/'前加"*"字符相当于指定从0秒开始.每个字段都有一系列可以开始或结束的数值。对于“秒”和“分”字段来说，其数值范围为0到59，对于“小时”字段来说其为0到23,对于“日”字段来说为0到31,而对于“月”字段来说为1到12。"/"字段仅仅只是帮助你在允许的数值范围内从开始"第n"的值。
	"0 0 12 * * ?"每天中午十二点触发
	"0 15 10 ? * *"每天早上10：15触发
	"0 15 10 * * ?"每天早上10：15触发
	"0 15 10 * * ? *"每天早上10：15触发
	"0 15 10 * * ? 2005" 2005年的每天早上10：15触发
	"0 * 14 * * ?"每天从下午2点开始到2点59分每分钟一次触发
	"0 0/5 14 * * ?"每天从下午2点开始到2：55分结束每5分钟一次触发
	"0 0/5 14,18 * * ?"每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
	"0 0-5 14 * * ?"每天14:00至14:05每分钟一次触发
	"0 10,44 14 ? 3 WED"三月的每周三的14：10和14：44触发
	"0 15 10 ? * MON-FRI"每个周一、周二、周三、周四、周五的10：15触发
	"0 15 10 15 * ?"每月15号的10：15触发
	"0 15 10 L * ?"每月的最后一天的10：15触发
	"0 15 10 ? * 6L"每月最后一个周五的10：15
 */