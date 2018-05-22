package com.longlu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longlu.pojo.Role;
import com.longlu.pojo.Users;
import com.longlu.service.RoleService;
import com.longlu.service.TradeOrderService;
import com.longlu.service.UsersService;
import com.longlu.util.MD5Utils;
import com.longlu.util.pagination.Pagination;

@RequestMapping("/users")
@Controller
public class UsersControler {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private TradeOrderService tradeOrderService;
	
	@RequestMapping("/list")
	public String usersList(@RequestParam HashMap<String, String> paramsMap, Pagination pagination,HashMap<String, Object> resultMap) {
		List<Map<String,Object>> usersList = usersService.selectUsersBySelective(null);
		resultMap.put("usersList", usersList);
		resultMap.put("total", usersList.size());
		return "sys/usersManager";
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public void userCreate(Users users,HashMap<String, Object> resultMap) throws Exception {
		if(!usersService.saveOrUpdateUsers(users)) {
			throw new Exception("新增用户失败");
		}
	}
	
	@RequestMapping("/createFrom")
	public String userCreateFrom(@RequestParam HashMap<String, String> paramsMap,HashMap<String, Object> resultMap) {
		if(StringUtils.isNotBlank(paramsMap.get("id"))) {
			//根据用户id查询用户信息
			Users users = usersService.selectUsersById(Long.valueOf(paramsMap.get("id")));
			resultMap.put("users", users);
		}
		//准备角色列表
		List<Role> roleList = roleService.selectRoleBySelective(null);
		
		//准备供应商列表
		List<Map<String, Object>> supplierList = tradeOrderService.getAllOrderSupplierName();
		resultMap.put("roleList", roleList);
		resultMap.put("supplierList", supplierList);
		return "sys/createUsers";
	}
	
	@RequestMapping("/revisePassword")
	public String revisePassword(HashMap<String, Object> resultMap) {
		//获取用户信息
		return "sys/revisePassword";
	}
	
	@RequestMapping("/updatePassword")
	@ResponseBody
	public void updatePassword(String password) {
		if(StringUtils.isNotBlank(password)) {
			Session session = SecurityUtils.getSubject().getSession();
			Map<String,Object> userMap = (Map<String, Object>) session.getAttribute("userMap");
			Users user = new Users();
			user.setId(Long.valueOf(userMap.get("id").toString()));
			user.setPassword(MD5Utils.MD5(password));
			usersService.updateByPrimaryKeySelective(user);
		}
	}
	
	@RequestMapping("/delete")
	public String deleteUsers(Users users) throws Exception {
		if(!usersService.deleteUsers(users)) {
			throw new Exception("删除用户失败!");
		}
		return "redirect:/users/list.do";
	}
	
	@RequestMapping("/reset")
	public String reset(Users users) throws Exception {
		users.setPassword(MD5Utils.MD5("123456"));
		usersService.updateByPrimaryKeySelective(users);
		return "redirect:/users/list.do";
	}
}
