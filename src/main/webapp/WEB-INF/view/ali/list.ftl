<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 商品管理 <span class="c-gray en">&gt;</span> 阿里巴巴商品管理 <a class="btn btn-success radius r" id="btn-refresh" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form id="paymentForm" class="layui-form" method="post" action="${rc.contextPath}/ali/list.do" >
		<div class="text-c">
			<input type="text" name="outerCode" id="outerCode" placeholder="商品编码" style="width:250px" class="input-text" value="${outerCode!}">
			<button id="paymentBtn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>查询</button>
		</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
	<span class="l">
		<a href="javascript:;" onclick="user_add('添加阿里商品URL','${rc.contextPath}/ali/createFrom.do','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加阿里商品URL</a>
	</span> 
	<span class="r">共有数据：<strong>${total!}</strong> 条</span> </div>
	
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="7">阿里巴巴商品管理</th>
			</tr>
			<tr class="text-c">
				<th width="15%">商品编码</th>
				<th width="10%">商品名称</th>
				<th width="42%">阿里巴巴商品URL地址</th>
				<th width="8%">供应商</th>
				<th width="7%">采购成本</th>
				<th width="10%">备注</th>
				<th width="8%">操作</th>
			</tr>
		</thead>
		<tbody>
		<#list aliList! as ali>
			<tr class="text-c">
				<td>${ali.outerCode!}</td>
				<td>${ali.goodsName!}</td>
				<td><a href="${ali.alibabaUrl!}" target="_blank">${ali.alibabaUrl!}</a></td>
				<td>${ali.supplierName!}</td>
				<td>${ali.cost!}</td>
				<td>${ali.bz!}</td>
				<td class="f-14">
					<a title="编辑" href="javascript:;" onClick="user_edit('编辑商品URL','${rc.contextPath}/ali/createFrom.do?id=${ali.id}','800','500')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> 
					<a title="删除" href="javascript:;" onClick="deleteRecord('${ali.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
				</td>
			</tr>
	   </#list>
		</tbody>
	</table>
	
</div>
<script type="text/javascript">

function user_add(title,url,w,h) {
	layer_show(title,url,w,h);
}

function user_edit(title,url,w,h){
	layer_show(title,url,w,h);
}

function deleteRecord(id){
	layer.confirm('确认要删除吗？',function(index){
		window.location.href = "${rc.contextPath}/ali/delete.do?id="+id;
	});
}
</script>
</body>
</html>