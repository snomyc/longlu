<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "../public/pub_header.ftl">
<#include "../public/pub_validate.ftl">
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" id="form-users-add" method="post" action="${rc.contextPath}/ali/create.do">
	<input type="hidden" class="input-text" id="id" name="id">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>商品编码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="outerCode" name="outerCode">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>商品名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="goodsName" name="goodsName">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>商品成本：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="cost" name="cost">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>供应商：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="supplierName" name="supplierName">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>阿里巴巴商品URL：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="alibabaUrl" name="alibabaUrl">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red"></span>备注：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="" id="bz" name="bz">
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
		$('#outerCode').val('${record?if_exists.outerCode!}');
		$('#goodsName').val('${record?if_exists.goodsName!}');
		$('#cost').val('${record?if_exists.cost!}');
		$('#supplierName').val('${record?if_exists.supplierName!}');
		$('#alibabaUrl').val('${record?if_exists.alibabaUrl!}');
		$('#bz').val('${record?if_exists.bz!}');
    }

	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	$("#form-users-add").validate({
		rules:{
			outerCode:{
				required:true
			},
			cost:{
				required:true
			},
			alibabaUrl:{
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
		url: "${rc.contextPath}/ali/create.do",
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