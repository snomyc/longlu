package com.longlu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.longlu.util.vo.SendEmailVo;
import com.sun.mail.util.MailSSLSocketFactory;

/***
 * 信头字段 目的 　　
 * From 邮件作者 　　
 * Sender 发信人 　　
 * Reply-To 回邮地址 　　
 * To 收信人地址 　　
 * CC 抄送：另一个收信人地址
 * BCC 密送：收信人地址，但其它收信人看不到这个收信人的地址。 　　
 * Subject 主题 　　
 * Comments 备注 　　
 * Keywords
 * 关键字，用来进一步搜索邮件 　　
 * In-Reply-To 被当前邮件回复的邮件的ID 　　
 * References 几乎同In-Reply-To一样
 * Encrypted 加密邮件的加密类型 　　
 * Date 发信日期
 * *
 ***/
public class SendMail {
	
	private static Session session;
	private static Transport ts;
	private static MimeMessage message;
	
	//静态块
	static{
		try {
			Properties prop = new Properties();
			prop.setProperty("mail.host", "smtp.qq.com");
			prop.setProperty("mail.transport.protocol", "smtp");//选择协议
			prop.setProperty("mail.smtp.auth", "true");  //普通客户端
			prop.setProperty("mail.smtp.ssl.enable", "true");  //避免ssl协议
			
			//下面的是使用ssl协议，不知道为什么458508804可以发，其他的QQ要下面4句代码才行
			//测试发现用 prop.setProperty("mail.smtp.ssl.enable", "true");  //避免ssl协议 也可代替下面4句
			//使用MailSSLSocketFactory避免了需要添加证书，你的密钥库如上所述,或配置自己的TrustManager
			//MailSSLSocketFactory sf = new MailSSLSocketFactory();  
			//sf.setTrustAllHosts(true);  
			//prop.put("mail.smtp.ssl.enable", "true");  
			//prop.put("mail.smtp.ssl.socketFactory", sf); 
			// 使用JavaMail发送邮件的5个步骤
			// 1、创建session
			session = Session.getInstance(prop);
			// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
			session.setDebug(false);
			// 2、通过session得到transport对象
			ts = session.getTransport();
			// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
			//ts.connect("smtp.qq.com", "qq", "password(授权码)");
			ts.connect("smtp.exmail.qq.com", "longlu@easychannel.com.cn","Ec33504991");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		SendEmailVo sendEmailVo = new SendEmailVo();
		sendEmailVo.setToEmail("458508804@qq.com");
		sendEmailVo.setSubject("测试哥");
		sendEmailVo.setContent("1111");
		// 4、创建邮件 发送个人
		SendMail.createSimpleMail(sendEmailVo);
		//Thread.sleep(10000);
		//SendMail.createMorePeopleMails(sendEmailVo);
		
		//第一种通过文件List
//		File f1 = new File("F:\\桌面文件\\ims\\1.png");
//		File f2 = new File("F:\\桌面文件\\ims\\2.png");
//		List<File> fileList = new ArrayList<File>();
//		fileList.add(f1);
//		fileList.add(f2);
//		String[] s = new String[fileList.size()];
//		for (int i = 0; i < fileList.size(); i++) {
//			s[i] = fileList.get(i).toString();
//			System.out.println(s[i]);
//		}
//		sendEmailVo.setImgFiles(s);
//		List<String> imgList = SendMail.getImgHtmlByImgFiles(sendEmailVo.getImgFiles());
//		StringBuffer sb = new StringBuffer();
//		sb.append("大家好，这是我要准备的附件，请看:");
//		for (int i = 0; i < imgList.size(); i++) {
//			sb.append(imgList.get(i));
//		}
//		sendEmailVo.setImgContent(sb.toString());
//		//调用发送图片接口
//		SendMail.createImageMailsByImgFiles(sendEmailVo);
		
		
		//第二种 通过String数组
		sendEmailVo.setImgStrings("F:\\桌面文件\\ims\\1.png*F:\\桌面文件\\ims\\2.png");
		sendEmailVo.setAttachStrings("F:\\桌面文件\\ims\\1.png*F:\\桌面文件\\ims\\2.png");
		//sendEmailVo.setImgStrings("");
		List<String> imgList = SendMail.getImgHtml(sendEmailVo.getImgStrings());
		StringBuffer sb = new StringBuffer();
		sb.append("大家好，这是我要准备的附件，请看:");
		for (int i = 0; i < imgList.size(); i++) {
			sb.append(imgList.get(i));
		}
		sendEmailVo.setContent(sb.toString());
		//调用发送图片接口
		SendMail.createMixedMail(sendEmailVo);
		
	}
	
	
	/**
	 * @return 返回基本邮件信息公用模版(设置好了 发送人，接收人，邮件主题，邮件内容等)
	 * @throws Exception 
	 */
	public static MimeMessage geMailstMimeMessage(SendEmailVo sendEmailVo) throws Exception {
		// 收件人邮件组
		String[] receivers = sendEmailVo.getToEmail().split(",");
		
		Address[] sendTo = new InternetAddress[receivers.length];

		if (receivers != null) {
			for (int i = 0; i < receivers.length; i++) {
				sendTo[i] = new InternetAddress(receivers[i]);
			}
		} else {
			//sendTo = new InternetAddress[1];
			//sendTo[0] = new InternetAddress("snomyc@qq.com");
			return null;
		}
		// 创建邮件对象
		message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(sendEmailVo.getFromEmail()));
		// 指明邮件的收件人setRecipients则是发送多人 setRecipient是发送一个人
		message.setRecipients(Message.RecipientType.TO, sendTo);
		// 邮件的标题
		message.setSubject(sendEmailVo.getSubject());
		// 邮件的文本内容
		message.setContent(sendEmailVo.getContent(), "text/html;charset=UTF-8");
		// 返回创建好的邮件
		return message;
	}
	
	
	/**
	 * @param session
	 * @param fromEmail  发送人邮箱
	 * @param toEmail   接收人邮箱
	 * @return 发送只包含文字的邮件，而且是单人的
	 * @throws Exception
	 */
	public static void createSimpleMail(SendEmailVo sendEmailVo)throws Exception {
		
		//如果没有连接邮件服务器
		if(!ts.isConnected()) {
			ts.connect();
		}
		// 创建邮件对象
		message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(sendEmailVo.getFromEmail()));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(
				sendEmailVo.getToEmail()));
		// 邮件的标题
		message.setSubject(sendEmailVo.getSubject());
		// 邮件的文本内容
		message.setContent(sendEmailVo.getContent(), "text/html;charset=UTF-8");
		// 5、发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}
	
	/** 
	* @Title: createMorePeopleMails 
	* @Description: TODO 
	* @param @param sendEmailVo
	* @param @throws Exception 发送多人的邮件 ,可以包含单人
	* @return void    返回类型 
	* @authoer yangcan
	* @throws 
	*/ 
	public static void createMorePeopleMails(SendEmailVo sendEmailVo)throws Exception {

		message = SendMail.geMailstMimeMessage(sendEmailVo);
		//如果没有连接邮件服务器
		if(!ts.isConnected()) {
			ts.connect();
		}
		//发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}
	
	

	/** 
	* @Title: getImgHtml 
	* @Description: TODO 
	* @param @param imgFiles
	* @param @return 将图片文件格式转换为html格式如xxx.jpg <img src='cid:xxx.jpg'>
	* @return List<String>    返回类型 
	* @authoer yangcan
	* @throws 
	*/ 
	public static List<String> getImgHtml(String[] imgFiles) {
		List<String> list = new ArrayList<String>();
		if(imgFiles != null) {
			for (int i = 0; i < imgFiles.length; i++) {
				String filename = imgFiles[i].substring(imgFiles[i].lastIndexOf("\\")+1);
				list.add("<img src='cid:"+filename +"'>");
			}
		}
		return list;
	}
	
	
	/** 
	* @Title: getImgHtml 
	* @Description: TODO 
	* @param @param imgStrings
	* @param @return 将图片文件格式转换为html格式如xxx.jpg <img src='cid:xxx.jpg'>
	* @return List<String>    返回类型 
	* @authoer yangcan
	* @throws 
	*/ 
	public static List<String> getImgHtml(String imgStrings) {
		List<String> list = new ArrayList<String>();
		if(imgStrings != null) {
			String[] imgNames = imgStrings.split("\\*");
			for (int i = 0; i < imgNames.length; i++) {
				String filename = imgNames[i].substring(imgNames[i].lastIndexOf("\\")+1);
				list.add("<img src='cid:"+filename +"'>");
			}
		}
		return list;
	}
	

	/**
	 * @Method: createImageMails
	 * @Description: 生成一封邮件正文带图片的邮件, 可以发送单人也可以发送多人 
	 * String[] imgFiles;//图片文件数组 
	 * 或
	 * String imgStrings;   //图片文件 ，存储图片文件的路径，多个图片文件用*隔开
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static void createImageMails(SendEmailVo sendEmailVo) throws Exception {

		message = SendMail.geMailstMimeMessage(sendEmailVo);
		// 准备邮件数据
		// 准备邮件正文数据
		MimeBodyPart text = new MimeBodyPart();
		text.setContent(sendEmailVo.getContent(),"text/html;charset=UTF-8");
		
		String[] imgNames;
		if(sendEmailVo.getImgStrings() != null && !sendEmailVo.getImgStrings().equals("")) {
			imgNames = sendEmailVo.getImgStrings().split("\\*");
		}else {
			imgNames = sendEmailVo.getImgFiles();
		}
		
		// 描述数据关系
		MimeMultipart mm = new MimeMultipart();
		if(imgNames != null) {
			for (int i = 0; i < imgNames.length; i++) {
				// 准备图片数据
				MimeBodyPart image = new MimeBodyPart();
//				String f1 = imgNames[i];
//				String f2 = imgNames[i].substring(imgNames[i].lastIndexOf("\\")+1);
//				System.out.println(f1);
//				System.out.println(f2);
				
				DataHandler dh = new DataHandler(new FileDataSource(imgNames[i]));
				image.setDataHandler(dh);
				//图片名称
				image.setContentID(imgNames[i].substring(imgNames[i].lastIndexOf("\\")+1));
				//有多少图片加多少图片
				mm.addBodyPart(image);
			}
		}
		
		mm.addBodyPart(text);
		mm.setSubType("related");
		message.setContent(mm);
		message.saveChanges();
		// 将创建好的邮件写入到E盘以文件的形式进行保存
		//message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
		//如果没有连接邮件服务器
		if(!ts.isConnected()) {
			ts.connect();
		}
		//发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}
	

	/** 
	* @Title: createAttachMail
	* @Description: 创建一封带附件的邮件  
	*  使用附件文件，存储附件文件的路径，多个附件文件用*隔开String attachStrings
	*  或
	*  使用附件文件数组 String[] attachFiles
	* @param @param sendEmailVo
	* @param @throws Exception 设定文件
	* @return void    返回类型 
	* @authoer yangcan
	* @throws 
	*/ 
	public static void createAttachMail(SendEmailVo sendEmailVo) throws Exception {

		// 设置邮件的基本信息
		message = SendMail.geMailstMimeMessage(sendEmailVo);
		
		// 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
		MimeBodyPart text = new MimeBodyPart();
		text.setContent(sendEmailVo.getContent(), "text/html;charset=UTF-8");

		String[] attahNames;
		if(sendEmailVo.getAttachStrings() != null && !sendEmailVo.getAttachStrings().equals("")) {
			attahNames = sendEmailVo.getAttachStrings().split("\\*");
		}else {
			attahNames = sendEmailVo.getAttachFiles();
		}
		
		// 创建容器描述数据关系
		MimeMultipart mp = new MimeMultipart();
		if(attahNames != null) {
			for (int i = 0; i < attahNames.length; i++) {
				// 创建邮件附件
				MimeBodyPart attach = new MimeBodyPart();
				DataHandler dh = new DataHandler(new FileDataSource(attahNames[i]));
				attach.setDataHandler(dh);
				attach.setFileName(dh.getName());
				//有多少附件加多少附件
				mp.addBodyPart(attach);
				
			}
		}
		
		mp.addBodyPart(text);
		mp.setSubType("mixed");
		message.setContent(mp);
		message.saveChanges();
		// 将创建的Email写入到E盘存储
		//message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
		//如果没有连接邮件服务器
		if(!ts.isConnected()) {
			ts.connect();
		}
		//发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}
	
	/** 
	* @Title: createMixedMail 
	* @Description: 生成一封带附件和带图片的邮件 
	* @param @param sendEmailVo
	* @param @throws Exception 设定文件
	* @return void    返回类型 
	* @authoer yangcan
	* @throws 
	*/ 
	public static void createMixedMail(SendEmailVo sendEmailVo) throws Exception {
		
		// 设置邮件的基本信息
		message = SendMail.geMailstMimeMessage(sendEmailVo);
		
		// 正文
		MimeBodyPart text = new MimeBodyPart();
		text.setContent(sendEmailVo.getContent(),"text/html;charset=UTF-8");
		
		
		//先设置图片容器
		String[] imgNames;
		if(sendEmailVo.getImgStrings() != null && !sendEmailVo.getImgStrings().equals("")) {
			imgNames = sendEmailVo.getImgStrings().split("\\*");
		}else {
			imgNames = sendEmailVo.getImgFiles();
		}
		
		MimeMultipart mp1 = new MimeMultipart();
		
		if(imgNames != null) {
			for (int i = 0; i < imgNames.length; i++) {
				MimeBodyPart image = new MimeBodyPart();
				DataHandler dh = new DataHandler(new FileDataSource(imgNames[i]));
				image.setDataHandler(dh);
				//图片名称
				image.setContentID(imgNames[i].substring(imgNames[i].lastIndexOf("\\")+1));
				//有多少图片加多少图片
				mp1.addBodyPart(image);
			}
		}
		
		//设置附件容器
		String[] attachNames;
		if(sendEmailVo.getAttachStrings() != null && !sendEmailVo.getAttachStrings().equals("")) {
			attachNames = sendEmailVo.getAttachStrings().split("\\*");
		}else {
			attachNames = sendEmailVo.getAttachFiles();
		}
		
		// 创建容器描述数据关系
		MimeMultipart mp2 = new MimeMultipart();
		
		if(attachNames != null) {
			for (int i = 0; i < attachNames.length; i++) {
				// 创建邮件附件
				MimeBodyPart attach = new MimeBodyPart();
				DataHandler dh = new DataHandler(new FileDataSource(attachNames[i]));
				attach.setDataHandler(dh);
				attach.setFileName(dh.getName());
				//有多少附件加多少附件
				mp2.addBodyPart(attach);
				
			}
		}
		

		// 代表正文的bodypart
		MimeBodyPart content = new MimeBodyPart();
		content.setContent(mp1);
		// 描述关系:正文和图片
		mp1.addBodyPart(text);
		mp1.setSubType("related");
		
		mp2.addBodyPart(content);
		mp2.setSubType("mixed");
		message.setContent(mp2);
		message.saveChanges();

		//message.writeTo(new FileOutputStream("E:\\MixedMail.eml"));
		// 返回创建好的的邮件
		//如果没有连接邮件服务器
		if(!ts.isConnected()) {
			ts.connect();
		}
		//发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}
}