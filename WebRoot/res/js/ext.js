jQuery.fn.shake = function (intShakes /*Amount of shakes*/, intDistance /*Shake distance*/, intDuration /*Time duration*/) {
    this.each(function () {
        var jqNode = $(this);
        jqNode.css({ position: 'relative' });
        for (var x = 1; x <= intShakes; x++) {
            jqNode.animate({ left: (intDistance * -1) }, (((intDuration / intShakes) / 4)))
            .animate({ left: intDistance }, ((intDuration / intShakes) / 2))
            .animate({ left: 0 }, (((intDuration / intShakes) / 4)));
        }
    });
    return this;
};
var ext = {
	/* 项目根路径 */
	path : "",
	/* 登陆者openid */
	openid : "",
	/* 登陆者所关注或店员的门店id */
	storeId : "",
	/* 默认房源图片，没上传图片时，显示此图片 */
	defaultHouseImage : "",
	/* 默认用户头像图片，没头像时，显示此图片 */
	defaultHeadImage : "",
	/* 默认门店图片，显示此图片 */
	defaultStoreImage : "",
	/* 页面访问之后，设置storeId、openid */
	setvars : function(p, s, o) {
		this.storeId = s;
		this.openid = o;
		this.path = p;
		this.defaultHouseImage = p + "res/images/defaultImg.png";
		this.defaultHeadImage = p + "res/images/defaultHead.jpg";
		this.defaultStoreImage = p + "res/images/defaultStore.jpg";
	},
	p:function(s) {
	    return s < 10 ? '0' + s: s;
	},
	isPoneAvailable:function(s) {
        var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
        return myreg.test(s);
    },
    isBefore:function(s){
    	var d = new Date(s.replace(/\-/g, "\/"));
    	var n = new Date();
    	return (d.getTime()+59000)>=n.getTime();
    },
    inWeek:function(s){
    	var d = new Date(s.replace(/\-/g, "\/"));
    	var n = new Date();
    	return d.getTime()<=(n.getTime()+604740000);
    },
	getTime:function(){
		var myDate = new Date();
		var year=myDate.getFullYear();
		var month=myDate.getMonth()+1;
		var date=myDate.getDate(); 
		var h=myDate.getHours();
		var m=myDate.getMinutes();
		return year+'-'+ext.p(month)+"-"+ext.p(date)+" "+ext.p(h)+':'+ext.p(m);
	},
	timeFormat:function(t){
		var _t = new Date(new Date(new Date().toLocaleDateString()).getTime()),_y = new Date(_t - 86400000);
		var _n = new Date(),_d = new Date(t),_c = (_n.getTime() - _d.getTime())/1000;
		if(_c<60) return "刚刚";
		if(_c<3600) return  Math.round(_c/60)+"分钟前";
		if(_d>_t) return "今天"+t.substr(11,8);
		if(_d>_y) return "昨天"+t.substr(11,8);
		return t.substr(0,19);
	},
	timeFormat2:function(s){
		var time = new Date(s.time);
		var y = time.getFullYear();//年
		var m = time.getMonth() + 1;//月
		var d = time.getDate();//日
		var h = time.getHours();//时
		var mm = time.getMinutes();//分
		return y+"-"+ext.p(m)+"-"+ext.p(d)+" "+ext.p(h)+":"+ext.p(mm);	
	}
};
//上滑分页组件
var paginate = {
	num:0,
	page:0,
	limit:10,
	init:function(_p,_l,callback){
		$.ajaxSetup({async : false}); 
		this.page = _p;
		this.limit = _l||this.limit;
		setTimeout(function() {paginate.num = callback(paginate.page,paginate.limit);}, 100);
	    $(window).unbind("scroll").scroll(function() {
			$('.SoCon,#room,#price,#towards').hide();
	    	if ((paginate.num==paginate.limit)&&($(document).scrollTop() >= $(document).height() - $(window).height())) {
	    		paginate.page++;
	    		setTimeout(function() {paginate.num = callback(paginate.page,paginate.limit);}, 300); 
	        }
	    });  
	}
};
/* 微信注入jsSDK url= location.href arr为接口数组 */
var initjsSDK = function(url, arr) {
	var storeId = ext.storeId;
	$.post(ext.path + "action/getJswxapiconf.do", {
		url : url.split('#')[0], storeId : storeId
	}, function(data) {
		wx.config({
			debug : false,
			appId : data.appId,
			timestamp : data.timestamp,
			nonceStr : data.nonceStr,
			signature : data.signature,
			jsApiList : arr
		});
		wx.ready(function(){
			if(url.indexOf("houseSourceInfo2.do")>0){
				var houseid = url.slice(url.indexOf("=")+1,url.indexOf("&"));
				$.post(ext.path+"house/getHouseSourceByHouseid.do",{houseid:houseid,storeId:storeId},function(data){
					if(!data.hs){
						location.href= ext.path+"page/errorHouse.do";
						return;
					}
					var picPath = data.pList.length>0?(data.pList[0].imagepath||ext.defaultHouseImage):ext.defaultHouseImage;
					var _r = data.hs.room,room = _r.charAt(0)+"室"+_r.charAt(1)+"厅"+_r.charAt(3)+"卫",total = data.hs.total+(data.hs.trading_type=="sale"?"万元":"元/月");
					var desc = data.hs.comm+"  "+room+"  "+data.hs.area+"㎡ \b"+total;
					// 分享到朋友圈
					wx.onMenuShareTimeline({
						title: data.wechatname + "-" + data.hs.comm,
					    link: url, 
					    imgUrl: picPath, 
					    success:function(){
					    	console.log(ext.storeId + "-" + data.hs.comm + "-分享成功。");
					    }
					});
					
					// 分享给朋友
					wx.onMenuShareAppMessage({
					    title: data.wechatname, 
					    desc: desc,
					    link: url,
					    imgUrl:picPath, 
					    type: 'link',
					    dataUrl: '',
					    success: function () {   
					        console.log(ext.storeId + "-" + data.hs.comm + "分享成功。");
					    } 
					});
					
					//分享到QQ
					wx.onMenuShareQQ({
						title: data.wechatname,
					    desc: desc,
						link: url,
						imgUrl:picPath,
						success: function () {
							console.log(ext.storeId + "-" + data.hs.comm + "分享成功。");
						}
					});
				},'json');
			}
		});
	}, "json");
};
var houseUtil = {
	/* 拼接出售房源 */
	showSale : function(s, o) {
		var hs = o.hs, pl = o.pl, vl = o.vl, htl = o.htl;
		var pp = ext.defaultHouseImage;
		pl&&pl.length > 0&&(pp = pl[0].imagepath);
		var f = "暂无";
		if (hs.floor != null && hs.totalFloor != null) 
			f = (hs.floor / hs.totalFloor <= 0.3 ? "低层" : (hs.floor/ hs.totalFloor <= 0.6 ? "中层" : "高层"));
		var pert = "暂无";
		if (hs.total != null && hs.area != null) 
			pert = (hs.total/ hs.area).toFixed(1) + "万/㎡";
		s += "<li onclick=\"houseUtil.showInfo('"+ hs.houseid+ "')\"><div class='houseImg'><img src='"+ pp+ "' onerror='houseUtil.changePic(this)' />";
		if(hs.vrno!=null&&hs.vrno != '')
			s +="<p class='vrImg'></p>";
		if(vl && vl.length > 0)
			s += "<p class='videoImg'></p>";
		s += "</div><dl class='DlHouseIntr'><dt><span class='Dlgrayleft fleft'><b>"+ hs.comm + "</b></span>" +
				"<span class='DlgrayFont fright'>"+hs.total+"<a>万元</a></span></dt><dd><em>"+hs.tuiguangyu+"</em></dd>";
		if (hs.area != null && hs.area != '') {
			s += "<dd>" + hs.area + "㎡ / ";
		}
		s += f + " 共" + hs.totalFloor + "层   / "+pert+"</dd><dd>";
		var tagNum = $('body').width()>=360?4:3;
		$.each(htl, function(i, b) {
			if (i < tagNum) {
				s += "<span class='Dmark" + i + "'>" + b + "</span>";
			}
		});
		s += "</dd></dl></li>";
		return s;
	},
	/* 拼接出租房源 */
	showRent : function(s, o) {
		var hs = o.hs, pl = o.pl, vl = o.vl, htl = o.htl;
		var pp = ext.defaultHouseImage;
		if (pl&&pl.length > 0)
			pp = pl[0].imagepath;
		var f = "暂无";
		if (hs.floor != null && hs.totalFloor != null) 
			f = (hs.floor / hs.totalFloor <= 0.3 ? "低层" : (hs.floor/ hs.totalFloor <= 0.6 ? "中层" : "高层"));
		s += "<li onclick=\"houseUtil.showInfo('"+ hs.houseid+ "')\"><div class='houseImg'><img src='"+ pp+ "' onerror='houseUtil.changePic(this)' />";
		if(hs.vrno!=null&&hs.vrno != '')
			s +="<p class='vrImg'></p>";
		if(vl && vl.length > 0)
			s += "<p class='videoImg'></p>";
		s += "</div><dl class='DlHouseIntr'><dt><span class='Dlgrayleft fleft'><b>"+ hs.comm + "</b></span>" +
				"<span class='DlgrayFont fright'>"+hs.total+"<a>元/月</a></span></dt><dd><em>"+hs.tuiguangyu+"</em></dd>";
		if (hs.area != null && hs.area != '') {
			s += "<dd>" + hs.area + "㎡ / ";
		}
		s += f + " 共" + hs.totalFloor + "层   ";
		if(hs.rentType){
			s+="/ "+hs.rentType+"</dd><dd>";
		}else{
			s+="</dd><dd>";
		}
		var tagNum = $('body').width()>=360?4:3;
		$.each(htl, function(i, b) {
			if (i < tagNum) {
				s += "<span class='Dmark" + i + "'>" + b + "</span>";
			}
		});
		s += "</dd></dl></li>";
		return s;
	},
	showSaleLeftSlide:function(s, o){
		var hs = o.hs, pl = o.pl, vl = o.vl, htl = o.htl;
		var pp = ext.defaultHouseImage;
		if (pl&&pl.length > 0)
			pp = pl[0].imagepath;
		var f = "暂无";
		if (hs.floor != null && hs.totalFloor != null) 
			f = (hs.floor / hs.totalFloor <= 0.3 ? "低层" : (hs.floor/ hs.totalFloor <= 0.6 ? "中层" : "高层"));
		var pert = "暂无";
		if (hs.total != null && hs.area != null) 
			pert = (hs.total/ hs.area).toFixed(1) + "万/㎡";
		s += "<li id='"+ hs.houseid+ "'><div class='items'><div class='houseImg' onclick=\"houseUtil.showInfo('"+hs.houseid+"')\"><img src='"+ pp+ "' onerror='houseUtil.changePic(this)' />";
		if(hs.vrno!=null&&hs.vrno != '')
			s +="<p class='vrImg'></p>";
		if(vl && vl.length > 0)
			s += "<p class='videoImg'></p>";
		s += "</div><dl class='DlHouseIntr' onclick=\"houseUtil.showInfo('"+hs.houseid+"')\"><dt><span class='Dlgrayleft fleft'><b>"+ hs.comm + "</b></span>" +
				"<span class='DlgrayFont fright'>"+hs.total+"<a>万元</a></span></dt><dd><em>"+hs.tuiguangyu+"</em></dd>";
		if (hs.area != null && hs.area != '') {
			s += "<dd>" + hs.area + "㎡ / ";
		}
		s += f + " 共" + hs.totalFloor + "层   / "+pert+"</dd><dd>";
		var tagNum = $('body').width()>=360?4:3;
		$.each(htl, function(i, b) {
			if (i < tagNum) {
				s += "<span class='Dmark" + i + "'>" + b + "</span>";
			}
		});
		s += "</dd></dl><div class='deal'><span class='upPic'>更改<br/>图片</span><span class='update'>编辑</span><span class='del'>删除</span></div></div></li>";
		return s;
	},
	showRentLeftSlide:function(s, o){
		var hs = o.hs, pl = o.pl, vl = o.vl, htl = o.htl;
		var pp = ext.defaultHouseImage;
		if (pl&&pl.length > 0)
			pp = pl[0].imagepath;
		var f = "暂无";
		if (hs.floor != null && hs.totalFloor != null) 
			f = (hs.floor / hs.totalFloor <= 0.3 ? "低层" : (hs.floor/ hs.totalFloor <= 0.6 ? "中层" : "高层"));
		s += "<li id='"+ hs.houseid+ "'><div class='items'><div class='houseImg' onclick=\"houseUtil.showInfo('"+hs.houseid+"')\"><img src='"+ pp+ "' onerror='houseUtil.changePic(this)' />";
		if(hs.vrno!=null&&hs.vrno != '')
			s +="<p class='vrImg'></p>";
		if(vl && vl.length > 0)
			s += "<p class='videoImg'></p>";
		s += "</div><dl class='DlHouseIntr' onclick=\"houseUtil.showInfo('"+hs.houseid+"')\"><dt><span class='Dlgrayleft fleft'><b>"+ hs.comm + "</b></span>" +
				"<span class='DlgrayFont fright'>"+hs.total+"<a>元/月</a></span></dt><dd><em>"+hs.tuiguangyu+"</em></dd>";
		if (hs.area != null && hs.area != '') {
			s += "<dd>" + hs.area + "㎡ / ";
		}
		s += f + " 共" + hs.totalFloor + "层   ";
		if(hs.rentType){
			s+="/ "+hs.rentType+"</dd><dd>";
		}else{
			s+="</dd><dd>";
		}
		var tagNum = $('body').width()>=360?4:3;
		$.each(htl, function(i, b) {
			if (i < tagNum) {
				s += "<span class='Dmark" + i + "'>" + b + "</span>";
			}
		});
		s += "</dd></dl><div class='deal'><span class='upPic'>更改<br/>图片</span><span class='update'>编辑</span><span class='del'>删除</span></div></div></li>";
		return s;
	},
	/* 跳转到房源详情 */
	showInfo : function(h) {
		location.href = ext.path + "page/houseSourceInfo.do?houseid=" + h
				+ "&openid=" + ext.openid + "&storeId=" + ext.storeId ;
	},
	//侧滑显示编辑删除按钮
	leftSlide:function(c){
		var expansion = null; //是否存在展开的list
		var container = document.querySelectorAll(c+' li .items');
		for(var i = 0; i < container.length; i++){    
		    var x, y, X, Y, swipeX, swipeY;
		    container[i].addEventListener('touchstart', function(event) {
		        x = event.changedTouches[0].pageX;
		        y = event.changedTouches[0].pageY;
		        swipeX = true;
		        swipeY = true;
		    });
		    container[i].addEventListener('touchmove', function(event){     
		        X = event.changedTouches[0].pageX;
		        Y = event.changedTouches[0].pageY;
		        // 左右滑动
		        if(swipeX && Math.abs(X - x) - Math.abs(Y - y) > 0){
		            // 阻止事件冒泡
		            event.stopPropagation();
		            if(X - x > 10){   //右滑
		                event.preventDefault();
		                this.className = "items";//右滑收起
		            }
		            if(x - X > 10){   //左滑
		                event.preventDefault();
		                if(expansion){//判断是否展开，如果展开则收起
				            expansion.className = "items";
				        }
		                this.className = "items swipeleft";//左滑展开
		                expansion = this;
		            }
		            swipeY = false;
		        }
		        // 上下滑动
		        if(swipeY && Math.abs(X - x) - Math.abs(Y - y) < 0) {
		            swipeX = false;
		        }        
		    });
		}
	},
	changePic : function(img) {
		$(img).attr("src", ext.defaultHouseImage);
	}
};
var lookHouseUtil = {
		showList:function(s, o){
			var ch = ext.isBefore(ext.timeFormat2(o.time)+":00");
			var yyvisorIntr = ch?"yyvisorIntr":"yyvisorIntr2";
			var deal = ch?"<span class='cancel'>取消<br/>预约</span>":"<span id='"+o.id+"' class='del'>删除</span>";
			s +="<li onclick=\"houseUtil.showInfo('"+o.houseid+"')\" id='"+o.id+"'><div class='items'><div class='"+yyvisorIntr+"'><p class='wzP'>" +
				"<b>"+o.comm+"</b><span>"+o.total+"元/月</span></p><p class='clearfix'>"+
				"<a class='yy_sh'>预约时间：" +ext.timeFormat2(o.time)+	"</a><span>" +
				"<a onclick='persionUtil.contact(this)' data-openid='"+ o.openid +"' class='chatBtn2 fleft'><em>微聊</em></a>";
			o.phone?(s+="<a class='telBtn2 fleft' href='tel:"+o.phone+"'><em>电话</em></a>"):
				(s+="<a class='telBtn2 fleft' href=\"javascript:alert('暂未填写电话')\"><em>电话</em></a>");
			s+=	"</span></p></div><div class='deal'>"+deal+"</div></div></li>";
			return s;
		}
};
var persionUtil = {
	/* 拼接客户 */
	showClient : function(s, o) {
		var name = o.required||o.nickname;
		var _na= name.length>7?(name.substr(0,6)+"..."):name;
		s += "<li id='"+o.openid+"'><div class='items'><div class='orl'><div class='advisor_Photo advisor_PhotoList'><a><img src='"+o.headImage+"'><b>"+_na+"</b></a></div>" +
			"<div class='adviso_rIntr adviso_rIntrList'><p class='wz_w'><a onclick='persionUtil.contact(this)' data-openid='"+ o.openid +"' class='chatBtn chatBtnUser fleft'><em> 微聊</em></a>";
		s+="</p></div></div><div class='deal'><span class='del'>删除</span></div></div></li>";
		return s;
	},
	/* 拼接经纪人 */
	showAgent : function(s, o) {
		var _na= o.nickname.length>7?(o.nickname.substr(0,6)+"..."):o.nickname;
		s += "<li id='"+o.openid+"'><div class='items'><div class='orl'><div class='advisor_Photo advisor_PhotoList'><a><img src='"+o.head_image+"'><b>"+_na+"</b></a></div>" +
			"<div class='adviso_rIntr adviso_rIntrList'><p class='wz_w'><a onclick='persionUtil.contact(this)' data-openid='"+ o.openid +"' class='chatBtn fleft'><em> 微聊</em></a>";
		if(o.phone!=null&&o.phone!=""){
			s+="<a class='telBtn fleft' href='tel:"+ o.phone + "'><em>电话</em></a>";
		}else{
			s+="<a class='telBtn fleft' href=\"javascript:alert('该经纪人暂未填写电话')\"><em>电话</em></a>";
		}
		s+="</p></div></div><div class='deal'><span class='del'>删除</span></div></div></li>";
		return s;
	},
	/* 拼接经纪人 */
	showAgent_orl : function(s, o) {
		var _na= o.nickname.length>7?(o.nickname.substr(0,6)+"..."):o.nickname;
		s += "<li style='border-bottom: 1px solid #d9d9d9;'><div class='advisor_Photo advisor_PhotoList'><a><img src='"+o.head_image+"'><b>"+_na+"</b></a></div>" +
		"<div class='adviso_rIntr adviso_rIntrList'><p class='wz_w'><a onclick='persionUtil.contact(this)' data-openid='"+ o.openid +"' class='chatBtn fleft'><em> 微聊</em></a>";
		if(o.phone!=null&&o.phone!=""){
			s+="<a class='telBtn fleft' href='tel:"+ o.phone + "'><em>电话</em></a>";
		}else{
			s+="<a class='telBtn fleft' href=\"javascript:alert('该经纪人暂未填写电话')\"><em>电话</em></a>";
		}
		s+="</p></div></li>";
		return s;
	},
	contact : function(o) {
		location.href = ext.path + "page/toChat.do?storeId=" + ext.storeId
				+ "&openid=" + ext.openid + "&toOpenid="
				+ $(o).attr("data-openid");
	}
};
