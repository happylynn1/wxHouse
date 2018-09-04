var storeHot={
		vars:{
			openid:"",
			storeId:"",
			page:0,
			limit:10
		},
		doSearch:function(page,limit){
			var num = 0;
			storeHot.vars.page = page;
			storeHot.vars.limit = limit;
			$('.hotHouseList').find(".waiting").remove();
			$.post(ext.path+"house/getStoreHotHouseList.do",storeHot.vars,function(data){
				num = data.length;
				if(num>0){
					var str=$('.hotHouseList').html();
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
					$('.hotHouseList').html(str);
				}else{
					if(page==0){
						$('.noData').show();
					}
				}
			},'json');
			return num;
		},
		init:function(){
			this.vars.storeId = ext.storeId;
			$('.hotHouseList').html('');
			paginate.init(0,10,this.doSearch);
			$('a').click(function(){
				var cl = $(this).find("p").eq(0).attr("class");
				switch(cl){
					case "menu-1":
						location.href = ext.path+"page/toComm.do?openid="+storeHot.vars.openid+"&storeId="+storeHot.vars.storeId;
						break;
					case "menu-2":
						location.href = ext.path+"page/toSale.do?openid="+storeHot.vars.openid+"&storeId="+storeHot.vars.storeId;
						break;
					case "menu-3":
						location.href = ext.path+"page/toRent.do?openid="+storeHot.vars.openid+"&storeId="+storeHot.vars.storeId;						
						break;
					case "menu-4":
						location.href = ext.path+"page/toAgentList.do?openid="+storeHot.vars.openid+"&storeId="+storeHot.vars.storeId;						
						break;
				}
			});
		}
};