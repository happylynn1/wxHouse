<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>我的客户</title>
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
	<script type="text/javascript" src="${path}res/js/myClient.js?v=${ver}"></script>
	<style>
		.czB{ border: none; background: #e6e4e3;}
		.advisorList{ overflow:hidden;}
		.advisorList li{width:123%;}
		.advisorList li .items{overflow:hidden;-webkit-transition:all 0.3s linear;transition:all 0.3s linear;width:100%;}
		.advisorList li .orl{float:left;width: 81.3%;border-bottom: 1px solid #d9d9d9;}
		.advisorList li .advisor_Photo{float:left;width: 50%;}
		.advisorList li .adviso_rIntr {float:left;display:inline-block;width:50%;}
		.advisorList li .deal{height:100%;float:right;width:14%;}
		.advisorList li .deal span{width:100%;display:inline-block;vertical-align:middle;text-align:center;color:#fff;}
		.advisorList li .del{background:#FA8955;height:3.2rem;padding-top: 1.5rem;}
		.swipeleft{transform:translateX(-14%);-webkit-transform:translateX(-14%);}
	</style>
</head>
<body>
<section>
    <ul class="advisorList clearfix"  style="-webkit-overflow-scrolling: touch;">
    </ul>
    <div class="my-alert" style="display:none;">
		<div class="alert">
			<div class="content"><h3>确认删除？</h3><br/><h5>有新聊天时该联系人仍会显示出来。</h5></div>
			<div class="fd-btn">
				<a href="javascript:;" class="cancel">取消</a>
				<a href="javascript:;" class="confirm">删除</a>
			</div>
		</div>
		<div class="toast"></div>
	</div>
    <div class="noData" style="display:none;"><img src="${path}res/images/noData.png"/></div>
</section>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		myClient.init();
	});
</script>
</html>