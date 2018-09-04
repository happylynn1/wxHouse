var dealRentRequire ={
		vars:{
			openid:"",
			storeId:"",
			requireid:"",
			type:"求租"
		},
		pageInit:function(){
			$.post(ext.path+'require/getRequireByRequireid.do',{requireid:dealRentRequire.vars.requireid},function(data){
				if(data){
					$("#address_span").html(data.city+data.district+data.street);
					$("#usedto_span").html(data.usedto);
					$("#decorate_span").html(data.decorate);
					$("#renttype_span").html(data.rent_type);
					$("#price_span").html(data.price+"元");
					var ss = data.room.split("");
					var room = ss[0] + "室" + ss[1] + "厅" + ss[2] + "卫";
					$("#room_span").html(room);
					$("#area_span").html(data.area+"平米");
					var remark = data.remark ? data.remark : "暂无描述";
					$(".editIntrotext2 li").html(remark);
					$("#name_span").html(data.name);
					$("#phone_span").html(data.phone);
					$(".sureModifyBtnl").attr("href","tel:"+data.phone+"");
				}
			},"json");
		},
		init:function(){
			this.vars.openid = ext.openid;
			this.vars.storeId = ext.storeId;
			dealRentRequire.pageInit();
		}
};