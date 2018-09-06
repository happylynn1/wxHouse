<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>房源详情</title>
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
	<link rel="stylexxzsheet" href="${path}res/css/global.css?v=${ver}"/>
	<link rel="stylesheet" href="${path}res/css/style.css?v=${ver}"/>	
	<link rel="stylesheet" href="${path}res/css/zoomify.min.css?v=${ver}"/>	
	<script type="text/javascript" src="${path}res/js/jquery.js?v=${ver}"></script>	
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${ver}"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=3.0&ak=yDZ6vQfcyVdA5RG1HiqQhrHin3zHUw2X"></script>
	<script type="text/javascript" src="${path}res/js/baiduMap.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/ext.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/zoomify.min.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/houseSourceInfo.js?v=${ver}"></script>
	<link rel="stylesheet" href="${path}res/css/FJL.picker.css" type="text/css"></link>
	<style>
	.BMap_noprint.anchorTR,.anchorBL{display:none;}
	.error::-webkit-input-placeholder{color:red;}
	</style>
</head>
<body>
<section>
	<div class="housePicBox clearfix">
	     <div class="icon-rotate">
            <img src="${path}res/images/720deg.png" class="img_lin_app">
            <img src="${path}res/images/finger_x.png">
         </div>
	     <!--轮播图片-->
	     <ul class="picSwitch clearfix">
	     </ul>
	     <!--轮播图片-->
	     <span class="countPic"><b>1</b>/6</span>
	     <!-- 调整为新的入口 -->
	</div>
	<div class="houseTil">
		<div class="houseTilCon">
         <h5 class="houseH5"></h5>
         <div class="markBox clear"></div>
         <div class="text_yykf entrust">预约看房</div>
         </div>
    </div>
    <ul class="keyBox">
         	  <li></li>
         	  <li></li>
         	  <li></li>
         </ul>
    <div class="details" style="border-top:0px;">
        <h2 class="Listing_h2">房源详情</h2>
        <div class="Core_selling">
            <h3 class="Core_h3"><a id="centralnotesBiji" class="editBjBtn fright" style="display:none;"></a>核心卖点</h3>
            <div id='centralnotesBijiUp' style="display:none;"><textArea></textArea></div>
            <dl id='centralnotes' class="clearfix">
            </dl>
        </div>
        <div class="Core_selling">
            <h3 class="Core_h3"><a id="matingnotesBiji" class="editBjBtn fright" style="display:none;"></a>小区配套</h3>
            <div id='matingnotesBijiUp' style="display:none;"><textArea></textArea></div>
            <dl id='matingnotes' class="clearfix">
            </dl>
        </div>
        <div class="Core_selling">
            <h3 class="Core_h3"><a id="biji" class="addBjBtn fright" style="display:none;"></a>经纪人笔记</h3>
            <div id='bijiUp' style="display:none;"><textArea></textArea></div>
            <div class="bijidl">
            </div>
        </div>
    </div>
    <div class="detailsBox1">
    	 <h5 class="tilStyle">房屋说明</h5>
    	 <ul class="explainList clearOv">
    	 </ul>
    </div>
	<ul class="attributeList clearOv">
	 	 <li style="display:none;">
	 	 	<label>放盘时间：</label>
	 	 	<span id="fpsj"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>房屋类型：</label>
	 	 	<span id="fwlx"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>户型：</label>
	 	 	<span id="hx"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>朝向：</label>
	 	 	<span id="cx"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>外景：</label>
	 	 	<span id="wj"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>电梯：</label>
	 	 	<span id="dt"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>装修情况：</label>
	 	 	<span id="zxqk"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>上次装修：</label>
	 	 	<span id="sczx"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>学位情况：</label>
	 	 	<span id="xwqk"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>房产证：</label>
	 	 	<span id="fcz"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>是否唯一：</label>
	 	 	<span id="sfwy"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>业主欠贷：</label>
	 	 	<span id="yzqk"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>看房时间：</label>
	 	 	<span id="kfsj"></span>
	 	 </li>
	 	 <li style="display:none;">
	 	 	<label>看房情况：</label>
	 	 	<span id="kfqk"></span>
	 	 </li>
	</ul>
	<div class="detailsBox1" >
    	 <h5 class="tilStyle">周边配套设施</h5>
    	 <div style="margin-top:1rem;">
    	 	<div id="baiduMap" style="width:100%;height:12rem;" class="">
			</div>
			<ul class="bdptss">
				<li><i class="zb_1"></i><span>地铁</span></li>
				<li><i class="zb_2"></i><span>公交</span></li>
				<li><i class="zb_3"></i><span>学校</span></li>
				<li><i class="zb_4"></i><span>餐饮</span></li>
				<li><i class="zb_5"></i><span>医院</span></li>
				<li><i class="zb_6"></i><span>银行</span></li>
			</ul>
    	 </div>
    </div>
    <div style="margin-bottom:3rem;">
    	 <h5 class="tilStyle">推荐房源</h5>
    	 <div style="margin-top:1rem;">
		    <ul class="rentalHouseList clearfix"  style="-webkit-overflow-scrolling: touch;">
    		</ul>
    		<div class="noData" style="display:none;"><img src="${path}res/images/noData.png"/></div>
    	 </div>
    </div>
	<div class="float_bottom clearfix" style="display:none;">
        <div class="advisor_Photo" style="height: 3rem;line-height: 3rem;">
         	<a><img src="" /><b></b></a>
         </div>
         <div class="advisor_sc">
         <p class="like"><img src=""><br>收藏</p>
         </div>
         <div class="adviso_rIntr">
            <p class="wz_w">
            </p>
        </div>
	</div>
</section>
<div class="popTipBoxL2" style="display: none;">
	<!--预约看房-->
	<div class="popTipConL2">
	     <div class="yykf_tic">
			<span class="tc_next"><img src="${path}/res/images/yy_gb.png"></span>
			<h1>预约看房</h1>
			<ul>
				<li><input id="entName" type="text" class="next_input" placeholder="请输入您的姓名"/></li>
				<li><input id="entPhone" type="number" class="next_input" placeholder="请输入手机号码"/></li>
				<li><button id="entTime" data-options="{&quot;type&quot;:&quot;datetime&quot;}" class="btn mui-btn mui-btn-block">请选择预约时间</button></li>
			</ul>
			<p><input type="submit" name="button" id="button" value="提交约看"></p>
	     </div>
	</div>
</div>
<!--弹出框-over-->
<div class="popTipBox1" style="display:none;">
	 <div class="popTip90"></div>
</div>
<script type="text/javascript" src="${path}res/js/FJL.min.js"></script>
<script type="text/javascript" src="${path}res/js/FJL.picker.min.js"></script>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		houseSourceInfo.houseid="${houseid}";
		houseSourceInfo.city="${store.city}";
		houseSourceInfo.lookHouse="${store.lookHouse}";
		houseSourceInfo.init();
	});
</script>
</html>