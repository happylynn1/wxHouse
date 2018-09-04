<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head lang="en">
<title>聊天室</title>
<link rel="shortcut icon" href="${path}res/images/favicon.ico"
	type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta name="description" content="">
<link rel="stylesheet" href="${path}res/css/m-base.css?v=${ver}" />
<link rel="stylesheet" href="${path}res/css/style_publish.css?v=${ver}" />
<script type="text/javascript" src="${path}res/js/jquery.js?v=${ver}"></script>
<script type="text/javascript" src="${path}res/js/ext.js?v=${ver}"></script>
<script type="text/javascript" src="${path}res/js/chat.js?v=${ver}"></script>
</head>
<body>
	<div class="nichengBox">
		<div class="nichengCon">
			<span id="requireSpan">昵称昵称</span> <input id="requireInput"
				type="text" placeholder="编辑昵称" style="display: none;" />
		</div>
		<a id="bjbcbtn" class="bjBtn" title="编辑"></a>
	</div>
	<div class="chatSec">
		<!--left content-->
		<div class="portraitListBox">
			<div class="portraitList">
			</div>
		</div>
		<!--right content-->
		<div class="rightBox" style="-webkit-overflow-scrolling: touch;">
			<ul class="chatListBox clearOv">
			</ul>
		</div>
		<div class="sendBox clearOv">
			<div>
				<textarea placeholder="请输入内容…" style="position:fixed;"></textarea>
			</div>
			<a title="点击发送" id="sendBtn" class="sendBtnRed">发送</a>
		</div>
	</div>
	<div class="popTipBox1" style="display:none;">
        <div class="popTip90"></div>
    </div>
</body>
<script>
	$(function() {
		ext.setvars("${path}", "${storeId}", "${openid}");
		chat.vars.toOpenid = "${toOpenid}";
		chat.vars.content = "${content}";
		chat.init();
		chat.nicknameinit();
		chat.leftListInit();
		var _w = $(window).width() - $('.portraitListBox').width()- $('#sendBtn').width() - 14.4;
		$('.sendBox div').css({"width" : _w + 14.4});
		$('textarea').css({"width" : _w}).bind("focus",function(){
			setTimeout(function(){$('body').scrollTop(500);},150);
		});
		$('#sendBtn').on("click", chat.send);
	});
</script>
</html>