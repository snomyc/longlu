package com.longlu.web;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.longlu.kdt.api.KdtApiItem;
import com.longlu.pojo.TradeDetail;
import com.longlu.service.KdtApiService;

@RequestMapping("/hello")
@Controller
public class HelloControler{
	
	@Autowired
	private KdtApiService kdtApiService;
	
	/** 
     * 跳转到myjsp页面 
     *  
     * @return 
     */  
    @RequestMapping("/login")
    public String login(String per) {
        return "main"; 
    }
	
	@RequestMapping("/index")
    public String index(){
		
        Subject currentUser = SecurityUtils.getSubject();  
        UsernamePasswordToken token = new UsernamePasswordToken(  
                "admin", "123456");  
        token.setRememberMe(true);  
        try {  
            currentUser.login(token);  
        } catch (AuthenticationException e) {  
            e.printStackTrace();  
        }  
        if(currentUser.isAuthenticated()){  
              
            System.out.println("身份认证成功");
        }else{  
        	System.out.println("身份认证失败"); 
        }  
        return "main";
    }
    
    /** 
     * 跳转到myjsp页面 
     *  
     * @return 
     */  
    @RequestMapping("/main")
    public String main(String per) {  
        Subject currentUser = SecurityUtils.getSubject();  
        if(currentUser.hasRole("admin")){  
            return "main";  
        }else{  
            return "error";  
        }  
    }
    
    /**
     * @return
     * 使用shiro注解
     * RequiresAuthentication注解
     * RequiresAuthentication注解要求在访问或调用被注解的类/实例/方法时，Subject在当前的session中已经被验证。
     * RequiresGuest注解
     * RequiresGuest注解要求当前Subject是一个“访客”，也就是，在访问或调用被注解的类/实例/方法时，他们没有被认证或者在被前一个Session记住。
     * RequiresPermissions 注解
     * RequiresPermissions 注解要求当前Subject在执行被注解的方法时具备一个或多个对应的权限。
     * RequiresRoles 注解
     * RequiresPermissions 注解要求当前Subject在执行被注解的方法时具备所有的角色，否则将抛出AuthorizationException异常。
     * http://blog.csdn.net/clj198606061111/article/details/24185023
     * @RequiresAuthentication
     * @RequiresRoles("administrator")
     *@RequiresPermissions({"home.do", "main.do1"} )
     *
     *@RequiresAuthentication @RequiresPermissions({"home.do"} ) 同时使用表示需要身份认证和授权才能访问
     */
    @RequiresPermissions({"home"})
    @RequestMapping("/guest")
    public String guest() {
    	String s = null;
    	System.out.println(s.toString());
    	System.out.println("123123123");
    	return "main";
    }
    
    
    @RequiresPermissions({"home1"})
    @RequestMapping("/guest2")
    public String guest2() {
    	System.out.println("aaaaaa");
    	return "main";
    }
    
    @RequestMapping("/logout")
    public String logout() {
    	Subject currentUser = SecurityUtils.getSubject();
    	currentUser.logout();
    	return "login";
    }
    
    @RequestMapping("/home")
    public String welcome() {
    	Subject currentUser = SecurityUtils.getSubject();
    	currentUser.logout();
    	return "home";
    }
}
