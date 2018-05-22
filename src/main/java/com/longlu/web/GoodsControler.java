package com.longlu.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.longlu.util.pagination.Pagination;

@RequestMapping("/goods")
@Controller
public class GoodsControler extends BaseControler{

	
	/**
	 * 获取在售商品信息
	 * @param paramsMap
	 * @param resultMap
	 * @return
	 */
	@RequestMapping("/saleGoodsInfo")
	public String saleGoodsInfo(@RequestParam HashMap<String, String> paramsMap,Pagination pagination,HashMap<String, Object> resultMap) {
//		String goodsName = paramsMap.get("goodsName");
//		resultMap.put("goodsName", goodsName);
//		pagination.setRows(30);
//		GoodsDetail[] goodsDetail = YouZanServerUtils.getSaleGoodsList(pagination,goodsName);
//		resultMap.put("goodsList", goodsDetail);
//		if(goodsDetail.length >0) {
//			pagination.setCountPage(pagination.getPage()+1);
//		}else {
//			pagination.setCountPage(pagination.getPage());
//		}
		return "goods/saleGoods";
	}
	
	
	@RequestMapping("/editGoodsInfo")
	@ResponseBody
	public Map<String, Object> editGoodsInfo(@RequestParam Map<String, Object> paramsMap,Map<String, Object> resultMap) {
		try{
			String numIid = (String)paramsMap.get("numIid");
			String skuId = (String)paramsMap.get("skuId");
			String quantity = (String)paramsMap.get("quantity");
			
			if(StringUtils.isNotBlank(skuId)) {
				//调用sku商品更新接口
				//YouZanServerUtils.YouZanKdtItemSkuUpdateParams(numIid, skuId, quantity, resultMap);
			}else {
				//调用非sku商品更新接口
				//YouZanServerUtils.YouZanKdtItemUpdate(numIid, quantity, resultMap);
			}
		}catch(Exception e) {
			resultMap.put("success", false);
			resultMap.put("msg", "更新商品信息异常,请联系管理员!");
		}
		return resultMap;
	}
}
