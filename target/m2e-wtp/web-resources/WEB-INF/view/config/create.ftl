<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<#include "../public/pub_validate.ftl">
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form-users-add" method="post" action="${rc.contextPath}/config/create.do">
	<input type="hidden" class="input-text" id="id" name="id">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>店铺名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="shopName" name="shopName">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>client_id：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="clientId" name="clientId">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>client_secret：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="clientSecret" name="clientSecret">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>token：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="token" name="token">
		</div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>refresh_token：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="refreshToken" name="refreshToken">
		</div>
	</div>
	
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>店铺状态：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="status" type="radio" id="sex-1" value="0" checked>
				<label for="sex-1">停用</label>
			</div>
			<div class="radio-box">
				<input name="status" type="radio" id="sex-2"  value="1">
				<label for="sex-2">启用</label>
			</div>
		</div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>店铺id：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="kdtId" name="kdtId">
		</div>
	</div>
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" id="userSumbit" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>
<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript">
$(function(){
	
	$('#id').val('${record?if_exists.id!}');
	var id = $('#id').val();
	if(id != null && id != '') {
		$('#shopName').val('${record?if_exists.shopName!}');
		$('#clientId').val('${record?if_exists.clientId!}');
		$('#clientSecret').val('${record?if_exists.clientSecret!}');
		$('#token').val('${record?if_exists.token!}');
		$('#kdtId').val('${record?if_exists.kdtId!}');
		$('#refreshToken').val('${record?if_exists.refreshToken!}');
		
		var status = '${record?if_exists.status!}';
    	var rs = document.getElementsByName("status");
    	for(var i =0;i<rs.length;i++){
       	var r =rs[i];
        	if(r.value == status){
            	r.checked=true;
            	break;
        	}
    	}
    }

	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	$("#form-users-add").validate({
		rules:{
			shopName:{
				required:true
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			//调用
			//如果提示ajaxSubmit is not function 则没有导入jquery-form.js
			$(form).ajaxSubmit({
				dataType: "json",
				success: function(json){
					var flag = json.success;
					if(flag) {
						parent.location.reload();
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
					}else {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
						parent.layer.msg(json.msg,{icon: 5,time:2000});
					}
				}
			});
		}
	});
});

function userSubmit() {
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/config/create.do",
		data:{username:'admin'},
		dataType:"json",
		success: function(json){
			alert(1);
			var flag = json.success;
			if(flag) {
				parent.location.reload();
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}else {
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
				parent.layer.msg(json.msg,{icon: 5,time:2000});
			}
		}
	});
}

</script> 
</body>
</html>