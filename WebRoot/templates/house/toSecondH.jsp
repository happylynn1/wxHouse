<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>精品二手房</title>
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
	<script type="text/javascript" src="${path}res/js/toSecondH.js?v=${ver}"></script>
</head>
<body>
<section>
	<div class="souosu_index">
		<span><img src="${path}res/images/sou_ban.png"></span>
		<p class="clearfix SsIndex"><input type="text" name="" onfocus="toSecondH.setKeys()" placeholder="请输入小区名称" class="next_text_1"><i></i></p>
	</div>
    <!--导航-->
	<ul class="poBox clearfix">
	    <li>
	    	<a href="javascript:;">
	    		<p data-name="toRentSale.do" class="list-1"><img src="${path}res/images/sou_ioc_1.png"></p>
	    		<p>房屋租售</p>
	    	</a>
	    </li>
	    <li>
	    	<a href="javascript:;">
	    		<p data-name="toRenting.do" class="list-1"><img src="${path}res/images/sou_ioc_2.png"></p>
	    		<p>我要租房</p>
	    	</a>
	    </li>
	    <li>
	    	<a href="javascript:;">
	    		<p data-name="toBuy.do" class="list-1"><img src="${path}res/images/sou_ioc_3.png"></p>
	    		<p>我要买房</p>
	    	</a>
	    </li>
	    <li>
	    	<a href="javascript:;">
	    		<p data-name="sellr.do?type=rent" class="list-1"><img src="${path}res/images/sou_ioc_4.png"></p>
	    		<p>我要出租</p>
	    	</a>
	    </li>
	    <li>
	    	<a href="javascript:;">
	    		<p data-name="sellr.do?type=sale" class="list-1"><img src="${path}res/images/sou_ioc_5.png"></p>
	    		<p>我要卖房</p>
	    	</a>
	    </li>
	    <li>
	    	<a href="javascript:;">
	    		<p data-name="toMyHouse.do" class="list-1"><img src="${path}res/images/sou_ioc_6.png"></p>
	    		<p>我的房源</p>
	    	</a>
	    </li>
	</ul>
    <!--导航-->
    <h4 class="tilStyle">精品二手房</h4>
    <div class="noData" style="display:none;"><img src="${path}res/images/noData.png"/></div>
    <ul class="rentalHouseList clearfix"  style="-webkit-overflow-scrolling: touch;"></ul>
</section>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		toSecondH.init();
	});
</script>
</html>