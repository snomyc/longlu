<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<#include "../public/pub_tag.ftl">
<script type="text/javascript" src="${rc.contextPath}/static/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/static/jquery/jquery.dataTables.min.js"></script>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 仓库管理 <span class="c-gray en">&gt;</span> 待发货订单 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">

	<form id="paymentForm" class="layui-form" method="post" action="${rc.contextPath}/deport/deportOrders.do" >
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
		<input type="text" name="tid" id="tid" placeholder="订单编号" style="width:250px" class="input-text" value="${tid!}">
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
			<a href="javascript:;" onclick="batchSendGoods();" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe676;</i>批量发货</a>
			<a href="javascript:;" onclick="exprotDeportOrders();" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe644;</i>导出excel</a>
			<a href="javascript:;" onclick="synchroYouZanTrade();" class="btn btn-warning radius"><i class="Hui-iconfont">&#xe634;</i>一键同步</a>
		</span>
	    <span class="r">
	             需发货订单：<strong>${total}</strong>个
	    </span>
	    
	</div>
	
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="260">商品名称</th>
					<th width="120">商品的规格</th>
					<th width="60">商品图片</th>
					<th width="40">数量</th>
					<th width="60">小计(元)</th>
					<th width="60">商品状态</th>
					<th width="120">操作</th>
				</tr>
			</thead>
			<tbody>
			<#list tradeDetailList! as tradeDetail>
				<tr class="text-c">
					<td colspan="8" align="rigth" bgcolor="#F5F5F5">
					<div style="text-align:left;margin-left:25px;">
						<span><b>店:<font color="red">${tradeDetail.shopName!}</font></b></span>
						<span style="margin-left:30px;"><b>订单编号:${tradeDetail.tid!}</b></span>
					    <!--<span style="margin-left:30px;">付款方式:${tradeDetail.pay_type!}</span>-->
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
						<td><span class="label label-success radius">${tradeOrder.state_str}</span></td>
						<td class="f-14 td-manage">
							<a class="btn btn-success radius" onClick="expessGoods('${tradeOrder.tid}','${tradeOrder.id}','${tradeOrder.oid}','${tradeOrder.shopId}')" href="javascript:;">发货</a> 
							<a class="btn btn-danger radius" onClick="cancelExpess('${tradeOrder.id}')" href="javascript:;">取消</a>
						</td>
					</tr>
				</#list>
			</#list>
			</tbody>
		</table>
	</div>
</div>
<input type="hidden" name="copyMobile" id="copyMobile"/>
<input type="hidden" id="tradeOrderId" name="tradeOrderId" readonly="readonly"/>
<input type="hidden" id="tradeOrderTid" name="tradeOrderTid" readonly="readonly"/>
<input type="hidden" id="tradeOrderOid" name="tradeOrderOid" readonly="readonly"/>
<input type="hidden" id="tradeOrderShopId" name="tradeOrderShopId" readonly="readonly"/>
<div id="express" style="display:none; text-align:center;margin-top:60px;">
		<div class="layui-form-item">
			<label class="layui-form-label">快递公司:</label>
		    <div class="layui-input-inline">
			      <select style="margin-top:10px;" id="expressCompany" name="expressCompany" class="select" size="1">
				    <option value="" selected>请选择快递公司</option>
				    <option value="1">申通E物流</option>
		    	    <option value="2">圆通速递</option>
		    	    <option value="3">中通速递</option>
		    	    <option value="4">韵达快运</option>
		    	    <option value="5">天天快递</option>
		    	    <option value="6">百世汇通</option>
		    	    <option value="7">顺丰速运</option>
		    	    <option value="8">邮政国内小包</option>
		    	    <option value="10">EMS经济快递</option>
		    	    <option value="11">EMS</option>
		    	    <option value="40">国通快递</option>
		    	    <option value="38">优速快递</option>
		    	    <option value="12">邮政平邮</option>
		    	    <option value="13">德邦快递</option>
		    	    <option value="34">快捷速递</option>
		    	    <option value="22">速尔</option>
		    	    <option value="23">飞康达速运</option>
		    	    <option value="25">宅急送</option>
		    	    <option value="27">联邦快递</option>
		    	    <option value="28">德邦物流</option>
		    	    <option value="30">中铁快运</option>
		    	    <option value="41">其他快递</option>
				  </select>
		    </div>
		</div>
		<div class="layui-form-item">
		    <label class="layui-form-label">快递单号:</label>
		    <div class="layui-input-inline">
		      <input type="text" name="expressNumber" id="expressNumber" lay-verify="title" placeholder="快递单号" class="layui-input">
		    </div>
		</div>
		<div class="layui-form-item">
			<button id="single" class="layui-btn layui-btn-warm" style="margin-top:20px;" onclick="expessSubmit();">发货</button>
			<button id="batch" class="layui-btn layui-btn-warm" style="margin-top:20px;" onclick="batchExpessSubmit();">批量发货</button>
		</div>
</div>

<div id="cancelExpess" style="display:none; text-align:center;">
	    <textarea placeholder="审核意见" id="bz" name="bz" cols="5" rows class="textarea radius" style="width:380px;height:150px; font-size:14px; padding:4px"></textarea>
		<div class="layui-form-item">
			<button class="layui-btn layui-btn-warm" style="margin-top:20px;" onclick="cancelExpessSubmit();">提交</button>
		</div>
