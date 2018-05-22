package com.longlu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.longlu.enums.TradeTimeEnum;

public class DateTool {
	Calendar calendar = null;

	public DateTool() {
		calendar = Calendar.getInstance();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
	}

	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	public String getMonth() {
		int m = getMonthInt();
		String[] months = new String[] { "January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		if (m > 12)
			return "Unknown to Man";
		return months[m - 1];
	}

	public String getDay() {
		int x = getDayOfWeek();
		String[] days = new String[] { "Sunday", "Monday", "Tuesday",
				"Wednesday", "Thursday", "Friday", "Saturday" };
		if (x > 7)
			return "Unknown to Man";
		return days[x - 1];
	}

	public int getMonthInt() {
		return 1 + calendar.get(Calendar.MONTH);
	}

	public String getDate() {
		return getYear() + "-" + getMonthInt() + "-" + getDayOfMonth();
	}

	public String getsDate() {
		return getYear() + "" + getMonthInt() + "" + getDayOfMonth();
	}

	public String getTime() {
		return getHour() + ":" + getMinute() + ":" + getSecond();
	}

	public int getDayOfMonth() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getDayOfYear() {
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	public int getWeekOfYear() {
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public int getWeekOfMonth() {
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	public int getDayOfWeek() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public int getHour() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	public int getSecond() {
		return calendar.get(Calendar.SECOND);
	}

	public int getEra() {
		return calendar.get(Calendar.ERA);
	}

	public String getUSTimeZone() {
		String[] zones = new String[] { "Hawaii", "Alaskan", "acific",
				"Mountain", "Central", "Eastern" };
		return zones[10 + getZoneOffset()];
	}

	public int getZoneOffset() {
		return calendar.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000);
	}

	public int getDSTOffset() {
		return calendar.get(Calendar.DST_OFFSET) / (60 * 60 * 1000);
	}

	public int getAMPM() {
		return calendar.get(Calendar.AM_PM);
	}

	public static String getChineseFormatTime() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(new Date());
	}
	
	public static String getStandardTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		return sdf.format(new Date());
	}

	public static String getSimpleFormatTime() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm", Locale.US);

		return sdf.format(new Date());
	}
	
	public static String getSimpleFullFormatTime() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);

		return sdf.format(new Date());
	}

	public int getDay(String strDate) throws Exception {
		int day = 0;
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dformat.parse(strDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		day = cal.get(cal.DAY_OF_YEAR);
		return day;
	}

	public static Date getDate(String strDate) throws Exception {

		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dformat.parse(strDate);

		return date;
	}

	public static Date getDateTime(String strDate) throws Exception {

		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = dformat.parse(strDate);

		return date;
	}

	public static String getDateTime() throws Exception {

		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = dformat.format(new Date());

		return date;
	}

	public int defer(String str1, String str2) throws Exception {
		if (getDay(str1) > getDay(str2))
			return getDay(str2) - getDay(str1) + 365;
		else
			return getDay(str2) - getDay(str1);
	}

	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	/**
	 * @param beforeDay 如果为负数即当前时间前beforeDay天，如果为正数则为当前时间后beforeDay天
	 * @return
	 */
	public static  String getBeforeDateStr(int beforeDay) {
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(new Date());//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, beforeDay);  //设置为当前时间前beforeDay天
		
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String beforeDate = dformat.format(calendar.getTime());
		return beforeDate;
	}
	
	/**
	 * @param beforeDay 如果为负数即当前时间前beforeDay天，如果为正数则为当前时间后beforeDay天
	 * @return
	 */
	public static  Date getBeforeDate(int beforeDay) {
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(new Date());//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, beforeDay);  //设置为当前时间前beforeDay天
		return calendar.getTime();
	}
	
	/**
	* 方法名称: getTodatDate
	* 方法描述：获得今天00：00：00的时间
	* 参数 :@return
	* 返回类型: String
	* @throws
	* 创建人：yagncan   
	* 创建时间：2016年12月28日 下午10:25:05     
	*/
	public static String getTodatDate() {
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String todayDate = dformat.format(new Date());
		return todayDate;
	}

	public static void main(String args[]) {
		try {
			String str1 = "2008-12-14";
			String str2 = "2009-01-12";
			DateTool date = new DateTool();
			// int day = new DateTool().getDay(str);
			// int day1 = new DateTool().getDay(str1);
			// System.out.println(str + "��" + str.subSequence(0, 4) + "��ĵ�" +
			// day
			// + "��");
			// int i = date.defer(str1, str2);
			Date day = date.getDate(str2);
			System.out.println(day);
			
			
//			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
//			String date2 = dformat.format(date.gets);
			System.out.println("年月日:"+date.getDate()+TradeTimeEnum.TWO.getTimeStr());
			System.out.println("小时:"+date.getHour());
			int hour = 14;
			if(6< hour && hour <= 10) {
				System.out.println(date.getDate()+TradeTimeEnum.ONE.getTimeStr());
			}else if(10 < hour && hour <= 14) {
				System.out.println(date.getDate()+TradeTimeEnum.TWO.getTimeStr());
			}else if(14 < hour && hour <= 18) {
				System.out.println(date.getDate()+TradeTimeEnum.THREE.getTimeStr());
			}else if(18 < hour && hour <= 24) {
				System.out.println(date.getDate()+TradeTimeEnum.FOUR.getTimeStr());
			}
			
			
			System.out.println("===="+DateTool.getSimpleFormatTime());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			System.out.println(DateTool.getDateTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("----------------------------------");
		System.out.println(DateTool.getBeforeDate(10));

	}
}