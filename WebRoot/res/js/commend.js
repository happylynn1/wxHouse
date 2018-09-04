var commend ={
		vars:{
			openid:"",
			storeId:"",
			type:"rent",
			page:0,
			limit:10
		},
		doSearch:function(page,limit){
			var num = 0;
			commend.vars.page = page;
			commend.vars.limit = limit;
			$('.rentalHouseList').find(".waiting").remove();
			$.post(ext.path+"recommend/getRecommendListByClientOpenid.do",commend.vars,function(data){
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
		changeType:function(obj){
			$('.tit').parent("li").removeClass("tabActive");
			$(obj).addClass("tabActive");
			commend.vars.type = $(obj).find('.tit').attr("name");
			$('.rentalHouseList').html('');
			paginate.init(0,10,this.doSearch);
		},
		init:function(){
			$('.rentalHouseList').html('');
			this.vars.openid = ext.openid;
			this.vars.storeId = ext.storeId;
			$('.tab_bar li').click(function(){commend.changeType(this);});
			paginate.init(0,10,this.doSearch);
		}
};