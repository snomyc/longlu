<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_upload_header.ftl">
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 阿里订单发货管理 <span class="c-gray en">&gt;</span> 订单导入发货 <a class="btn btn-success radius r" id="btn-refresh" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
	<span class="l">
		<button type="button" class="layui-btn" id="importSendExcel">
			  <i class="layui-icon">&#xe67c;</i>上传阿里巴巴订单
		</button>
		<a href="javascript:;" onclick="deleteNoUseAliOrder();" class="btn btn-warning radius"><i class="Hui-iconfont">&#xe634;</i>一键去除无用订单</a>
	</span>
	
	<span class="r">共有数据：<strong>${total!}</strong> 条</span> </div>
	
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="7">阿里巴巴商品管理</th>
			</tr>
			<tr class="text-c">
				<th width="10%">订单编号</th>
				<th width="7%">收货人姓名</th>
				<th width="7%">收货人手机</th>
				<th width="25%">收货人地址</th>
				<th width="7%">快递公司</th>
				<th width="10%">快递单号</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
		<#list aliSendList! as ali>
			<tr class="text-c">
				<td>${ali.orderCode!}</td>
				<td>${ali.receiverName!}</td>
				<td>${ali.receiverMobile!}</td>
				<td>${ali.receiverAddress!}</td>
				<td>${ali.expressCompany!}</td>
				<td>${ali.expressNumber!}</td>
				<td class="f-14">
					<a class="btn btn-success radius" href="javascript:;" onClick="user_edit('阿里订单发货','${rc.contextPath}/ali/aliSendFrom.do?id=${ali.id}','800','500')">发货</a> 
					<a class="btn btn-danger radius" href="javascript:;" onClick="deleteRecord('${ali.id}')" class="ml-5">删除</a>
				</td>
			</tr>
	   </#list>
		</tbody>
	</table>
	
</div>
<script type="text/javascript">

layui.use('upload', function(){
  var upload = layui.upload;
   
  //执行实例
  var uploadInst = upload.render({
    elem: '#importSendExcel' //绑定元素
    ,url: '${rc.contextPath}/ali/upload.do' //上传接口
    ,accept: 'file' //普通文件
    ,done: function(res){
      //上传完毕回调
      window.location.href = "${rc.contextPath}/ali/aliSendList.do";
    }
    ,error: function(){
      //请求异常回调
    }
  });
});

function user_edit(title,url,w,h){
	layer_show(title,url,w,h);
}

function deleteRecord(id){
	layer.confirm('确认要删除吗？',function(index){
		window.location.href = "${rc.contextPath}/ali/deleteAliSend.do?id="+id;
	});
}

function deleteNoUseAliOrder() {
	var index = layer.load(2, {shade: false});
	//ajax更新审核状态
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/ali/deleteNoUseAliOrder.do",
		dataType:"json",
		success: function(json){
			layer.close(index);
			var flag = json.success;
			if(flag) {
				window.location.href = "${rc.contextPath}/ali/aliSendList.do";
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