</div>
<script type="text/javascript">

layui.use(['layer','form'], function(){
	  var form = layui.form(),layer = layui.layer;
});

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

function synchroYouZanTrade() {
	var index = layer.load(2, {shade: false});
	//ajax更新审核状态
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/deport/synchroYouZanTrade.do",
		dataType:"json",
		success: function(json){
			layer.close(index);
			var flag = json.success;
			if(flag) {
				window.location.href = "${rc.contextPath}/deport/deportOrders.do";
			}else {
				layer.closeAll();
				layer.msg(json.msg,{icon: 5,time:5000,area:['480px','200px']});
			}
		}
	});
}

function batchSendGoods() {
	
	var orderIds = "";
	$("input:checkbox[name='orderId']:checked").each(function() {
	  orderIds = orderIds + $(this).val()+",";
	});
	if(orderIds == "") {
		layer.open({
		  title: '提示'
		  ,content: '请勾选待发货订单!'
		});
		return;    
	}
	$('#tradeOrderId').val(orderIds);
	$('#single').hide();
	$('#batch').show();
	layer.open({
	  type: 1,
	  fix: false, //不固定
	  maxmin: true,
	  shade:0.4,
	  content: $('#express'),
	  area: ['400px', '300px'],
	  title:'快递信息'
	});
}

function batchExpessSubmit() {
	
	var orderIds = $('#tradeOrderId').val();
	var expressCompany = $('#expressCompany').val();
	var expressNumber = $('#expressNumber').val();
	if(expressCompany == '' || expressNumber =='') {
		alert("快递公司或快递单号不能为空");
		return;
	}
	
	var index = layer.load(2, {shade: false});
	//ajax更新审核状态
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/deport/batchSendGoods.do",
		data:{orderIds:orderIds,expressCompany:expressCompany,expressNumber:expressNumber},
		dataType:"json",
		success: function(json){
			layer.close(index);
			var flag = json.success;
			if(flag) {
				window.location.href = "${rc.contextPath}/deport/deportOrders.do";
			}else {
				layer.closeAll();
				layer.msg(json.msg,{icon: 5,time:5000,area:['480px','200px']});
			}
		}
	});
}


function exprotDeportOrders() {
	//获得支付起始时间
	var payTimeStart = $('#payTimeStart').val();
	//获得支付结束时间
	var payTimeEnd =  $('#payTimeEnd').val();
	//订单编号
	var tid =  $('#tid').val();
	var name = $('#name').val();
	var params = '?payTimeStart='+payTimeStart+'&payTimeEnd='+payTimeEnd+'&tid='+tid+'&name='+name;
	window.location.href = "${rc.contextPath}/deport/exprotDeportOrders.do"+params;
}

//审核通过 发货提交
function expessSubmit() {
	var id = $('#tradeOrderId').val();
	var tid = $('#tradeOrderTid').val();
	var oid = $('#tradeOrderOid').val();
	var shopId = $('#tradeOrderShopId').val();
	var expressCompany = $('#expressCompany').val();
	var expressNumber = $('#expressNumber').val();
	if(expressCompany == '' || expressNumber =='') {
		alert("快递公司或快递单号不能为空");
		return;
	}
	var index = layer.load(2, {shade: false});
	//ajax更新审核状态
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/deport/checkOrderExpress.do",
		data:{id:id,tid:tid,oid:oid,shopId:shopId,expressCompany:expressCompany,expressNumber:expressNumber},
		dataType:"json",
		success: function(json){
			layer.close(index);
			var flag = json.success;
			if(flag) {
				//layer.msg('审核通过!',{icon: 6,time:2000});
				window.location.href = "${rc.contextPath}/deport/deportOrders.do";
			}else {
				layer.closeAll();
				layer.msg(json.msg,{icon: 5,time:2000});
			}
		}
	});
}

/*发货*/
function expessGoods(tid,id,oid,shopId){
	$('#tradeOrderTid').val(tid);
	$('#tradeOrderId').val(id);
	$('#tradeOrderOid').val(oid);
	$('#tradeOrderShopId').val(shopId);
	$('#single').show();
	$('#batch').hide();
	layer.open({
	  type: 1,
	  fix: false, //不固定
	  maxmin: true,
	  shade:0.4,
	  content: $('#express'),
	  area: ['400px', '300px'],
	  title:'快递信息'
	});
}


//审核不通过 ，取消
function cancelExpessSubmit() {
	var id = $('#tradeOrderId').val();
	var bz = $('#bz').val();
	if(bz=='') {
		alert('请输入取消原因!');
		return;
	}
	//ajax更新审核状态
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/deport/checkOrderExpress.do",
		data:{id:id,bz:bz},
		dataType:"json",
		success: function(json){
			var flag = json.success;
			if(flag) {
				//layer.msg('审核通过!',{icon: 6,time:2000});
				window.location.href = "${rc.contextPath}/deport/deportOrders.do";
			}else {
				layer.closeAll();
				layer.msg(json.msg,{icon: 5,time:2000});
			}
		}
	});

}


/*取消发货*/
function cancelExpess(id) {
	$('#tradeOrderId').val(id);
	layer.open({
	  type: 1,
	  fix: false, //不固定
	  maxmin: true,
	  shade:0.4,
	  content: $('#cancelExpess'),
	  area: ['400px', '300px'],
	  title:'取消原因'
	});
}

</script> 
</body>
</html>