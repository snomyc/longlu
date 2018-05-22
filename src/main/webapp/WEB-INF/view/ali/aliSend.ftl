<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<#include "../public/pub_tag.ftl">
<#include "../public/pub_validate.ftl">
</head>
<body>
<article class="page-container">
<div class="page-container">
	<font color="red">阿里订单商品名称:</font><font color="green">${record.goodsName!}</font>
	<div>
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
				</tr>
			</thead>
			<tbody>
			<#list tradeDetailList! as tradeDetail>
				<tr class="text-c">
					<td colspan="7" align="rigth" bgcolor="#F5F5F5">
					<div style="text-align:left;margin-left:25px;">
						<span><b>店:<font color="red">${tradeDetail.shopName!}</font></b></span>
						<span style="margin-left:30px;"><b>订单编号:${tradeDetail.tid!}</b></span>
						<span style="margin-left:30px;"><font color="green">支付时间:${tradeDetail.pay_time!}</font></span>
						<span style="margin-left:30px;">实付金额:￥${tradeDetail.payment?string("#.##")}</span>
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
					</tr>
				</#list>
			</#list>
			</tbody>
		</table>
	</div>
</div>
	<div class="layui-form-item">
      	<input type="hidden" class="input-text" id="aliSendGoodsId" name="aliSendGoodsId" value="${record.id!}">
        <input type="hidden" value="${expressManager.num!}" name="num" id="num" class="layui-input">
        
	    <label class="layui-form-label"><span class="c-red">*</span>物流公司：</label>
	    <div class="layui-input-inline">
	      <input type="text" value="${expressManager.company!}" name="company" id="company" readonly="readonly" class="layui-input">
	    </div>
	    
	    <label class="layui-form-label"><span class="c-red">*</span>物流单号：</label>
	    <div class="layui-input-inline">
	      <input type="text" value="${record.expressNumber!}" name="expressNumber" id="expressNumber" readonly="readonly" class="layui-input">
	    </div>
	</div>
	<center>
	<a href="javascript:;" onclick="batchSendGoods();" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe676;</i>批量发货</a>
	</center>
</article>
<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript">
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
	var expressCompany = $('#num').val();
	var expressNumber = $('#expressNumber').val();
	var aliSendGoodsId = $('#aliSendGoodsId').val();
	
	if(expressCompany == '' || expressNumber =='') {
		alert("物流公司或物流单号不能为空");
		return;
	}
	
	var index = layer.load(2, {shade: false});
	//ajax更新审核状态
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/ali/aliSendExpress.do",
		data:{orderIds:orderIds,expressCompany:expressCompany,expressNumber:expressNumber,aliSendGoodsId:aliSendGoodsId},
		dataType:"json",
		success: function(json){
			layer.close(index);
			var flag = json.success;
			if(flag) {
				//关掉窗口并刷新父页面
				parent.location.reload();
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}else {
				layer.closeAll();
				layer.msg(json.msg,{icon: 5,time:5000,area:['480px','200px']});
			}
		}
	});
}



</script> 
</body>
</html>