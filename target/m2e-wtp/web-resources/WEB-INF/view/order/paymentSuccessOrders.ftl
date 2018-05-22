<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<script type="text/javascript" src="${rc.contextPath}/static/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/static/jquery/jquery.dataTables.min.js"></script>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 订单管理  <span class="c-gray en">&gt;</span> 交易完成订单列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">

	<form id="paymentForm" class="layui-form" method="post" action="${rc.contextPath}/trade/paymentSuccessOrders.do" >
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
		<button id="paymentBtn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>查询</button>
	</div>
	</form>
	
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<a href="javascript:;" onclick="exportTradeSuccess();" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe644;</i>导出excel</a>
		</span>
	    <span class="r">
		      <div id="topPage" class="r"></div>
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
					<th width="80">财务审核状态</th>
					<th width="95">快递公司</th>
					<th width="125">单号</th>
				</tr>
			</thead>
			<tbody>
			<#list tradeDetailList! as tradeDetail>
				<tr class="text-c">
					<td colspan="9" align="rigth" bgcolor="#F5F5F5">
					<div style="text-align:left;margin-left:25px;">
						<span><b>店:<font color="red">${tradeDetail.shopName!}</font></b></span>
						<span style="margin-left:30px;"><b>订单编号:${tradeDetail.tid!}</b></span>
						<span style="margin-left:30px;"><font color="green">支付时间:${tradeDetail.pay_time!}</font></span>
						<span style="margin-left:30px;">实付金额:￥${tradeDetail.payment?string("#.##")}</span>
						<span style="margin-left:30px;">交易状态:${tradeDetail.status!}</span>
					</div>
					<div style="text-align:left;margin-left:25px;">
						<span><b>收货信息:<font color="blue">${tradeDetail.receiver_state!}-${tradeDetail.receiver_city!}-${tradeDetail.receiver_district!}-
									${tradeDetail.receiver_address!}-${tradeDetail.receiver_name!}-${tradeDetail.receiver_mobile!}</font></b></span>
						<span style="margin-left:20px;"><b>留言:<font color="red">${tradeDetail.buyer_message!}</font></b></span>
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
						<td>￥${tradeOrder.payment?string("#.##")}</td>
						<td><span class="label label-success radius">${tradeOrder.state_str}</span></td>
						<td>
							<#if tradeOrder.orderReview?? &&  tradeOrder.orderReview == 1 >已审核<#else>未审核</#if>
						</td>
						<td>${tradeOrder.expressCompany!}</td>
						<td>${tradeOrder.expressNumber!}</td>
						
					</tr>
				</#list>
			</#list>
			</tbody>
		</table>
		<form class="layui-form">
		
		<div class="layui-form-item">
		    <div id="endPage" class="r"></div>
		 </div>
		
		</form>
		
	</div>
</div>
<script type="text/javascript">
layui.use(['laypage', 'layer','form'], function(){
	  var form = layui.form(),layer = layui.layer;
	  var laypage = layui.laypage,layer = layui.layer;
	  
	  laypage({
	    cont: 'topPage'
	    ,curr:  ${pagination.page!}
	    ,pages: ${pagination.countPage!}
	    ,skin: '#1E9FFF'
	    ,jump: function(obj, first){
	      if(!first){
	      	//调用分页方法
	      	var tid = $('#tid').val();
	      	var payTimeStart = $('#payTimeStart').val();
	      	var payTimeEnd = $('#payTimeEnd').val();
	      	var shopId = $('#shopId').val();
	      	var params = '&tid='+tid+'&payTimeStart='+payTimeStart+'&payTimeEnd='+payTimeEnd+'&shopId='+shopId;
	        window.location.href = "${rc.contextPath}/trade/paymentSuccessOrders.do?page="+obj.curr+params;
	      }
	    }
	  });
	  
	  laypage({
	    cont: 'endPage'
	    ,curr:  ${pagination.page!}
	    ,pages: ${pagination.countPage!}
	    ,skin: '#1E9FFF'
	    ,jump: function(obj, first){
	      if(!first){
	      	//调用分页方法
	      	var tid = $('#tid').val();
	      	var payTimeStart = $('#payTimeStart').val();
	      	var payTimeEnd = $('#payTimeEnd').val();
	      	var shopId = $('#shopId').val();
	      	var params = '&tid='+tid+'&payTimeStart='+payTimeStart+'&payTimeEnd='+payTimeEnd+'&shopId='+shopId;
	        window.location.href = "${rc.contextPath}/trade/paymentSuccessOrders.do?page="+obj.curr+params;
	      }
	    }
	  });
});

function exportTradeSuccess() {
	//获得支付起始时间
	var payTimeStart = $('#payTimeStart').val();
	//获得支付结束时间
	var payTimeEnd =  $('#payTimeEnd').val();
	//订单编号
	var tid =  $('#tid').val();
	var shopId = $('#shopId').val();
	var params = '?payTimeStart='+payTimeStart+'&payTimeEnd='+payTimeEnd+'&tid='+tid+'&shopId='+shopId;
	window.location.href = "${rc.contextPath}/trade/exportTradeSuccess.do"+params;
}
</script> 
</body>
</html>