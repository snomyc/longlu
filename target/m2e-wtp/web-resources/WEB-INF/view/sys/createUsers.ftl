<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<#include "../public/pub_validate.ftl">
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form-users-add" method="post" action="${rc.contextPath}/users/create.do">
	<input type="hidden" class="input-text" id="id" name="id">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="username" name="username">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>姓名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="xingming" name="xingming">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="sex" type="radio" id="sex-1" value="男" checked>
				<label for="sex-1">男</label>
			</div>
			<div class="radio-box">
				<input type="radio" id="sex-2" name="sex" value="女">
				<label for="sex-2">女</label>
			</div>
		</div>
	</div>
	<div class="row cl">
    	<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>供应商：</label>
        <div class="formControls col-xs-8 col-sm-9">
      		<select class="easyui-combobox" name="f0" id="f0" multiple="true" required="true" labelPosition="top" style="width:100%;">
				<option value="无">无</option>
				<#list supplierList! as supplier>
					<option value="${supplier.supplierName!}">${supplier.supplierName!}</option>
				</#list>
			</select>
    	</div>
  	</div>
  	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色：</label>
		<div class="formControls col-xs-8 col-sm-9"> 
			<select class="easyui-combobox" name="roleid" id="roleid" required="true" labelPosition="top" style="width:100%;">
				<#list roleList! as role>
					<option value="${role.id!}">${role.rolename!}</option>
				</#list>
			</select> 
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>是否邮件推送：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<select class="easyui-combobox" name="f1" id="f1" required="true" labelPosition="top" style="width:100%;">
				<option value="1">是</option>
				<option value="0">否</option>
			</select> 
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>邮箱：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" placeholder="@" name="email" id="email">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>联系电话：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" placeholder="" id="phone" name="phone">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">座机电话：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" placeholder="" id="telphone" name="telphone">
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
	
	$('#id').val('${users?if_exists.id!}');
	var id = $('#id').val();
	if(id != null && id != '') {
		$('#username').val('${users?if_exists.username!}');
		$('#xingming').val('${users?if_exists.xingming!}');
		$('#email').val('${users?if_exists.email!}');
		$('#phone').val('${users?if_exists.phone!}');
		$('#telphone').val('${users?if_exists.telphone!}');
		$("#roleid").combobox('setValue','${users?if_exists.roleid!}');
		$("#f0").combobox('setValues','${users?if_exists.f0!}');
		$("#f1").combobox('setValue','${users?if_exists.f1!}');
		var sex = '${users?if_exists.sex!}';
    	var rs = document.getElementsByName("sex");
    	for(var i =0;i<rs.length;i++){
       	var r =rs[i];
        	if(r.value == sex){
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
			username:{
				required:true,
				minlength:4,
				maxlength:50
			},
			xingming:{
				required:true
			},
			sex:{
				required:true
			},
			phone:{
				required:true,
				isPhone:true
			},
			email:{
				required:true,
				email:true
			}
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			//调用
			//form.submit();
			//userSubmit();
			//如果提示ajaxSubmit is not function 则没有导入jquery-form.js
			$(form).ajaxSubmit();
			parent.location.reload();
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
	});
});

function userSubmit() {
	$.ajax({
		type:'POST',
		url: "${rc.contextPath}/users/create.do",
		data:{username:'admin'},
		dataType:"json",
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

</script> 
</body>
</html>