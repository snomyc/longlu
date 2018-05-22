package com.longlu.pojo;

import java.util.List;

/**     
* 类描述：   有赞交易数据结构bean
* 创建人：yagncan   
* 创建时间：2016年12月14日 下午11:09:45     
*/
public class TradeDetail {
	
	private Long id; //唯一主键
	
	private String buyer_area;//买家下单的地区
	private Integer num; //买家购买的数量
	
	/**
	 * FIXED （一口价）
		GIFT （送礼）
		BULK_PURCHASE（来自分销商的采购）
		PRESENT （赠品领取）
		GROUP （拼团订单）
		PIFA （批发订单）
		COD （货到付款）
		PEER （代付）
		QRCODE（扫码商家二维码直接支付的交易）
		QRCODE_3RD（线下收银台二维码交易)
	 */
	private String type; //交易类型
	
	private String tid; //交易编号
	
	/**
	 * 0 无维权，1 顾客发起维权，2 顾客拒绝商家的处理结果，3 顾客接受商家的处理结果，9 商家正在处理,101 维权处理中,110 维权结束。
		备注：1到10的状态码是微信维权状态码，100以上的状态码是有赞维权状态码
	 */
	private Integer feedback; //交易维权状态
	
	private Double price; //商品价格,精确到2位小数 单位：元 当一个trade对应多个order的时候，值为第一个交易明细中的商品的价格
	
	private Double total_fee; //商品总价（商品价格乘以数量的总金额）。单位：元，精确到分
	
	private Double payment; //实付金额。单位：元，精确到分
	
	private String buyer_message; //买家购买附言
	
	private String created; //交易创建时间
	
	private String pay_time; //买家付款时间
	
	private List<TradeOrder> orders;//交易明细列表
	
	/**
	 *  退款状态。取值范围：
		NO_REFUND（无退款）
		PARTIAL_REFUNDING（部分退款中）
		PARTIAL_REFUNDED（已部分退款）
		PARTIAL_REFUND_FAILED（部分退款失败）
		FULL_REFUNDING（全额退款中）
		FULL_REFUNDED（已全额退款）
		FULL_REFUND_FAILED（全额退款失败）
	 */
	
	private String refund_state;
	
	/**
	 * 交易状态。取值范围：
		TRADE_NO_CREATE_PAY (没有创建支付交易) 
		WAIT_BUYER_PAY (等待买家付款) 
		WAIT_PAY_RETURN (等待支付确认) 
		WAIT_GROUP（等待成团，即：买家已付款，等待成团）
		WAIT_SELLER_SEND_GOODS (等待卖家发货，即：买家已付款) 
		WAIT_BUYER_CONFIRM_GOODS (等待买家确认收货，即：卖家已发货) 
		TRADE_BUYER_SIGNED (买家已签收) 
		TRADE_CLOSED (付款以后用户退款成功，交易自动关闭) 
		TRADE_CLOSED_BY_USER (付款以前，卖家或买家主动关闭交易)
	 */
	private String status; 
	
	private Double post_fee;//运费
	
	private String pic_thumb_path; //商品主图片缩略图地址
	
	private String receiver_city; //收货人的所在城市。PS：如果订单类型是送礼订单，收货地址在sub_trades字段中；如果物流方式是到店自提，收货地址在fetch_detail字段中

	private Double refunded_fee;//交易完成后退款的金额。单位：元，精确到分
	
	private String num_iid; //废弃2017年8月19日16:32:19  //商品数字编号。当一个trade对应多个order的时候，值为第一个交易明细中的商品的编号
	
	private String title; //交易标题，以首个商品标题作为此标题的值
	
	private String receiver_state; //收货人的所在省份
	
	private String receiver_zip; //收货人的邮编
	
	private String receiver_name; //收货人的姓名
	
	/**
	 * 支付类型。取值范围：
		WEIXIN (微信自有支付)
		WEIXIN_DAIXIAO (微信代销支付)
		ALIPAY (支付宝支付)
		BANKCARDPAY (银行卡支付)
		PEERPAY (代付)
		CODPAY (货到付款)
		BAIDUPAY (百度钱包支付)
		PRESENTTAKE (直接领取赠品)
		COUPONPAY（优惠券/码全额抵扣）
		BULKPURCHASE（来自分销商的采购）
		MERGEDPAY (合并付货款) 
		ECARD（有赞E卡支付）
	 */
	private String pay_type;
	
	private String receiver_district; //收货人的所在地区
	
	private String pic_path; //商品主图片地址。当一个trade对应多个order的时候，值为第一个交易明细中的商品的图片地址
	
	private String receiver_mobile; //收货人的手机号码
	
	private String sign_time; //买家签收时间
	
	private String receiver_address; //收货人的详细地址
	
	private String outer_tid; //外部交易编号。比如，如果支付方式是微信支付，就是财付通的交易单号

	private String shipping_type;//创建交易时的物流方式。取值范围：express（快递），fetch（到店自提），local（同城配送）
	
	private String shopName; //店铺名称 用于区分是哪个店铺的订单  (自定义)
	
	private String shopId; //店铺配置ID (自定义)
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuyer_area() {
		return buyer_area;
	}

	public void setBuyer_area(String buyer_area) {
		this.buyer_area = buyer_area;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Integer getFeedback() {
		return feedback;
	}

	public void setFeedback(Integer feedback) {
		this.feedback = feedback;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public String getBuyer_message() {
		return buyer_message;
	}

	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public List<TradeOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<TradeOrder> orders) {
		this.orders = orders;
	}

	public String getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(String refund_state) {
		this.refund_state = refund_state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getPost_fee() {
		return post_fee;
	}

	public void setPost_fee(Double post_fee) {
		this.post_fee = post_fee;
	}

	public String getPic_thumb_path() {
		return pic_thumb_path;
	}

	public void setPic_thumb_path(String pic_thumb_path) {
		this.pic_thumb_path = pic_thumb_path;
	}

	public String getReceiver_city() {
		return receiver_city;
	}

	public void setReceiver_city(String receiver_city) {
		this.receiver_city = receiver_city;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReceiver_state() {
		return receiver_state;
	}

	public void setReceiver_state(String receiver_state) {
		this.receiver_state = receiver_state;
	}

	public String getReceiver_zip() {
		return receiver_zip;
	}

	public void setReceiver_zip(String receiver_zip) {
		this.receiver_zip = receiver_zip;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getReceiver_district() {
		return receiver_district;
	}

	public void setReceiver_district(String receiver_district) {
		this.receiver_district = receiver_district;
	}

	public String getPic_path() {
		return pic_path;
	}

	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}

	public String getReceiver_mobile() {
		return receiver_mobile;
	}

	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}

	public String getSign_time() {
		return sign_time;
	}

	public void setSign_time(String sign_time) {
		this.sign_time = sign_time;
	}

	public String getReceiver_address() {
		return receiver_address;
	}

	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}

	public String getOuter_tid() {
		return outer_tid;
	}

	public void setOuter_tid(String outer_tid) {
		this.outer_tid = outer_tid;
	}

	public String getShipping_type() {
		return shipping_type;
	}

	public void setShipping_type(String shipping_type) {
		this.shipping_type = shipping_type;
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
