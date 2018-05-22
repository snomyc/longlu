package com.longlu.util;

import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

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
 * mail 1.4.3.jar可以解决jdk7不兼容的问题，mail 1.4.1.jar会使import javax.mail.internet.MimeMultipart;报错
 ***/
public class SendMailTest {

	public static void main(String[] args) throws Exception {

		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.qq.com");
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		// 使用JavaMail发送邮件的5个步骤
		// 1、创建session
		Session session = Session.getInstance(prop);
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(true);
		// 2、通过session得到transport对象
		Transport ts = session.getTransport();
		// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
		ts.connect("smtp.qq.com", "snomyc@qq.com", "snomyc5201314");
		// 4、创建邮件
		Message message = createSimpleMail(session);
		// 发送多人
		//Message message = createMorePeopleMail(session);
		
		//发送带图片的邮件
		//Message message = createImageMail(session);
		
		//发送带附件的邮件
		//Message  message = createAttachMail(session);
		
		//发送图片和带附件的邮件
		//Message message = createMixedMail(session);
		
		// 5、发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}

	/***
	 * 发送只包含文字的邮件，而且是单人的
	 * **/
	public static MimeMessage createSimpleMail(Session session)
			throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress("snomyc@qq.com"));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(
				"474457717@qq.com"));
		// 邮件的标题
		message.setSubject("不好意思，打扰了!");
		// 邮件的文本内容
		message.setContent("不小心少打了位数字，导致qq号输入错误,误将测试邮件发送到你的QQ中，造成困扰十分抱歉，请看到邮件后删除即可!", "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}

	/****
	 * 发送多人的邮件
	 * 
	 * @throws Exception
	 * @throws
	 * **/
	public static MimeMessage createMorePeopleMail(Session session)
			throws Exception {
		// 收件人邮件组
		String[] receivers = new String[] { "snomyc@qq.com",
				"250793091@qq.com", "474457717@qq.com" };
		Address[] sendTo = new InternetAddress[receivers.length];

		if (receivers != null) {
			for (int i = 0; i < receivers.length; i++) {
				sendTo[i] = new InternetAddress(receivers[i]);
			}
		} else {
			sendTo = new InternetAddress[1];
			sendTo[0] = new InternetAddress("snomyc@qq.com");
		}

		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress("snomyc@qq.com"));
		// 指明邮件的收件人setRecipients则是发送多人 setRecipient是发送一个人
		message.setRecipients(Message.RecipientType.TO, sendTo);
		// 邮件的标题
		message.setSubject("我是测试哥!");
		// 邮件的文本内容
		message.setContent("测试http://www.qq.com", "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}

	/**
	 * @Method: createImageMail
	 * @Description: 生成一封邮件正文带图片的邮件
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createImageMail(Session session) throws Exception {
		// 创建邮件
		MimeMessage message = new MimeMessage(session);
		// 设置邮件的基本信息
		// 发件人
		message.setFrom(new InternetAddress("snomyc@qq.com"));
		// 收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(
				"474457717@qq.com"));
		// 邮件标题
		message.setSubject("带图片的邮件");

		// 准备邮件数据
		// 准备邮件正文数据
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("这是一封邮件正文带图片<img src='cid:yangcan.jpg'>的邮件",
				"text/html;charset=UTF-8");
		// 准备图片数据
		MimeBodyPart image = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("F:\\yc.jpg"));
		image.setDataHandler(dh);
		image.setContentID("yangcan.jpg");
		// 描述数据关系
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text);
		mm.addBodyPart(image);
		mm.setSubType("related");

		message.setContent(mm);
		message.saveChanges();
		// 将创建好的邮件写入到E盘以文件的形式进行保存
		//message.writeTo(new FileOutputStream("F:\\ImageMail.eml"));
		// 返回创建好的邮件
		return message;
	}

	/**
	 * @Method: createAttachMail
	 * @Description: 创建一封带附件的邮件
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createAttachMail(Session session)
			throws Exception {
		MimeMessage message = new MimeMessage(session);

		// 设置邮件的基本信息
		// 发件人
		message.setFrom(new InternetAddress("snomyc@qq.com"));
		// 收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(
				"474457717@qq.com"));
		// 邮件标题
		message.setSubject("JavaMail邮件发送测试");

		// 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("使用JavaMail创建的带附件的邮件", "text/html;charset=UTF-8");

		// 创建邮件附件
		MimeBodyPart attach = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("F:\\yc.jpg"));
		attach.setDataHandler(dh);
		attach.setFileName(dh.getName());

		// 创建容器描述数据关系
		MimeMultipart mp = new MimeMultipart();
		mp.addBodyPart(text);
		mp.addBodyPart(attach);
		mp.setSubType("mixed");

		message.setContent(mp);
		message.saveChanges();
		// 将创建的Email写入到E盘存储
		//message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
		// 返回生成的邮件
		return message;
	}

	/**
	 * @Method: createMixedMail
	 * @Description: 生成一封带附件和带图片的邮件
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createMixedMail(Session session) throws Exception {
		// 创建邮件
		MimeMessage message = new MimeMessage(session);

		// 设置邮件的基本信息
		message.setFrom(new InternetAddress("snomyc@qq.com"));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(
				"474457717@qq.com"));
		message.setSubject("带附件和带图片的的邮件");

		// 正文
		MimeBodyPart text = new MimeBodyPart();
		text.setContent("xxx这是女的xxxx<br/><img src='cid:yangcan.jpg'>",
				"text/html;charset=UTF-8");

		// 图片
		MimeBodyPart image = new MimeBodyPart();
		image.setDataHandler(new DataHandler(new FileDataSource("F:\\yc.jpg")));
		image.setContentID("yangcan.jpg");

		// 附件1
		MimeBodyPart attach = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource("F:\\yc.rar"));
		attach.setDataHandler(dh);
		attach.setFileName(dh.getName());

		// 附件2
		MimeBodyPart attach2 = new MimeBodyPart();
		DataHandler dh2 = new DataHandler(new FileDataSource("F:\\yc2.rar"));
		attach2.setDataHandler(dh2);
		attach2.setFileName(MimeUtility.encodeText(dh2.getName()));

		// 描述关系:正文和图片
		MimeMultipart mp1 = new MimeMultipart();
		mp1.addBodyPart(text);
		mp1.addBodyPart(image);
		mp1.setSubType("related");

		// 描述关系:正文和附件
		MimeMultipart mp2 = new MimeMultipart();
		mp2.addBodyPart(attach);
		mp2.addBodyPart(attach2);

		// 代表正文的bodypart
		MimeBodyPart content = new MimeBodyPart();
		content.setContent(mp1);
		mp2.addBodyPart(content);
		mp2.setSubType("mixed");

		message.setContent(mp2);
		message.saveChanges();

		//message.writeTo(new FileOutputStream("E:\\MixedMail.eml"));
		// 返回创建好的的邮件
		return message;
	}
}