<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#include "public/pub_header.ftl">
<body>
<div class="page-container">
	<table class="table table-border table-bordered table-bg mt-20">
		<thead>
			<tr>
				<th colspan="2" scope="col">我的工作台</th>
			</tr>
		</thead>
		<tbody>
			<#if financeCount?? >
				<tr>
					<th width="30%">财务待审核订单</th>
					<td class="text-c"><span id="lbServerName">
						<a href="${rc.contextPath}/finance/financeOrderReview.do">${financeCount}</a>
					</span></td>
				</tr>
			</#if>
			<#if depotCount?? >
				<tr>
					<th width="30%">仓库待审核发货订单</th>
					<td class="text-c"><span id="lbServerName">
					<a href="${rc.contextPath}/deport/deportOrders.do">${depotCount}</a>
					</span></td>
				</tr>
			</#if>
			<#if marketCount?? >
				<tr>
					<th width="30%">有赞待录入订单</th>
					<td class="text-c"><span id="lbServerName">
					<a href="${rc.contextPath}/trade/expessOrders.do">${marketCount}</a>
					</span></td>
				</tr>
			</#if>
		</tbody>
	</table>
</div>

<footer class="footer mt-20">
	<div class="container">
		<p>本后台系统由<a href="http://my.easychannel.com.cn/" target="_blank">深圳龙路福利内购平台</a>提供技术支持</p>
	</div>
</footer>
</body>
</html>