var entrustHouse2 = {
	clientopenid:"",
	houseid:"",
	picPath:"",
	showPictureList:function(){
		$('.huxingtu2').html('');
		$.post(ext.path+"picture/getPictureListByOpenidAndHouseId.do",{openid:ext.openid,houseid:entrustHouse2.houseid},function(data){
			var str = "";
			if(data.length>0){
				$.each(data,function(i,p){
					str += "<li><img name="+p.filename+" src="+p.imagepath+" /><i onclick='entrustHouse2.takePicture(this)' class='radioImgBtn'></i></li>";
				});
			}else{
				str += "<li><img src="+ext.defaultHouseImage+" /><i onclick='entrustHouse2.takePicture(this)' class='radioImgBtn'></i></li>";
			}
			$('.huxingtu2').html(str).find("li").eq(0).find("i").click();
		},"json");
	},
	takePicture:function(obj){
		if(!$(obj).hasClass("radioImgBtnOn")){
			$(obj).parents(".huxingtu2").find("li").each(function(i,b){
				$(b).find("i").removeClass("radioImgBtnOn").addClass("radioImgBtn");
			});
			$(obj).removeClass("radioImgBtn").addClass("radioImgBtnOn");
		}
		entrustHouse2.picPath = $(obj).prev("img").attr("src");
	},
	init:function(){
		this.showPictureList();
		$('.sureModifyBtn').click(function(){
			$.post(ext.path+"picture/upPictureByPicName.do",{openid:ext.openid,houseid:entrustHouse2.houseid,picPath:entrustHouse2.picPath},function(data){
				if(data){
					location.href= ext.path+"page/entrustHouse3.do?storeId="+ext.storeId+"&openid="+ext.openid+"&houseid="+entrustHouse2.houseid+"&clientopenid="+entrustHouse2.clientopenid;
				}
			});
		});
	}
};