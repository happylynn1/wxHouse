var agentHousePub4 = {
	houseid:"",
	openid:"",
	openidArr:"",
	setOpenidArr:function(){
		agentHousePub4.openidArr ="";  
		$('.choiceYes').each(function(i){
			agentHousePub4.openidArr += $(this).attr("id")+",";
		});
	},
	getClientList:function(){
		$('.mesTipsList').html('');
		$.post(ext.path+"recommend/getClientRecommendListByOpenid.do",{openid:agentHousePub4.openid,houseid:agentHousePub4.houseid},function(data){
			if(data.length>0){
				var str ='';
				$.each(data,function(i,d){
					console.log((d.comm==1?"ac":""));
					str+="<li><div class='photoBox'><i class='choice choiceNo' id="+d.openid+"></i><img src='"+d.headImage+"' /></div>"+
						"<div class='mesBox'><h6><b>"+d.nickname+"</b><span></span></h6>"+
						"<div class='markBox'><span class="+(d.comm==1?"ac":"")+">小区</span><span class="+(d.room==1?"ac":"")+">居室</span>" +
						"<span class="+(d.price==1?"ac":"")+">价格</span></div></div></li>";
				});
				$('.mesTipsList').html(str);
				$('.choice').click(function(){
					if($(this).hasClass("choiceNo")){
						$(this).removeClass("choiceNo").addClass("choiceYes");
					}else{
						$(this).removeClass("choiceYes").addClass("choiceNo");
					}
				});
				$('.sureModifyBtn').click(function(){
					agentHousePub4.setOpenidArr();
					$.post(ext.path+"recommend/batchSend.do",{"openidArr":agentHousePub4.openidArr,"houseid":agentHousePub4.houseid,"openid":agentHousePub4.openid},function(){
						wx.closeWindow();
					});
				});
			}else{
				$('.mesTips').html('系统检测您还没有添加客户,不能发送消息提醒');
				$('.sureModifyBtn').html('返回').click(function(){
					wx.closeWindow();
				});
			}
		},"json");
	},
	init:function(){
		this.openid=ext.openid;
		this.getClientList();
		initjsSDK(location.href,['closeWindow']);
	}
};