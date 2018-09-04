var myHouse = {
		vars:{
			storeId:"",
			openid:"",
			type:"sale",
			page:0,
			limit:10
		},
		deal:null,
		doSearch:function(page,limit){
			var num = 0;
			myHouse.vars.page = page;
			myHouse.vars.limit = limit;
			$('.rentalHouseList').find(".waiting").remove();
			$.post(ext.path+"house/getMyHouse.do",myHouse.vars,function(data){
				var str =$('.rentalHouseList').html();
				num = data.length;
				if(num>0){
					$.each(data,function(i,o){
						if(o.hs.trading_type=="sale"){
							str = houseUtil.showSaleLeftSlide(str,o);
						}else{
							str = houseUtil.showRentLeftSlide(str,o);
						}
					});
					$('.noData').hide();
					if(num==limit){
						str+="<li class='waiting'><span style='width:73%;'>加载中...</span></li>";
					}
					$('.rentalHouseList').html(str);
					houseUtil.leftSlide(".rentalHouseList");
					$('.upPic').click(function(){
						var houseid = $(this).parents("li").attr("id");
						location.href= ext.path+"page/changePic1.do?houseid="+houseid+"&storeId="+ext.storeId+"&openid="+ext.openid;
					});
					$('.del').click(function(){
						var obj= this; 
						$.post(ext.path+"house/getDelPermission.do",{openid:myHouse.vars.openid},function(data){
							if(data == 'y'){
								myHouse.showDel(obj);
							} else {
								myHouse.showErr("您没有删除权限，请联系管理员.");
							}
						});
					});
					$('.update').click(function(){
						var houseid = $(this).parents("li").attr("id");
						location.href= ext.path+"page/upMyHouseById.do?houseid="+houseid+"&storeId="+ext.storeId+"&openid="+ext.openid;
					});
				}else{
					if(page==0){
						$('.noData').show();
					}
				}
			},'json');
			return num;
		},
		showErr:function(msg){
			var _w = (msg.length+2)*17.6;
			var _l = ($(window).width()-_w)/2;
			$('.popTip90').html(msg).css({"width":_w,"left":_l});
			$('.popTipBox1').show().fadeOut(2000);
		},
		showDel:function(obj){
			$('.my-alert').show().unbind().bind("click",function(){myHouse.hideDel();});
			$('.alert').show();
			$('.toast').hide();
			$('.cancel').unbind().bind("click",function(){myHouse.hideDel();});
			$('.confirm').unbind().bind("click",function(){
				var id = $(obj).parents("li").attr("id");
				console.log(obj);
				console.log($(obj));
				console.log(id);
				$.post(ext.path+"house/delMyHouseById.do",{houseid:id},function(data){
					console.log(data);
					if(data){
						$(obj).parents("li").remove();
						if($('.rentalHouseList li').length==0){
							$('.noData').show();
						}
						myHouse.showToast("操作成功");
					}else{
						myHouse.showToast("操作失败");
					}
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
		changeType:function(obj){
			$('.tit').parent("li").removeClass("tabActive");
			$(obj).addClass("tabActive");
			myHouse.vars.type = $(obj).find('.tit').attr("name");
			$('.rentalHouseList').html('');
			paginate.init(0,10,myHouse.doSearch);
		},
		init:function(){
			$('.rentalHouseList').html('');
			this.vars.storeId = ext.storeId;
			this.vars.openid = ext.openid;
			$('.tab_bar li').click(function(){myHouse.changeType(this);});
			paginate.init(0,10,this.doSearch);
		}
};