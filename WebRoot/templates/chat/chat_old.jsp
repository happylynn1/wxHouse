<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
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
	Welcome
	<br/>
	message:<input id="text" type="text" /><br/>
	id<input id="toId" type="text" />
	<button onclick="chat.send()">Send</button>
	<button onclick="chat.closeWs()">Close</button>
	<div id="message"></div>
</body>
<script>
	$(function(){
		ext.setvars("${path}","${storeId}","${openid}");
		chat.vars.toOpenid="${toOpenid}";
		chat.vars.content="${content}";
		console.log(chat.vars.toOpenid);
		console.log(chat.vars.content);
		chat.init();
	});
</script>
</html>