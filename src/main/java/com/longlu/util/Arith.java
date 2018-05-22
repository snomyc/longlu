package com.longlu.util;

import java.math.BigDecimal;

public class Arith{
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;
    //这个类不能实例化
    public Arith(){
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){

        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * 被除数/除数 == 商
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。 v1/v2
     * @return 两个参数的商
     */
    public static double divide(double v1,double v2,Integer scale){
    	try{
    		if(scale == null || scale<0){
        		scale = 0;
            }
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    	}catch (Exception e) {
    		return 0;
		}
    }
    
    /** 
    * @Title: multiply 
    * @Description: TODO 
    * @param @param v1
    * @param @param v2
    * @param @param scale 保留的小数位数
    * @param @return 2数相乘 v1*v2
    * @return double    返回类型 
    * @authoer yangcan
    * @throws 
    */ 
    public static double multiply(double v1,double v2,Integer scale) {
    	if(scale == null || scale<0){
    		scale = 0;
        }
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /** 
    * @Title: subtract 
    * @Description: TODO 
    * @param @param v1
    * @param @param v2
    * @param @param scale 保留的小数位
    * @param @return 2数相减 v1-v2
    * @return double    返回类型 
    * @authoer yangcan
    * @throws 
    */ 
    public static double subtract(double v1,double v2,Integer scale) {
    	if(scale == null || scale<0){
    		scale = 0;
        }
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    
    public static double setScale(String number,Integer scale) {
    	if(scale == null || scale<0){
    		scale = 2;
        }
    	return new BigDecimal(number).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
};