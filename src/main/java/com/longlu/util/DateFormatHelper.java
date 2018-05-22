/**
 * 
 */
package com.longlu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 日期时间转换器
 * 
 * @author huxiang 2012-11-30
 */
public class DateFormatHelper {
    /**
     * yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS      = "yyyyMMddHHmmss";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy/MMdd/ HH:mm:ss
     */
    public static final String YYYYMMDDHH_MM_SS    = "yyyy/MMdd/ HH:mm:ss";

    /**
     * 按照yyyy-MM-dd HH:mm:ss输出结果; 例: date:20121205160708 pattern:yyyyMMddHHmmss
     * 输出结果 2012-12-05 16:07:08
     */
    public static String formatDateString(String date, String pattern) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        try {
            return DateFormatHelper.toLocaleString(new SimpleDateFormat(pattern).parse(date),pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将字符串转换成Date
     */
    public static Date formatDate(String date, String pattern) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将date按照指定类型输出;
     */
    public static String parseDate(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 按yyyy-MM-dd HH:mm:ss 格式输出
     */
    public static String toLocaleString(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    
    /**
     * 按yyyy-MM-dd HH:mm:ss 格式输出
     */
    public static String toLocaleString(Date date) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    
    public static void main(String[] args) {
        System.out.println(DateFormatHelper.toLocaleString(new Date(),"HH"));
    }
}
