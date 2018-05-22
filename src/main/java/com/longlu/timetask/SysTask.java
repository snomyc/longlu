package com.longlu.timetask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longlu.util.SendMail;
import com.longlu.util.vo.SendEmailVo;

public class SysTask {
	
	private static Logger logger = LoggerFactory.getLogger(SysTask.class);

	public void sync() {
		try {
			logger.error("开始发送...");
			SendEmailVo sendEmailVo = new SendEmailVo();
			//sendEmailVo.setToEmail("149342700@qq.com,156025864@qq.com");
			sendEmailVo.setToEmail("458508804@qq.com");
			sendEmailVo.setSubject("定时任务测试!");
			sendEmailVo.setContent("定时任务测试,发送....!");;
			//发送多人
			SendMail.createAttachMail(sendEmailVo);
			logger.error("发送成功...");
		} catch (Exception e) {
			logger.error("发送失败...");
		}
	}
}
