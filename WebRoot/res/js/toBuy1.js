var toBuy1 ={
		vars:{
			openid:"",
			storeId:"",
			type:"求购",
			name:"",
			phone:"",
			remark:"",
			city:"",
			district:"",
			street:"",
			usedto:"",
			decorate:"",
			price:"",
			room:"",
			area:""
		},
		setVars:function(){
			this.vars.name = $("#name_input").val();
			this.vars.phone = $("#phone_input").val();
			this.vars.remark = $("#remark_area").val();
			this.vars.city = $("#city_input").val();
			this.vars.district = $("#district_input").val();
			this.vars.street = $("#street_input").val();
			this.vars.usedto = $("#usedto_input").val();
			this.vars.decorate = $("#decorate_input").val();
			this.vars.price = $("#price_input").val();
			this.vars.room = $("#shi_input").val() + $("#ting_input").val() + $("#wei_input").val();
			this.vars.area = $("#area_input").val();
		},
		submit:function(){
			if($("#city_input").val()==''){
				toBuy1.showErr("城市不能为空","#city_input");
				return ;
			}
			toBuy1.vars.city = $("#city_input").val();
			if($("#district_input").val()==''){
				toBuy1.showErr("区域不能为空","#district_input");
				return ;
			}
			toBuy1.vars.district = $("#district_input").val();
			if($("#street_input").val()==''){
				toBuy1.showErr("街道不能为空","#street_input");
				return ;
			}
			toBuy1.vars.street = $("#street_input").val();
			if($("#usedto_input").val()==''){
				toBuy1.showErr("用途不能为空","#usedto_input");
				return ;
			}
			toBuy1.vars.usedto = $("#usedto_input").val();
			if($("#decorate_input").val()==''){
				toBuy1.showErr("装修不能为空","#decorate_input");
				return ;
			}
			toBuy1.vars.decorate = $("#decorate_input").val();
			if($("#price_input").val()==''){
				toBuy1.showErr("租金不能为空","#price_input");
				return ;
			}
			toBuy1.vars.price = $("#price_input").val();
			if($("#shi_input").val()==''){
				toBuy1.showErr("居室不能为空","#shi_input");
				return ;
			}
			if($("#ting_input").val()==''){
				toBuy1.showErr("居室不能为空","#ting_input");
				return ;
			}
			if($("#wei_input").val()==''){
				toBuy1.showErr("居室不能为空","#wei_input");
				return ;
			}
			toBuy1.vars.room = $("#shi_input").val() + $("#ting_input").val() + $("#wei_input").val();
			if($("#area_input").val()==''){
				toBuy1.showErr("面积不能为空","#area_input");
				return ;
			}
			toBuy1.vars.area = $("#area_input").val();

			toBuy1.vars.remark = $("#remark_area").val();
			if($("#name_input").val()==''){
				toBuy1.showErr("姓名不能为空","#name_input");
				return ;
			}
			toBuy1.vars.name = $("#name_input").val();
			if($("#phone_input").val()==''){
				toBuy1.showErr("电话不能为空","#phone_input");
				return ;
			}
			toBuy1.vars.phone = $("#phone_input").val();
			
			$.post(ext.path+'require/addRequire.do',toBuy1.vars,function(data){
				if(data.flag == 1){
					location.href=ext.path + "page/requireEntrust.do?requireid="+data.requireid+"&storeId="+ext.storeId+"&openid="+ext.openid;
				}
			},"json");
		},
		showErr:function(msg,obj){
			var _w = (msg.length+2)*17.6;
			var _l = ($(window).width()-_w)/2;
			$('.error').removeClass("error");
			$('.popTip90').html(msg).css({"width":_w,"left":_l});
			$('.popTipBox1').show().fadeOut(2000);
			setTimeout(function(){$(obj).val('').addClass("error").focus();},1500);
		},
		clickselectinit:function(){
			$("#city_span").click(function(){
				$(".popTipBoxL").show();
				$('body,html').addClass("noscroll");
				$("#city_div").show();
			});
			$("#city_div").find("li").click(function(){
				var city = $(this).html();
				var citycode = $(this).attr("data-id");
//				console.log(citycode);
				var city_i = $("#city_input").val();
				$("#city_input").val(city);
				if(city != city_i){
					$("#district_input").val('');
					$("#street_input").val('');
				}
				
				$.post(ext.path+'res/json/city1.json',function(data){
					var obj1 = data[citycode];
//					console.log(obj1);
					var obj2 = obj1.a;
//					console.log(obj2);
					var distincthtml = "";
					$.each(obj2,function(index,value){
						for(var key in value){
//							console.log(key+':'+value[key]);
							distincthtml += "<li data-id=" + key + " onclick='toBuy1.clickdistrict(this)'>" + value[key] + "</li>";
						}
					});
					$("#district_div ul").html(distincthtml);
				},'json');
				$(".popTipBoxL").hide();
				$('body,html').removeClass("noscroll");
				$("#city_div").hide();
			});
			
			$("#district_span").click(function(){
				var cityh = $("#city_input").val();
//				console.log(cityh);
				if("undefined" == typeof(cityh) || null == cityh || "" == cityh){
					toBuy1.showErr("请先选择城市。", "#city_input");
					return false;
				}
				$(".popTipBoxL").show();
				$('body,html').addClass("noscroll");
				$("#district_div").show();
			});
			
			$("#street_span").click(function(){
				var districth = $("#district_input").val();
				if("undefined" == typeof(districth) || null == districth || "" == districth){
					toBuy1.showErr("请先选择区域。", "#district_input");
					return false;
				}
				$(".popTipBoxL").show();
				$('body,html').addClass("noscroll");
				$("#street_div").show();
			});
			
			$("#city_input").change(function(){
				$("#district_input").val('');
				$("#street_input").val('');
			});
			$("#district_input").change(function(){
				$("#street_input").val('');
			});
			
			$("#usedto_span").click(function(){
				$(".popTipBoxL").show();
				$('body,html').addClass("noscroll");
				$("#usedto_div").show();
			});
			$("#usedto_div li").click(function(){
				$("#usedto_input").val($(this).html());
				$(".popTipBoxL").hide();
				$('body,html').removeClass("noscroll");
				$("#usedto_div").hide();
			});
			$("#decorate_span").click(function(){
				$(".popTipBoxL").show();
				$('body,html').addClass("noscroll");
				$("#decorate_div").show();
			});
			$("#decorate_div li").click(function(){
				$("#decorate_input").val($(this).html());
				$(".popTipBoxL").hide();
				$('body,html').removeClass("noscroll");
				$("#decorate_div").hide();
			});
		},
		clickdistrict:function(obj){
			var district = $(obj).html();
			var districtcode = $(obj).attr("data-id");
			var district_i = $("#district_input").val();
			$("#district_input").val(district);
			if(district != district_i){
				$("#street_input").val('');
			}
			
//			console.log(districtcode);
			$.post(ext.path+'res/json/'+ districtcode +'.json',function(data){
				var streethtml = "";
				for(var key in data){
//					console.log(key + ":" + data[key]);
					streethtml += "<li data-id=" + key + " onclick='toBuy1.clickstreet(this)'>" + data[key] + "</li>";
				}
				$("#street_div ul").html(streethtml);
			},'json');
			$(".popTipBoxL").hide();
			$('body,html').removeClass("noscroll");
			$("#district_div").hide();
		},
		clickstreet:function(obj){
			var street = $(obj).html();
			var streetcode = $(obj).attr("data-id");
			$("#street_input").val(street);
//			console.log(streetcode);
			$(".popTipBoxL").hide();
			$('body,html').removeClass("noscroll");
			$("#street_div").hide();
		},
		init:function(){
			this.vars.openid = ext.openid;
			this.vars.storeId = ext.storeId;
			toBuy1.clickselectinit();
			$(".sureModifyBtnl").click(function(){
				toBuy1.submit();
			});
		}
};