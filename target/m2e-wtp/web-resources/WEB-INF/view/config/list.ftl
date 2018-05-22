<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 有赞店铺配置<a class="btn btn-success radius r" id="btn-refresh" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form id="paymentForm" class="layui-form" method="post" action="${rc.contextPath}/config/list.do" >
		<div class="text-c">
			<input type="text" name="shopName" id="shopName" placeholder="店铺名称" style="width:250px" class="input-text" value="${shopName!}">
			<button id="paymentBtn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i>查询</button>
		</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
	<span class="l">
		<a href="javascript:;" onclick="user_add('添加有赞店铺配置','${rc.contextPath}/config/createFrom.do','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加有赞店铺配置</a>
		<a href="https://open.youzan.com/oauth/authorize?client_id=bd610ddc096cb03c89&response_type=code&state=teststate&redirect_uri=http://snomyc.51mypc.cn/innerbuy/youzan_auth.do" target="_blank" class="btn btn-primary radius">有赞店铺授权</a>
	</span> 
	<span class="r">共有数据：<strong>${total!}</strong> 条</span> </div>
	
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="7">有赞店铺配置管理</th>
			</tr>
			<tr class="text-c">
				<th width="15%">店铺名称</th>
				<th width="15%">client_id</th>
				<th width="24%">client_secret</th>
				<th width="24%">token</th>
				<th width="6%">店铺状态</th>
				<th width="10%">店铺id</th>
				<th width="8%">操作</th>
			</tr>
		</thead>
		<tbody>
		<#list configList! as config>
			<tr class="text-c">
				<td>${config.shopName!}</td>
				<td>${config.clientId!}</td>
				<td>${config.clientSecret!}</td>
				<td>${config.token!}</td>
				<td>
					<#if config.status == 1>
						<span class="label label-success radius">启用</span>
					<#else>
						<span class="label label-fail radius">停用</span>
					</#if>
				</td>
				<td>${config.kdtId!}</td>
				<td class="f-14">
					<a title="编辑" href="javascript:;" onClick="user_edit('编辑商品URL','${rc.contextPath}/config/createFrom.do?id=${config.id}','800','500')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> 
					<a title="删除" href="javascript:;" onClick="deleteRecord('${config.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
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
		window.location.href = "${rc.contextPath}/config/delete.do?id="+id;
	});
}
</script>
</body>
</html>