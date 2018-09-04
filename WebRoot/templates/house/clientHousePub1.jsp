<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>业主房源发布</title>
    <link rel="shortcut icon" href="${path}res/images/favicon.ico" type="image/x-icon" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen">
	<meta content="telephone=no,email=no" name="format-detection">
	<meta name="description" content="">
	<link rel="stylesheet" href="${path}res/css/m-base.css?v=${ver}"/>
	<link rel="stylesheet" href="${path}res/css/style_publish.css?v=${ver}"/>
	<script type="text/javascript" src="${path}res/js/jquery.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/ext.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/clientHousePub1.js?v=${ver}"></script>
	<style>
		.error::-webkit-input-placeholder{color:red;}
	</style>
</head>
<body style="background:#f0eff5;">
<section>
	<ul class="pubIntroList clearfix">
		<li>
			<label>您的称呼：</label>
			<input id="name" class="" placeholder="请输入您的称呼" />
		</li>
		<li>
			<label>您的联系方式：</label>
			<input id="phone" class="" type="number" placeholder="请输入您的联系方式" />
		</li>
		<li>
			<label>其它说明：</label>
			<textarea placeholder="请输入……"></textarea>
		</li>
	</ul>
	<a class="sureModifyBtn">提交委托</a>
</section>
<div class="popTipBox1" style="display:none;">
	 <div class="popTip90"></div>
</div>
</body>
<script>
$(function(){
	ext.setvars("${path}","${storeId}", "${openid}");
	clientHousePub1.vars.type="${type}";
	clientHousePub1.init();
});
</script>
</html>