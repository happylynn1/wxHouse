/* 心跳机制  browser 定时向 server 发送心跳消息，server接收心跳返回 */
var heartCheck = {
	timeout : 3000,
	timeoutObj : null,
	serverTimeoutObj: null,
	reset:function(){
		console.log("--reset--");
		this.timeoutObj && clearTimeout(this.timeoutObj);
		this.serverTimeoutObj && clearTimeout(this.serverTimeoutObj);
	    this.start();
	},
	start:function(){
		console.log("--start--");
		var self = this;
		this.timeoutObj = setTimeout(function(){
			chat.websocket.send("heartbeat");
			self.serverTimeoutObj = setTimeout(function(){
				chat.closeWs();
				chat.reconnect();
			}, 100);
		}, this.timeout);
	}
};

var chat={
		lockReconnect:false,
		rectime:null,
		websocket:null,
		isScroll:false,
		require:"",
		vars:{
			openid:"",
			toOpenid:"",
			fromNickName:"",
			toNickName:"",
			content:""
		},
		reconnect:function(){
			console.log("chat.lockReconnect : " + chat.lockReconnect);
			if(chat.lockReconnect){
				return;
			}
			chat.lockReconnect = true;
			this.rectime && clearTimeout(this.rectime);
			this.rectime = setTimeout(function () {
				console.log("--reconnect--");
				chat.creatWs();
				chat.lockReconnect = false;
			}, 100);
		},
		creatWs:function(){
			if ('WebSocket' in window) {
				//var wsPath = ext.path.replace("http://","ws://")+"chat/";
				var wsPath = "ws://123.59.105.113:10240/wxHouse/chat/";
				wsPath += this.vars.openid+"/"+this.vars.toOpenid;
				this.websocket = new WebSocket(wsPath);
				//连接成功建立的回调方法
				this.websocket.onopen = function(event) {
					console.log("连接建立");
					if(chat.vars.content!=""){
						chat.websocket.send("[houseid:"+chat.vars.content+"]");
						chat.vars.content="";
					}
					heartCheck.start();
				};
				//接收到消息的回调方法
				this.websocket.onmessage = function(event) { 
					var msg = event.data;
					console.log("msg : " + msg);
					if(msg === "heartbeat"){
						return false;
					}
					if(msg.indexOf("-") == 0){
						// 有其他用户新信息来变色
						chat.leftListInit(msg);
					} else {
						chatcUtil.addOtherChat(msg);
					}
					heartCheck.reset();
				};
				//
				this.websocket.onerror = function(){
					console.log('发生异常');
					chat.reconnect();
				};
				//连接关闭的回调方法
				this.websocket.onclose = function() {
					console.log("连接关闭");
				};
				//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
				window.onbeforeunload = function() {
					chat.closeWs();
				};
			}
		},
		send:function(){
			if( "undefined" == typeof(chat.websocket) || null == chat.websocket ){
				console.log("chat.websocket 未生成.");
				chat.showErr("服务器断开链接，请稍后重试。");
				return false;
			}
			if(chat.websocket.readyState != 1){
				console.log("websocket 链接尚未建立. - " + chat.websocket.readyState);
				chat.showErr("服务器断开链接，请稍后重试。");
				return false;
			}
			var message = $.trim($(".sendBox div textarea").val());
			if(message!=''){
				chat.websocket.send(message);
				$(".sendBox div textarea").val('');
				chatcUtil.addMyChat(message);
			}
		},
		closeWs:function(){
			this.websocket.close();
		},
		upRequire:function(obj){
			$('#requireSpan').hide();
			$('#requireInput').val(chat.require).show().focus();
			$(obj).removeClass("bjBtn").addClass("bcBtn");
		},
		subRequire:function(obj){
			var require = $('#requireInput').val();
			$.post(ext.path+"chatc/upRequire.do",{openid:chat.vars.openid,toOpenid:chat.vars.toOpenid,require:require},function(data){
				chat.require = require;
				var reqName = require.length>16?require.substr(0,16)+"...":require;
				$('#requireSpan').html(reqName).show();
				$('#requireInput').hide();
				$(obj).removeClass("bcBtn").addClass("bjBtn");
			});
		},
		init:function(){
			var _th = $('.nichengBox').height(),_bh = $('.sendBox ').height(),_mh = $(window).height()-_th-_bh;
			$('.rightBox').height(_mh);
			$('.portraitListBox').height(_mh);
			chat.vars.openid = ext.openid;
			chat.creatWs();
			$.post(ext.path+"chatc/isInAgentClient.do",{openid:chat.vars.openid,toOpenid:chat.vars.toOpenid},function(data){if(!data.flag)$('#bjbcbtn').hide();},'json');
			//添加备注
			$('#bjbcbtn').bind("click",function(){if($(this).hasClass("bjBtn")){chat.upRequire(this);}else{chat.subRequire(this);}});
			//下滑获取聊天记录
			$('.rightBox').scroll(function(e){
				 if ($(this).scrollTop()<=0&&chat.isScroll){
					 e.preventDefault();
					 setTimeout(function(){chat.oldMsg(5);},500);
					 chat.isScroll=false;
				 }
			});
		},
		nicknameinit:function(){
			$.post(ext.path+"chatc/getChatNickName.do",{openid:chat.vars.openid,toOpenid:chat.vars.toOpenid},function(data){
				chat.vars.fromNickName = data.fromNickname;
				chat.vars.toNickName = data.toNickName;
				chat.require = data.toNickName;
				var reqName = chat.require.length>16?chat.require.substr(0,16)+"...":chat.require;
				$('#requireSpan').html(reqName);
				if(chat.vars.openid===chat.vars.toOpenid){
					$('.chatListBox').append("<li class='khInf'><span>你确定要自己跟自己聊天？</span></li>");
			    	chat.scrollToBottom();
				}else{
					chat.unreadMsgInit();
				}
			},"json");
		},
		leftListInit:function(msg){
			$.post(ext.path+"chatc/getUser.do",{openid:ext.openid,toOpenid:chat.vars.toOpenid},function(data){
				$('.portraitList').html('');
				if(data.length>0){
					var str="";
					$.each(data,function(i,o){
						if(o!=null)
							str = chatcUtil.showToUser(str, o);
					});
					$('.portraitList').html(str);
					if(msg!=undefined)
						chatcUtil.updateUserList(msg.slice(1));
				}
				$('.portraitList span i.del').unbind().bind("click",function(){
					var to = $(this).next("img").attr("data-openid");
					$.post(ext.path+"chatc/delChatList.do",{openid:ext.openid,toOpenid:to},function(){
						if($('.portraitList span').length==1){
							location.href = ext.path+"page/toMyAgent.do";
						}else{
							if($("img[data-openid='"+to+"']").parent("span").hasClass('ac')){
								var t2 = "";
								$("div.portraitList span").each(function(){
									if($(this).find('img').attr("data-openid")!=to){
										t2 = $(this).find('img').attr("data-openid");
										return false; 
									}
								});
								location.href = ext.path+"page/toChat.do?storeId="+ext.storeId+"&openid="+ext.openid+"&toOpenid="+t2;
							}else{
								chat.leftListInit();
							}
						}
					});
				});
			},"json");
		},
		unreadMsgInit:function(){
			$.post(ext.path+"chatc/getUnreadMsg.do",{toOpenid:chat.vars.toOpenid,openid:chat.vars.openid},function(data){
				$('.chatListBox').html('');
				if(data.length>0){
					var str="";
					$.each(data,function(i,o){
						if(o.content.indexOf("[houseid:")>=0){
				    		var houseid = o.content.replace("[houseid:","").replace("]","");
				    		$.ajax({
				    			type: 'POST',
				    			url:ext.path+"house/getHouseSourceByHouseid.do",
				    			data: {houseid:houseid},
				    			dataType:"json",
				    			async:false,
				    			success:function(data){
				    				if(data.flag){
										var pp = ext.defaultHouseImage;
										if(data.pList.length>0) pp = data.pList[0].imagepath;
										var f = "暂无";
										if(data.hs.floor!=null&&data.hs.totalFloor!=null){
											f=(data.hs.floor/data.hs.totalFloor<=0.3?"低层":(data.hs.floor/data.hs.totalFloor<=0.6?"中层":"高层"));
										}
										str += "<li onclick=\"houseUtil.showInfo('"+houseid+"')\" class='khInf fyInf'><span style='display: flex; display: -webkit-flex;'>" +
										"<div class='houseImg'><img src='"+pp+"' /></div><dl class='hotHouseIntr'><dt>"+data.hs.comm+"</dt>" +
										"<dd>"+data.hs.area+"㎡/";
										if (data.hs.towards != null && data.hs.towards != '') {
											str += data.hs.towards + "/";
										}
										str += f+"共"+data.hs.totalFloor+"层</dd></dl></span></li>";
									}else{
										str += "<li class='khInf'><span>经查询无此房源...</span></li>";
									}
				    			}
				    		});
				    	}else{
				    		str += "<li class='khInf'><span>" + o.content + "</span></li>";
				    	}
					});
					$('.chatListBox').html(str);
					chat.scrollToBottom();
				}
				$.ajaxSettings.async = false;  
				if(chat.vars.content!=""){
					chatcUtil.addMyChat("[houseid:"+chat.vars.content+"]");
				}
//				console.log(".chatListBox li length : " + $('.chatListBox li').length);
				if($('.chatListBox li').length<10){
					chat.oldMsg(10);
				}
				$.ajaxSettings.async = true;  
			},"json");
		},
		oldMsg:function(num){
			var index = $('.chatListBox li').length;
//			console.log("index : " + index);
			var _yh = $('.chatListBox').outerHeight();
			$.post(ext.path+"chatc/getOldMsg.do",{toOpenid:chat.vars.toOpenid,openid:chat.vars.openid,index:index,pageNum:num},function(data){
				var str = "";
				$.each(data,function(i,o){
					if(o.senderid == chat.vars.openid){
						str += chat.addMsg(o.content,"my");
					}else{
						str += chat.addMsg(o.content,"other");
					}
				});
				$('.chatListBox').prepend(str);
				var _nh = $('.chatListBox').outerHeight();
				$('.rightBox').scrollTop(_nh-_yh);
				chat.isScroll=true;
			},'json');
		},
		addMsg:function(m,type){
			var ss = "",ac=(type==="my")?"myInf":"khInf";
			if(m.indexOf("[houseid:")>=0){
	    		var houseid = m.replace("[houseid:","").replace("]","");
	    		$.ajax({
	    			url:ext.path+"house/getHouseSourceByHouseid.do",
	    			data:{houseid:houseid},
	    			dataType:"json",
	    			async:false,
	    			success:function(data){
	    				if(data.flag){
//	    					console.log("addMsg-data.flag:" + data.flag);
							var pp = ext.defaultHouseImage;
							if(data.pList.length>0) pp = data.pList[0].imagepath;
							var f = "暂无";
							if(data.hs.floor!=null&&data.hs.totalFloor!=null){
								f=(data.hs.floor/data.hs.totalFloor<=0.3?"低层":(data.hs.floor/data.hs.totalFloor<=0.6?"中层":"高层"));
							}
							ss+="<li onclick=\"houseUtil.showInfo('"+houseid+"')\" class='"+ac+" fyInf'><span style='display: flex; display: -webkit-flex;'>" +
							"<div class='houseImg'><img src='"+pp+"' /></div><dl class='hotHouseIntr'><dt>"+data.hs.comm+"</dt>" +
							"<dd>"+data.hs.area+"㎡/";
							if (data.hs.towards != null && data.hs.towards != '') {
								ss += data.hs.towards + "/";
							}
							ss += f+"共"+data.hs.totalFloor+"层</dd></dl></span></li>";
						}else{
							ss+="<li class='"+ac+"'><span>经查询无此房源...</span></li>";
						}
	    			}
	    		});
	    	}else{
	    		ss+="<li class='"+ac+"'><span>" + m + "</span></li>";
	    	}
			return ss;
		},
		scrollToBottom:function(){
			$('.rightBox').scrollTop($('.rightBox')[0].scrollHeight);
		},
		showErr:function(msg){
			var _w = (msg.length+2)*17.6;
			var _l = ($(window).width()-_w)/2;
			$('.popTip90').html(msg).css({"width":_w,"left":_l});
			$('.popTipBox1').show().fadeOut(2000);
		}
};

