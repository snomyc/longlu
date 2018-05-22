package com.longlu.test;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;

import com.longlu.kdt.api.KdtApiUtils;
import com.longlu.util.DateTool;
import com.longlu.util.HttpClientHelper;

public class Test {

	public static void main(String[] args) {
		
//		StringBuffer sb = new StringBuffer();
//		sb.append("1111111").append("-");
//		System.out.println(sb.delete(0, sb.length()).toString());
//		System.out.println(DateTool.getTodatDate());
		
		String s = "x-y-z-c";
		String[] str = s.split("-", 2);
		System.out.println(str[0]);
		System.out.println(str[1]);
		System.out.println(str.length);
		
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(sb.toString())) {
			System.out.println("1111");
		}
		System.out.println("-----------------------");
		佛祖保佑();
		System.out.println("-----------------------");
		福彩交易();
	}
	
	public static void 福彩交易() {
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━");
		System.out.println("|	┏┳━━━━━━━━━━━━┓	|");
		System.out.println("|	┃┃████████████┃	|");
		System.out.println("|	┃┃███████┏━━┓█┃	|");
		System.out.println("|	┣┫███████┃河交┃█┃	|");
		System.out.println("|	┃┃███████┃南易┃█┃	|");
		System.out.println("|	┃┃███████┃福系┃█┃	|");
		System.out.println("|	┣┫███████┃彩统┃█┃	|");
		System.out.println("|	┃┃███████┗━━┛█┃	|");
		System.out.println("|	┣┫████████████┃	|");
		System.out.println("|	┃┃████████████┃	|");
		System.out.println("|	┗┻━━━━━━━━━━━━┛	|");
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━");
		
		System.out.println("	┏┳━━━━━━━━━━━━┓	");
		System.out.println("	┃┃████████████┃	");
		System.out.println("	┃┃███████┏━━┓█┃	");
		System.out.println("	┣┫███████┃河交┃█┃	");
		System.out.println("	┃┃███████┃南易┃█┃	");
		System.out.println("	┃┃███████┃福系┃█┃	");
		System.out.println("	┣┫███████┃彩统┃█┃	");
		System.out.println("	┃┃███████┗━━┛█┃	");
		System.out.println("	┣┫████████████┃	");
		System.out.println("	┃┃████████████┃	");
		System.out.println("	┗┻━━━━━━━━━━━━┛	");
//		┏┳━━━━━━━━━━━━┓
//		┃┃████████████┃
//		┃┃███████┏━━┓█┃
//		┣┫███████┃河交┃█┃
//		┃┃███████┃南易┃█┃
//		┃┃███████┃福系┃█┃
//		┣┫███████┃彩统┃█┃
//		┃┃███████┗━━┛█┃
//		┣┫████████████┃
//		┃┃████████████┃
//		┗┻━━━━━━━━━━━━┛
	}
	
	
	public static void 佛祖保佑(){
		System.out.println("                   _ooOoo_");
		System.out.println("                  o8888888o");
		System.out.println("                  88\" . \"88");
		System.out.println("                  (| -_- |)");
		System.out.println("                  O\\  =  /O");
		System.out.println("               ____/`---'\\");
		System.out.println("             .'  \\|     |//  `.");
		System.out.println("            /  \\\\|||  :  |||//  \\");
		System.out.println("           /  _||||| -:- |||||-  \\");
		System.out.println("           |   | \\\\\\  -  /// |   |");
		System.out.println("           | \\_|  ''\\---/''  |   |");
		System.out.println("           \\  .-\\__  `-`  ___/-. /");
		System.out.println("         ___`. .'  /--.--\\  `. . __");
		System.out.println("      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".");
		System.out.println("     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |");
		System.out.println("     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /");
		System.out.println("======`-.____`-.___\\_____/___.-`____.-'======");
		System.out.println("                   `=---='");
		System.out.println("                  写字楼里写字间，写字间里程序员；");
		System.out.println("                  程序人员写程序，又拿程序换酒钱。 ");
		System.out.println("                  酒醒只在网上坐，酒醉还来网下眠；");
		System.out.println("                  酒醉酒醒日复日，网上网下年复年。");
		System.out.println("                  但愿老死电脑间，不愿鞠躬老板前；");
		System.out.println("                  奔驰宝马贵者趣，公交自行程序员。");
		System.out.println("                  别人笑我忒疯癫，我笑自己命太贱；");
		System.out.println("                  不见满街漂亮妹，哪个归得程序员？");
		
		String client_id = "f56fe54e7445a34cfe";
		String client_secret = "5048811c9b1850458c8f636766ef8bbb";
		String kdt_id = "40080916";
		
		StringBuffer sb = new StringBuffer();
		sb.append("https://open.youzan.com/oauth/token?");
		sb.append("client_id=").append(client_id).append("&");
		sb.append("client_secret=").append(client_secret).append("&");
		sb.append("grant_type=silent&");
		sb.append("kdt_id=").append(kdt_id);
		System.out.println(sb.toString());
		
		String result = HttpClientHelper.httpGet(sb.toString());
		System.out.println(result);
		
		//转换成object类型
    	JSONObject jo = JSONObject.fromObject(result);
    	//获得response节点
    	String token = jo.get("access_token").toString();
    	System.out.println(token);
	}
}
