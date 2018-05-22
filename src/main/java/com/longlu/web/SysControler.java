package com.longlu.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.product.param.AlibabaProductMicroSupplyGetParam;
import com.alibaba.product.param.AlibabaProductMicroSupplyGetResult;
import com.longlu.pojo.Users;
import com.longlu.pojo.YouzanShopConfig;
import com.longlu.service.TradeDetailService;
import com.longlu.service.TradeOrderService;
import com.longlu.service.YouzanShopConfigService;
import com.longlu.timetask.TradeDetailTimeTask;
import com.longlu.util.HttpClientHelper;
import com.longlu.util.MD5Utils;
import com.longlu.youzan.YouZanServerUtils;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanShopGetResult;

@RequestMapping("/")
@Controller
public class SysControler {
	private static Logger logger = LoggerFactory.getLogger(SysControler.class);

	@Autowired
	private TradeOrderService tradeOrderService;
	
	@Autowired
	private TradeDetailService tradeDetailService;
	
	@Autowired
	private YouzanShopConfigService youzanShopConfigService;
	
	/** 
     * 跳转到内购平台主页面
     *  登录验证页面
     * @return 
     */  
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam HashMap<String, String> paramsMap,HashMap<String, Object> resultMap) {

        try {
        	if(StringUtils.isBlank(paramsMap.get("username")) ||  StringUtils.isBlank(paramsMap.get("password"))) {
        		resultMap.put("msg", "用户名或密码为空!");
    			resultMap.put("success", false);
    			return resultMap;
        	}
        	Users users = new Users();
        	users.setUsername(paramsMap.get("username"));
        	users.setPassword(paramsMap.get("password"));
	    	Subject subject = SecurityUtils.getSubject();
	    	UsernamePasswordToken token = new UsernamePasswordToken(users.getUsername(), MD5Utils.MD5(users.getPassword()));  
	        token.setRememberMe(true);
	        //开始验证，调用login方法时将会自动调用MyRealm中的权限认证类中方法
			subject.login(token);
			resultMap.put("msg", "用户登录成功");
			resultMap.put("success", true);
			return resultMap;
		} catch (UnknownAccountException uae ) {//没有该用户，或者用户名与其他员工工号发生冲突也会抛此异常
			resultMap.put("msg", "用户不存在!");
		} catch ( IncorrectCredentialsException ice ) {//错误的凭证
			resultMap.put("msg", "密码不正确!");
		} catch (LockedAccountException lae ) {//账户被锁定
			resultMap.put("msg", "账号被锁定!");
		} catch (ExcessiveAttemptsException eae ) {//登录失败次数过多
			resultMap.put("msg", "登录次数过多!");
		} catch (Exception e ) {
			resultMap.put("msg", "未知错误!");
		}
        resultMap.put("success", false);
        return resultMap; 
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
//    @RequiresAuthentication
//    @RequiresRoles({"财务"})
    @RequestMapping("/main")
    public String main() {
        return "main"; 
    }
    
	@RequestMapping("/home")
	public String home(HashMap<String, Object> resultMap) {
		//根据用户查询用户的待办信息
		Session session = SecurityUtils.getSubject().getSession();
		Map<String,Object> userMap = (Map<String, Object>) session.getAttribute("userMap");
		//获取用户的角色
		String rolename = userMap.get("rolename").toString();
		//获取用户的供应商信息
		String supplierName = userMap.get("f0").toString();
		Map<String, Object> paramsMap = new HashMap<String,Object>();
		if(rolename.equals("财务")) {
			paramsMap.put("orderReview", 0);
			paramsMap.put("supplierNames", supplierName.split(","));
			Long count = tradeOrderService.selectTradeOrderBySelectiveCount(paramsMap);
			resultMap.put("financeCount", count);
		}else if(rolename.equals("仓库")) {
			paramsMap.put("orderReview", 1);
			paramsMap.put("supplierNames", supplierName.split(","));
			Long count = tradeOrderService.selectTradeOrderBySelectiveCount(paramsMap);
			resultMap.put("depotCount", count);
		}else if(rolename.equals("财务-仓库")) {
			paramsMap.put("orderReview", 0);
			paramsMap.put("supplierNames", supplierName.split(","));
			Long count = tradeOrderService.selectTradeOrderBySelectiveCount(paramsMap);
			resultMap.put("financeCount", count);
			paramsMap.put("orderReview", 1);
			count = tradeOrderService.selectTradeOrderBySelectiveCount(paramsMap);
			resultMap.put("depotCount", count);
		} else if(rolename.equals("有赞")) {
			paramsMap.put("orderReview", 2);
			Long count = tradeOrderService.selectTradeOrderBySelectiveCount(paramsMap);
			resultMap.put("marketCount", count);
		}
	  	return "home";
	}
	
	
    @RequestMapping("/logout")
    public String logout() {
    	Subject currentUser = SecurityUtils.getSubject();
    	currentUser.logout();
    	return "redirect:index.html";
    }
    
    
    /**
     * http://localhost:8080/innerbuy/tradesSoldGet.do?start_update=2017-8-11%2000:00:00&end_update=2017-8-12%2000:00:00&shop_id=1
     * @param paramsMap
     * @param resultMap
     * @return
     * @author yangcan
     */
    @RequestMapping("/tradesSoldGet")
    @ResponseBody
    public Map<String, Object> tradesSoldGet(@RequestParam Map<String, String> paramsMap,Map<String, Object> resultMap) {
    	try{
    		tradeDetailService.getTradeSuccessOrdersToYouZan(paramsMap.get("start_update"),paramsMap.get("end_update"),paramsMap.get("shop_id"));
    		resultMap.put("success", true);
    		resultMap.put("msg", "获取有赞交易成功订单成功!");
    		return resultMap;
    	}catch (Exception e) {
    		resultMap.put("success", false);
    		resultMap.put("msg", "获取有赞交易成功订单失败!"+e.getMessage());
    		return resultMap;
		}
    }
    
    
//    @RequestMapping("/alibaba_auth")
//    @ResponseBody
//    public Map<String, Object> alibabaAuth(@RequestParam Map<String, String> paramsMap,Map<String, Object> resultMap) {
//    	String code = paramsMap.get("code");
//    	
//    	if(StringUtils.isNotBlank(code)) {
//    		//https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/YOUR_APPKEY?grant_type=authorization_code&need_refresh_token=true&client_id= YOUR_APPKEY&client_secret= YOUR_APPSECRET&redirect_uri=YOUR_REDIRECT_URI&code=CODE
//    		String url = "https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/6695409";
//    		
//    		Map<String, Object> parameters = new HashMap<String,Object>();
//    		parameters.put("grant_type", "authorization_code");
//    		parameters.put("need_refresh_token", true);
//    		parameters.put("client_id", "6695409");
//    		parameters.put("client_secret", "8p9sSZgvj5Q");
//    		parameters.put("redirect_uri", "http://localhost:8080/innerbuy/alibaba_auth.do");
//    		parameters.put("code", code);
//    		try {
//				String jsonResult = HttpClientHelper.httpPost(url, parameters);
//				System.out.println(jsonResult);
//				
//				JSONObject json = JSONObject.fromObject(jsonResult);
//				String token = json.get("access_token").toString();
//				System.out.println("token="+token);
//				ApiExecutor apiExecutor = new ApiExecutor("6695409", "8p9sSZgvj5Q");
//				
//				AlibabaProductMicroSupplyGetParam param = new AlibabaProductMicroSupplyGetParam();
//				param.setProductId(11111111111L);
//				
//				AlibabaProductMicroSupplyGetResult result = apiExecutor.execute(param, token);
//				System.out.println(result.getErrorCode());
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			} 
//    	}
//    	
//    	System.out.println("code="+code);
//    	resultMap.put("code", paramsMap.get("code"));
//    	return resultMap;
//    }
    
    
    /**
     * 有赞授权
     * @param paramsMap
     * @param resultMap
     * @return
     */
    @RequestMapping("/youzan_auth")
    public String youzanAuth(@RequestParam Map<String, String> paramsMap,Map<String, Object> resultMap) {
    	
    	//获取商家授权的临时授权码
    	String code = paramsMap.get("code");
    	String state = paramsMap.get("state");
    	
    	if(StringUtils.isNotBlank(code) && state.equals("teststate")) {
    		try {
				String jsonResult = YouZanServerUtils.youzanAuthToken(code);
				
				JSONObject json = JSONObject.fromObject(jsonResult);
				//过期时间7天
				String token = json.get("access_token").toString();
				
				//用于刷新令牌 access_token 的 refresh_token 过期时间28天
				String refresh_token = json.get("refresh_token").toString();
				logger.error("token="+token);
				logger.error("refresh_token="+refresh_token);
				
				//通过token获取有赞店铺名称
				YouzanShopGetResult result = YouZanServerUtils.youzanShopGet(token);
				
				//查询店铺名称是否存在，如果存在的话那么就不新增
				YouzanShopConfig record = youzanShopConfigService.selectByShopName(result.getName());
				if(record != null) {
					//获取店铺ID
					record.setKdtId(result.getId().toString());
					//token,refresh_token入库
					record.setToken(token);
					record.setRefreshToken(refresh_token);
					//授权店铺入库成功
					youzanShopConfigService.saveOrUpdate(record);
				}else {
					record = new YouzanShopConfig();
					//获取店铺名称
					record.setShopName(result.getName());
					//获取店铺ID
					record.setKdtId(result.getId().toString());
					//token,refresh_token入库
					record.setToken(token);
					record.setRefreshToken(refresh_token);
					
					//授权店铺入库成功
					youzanShopConfigService.saveOrUpdate(record);
				}
			} catch (Exception e) {
				return "redirect:error.html";
			} 
    	}
    	
    	return "redirect:youzanAuthSuccess.html";
    }
    
