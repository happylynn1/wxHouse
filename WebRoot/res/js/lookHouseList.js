var lookHouseList={
	id:"",
	openid:"",
	isAgent:"",
	getNum:function(g){
		var are = document.getElementById('introduce'); 
		var num = document.getElementById('num'); 
		if(g.value.length > 20){ 
			are.value = are.value.substring(0,20); 
		} else{ 
			num.innerHTML = ""; 
			num.innerHTML = g.value.length.toString(); 
		} 
	},
	doSearch:function(page,limit){
		var num = 0;
		$('.advisorList2').find(".waiting").remove();
		$.post(ext.path+"lookHouse/getLookHouseList.do",{openid:lookHouseList.openid,page:page,limit:limit},function(data){
			num = data.list.length;
			lookHouseList.isAgent = data.isAgent||"0";
			if(num>0){
				var str = $('.advisorList2').html();
				$.each(data.list,function(i,o){
					str = lookHouseUtil.showList(str,o);
				});
				$('.noData').hide();
				num==limit&&(str+="<li class='waiting'><span style='width:79.3%'>加载中...</span></li>");
				$('.advisorList2').html(str);
				houseUtil.leftSlide(".advisorList2");
				$('.del').click(function(){
					lookHouseList.showDel(this);
				});
				$('.cancel').click(function(){
					lookHouseList.showCancel(this);
				});
			}else{
				if(page==0){
					$('.noData').show();
				}
			}
		},"json");
		return num;
	},
	showDel:function(obj){
		$('.my-alert').show().unbind().bind("click",function(){lookHouseList.hideDel();});
		$('.alert').show();
		$('.toast').hide();
		$('.cancel').unbind().bind("click",function(){lookHouseList.hideDel();});
		$('.confirm').unbind().bind("click",function(){
			var id = $(obj).parents("li").attr("id");
			$.post(ext.path+"lookHouse/delLookHouse.do",{id:id,isAgent:lookHouseList.isAgent},function(data){
				data?($(obj).parents("li").remove(),($('.advisorList2 li').length==0)&&($('.noData').show()),
				lookHouseList.showToast("操作成功")):lookHouseList.showToast("操作失败");
			});
		});
	},
	hideDel:function(){
		$('.my-alert').hide();
	},
	showToast:function(msg){
		$('.alert').hide();
		$('.my-alert').show().unbind().fadeOut(2000);
		$('.toast').html(msg).show().fadeOut(2000);
	},
	showCancel:function(obj){
		$('.popTipBoxL3').show();
		$('.tc_next3,#button_xx').unbind().bind("click",function(){
			$('.popTipBoxL3').hide();
		});
		$('#button_qd').unbind().bind("click",function(){
			var toOpenid = $(obj).parents("li").find(".chatBtn2").attr("data-openid");
			var id = $(obj).parents("li").attr("id"),reason=$('textarea').val();
			$.post(ext.path+"lookHouse/cancelLookHouse.do",{id:id,reason:reason,openid:lookHouseList.openid,storeId:ext.storeId,toOpenid:toOpenid,isAgent:lookHouseList.isAgent,},function(data){
				data?($('.popTipBoxL3').hide(),$(obj).parents("li").remove(),($('.advisorList2 li').length==0)&&($('.noData').show()),
				lookHouseList.showToast("操作成功")):lookHouseList.showToast("操作失败");
			});
			$('.popTipBoxL3').hide();
		});
	},
	init:function(){
		this.openid = ext.openid;
		paginate.init(0,this.id?1000:10,this.doSearch);
		setTimeout(function(){
			if(lookHouseList.id){
				$(".advisorList2 li").each(function(){
					var obj = $(this);
					if(obj.attr("id")==lookHouseList.id){
						var _h = obj.offset().top;
						$('body,html').stop().animate({"scrollTop":_h},1000,function(){
							obj.shake(2, 10, 400);
						});
					}
				});
			}
		},500);
	}
};