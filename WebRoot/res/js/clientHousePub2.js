var clientHousePub2 = {
	openid:"",
	houseid:"",
	count:0,
	type:"",
	maxCount:5,
	showPictureList:function(){
		$('.huxingtu').html('');
		$.post(ext.path+"picture/getPictureListByOpenid.do",function(data){
			var str = "";
			$.each(data,function(i,p){
				str += "<li><img name="+p.filename+" src="+p.imagepath+" /><i onclick='clientHousePub2.delPicture(this)' class='deleteImgBtn'></i></li>";
			});
			clientHousePub2.count = data.length;
			$('.fright').html(clientHousePub2.count+"/"+clientHousePub2.maxCount);
			$('.huxingtu').html(str);
			if($('.huxingtu li').length<clientHousePub2.maxCount){
				$('.huxingtu').html($('.huxingtu').html()+"<li><a class='addImg_Btn' title='点击添加户型图' onclick='clientHousePub2.takePicture()'></a></li>");
			}
		},"json");
	},
	takePicture:function(){
		wx.chooseImage({
	        count: clientHousePub2.maxCount-clientHousePub2.count, // 默认9
	        sizeType: ['original', 'compressed'],
	        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	        success: function (res) {
	            clientHousePub2.uploadImage(res.localIds);
	        }
	    });
	},
	uploadImage:function(localIds){
		var localId = localIds.pop();
		wx.uploadImage({
            localId: localId.toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
            isShowProgressTips: 1, // 默认为1，显示进度提示
            success: function (res) {
               var mediaId = res.serverId; // 返回图片的服务器端ID，即mediaId
               $.post(ext.path+"picture/savePicture.do",{mediaId:mediaId},function(data){
            	    $('.huxingtu li:last-child').remove();
            	    $('.huxingtu').html($('.huxingtu').html()+"<li><img name="+data.filename+" src="+data.imagepath+" /><i onclick='clientHousePub2.delPicture(this)' class='deleteImgBtn'></i></li>");
            	    clientHousePub2.count += 1;
            	    $('.fright').html(clientHousePub2.count+"/"+clientHousePub2.maxCount);
            	    if($('.huxingtu li').length<clientHousePub2.maxCount){
       					$('.huxingtu').html($('.huxingtu').html()+"<li><a class='addImg_Btn' title='点击添加户型图' onclick='clientHousePub2.takePicture()'></a></li>");
       				}
            	    if(localIds.length > 0){
            	    	clientHousePub2.uploadImage(localIds);
            	    }
               },"json");
            },
            fail: function (res) {
                alert('上传图片失败，请重试');
            }
        }); 
	},
	delPicture:function(o){
		$.post(ext.path+"picture/delPicture.do",{filename:$(o).prev("img").attr("name")},function(){
			$(o).parent("li").remove();
			clientHousePub2.count-=1;
			$('.fright').html(clientHousePub2.count+"/"+clientHousePub2.maxCount);
			if(clientHousePub2.maxCount-clientHousePub2.count ==1){
				$('.huxingtu').html($('.huxingtu').html()+"<li><a class='addImg_Btn' title='点击添加户型图' onclick='clientHousePub2.takePicture()'></a></li>");
			}
		});
	},
	init:function(){
		this.openid = ext.openid;
		initjsSDK(location.href,['chooseImage', 'uploadImage','closeWindow']);
		this.showPictureList();
		$('.sureModifyBtn').click(function(){
			$.post(ext.path+"picture/upClientPicture.do",{openid:clientHousePub2.openid,houseid:clientHousePub2.houseid},function(){
				wx.closeWindow();
			});
		});
		$('.sureModifyBtn_gray').click(function(){
			wx.closeWindow();
		});
	}	
};