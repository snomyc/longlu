<#-- 该模板用于引入 spring标签 和 一些静态文件url路径 -->
<#assign spring=JspTaglibs["http://www.springframework.org/tags"] />
<#-- 图片引用路径url，对应项目目录 /WEB-INF/images/ -->
<#assign imgUrl><@spring.url value="/images"/></#assign>
<#-- 脚本引用url，对应项目目录 /WEB-INF/scripts/ 
<#assign jsUrl><@spring.url value="/js"/></#assign>-->
<#-- 样式表引用url，对应项目目录 /WEB-INF/styles/ 
<#assign cssUrl><@spring.url value="/css"/></#assign>-->
<#-- url根路径
<#assign rootUrl><@spring.url value="/"/></#assign>-->
<#-- 附件根路径 ,对应项目目录 /WEB-INF/attachment/
<#assign viewUrl><@spring.url value="/view"/></#assign> -->
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] />
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"] />