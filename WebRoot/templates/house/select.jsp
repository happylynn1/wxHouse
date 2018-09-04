<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>选择房源类型</title>
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
	<script type="text/javascript" src="${path}res/js/select.js?v=${ver}"></script>
</head>
<body>
<section>
	<div class="tabBox">
	    <div title="我要出租" id="rent" class="rentA">
	        <span></span>
	        <p>我要出租</p>
	    </div>
	</div>
	<div class="tabBox">
	    <div title="我要出租" id="sale" class="saleA">
	        <span></span>
	        <p>我要出售</p>
	    </div>
	</div>
</section>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		sel.init();
	});
</script>
</html>