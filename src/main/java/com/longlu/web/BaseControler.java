package com.longlu.web;

import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class BaseControler {

	
	protected String[] getUserSupplierName() {
		Session session = SecurityUtils.getSubject().getSession();
		Map<String,Object> userMap = (Map<String, Object>) session.getAttribute("userMap");
		String supplierNames = userMap.get("f0").toString();
		return supplierNames.split(",");
	}
}
