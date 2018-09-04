var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
var clientHousePub1 ={
		vars:{
			openid:"",
			storeId:"",
			type:"",
			name:"",
			phone:"",
			remark:""
		},
		setVars:function(){
			var u = $('.pubIntroList');
			this.vars.name = u.find('li').eq(0).find('input').val();
			this.vars.phone= u.find('li').eq(1).find('input').val();
			this.vars.remark=u.find('li').eq(2).find('textarea').val();
		},
		showErr:function(msg,obj){
			var _w = (msg.length+2)*17.6;
			var _l = ($(window).width()-_w)/2;
			$('.error').removeClass("error");
			$('.popTip90').html(msg).css({"width":_w,"left":_l});
			$('.popTipBox1').show().fadeOut(2000);
			setTimeout(function(){$(obj).val('').addClass("error").focus();},1500);
		},
		init:function(){
			this.vars.openid = ext.openid;
			this.vars.storeId = ext.storeId;
			$('.sureModifyBtn').click(function(){
				clientHousePub1.setVars();
				if(clientHousePub1.vars.name==''){
					clientHousePub1.showErr("称呼不能为空!","#name");
					return;
				}
				/*if(!myreg.test(clientHousePub1.vars.phone)){
					clientHousePub1.showErr("请输入正确的手机号码!","#phone");
					return;
				}*/
				$.post(ext.path+"house/addClientHouse.do",clientHousePub1.vars,function(data){
					location.href=ext.path+"page/clientHousePub2.do?houseid="+data;
				});
			});
			$('input').focus(function(){
				$(this).css("border-color","#d2d2d2");
			});
		}
};