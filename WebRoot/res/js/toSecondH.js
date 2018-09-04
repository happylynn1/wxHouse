var toSecondH={
		vars:{
			openid:"",
			storeId:"",
			type:'sale',
			genre:"secondH",
			keys:"",
			page:0,
			limit:10
		},
		setKeys:function(){
			location.href = ext.path+"page/toRentSale.do?focus=1&openid="+toSecondH.vars.openid+"&storeId="+toSecondH.vars.storeId;
		},
		doSearch:function(page,limit){
			var num = 0;
			toSecondH.vars.page = page;
			toSecondH.vars.limit = limit;
			$('.rentalHouseList').find(".waiting").remove();
			$.post(ext.path+"house/getHouseListByType.do",toSecondH.vars,function(data){
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
			this.vars.storeId = ext.storeId;
			$('.hotHouseList').html('');
			paginate.init(0,10,this.doSearch);
			$('a').click(function(){
				var u = $(this).find("p").attr("data-name");
				if(u.indexOf("?")>=0){
					location.href = ext.path+"page/"+$(this).find("p").attr("data-name")+"&openid="+toSecondH.vars.openid+"&storeId="+toSecondH.vars.storeId;
				}else{
					location.href = ext.path+"page/"+$(this).find("p").attr("data-name")+"?openid="+toSecondH.vars.openid+"&storeId="+toSecondH.vars.storeId;
				}
			});
		}
};