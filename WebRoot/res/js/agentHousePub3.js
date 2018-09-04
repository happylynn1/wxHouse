var agentHousePub3 = {
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
		picPath:""
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
	showTags:function(t){
		$('#tagList').html('');
		$.post(ext.path+"house/getHouseTagList.do",{type:t},function(data){
			var str = "";
			$.each(data,function(i,m){
				str+="<dl class='listCon'><dt>"+m.group+"</dt><dd>";
				$.each(m.list,function(j,o){
					str+="<span name="+o.name+" data_label="+o.islabel+">"+o.label+"</span>";
				});
				str +="</dd></dl>";
			});
			$('#tagList').html(str);
			$('#other').find("span").click(function(){
				$(this).addClass("activeSpan").siblings("span").removeClass("activeSpan");
			});
		},"json");
	},
	selLoaction:function(){
		agentHousePub3.commName = $('#comm').val();
		if(agentHousePub3.commName==''){
			wx.getLocation({
	            type : 'wgs84',
	            success : function(res) {
	            	var longitude = res.longitude;
	                var latitude  = res.latitude;
	                var lnglat = longitude+","+latitude;
	                $.post(ext.path+"house/getCommByLnglatByWx.do",{lnglat:lnglat},function(data){
	                	if(data!=null){
	                		$('.label_1').next("input").val(data.name);
	                		$('#commInput').val(data.name);
	                		$('#addressInput').val(data.address);
	                		$('#lngInput').val(data.lng);
	                		$('#latInput').val(data.lat);
	                		var scn = data.name.length>7?data.name.substring(0,7)+"...":data.name;
	                		var ss = data.address.length>7?data.address.substring(0,7)+"...":data.address;
	                		baiduMap.init("baiduMap",agentHousePub3.vars.city,scn,ss,data.lng,data.lat);
	                	}else{
	                		$('.label_1').next("input").attr("placeholder","定位小区出错,请手动输入");
	                	}
	                },'json');
	            }
	        });
		}else{
			$.post(ext.path+'house/getCommByLnglatBySearch.do',{city:agentHousePub3.vars.city,comm:agentHousePub3.commName},function(data){
				//$('#commInput').val(agentHousePub3.commName);
				$('#addressInput').val(data.address);
				$('#lngInput').val(data.lng);
				$('#latInput').val(data.lat);
				var scn = data.name.length>7?data.name.substring(0,7)+"...":data.name;
				var ss = data.address.length>7?data.address.substring(0,7)+"...":data.address;
				baiduMap.init("baiduMap",agentHousePub3.vars.city,scn,ss,data.lng,data.lat);
			},'json');
		}
	},
	searchVrlist:function(c,a,r,t){
		a = a.replace("㎡","");
		r = r.replace("室","").replace("厅","").replace("厨","").replace("卫","").replace("阳台","");
		$('.vrChoList').html('');
		$.post(ext.path+'house/searchVrlist.do',{comm:c,area:a,room:r,towards:t,openid:agentHousePub3.vars.openid},function(data){
			if(data.length>0){
				var str ="";
				$.each(data,function(i,d){
					str+="<dl class='vrListCon clearOv' data_path="+d.vr_path+" data_vrno="+d.vrno+" onclick='agentHousePub3.selvr(this)'>"+
					"<dt><img src='"+d.image_path+"' /></dt>"+
					"<dd>"+d.comm+"</dd></dl>";
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
		agentHousePub3.vars.comm=$('#comm').val();
		if(agentHousePub3.vars.comm==''){
			agentHousePub3.showErr("小区名称不能为空","#comm");
			return;
		}
		agentHousePub3.vars.address=$('#addressInput').val();
		agentHousePub3.vars.lng=$('#lngInput').val();
		agentHousePub3.vars.lat=$('#latInput').val();
		if(agentHousePub3.vars.buildingNo==''){
			agentHousePub3.showErr("楼栋号不能为空","#buildingNo");
			return;
		} 
		if(agentHousePub3.vars.unitno==''){
			agentHousePub3.showErr("单元号不能为空","#unitno");
			return;
		}
		if(agentHousePub3.vars.doorno==''){
			agentHousePub3.showErr("户号不能为空","#doorno");
			return;
		}
		if(agentHousePub3.vars.houseno==''){
			agentHousePub3.showErr("房源编码不能为空","#houseno");
			return;
		}
		if(agentHousePub3.vars.floor==''){
			agentHousePub3.showErr("楼层不能为空","#floor");
			return;
		} 
		if(agentHousePub3.vars.totalFloor==''){
			agentHousePub3.showErr("总楼层不能为空","#totalfloor");
			return;
		} 
		if(agentHousePub3.vars.area==''){
			agentHousePub3.showErr("面积不能为空","#area");
			return;
		} 
		if(agentHousePub3.vars.total==''){
			agentHousePub3.showErr("价格不能为空","#total");
			return;
		} 
		if(agentHousePub3.vars.perPhone==''){
			agentHousePub3.showErr("业主电话不能为空","#perPhone");
			return;
		} 
		$.post(ext.path+'house/submmit.do',agentHousePub3.vars,function(data){
			console.log(data);
			var obj = eval('(' + data + ')');
			console.log(obj.role + "-" + obj.houseid);
			if(obj.role === "agent"){
				location.href=ext.path+"page/agentHousePub4.do?houseid="+obj.houseid;
			} else {
				location.href=ext.path+"page/clientHousePub4.do?houseid="+obj.houseid;
			}
		});
	},
	init:function(){
		initjsSDK(location.href,['getLocation']);
		this.vars.openid=ext.openid;
		this.vars.storeId=ext.storeId;
		this.showTags(this.vars.type);
		if(this.vars.type=="sale"){
			agentHousePub3.vars.genre = "recommend";
			$('#total').next("span").html("万元");
			$('#rentType').parent("li").hide();
			$('#houseType span').click(function(){
				if(!$(this).hasClass("on")){
					agentHousePub3.vars.genre = $(this).attr("data-opion");
					$(this).addClass("on").siblings().removeClass("on");
				}
			});
		}else{
			$('#houseType').parent("li").hide();
			$('#total').next("span").html("元/月");
			$('#rentType').parent("li").show();
		}
		/*$('#comm').keyup(function(){
			if(agentHousePub3.commName!=$(this).val()){
				agentHousePub3.commName = $(this).val();
				$.post(ext.path+'house/getCommByLnglatBySearch.do',{city:agentHousePub3.vars.city,comm:agentHousePub3.commName},function(data){
					$('#commInput').val(data.name);
					$('#addressInput').val(data.address);
					$('#lngInput').val(data.lng);
					$('#latInput').val(data.lat);
					var scn = data.name.length>7?data.name.substring(0,7)+"...":data.name;
					var ss = data.address.length>7?data.address.substring(0,7)+"...":data.address;
					baiduMap.init("baiduMap",agentHousePub3.vars.city,scn,ss,data.lng,data.lat);
				},'json');
			}
		});*/
		//定位
		$('.xzxqIcon').click(function(){
			agentHousePub3.selLoaction();
		});
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
		$('#rentTypeSel').click(function(){
			$('.popTipBox').show();
			$('#rentTypeList').show();
		});
		$('#rentTypeList').find("span").click(function(){
			$(this).addClass("activeSpan").siblings("span").removeClass("activeSpan");
		});
		$('#otherSel').click(function(){
			$('.popTipBox').show();
			$('#other').show();
		});
		$('#vrSel').click(function(){
			var ri = $("#roomVal input"),rv = ri.eq(0).val()+"室"+ ri.eq(1).val()+"厅"+ri.eq(2).val()+"厨"+ri.eq(3).val()+"卫"+ri.eq(4).val()+"阳台";
			$('.popTipBox').show();
			$('#vrComm').val($("#comm").val());
			$('#vrArea').val($("#area").val()==''?'':$("#area").val()+'㎡');
			//$('#vrRoom').val(rv);
			$('#vrTowards').val(agentHousePub3.vars.towards);
			agentHousePub3.searchVrlist($("#vrComm").val(),$("#vrArea").val(),$('#vrRoom').val(),$('#vrTowards').val());
			$('#vrlist').show();
		});
		$('#searchVr').click(function(){
			agentHousePub3.searchVrlist($("#vrComm").val(),$("#vrArea").val(),$('#vrRoom').val(),$('#vrTowards').val());
		});
		$('#vrlist .clear').click(function(){
			$('#vrComm').val('');
			$('#vrArea').val('');
			$('#vrRoom').val('');
			$('#vrTowards').val('');
		});
		$('#vrlist .sure').click(function(){
			var a = $('.vrChoList .selYes');
			agentHousePub3.vars.vrno = a.attr("data_vrno");
			agentHousePub3.vars.vr_path = a.attr("data_path");
			$('#vrInfo').html(a.find("dd").html());
			$(".popTipBox").hide();
			$('#vrlist').hide();
		});
		$('#rentTypeList .sureBtn').click(function(){
			$('#rentTypeList').find(".activeSpan").each(function(){
				$('#rentType').html($(this).html());
			});
			$(".popTipBox").hide();
			$("#rentTypeList").hide();
			$("#other").hide();
		});
		$('#rentTypeList .clearBtn').click(function(){
			$('#rentTypeList').find("span").removeClass("activeSpan");
			$('#roomVal').html('');
		});
		$('#other .sureBtn').click(function(){
			var to = $('#other').find(".activeSpan[data_label='towards']");
			agentHousePub3.vars.towards=to.length>0?to.attr("name"):"";
			var po = $('#other').find(".activeSpan[data_label='property']");
			agentHousePub3.vars.property_type=po.length>0?po.attr("name"):"";
			agentHousePub3.vars.outdoor=$('#other').find(".activeSpan[data_label='outdoor']").attr("name");
			agentHousePub3.vars.lift=$('#other').find(".activeSpan[data_label='lift']").attr("name");
			agentHousePub3.vars.renovate=$('#other').find(".activeSpan[data_label='renovate']").attr("name");
			agentHousePub3.vars.lastRenovate=$('#other').find(".activeSpan[data_label='lastRenovate']").attr("name");
			agentHousePub3.vars.degree=$('#other').find(".activeSpan[data_label='degree']").attr("name");
			agentHousePub3.vars.estateLicense=$('#other').find(".activeSpan[data_label='estateLicense']").attr("name");
			agentHousePub3.vars.only=$('#other').find(".activeSpan[data_label='only']").attr("name");
			agentHousePub3.vars.arrears=$('#other').find(".activeSpan[data_label='arrears']").attr("name");
			agentHousePub3.vars.showStatus=$('#other').find(".activeSpan[data_label='showStatus']").attr("name");
			agentHousePub3.vars.showTime=$('#other').find(".activeSpan[data_label='showTime']").attr("name");
			var label = "",info="";
			$('#other').find(".activeSpan").each(function(i,b){
				if($(b).attr("data_label")=="label"){
					label+=$(b).attr("name")+",";
				}else if($(b).attr("data_label")=="info"){
					info+=$(b).attr("name")+",";
				}
			});
			
			$('#labels').html(label);
			agentHousePub3.vars.info = info;
			$(".popTipBox").hide();
			$("#rentTypeList").hide();
			$("#other").hide();
		});
		$('#other .clearBtn').click(function(){
			$('#other').find("span").removeClass("activeSpan");
			$('#labels').html('');
			agentHousePub3.vars.info="";
			agentHousePub3.vars.towards="";
			agentHousePub3.vars.property_type="";
		});
		$('#stateRadioBtn').click(function(){
			if($(this).hasClass("stateRadio_OFF")){
				$(this).removeClass("stateRadio_OFF").addClass("stateRadio_ON");
			}else{
				$(this).removeClass("stateRadio_ON").addClass("stateRadio_OFF");
			}
		});
		$('.sureModifyBtn').click(function(){
			agentHousePub3.setVars();
			agentHousePub3.submmit();
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