<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<script type="text/javascript" src="${rc.contextPath}/static/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/static/jquery/jquery.dataTables.min.js"></script>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 商品管理 <span class="c-gray en">&gt;</span> 库存管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">

	<form id="paymentForm" class="layui-form" method="post" action="${rc.contextPath}/goods/saleGoodsInfo.do" >
	<div class="text-c">
		<input type="text" name="goodsName" id="goodsName" placeholder="商品名称" style="width:250px" class="input-text" value="${goodsName!}">
		<button id="paymentBtn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>查询</button>
	</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
	    <span class="r">
		      <div id="topPage" class="r"></div>
	    </span>
	    
	</div>
	
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th width="260">商品规格</th>
					<th width="50">库存</th>
					<th width="60">编辑</th>
				</tr>
			</thead>
			<tbody>
			<#list goodsList! as goods>
			
				<tr class="text-c">
					<td colspan="5" align="rigth" bgcolor="#F5F5F5">
					<div style="text-align:left;margin-left:25px;">
						<span style="color:red;"><b>商品名称:&nbsp;&nbsp;${goods.title!}</b></span>
					</div>
					<div style="text-align:left;margin-left:25px;">
						<span><b>商品主图:&nbsp;&nbsp;</b><img width="50" height="50" src="${goods.picUrl!}"/></span>
						<span style="margin-left:30px;"><b>商品总库存:&nbsp;&nbsp;</b>${goods.num!}</span>
					    <span style="margin-left:30px;"><b>商品总销量:&nbsp;&nbsp;</b>${goods.soldNum!}</span>
						<span style="margin-left:30px;"><b>商品状态:&nbsp;&nbsp;</b>
							<#if goods.isListing?? && goods.isListing == true>
								<span class="label label-success radius">已上架</span>
							<#else>
								<span class="label label-false radius">已下架</span>
							</#if>
						</span>
						<span style="margin-left:30px;"><b>商品属性:&nbsp;&nbsp;</b>
							<#if goods.itemType?? && goods.itemType == 10>
								<span class="label label-false radius">分销商品</span>
							<#else>
								<span class="label label-success radius">普通商品</span>
							</#if>
						</span>
					</div>
					</td>
				</tr>
			
				<#if goods.skus?size!=0>
				  	<#list goods.skus! as sku>
					<tr class="text-c">
						<td>${sku.propertiesName!}</td>
						<td>${sku.quantity!}</td>
						<td>
							<#if goods.itemType?? && goods.itemType == 10>
								<span class="label label-false radius">分销商品不可编辑库存</span>
							<#else>
								<a class="btn btn-success radius" onClick="editSkuGoodsInfo('${goods.numIid!}','${sku.skuId!}','${sku.quantity!}')" href="javascript:;">编辑</a>
							</#if>
						</td>
					</tr>
					</#list>
				<#else>
					<tr class="text-c">
						<td>${goods.title!}</td>
						<td>${goods.num!}</td>
						<td>
							<#if goods.itemType?? && goods.itemType == 10>
								<span class="label label-false radius">分销商品不可编辑库存</span>
							<#else>
								<a class="btn btn-success radius" onClick="editGoodsInfo('${goods.numIid!}','${goods.num!}')" href="javascript:;">编辑</a>
							</#if>
						</td>
					</tr>
				</#if>
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
<div id="goodsWin" style="display:none; text-align:center;">
		<input type="hidden" id="numIid" name="numIid" readonly="readonly">
		<input type="hidden" id="skuId" name="skuId" readonly="readonly">
		
		<div class="layui-form-item">
		    <label class="layui-form-label">商品库存:</label>
		    <div class="layui-input-inline">
		      <input type="text" name="quantity" id="quantity" class="layui-input">
		    </div>
		</div>
		<div class="layui-form-item">
			<button class="layui-btn layui-btn-warm" style="margin-top:20px;" onclick="submitGoodsInfo();">提交</button>
		</div>
<div>

<script type="text/javascript">
layui.use(['laypage', 'layer','form'], function(){
	  var form = layui.form(),layer = layui.layer;
	  var laypage = layui.laypage,layer = layui.layer;
	  
	   laypage({
	    cont: 'topPage'
	    ,curr:  ${pagination.page!}
	    ,pages: ${pagination.countPage!}
	    ,groups: 0
	    ,first: false
	    ,last: false
	    ,jump: function(obj, first){
	      if(!first){
	        layer.msg('第 '+ obj.curr +' 页');
	        //调用分页方法
	        var goodsName = $('#goodsName').val();
	        var params = '&goodsName='+goodsName;
	        window.location.href = "${rc.contextPath}/goods/saleGoodsInfo.do?page="+obj.curr+params;
	      }
	    }
	  });
	  
	  laypage({
	    cont: 'endPage'
	    ,curr:  ${pagination.page!}
	    ,pages: ${pagination.countPage!}
	    ,groups: 0
	    ,first: false
	    ,last: false
	    ,jump: function(obj, first){
	      if(!first){
	        layer.msg('第 '+ obj.curr +' 页');
	        //调用分页方法
	        var goodsName = $('#goodsName').val();
	        var params = '&goodsName='+goodsName;
	        window.location.href = "${rc.contextPath}/goods/saleGoodsInfo.do?page="+obj.curr+params;
	      }
	    }
	  });
	  
});

function submitGoodsInfo() {
	var numIid = $('#numIid').val();
	var skuId = $('#skuId').val();
	var quantity = $('#quantity').val();
	var goodsName = $('#goodsName').val();
	if(quantity=='') {
		alert('请输入库存数量!');
		return;
	}
	
	//ajax更新审核状态
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/goods/editGoodsInfo.do",
		data:{numIid:numIid,skuId:skuId,quantity:quantity},
		dataType:"json",
		success: function(json){
			var flag = json.success;
			if(flag) {
				window.location.href = "${rc.contextPath}/goods/saleGoodsInfo.do?goodsName="+goodsName+"&page="+${pagination.page!};
			}else {
				layer.closeAll();
				layer.msg(json.msg,{icon: 5,time:2000});
			}
		}
	});
	
}



/*编辑sku商品信息*/
function editSkuGoodsInfo(numIid,skuId,quantity) {
	$('#numIid').val(numIid);
	$('#skuId').val(skuId);
	$('#quantity').val(quantity);
	layer.open({
		  type: 1,
		  fix: false, //不固定
		  maxmin: true,
		  shade:0.4,
		  content: $('#goodsWin'),
		  area: ['400px', '300px'],
		  title:'编辑商品'
	});
}
/*编辑没有sku商品种类信息*/
function editGoodsInfo(numIid,quantity) {
	$('#numIid').val(numIid);
	$('#quantity').val(quantity);
	layer.open({
		  type: 1,
		  fix: false, //不固定
		  maxmin: true,
		  shade:0.4,
		  content: $('#goodsWin'),
		  area: ['400px', '300px'],
		  title:'编辑商品'
	});
}
</script> 
</body>
</html>