<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>填写房源信息</title>
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
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js?v=${ver}"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=3.0&ak=yDZ6vQfcyVdA5RG1HiqQhrHin3zHUw2X"></script>
	<script type="text/javascript" src="${path}res/js/baiduMap.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/ext.js?v=${ver}"></script>
	<script type="text/javascript" src="${path}res/js/agentHousePub3.js?v=${ver}"></script>
	<style>
		.error::-webkit-input-placeholder{color:red;}
		.BMap_noprint.anchorTR,.anchorBL{display:none;}
	</style>
</head>
<body style="background:#f0eff5;">
<section>
	<div class="liTips">提示：前台不显示具体楼层，请放心填写</div>
	<ul class="editIntroList clearfix" style="margin-top: 0;border-top: 1px solid #f8e7b9;">
		<li>
			<label class="label_1">小区名称</label>
			<input id="comm" class="" placeholder="输入小区名称" />
			<input id="commInput" value="" style="display:none;"/>
			<input id="addressInput" value="" style="display:none;"/>
			<input id="lngInput" value="" style="display:none;"/>
			<input id="latInput" value="" style="display:none;"/>
			<span class="xzxqIcon"></span>
		</li>
		<li style="display:none;height:auto;">
			<div id="baiduMap" style="width:100%;height:12rem;" class="">
			</div>
		</li>
		<li>
			<label class="label_t">推广语</label>
			<input id="tuiguangyu" class="" placeholder="输入14个字以内的推广语" />
		</li>
		<li>
			<label class="label_2">楼栋号</label>
			<input id="buildingNo" class="" placeholder="输入楼栋号" />
		</li>
		<li>
			<label class="label_unit">单元号</label>
			<input id="unitno" class="" placeholder="输入单元号" />
		</li>
		<li>
			<label class="label_door">户号</label>
			<input id="doorno" class="" placeholder="输入户号" />
		</li>
		<li>
			<label class="label_hno">房源编码</label>
			<input id="houseno" class="" placeholder="输入房源编码" />
		</li>
		<li>
		    <label class="label_floor1">楼层</label>
		    <input id="floor" type="number" class="" placeholder="请输入楼层" />
		</li>
		<li>
		    <label class="label_floor2">总楼层</label>
		    <input id="totalfloor"  type="number" class="" placeholder="请输入总楼层" />
		</li>
		<li>
			<label class="label_3">户型</label>
			<div id="roomVal" class="flexBox fontSmall clearOv">
				 <input type="number" value="1" class="txtLc1"/>
	             <span>室</span>
	             <input type="number" value="1" class="txtLc1"/>
	             <span>厅</span>
	             <input type="number" value="1" class="txtLc1"/>
	             <span>厨</span>
	             <input type="number" value="1" class="txtLc1"/>
	             <span>卫</span>
	             <input type="number" value="1" class="txtLc1"/>
	             <span class="smallA">阳台</span>
            </div>
		</li>
		<li>
			<label class="label_4">面积</label>
			<input id="area" class="" type="number" placeholder="请输入面积" />
			<span>平米</span>
		</li>
		<li>
			<label class="label_5">价格</label>
			<input id="total" class="" type="number" placeholder="请输入价格" />
			<span></span>
		</li>
		<li>
			<label class="label_zj">租金方式</label>
			<div id="rentType" class="xxBox"></div>
			<span class="qtxxIcon" id="rentTypeSel"></span>
		</li>
		<li>
			<label class="label_6">其它信息</label>
			<div id="labels" class="xxBox"></div>
			<span class="qtxxIcon" id="otherSel"></span>
		</li>
		<li>
			<label class="label_7">业主电话</label>
			<input id="perPhone" class="" type="number" placeholder="请输入电话" />
		</li>
		<li>
			<label class="label_8">选择VR房源</label>
			<div id="vrInfo" class="xxBox"></div>
			<span class="qtxxIcon" id="vrSel"></span>
		</li>
		<div class="vrImgList" style="display:none;">
		     <div><img src=""/></div>
		</div>
		<li>
			<label class="label_9">不上架</label>
			<span style="color: #D2D2D2;">（自己客户可见）</span>
			<div id="stateRadioBtn" class="stateRadio_ON"></div>
		</li>
		<li>
			<label class="label_10">房源类别</label>
			<div id="houseType" class="flexBox houseType">
				<span class="on" data-opion="recommend">推荐</span>
				<span class="" data-opion="secondh">二手房</span>
				<span class="" data-opion="new">新房</span>
			</div>
		</li>
	</ul>
	<a class="sureModifyBtn">保存房源</a>
</section>
<!--弹出框-Begin-->
<div class="popTipBox" style="display: none;">
 	<!--房源户型选择-->
	<div id="rentTypeList" class="popTipCon" style="display: none;">
	     <h4>
	        <a title="清空" class="clearBtn fleft">清空</a>
	        <b>选择租金方式</b>
	        <a title="确定" class="sureBtn fright">确定</a>
	     </h4>
	     <div class="intrChoList">
	          <dl class="listCon">
	              <dd>
	                  <span>押一付一</span>
	                  <span>押一付三</span>
	                  <span>押一付六</span>
	                  <span>押二付二</span>
	                  <span>押二付三</span>
	              </dd>
	          </dl>
	     </div>
	</div>
	<!--房源其他信息选择-->
	<div id="other" class="popTipCon qitaBox" style="display: none;">
	     <h4>
	        <a title="清空" class="clearBtn fleft">清空</a>
	        <b>其它信息</b>
	        <a title="确定" class="sureBtn fright">确定</a>
	     </h4>
	     <div id="tagList" class="intrChoList" style="-webkit-overflow-scrolling: touch;">
	     </div>
	</div>
	<!--选择VR房源-->
	<div id="vrlist" class="popTipCon" style="display: none;">
	     <h4>
	        <b class="fleft">选择VR房源</b>
	        <a title="搜索" id="searchVr" class="sureBtn fright">搜索</a>
	     </h4>
         <!--筛选条-->
		 <ul class="txtBar clearOv">
		     <li><input id="vrComm" class="" placeholder="选择小区" /></li>
		     <li><input id="vrArea" class="" placeholder="选择面积" /></li>
		     <li style="display:none;"><input id="vrRoom" class="" placeholder="选择户型" value=""/></li>
		     <li><input id="vrTowards" class="" placeholder="选择朝向" /></li>
		 </ul>
	     <!--筛选条-->
	     <div class="vrChoList clearOv">
	     </div>
	     <div class="vrChoNone clearOv" style="display:none;">
	     	      未找到符合条件的房源~~~
	     </div>
	     <div class="btnFixBox clearOv">
	          <a title="清空" class="qkBtn clear">清空</a>
	          <a title="确定" class="qdBtn sure">确定</a>
	     </div>
	</div>
</div>
<!--弹出框-over-->
<div class="popTipBox1" style="display:none;">
	 <div class="popTip90"></div>
</div>
<!-- <div id="SquareId" style ="position:absolute;background-color:#fff;width:100px;height:50px;">
	<span id="SquareCommId" style="font-size:13px;display:block;line-height:25px;text-align:center;"></span>
	<span id="SquareStreetId" style="font-size:11px;display:block;line-height:20px;text-align:center;"></span>
</div> -->
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}", "${openid}");
		agentHousePub3.vars.type="${type}";
		agentHousePub3.vars.city="${store.city}";
		agentHousePub3.vars.picPath="${picPath}";
		agentHousePub3.init();
	});
</script>
</html>