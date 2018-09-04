var rentHouse = {
	vars:{
		storeId:"",
		type:"rent",
		openid:"",
		keys:"",
		room:"",
		price:"",
		towards:"",
		page:0,
		limit:10
	},
	setKeys:function(){
		this.vars.keys = $('form input').val();
		$('.rentalHouseList').html('');
		paginate.init(0,10,rentHouse.doSearch);
	},
	doSearch:function(page,limit){
		$('#room').hide();
		$('#price').hide();
		$('#towards').hide();
		var num = 0;
		rentHouse.vars.page = page;
		rentHouse.vars.limit = limit;
		$('.rentalHouseList').find(".waiting").remove();
		$.post(ext.path+"house/getHouseListByType.do",rentHouse.vars,function(data){
			num = data.length;
			if(num>0){
				var str = $('.rentalHouseList').html();
				$.each(data,function(i,o){
					str = houseUtil.showRent(str,o);
				});
				$('.noData').hide();
				if(num==limit){
					str+="<li class='waiting'><span>加载中...</span></li>";
				}
				$('.rentalHouseList').html(str);
			}else{
				if(page==0){
					$('.noData').show();
				}
			}
		},'json');
		return num;
	},
	init:function(){
		this.vars.openid = ext.openid;
		this.vars.storeId = ext.storeId;
		$('.rentalHouseList').html('');
		paginate.init(0,10,this.doSearch);
		$('.tab_bar li').click(function(){
			$('#room,#price,#towards').hide();
			/*if($(this).hasClass("tabActive")){
				$(this).siblings().removeClass("tabActive");
			}else{*/
				$(this).addClass("tabActive").siblings().removeClass("tabActive");
				if($(this).hasClass("room")){
					$("#room li[data-name='"+rentHouse.vars.room+"']").addClass("activeOn").siblings("li").removeClass("activeOn");
					$('#room').show();
				}else if($(this).hasClass("towards")){
					$("#towards li[data-name='"+rentHouse.vars.towards+"']").addClass("activeOn").siblings("li").removeClass("activeOn");
					$('#towards').show();
				}else{
					$("#price li[data-name='"+rentHouse.vars.price+"']").addClass("activeOn").siblings("li").removeClass("activeOn");
					$('#price').show();
				}
			/*}*/
		});
		$("#towards li,#price li,#room li").click(function(){
			if($(this).parent("ul").attr("id")=="room"){
				rentHouse.vars.room = $(this).attr("data-name");
				if($(this).html()=="全部"){
					$(".room h2").html("居室");
				} else {
					$(".room h2").html($(this).html());
				}
			}else if($(this).parent("ul").attr("id")=="towards"){
				rentHouse.vars.towards = $(this).attr("data-name");
				if($(this).html()=="全部"){
					$(".towards h2").html("朝向");
				} else {
					$(".towards h2").html($(this).html());
				}
			}else{
				rentHouse.vars.price = $(this).attr("data-name");
				if($(this).html()=="全部"){
					$(".price h2").html("价格");
				} else {
					$(".price h2").html($(this).html());
				}
			}
			$(".tab_bar li").removeClass("tabActive");
			$('.rentalHouseList').html('');
			paginate.init(0,10,rentHouse.doSearch);
		});
	}
};