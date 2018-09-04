var agentHousePub2 = {
	type:"",
	picPath:"",
	showPictureList:function(){
		$('.huxingtu2').html('');
		$.post(ext.path+"picture/getPictureListByOpenid.do",function(data){
			var str = "";
			if(data.length>0){
				$.each(data,function(i,p){
					str += "<li><img name="+p.filename+" src="+p.imagepath+" /><i onclick='agentHousePub2.takePicture(this)' class='radioImgBtn'></i></li>";
				});
			}else{
				str += "<li><img src="+ext.defaultHouseImage+" /><i onclick='agentHousePub2.takePicture(this)' class='radioImgBtn'></i></li>";
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
		agentHousePub2.picPath = $(obj).prev("img").attr("src");
	},
	init:function(){
		this.showPictureList();
		$('#agentHousePub3').click(function(){
			location.href= ext.path+"page/"+$(this).attr("id")+".do?type="+agentHousePub2.type+"&picPath="+agentHousePub2.picPath;
		});
	}
};