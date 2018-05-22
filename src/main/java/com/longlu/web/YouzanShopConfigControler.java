package com.longlu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.longlu.pojo.YouzanShopConfig;
import com.longlu.service.YouzanShopConfigService;
import com.longlu.util.pagination.Pagination;

/**
 * @author Administrator
 * 有赞配置
 */
@RequestMapping("/config")
@Controller
public class YouzanShopConfigControler {

	@Autowired
	private YouzanShopConfigService youzanShopConfigService;
	
	@RequestMapping("/list")
	public String aliOrderCodeList(@RequestParam HashMap<String, Object> paramsMap,HashMap<String, Object> resultMap) {
		resultMap.put("shopName", paramsMap.get("shopName"));
		List<Map<String,Object>> aliList = youzanShopConfigService.selectBySelective(resultMap);
		resultMap.put("configList", aliList);
		resultMap.put("total", aliList.size());
		return "config/list";
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public Map<String, Object> create(YouzanShopConfig youzanShopConfig,Map<String, Object> resultMap) throws Exception {
		if(!youzanShopConfigService.saveOrUpdate(youzanShopConfig)) {
			resultMap.put("success", false);
			resultMap.put("msg", "配置已存在,新增失败!");
		}else {
			resultMap.put("success", true);
			resultMap.put("msg", "新增成功!");
		}
		return resultMap;
	}
	
	@RequestMapping("/createFrom")
	public String createFrom(@RequestParam HashMap<String, String> paramsMap,HashMap<String, Object> resultMap) {
		if(StringUtils.isNotBlank(paramsMap.get("id"))) {
			//根据用户id查询用户信息
			YouzanShopConfig record = youzanShopConfigService.selectByPrimaryKey(Long.valueOf(paramsMap.get("id")));
			resultMap.put("record", record);
		}
		return "config/create";
	}
	
	@RequestMapping("/delete")
	public String delete(YouzanShopConfig record) throws Exception {
		if(youzanShopConfigService.deleteByPrimaryKey(record.getId()) == 0) {
			throw new Exception("删除配置信息失败!");
		}
		return "redirect:/config/list.do";
	}
}
