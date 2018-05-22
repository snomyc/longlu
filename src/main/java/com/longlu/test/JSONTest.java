package com.longlu.test;

import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  
import net.sf.json.JSON;  
import net.sf.json.JSONArray;  
import net.sf.json.JSONObject;  
import net.sf.json.JsonConfig;  
  
public class JSONTest {  
      
      
    private String name;  
    private Integer password;
      
    public JSONTest() {  
        super();  
    }  
  
    public JSONTest(String name, Integer password) {  
        super();  
        this.name = name;  
        this.password = password;
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public Integer getPassword() {  
        return password;  
    }  
  
    public void setPassword(Integer password) {  
        this.password = password;  
    }
    
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
          
          
        /** 
         * List集合内含对象转换为json代码, 
         * **/  
        JSONTest j1 = new JSONTest("admin",123456);  
        JSONTest j2 = new JSONTest("yonghu",123);  
          
        List<JSONTest> claList = new ArrayList<JSONTest>();  
        claList.add(j1);  
        claList.add(j2);  
        JSONArray jiequ = JSONArray.fromObject(claList);  
        System.out.println(jiequ);  
        //结果  [{"name":"admin","password":"123456"},{"name":"yonghu","password":"123"}]  
          
        //获得list的第二个值的json格式  
        System.out.println(jiequ.getString(1));  
        //结果  {"name":"yonghu","password":"123"}  
          
        JSONObject root = jiequ.getJSONObject(1);  
        System.out.println(root);  
        //结果  {"name":"yonghu","password":"123"}  
        System.out.println(root.get("name"));  
        //结果 yonghu  
        System.out.println(root.get("password"));  
        //结果 123  
          
          
        /** 
         *   将实体类 转换为 JSONObject 
         * **/  
        System.out.println("--------------------Object----------------------");  
        JSONObject job = JSONObject.fromObject(new JSONTest("yc",123));  
        System.out.println(job);  
        //获得name属性值  
        System.out.println(job.get("name"));  
        //结果 yc  
        //获得password属性值  
        System.out.println(job.get("password"));  
        //结果 123  
          
          
        /** 
         * JSONObject转换为实体类 
         * **/  
          
        JSONTest jt = (JSONTest) JSONObject.toBean(job,JSONTest.class);  
          
        System.out.println(jt.getName()+":"+jt.getPassword());  
          
          
        System.out.println("--------------------Map----------------------");  
          
        /** 
         * Map集合转换为json代码 
         * HashMap和Hashtable 区别 
         * 第一个不同主要是历史原因。Hashtable是基于陈旧的Dictionary类的，HashMap是Java 1.2引进的Map接口的一个实现。 
         * 最重要的不同是Hashtable的方法是同步的，而HashMap的方法不是。这就意味着，虽然你可以不用采取任何特殊的行为就可以在一个多线程的应用程序中用一个Hashtable， 
         *    但你必须同样地为一个HashMap提供外同步。 
         * 第三点 ，HashMap可以允许有空的键和值，Hashtable则不可以 
         * */  
        Map map = new HashMap();  
        map.put("0", "0");  
        map.put("1", "1");  
        map.put("2", "2");  
        map.put("3", "3");  
        map.put("4", "4");  
        map.put("5", "5");  
          
        JSONArray jmap = JSONArray.fromObject(map);  
        System.out.println(jmap);  
        //结果 [{"3":"3","null":"0","2":"2","1":"1","5":"5","4":"4"}]  
          
        JSONObject json = JSONObject.fromObject(map);  
        System.out.println(json);  
        //结果  {"3":"3","null":"0","2":"2","1":"1","5":"5","4":"4"}  
          
          
        //后台遍历json的map的值  
        Iterator it = json.entrySet().iterator();  
        while(it.hasNext()) {  
            Entry entry = (Entry)it.next();  
            System.out.println(entry.getKey()+":"+entry.getValue());  
        }  
          
          
          
          
        /** 
         * 将json对象转换为List类型  ,对于list中包含为类的 
         * */  
          
        //List<JSONTest> lj = JSONArray.toList(jiequ, JSONTest.class);  
        //等价上面的代码  
        List<JSONTest> lj = JSONArray.toList(jiequ, new JSONTest(),new JsonConfig());  
        for(JSONTest j : lj) {  
            System.out.println(j.getName()+":"+j.getPassword());  
        }  
          
        /** 
         * 将json对象转换为List类型  ,对于list中包含字符串等基本类型 
         * **/  
        List<String> lt = JSONArray.toList(jlist);  
          
        for(String s: lt) {  
            System.out.println(s);  
        }  
          
    }  
  
}  

