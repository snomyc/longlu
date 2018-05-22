<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "public/pub_header.ftl">
<body style="overflow:hidden;">
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="${rc.contextPath}/main.do">有赞店铺业务平台</a><span class="logo navbar-slogan f-l mr-10 hidden-xs">深圳龙路</span> <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
			<nav class="nav navbar-nav">
				<!--<ul class="cl">
					<li class="dropDown dropDown_hover"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 新增 <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" onclick="article_add('添加资讯','article-add.html')"><i class="Hui-iconfont">&#xe616;</i> 资讯</a></li>
							<li><a href="javascript:;" onclick="picture_add('添加资讯','picture-add.html')"><i class="Hui-iconfont">&#xe613;</i> 图片</a></li>
							<li><a href="javascript:;" onclick="product_add('添加资讯','product-add.html')"><i class="Hui-iconfont">&#xe620;</i> 产品</a></li>
							<li><a href="javascript:;" onclick="member_add('添加用户','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 用户</a></li>
						</ul>
					</li>
				</ul>-->
			</nav>
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar ">
				<ul class="cl">
					<li></li>
					<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A">欢迎您 <@shiro.principal/><i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" onClick="revise_password('修改密码','${rc.contextPath}/users/revisePassword.do','430','250')">修改密码</a></li>
							<li><a href="${rc.contextPath}/logout.do">退出</a></li>
						</ul>
					</li>
					<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
							<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
							<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
							<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
							<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
							<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</header>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<@shiro.hasAnyRoles name="有赞,管理员">
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i>&nbsp;订单管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${rc.contextPath}/trade/paymentOrders.do" data-title="订单流程跟踪" href="javascript:void(0)">订单流程跟踪</a></li>
					<!--<li><a _href="${rc.contextPath}/trade/expessOrders.do" data-title="订单快递录入" href="javascript:void(0)">订单快递录入</a></li>-->
					<li><a _href="${rc.contextPath}/trade/paymentSuccessOrders.do" data-title="交易完成订单" href="javascript:void(0)">交易完成订单</a></li>
				</ul>
			</dd>
		</dl>
		</@shiro.hasAnyRoles >
		<@shiro.hasAnyRoles name="有赞,管理员">
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i>&nbsp;商品管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${rc.contextPath}/goods/saleGoodsInfo.do" data-title="商品上架管理" href="javascript:void(0)">商品上架管理</a></li>
					<li><a _href="${rc.contextPath}/ali/list.do" data-title="阿里巴巴商品管理" href="javascript:void(0)">阿里巴巴商品管理</a></li>
				</ul>
			</dd>
		</dl>
		</@shiro.hasAnyRoles >
		<@shiro.hasAnyRoles  name="财务,管理员,财务-仓库">  
		<dl id="menu-bill">
			<dt><i class="Hui-iconfont">&#xe616;</i>&nbsp;财务管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${rc.contextPath}/finance/financeOrderReview.do" data-title="订单审核" href="javascript:void(0)">订单审核</a></li>
					<!--<li><a _href="" data-title="财务管理" href="javascript:void(0)">财务对账</a></li>
					<li><a _href="" data-title="财务管理" href="javascript:void(0)">退货审核</a></li>-->
				</ul>
			</dd>
		</dl>
		</@shiro.hasAnyRoles >
		<@shiro.hasAnyRoles  name="仓库,管理员,财务-仓库">
		<dl id="menu-depot">
			<dt><i class="Hui-iconfont">&#xe616;</i>&nbsp;仓库管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${rc.contextPath}/deport/deportOrders.do" data-title="仓库管理" href="javascript:void(0)">待发货订单</a></li>
				</ul>
			</dd>
		</dl>
		</@shiro.hasAnyRoles >
		<@shiro.hasAnyRoles name="有赞,管理员">
		<dl id="menu-ali-export">
			<dt><i class="Hui-iconfont">&#xe616;</i>&nbsp;阿里订单发货管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${rc.contextPath}/ali/aliSendList.do" data-title="订单导入发货" href="javascript:void(0)">订单导入发货</a></li>
					<li><a _href="${rc.contextPath}/express/list.do" data-title="有赞-阿里物流管理" href="javascript:void(0)">有赞-阿里物流管理</a></li>
				</ul>
			</dd>
		</dl>
		</@shiro.hasAnyRoles >
		<!--<dl id="menu-picture">
			<dt><i class="Hui-iconfont">&#xe613;</i>&nbsp;图片管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="picture-list.html" data-title="图片管理" href="javascript:void(0)">图片管理</a></li>
				</ul>
			</dd>
		</dl>-->
		<@shiro.hasAnyRoles  name="管理员">
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				<dl id="menu-admin">
					<dt><i class="Hui-iconfont">&#xe62d;</i> 管理员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<li><a _href="${rc.contextPath}/users/list.do" data-title="用户管理" href="javascript:void(0)">用户管理</a></li>
							<li><a _href="${rc.contextPath}/role/list.do" data-title="角色管理" href="javascript:void(0)">角色管理</a></li>
							<li><a _href="${rc.contextPath}/config/list.do" data-title="有赞店铺配置" href="javascript:void(0)">有赞店铺配置</a></li>
							<!--<li><a _href="admin-permission.html" data-title="权限管理" href="javascript:void(0)">权限管理</a></li>
							<li><a _href="admin-permission.html" data-title="部门管理" href="javascript:void(0)">部门管理</a></li>-->
						</ul>
					</dd>
				<!--</dl>
					<li><a _href="system-base.html" data-title="系统设置" href="javascript:void(0)">系统设置</a></li>
					<li><a _href="system-category.html" data-title="栏目管理" href="javascript:void(0)">栏目管理</a></li>
					<li><a _href="system-data.html" data-title="数据字典" href="javascript:void(0)">数据字典</a></li>
					<li><a _href="system-log.html" data-title="系统日志" href="javascript:void(0)">系统日志</a></li>
				</ul>-->
			</dd>
		</dl>
		</@shiro.hasAnyRoles >
	</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span>工作台</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="${rc.contextPath}/home.do"></iframe>
		</div>
	</div>
</section>
<script type="text/javascript">
/*用户-修改密码*/
function revise_password(title,url,w,h){
	layer_show(title,url,w,h);
}

/*资讯-添加*/
function article_add(title,url){
	alert(222);
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*图片-添加*/
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*产品-添加*/
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
</script> 
</body>
</html>