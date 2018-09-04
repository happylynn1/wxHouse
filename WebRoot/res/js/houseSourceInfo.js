var wxPicPath = "http://wx.jjr580.com/commpic";
var houseSourceInfo={
	houseid:"",
	openid:"",
	count:1,
	type:"",
	city:"",
	page:0,
	limit:10,
	getHouseSourceInfo:function(){
		$('.picSwitch').html('');
		if(this.openid==null||this.openid==""){
			$('.like').hide();
		}
		$.post(ext.path+"house/getHouseSourceInfoByHouseid.do",{houseid:this.houseid,openid:this.openid},function(data){
			if(!data.hs){
				location.href= ext.path+"page/errorHouse.do";
				return;
			}
			var scn = data.hs.comm.length>7?data.hs.comm.substring(0,7)+"...":data.hs.comm;
			var ss = data.hs.address.length>7?data.hs.address.substring(0,7)+"...":data.hs.address;
			baiduMap.init("baiduMap", houseSourceInfo.city,scn,ss, data.hs.lng, data.hs.lat,true);
			
			var countpic = 0;
			var img = "";
			if(data.vlist.length > 0){
				countpic += data.vlist.length;
				$.each(data.vlist,function(i,p){
					img += "<li><div class='video_area'><div class='video-mask'></div><video src='" + p.videopath + "' style='position: relative; z-index: 10' controls='controls' poster='" + p.videopath.substring(0,72) + "png'></video>";
					if(i==0&&(data.vlist>1||data.pList.length>0))
						img += "<div class='video-guide'>左滑</div>";
					img += "</div></li>";
				});
			}
			if(data.pList.length>0){
				countpic += data.pList.length;
				$.each(data.pList,function(i,p){
					img+="<li><img src='"+p.imagepath+"' /></li>";
				});
			}
			if(countpic > 0){
				houseSourceInfo.count=countpic;
				$('.countPic').html("<b>1</b>/"+houseSourceInfo.count);
				$('.picSwitch').html(img);
				houseSourceInfo.Carousel();
			} else {
				$('.picSwitch').html("<li><img src='"+ext.defaultHouseImage+"' /></li>");
				$('.countPic').html("<b>0</b>/0");
			}
			$('.picSwitch li img').zoomify();
			$('.houseH5').html(data.hs.comm);
			if(data.collect){
				$('.like img').attr("src",ext.path+"res/images/xqIcon_1a.png");
			}else{
				$('.like img').attr("src",ext.path+"res/images/xqIcon_1.png");
			}
			var markBox = "",explainList="";
			$.each(data.likeTags,function(i,l){
				if(i<=3){
					markBox+="<span class='mark"+i+"'>"+data.likeTags[i]+"</span>";
				}
				explainList+="<li>"+data.likeTags[i]+"</li>";
			});
			$('.markBox').html(markBox);
			$('.explainList').html(explainList).parent("div.detailsBox1").show();
			houseSourceInfo.type = data.hs.trading_type;
			var sss = data.hs.trading_type=="sale"?"售价":"租金";
			$('.keyBox li').eq(0).html(data.hs.total+(data.hs.trading_type=="sale"?"万元":"元/月")+"<p>"+sss+"</p>");
			var _r = data.hs.room,_l= _r.length;
			if(_l!=5){
				$('.keyBox li').eq(1).html("暂无数据");
				$('#hx').html("暂无数据");
			}else{
				$('.keyBox li').eq(1).html(_r.charAt(0)+"室"+_r.charAt(1)+"厅"+_r.charAt(3)+"卫"+"<p>户型</p>");
				$('#hx').html(_r.charAt(0)+"室"+_r.charAt(1)+"厅"+_r.charAt(3)+"卫");
			}
			$('.keyBox li').eq(2).html(data.hs.area+"㎡"+"<p>面积</p>");
			if(data.hs.vrno==""){
				$('.icon-rotate').hide();
			}else{
				$('.icon-rotate').show().click(function(){
					location.href=data.hs.vr_path;
				});
			}
			if(data.hs.entryTime){
				$('#fpsj').html(data.hs.entryTime.substring(0,10)).parent('li').show();
			}
			if(data.hs.property_type){
				$('#fwlx').html(data.hs.property_type).parent('li').show();
			}
			if(data.hs.towards){
				$('#cx').html(data.hs.towards).parent('li').show();
			}
			if(data.hs.outdoor){
				$('#wj').html(data.hs.outdoor).parent('li').show();
			}
			if(data.hs.lift){
				$('#dt').html(data.hs.lift).parent('li').show();
			}
			if(data.hs.renovate){
				$('#zxqk').html(data.hs.renovate).parent('li').show();
			}
			if(data.hs.lastRenovate){
				$('#sczx').html(data.hs.lastRenovate).parent('li').show();
			}
			if(data.hs.degree){
				$('#xwqk').html(data.hs.degree).parent('li').show();
			}
			if(data.hs.estateLicense){
				$('#fcz').html(data.hs.estateLicense).parent('li').show();
			}
			if(data.hs.only){
				$('#sfwy').html(data.hs.only).parent('li').show();
			}
			if(data.hs.arrears){
				$('#yzqk').html(data.hs.arrears).parent('li').show();
			}
			if(data.hs.showTime){
				$('#kfsj').html(data.hs.showTime).parent('li').show();
			}
			if(data.hs.showStatus){
				$('#kfqk').html(data.hs.showStatus).parent('li').show();
			}
			if(data.hs.centralnotes!=null&&data.hs.centralnotes!=""){
				$('#centralnotes').html("<dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>"+data.hs.centralnotes+"</prev></dt><dd><em>"+ext.timeFormat(data.hs.centralnotesTime)+"</em><span><a class='delBtnA' href='javascript:;' onclick=\"houseSourceInfo.delOtherNotes('1')\"><i class='delImgBtn'></i>删除</a></span></dd>");
			}else{
				$('#centralnotes').html("<dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>暂无数据</prev></dt>");
			}
			if(data.hs.matingnotes!=null&&data.hs.matingnotes!=""){
				$('#matingnotes').html("<dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>"+data.hs.matingnotes+"</prev></dt><dd><em>"+ext.timeFormat(data.hs.matingnotesTime)+"</em><span><a class='delBtnA' href='javascript:;' onclick=\"houseSourceInfo.delOtherNotes('2')\"><i class='delImgBtn'></i>删除</a></span></dd>");
			}else{
				$('#matingnotes').html("<dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>暂无数据</prev></dt>");
			}
			$('div.bijidl').html('');
			if(data.notes!=null&&data.notes.length>0){
				$.each(data.notes,function(i,n){
					$('div.bijidl').append("<dl class='clearfix' id='"+n.id+"'><dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>"+n.notes+"</prev></dt><dd><em>"+ext.timeFormat(n.times)+"</em><span><a class='delBtnA' href='javascript:;' onclick=\"houseSourceInfo.delNotes('"+n.id+"')\"><i class='delImgBtn'></i>删除</a></span></dd></dl>");
				});
			}else{
				$('div.bijidl').append("<dl class='clearfix'><dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>暂无笔记</prev></dt></dl>");
			}
			if(data.agent!=null&&data.agent.isonjob=="是"){
				if(houseSourceInfo.openid==data.agent.openid){
					//显示看房笔记编辑按钮
					$('#centralnotesBiji').show().bind("click",function(){houseSourceInfo.upOtherNotes(this,'1');});
					$('#matingnotesBiji').show().bind("click",function(){houseSourceInfo.upOtherNotes(this,'2');});
					$('#biji').show().bind("click",function(){houseSourceInfo.addNotes(this);});
					$('.delBtnA').show(); 
				}
				var opd = data.agent.openid,pho=data.agent.phone,hi=data.agent.head_image,na=data.agent.name||data.agent.nickname,_na= na.length>4?(na.substr(0,4)+"..."):na;
				$('.advisor_Photo a img').attr("src",hi);
				$('.advisor_Photo a b').html(_na);
				var advisorIntr = "<p class='wz_w'><a onclick='houseSourceInfo.contact(this)' data-openid='"+opd+"' class='chatBtn fleft'><em> 微聊</em></a>";
				if(pho!=null&&pho!=''){
					advisorIntr += "<a class='telBtn fleft' href='tel:"+pho+"' ><em>电话</em></a>";
				}else{
					advisorIntr += "<a class='telBtn fleft' href=\"javascript:alert('该经纪人暂未填写电话')\" ><em>电话</em></a>";
				}
				advisorIntr+="</p>";
				$('.adviso_rIntr').html(advisorIntr);
				$('.float_bottom').show();
			}
		},'json');
	},
	upOtherNotes:function(obj,type){
		$(obj).removeClass("editBjBtn").addClass("sure2Btn");
		if(type=="1"){
			var con = $('#centralnotes dt prev').html();
			if(con == "暂无数据") con = "";
			$('#centralnotes').hide();
			$('#centralnotesBijiUp').show().find("textArea").text(con).focus();
		}else{
			var con = $('#matingnotes dt prev').html();
			if(con == "暂无数据") con = "";
			$('#matingnotes').hide();
			$('#matingnotesBijiUp').show().find("textArea").text(con).focus();
		}
		$(obj).unbind("click").bind("click",function(){
			houseSourceInfo.upSubimtOtherNotes(this,type);
		});
	},
	upSubimtOtherNotes:function(obj,type){
		var con = type=='1'?$('#centralnotesBijiUp textArea').val():$('#matingnotesBijiUp textArea').val();
		$(obj).removeClass("sure2Btn").addClass("editBjBtn");
		$.post(ext.path+"house/upOtherNotes.do",{houseid:houseSourceInfo.houseid,type:type,content:con},function(data){
			con=con||"暂无数据";
			if(type=="1"){
				$('#centralnotesBijiUp').hide();
				$('#centralnotes').html("<dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>"+con+"</prev></dt>"+
		        "<dd><em>刚刚</em><span><a class='delBtnA' href='javascript:;' onclick=\"houseSourceInfo.delOtherNotes('1')\"><i class='delImgBtn'></i>删除</a></span></dd>").show();
				$('#centralnotesBiji').unbind("click").bind("click",function(){houseSourceInfo.upOtherNotes(this,'1');});
				if(con!="暂无数据") $('#centralnotes .delBtnA').show(); 
			}else{
				$('#matingnotesBijiUp').hide();
				$('#matingnotes').html("<dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>"+con+"</prev></dt>"+
			    "<dd><em>刚刚</em><span><a class='delBtnA' href='javascript:;' onclick=\"houseSourceInfo.delOtherNotes('2')\"><i class='delImgBtn'></i>删除</a></span></dd>").show();
				$('#matingnotesBiji').unbind("click").bind("click",function(){houseSourceInfo.upOtherNotes(this,'2');});
				if(con!="暂无数据") $('#matingnotes .delBtnA').show(); 
			}
		});
	},
	delOtherNotes:function(type){
		$.post(ext.path+"house/upOtherNotes.do",{houseid:houseSourceInfo.houseid,type:type,content:''},function(data){
			if(type=="1"){
				$('#centralnotesBijiUp textarea').val('');
				$('#centralnotes').html("<dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>暂无数据</prev></dt>");
			}else{
				$('#matingnotesBijiUp textarea').val('');
				$('#matingnotes').html("<dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>暂无数据</prev></dt>");
			}
		});
	},
	addNotes:function(obj){
		$(obj).removeClass("addBjBtn").addClass("sure2Btn");
		$('#bijiUp').show().find("textArea").focus();
		var _s = "";
		$('div.bijidl dl').each(function(){
			if($(this).find("dd").length==1){
				_s+="<dl class='clearfix' id='"+$(this).attr("id")+"'>"+$(this).html()+"</dl>";
			}
		});
		$('div.bijidl').html(_s);
		$(obj).unbind("click").bind("click",function(){
			houseSourceInfo.submitNotes(this);
		});
	},
	submitNotes:function(obj){
		var _t = $('#bijiUp textArea').val(),
		_tz = _t.replace(/\ +/g,"").replace(/[ ]/g,"").replace(/[\r\n]/g,"");
		$(obj).removeClass("sure2Btn").addClass("addBjBtn");
		if(_tz!=''){
			$.post(ext.path+"house/addNotes.do",{houseid:houseSourceInfo.houseid,notes:_t},function(data){
				var s = "<dl class='clearfix' id='"+data+"'><dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>"+$('#bijiUp textArea').val()+"</prev></dt><dd><em>刚刚</em><span><a class='delBtnA' href='javascript:;' onclick=\"houseSourceInfo.delNotes('"+data+"')\"><i class='delImgBtn'></i>删除</a></span></dd></dl>";
				$('div.bijidl dl').each(function(){
					if($(this).find("dd").length==1){
						s+="<dl class='clearfix' id='"+$(this).attr("id")+"'>"+$(this).html()+"</dl>";
					}
				});
				$('div.bijidl').html(s);
				$('#bijiUp').hide().find("textArea").val('');
				$('#biji').unbind("click").bind("click",function(){houseSourceInfo.addNotes(obj);});
				$('.delBtnA').show(); 
			});
		}else{
			if($('div.bijidl dl').length==0){
				$('div.bijidl').append("<dl class='clearfix'><dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>暂无笔记</prev></dt></dl>");
			}
			$('#bijiUp').hide().find("textArea").val('');
			$('#biji').unbind().bind("click",function(){houseSourceInfo.addNotes(obj);});
		}
	},
	delNotes:function(id){
		$.post(ext.path+"house/delNotes.do",{id:id},function(){
			$('div.bijidl').find("dl[id='"+id+"']").remove();
			if($('div.bijidl dl').length==0){
				$('div.bijidl').append("<dl class='clearfix'><dt><img src='"+ext.path+"res/images/list_ioc_1.png' /><prev>暂无笔记</prev></dt></dl>");
			}
		});
		$('.delBtnA').show(); 
	},
	contact:function(o){
		location.href=ext.path+"page/toChat.do?storeId="+ext.storeId+"&openid="+ext.openid+"&toOpenid="+$(o).attr("data-openid")+"&content="+houseSourceInfo.houseid;
	},
	Carousel:function(){
		var list = $('.picSwitch');
		var number = $('.picSwitch').find("li");
		number.width($('.housePicBox').width());
		list.width($('.housePicBox').width()*number.length);
        var sw = 0,oneWidth = number.eq(0).width();
        number.click(function(){
        	$(this).addClass("active").siblings("span").removeClass("active");
            sw=$(this).index();
            $('.countPic').html("<b>"+(sw+1)+"</b>/"+houseSourceInfo.count);
            list.animate({"right":oneWidth*sw});
        });
        
        var x, X,tX;
        var container = $(".picSwitch li");
        for(var i = 0; i < container.length; i++){
        	container[i].addEventListener('touchstart', function(event) {
    	        x = event.changedTouches[0].pageX;
    	    });
            container[i].addEventListener('touchmove', function(event){  
            	event.preventDefault();
    	        X = event.changedTouches[0].pageX;
    	    });
            container[i].addEventListener("touchend", function(event){
            	if(!number.eq(sw).find("img").hasClass("zoomed")){
            		if(Math.abs(X - x) > 0&&tX != X){
            			tX = X;
            			if(x - X > 5){   //右滑
            				if(sw < container.length - 1){
            					sw++;
            					number.eq(sw).click();
            				}
            			}
            			if(X - x > 5){   //左滑
            				if(sw > 0){
            					sw--;
            					number.eq(sw).click();
            				}
            			}
            		}
            	} 
            });
        }
        $(".video-mask").click(function(){
        	var obj = this;
        	var par_dom = obj.parentNode;
        	var video_dom = obj.nextElementSibling;
        	var video_src = video_dom.getAttribute("src");
        	var video_style = video_dom.getAttribute("style");
        	var video_controls = video_dom.getAttribute("controls");
        	var video_poster = video_dom.getAttribute("poster");
        	
        	var newNode = document.createElement("video");
        	newNode.setAttribute('src', video_src);
        	newNode.setAttribute('style', video_style);
        	newNode.setAttribute('controls', video_controls);
        	newNode.setAttribute('poster', video_poster);
        	
        	video_dom.play();
        	video_dom.onpause = function(){
        		par_dom.replaceChild(newNode,video_dom);
        	};
        	video_dom.onended = function(){
        		par_dom.replaceChild(newNode,video_dom);
        	};
        });
	},
	collect:function(){
		$(".like").click(function(){
			if($(this).find('img').attr("src").indexOf("xqIcon_1a.png")>0){
				$(this).find('img').attr("src",ext.path+"res/images/xqIcon_1.png");
				$.post(ext.path+"collect/delMyCollect.do",{houseid:houseSourceInfo.houseid,openid:houseSourceInfo.openid});
			}else{
				$(this).find('img').attr("src",ext.path+"res/images/xqIcon_1a.png");
				$.post(ext.path+"collect/addMyCollect.do",{houseid:houseSourceInfo.houseid,openid:houseSourceInfo.openid,type:houseSourceInfo.type});
			}
		});
	},
	commend:function(){
		$('.rentalHouseList').html('');
		paginate.init(0,10,houseSourceInfo.doSearch);
	},
	doSearch:function(page,limit){
		var num = 0;
		houseSourceInfo.page = page;
		houseSourceInfo.limit = limit;
		$('.rentalHouseList').find(".waiting").remove();
		$.post(ext.path+"recommend/getRecommendListByClientOpenid.do",{storeId:ext.storeId,
			openid:ext.openid,
			type:houseSourceInfo.type,
			page:houseSourceInfo.page,
			limit:houseSourceInfo.limit},function(data){
			num = data.length;
			if(num>0){
				var str=$('.rentalHouseList').html();
				$.each(data,function(i,o){
					if(o.hs.trading_type=="sale"){
						str = houseUtil.showSale(str,o);
					}else{
						str = houseUtil.showRent(str,o);
					}
				});
				$('.noData').hide();
				if(num==limit){
					str+="<li class='waiting'><span>加载中...</span></li>";
				}
				$('.rentalHouseList').html(str);
			}else{
				if(page==0){
					$('.noData').show();
				}
			}
		},'json');
		return num;
	},
	init:function(){
		initjsSDK(location.href.replace(ext.openid,"unopenid"),['onMenuShareTimeline', 'onMenuShareAppMessage','onMenuShareQQ']);
		this.openid = ext.openid;
		this.getHouseSourceInfo();
		this.collect();
		$(window).scroll(function(){
			var t=$("#baiduMap").offset().top;    
		    if (t >= $(window).scrollTop() && t < ($(window).scrollTop()+$(window).height())) {      
		        if($('.rentalHouseList li').length==0&&$('.noData').is(":hidden")){
		        	houseSourceInfo.commend();
		        }
		    }
		});
	}
};