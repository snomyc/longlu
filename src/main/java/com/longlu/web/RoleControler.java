package com.longlu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longlu.pojo.Role;
import com.longlu.service.RoleService;
import com.longlu.util.pagination.Pagination;

@RequestMapping("/role")
@Controller
public class RoleControler {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/list")
	public String roleList(@RequestParam HashMap<String, String> paramsMap, Pagination pagination,HashMap<String, Object> resultMap) {
		List<Role> roleList = roleService.selectRoleBySelective(null);
		resultMap.put("roleList", roleList);
		resultMap.put("total", roleList.size());
		return "sys/roleManager";
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public void roleCreate(Role role,HashMap<String, Object> resultMap) throws Exception {
		if(!roleService.saveOrUpdateRole(role)) {
			throw new Exception("新增角色失败");
		}
	}
	
	@RequestMapping("/createFrom")
	public String userCreateFrom(@RequestParam HashMap<String, String> paramsMap,HashMap<String, Object> resultMap) {
		if(StringUtils.isNotBlank(paramsMap.get("id"))) {
			//根据用户id查询用户信息
			Role role = roleService.selectRoleById(Long.valueOf(paramsMap.get("id")));
			resultMap.put("role", role);
		}
		return "sys/createRole";
	}
	
	@RequestMapping("/find")
	public String findRoleByparams() {
		return "";
	}
	
	@RequestMapping("/delete")
	public String deleteRole(Role role) throws Exception {
		if(!roleService.deleteRole(role)) {
			throw new Exception("删除角色失败!");
		}
		return "redirect:/role/list.do";
	}
	
}
