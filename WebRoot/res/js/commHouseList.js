var commHouseList = {
		vars:{
			storeId:"",
			openid:"",
			type:"",
			keys:"",
			page:0,
			limit:10
		},
		doSearch:function(page,limit){
			var num = 0;
			commHouseList.vars.page = page;
			commHouseList.vars.limit = limit;
			$('.rentalHouseList').find(".waiting").remove();
			$.post(ext.path+"house/getHouseListByCommType.do",commHouseList.vars,function(data){
				num = data.length;
				if(num>0){
					var str=$('.rentalHouseList').html();
					$.each(data,function(i,o){
						if(commHouseList.vars.type=="sale"){
							str=houseUtil.showSale(str,o);
						}else{
							str=houseUtil.showRent(str,o);
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
			$('.rentalHouseList').html('');
			this.vars.storeId = ext.storeId;
			this.vars.openid = ext.openid;
			if(this.vars.type=="sale"){
				document.title='出售房源列表';
			}else{
				document.title='出租房源列表';
			}
			paginate.init(0,10,this.doSearch);
		}
};