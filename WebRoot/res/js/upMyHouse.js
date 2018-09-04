var upMyHouse = {
	commName:"",
	vars:{
		type:"",
		city:"",
		storeId:"",
		openid:"",
		comm:"",
		tuiguangyu:"",
		address:"",
		lng:"",
		lat:"",
		buildingNo:"",
		unitno:"",
		doorno:"",
		houseno:"",
		room:"",
		area:"",
		total:"",
		rentType:"",
		info:"",
		floor:"",
		totalFloor:"",
		labels:"",
		outdoor:"",
		lift:"",
		renovate:"",
		lastRenovate:"",
		degree:"",
		estateLicense:"",
		only:"",
		arrears:"",
		showStatus:"",
		showTime:"",
		towards:"",
		property_type:"",
		perPhone:"",
		vrno:"",
		vr_path:"",
		ispublic:"",
		genre:"",
		picPath:"",
		houseid:""
	},
	getHouseInfo:function(){
		$.post(ext.path+"house/getHouseSourceInfoByHouseid.do",{houseid:upMyHouse.vars.houseid},function(data){
			if(data.hs){
				var scn = data.hs.comm.length>7?data.hs.comm.substring(0,7)+"...":data.hs.comm;
				var ss = data.hs.address.length>7?data.hs.address.substring(0,7)+"...":data.hs.address;
				baiduMap.init("baiduMap", upMyHouse.vars.city,scn,ss, data.hs.lng, data.hs.lat);
				upMyHouse.vars.vrno = data.hs.vrno;
				upMyHouse.vars.vr_path = data.hs.vr_path;
				upMyHouse.vars.type = data.hs.trading_type;
				upMyHouse.showTags(data.hs);
				if(data.hs.trading_type=="sale"){
					$('#total').next("span").html("万元");
					$('#rentType').parent("li").hide();
					upMyHouse.vars.genre = data.hs.genre;
					$('#houseType span').click(function(){
						if(!$(this).hasClass("on")){
							upMyHouse.vars.genre = $(this).attr("data-opion");
							$(this).addClass("on").siblings().removeClass("on");
						}
					});
				}else{
					$('#total').next("span").html("元/月");
					$('#houseType').parent("li").hide();
					$('#rentType').parent("li").show();
				}
				$('#comm').val(data.hs.comm);
				//$('#commInput').val(data.hs.comm);
				$('#lngInput').val(data.hs.lng);
				$('#latInput').val(data.hs.lat);
				$('#tuiguangyu').val(data.hs.tuiguangyu);
				$('#unitno').val(data.hs.unitno);
				$('#doorno').val(data.hs.doorno);
				$('#houseno').val(data.hs.houseno);
				$('#buildingNo').val(data.hs.buildingno);
				$('#floor').val(data.hs.floor);
				$('#totalfloor').val(data.hs.totalFloor);
				$('#area').val(data.hs.area);
				$('#total').val(data.hs.total);
				$('#perPhone').val(data.hs.perPhone);
				$('#roomVal input').each(function(i,o){
					$(this).val(data.hs.room.charAt(i));
				});
				$('#stateRadioBtn').attr("class",data.hs.ispublic=="1"?"stateRadio_ON":"stateRadio_OFF");
				upMyHouse.getMyVr(data.hs.vrno);
				$('#houseType').find("span[data-opion='"+data.hs.genre+"']").addClass("on");
				
				$('#rentTypeList').find("span").each(function(index, obj){
					if($(this).html()==data.hs.rentType){
						$(this).addClass("activeSpan");
					}
				});
				$("#rentType").html(data.hs.rentType);
			}
		},'json');
	},
	setVars:function(){
		this.vars.comm=$("#comm").val();
		this.vars.tuiguangyu=$("#tuiguangyu").val();
		this.vars.buildingNo=$("#buildingNo").val();
		this.vars.unitno=$("#unitno").val();
		this.vars.doorno=$("#doorno").val();
		this.vars.houseno=$("#houseno").val();
		this.vars.room=$("#roomVal").text();
		var ri = $("#roomVal input");
		this.vars.room =ri.eq(0).val()+ri.eq(1).val()+ri.eq(2).val()+ri.eq(3).val()+ri.eq(4).val();
		this.vars.area=$("#area").val();
		this.vars.floor=$("#floor").val();
		this.vars.rentType=$("#rentType").html();
		this.vars.totalFloor=$("#totalfloor").val();
		this.vars.total=$("#total").val();
		this.vars.labels=$("#labels").html();
		this.vars.perPhone=$("#perPhone").val();
		this.vars.ispublic=$("#stateRadioBtn").hasClass("stateRadio_ON")?"1":"0";
	},
	showTags:function(h){
		$('#tagList').html('');
		$.post(ext.path+"house/getHouseTagList.do",{type:h.trading_type},function(data){
			var str = "";
			$.each(data,function(i,m){
				str+="<dl class='listCon'><dt>"+m.group+"</dt><dd>";
				$.each(m.list,function(j,o){
					var labelArr = h.labels.split(",");
					var infoAr = h.info.split(",");
					if(o.name==h.property_type||o.name==h.towards
						||o.name==h.outdoor||o.name==h.lift||
						o.name==h.renovate||o.name==h.lastRenovate||
						o.name==h.degree||o.name==h.estateLicense||
						o.name==h.only||o.name==h.arrears||
						o.name==h.showStatus||o.name==h.showTime){
						str+="<span name="+o.name+" data_label="+o.islabel+" class='activeSpan'>"+o.label+"</span>";
					}else if(labelArr.indexOf(o.name)>=0){
						str+="<span name="+o.name+" data_label="+o.islabel+" class='activeSpan'>"+o.label+"</span>";
					}else if(infoAr.indexOf(o.name)>=0){
						str+="<span name="+o.name+" data_label="+o.islabel+" class='activeSpan'>"+o.label+"</span>";
					}else{
						str+="<span name="+o.name+" data_label="+o.islabel+">"+o.label+"</span>";
					}
				});
				str +="</dd></dl>";
			});
			$('#tagList').html(str);
			var to = $('#other').find(".activeSpan[data_label='towards']");
			upMyHouse.vars.towards=to.length>0?to.attr("name"):"";
			var po = $('#other').find(".activeSpan[data_label='property']");
			upMyHouse.vars.property_type=po.length>0?po.attr("name"):"";
			upMyHouse.vars.outdoor=$('#other').find(".activeSpan[data_label='outdoor']").attr("name");
			upMyHouse.vars.lift=$('#other').find(".activeSpan[data_label='lift']").attr("name");
			upMyHouse.vars.renovate=$('#other').find(".activeSpan[data_label='renovate']").attr("name");
			upMyHouse.vars.lastRenovate=$('#other').find(".activeSpan[data_label='lastRenovate']").attr("name");
			upMyHouse.vars.degree=$('#other').find(".activeSpan[data_label='degree']").attr("name");
			upMyHouse.vars.estateLicense=$('#other').find(".activeSpan[data_label='estateLicense']").attr("name");
			upMyHouse.vars.only=$('#other').find(".activeSpan[data_label='only']").attr("name");
			upMyHouse.vars.arrears=$('#other').find(".activeSpan[data_label='arrears']").attr("name");
			upMyHouse.vars.showStatus=$('#other').find(".activeSpan[data_label='showStatus']").attr("name");
			upMyHouse.vars.showTime=$('#other').find(".activeSpan[data_label='showTime']").attr("name");
			var label = "",info="";
			$('#other').find(".activeSpan").each(function(i,b){
				if($(b).attr("data_label")=="label"){
					label+=$(b).attr("name")+",";
				}else if($(b).attr("data_label")=="info"){
					info+=$(b).attr("name")+",";
				}
			});
			$('#labels').html(label);
			upMyHouse.vars.info = info;
			
			$('#other').find("span").click(function(){
				$(this).addClass("activeSpan").siblings("span").removeClass("activeSpan");
			});
		},"json");
	},
	selLoaction:function(){
		upMyHouse.commName = $('#comm').val();
		if(upMyHouse.commName==''){
			wx.getLocation({
	            type : 'wgs84',
	            success : function(res) {
	            	var longitude = res.longitude;
	                var latitude  = res.latitude;
	                var lnglat = longitude+","+latitude;
	                $.post(ext.path+"house/getCommByLnglatByWx.do",{lnglat:lnglat},function(data){
	                	if(data!=null){
	                		$('.label_1').next("#comm").val(data.name);
	                		$('#commInput').val(data.name);
	                		$('#addressInput').val(data.address);
	                		$('#lngInput').val(data.lng);
	                		$('#latInput').val(data.lat);
	                		var scn = data.name.length>7?data.name.substring(0,7)+"...":data.name;
	                		var ss = data.address.length>7?data.address.substring(0,7)+"...":data.address;
	                		baiduMap.init("baiduMap",upMyHouse.vars.city,scn,ss,data.lng,data.lat);
	                	}else{
	                		$('.label_1').next("input").attr("placeholder","定位小区出错,请手动输入");
	                	}
	                },'json');
	            }
	        });
		}else{
			$.post(ext.path+'house/getCommByLnglatBySearch.do',{city:upMyHouse.vars.city,comm:upMyHouse.commName},function(data){
				//$('#commInput').val(data.name);
				$('#addressInput').val(data.address);
				$('#lngInput').val(data.lng);
				$('#latInput').val(data.lat);
				var scn = data.name.length>7?data.name.substring(0,7)+"...":data.name;
				var ss = data.address.length>7?data.address.substring(0,7)+"...":data.address;
				baiduMap.init("baiduMap",upMyHouse.vars.city,scn,ss,data.lng,data.lat);
			},'json');
		}
	},
	getMyVr:function(vrno){
		if(vrno!=null&&vrno!=""){
			$.post(ext.path+'house/getMyVr.do',{vrno:vrno,openid:ext.openid},function(data){
				if(data){
					var d =data;
					var str ="";
					str+="<dl class='vrListCon clearOv selYes' data_path="+d.vr_path+" data_vrno="+d.vrno+" onclick='upMyHouse.selvr(this)'>"+
					"<dt><img src='"+d.image_path+"' /></dt>"+
					"<dd>"+d.comm+"</dd></dl>";
					$('.vrChoNone').hide();
					$('.vrChoList').html(str).show();
				}else{
					$('.vrChoList').hide();
					$('.vrChoNone').show();
				}
			},"json");
		}else{
			$('.vrChoList').hide();
			$('.vrChoNone').show();	
		}
	},
	searchVrlist:function(c,a,r,t,vrno){
		a = a.replace("㎡","");
		r = r.replace("室","").replace("厅","").replace("厨","").replace("卫","").replace("阳台","");
		$('.vrChoList').html('');
		$.post(ext.path+'house/searchVrlist.do',{comm:c,area:a,room:r,towards:t,openid:upMyHouse.vars.openid},function(data){
			if(data.length>0){
				var str ="";
				$.each(data,function(i,d){
					if(vrno!=undefined&&vrno==d.vrno){
						str+="<dl class='vrListCon clearOv selYes' data_path="+d.vr_path+" data_vrno="+d.vrno+" onclick='upMyHouse.selvr(this)'>"+
						"<dt><img src='"+d.image_path+"' /></dt>"+
						"<dd>"+d.comm+"</dd></dl>";
					}else{
						str+="<dl class='vrListCon clearOv' data_path="+d.vr_path+" data_vrno="+d.vrno+" onclick='upMyHouse.selvr(this)'>"+
						"<dt><img src='"+d.image_path+"' /></dt>"+
						"<dd>"+d.comm+"</dd></dl>";
					}
				});
				$('.vrChoNone').hide();
				$('.vrChoList').html(str).show();
			}else{
				$('.vrChoList').hide();
				$('.vrChoNone').show();
			}
		},"json");
	},
	selvr:function(o){
		if($(o).hasClass("selYes")){
			$(o).removeClass("selYes");
		}else{
			$(o).siblings().removeClass("selYes");
			$(o).addClass("selYes");
		}
	},
	showErr:function(msg,obj){
		var _w = (msg.length+2)*17.6;
		var _l = ($(window).width()-_w)/2;
		$('.error').removeClass("error");
		$('.popTip90').html(msg).css({"width":_w,"left":_l});
		$('.popTipBox1').show().fadeOut(2000);
		setTimeout(function(){$(obj).addClass("error").focus();},1500);
		$("html,body").animate({scrollTop:$(obj).offset().top},100);
	},
	submmit:function(){
		upMyHouse.vars.comm = $('#comm').val();
		if(upMyHouse.vars.comm==''){
			upMyHouse.showErr("小区名称不能为空","#comm");
			return;
		} 
		upMyHouse.vars.address=$('#addressInput').val();
		upMyHouse.vars.lng=$('#lngInput').val();
		upMyHouse.vars.lat=$('#latInput').val();
		if(upMyHouse.vars.buildingNo==''){
			upMyHouse.showErr("楼栋号不能为空","#buildingNo");
			return;
		} 
		if(upMyHouse.vars.unitno==''){
			upMyHouse.showErr("单元号不能为空","#unitno");
			return;
		}
		if(upMyHouse.vars.doorno==''){
			upMyHouse.showErr("户号不能为空","#doorno");
			return;
		}
		if(upMyHouse.vars.houseno==''){
			upMyHouse.showErr("房源编码不能为空","#houseno");
			return;
		}
		if(upMyHouse.vars.floor==''){
			upMyHouse.showErr("楼层不能为空","#floor");
			return;
		} 
		if(upMyHouse.vars.totalFloor==''){
			upMyHouse.showErr("总楼层不能为空","#totalfloor");
			return;
		} 
		if(upMyHouse.vars.area==''){
			upMyHouse.showErr("面积不能为空","#area");
			return;
		} 
		if(upMyHouse.vars.total==''){
			upMyHouse.showErr("价格不能为空","#total");
			return;
		} 
		if(upMyHouse.vars.perPhone==''){
			upMyHouse.showErr("业主电话不能为空","#perPhone");
			return;
		} 
		console.log(upMyHouse.vars.comm);
		console.log(upMyHouse.vars);
		$.post(ext.path+'house/updateHouseByHouseid.do',upMyHouse.vars,function(data){
			location.href=ext.path+"page/toMyHouse.do?openid="+ext.openid;
		});
	},
	init:function(){
		initjsSDK(location.href,['getLocation']);
		this.vars.openid=ext.openid;
		this.vars.storeId=ext.storeId;
		this.getHouseInfo();
		$('#tuiguangyu').keyup(function(){
			var _t = $(this),w = 0; 
			for (var i=0; i<_t.val().length; i++) {
				w++; 
				if (w > 14) { 
					_t.val(_t.val().substr(0,i));
					break; 
				} 
			}
		});
		/*其他信息显示*/
		$('#otherSel').click(function(){
			$('.popTipBox').show();
			$('#other').show();
		});
		/*其他信息清空*/
		$('#other .clearBtn').click(function(){
			$('#other').find("span").removeClass("activeSpan");
			$('#labels').html('');
			upMyHouse.vars.info="";
			upMyHouse.vars.towards="";
			upMyHouse.vars.property_type="";
		});
		/*其他信息确认*/
		$('#other .sureBtn').click(function(){
			var to = $('#other').find(".activeSpan[data_label='towards']");
			upMyHouse.vars.towards=to.length>0?to.attr("name"):"";
			var po = $('#other').find(".activeSpan[data_label='property']");
			upMyHouse.vars.property_type=po.length>0?po.attr("name"):"";
			upMyHouse.vars.outdoor=$('#other').find(".activeSpan[data_label='outdoor']").attr("name");
			upMyHouse.vars.lift=$('#other').find(".activeSpan[data_label='lift']").attr("name");
			upMyHouse.vars.renovate=$('#other').find(".activeSpan[data_label='renovate']").attr("name");
			upMyHouse.vars.lastRenovate=$('#other').find(".activeSpan[data_label='lastRenovate']").attr("name");
			upMyHouse.vars.degree=$('#other').find(".activeSpan[data_label='degree']").attr("name");
			upMyHouse.vars.estateLicense=$('#other').find(".activeSpan[data_label='estateLicense']").attr("name");
			upMyHouse.vars.only=$('#other').find(".activeSpan[data_label='only']").attr("name");
			upMyHouse.vars.arrears=$('#other').find(".activeSpan[data_label='arrears']").attr("name");
			upMyHouse.vars.showStatus=$('#other').find(".activeSpan[data_label='showStatus']").attr("name");
			upMyHouse.vars.showTime=$('#other').find(".activeSpan[data_label='showTime']").attr("name");
			var label = "",info="";
			$('#other').find(".activeSpan").each(function(i,b){
				if($(b).attr("data_label")=="label"){
					label+=$(b).attr("name")+",";
				}else if($(b).attr("data_label")=="info"){
					info+=$(b).attr("name")+",";
				}
			});
			$('#labels').html(label);
			upMyHouse.vars.info = info;
			$(".popTipBox").hide();
			$("#rentTypeList").hide();
			$("#other").hide();
		});
		//点击其他隐藏弹出框
		$(".popTipBox").click(function(e){
			if($(e.target).find("a").length!==0){
				$(".popTipBox").hide();
				$("#rentTypeList").hide();
				$("#other").hide();
				$('#vrlist').hide();
			}
		});
		/*$('#comm').keyup(function(){
			if(upMyHouse.commName!=$(this).val()){
				console.log(upMyHouse.commName+"--->"+$(this).val());
				upMyHouse.commName = $(this).val();
				$.post(ext.path+'house/getCommByLnglatBySearch.do',{city:upMyHouse.vars.city,comm:upMyHouse.commName},function(data){
					$('#commInput').val(data.name);
					$('#addressInput').val(data.address);
					$('#lngInput').val(data.lng);
					$('#latInput').val(data.lat);
					var scn = data.name.length>7?data.name.substring(0,7)+"...":data.name;
					var ss = data.address.length>7?data.address.substring(0,7)+"...":data.address;
					baiduMap.init("baiduMap",upMyHouse.vars.city,scn,ss,data.lng,data.lat);
				},'json');
			}
		});*/
		//定位
		$('.xzxqIcon').click(function(){
			upMyHouse.selLoaction();
		});
		/*vr信息显示*/
		$('#vrSel').click(function(){
			var ri = $("#roomVal input"),rv = ri.eq(0).val()+"室"+ ri.eq(1).val()+"厅"+ri.eq(2).val()+"厨"+ri.eq(3).val()+"卫"+ri.eq(4).val()+"阳台";
			$('.popTipBox').show();
			$('#vrComm').val($("#comm").val());
			$('#vrArea').val($("#area").val()==''?'':$("#area").val()+'㎡');
			//$('#vrRoom').val(rv);
			$('#vrTowards').val(upMyHouse.vars.towards);
			$('#vrlist').show();
		});
		/*vr信息搜索*/
		$('#searchVr').click(function(){
			upMyHouse.searchVrlist($("#vrComm").val(),$("#vrArea").val(),$('#vrRoom').val(),$('#vrTowards').val(),upMyHouse.vars.vrno);
		});
		/*vr信息清空*/
		$('#vrlist .clear').click(function(){
			$('#vrComm').val('');
			$('#vrArea').val('');
			$('#vrRoom').val('');
			$('#vrTowards').val('');
		});
		/*vr信息确认*/
		$('#vrlist .sure').click(function(){
			var a = $('.vrChoList .selYes');
			upMyHouse.vars.vrno = a.attr("data_vrno");
			upMyHouse.vars.vr_path = a.attr("data_path");
			$('#vrInfo').html(a.find("dd").html());
			$(".popTipBox").hide();
			$('#vrlist').hide();
		});
		/*出租类型显示*/
		$('#rentTypeSel').click(function(){
			$('.popTipBox').show();
			$('#rentTypeList').show();
		});
		/*出租类型选择*/
		$('#rentTypeList').find("span").click(function(){
			$(this).addClass("activeSpan").siblings("span").removeClass("activeSpan");
		});
		/*出租类型确认*/
		$('#rentTypeList .sureBtn').click(function(){
			$('#rentTypeList').find(".activeSpan").each(function(){
				$('#rentType').html($(this).html());
			});
			$(".popTipBox").hide();
			$("#rentTypeList").hide();
			$("#other").hide();
		});
		/*出租类型清空*/
		$('#rentTypeList .clearBtn').click(function(){
			$('#rentTypeList').find("span").removeClass("activeSpan");
			$('#roomVal').html('');
		});
		/*房源是否可见切换*/
		$('#stateRadioBtn').click(function(){
			if($(this).hasClass("stateRadio_OFF")){
				$(this).removeClass("stateRadio_OFF").addClass("stateRadio_ON");
			}else{
				$(this).removeClass("stateRadio_ON").addClass("stateRadio_OFF");
			}
		});
		$('.sureModifyBtn').click(function(){
			upMyHouse.setVars();
			upMyHouse.submmit();
		});
		// 点击房源编号的输入框，自动获取“楼栋号-单元号-户号”
		$("#houseno").click(function(){
			var hno = "";
			hno = ($("#buildingNo").val() != '' && $("#buildingNo").val() != null) ? $("#buildingNo").val() : hno;
			hno = ($("#unitno").val() != '' && $("#unitno").val() != null) ? hno + "-" +$("#unitno").val() : hno;
			hno = ($("#doorno").val() != '' && $("#doorno").val() != null) ? hno + "-" +$("#doorno").val() : hno;
			$("#houseno").val(hno);			
		});
	}	
};