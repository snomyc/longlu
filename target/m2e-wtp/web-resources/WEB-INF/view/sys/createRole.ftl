<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<#include "../public/pub_validate.ftl">
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form-role-add" method="post" action="${rc.contextPath}/role/create.do">
	<input type="hidden" class="input-text" id="id" name="id">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="rolename" name="rolename">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">描述：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea name="bz" id="bz" cols="" rows="" class="textarea"  placeholder="说点什么...100个字符以内" dragonfly="true" onKeyUp="textarealength(this,100)"></textarea>
			<p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
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
	
	$('#id').val('${role?if_exists.id!}');
	$('#rolename').val('${role?if_exists.rolename!}');
	$('#bz').val('${role?if_exists.bz!}');

	$("#form-role-add").validate({
		rules:{
			rolename:{
				required:true,
				maxlength:20
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			//如果提示ajaxSubmit is not function 则没有导入jquery-form.js
			$(form).ajaxSubmit();
			parent.location.reload();
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
	});
});

</script> 
</body>
</html>