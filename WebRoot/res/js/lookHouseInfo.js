var lookHouseInfo={
	id:"",
	openid:"",
	isAgent:"",
	doSearch:function(){
		$.post(ext.path+"lookHouse/getLookHouseInfo.do",{id:lookHouseInfo.id,storeId:ext.storeId,openid:lookHouseInfo.openid},function(data){
			$('#houseInfo').html("预约房源：<a href='javascript:;'>"+data.houseInfo+"</a>");
			$('#time').html("预约时间："+data.time);
			$('#reason').html("预约取消原因："+data.reason);
			$('#phone').attr("href",data.phone?("tel:"+data.phone):"javascript:alert('暂未填写电话')");
			$('#houseInfo a').click(function(){
				houseUtil.showInfo(data.houseid);
			});
		},'json');
	},
	init:function(){
		this.openid = ext.openid;
		this.doSearch();
	}
};