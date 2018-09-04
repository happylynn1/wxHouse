<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>我的房源</title>
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
	<script type="text/javascript" src="${path}res/js/myHouse.js?v=${ver}"></script>
	<style>
		.czB{ border: none; background: #e6e4e3;}
		.rentalHouseList{ overflow:hidden;}
		.rentalHouseList li{width:160%;border-bottom:1px solid #ddd;padding: 1rem 0;}
		.rentalHouseList li dl{display:inline-block;}
		.rentalHouseList li .items{overflow:hidden;-webkit-transition:all 0.3s linear;transition:all 0.3s linear;width:100%;}
		.rentalHouseList li .houseImg{float:left;width: 19%;}
		.rentalHouseList li .DlHouseIntr {width:43%;}
		.rentalHouseList li .deal{height:100%;float:right;width:32%;}
		.rentalHouseList li .deal span{width:33.3%;display:inline-block;vertical-align:middle;text-align:center;color:#fff;}
		.rentalHouseList li .upPic{float:left;background:#8BC34A;height:3.7rem;padding-top: 1rem;}
		.rentalHouseList li .update{float:left;background:#B7D390;height:3.2rem;padding-top: 1.5rem;}
		.rentalHouseList li .del{background:#FA8955;height:3.2rem;padding-top: 1.5rem;}
		.swipeleft{transform:translateX(-35%);-webkit-transform:translateX(-35%);}
	</style>
</head>
<body>
<section>
	<!--筛选条-->
	<ul class="tab_bar czB clearfix">
	    <li class="tabActive"><h2 class="tit"  name="sale">出售房源</h2></li>
	    <li><h2 class="tit" name="rent">出租房源</h2></li>
	</ul>
    <!--筛选条-->
    <ul class="rentalHouseList clearfix"  style="-webkit-overflow-scrolling: touch;">
    </ul>
    <div class="noData" style="display:none;"><img src="${path}res/images/noData.png"/></div>
</section>
<div class="my-alert" style="display:none;">
	<div class="alert">
		<div class="content">确认要删除该房源？</div>
		<div class="fd-btn">
			<a href="javascript:;" class="cancel">取消</a>
			<a href="javascript:;" class="confirm">删除</a>
		</div>
	</div>
	<div class="toast"></div>
</div>
<!--弹出框-over-->
<div class="popTipBox1" style="display:none;">
	 <div class="popTip90"></div>
</div>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		myHouse.init();
	});
</script>
</html>