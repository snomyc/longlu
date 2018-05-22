package com.longlu.pojo;

/**     
* 类描述：   交易明细数据结构
* 创建人：yagncan   
* 创建时间：2016年12月15日 下午10:18:20     
*/
public class TradeOrder {

	private Long id; //唯一主键
	private String tid; //交易编号
	private String outer_item_id; //商品货号（商家为商品设置的外部编号）
	private String pic_thumb_path; //商品主图片缩略图地址
	private Integer num; //商品购买数量
	private Double refunded_fee; //退款金额
	private String num_iid; //废弃2017年8月19日16:32:19 //商品数字编号  
	private String oid; //交易明细编号。该编号并不唯一，只用于区分交易内的多条明细记录
	private String title; //商品标题
	private Double fenxiao_payment; //商品在分销商那边的实付金额。精确到2位小数；单位：元。如果是采购单才有值，否则值为 0
	private Double discount_fee; //交易明细内的优惠金额。精确到2位小数，单位：元
	private Double price; //商品价格。精确到2位小数；单位：元
	private Double fenxiao_price; //商品在分销商那边的出售价格。精确到2位小数；单位：元。如果是采购单才有值，否则值为 0
	private Double total_fee; //应付金额（商品价格乘以数量的总金额）
	private Double payment; //实付金额。精确到2位小数，单位：元
	private String outer_sku_id; //商家编码（商家为Sku设置的外部编号）XXX-商品编码 xxx是供应商名称
	private String sku_unique_code; //Sku在系统中的唯一编号，可以在开发者的系统中用作 Sku 的唯一ID，但不能用于调用接口
	private String sku_id; //Sku的ID，sku_id 在系统里并不是唯一的，结合商品ID一起使用才是唯一的。
	private String sku_properties_name; //SKU的值，即：商品的规格。如：机身颜色:黑色;手机套餐:官方标配
	private String pic_path; //商品主图片地址
	private String item_refund_state; //商品退款状态* NO_REFUND(无退款),PARTIAL_REFUND(部分退款),FULL_REFUND(全额退款) 只有当订单是部分退款, 才会生成item_refund_state
	private String state_str; //商品状态(已发货,待发货,退款中(买家申请退款),退款关闭,退款成功)
	private Integer allow_send; //是否允许发货 1 可以发货 0 不能发货
	private String seller_nick; //卖家昵称
	
	//新增字段
	private String supplierName; //供应商名称，供应商可以有多个仓库,结算使用供应商结算,供应商获取是从outer_sku_id中获取
	private String goodsNumber; //商品编号，outer_sku_id中获取
	private String  expressCompany;//快递公司
	private String expressNumber;//快递单号
	private Integer orderReview; //订单审核 默认0 ,0 :财务未审核 1：财务审核通过,2:仓库发货成功，-1：财务审核不通过 ,-2：快递发货不通过 3:有赞订单管理者录入快递信息成功,-3 取消录入
	private Integer refundReview; //退款审核 默认:0未审核，1成功
	private String payTime;       //订单支付时间
	private String payAddress;    //订单地址
	private String bz; //备注，审核不通过备注，快递取消等备注
	private Integer emailStatus; //邮件发送状态 默认0未发送， 1已发送
	private String cost;//订单成本

	/*非表字段*/
	private String alibabaUrl; //阿里巴巴商品url地址
	private String alicost; //阿里巴巴商品成本,如果订单成本为空可以用该字段
	private String aliSupplierName; //阿里巴巴供应商
	private String aliBz; //阿里巴巴商品备注
	
	
	//收货人信息
	private String receiver_name; //收货人的姓名
	private String receiver_mobile; //收货人的手机号码
	private String receiver_address; //收货人地址
	
	private String shopName; //店铺名称 用于区分是哪个店铺的订单
	private String shopId; //店铺配置ID (自定义)
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getOuter_item_id() {
		return outer_item_id;
	}
	public void setOuter_item_id(String outer_item_id) {
		this.outer_item_id = outer_item_id;
	}
	public String getPic_thumb_path() {
		return pic_thumb_path;
	}
	public void setPic_thumb_path(String pic_thumb_path) {
		this.pic_thumb_path = pic_thumb_path;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getRefunded_fee() {
		return refunded_fee;
	}
	public void setRefunded_fee(Double refunded_fee) {
		this.refunded_fee = refunded_fee;
	}
	public String getNum_iid() {
		return num_iid;
	}
	public void setNum_iid(String num_iid) {
		this.num_iid = num_iid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getFenxiao_payment() {
		return fenxiao_payment;
	}
	public void setFenxiao_payment(Double fenxiao_payment) {
		this.fenxiao_payment = fenxiao_payment;
	}
	public Double getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(Double discount_fee) {
		this.discount_fee = discount_fee;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getFenxiao_price() {
		return fenxiao_price;
	}
	public void setFenxiao_price(Double fenxiao_price) {
		this.fenxiao_price = fenxiao_price;
	}
	public Double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
	public Double getPayment() {
		return payment;
	}
	public void setPayment(Double payment) {
		this.payment = payment;
	}
	public String getOuter_sku_id() {
		return outer_sku_id;
	}
	public void setOuter_sku_id(String outer_sku_id) {
		this.outer_sku_id = outer_sku_id;
	}
	public String getSku_unique_code() {
		return sku_unique_code;
	}
	public void setSku_unique_code(String sku_unique_code) {
		this.sku_unique_code = sku_unique_code;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku_properties_name() {
		return sku_properties_name;
	}
	public void setSku_properties_name(String sku_properties_name) {
		this.sku_properties_name = sku_properties_name;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}
	public String getItem_refund_state() {
		return item_refund_state;
	}
	public void setItem_refund_state(String item_refund_state) {
		this.item_refund_state = item_refund_state;
	}
	public String getState_str() {
		return state_str;
	}
	public void setState_str(String state_str) {
		this.state_str = state_str;
	}
	public Integer getAllow_send() {
		return allow_send;
	}
	public void setAllow_send(Integer allow_send) {
		this.allow_send = allow_send;
	}
	public String getSeller_nick() {
		return seller_nick;
	}
	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	public Integer getOrderReview() {
		return orderReview;
	}
	public void setOrderReview(Integer orderReview) {
		this.orderReview = orderReview;
	}
	public Integer getRefundReview() {
		return refundReview;
	}
	public void setRefundReview(Integer refundReview) {
		this.refundReview = refundReview;
	}
	public String getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getPayAddress() {
		return payAddress;
	}
	public void setPayAddress(String payAddress) {
		this.payAddress = payAddress;
	}
	public String getAlibabaUrl() {
		return alibabaUrl;
	}
	public void setAlibabaUrl(String alibabaUrl) {
		this.alibabaUrl = alibabaUrl;
	}
	public Integer getEmailStatus() {
		return emailStatus;
	}
	public void setEmailStatus(Integer emailStatus) {
		this.emailStatus = emailStatus;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getAlicost() {
		return alicost;
	}
	public void setAlicost(String alicost) {
		this.alicost = alicost;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getReceiver_mobile() {
		return receiver_mobile;
	}
	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}
	public String getReceiver_address() {
		return receiver_address;
	}
	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}
	public String getAliSupplierName() {
		return aliSupplierName;
	}
	public void setAliSupplierName(String aliSupplierName) {
		this.aliSupplierName = aliSupplierName;
	}
	
	public String getAliBz() {
		return aliBz;
	}
	public void setAliBz(String aliBz) {
		this.aliBz = aliBz;
	}
	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
}
