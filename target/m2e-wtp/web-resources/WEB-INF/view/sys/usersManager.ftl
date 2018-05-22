<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 管理员管理 <span class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success radius r" id="btn-refresh" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray"> 
	<span class="l">
		<a href="javascript:;" onclick="user_add('添加用户','${rc.contextPath}/users/createFrom.do','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a>
	</span> 
	<span class="r">共有数据：<strong>${total!}</strong> 条</span> </div>
	
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="9">用户管理(提示:用户初始化密码为123456)</th>
			</tr>
			<tr class="text-c">
				<th width="100">用户名</th>
				<th width="40">姓名</th>
				<th width="40">性别</th>
				<th width="150">Email</th>
				<th width="100">联系电话</th>
				<th width="100">座机电话</th>
				<th width="70">角色</th>
				<th width="80">供应商</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
		<#list usersList! as users>
			<tr class="text-c">
				<td>${users.username!}</td>
				<td>${users.xingming!}</td>
				<td>${users.sex!}</td>
				<td>${users.email!}</td>
				<td>${users.phone!}</td>
				<td>${users.telphone!}</td>
				<td>${users.rolename!}</td>
				<td>${users.f0!}</td>
				<td class="f-14">
					<a title="编辑" href="javascript:;" onClick="user_edit('编辑用户','${rc.contextPath}/users/createFrom.do?id=${users.id}','800','500')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> 
					<a title="删除" href="javascript:;" onClick="deleteUsers('${users.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
					<a title="重置" href="javascript:;" onClick="reset('${users.id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6f7;</i></a>
				</td>
			</tr>
	   </#list>
		</tbody>
	</table>
	
</div>
<script type="text/javascript">

//用户添加
function user_add(title,url,w,h) {
	layer_show(title,url,w,h);
}

/*用户-编辑*/
function user_edit(title,url,w,h){
	layer_show(title,url,w,h);
}

/*用户-删除*/
function deleteUsers(id){
	layer.confirm('确认要删除吗？',function(index){
		window.location.href = "${rc.contextPath}/users/delete.do?id="+id;
	});
}
function reset(id) {
	layer.confirm('要在重置用户密码吗?',function(index){
		window.location.href = "${rc.contextPath}/users/reset.do?id="+id;
	});
}


</script>
</body>
</html>