//package com.longlu.alibaba;
//
//import net.sf.json.JSONObject;
//
//import com.alibaba.commissionSale.microsupply.param.AlibabaChinaMicrosupplyOpenCalculateFreightFeeParam;
//import com.alibaba.commissionSale.microsupply.param.AlibabaChinaMicrosupplyOpenCalculateFreightFeeResult;
//import com.alibaba.commissionSale.microsupply.param.AlibabaChinaMicrosupplyOpenMicroSupplyFreightFeeInputModel;
//import com.alibaba.commissionSale.microsupply.param.AlibabaChinaMicrosupplyShopOrderParam;
//import com.alibaba.commissionSale.microsupply.param.AlibabaChinaMicrosupplyShopOrderResult;
//import com.alibaba.ocean.rawsdk.ApiExecutor;
//import com.alibaba.ocean.rawsdk.client.entity.AuthorizationToken;
//import com.alibaba.product.param.AlibabaProductMicroSupplyGetParam;
//import com.alibaba.product.param.AlibabaProductMicroSupplyGetResult;
//import com.alibaba.product.param.AlibabaProductProductInfo;
//import com.alibaba.product.param.AlibabaProductProductPriceRange;
//import com.alibaba.product.param.AlibabaProductProductSKUInfo;
//import com.alibaba.product.param.AlibabaProductProductShippingInfo;
//import com.alibaba.product.param.AlibabaProductSKUAttrInfo;
//import com.longlu.util.HttpClientHelper;
//
///**
// * 阿里巴巴第三方测试
// * @author Administrator
// *
// */
//public class AliApiTest {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		
////		在调用API的过程中，您首先需要确定接口对应的类名称： 
////		在SDK中对应的类为：每个单词首字母大写，并去掉分隔符（“.”），末尾加上Param（或Result）; 例如接口名：alibaba.product.add对应的类为：
////		AlibabaProductAddParam（请求类）
////		AlibabaProductAddResult（响应类）
//		
//		
//		//web授权方式
//		//https://open.1688.com/api/sys_auth.htm?spm=a260s.8208024.0.0.qUuUby&ns=cn.alibaba.open#authClassification
//		
//		
////		String jsonStr = "{\"access_token\":\"56cd6399-243c-4a9f-b3de-b330317c76d0\"}";
////		JSONObject json = JSONObject.fromObject(jsonStr);
////		System.out.println(json.get("access_token"));
//		
//		
//		ApiExecutor apiExecutor = new ApiExecutor("6695409", "8p9sSZgvj5Q");
//		//alibaba.product.microSupply.get
//		//获取微供商品
//		AlibabaProductMicroSupplyGetParam param = new AlibabaProductMicroSupplyGetParam();
//		param.setProductId(556639956929L);
//		String token = "615313e6-3906-4274-aa9c-5ef51ac7ab6b";
//		AlibabaProductMicroSupplyGetResult result = apiExecutor.execute(param, token);
//		
//		System.out.println("code:"+result.getErrorCode()+",message:"+result.getErrorMessage()+",success:"+result.getSuccess());
//		
//		AlibabaProductProductInfo product = result.getProduct();
//		System.out.println("----------PRODUCT--------------");
//		System.out.println("商品ID:"+product.getProductID());
//		System.out.println("商品标题:"+product.getSubject());
//		System.out.println("商品详细:"+product.getDescription());
//		System.out.println("商品主图:"+product.getImage());
//		System.out.println("供应商ID:"+product.getSupplierUserId());
//		System.out.println("----------PRODUCT--------------");
//		//商品SKU信息
//		System.out.println("-----------SKU-------------");
//		for (AlibabaProductProductSKUInfo sku : product.getSkuInfos()) {
//			System.out.println("specId:"+sku.getSpecId());
//			System.out.println("skuId:"+sku.getSkuId());
//			System.out.println("skuCode:"+sku.getSkuCode());
//			System.out.println("price:"+sku.getPrice());
//			System.out.println("可销售数量amountOnSale:"+sku.getAmountOnSale());
//			for (AlibabaProductSKUAttrInfo skuInfo : sku.getAttributes()) {
//				System.out.println(skuInfo.getAttributeDisplayName()+":"+skuInfo.getAttributeValue());
//			}
//		}
//		System.out.println("-----------SKU-------------");
//		System.out.println("-----------商品物流信息-------------");
//		AlibabaProductProductShippingInfo ship = product.getShippingInfo();
//		//运费模板ID
//		System.out.println("运费模板ID:"+ship.getFreightTemplateID());
//		System.out.println("尺寸:"+ship.getPackageSize());
//		System.out.println("单位重量:"+ship.getUnitWeight());
//		System.out.println("-----------商品物流信息-------------");
//		
//		System.out.println("-----------商品销售信息-------------");
//		System.out.println("-----------商品销售信息-------------");
//		
//		//微供开放平台运费计算接口
//		AlibabaChinaMicrosupplyOpenCalculateFreightFeeParam fee = new AlibabaChinaMicrosupplyOpenCalculateFreightFeeParam();
//		
//		//城市编码  深圳 行政区划代码440300  邮政编码:518000
//		fee.setCityCode(440300L);
//		//区域编码  宝安行政区划代码440306 邮政编码:518101
//		fee.setDistrictCode(440306L);
//		AlibabaChinaMicrosupplyOpenMicroSupplyFreightFeeInputModel model = new AlibabaChinaMicrosupplyOpenMicroSupplyFreightFeeInputModel();
//		//运费模板id
//		model.setFreightId(1L);
//		//sku
//		model.setSkuId(3611495050998L);
//		model.setSpecId("ff1d933eb41d9e32beaf1b0064537a3d");
//		//商品单价
//		model.setUnitPrice(65.0);
//		//购买商品数量	
//		model.setQuantity(1L);
//		//offerId 商品ID
//		model.setOfferId(556639956929L);
//		//是否包邮
//		model.setIsfreefreight(1);
//		//outId
//		//model.setOutId("1");
//		
//		AlibabaChinaMicrosupplyOpenMicroSupplyFreightFeeInputModel[] models = new AlibabaChinaMicrosupplyOpenMicroSupplyFreightFeeInputModel[1];
//		models[0] = model;
//		fee.setOrderInputModel(models);
//		//fee
//		
//		AlibabaChinaMicrosupplyOpenCalculateFreightFeeResult feeResult = apiExecutor.execute(fee, token);
//		
//		
//		System.out.println("运费(分):"+feeResult.getModel().getTotalFee());
//		System.out.println("运费(元):"+feeResult.getModel().getTotalFreightFee());
//		System.out.println("code:"+feeResult.getMsgCode()+",message:"+feeResult.getMsgInfo()+",success:"+feeResult.getSuccess());
////		//调用下单接口
////		AlibabaChinaMicrosupplyShopOrderParam order = new AlibabaChinaMicrosupplyShopOrderParam();
////		
////		AlibabaChinaMicrosupplyShopOrderResult orderResult = apiExecutor.execute(order, token);
//		
//	}
//}
