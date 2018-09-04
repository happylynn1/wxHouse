<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>处理求购需求</title>
    <link rel="shortcut icon" href="${path}res/${path}res/images/favicon.ico" type="image/x-icon" />
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
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/ext.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/dealSaleRequire.js?v=${ver}"></script>
	<style>
		.error::-webkit-input-placeholder{color:red;}
	</style>
</head>
<body style="background:#f0eff5;">
<section>
	<div class="liTips_1">房源信息</div>
	<ul class="editIntrotext clearfix" style="margin-top: 0;border-top: 1px solid #c8c8c8;">
		<li>
			<label class="labell_1">城  市：</label><span id="address_span">北京海淀区五道口</span>
		</li>
		<li>
		    <label class="labell_floor2">用  途：</label><span id="usedto_span">住宅</span>
		</li>
		<li>
			<label class="labell_3">装  修：</label><span id="decorate_span">精装</span>
		</li>
		<li>
			<label class="labell_5">售  价：</label><span id="price_span">2600元</span>
		</li>
		<li>
			<label class="labell_6">户  型：</label><span id="room_span">3室1厅1卫</span>
		</li>
		<li>
			<label class="labell_7">面  积：</label><span id="area_span">20平米</span>
		</li>
	</ul>
	<div class="liTips_1">其他描述</div>
	<ul class="editIntrotext2 clearfix" style="margin-top: 0;border-top: 1px solid #c8c8c8;">
		<li>
			想要靠近地铁的房子，并且有大阳台，光线好，家具齐全
		</li>
	</ul>
		<div class="liTips_1">个人信息</div>
	<ul class="editIntrotext clearfix" style="margin-top: 0;border-top: 1px solid #c8c8c8;">
		<li>
			<label class="labell_1">姓  名：</label><span id="name_span">尹林</span>
		</li>
		<li>
			<label class="labell_2">电  话：</label><span id="phone_span">13546677767</span>
		</li>
	</ul>
	<a class="sureModifyBtnl">拨打电话</a>
</section>
</body>
<script>
$(function(){
	ext.setvars("${path}", "${storeId}", "${openid}");
	dealSaleRequire.vars.requireid = "${requireid}";
	dealSaleRequire.init();
});
</script>
</html>