var chatcUtil={
	// 左侧用户列表
    showToUser:function(str,o){
    	var headimgpath = o.headImage||ext.defaultHeadImage;
    	if(o.openid == chat.vars.toOpenid){
    		str += "<span class='ac'><i class='del'>1</i><img src='"+ headimgpath +"' onclick='chatcUtil.contact(this)' data-openid='"+o.openid+"'/></span>";
    	} else {
    		str += "<span><i class='del'>1</i><img src='"+ headimgpath +"' onclick='chatcUtil.contact(this)' data-openid='"+o.openid+"'/></span>";
    	}
    	return str;
    },
    contact:function(o){
    	location.href=ext.path+"page/toChat.do?storeId="+ext.storeId+"&openid="+ext.openid+"&toOpenid="+$(o).attr("data-openid");
    },
    // 我发出的信息在聊天记录窗口显示
    addMyChat:function(m){
    	if(m === "heartbeat"){return ;}
    	if(m.indexOf("[houseid:")>=0){
    		var houseid = m.replace("[houseid:","").replace("]","");
    		$.post(ext.path+"house/getHouseSourceByHouseid.do",{houseid:houseid},function(data){
				if(data.flag){
//					console.log("addMyChat-data.flag:" + data.flag);
					var pp = ext.defaultHouseImage;
					if(data.pList.length>0) pp = data.pList[0].imagepath;
					var f = "暂无";
					if(data.hs.floor!=null&&data.hs.totalFloor!=null){
						f=(data.hs.floor/data.hs.totalFloor<=0.3?"低层":(data.hs.floor/data.hs.totalFloor<=0.6?"中层":"高层"));
					}
					var ss = "<li onclick=\"houseUtil.showInfo('"+houseid+"')\" class='myInf fyInf'><span style='display: flex; display: -webkit-flex;'>" +
					"<div class='houseImg'><img src='"+pp+"' /></div><dl class='hotHouseIntr'><dt>"+data.hs.comm+"</dt>" +
					"<dd>"+data.hs.area+"㎡/";
					if (data.hs.towards != null && data.hs.towards != '') {
						ss += data.hs.towards + "/";
					}
					ss += f+"共"+data.hs.totalFloor+"层</dd></dl></span></li>";
					$('.chatListBox').append(ss);
					chat.scrollToBottom();
				}else{
					$('.chatListBox').append("<li class='myInf'><span>经查询无此房源...</span></li>");
					chat.scrollToBottom();
				}
    		},'json');
    	}else{
    		$('.chatListBox').append("<li class='myInf'><span>" + m + "</span></li>");
    		chat.scrollToBottom();
    	}
//    	console.log("addMyChat-.chatListBox li length : " + $('.chatListBox li').length);
    },
    // 接收的信息在聊天记录窗口显示
    addOtherChat:function(m){
    	if(m.indexOf("[houseid:")>=0){
    		var houseid = m.replace("[houseid:","").replace("]","");
    		$.post(ext.path+"house/getHouseSourceByHouseid.do",{houseid:houseid},function(data){
				if(data.flag){
//					console.log("addOtherChat-data.flag:" + data.flag);
					var pp = ext.defaultHouseImage;
					if(data.pList.length>0) pp = data.pList[0].imagepath;
					var f = "暂无";
					if(data.hs.floor!=null&&data.hs.totalFloor!=null){
						f=(data.hs.floor/data.hs.totalFloor<=0.3?"低层":(data.hs.floor/data.hs.totalFloor<=0.6?"中层":"高层"));
					}
					var ss = "<li onclick=\"houseUtil.showInfo('"+houseid+"')\" class='khInf fyInf'><span style='display: flex; display: -webkit-flex;'>" +
					"<div class='houseImg'><img src='"+pp+"' /></div><dl class='hotHouseIntr'><dt>"+data.hs.comm+"</dt>" +
					"<dd>"+data.hs.area+"㎡/";
					if (data.hs.towards != null && data.hs.towards != '') {
						ss += data.hs.towards + "/";
					}
					ss += f+"共"+data.hs.totalFloor+"层</dd></dl></span></li>";
					$('.chatListBox').append(ss);
					chat.scrollToBottom();
				}else{
					$('.chatListBox').append("<li class='khInf'><span>经查询无此房源...</span></li>");
					chat.scrollToBottom();
				}
    		},'json');
    	}else{
    		$('.chatListBox').append("<li class='khInf'><span>" + m + "</span></li>");
    		chat.scrollToBottom();
    	}
    },
    updateUserList:function(id){
    	$(".portraitList").find("span").each(function(){
    		var openid = $(this).children("img").attr("data-openid");
    		if(id == openid) {
    			$(this).addClass("readNo");
    			$(this).prependTo(".portraitList");
    		}
    	});
    }
};