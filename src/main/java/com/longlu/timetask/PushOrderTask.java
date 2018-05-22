package com.longlu.timetask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.longlu.service.TradeOrderService;
import com.longlu.service.UsersService;
import com.longlu.util.SendMail;
import com.longlu.util.vo.SendEmailVo;

/**
 * @author Administrator
 * 订单推送到用户邮件 定时任务 半小时触发一次(早上9点到晚上20点)
 */
public class PushOrderTask {
	
	private static Logger logger = LoggerFactory.getLogger(PushOrderTask.class);

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private TradeOrderService tradeOrderService;
	
	public void sync() {
		try{
			//查询需要邮件推送的用户信息
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("f1", "1");
			List<Map<String,Object>> userList = usersService.selectUsersBySelective(params);
			Map<String, Object> paramsMap = new HashMap<String,Object>();
			//遍历用户
			for (Map<String,Object> users : userList) {
				String rolename = users.get("rolename").toString();
				String supplierName = users.get("f0").toString();
				String email = users.get("email").toString();
				if(rolename.equals("财务")) {
					paramsMap.put("orderReview", 0);
					paramsMap.put("supplierNames", supplierName.split(","));
					paramsMap.put("emailStatus", 0);
					Long count = tradeOrderService.selectTradeOrderBySelectiveCount(paramsMap);
					if(count > 0){
						String content = "您有"+count+"个待审批订单,请点击 http://118.178.193.88:8888/innerbuy/index.html 进行操作!";
						this.sendEmailToOrder(email, content);
					}
					
					//更新邮件发送状态
					paramsMap.put("emailStatus", 1);
					tradeOrderService.updateTradeOrderBySelectiveCount(paramsMap);
				}else if(rolename.equals("仓库")) {
					paramsMap.put("orderReview", 1);
					paramsMap.put("supplierNames", supplierName.split(","));
					paramsMap.put("emailStatus", 0);
					Long count = tradeOrderService.selectTradeOrderBySelectiveCount(paramsMap);
					if(count > 0){
						String content = "您有"+count+"个待发货订单,请点击 http://118.178.193.88:8888/innerbuy/index.html 进行操作!";
						this.sendEmailToOrder(email, content);
					}
					
					//更新邮件发送状态
					paramsMap.put("emailStatus", 1);
					tradeOrderService.updateTradeOrderBySelectiveCount(paramsMap);
				}else if(rolename.equals("财务-仓库")) {
					paramsMap.put("orderReview", 0);
					paramsMap.put("supplierNames", supplierName.split(","));
					paramsMap.put("emailStatus", 0);
					Long count = tradeOrderService.selectTradeOrderBySelectiveCount(paramsMap);
					StringBuffer sb = new StringBuffer();
					if(count > 0){
						sb.append("您有"+count+"个待审批订单!");
					}
					paramsMap.put("orderReview", 1);
					count = tradeOrderService.selectTradeOrderBySelectiveCount(paramsMap);
					if(count > 0) {
						sb.append("您有"+count+"个待发货订单!");
					}
					if(StringUtils.isNotBlank(sb.toString())) {
						sb.append("请点击 http://118.178.193.88:8888/innerbuy/index.html 进行操作!");
						this.sendEmailToOrder(email, sb.toString());
					}
					
					//更新邮件发送状态
					paramsMap.put("emailStatus", 1);
					tradeOrderService.updateTradeOrderBySelectiveCount(paramsMap);
					paramsMap.put("orderReview", 0);
					tradeOrderService.updateTradeOrderBySelectiveCount(paramsMap);
				}
				
			}
		}catch (Exception e) {
			logger.error("待审批订单邮件推送失败");
		}
		
	}
	
	public void sendEmailToOrder(String email,String content) throws Exception {
		//查询是否有待审批的订单总数
		//邮件推送
		SendEmailVo sendEmailVo = new SendEmailVo();
		sendEmailVo.setToEmail(email);
		sendEmailVo.setSubject("您有待办订单提醒,请登录龙路仓内购平台进行操作!");
		sendEmailVo.setContent(content);;
		//发送单人
		SendMail.createSimpleMail(sendEmailVo);
	}
}
