<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>微名片</title>
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
	<script type="text/javascript" src="${path}res/js/wxCard.js?v=${ver}"></script>
</head>
<body>
<section>
	<div class="cardImgBox">
		 <div><img id="head_image" src="" /></div>
		 <p id="name" class="nameP"></p>
		 <p id="phone" class="telP"></p>
	</div>
	<ul class="expertBox clearOv">
		<li><label>所在门店：</label><span id="storeName">北京神鹰城讯</span></li>
		<li><label>擅长技能：</label><span id="speciality">具备团队管理工作经验。</span></li>
		<li><label>从业年限：</label><span id="workperiod">10年</span></li>
	</ul>
	<div class="codeBox">
		 <img id="qrcode" src="" />
		 <p>扫一扫,关注我~</p>
	</div>
</section>
</body>
<script>
$(function(){
	ext.setvars("${path}","${storeId}", "${openid}");
	wxCard.init();
});
</script>
</html>