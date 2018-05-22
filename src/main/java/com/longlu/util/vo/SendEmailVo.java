package com.longlu.util.vo;

/**
 * @author yangcan
 * 发送邮件VO
 *
 */
public class SendEmailVo {

	private String fromEmail = "longlu@easychannel.com.cn";  //发送人
	private String toEmail;    //接收人,多个接收人 邮箱之间用逗号隔开
	private String subject;    //邮件标题
	private String content;    //邮件内容,带图片,附件邮件内容都使用此字段
	private String imgStrings;   //图片文件 ，存储图片文件的路径，多个图片文件用*隔开
	private String[] imgFiles;   //图片文件数组
	private String partContent;  //带图片,附件邮件内容 (带图片,附件专用)
	private String attachStrings;  //附件文件，存储附件文件的路径，多个附件文件用*隔开
	private String[] attachFiles;  //附件文件数组
	
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getImgFiles() {
		return imgFiles;
	}
	public void setImgFiles(String[] imgFiles) {
		this.imgFiles = imgFiles;
	}
	public String getPartContent() {
		return partContent;
	}
	public void setPartContent(String partContent) {
		this.partContent = partContent;
	}
	public String getImgStrings() {
		return imgStrings;
	}
	public void setImgStrings(String imgStrings) {
		this.imgStrings = imgStrings;
	}
	public String getAttachStrings() {
		return attachStrings;
	}
	public void setAttachStrings(String attachStrings) {
		this.attachStrings = attachStrings;
	}
	public String[] getAttachFiles() {
		return attachFiles;
	}
	public void setAttachFiles(String[] attachFiles) {
		this.attachFiles = attachFiles;
	}
	
}
