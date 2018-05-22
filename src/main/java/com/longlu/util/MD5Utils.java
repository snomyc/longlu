package com.longlu.util;
import java.security.MessageDigest;


/**
 * MD5加密
 * 
 * @author GuJiale
 * 
 */
public class MD5Utils {
	
	private MD5Utils(){}

	/**
	 * 使用MD5加密
	 * 
	 * @param string
	 *            String
	 * @return String
	 */
	public static final String MD5(String string) {
		
		//hexDigits是生成md5的编号，可以是任意的16位可重复的数字和字母,顺序也可以随便更换，不过注意的是 自定义的格式只能用自定义的方法解析，其他的解析方法会出现md5编码不一样的情况
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = string.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] arg0) {
		
		String name = "123";
		name = MD5Utils.MD5(name);
		System.out.println(name);
		
		//打印1-100的md5码
//		String md5 = "";
//		for (int i = 0; i < 100; i++) {
//			md5 = String.valueOf(i);
//			md5 = MD5Utils.MD5(md5);
//			System.out.println(i+" md5："+md5);
//		}
		
	}
	
}
