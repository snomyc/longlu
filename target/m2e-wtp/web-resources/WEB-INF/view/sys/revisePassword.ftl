<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<#include "../public/pub_validate.ftl">
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form-users-password" method="post" action="${rc.contextPath}/users/updatePassword.do">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>请输入新密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" value="" id="password" name="password">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>请确认新密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" value="" id="confirm_password" name="confirm_password">
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

	$("#form-users-password").validate({
		rules:{
			password:{
				required:true,
				minlength:3,
				maxlength:50
			},
			confirm_password:{
				required:true,
				minlength:3,
				maxlength:50,
				equalTo:'#password'
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			//如果提示ajaxSubmit is not function 则没有导入jquery-form.js
			$(form).ajaxSubmit();
			//parent.location.reload();
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
	});
});

</script> 
</body>
</html>