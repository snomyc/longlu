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

import com.longlu.domain.Express;
import com.longlu.kdt.api.KdtApiUtils;
import com.longlu.pojo.ExpressManager;
import com.longlu.service.ExpressManagerService;
import com.longlu.util.pagination.Pagination;

@RequestMapping("/express")
@Controller
public class ExpressManagerControler {

	@Autowired
	private ExpressManagerService expressManagerService;
	
	@RequestMapping("/list")
	public String list(@RequestParam HashMap<String, Object> paramsMap, Pagination pagination,HashMap<String, Object> resultMap) {
		List<Map<String,Object>> expressList = expressManagerService.selectBySelective(paramsMap);
		resultMap.put("expressList", expressList);
		resultMap.put("total", expressList.size());
		return "express/list";
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public Map<String, Object> create(ExpressManager expressManager,Map<String, Object> resultMap) throws Exception {
		if(!expressManagerService.saveOrUpdate(expressManager)) {
			resultMap.put("success", false);
			resultMap.put("msg", expressManager.getCompany()+"已存在,新增失败!");
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
			ExpressManager record = expressManagerService.selectByPrimaryKey(Long.valueOf(paramsMap.get("id")));
			resultMap.put("record", record);
		}
		return "express/create";
	}
	
	@RequestMapping("/delete")
	public String delete(ExpressManager record) throws Exception {
		if(expressManagerService.deleteByPrimaryKey(record.getId()) == 0) {
			throw new Exception("删除商品URL失败!");
		}
		return "redirect:/express/list.do";
	}
	
	@RequestMapping("/syncExpress")
	public String syncYouZanExpress() {
		List<Express> expressList = KdtApiUtils.expressGet();
		for (Express express : expressList) {
			ExpressManager expressManager = new ExpressManager();
			expressManager.setNum(String.valueOf(express.getId()));
			expressManager.setCompany(express.getName());
			expressManagerService.insertSelective(expressManager);
		}
		return "redirect:/express/list.do";
	}
	
}
