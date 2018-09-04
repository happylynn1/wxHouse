<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head lang="en">
    <title>聊天室</title>
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
	<script type="text/javascript" src="${path}res/js/chat.js?v=${ver}"></script>
</head>
<body>
<section class="chatSec" style="padding-bottom: 0px;">
	<!--left content-->
    <div class="portraitListBox">
    	 <span><img src="${path}res/images/imgDF.png"/></span>
    	 <span class="ac"><img src="${path}res/images/imgDF.png"/></span>
    	 <span><img src="${path}res/images/imgDF.png"/></span>
    	 <span class="bc"><img src="${path}res/images/imgDF.png"/></span>
    </div>
    <!--right content-->
    <div class="rightBox" style="overflow:auto;">
    	<div class="remarksBox">
    	 	  <input placeholder="备注" class="" />
    	 	  <a class="saveBtn" title="保存备注"></a>
    	 </div>
    	 <ul class="chatListBox clearOv" style="padding:1rem 0rem 0.6rem 0rem">
    	     <!-- <li class="khInf fyInf">
    	 	  	  <label>胡先生</label>
    	 	  	  <span style="display: flex; display: -webkit-flex;">
		    	 	  	  <div class="houseImg">
				               <img src="images/imgDF.png" />
				          </div>
				          <dl class="hotHouseIntr">
				         	  <dt>百环家园</dt>
				              <dd>223.87㎡ / 南北 / 低层 共6层</dd>
				          </dl>
		          </span>
    	 	  </li> -->
    	 </ul>
    </div>
    <div class="sendBox clearOv">
        <div><textarea placeholder="请输入内容…"></textarea></div>
    	<a title="点击发送" class="sendBtn"></a>
    </div>
</section>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}","${openid}");
		chat.vars.toOpenid="${toOpenid}";
		chat.vars.content="${content}";
		chat.init();
		chat.nicknameinit();
		chat.leftListInit();
		$('.sendBtn').on("click",chat.send);
	});
</script>
</html>