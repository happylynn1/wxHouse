<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>预约看房</title>
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
	<script type="text/javascript" src="${path}res/js/lookHouseList.js?v=${ver}"></script>
	<style>
		.advisorList2{ overflow:hidden;}
		.advisorList2 li{width:126%;}
		.advisorList2 li .items{display:inline-block;overflow:hidden;-webkit-transition:all 0.3s linear;transition:all 0.3s linear;width:100%;}
		.advisorList2 li .yyvisorIntr,.advisorList2 li .yyvisorIntr2{margin-right:5%;width:79.3%;float:left;}
		.advisorList2 li .deal{height:5.5rem;float:right;width:15.7%;height: 5.5rem;line-height:5.5rem; font-size: .75rem;text-align: center;background: #fb7330;}
		.advisorList2 li .deal span{margin: 0 auto;width:100%;vertical-align:middle;text-align:center;color:#fff;}
		.advisorList2 li .del{height:2.4rem}
		.advisorList2 li .cancel{display: inline-block;line-height:1.2rem}
		.swipeleft{transform:translateX(-15.7%);-webkit-transform:translateX(-15.7%);}
	</style>
</head>
<body>
<section>
    <ul class="advisorList2 clearfix">
    </ul>
</section>
<div class="popTipBoxL3" style="display:none;">
	<!--预约看房-->
	<div class="popTipConL3">
	     <div class="yykf_tic3">
			<span class="tc_next3"><img src="${path}res/images/yy_gb.png"></span>
			<h1>取消预约</h1>
			<ul>
				<li><textarea rows="4" cols="50" name="introduce" id="introduce" onKeyDown="lookHouseList.getNum(this);" onKeyUp="lookHouseList.getNum(this);" placeholder="请输入取消预约的原因，20字以内" class="txtLc_text2"></textarea> 
				<p><span id="num">0</span>/20</p></li>
			</ul>
			<p><input type="submit" name="button" id="button_xx" value="我再想想" /> <input type="submit" name="button" id="button_qd" value="确定" /></p>
	     </div>
	</div>
</div>
<div class="fix_fd">
<div class="noData" style="display:none;"><img src="${path}res/images/noData.png"/></div>
    <ul class="rentalHouseList clearfix"  style="-webkit-overflow-scrolling: touch;">
    </ul>
</div>
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
<script type="text/javascript">
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		lookHouseList.id="${id}";
		lookHouseList.init();
	});
</script>
</body>
</html>