var changePic1 = {
	count:0,
	type:"",
	houseid:"",
	maxCount:9,
	vcount:0,
	maxVcount:6,
	showPictureList:function(){
		$('.huxingtu').html('');
		$.post(ext.path+"picture/getPictureListByOpenidAndHouseId.do",{openid:ext.openid,houseid:changePic1.houseid},function(data){
			var str = "";
			$.each(data,function(i,p){
				str += "<li><img name="+p.filename+" src="+p.imagepath+" /><i onclick='changePic1.delPicture(this)' class='deleteImgBtn'></i></li>";
			});
			changePic1.count = data.length;
			$('.fright').html(changePic1.count+"/"+changePic1.maxCount);
			$('.huxingtu').html(str);
			if($('.huxingtu li').length<changePic1.maxCount){
				$('.huxingtu').html($('.huxingtu').html()+"<li><a class='addImg_Btn' title='点击添加户型图' onclick='changePic1.takePicture()'></a></li>");
			}
		},"json");
	},
	showVideo:function(){
		$('.huxing_sp').html('');
		$('.vright').html(changePic1.vcount+"/"+changePic1.maxVcount);
		$('.huxing_sp').html("<li><a class='addImg_Btn' title='点击添加房源视频' onclick='changePic1.takeVideo()'></a></li>");
		$.post(ext.path+"picture/getVideoByHouseid.do",{houseid:changePic1.houseid},function(data){
			var str = "";
			changePic1.vcount = data.length;
			$.each(data,function(i,json){
				str += "<li><img name='" + json.filename + "' src='" + ext.path + "houseVideo/" + json.filename.substring(0,30) + "png' alt='视频第一帧截图'><i onclick='changePic1.delVideo(this)' class='deleteImgBtn'></i></li>";
			});
			$(".huxing_sp").prepend(str);
			if(changePic1.vcount == 5){
				$('.huxing_sp').append("<li><a class='addImg_Btn' title='点击添加房源视频' onclick='changePic1.takeVideo()'></a></li>");
			}
		},'json');
		// 删除用户上传但是没有绑定房源的视频，包括数据库记录和硬盘文件。
		$.post(ext.path+"picture/delVideoByOpenid.do",{openid:ext.openid,storeId:ext.storeId});
	},
	takeVideo:function(){
		$("#video_upload").click();
		$("#video_upload").unbind().bind("change",function(){
			var fileSize = document.getElementById("video_upload").files[0].size;
			console.log(fileSize);
			if(fileSize > 20 * 1024 * 1024){
				changePic1.showErr("上传文件不能大于20M.");
				return ;
			}
			var w = $(window).width();
			var h = $(window).height();

			$("#bg_gray").css('width',w);
			$("#bg_gray").css('height',h);
			$("#bg_gray").show();
			
			$("#progressbar").css('width',w / 3 * 2);
			$("#progressbar").css('top',h / 5 * 2);
			$("#progressbar").css('left',w / 2 - w / 3);
			$("#progressbar").show();
						
			setTimeout(function(){
				var xhr = new XMLHttpRequest();
				var fd = new FormData(document.getElementById('upload'));
				fd.append("openid",ext.openid);
				fd.append("storeId",ext.storeId);
				xhr.upload.addEventListener("progress", changePic1.uploadProgress, false);
		    	xhr.addEventListener("load", changePic1.uploadComplete, false);
		    	
		    	xhr.open("POST", ext.path+"picture/saveVideoByHouseid.do?houseid="+changePic1.houseid);
		    	xhr.send(fd);
		    	xhr.onreadystatechange = function(){
		    		if(xhr.readyState === 4 && xhr.status === 200) {
		    		    console.log(xhr.responseText);
		    		    var json = eval('(' + xhr.responseText + ')');
		    		    console.log(json.urlpath);
		    		    if(json.success){
		    		    	var videohtml = "<li><img name='" + json.filename + "' src='" + ext.path + "houseVideo/" + json.filename.substring(0,30) + "png' alt='视频第一帧截图'><i onclick='changePic1.delVideo(this)' class='deleteImgBtn'></i></li>";
		    		    	$(".huxing_sp").prepend(videohtml);
		    		    	changePic1.vcount += 1;
		    		    	if(changePic1.vcount == 6){
		    		    		$(".huxing_sp .addImg_Btn").parent("li").remove();
		    		    	}		    		    
		    		    	$('.vright').html(changePic1.vcount+"/"+changePic1.maxVcount);
		    		    }else{
		    		    	alert(json.msg);
		    		    }
		    		} else {
		    			console.log("State error:" + xhr.responseText + "--" + xhr.statusText);
		    		}	    			
		    	};
		    }, 1000);
		});
	},
	showErr:function(msg){
		var _w = (msg.length+2)*17.6;
		var _l = ($(window).width()-_w)/2;
		$('.popTip90').html(msg).css({"width":_w,"left":_l});
		$('.popTipBox1').show().fadeOut(2000);
	},
	uploadProgress:function(evt){
		if(evt.lengthComputable){
			var percentComplete = Math.round(evt.loaded * 100 / evt.total);
			changePic1.progressfn(percentComplete);
		}
	},
	progressfn:function(cent){
		var progressbar = document.getElementById("progressbar");
        progressbar = progressbar.getElementsByTagName("p");
        progressbar[0].innerHTML = cent + "%";
        var progress = document.getElementById("progress");
        progress.style.width = cent + "%";
	},
	uploadComplete:function(){
		$("#progressbar").hide();
		$("#bg_gray").hide();
	},
	delVideo:function(o){
		$.post(ext.path+"picture/delVideoByFilenameAndHouseid.do",{filename:$(o).prev("img").attr("name"),houseid:changePic1.houseid},function(data){
			$(o).parent().remove();
			changePic1.vcount -= 1;
			if(changePic1.vcount == 5){
				$('.huxing_sp').append("<li><a class='addImg_Btn' title='点击添加房源视频' onclick='changePic1.takeVideo()'></a></li>");
			}
			$('.vright').html(changePic1.vcount+"/"+changePic1.maxVcount);
		},'json');
	},
	
	takePicture:function(){
		wx.chooseImage({
	        count: changePic1.maxCount-changePic1.count, // 默认9
	        sizeType: ['original', 'compressed'],
	        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	        success: function (res) {
	            //res.localIds 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        	changePic1.uploadImage(res.localIds);
	        }
	    });
	},
	uploadImage:function(localIds){
		var localId = localIds.shift();
    	wx.uploadImage({
            localId: localId.toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
            isShowProgressTips: 1, // 默认为1，显示进度提示
            success: function (res) {
               var mediaId = res.serverId; // 返回图片的服务器端ID，即mediaId
               $.post(ext.path+"picture/savePictureByHouseid.do",{mediaId:mediaId,houseid:changePic1.houseid},function(data){
            	    if(data.success){
            	    	$('.huxingtu li:last-child').remove();
            	    	$('.huxingtu').html($('.huxingtu').html()+"<li><img name="+data.picture.filename+" src="+data.picture.imagepath+" /><i onclick='changePic1.delPicture(this)' class='deleteImgBtn'></i></li>");
            	    	changePic1.count += 1;
            	    	$('.fright').html(changePic1.count+"/"+changePic1.maxCount);
            	    	if($('.huxingtu li').length<changePic1.maxCount){
            	    		$('.huxingtu').html($('.huxingtu').html()+"<li><a class='addImg_Btn' title='点击添加户型图' onclick='changePic1.takePicture()'></a></li>");
            	    	}
            	    	if(localIds.length > 0){
            	    		changePic1.uploadImage(localIds);
            	    	}
            	    }else{
            	    	alert(data.msg);
            	    }
               },"json");
            },
            fail: function (res) {
                alert('上传图片失败，请重试');
            }
        }); 
	},
	delPicture:function(o){
		$.post(ext.path+"picture/delPictureByHouseid.do",{filename:$(o).prev("img").attr("name"),houseid:changePic1.houseid},function(){
			$(o).parent("li").remove();
			changePic1.count-=1;
			$('.fright').html(changePic1.count+"/"+changePic1.maxCount);
			if(changePic1.maxCount - changePic1.count == 1){
				$('.huxingtu').html($('.huxingtu').html()+"<li><a class='addImg_Btn' title='点击添加户型图' onclick='changePic1.takePicture()'></a></li>");
			}
		});
	},
	init:function(){
		initjsSDK(location.href,['chooseImage', 'uploadImage','hideAllNonBaseMenuItem']);
		setTimeout(function(){wx.hideAllNonBaseMenuItem();},1000);
		this.showPictureList();
		this.showVideo();
		$('#changePic2').click(function(){
			location.href= ext.path+"page/"+$(this).attr("id")+".do?houseid="+changePic1.houseid;
		});
	}
};