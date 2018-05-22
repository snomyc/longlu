<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<#include "../public/pub_tag.ftl">
<script type="text/javascript" src="${rc.contextPath}/static/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/static/jquery/jquery.dataTables.min.js"></script>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 财务管理 <span class="c-gray en">&gt;</span> 财务审核 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">

	<form id="paymentForm" class="layui-form" method="post" action="${rc.contextPath}/finance/financeOrderReview.do" >
	<div class="text-c">
	<span class="select-box" style="width:150px">
			<select class="select" id="shopId" name="shopId" size="1">
				<option value=" " selected>全部店铺</option>
				<#list shopConfig! as config>
					<#if shopId?? && shopId?number == config.id>
						<option value="${config.id!}" selected>${config.shopName}</option>
					<#else>
						<option value="${config.id!}">${config.shopName}</option>
					</#if>
				</#list>
			</select>
	</span>
	支付日期：<input type="text" value="${payTimeStart!}" onfocus="WdatePicker()" id="payTimeStart" name="payTimeStart" class="input-text Wdate" style="width:120px;">
		-
		<input type="text" value="${payTimeEnd!}" onfocus="WdatePicker()" id="payTimeEnd" name="payTimeEnd" class="input-text Wdate" style="width:120px;">
		<input type="text" name="tid" id="tid" placeholder="订单编号" style="width:200px" class="input-text" value="${tid!}">
		<input type="text" name="name" id="name" placeholder="收件人|手机" style="width:100px" class="input-text" value="${receiver_name!}">
		<span class="select-box" style="width:110px">
			<select class="select" id="orderBy" name="orderBy" size="1">
				<option value=" " selected>默认排序</option>
				<#if orderBy??>
					<option value="${orderBy}" selected>电话号码</option>
				<#else>
					<option value="receiver_mobile asc,pay_time asc">电话号码</option>
				</#if>
				
			</select>
		</span>
		<button id="paymentBtn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>查询</button>
	</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="checkFinanceOrder();" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe676;</i>批量通过</a>
			<a href="javascript:;" onclick="checkFinanceOrderNoPass();" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe676;</i>批量不通过</a>
			<!--<a href="javascript:;" onclick="exprotFinanceOrders();" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe644;</i>导出excel</a>-->
		</span>
	    <span class="r">
	             需审核订单：<strong>${total}</strong>个
	    </span>
	    
	</div>
	
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="260">商品名称</th>
					<th width="120">商品的规格</th>
					<th width="50">商品图片</th>
					<th width="30">数量</th>
					<th width="50">小计(元)</th>
					<th width="50">供应商</th>
					<th width="60">备注</th>
					<th width="60">商品链接</th>
					<th width="130">审核</th>
				</tr>
			</thead>
			<tbody>
			<#list tradeDetailList! as tradeDetail>
				<tr class="text-c">
					<td colspan="10" align="rigth" bgcolor="#F5F5F5">
					<div style="text-align:left;margin-left:25px;">
						<span><b>店:<font color="red">${tradeDetail.shopName!}</font></b></span>
						<span style="margin-left:30px;"><b>订单编号:${tradeDetail.tid!}</b></span>
					    <span style="margin-left:30px;"><font color="green">支付时间:${tradeDetail.pay_time!}</font></span>
						<span style="margin-left:30px;">实付金额:￥${tradeDetail.payment?string("#.##")}</span>
						<span style="margin-left:30px;">交易状态:${tradeDetail.status!}</span>
					</div>
					<div style="text-align:left;margin-left:25px;">
						<span>
							<b>收货信息:<font color="blue">${tradeDetail.receiver_state!}-${tradeDetail.receiver_city!}-${tradeDetail.receiver_district!}-
									${tradeDetail.receiver_address!}-${tradeDetail.receiver_name!}-${tradeDetail.receiver_mobile!}</font>
								<a href="javascript:;" onclick="copyMobile('${tradeDetail.receiver_mobile!}');"  value="${tradeDetail.receiver_mobile!}"><img width="30" height="25" src="${imgUrl}/mobile.jpg"/></a>
							</b>
						</span>
						<span style="margin-left:20px;"><b>留言:<font color="red">${tradeDetail.buyer_message!}</font></b></span>
					</div>
					</td>
				</tr>
				
				<#list tradeDetail.orders! as tradeOrder>
					<tr class="text-c">
						<td><input type="checkbox" name="orderId" value="${tradeOrder.id}-${tradeOrder.tid}-${tradeOrder.oid}-${tradeOrder.shopId}"></td>
						<td>${tradeOrder.title}<br/>
							商品编码:${tradeOrder.supplierName}-${tradeOrder.goodsNumber}
						</td>
						<td>${tradeOrder.sku_properties_name}</td>
						<td><img width="50" height="50" src="${tradeOrder.pic_path}"/></td>
						<td>${tradeOrder.num}</td>
						<td>￥${tradeOrder.payment?string("#.##")}</td>
						<td><span class="label label-success radius">${tradeOrder.aliSupplierName!}</span></td>
						<td><span class="label label-success radius">${tradeOrder.aliBz!}</span></td>
						<#if tradeOrder.alibabaUrl??>
							<td><a href="javascript:;" onClick="goTaobao('${tradeOrder.alibabaUrl!}','${tradeDetail.receiver_state!}','${tradeDetail.receiver_city!}','${tradeDetail.receiver_district!}','${tradeDetail.receiver_address!}','${tradeDetail.receiver_name!}','${tradeDetail.receiver_mobile!}')" target="_blank"><img width="50" height="50" src="${imgUrl}/urlImg.jpg"/></a></td>
						<#else>
							<td></td>
						</#if>
						<td class="f-14 td-manage">
							<a class="btn btn-success radius" onClick="checkOrderReview('${tradeOrder.tid}','${tradeOrder.id}','${tradeOrder.oid}','${tradeOrder.shopId}')" href="javascript:;">通过</a>
							<a class="btn btn-danger radius" onClick="checkOrderReviewNotPass('${tradeOrder.id}')" href="javascript:;">不通过</a>
						</td>
					</tr>
				</#list>
			</#list>
			</tbody>
		</table>
	</div>
