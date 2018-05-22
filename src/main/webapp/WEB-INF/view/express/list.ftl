<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 物流管理 <span class="c-gray en">&gt;</span> 有赞-阿里物流管理 <a class="btn btn-success radius r" id="btn-refresh" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
	<span class="l">
		<a href="javascript:;" onclick="user_add('添加物流信息','${rc.contextPath}/express/createFrom.do','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加有赞-阿里物流</a>
	</span> 
	<span class="r">共有数据：<strong>${total!}</strong> 条</span> </div>
	
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="4">有赞-阿里物流管理</th>
			</tr>
			<tr class="text-c">
				<th width="15%">有赞物流公司编号</th>
				<th width="35%">有赞物流公司</th>
				<th width="35%">阿里巴巴物流公司</th>
				<th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
		<#list expressList! as express>
			<tr class="text-c">
				<td>${express.num!}</td>
				<td>${express.company!}</td>
				<td>${express.ali_company!}</a></td>
				<td class="f-14">
					<a title="编辑" href="javascript:;" onClick="user_edit('编辑物流信息','${rc.contextPath}/express/createFrom.do?id=${express.id}','800','500')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> 
					<a title="删除" href="javascript:;" onClick="deleteRecord('${express.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
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
		window.location.href = "${rc.contextPath}/express/delete.do?id="+id;
	});
}
</script>
</body>
</html>