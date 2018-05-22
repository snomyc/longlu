<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 角色管理 <a class="btn btn-success radius r" id="btn-refresh" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray"> 
	<span class="l">
		<a href="javascript:;" onclick="role_add('添加角色','${rc.contextPath}/role/createFrom.do','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加角色</a>
	</span> 
	<span class="r">共有数据：<strong>${total!}</strong> 条</span> </div>
	
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="8">角色管理</th>
			</tr>
			<tr class="text-c">
				<th width="100">角色名称</th>
				<th width="40">描述</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
		<#list roleList! as role>
			<tr class="text-c">
				<td>${role.rolename!}</td>
				<td>${role.bz!}</td>
				<td class="f-14">
					<a title="编辑" href="javascript:;" onClick="role_edit('编辑角色','${rc.contextPath}/role/createFrom.do?id=${role.id}','800','500')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> 
					<a title="删除" href="javascript:;" onClick="deleteRole('${role.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
				</td>
			</tr>
	   </#list>
		</tbody>
	</table>
	
</div>
<script type="text/javascript">

//角色添加
function role_add(title,url,w,h) {
	layer_show(title,url,w,h);
}

/*角色-编辑*/
function role_edit(title,url,w,h){
	layer_show(title,url,w,h);
}

/*角色-删除*/
function deleteRole(id){
	layer.confirm('确认要删除吗？',function(index){
		window.location.href = "${rc.contextPath}/role/delete.do?id="+id;
	});
}


</script>
</body>
</html>