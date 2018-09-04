var myClient ={
		openid:"",
		doSearch:function(){
			$.post(ext.path+"contact/getMyClient.do",{openid:myClient.openid,storeId:ext.storeId},function(data){
				$('.advisorList').html('');
				if(data.length>0){
					var str="";
					$.each(data,function(i,o){
						str=persionUtil.showClient(str, o);
					});
					$('.noData').hide();
					$('.advisorList').html(str);
					houseUtil.leftSlide(".advisorList");
					$('.del').click(function(){
						myClient.showDel(this);
					});
				}else{
					$('.noData').show();
				}
			},'json');
		},
		hideDel:function(){
			$('.my-alert').hide();
		},
		showToast:function(msg){
			$('.alert').hide();
			$('.my-alert').show().unbind().fadeOut(2000);
			$('.toast').html(msg).show().fadeOut(2000);
		},
		showDel:function(obj){
			$('.my-alert').show().unbind().bind("click",function(){myClient.hideDel();});
			$('.alert').show();
			$('.toast').hide();
			$('.cancel').unbind().bind("click",function(){myClient.hideDel();});
			$('.confirm').unbind().bind("click",function(){
				var toOpenid = $(obj).parents("li").attr("id");
				$.post(ext.path+"contact/addContactFilter.do",{openid:myClient.openid,toOpenid:toOpenid},function(data){
					if(data){
						$(obj).parents("li").remove();
						if($('.advisorList li').length==0){
							$('.noData').show();
						}
						myClient.showToast("操作成功");
					}else{
						myClient.showToast("操作失败");
					}
				});
			});
		},
		init:function(){
			this.openid = ext.openid;
			this.doSearch();
		}
};