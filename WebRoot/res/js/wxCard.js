var wxCard = {
		openid:"", 
		doSearch:function(){
			$.post(ext.path+"agent/getMyWxCard.do",{openid:wxCard.openid,storeId:ext.storeId},function(data){
				$('#head_image').attr("src",data.agent.head_image);
				$('#name').html(data.agent.name);
				$('#phone').html("Tel / "+data.agent.phone);
				$('#storeName').html(data.agent.storeName);
				$('#speciality').html(data.agent.speciality);
				var workperiod="暂无";
				if(data.agent.workperiod!=null){
					if(data.agent.workperiod.indexOf("年")>=0){
						workperiod = data.agent.workperiod;
					}else{
						workperiod = data.agent.workperiod+"年";
					}
				}
				$('#workperiod').html(workperiod);
				$('#qrcode').attr("src",data.qrcode);
			},'json');
		},
		init:function(){
			this.openid= ext.openid;
			this.doSearch();
		}
};