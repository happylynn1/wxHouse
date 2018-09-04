var wxPicPath = "http://wx.jjr580.com/commpic";
var commList = {
		vars:{
			storeId:"",
			openid:"",
			comm:"",
			type:"",
			page:0,
			limit:10
		},
		setComm:function(){
			this.vars.comm=$('form input').val();
			$('.hotHouseList').html('');
			paginate.init(0,10,this.doSearch);
		},
		doSearch:function(page,limit){
			var num = 0;
			commList.vars.page = page;
			commList.vars.limit = limit;
			$('.hotHouseList').find(".waiting").remove();
			$.post(ext.path+"house/getCommList.do",commList.vars,function(data){
				num = data.length;
				if(num>0){
					var str =$('.hotHouseList').html();
					$.each(data,function(i,o){
						var picPath = ext.defaultHouseImage;
						if(o.picPath!=null&&o.picPath!="")	picPath =wxPicPath+o.picPath; 
						var avg_price ="<b>暂无</b>";
						if(o.avg_price!=null&&o.avg_price!=""){
							avg_price=  "<b>"+o.avg_price.replace("元/平米","")+"</b>元/㎡";
						}
						str+="<li><div class='houseImg'><img src='"+picPath+"' onerror='commList.changePic(this)' /></div>"+
						"<dl class='villageIntr'><dt><strong class='fleft'>"+o.name+"</strong><span class='priceS fright'>"+avg_price+"</span></dt>";
						if((o.area!=null&&o.area!=""&&o.street!=null&&o.street!="")){
							str+="<dd class='areaDD'>"+o.area+"|"+o.street+"</dd>";
						}else{
							str+="<dd class='areaDD'>"+o.area+o.street+"</dd>";
						}
						str+="<dd class='btnDD'>"+
						"<a class='checkRentBtn' onclick=\"commList.showHouseList('sale','"+o.name+"')\"><span class='wzSpan'>查看售房</span><span class='numSpan'>"+(o.saleNum!=""?o.saleNum:"0")+"套</span></a>"+
	                  	"<a class='checkSaleBtn' onclick=\"commList.showHouseList('rent','"+o.name+"')\"><span class='wzSpan'>查看租房</span><span class='numSpan'>"+(o.rentNum!=""?o.rentNum:"0")+"套</span></a>"+
	                  	"</dd></dl></li>";
					});
					$('.noData').hide();
					if(num==limit){
						str+="<li class='waiting'><span>加载中...</span></li>";
					}
					$('.hotHouseList').html(str);
				}else{
					if(page==0){
						$('.noData').show();
					}
				}
			},'json');
			return num;
		},
		changePic:function(img){
			$(img).attr("src",ext.defaultHouseImage);
		},
		showHouseList:function(type,comm){
			location.href=ext.path+"house/commHouseList.do?comm="+comm+"&openid="+commList.vars.openid+"&type="+type;
		},
		init:function(){
			$('.hotHouseList').html('');
			this.vars.storeId = ext.storeId;
			this.vars.openid = ext.openid;
			paginate.init(0,10,this.doSearch);
		}
};