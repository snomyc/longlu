package com.longlu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.SecurityUtils;

public class HttpClientHelper {

	/**
	 * 调用 get method 有设置 cookie
	 * 
	 * @param url
	 *            访问路径
	 * @return
	 */
	public static String httpGetCookie(String url) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(url);
			Object CASTGC = SecurityUtils.getSubject().getSession()
					.getAttribute("CASTGC");
			getMethod.setHeader("Cookie", "CASTGC=" + CASTGC);
			HttpResponse response = httpclient.execute(getMethod);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				String backData = "";
				String line = null;
				while ((line = reader.readLine()) != null) {
					backData += line;
				}
				if (entity != null) {
					entity.consumeContent();
				}
				return backData;
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 调用 post 方法 有设置 cookie
	 * 
	 * @param url
	 *            访问路径
	 * @return
	 */
	public static String httpPostCookie(String url) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost postMethod = new HttpPost(url);
			Object CASTGC = SecurityUtils.getSubject().getSession()
					.getAttribute("CASTGC");
			postMethod.setHeader("Cookie", "CASTGC=" + CASTGC);
			HttpResponse response = httpclient.execute(postMethod);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				String backData = "";
				String line = null;
				while ((line = reader.readLine()) != null) {
					backData += line;
				}
				if (entity != null) {
					entity.consumeContent();
				}
				return backData;
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 调用 post 方法 有设置 cookie
	 * 
	 * @param postMethod
	 *            方法，可设置参数
	 * @return
	 */
	public static String httpPostCookie(HttpPost postMethod) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			Object CASTGC = SecurityUtils.getSubject().getSession()
					.getAttribute("CASTGC");
			postMethod.setHeader("Cookie", "CASTGC=" + CASTGC);
			HttpResponse response = httpclient.execute(postMethod);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				String backData = "";
				String line = null;
				while ((line = reader.readLine()) != null) {
					backData += line;
				}
				if (entity != null) {
					entity.consumeContent();
				}
				return backData;
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * http get method 无 cookie
	 * 
	 * @param url
	 * @return
	 */
	public static String httpGet(String url) {
		try {
			
			 //HttpClient client = new DefaultHttpClient();
	        //如果使用HttpClient对象则会报错SSLexception
	        CloseableHttpClient httpclient = HttpClients.createDefault();
			//HttpClient httpclient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(url);
			HttpResponse response = httpclient.execute(getMethod);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				String backData = "";
				String line = null;
				while ((line = reader.readLine()) != null) {
					backData += line;
				}
				if (entity != null) {
					entity.consumeContent();
				}
				return backData;
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 带参数的http get请求
	 * 
	 * @param url
	 * @param params
	 *            要提交的请求参数
	 * @return
	 * @throws Exception
	 */
	public static String httpGetCookie(String url, Map<String, Object> params)
			throws Exception {
		String paramStr = "";
		if (params != null && !params.isEmpty()) {
			Set<Entry<String, Object>> set = params.entrySet();
			for (Entry<String, Object> entry : set) {
				paramStr += "&" + entry.getKey() + "=" + entry.getValue();
			}
		}
		url = url.contains("?") ? url + paramStr : url
				+ paramStr.replaceFirst("&", "?");
		return httpGetCookie(url);
	}
	
	public static String httpPost( String url, Map<String, Object> parameters) throws ClientProtocolException, IOException{
		//如果使用HttpClient对象则会报错SSLexception
		//HttpClient httpclient = new DefaultHttpClient();
        CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost postMethod = new HttpPost(url);
		if( parameters != null && parameters.size() != 0 ){
			postMethod.setEntity(new UrlEncodedFormEntity( mapToList(parameters), "UTF-8"));
		}
		HttpResponse response = httpclient.execute(postMethod);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(entity.getContent(), "UTF-8"));
			StringBuffer backData = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				backData.append(line);
			}
			reader.close();
			return backData.toString();
		}
		return null;
		
	}
	
	public static String httpPost( String url, String name, String value) throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost postMethod = new HttpPost(url);
		if( StringUtils.isNotBlank(url) && StringUtils.isNotBlank(name) &&  StringUtils.isNotBlank(value)){
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add( new BasicNameValuePair( name, value ) );
			postMethod.setEntity(new UrlEncodedFormEntity( list, "UTF-8"));
			HttpResponse response = httpclient.execute(postMethod);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuffer backData = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					backData.append(line);
				}
				reader.close();
				return backData.toString();
			}
		}
		return null;
	}
	
	private static  List<NameValuePair> mapToList(Map<String, Object> params){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for( String key : params.keySet() ) {
			list.add( new BasicNameValuePair( key, params.get(key).toString() ) );
		}
		return list;
	}
}
	
