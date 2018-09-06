<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>取消预约</title>
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
	<link rel="stylesheet" href="${path}res/css/style_main.css?v=${ver}"/>
	<script type="text/javascript" src="${path}res/js/jquery.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/ext.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/lookHouseInfo.js?v=${ver}"></script>
</head>
<body>
<section>
	<div class="reservation">
		<h1 id="title"></h1>
		<ul>
			<li id='houseInfo'></li>
			<li id='time'></li>
			<li id='reason'></li>
		</ul>
		<p>您可以拨打对方电话了解详情</p>
		<div class="animate-bounce-up"></div>
		<span class="reservation_span">
			<a id="phone" class="rest_input">拨打电话
			</a>
		</span>
	</div>
</section>
<script type="text/javascript">
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		lookHouseInfo.id="${id}";
		lookHouseInfo.init();
	});
</script>
</body>
</html>