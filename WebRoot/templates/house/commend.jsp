<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>推荐房源</title>
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
	<script type="text/javascript" src="${path}res/js/commend.js?v=${ver}"></script>
	<style>
	.czB{ border: none; background: #e6e4e3; }
	</style>
</head>
<body>
<section>
	<!--筛选条-->
	<ul class="tab_bar czB clearfix">
	    <li class="tabActive"><h2 class="tit" name="rent">出租房源</h2></li>
	    <li><h2 class="tit"  name="sale">出售房源</h2></li>
	</ul>
    <!--筛选条-->
    <ul class="rentalHouseList clearfix"  style="-webkit-overflow-scrolling: touch;">
    </ul>
    <div class="noData" style="display:none;"><img src="${path}res/images/noData.png"/></div>
</section>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		commend.init();
	});
</script>
</html>