//    @RequestMapping("/youzan_callback")
//    @ResponseBody
//    public  Map<String, Object> youzanCallback(@RequestParam Map<String, String> paramsMap,Map<String, Object> resultMap) {
//    	String code = paramsMap.get("code");
//    	
//    	if(StringUtils.isNotBlank(code)) {
//    		//https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/YOUR_APPKEY?grant_type=authorization_code&need_refresh_token=true&client_id= YOUR_APPKEY&client_secret= YOUR_APPSECRET&redirect_uri=YOUR_REDIRECT_URI&code=CODE
//    		String url = "https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/6695409";
//    		
//    		Map<String, Object> parameters = new HashMap<String,Object>();
//    		parameters.put("grant_type", "authorization_code");
//    		parameters.put("need_refresh_token", true);
//    		parameters.put("client_id", "6695409");
//    		parameters.put("client_secret", "8p9sSZgvj5Q");
//    		parameters.put("redirect_uri", "http://localhost:8080/innerbuy/alibaba_auth.do");
//    		parameters.put("code", code);
//    		try {
//				String jsonResult = HttpClientHelper.httpPost(url, parameters);
//				System.out.println(jsonResult);
//				
//				JSONObject json = JSONObject.fromObject(jsonResult);
//				String token = json.get("access_token").toString();
//				System.out.println("token="+token);
//				ApiExecutor apiExecutor = new ApiExecutor("6695409", "8p9sSZgvj5Q");
//				
//				AlibabaProductMicroSupplyGetParam param = new AlibabaProductMicroSupplyGetParam();
//				param.setProductId(11111111111L);
//				
//				AlibabaProductMicroSupplyGetResult result = apiExecutor.execute(param, token);
//				System.out.println(result.getErrorCode());
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			} 
//    	}
//    	
//    	System.out.println("code="+code);
//    	resultMap.put("code", paramsMap.get("code"));
//    	return resultMap;
//    }
    
    
//    @RequestMapping("/test")
//    @ResponseBody
//    public  Map<String, Object> test(@RequestParam Map<String, String> paramsMap,Map<String, Object> resultMap) {
//	    //查询店铺名称是否存在，如果存在的话那么就不新增
//		YouzanShopConfig record = youzanShopConfigService.selectByShopName(paramsMap.get("name").toString());
//		String token ="test";
//		String refresh_token = "test";
//		String kdtId = "tset";
//		if(record != null) {
//			//获取店铺ID
//			record.setKdtId(kdtId);
//			//token,refresh_token入库
//			record.setToken(token);
//			record.setRefreshToken(refresh_token);
//			//授权店铺入库成功
//			youzanShopConfigService.saveOrUpdate(record);
//		}else {
//			record = new YouzanShopConfig();
//			//获取店铺名称
//			record.setShopName(paramsMap.get("name"));
//			//获取店铺ID
//			record.setKdtId(kdtId);
//			//token,refresh_token入库
//			record.setToken(token);
//			record.setRefreshToken(refresh_token);
//			
//			//授权店铺入库成功
//			youzanShopConfigService.saveOrUpdate(record);
//		}
//		return resultMap;
//    }
    
    
}
