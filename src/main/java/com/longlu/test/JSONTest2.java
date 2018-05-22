package com.longlu.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;  
import java.util.Date;
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  
  





import org.apache.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSON;  
import net.sf.json.JSONArray;  
import net.sf.json.JSONObject;  
import net.sf.json.JsonConfig;  
  
public class JSONTest2 {  
      
      
    private Date name;  
    private Double password;
      
    public JSONTest2() {  
        super();  
    }  
  
    public JSONTest2(Date name, Double password) {  
        super();  
        this.name = name;  
        this.password = password;  
    }  
  
    public Date getName() {  
        return name;  
    }  
  
    public void setName(Date name) {  
        this.name = name;  
    }  
  
    public Double getPassword() {  
        return password;  
    }  
    
    
	public void setPassword(Double password) {
		this.password = password;
	}
	
	
	public static String test3() {
		for (int i = 0; i < 5; i++) {
			if(i==1) {
				return "o";
			}
		}
		return null;
	}

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {  
  
        //JSONArray用法  
        //获得JSONArray对象  
        //第一种创建JSONArray对象  
        JSONArray jarray = new JSONArray();  
        //第二种通过方法获取，JSONArray里面有许多方法返回的是JSONArray对象  
        //如:fromObject方法等等  
          
        /*** 
         * JSONObject和JSONArray的区别 
         * 区别在于JSONObject是一个{}包裹起来的一个对象(Object)， 
            而JSONArray则是[]包裹起来的一个数组(Array)， 
            说白点就是一个是数组一个是对象或字符串 
             
            JSONObjec 有键名，JSONArray没有。 
            所以解析的时候JSONObject是JSONObject.getString("msg")， 
            而JSONArray是JSONArray.getString(5)，这个数字5是位置 
             
            第二个要注意的是，JSONObject不能转换List类型， 
            JSONObject.fromObject(list) 会报错 
         * ***/  
          
        /*** 
         *  
         * 准确说，是如果里面是键值对的话，比如 {"3":"3","null":"0","2":"2","1":"1","5":"5","4":"4"}  
         * 外面一定要使用{}大括号，如果里面是["1","2"]  
         * 这类型的，一定要使用[]中括号 
         * */  
          
        /** 
         * List集合转换为json代码 
         * */  
        List list = new ArrayList();  
        list.add("1");  
        list.add("2");  
        JSONArray jlist = JSONArray.fromObject(list);  
        //打印查看  
        System.out.println(jlist);  
        //结果  ["1","2"]  
        //获得索引相关的字符串  
        System.out.println(jlist.getString(1));  
        //结果  2  
        System.out.println("-------------------------------------------------");    
          
        /** 
         * List集合内含对象转换为json代码, 
         * **/  
        JSONTest j1 = new JSONTest("2016-12-09 22:40:00",123456);  
        JSONTest j2 = new JSONTest("2016-12-09 22:40:00",123);  
          
        List<JSONTest> claList = new ArrayList<JSONTest>();  
        claList.add(j1);  
        claList.add(j2);  
        JSONArray jiequ = JSONArray.fromObject(claList);  
        System.out.println(jiequ);  
        //结果  [{"name":"admin","password":"123456"},{"name":"yonghu","password":"123"}]  
          
        
        
        Gson gson = new Gson();
        List<JSONTest2> gsonList = gson.fromJson(jiequ.toString(), new TypeToken<List<JSONTest2>>(){}.getType());
        
        for(JSONTest2 j : gsonList) {  
            System.out.println(j.getName()+":"+j.getPassword());  
        }  
        
        /** 
         * 将json对象转换为List类型  ,对于list中包含为类的 
         * */  
          
        //List<JSONTest> lj = JSONArray.toList(jiequ, JSONTest.class);  
        //等价上面的代码  
//        JsonConfig jsonConfig = new JsonConfig();
//        jsonConfig.setEnclosedType(TradeBuyerMessage[].class);
//        List<JSONTest2> lj = JSONArray.toList(jiequ, new JSONTest2(),jsonConfig);  
//        for(JSONTest2 j : lj) {  
//            System.out.println(j.getName()+":"+j.getPassword());  
//        }  
        
        System.out.println(JSONTest2.test3());
          
    }  
  
}  

