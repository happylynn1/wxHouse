var sel = {
	init:function(){
		$('div span,div p').click(function(){
			location.href= ext.path+"page/sellr.do?type="+$(this).parent("div").attr("id");
		});
	}	
};