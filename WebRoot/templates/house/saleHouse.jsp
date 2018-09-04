<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>出售列表</title>
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
	<script type="text/javascript" src="${path}res/js/saleHouse.js?v=${ver}"></script>
</head>
<body>
<section>
<div class="fox_fixt">
	<div class="SouBox">
         <span class="SoBox"><h2>二手房</h2><i class="icon_trbox"></i></span>
         <label><form action="javascript:saleHouse.setKeys()"><input type="text" class="input" placeholder="搜索">
         <i></i></form></label>
    </div>
    <!--筛选条-->
	<ul class="tab_bar fd_ bar clearfix">
	    <li class="room"><h2 class="tit">居室</h2><i class="icon_triangle"></i></li>
	    <li class="price"><h2 class="tit">价格</h2><i class="icon_triangle"></i></li>
	    <li class="towards"><h2 class="tit">朝向</h2><i class="icon_triangle"></i></li>
	</ul>
	  <!--筛选条-->
	<ul class="SoCon" style="display:none;">
    	<li data-name="secondh">二手房</li>
    	<li data-name="recommend" >急售房</li>
    	<li data-name="new" >新房</li>
	</ul>
	<!--房型筛选begin-->
	<ul id ="room" class="sortCon"  style="display:none;">
	    <li data-name="" class="activeOn">全部</li>
	    <li data-name="1" class="">一室</li>
	    <li data-name="2" class="">二室</li>
	    <li data-name="3" class="">三室</li>
	    <li data-name="4" class="">四室</li>
	    <li data-name="5" class="">五室</li>
	    <li data-name="6" class="">其他</li>
	</ul>
	<ul id ="price" class="sortCon"  style="display:none;">
	    <li data-name="" class="activeOn">全部</li>
	    <li data-name="0-200" class="">0-200万</li>
	    <li data-name="200-300" class="">200-300万</li>
	    <li data-name="300-500" class="">300-500万</li>
	    <li data-name="500-800" class="">500-800万</li>
	    <li data-name="800-1000" class="">800-1000万</li>
	    <li data-name="1000-1500" class="">1000-1500万</li>
	    <li data-name="1500-2000" class="">1500-2000万</li>
	    <li data-name="2000-999999" class="">2000万以上</li>
	</ul>
	<ul id ="towards" class="sortCon" style="display:none;">
	    <li data-name="" class="activeOn">全部</li>
	    <li data-name="东南朝向" class="">东南朝向</li>
	    <li data-name="正南朝向" class="">正南朝向</li>
	    <li data-name="南北通透" class="">南北通透</li>
	    <li data-name="西南朝向" class="">西南朝向</li>
	    <li data-name="正西朝向" class="">正西朝向</li>
	    <li data-name="东西通透" class="">东西通透</li>
	    <li data-name="西北朝向" class="">西北朝向</li>
	    <li data-name="正北朝向" class="">正北朝向</li>
	    <li data-name="东北朝向" class="">东北朝向</li>
	    <li data-name="正东朝向" class="">正东朝向</li>
	</ul>
	<!--房型筛选end-->
</div>
	 <!--筛选条-->
<div class="fix_fd">
    <div class="noData" style="display:none;"><img src="${path}res/images/noData.png"/></div>
    <ul class="rentalHouseList clearfix"  style="-webkit-overflow-scrolling: touch;"></ul>
</div>
</section>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		saleHouse.vars.genre="${genre}";
		saleHouse.init();
	});
</script>
</html>