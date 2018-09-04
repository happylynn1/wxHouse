var clientHousePub4 = {
	houseid:"",
	openid:"",
	getClientList:function(){
		$('.mesTipsList').html('');
		$.post(ext.path+"house/getRandAgent.do",{storeid:ext.storeId},function(data){
			if(data.length>0){
				var str ='';
				$.each(data,function(i,d){
					var name = (d.name!=null&&d.name!="") ? d.name : d.nickname;
					if(i == 0){
						str+="<li><div class='photoBox'><i class='choice choiceYes' id="+d.openid+"></i><img src='"+d.head_image+"' /></div>"+
						"<div class='mesBox'><h6><b>"+ name +"</b><span></span></h6></li>";
					} else {
						str+="<li><div class='photoBox'><i class='choice choiceNo' id="+d.openid+"></i><img src='"+d.head_image+"' /></div>"+
						"<div class='mesBox'><h6><b>"+ name +"</b><span></span></h6></li>";
					}					
				});
				$('.mesTipsList').html(str);
				$('.choice').click(function(){
					$('.choice').each(function(i,b){
						$(b).removeClass("choiceYes").addClass("choiceNo");
					});
					$(this).removeClass("choiceNo").addClass("choiceYes");
				});
				$('.sureModifyBtn').click(function(){
					var agentopenid = $('.choiceYes').attr("id");
					if(agentopenid != null){
						$.post(ext.path+"house/updateClienthouseByAgentopenid.do",{"houseid":clientHousePub4.houseid,"agentopenid":agentopenid,"storeid":ext.storeId,"clientopenid":clientHousePub4.openid},function(){
							location.href=ext.path+"page/entrustSuccess.do"
						});
					} else {
						alert("请选择委托经纪人。");
					}
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