var myCollect = {
		vars:{
			openid:"",
			type:"rent",
			page:0,
			limit:10
		},
		getMyCollect:function(page,limit){
			var num = 0;
			myCollect.vars.page = page;
			myCollect.vars.limit = limit;
			$('.rentalHouseList').find(".waiting").remove();
			$.post(ext.path+"collect/getMyCollect.do",myCollect.vars,function(data){
				num = data.length;
				if(num>0){
					var str =$('.rentalHouseList').html();
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
			myCollect.vars.type = $(obj).find('.tit').attr("name");
			$('.rentalHouseList').html('');
			paginate.init(0,10,myCollect.getMyCollect);
		},
		init:function(){
			$('.rentalHouseList').html('');
			myCollect.vars.openid = ext.openid;
			$('.tab_bar li').click(function(){
				myCollect.changeType(this);
			});
			paginate.init(0,10,this.getMyCollect);
		}
};