package com.longlu.enums;

/**     
* 类描述：   交易创建时间枚举
* 创建人：yagncan   
* 创建时间：2017年2月18日 下午9:54:23     
*/
public enum TradeTimeEnum {

	ONE(" 00:00:00","0点"),
	TWO(" 10:00:00","10点"),
	THREE(" 14:00:00","14点"),
	FOUR(" 18:00:00","18点");
	
	private TradeTimeEnum(String timeStr, String title) {
		this.timeStr = timeStr;
		this.title = title;
	}
	private String timeStr;
	private String title;
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
