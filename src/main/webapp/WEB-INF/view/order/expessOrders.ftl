<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<script type="text/javascript" src="${rc.contextPath}/static/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/static/jquery/jquery.dataTables.min.js"></script>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 订单管理 <span class="c-gray en">&gt;</span> 订单快递录入 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">

	<form id="paymentForm" class="layui-form" method="post" action="${rc.contextPath}/trade/expessOrders.do" >
	<div class="text-c"> 支付日期：
		<input type="text" value="${payTimeStart!}" onfocus="WdatePicker()" id="payTimeStart" name="payTimeStart" class="input-text Wdate" style="width:120px;">
		-
		<input type="text" value="${payTimeEnd!}" onfocus="WdatePicker()" id="payTimeEnd" name="payTimeEnd" class="input-text Wdate" style="width:120px;">
		<input type="text" name="tid" id="tid" placeholder="订单编号" style="width:250px" class="input-text" value="${tid!}">
		<button id="paymentBtn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>查询</button>
	</div>
	</form>
	
	
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
		</span>
	    <span class="r">
	             需录入订单：<strong>${total}</strong>个
	    </span>
	    
	</div>
	
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th width="260">商品名称</th>
					<th width="120">商品的规格</th>
					<th width="50">商品图片</th>
					<th width="30">数量</th>
					<th width="50">小计(元)</th>
					<th width="60">商品状态</th>
					<th width="95">快递公司</th>
					<th width="125">单号</th>
					<th width="120">快递录入</th>
				</tr>
			</thead>
			<tbody>
			<#list tradeDetailList! as tradeDetail>
				<tr class="text-c">
					<td colspan="9" align="rigth" bgcolor="#F5F5F5">
					<div style="text-align:left;margin-left:25px;">
						<span style="color:red;"><b>订单编号:${tradeDetail.tid!}</b></span>
						<span style="margin-left:30px;">交易状态:${tradeDetail.status!}</span>
					    <span style="margin-left:30px;">付款方式:${tradeDetail.pay_type!}</span>
						<span style="margin-left:30px;">支付时间:${tradeDetail.pay_time!}</span>
						<span style="margin-left:30px;">实付金额:￥${tradeDetail.payment!}</span>
						
					</div>
					<div style="text-align:left;margin-left:25px;">
						<span style="color:blue;"><b>收货信息:${tradeDetail.receiver_state!}-${tradeDetail.receiver_city!}-${tradeDetail.receiver_district!}-
									${tradeDetail.receiver_address!}-${tradeDetail.receiver_name!}-${tradeDetail.receiver_mobile!}</b></span>
						<span style="color:red;margin-left:20px;">留言:${tradeDetail.buyer_message!}</span>
					</div>
					</td>
				</tr>
				
				<#list tradeDetail.orders! as tradeOrder>
					<tr class="text-c">
						<td>${tradeOrder.title}<br/>
							商品编码:${tradeOrder.supplierName}-${tradeOrder.goodsNumber}
						</td>
						<td>${tradeOrder.sku_properties_name}</td>
						<td><img width="50" height="50" src="${tradeOrder.pic_path}"/></td>
						<td>${tradeOrder.num}</td>
						<td>￥${tradeOrder.payment}</td>
						<td><span class="label label-success radius">${tradeOrder.state_str}</span></td>
						<td>${tradeOrder.expressCompany!}</td>
						<td>${tradeOrder.expressNumber!}</td>
						<td class="f-14 td-manage">
							<a class="btn btn-success radius" onClick="checkOrderReview('${tradeOrder.tid}','${tradeOrder.id}')" href="javascript:;">完成</a>
							<a class="btn btn-danger radius" onClick="checkOrderReviewNotPass('${tradeOrder.id}')" href="javascript:;">取消</a>
						</td>
					</tr>
				</#list>
			</#list>
			</tbody>
		</table>
	</div>
</div>

<div id="financeIdea" style="display:none; text-align:center;">
		<input type="hidden" id="tradeOrderId" name="tradeOrderId" readonly="readonly">
	    <textarea placeholder="审核意见" id="bz" name="bz" cols="5" rows class="textarea radius" style="width:380px;height:150px; font-size:14px; padding:4px"></textarea>
		<div class="layui-form-item">
			<button class="layui-btn layui-btn-warm" style="margin-top:20px;" onclick="submitOrderReview();">提交</button>
		</div>
<div>

<script type="text/javascript">

/*订单录入完成*/
function checkOrderReview(tid,id){
	layer.confirm('确认快递录入完成吗？',function(index){
		//ajax更新审核状态
		$.ajax({
			type:'POST',
			url: "${rc.contextPath}/trade/checkPayOrderExpessInput.do",
			data:{id:id,tid:tid},
			dataType:"json",
			success: function(json){
				var flag = json.success;
				if(flag) {
					window.location.href = "${rc.contextPath}/trade/expessOrders.do";
				}else {
					layer.msg(json.msg,{icon: 5,time:2000});
				}
			}
		});
	});
}

/*订单取消录入*/
function checkOrderReviewNotPass(id){

	$('#tradeOrderId').val(id);
	layer.open({
		  type: 1,
		  fix: false, //不固定
		  maxmin: true,
		  shade:0.4,
		  content: $('#financeIdea'),
		  area: ['400px', '300px'],
		  title:'取消录入原因'
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
		url: "${rc.contextPath}/trade/checkPayOrderExpessInput.do",
		data:{id:id,bz:bz},
		dataType:"json",
		success: function(json){
			var flag = json.success;
			if(flag) {
				window.location.href = "${rc.contextPath}/trade/expessOrders.do";
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