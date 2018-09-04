<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>求购登记</title>
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
	<script type="text/javascript" src="${path}res/js/toBuy1.js?v=${ver}"></script>
	<style>
		.error::-webkit-input-placeholder{color:red;}
		.noscroll{height:100%;overflow:hidden;}
	</style>
</head>
<body style="background:#f0eff5;">
<section>
	<div class="liTips_1">房源信息</div>
	<ul class="editIntrotext clearfix" style="margin-top: 0;border-top: 1px solid #c8c8c8;">
		<li>
			<label class="labell_1">城  市</label>
			<input class="" id="city_input" placeholder="请选择期望城市" />
			<span class="xzxqIconl" id="city_span"><img src="${path}res/images/left_ioc_1.png"></span>
		</li>
		<li>
			<label class="labell_2">区  域</label>
			<input class="" id="district_input" placeholder="请选择期望区域" />
			<span class="xzxqIconl" id="district_span"><img src="${path}res/images/left_ioc_1.png"></span>
		</li>
		<li>
		    <label class="labell_floor1">街  道</label>
		    <input class="" id="street_input" placeholder="请选择期望街道" />
		    <span class="xzxqIconl" id="street_span"><img src="${path}res/images/left_ioc_1.png"></span>
		</li>
		<li>
		    <label class="labell_floor2">用  途</label>
		    <input class="" id="usedto_input" placeholder="请选择用途" />
		    <span class="xzxqIconl" id="usedto_span"><img src="${path}res/images/left_ioc_1.png"></span>
		</li>
		<li>
			<label class="labell_3">装  修</label>
			<input class="" id="decorate_input" placeholder="请选择装修情况" />
            <span class="xzxqIconl" id="decorate_span"><img src="${path}res/images/left_ioc_1.png"></span>
		</li>
		<li>
			<label class="labell_5">售  价</label>
			<input class="" id="price_input" type='number' placeholder="请输入房屋售价，单位：万元" />
		</li>
		<li>
			<label class="labell_4">户  型</label>
			<div class="flexBoxl clearOvl">
				 <input id="shi_input" type='number' placeholder="几室" class="txtLc1"/>
	             <input id="ting_input" type='number' placeholder="几厅" class="txtLc1"/>
	             <input id="wei_input" type='number' placeholder="几卫" class="txtLc2"/>
            </div>
		</li>
		<li>
			<label class="labell_5">面  积</label>
			<input class="" id="area_input" type='number' placeholder="请输入面积，单位：平米" />
		</li>
	</ul>
	<div class="liTips_1">其他描述</div>
	<ul class="editIntrotext2 clearfix" style="margin-top: 0;border-top: 1px solid #c8c8c8;">
		<li>
		<textarea rows="4" cols="50" id="remark_area" placeholder="请输入其他描述" class="txtLc_text"></textarea>
		</li>
	</ul>
		<div class="liTips_1">个人信息</div>
	<ul class="editIntrotext clearfix" style="margin-top: 0;border-top: 1px solid #c8c8c8;">
		<li>
			<label class="labell_1">姓  名</label>
			<input class="" id="name_input" placeholder="请输入姓名" />
		</li>
		<li>
			<label class="labell_2">电  话</label>
			<input class="" id="phone_input" placeholder="请输入手机号码" />
		</li>
	</ul>
	<a class="sureModifyBtnl">提交委托</a>
</section>
<!--弹出框-Begin-->
<div class="popTipBoxL" style="display:none;">
	<!--期望城市-->
	<div class="popTipConL qitaBoxL" id="city_div" style="display: none;">
	     <h4>
	        <b>期望城市</b>
	     </h4>
	     <div class="intrChoListL">
			<ul>
				<li data-id="110000">北京</li>
				<li data-id="310000">上海</li>
				<li data-id="120000">天津</li>
				<li data-id="500000">重庆</li>
				<li data-id="440100">广州</li>
				<li data-id="440300">深圳</li>
				<li data-id="510100">成都</li>
				<li data-id="320100">南京</li>
				<li data-id="420100">武汉</li>
				<li data-id="370100">济南</li>
				<li data-id="330100">杭州</li>
				<li data-id="530100">昆明</li>
				<li data-id="320500">苏州</li>
				<li data-id="520100">贵阳</li>
				<li data-id="210200">大连</li>
			</ul>
	     </div>
	</div>
	<!--期望区域-->
	<div class="popTipConL qitaBoxL" id="district_div" style="display:none;">
	     <h4>	      
	        <b>期望区域</b>
	     </h4>
	     <div class="intrChoListL">
			<ul>
			</ul>
	     </div>
	</div>
	<!--期望街道-->
	<div class="popTipConL qitaBoxL" id="street_div" style="display:none;">
	     <h4>
	        <b>期望街道</b>
	     </h4>
	     <div class="intrChoListL">
			<ul>				
			</ul>
	     </div>
	</div>
	<!--选择用途-->
	<div class="popTipConL qitaBoxL" id="usedto_div" style="display:none;">
	     <h4>
	        <b>选择用途</b>
	     </h4>
	     <div class="intrChoListL">
			<ul>
				<li>住宅</li>
				<li>别墅</li>
				<li>商铺</li>
				<li>写字楼</li>
				<li>其他</li>
			</ul>
	     </div>
	</div>
	<!--选择装修-->
	<div class="popTipConL qitaBoxL" id="decorate_div" style="display:none;">
	     <h4>
	        <b>选择装修</b>
	     </h4>
	     <div class="intrChoListL">
			<ul>
				<li>毛坯</li>
				<li>简装</li>
				<li>精装</li>
				<li>豪华</li>
				<li>中装</li>
			</ul>
	     </div>
	</div>
	
</div>
<!--弹出框-over-->
<div class="popTipBox1" style="display:none;">
    <div class="popTip90"></div>
</div>
</body>
<script>
$(function(){
	ext.setvars("${path}","${storeId}", "${openid}");
	toBuy1.init();
});
</script>
</html>