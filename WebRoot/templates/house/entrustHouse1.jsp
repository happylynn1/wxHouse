<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>更改图片视频</title>
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
	<link rel="stylesheet" href="${path}res/css/style_progress.css?v=${ver}"/>	
	<script type="text/javascript" src="${path}res/js/jquery.js?v=${ver}"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/ext.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/entrustHouse1.js?v=${ver}"></script>
</head>
<body style="background:#f0eff5;">
<section>
	<h4 class="tilStyle clear">
		<span class="fleft">上传图片</span>
		<b class="fright"></b>
	</h4>
	<ul class="huxingtu clearfix">
	</ul>
	<div class="sp_2018">
		<h4 class="tilStyle_sp clear">
		   <span class="fleft">上传视频</span>
		   <b class="vright">0/6</b>
	    </h4>
	    <ul class="huxing_sp clearfix">
	    </ul>
	</div>
	<form id="upload" enctype="multipart/form-data" method="post" style="display:none">
	    <input type='file' name="file" accept="video/*" id="video_upload">
	</form>
	<a class="sureModifyBtn" id="entrustHouse2" >设置封面图</a>
</section>
<div id="progressbar">
    <div id='progress'></div>
    <p></p>
</div>
<div id="bg_gray"></div>
<!--弹出框-over-->
<div class="popTipBox1" style="display:none;">
	 <div class="popTip90"></div>
</div>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		entrustHouse1.houseid="${houseid}";
		entrustHouse1.clientopenid="${clientopenid}";
		entrustHouse1.init();
	});
</script>
</html>