var toRentSale = {
	vars:{
		storeId:"",
		type:"sale",
		openid:"",
		keys:"",
		room:"",
		price:"",
		towards:"",
		genre:"",
		page:0,
		limit:10
	},
	setKeys:function(){
		this.vars.keys = $('form input').val();
		$('.rentalHouseList').html('');
		paginate.init(0,10,toRentSale.doSearch);
	},
	doSearch:function(page,limit){
		$('#room,#priceSale,#priceRent,#towards').hide();
		var num = 0;
		toRentSale.vars.page = page;
		toRentSale.vars.limit = limit;
		$('.rentalHouseList').find(".waiting").remove();
		$.post(ext.path+"house/getHouseListByType.do",toRentSale.vars,function(data){
			num = data.length;
			if(num>0){
				var str = $('.rentalHouseList').html();
				$.each(data,function(i,o){
					if(o.hs.trading_type=="sale"){
						str = houseUtil.showSale(str,o);
					}else{
						str = houseUtil.showRent(str,o);
					}
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
	init:function(focus){
		$(document).on("click", function(e){
			var _e = $(e.target);
			if((!_e.hasClass("SoBox"))&&(!_e.parent().hasClass("SoBox"))){
				$('.SoCon').hide();
			}
			if((!_e.hasClass("tab_bar"))&&(!_e.parents("ul").hasClass("tab_bar"))){
				$('#room,#priceSale,#priceRent,#towards').hide();
			}
		});
		this.vars.openid = ext.openid;
		this.vars.storeId = ext.storeId;
		this.vars.genre = this.vars.genre||"secondh";
		$(".SoCon li[data-name='"+toRentSale.vars.genre+"']").addClass("on");
		$('.SoBox h2').html($(".SoCon li[data-name='"+toRentSale.vars.genre+"']").html());
		if(focus){
			$('.input').focus();
		}else{
			$('.rentalHouseList').html('');
			paginate.init(0,10,this.doSearch);
		}
		
		$('.SoBox').click(function(){
			$('.SoCon').show();
			$('#room,#priceSale,#priceRent,#towards').hide();
		});
		$('.SoCon li').click(function(){
			$(this).addClass("on").siblings().removeClass("on");
			var ge = $(this).attr("data-name");
			var aa = toRentSale.vars.type;
			if(ge=="rent"){
				toRentSale.vars.genre = "";
				toRentSale.vars.type = "rent";
			}else{
				toRentSale.vars.genre = ge;
				toRentSale.vars.type = "sale";
			}
			if(aa!=toRentSale.vars.type){
				toRentSale.vars.price="";
				$(".price h2").html("价格");
			}
			$('.SoBox h2').html($(this).html());
			$('.SoCon').hide();
			$('.rentalHouseList').html('');
			paginate.init(0,10,toRentSale.doSearch);
		});
		$('.tab_bar li').click(function(){
			$('#room,#priceSale,#priceRent,#towards').hide();
			$(this).addClass("tabActive").siblings().removeClass("tabActive");
			if($(this).hasClass("room")){
				$("#room li[data-name='"+toRentSale.vars.room+"']").addClass("activeOn").siblings("li").removeClass("activeOn");
				$('#room').show();
			}else if($(this).hasClass("towards")){
				$("#towards li[data-name='"+toRentSale.vars.towards+"']").addClass("activeOn").siblings("li").removeClass("activeOn");
				$('#towards').show();
			}else{
				if("sale"==toRentSale.vars.type){
					$("#priceSale li[data-name='"+toRentSale.vars.price+"']").addClass("activeOn").siblings("li").removeClass("activeOn");
					$('#priceSale').show();
				}else{
					$("#priceRent li[data-name='"+toRentSale.vars.price+"']").addClass("activeOn").siblings("li").removeClass("activeOn");
					$('#priceRent').show();
				}
			}
		});
		$("#towards li,#priceSale li,#priceRent li,#room li").click(function(){
			if($(this).parent("ul").attr("id")=="room"){
				toRentSale.vars.room = $(this).attr("data-name");
				if($(this).html()=="全部"){
					$(".room h2").html("居室");
				} else {
					$(".room h2").html($(this).html());
				}
			}else if($(this).parent("ul").attr("id")=="towards"){
				toRentSale.vars.towards = $(this).attr("data-name");
				if($(this).html()=="全部"){
					$(".towards h2").html("朝向");
				} else {
					$(".towards h2").html($(this).html());
				}
			}else{
				toRentSale.vars.price = $(this).attr("data-name");
				if($(this).html()=="全部"){
					$(".price h2").html("价格");
				} else {
					$(".price h2").html($(this).html());
				}
			}
			$(".tab_bar li").removeClass("tabActive");
			$('.rentalHouseList').html('');
			paginate.init(0,10,toRentSale.doSearch);
		});
	}
};