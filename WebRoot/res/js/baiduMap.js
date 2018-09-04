var baiduMap={
	lng:"",	
	lat:"",	
	commName:"",
	street:"",
	city:"",
	point:null,
	map:null,
	mySquare:null,
	init:function(Mapid,city,commName,street,lng,lat,flag){
		if($('#'+Mapid).parent("li").is(":hidden")) $('#'+Mapid).parent("li").show();
		if(commName==""||lat==""||lng==""){
			if(!$('#'+Mapid).hasClass("nocomm")) $('#'+Mapid).addClass("nocomm");
			$('#'+Mapid).html('');
			$('.bdptss').html('');
			return;
		}
		$('#'+Mapid).removeClass("nocomm");
		this.map =null;
		this.point =null;
		this.mySquare =null;
		this.city =city;
		map = new BMap.Map(Mapid);
		point = new BMap.Point(lng, lat);
		map.centerAndZoom(point, 15);
		map.addControl(new BMap.MapTypeControl({mapTypes:[BMAP_NORMAL_MAP]}));
		map.setCurrentCity(this.city);
		map.enableScrollWheelZoom(true);
		//mySquare = new SquareOverlay(map.getCenter(),commName,street);
		//map.addOverlay(mySquare);
		var myIcon = new BMap.Icon(ext.path+"res/images/location.png", new BMap.Size(30,30),{anchor:new BMap.Size(15,30)});
		var marker2 = new BMap.Marker(point,{icon:myIcon});  // 创建标注
		map.addOverlay(marker2);
		marker2.enableDragging();
		if(flag){
			map.disableScrollWheelZoom();
			var local = new BMap.LocalSearch(map, {renderOptions:{map: map, autoViewport: true}}); 
			$('.bdptss li').click(function(){
				if(!$(this).find("i").hasClass("on")){
					$(this).find("i").addClass("on").parent("li").siblings().find("i").removeClass("on");
					local.searchNearby($(this).find("span").html(),map.getCenter());
				}
			});
		}
		map.addEventListener("click", function(e){
			 $.post(ext.path+"house/getCommByLnglatByBaidu.do",{lnglat:e.point.lng+","+e.point.lat},function(data){
             	if(data!=null&&data!=""&&data.indexOf(":")>=0){
             		$('.label_1').next("input").val(data.split(":")[0]);
             		baiduMap.commName = data.split(":")[0];
             		baiduMap.street = data.split(":")[1];
             		baiduMap.lng =  e.point.lng;
             		baiduMap.lat =  e.point.lat;
             		$('#commInput').val(data.split(":")[0]);
            		$('#addressInput').val(data.split(":")[1]);
            		$('#lngInput').val(e.point.lng);
            		$('#latInput').val(e.point.lat);
             		var scn = baiduMap.commName.length>7?baiduMap.commName.substring(0,7)+"...":baiduMap.commName;
             		var ss = baiduMap.street.length>7?baiduMap.street.substring(0,7)+"...":baiduMap.street;
					baiduMap.init("baiduMap",this.city,scn,ss,e.point.lng,e.point.lat);
             	}
             });
		});
		marker2.addEventListener("dragend", function(e){
			$.post(ext.path+"house/getCommByLnglatByBaidu.do",{lnglat:e.point.lng+","+e.point.lat},function(data){
				if(data!=null&&data!=""&&data.indexOf(":")>=0){
					$('.label_1').next("input").val(data.split(":")[0]);
					baiduMap.commName = data.split(":")[0];
					baiduMap.street = data.split(":")[1];
					baiduMap.lng =  e.point.lng;
					baiduMap.lat =  e.point.lat;
					$('#commInput').val(data.split(":")[0]);
					$('#addressInput').val(data.split(":")[1]);
					$('#lngInput').val(e.point.lng);
					$('#latInput').val(e.point.lat);
					var scn = baiduMap.commName.length>7?baiduMap.commName.substring(0,7)+"...":baiduMap.commName;
					var ss = baiduMap.street.length>7?baiduMap.street.substring(0,7)+"...":baiduMap.street;
					baiduMap.init("baiduMap",this.city,scn,ss,e.point.lng,e.point.lat);
				}
			});
		});
	}
};
/*var SquareOverlay = function(center,commName,street){
	this._center = center;
    this._DIV = document.createElement("div");
    this._DIV.style.position = "absolute";
    this._DIV.style.backgroundColor = "#fff";
    this._DIV.style.width = "100px";
    this._DIV.style.height = "50px";
    var span1 = document.createElement("span");
    span1.style.fontSize ="11px";
    span1.style.display ="block";
    span1.style.lineHeight ="25px";
    span1.style.textAlign ="center";
    span1.innerText=commName;
    this._DIV.appendChild(span1);
    var span2 = document.createElement("span");
    span2.style.fontSize ="10px";
    span2.style.display ="block";
    span2.style.lineHeight ="20px";
    span2.style.textAlign ="center";
    span2.innerText=street;
    this._DIV.appendChild(span2);
    
};
//继承API的BMap.Overlay
SquareOverlay.prototype = new BMap.Overlay();
SquareOverlay.prototype.initialize = function(map){
    this._map = map;
    var div = this._DIV;
    map.getPanes().labelPane.appendChild(div);
    this._div = div;
    return div;
};
// 实现绘制方法   
SquareOverlay.prototype.draw = function(){    
// 根据地理坐标转换为像素坐标，并设置给容器    
    var position = this._map.pointToOverlayPixel(this._center);    
    this._div.style.left = position.x - $(this._DIV)[0].offsetWidth / 2 + "px";    
    this._div.style.top = position.y - $(this._DIV)[0].offsetHeight *1.3 + "px";    
};
// 实现显示方法    
SquareOverlay.prototype.show = function(){    
    if (this._div){    
        this._div.style.display = "";    
    }    
};  
// 实现隐藏方法  
SquareOverlay.prototype.hide = function(){    
    if (this._div){    
        this._div.style.display = "none";    
    }    
};
// 添加自定义方法   
SquareOverlay.prototype.toggle = function(){    
    if (this._div){    
        if (this._div.style.display == ""){    
            this.hide();    
        } else {    
            this.show();    
        }    
    }    
};*/