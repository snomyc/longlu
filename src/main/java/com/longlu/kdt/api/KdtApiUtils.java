package com.longlu.kdt.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.longlu.domain.Express;
import com.longlu.util.JsonUtil;

public class KdtApiUtils {
	
	public static String youzanAPIService(String method, HashMap<String,String> params) {
		String strResult = "";
		try {
			KdtApiClient3 client = new KdtApiClient3(KdtApiItem.APP_ID, KdtApiItem.APP_SECRET);
			HttpResponse response = client.get(method, params);
			HttpEntity entity = response.getEntity();
			String charset = "UTF-8";
	    	if(entity !=null) {
	    		charset = JsonUtil.getContentCharSet(entity);
	    		// 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1 
	    		strResult = EntityUtils.toString(entity,charset);
	    	}
//			System.out.println(strResult.toString());
		} catch (Exception e) {
			return null;
		}
		
		return strResult;
	}

	/**
	 * @throws Exception
	 * 获取快递公司的列表JSON
	 */
	public static List<Express> expressGet(){
		
		String method = "youzan.logistics.express.get";
		HashMap<String, String> params = new HashMap<String, String>();
		String result = KdtApiUtils.youzanAPIService(method, params);
		//转换成object类型
    	JSONObject jo = JSONObject.fromObject(result);
    	//获得response节点
    	JSONObject root = jo.getJSONObject("response");
    	//获得root节点下的trades节点
    	JSONArray jarray = JSONArray.fromObject(root.get("allExpress"));
		Gson gson = new Gson();
    	List<Express> expressList = gson.fromJson(jarray.toString(), new TypeToken<List<Express>>(){}.getType());
		return expressList;
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public static boolean onlineConfirm(HashMap<String, String> params) {
		
		String method = "youzan.logistics.online.confirm";
		String result = KdtApiUtils.youzanAPIService(method, params);
		//转换成object类型
    	JSONObject jo = JSONObject.fromObject(result);
		//获得response节点
    	JSONObject root = jo.getJSONObject("response");
    	if(!root.isNullObject()) {
    		Boolean flag = (Boolean) root.get("is_success");
    		return flag;
    	}else {
    		System.out.println(jo.getJSONObject("error_response").get("msg"));
    	}
		return false;
	}
	
	
	public static void main(String[] args) {
	}
}