</div>
<input type="hidden" name="customerAddress" id="customerAddress"/>
<input type="hidden" name="copyMobile" id="copyMobile"/>
<div id="financeIdea" style="display:none; text-align:center;">
		<input type="hidden" id="orderIds" name="orderIds" readonly="readonly"/>
		<input type="hidden" id="tradeOrderId" name="tradeOrderId" readonly="readonly"/>
	    <textarea placeholder="审核意见" id="bz" name="bz" cols="5" rows class="textarea radius" style="width:380px;height:150px; font-size:14px; padding:4px"></textarea>
		<div class="layui-form-item">
			<button id="single" class="layui-btn layui-btn-warm" style="margin-top:20px;" onclick="submitOrderReview();">提交</button>
			<button id="batch" class="layui-btn layui-btn-warm" style="margin-top:20px;" onclick="submitOrderNoPass();">批量提交</button>
		</div>
<div>

<script type="text/javascript">
layui.use(['layer','form'], function(){
	  var form = layui.form(),layer = layui.layer;
});

function goTaobao(ali_url,state,city,district,address,name,mobile){
	var customerAddress = state+" "+city+" "+district+" "+address+" "+name+" "+mobile;
	$('#customerAddress').val(customerAddress);
	//显型
	$('#customerAddress').attr("type","text");
	var e = document.getElementById("customerAddress");
    e.select();
	document.execCommand("Copy");
	//隐藏
	$('#customerAddress').attr("type","hidden");
	window.open(ali_url);
}

function copyMobile(mobile) {
	$('#copyMobile').val(mobile);
	//显型
	$('#copyMobile').attr("type","text");
	var e = document.getElementById("copyMobile");
    e.select();
	document.execCommand("Copy");
	//隐藏
	$('#copyMobile').attr("type","hidden");
}

function checkFinanceOrder() {

	var orderIds = "";
	$("input:checkbox[name='orderId']:checked").each(function() {
	  orderIds = orderIds + $(this).val()+",";
	});
	if(orderIds == "") {
		layer.open({
		  title: '提示'
		  ,content: '请勾选待审核订单!'
		});
		return;    
	}
	layer.confirm('确认批量审核通过吗？',function(index){
		//0代表加载的风格，支持0-2
		var index = layer.load(2, {shade: false});
		//ajax更新审核状态
		$.ajax({
			type:'POST',
			url: "${rc.contextPath}/finance/batchOrderCheckPass.do",
			data:{orderIds:orderIds},
			dataType:"json",
			success: function(json){
				layer.close(index);
				var flag = json.success;
				if(flag) {
					window.location.href = "${rc.contextPath}/finance/financeOrderReview.do";
				}else {
					layer.msg(json.msg,{icon: 5,time:5000,area:['480px','200px']});
				}
			}
		});
	});
	
}

function checkFinanceOrderNoPass() {
	var orderIds = "";
	$("input:checkbox[name='orderId']:checked").each(function() {
	  orderIds = orderIds + $(this).val()+",";
	});
	if(orderIds == "") {
		layer.open({
		  title: '提示'
		  ,content: '请勾选待审核订单!'
		});
		return;    
	}
	$('#orderIds').val(orderIds);
	$('#single').hide();
	$('#batch').show();
	layer.open({
		  type: 1,
		  fix: false, //不固定
		  maxmin: true,
		  shade:0.4,
		  content: $('#financeIdea'),
		  area: ['400px', '300px'],
		  title:'批量审核不通过原因'
	});
}

function submitOrderNoPass() {
	var orderIds = $('#orderIds').val();
	var bz = $('#bz').val();
	//ajax更新审核状态
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/finance/batchOrderCheckNoPass.do",
		data:{orderIds:orderIds,bz:bz},
		dataType:"json",
		success: function(json){
			var flag = json.success;
			if(flag) {
				window.location.href = "${rc.contextPath}/finance/financeOrderReview.do";
			}else {
				layer.msg(json.msg,{icon: 5,time:5000,area:['480px','200px']});
			}
		}
	});
}


function exprotFinanceOrders() {
	//获得支付起始时间
	var payTimeStart = $('#payTimeStart').val();
	//获得支付结束时间
	var payTimeEnd =  $('#payTimeEnd').val();
	//订单编号
	var tid =  $('#tid').val();
	var name = $('#name').val();
	var mobile = $('#mobile').val();
	var params = '?payTimeStart='+payTimeStart+'&payTimeEnd='+payTimeEnd+'&tid='+tid+'&name='+name+'&mobile='+mobile;
	window.location.href = "${rc.contextPath}/finance/exprotFinanceOrders.do"+params;
}

/*订单审核通过*/
function checkOrderReview(tid,id,oid,shopId){
	layer.confirm('确认审核通过吗？',function(index){
		//加载层 0代表加载的风格，支持0-2
		var index = layer.load(2, {shade: false});
		//ajax更新审核状态
		$.ajax({
			type:'POST',
			url: "${rc.contextPath}/finance/orderCheckPass.do",
			data:{id:id,tid:tid,oid:oid,shopId:shopId},
			dataType:"json",
			success: function(json){
				layer.close(index);
				var flag = json.success;
				if(flag) {
					window.location.href = "${rc.contextPath}/finance/financeOrderReview.do";
				}else {
					layer.msg(json.msg,{icon: 5,time:3000,area:['480px']});
				}
			}
		});
	});
}

/*订单审核不通过*/
function checkOrderReviewNotPass(id){

	$('#tradeOrderId').val(id);
	$('#single').show();
	$('#batch').hide();
	layer.open({
		  type: 1,
		  fix: false, //不固定
		  maxmin: true,
		  shade:0.4,
		  content: $('#financeIdea'),
		  area: ['400px', '300px'],
		  title:'审核不通过原因'
	});
}

function submitOrderReview() {
	var id = $('#tradeOrderId').val();
	var bz = $('#bz').val();
	if(bz=='') {
		alert('请输入取消原因!');
		return;
	}
	
	//ajax更新审核状态
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/finance/orderCheckPass.do",
		data:{id:id,bz:bz},
		dataType:"json",
		success: function(json){
			var flag = json.success;
			if(flag) {
				window.location.href = "${rc.contextPath}/finance/financeOrderReview.do";
			}else {
				layer.closeAll();
				layer.msg(json.msg,{icon: 5,time:2000});
			}
		}
	});
	
}

</script> 
</body>
</html>