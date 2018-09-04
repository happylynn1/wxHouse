var agentList={
		vars:{
			openid:"",
			storeId:"",
			keys:"",
		},
		setKeys:function(){
			this.vars.keys=$('form input').val();
			this.doSearch();
		},
		doSearch:function(){
			$.post(ext.path+"contact/getAgentListByStoreId.do",agentList.vars,function(data){
				$('.advisorList').html('');
				if(data.length>0){
					var str="";
					$.each(data,function(i,o){
						str=persionUtil.showAgent_orl(str, o);
					});
					$('.noData').hide();
					$('.advisorList').html(str);
				}else{
					$('.noData').show();
				}
			},'json');
		},
		init:function(){
			this.vars.openid = ext.openid;
			this.vars.storeId =ext.storeId;
			this.doSearch();
		}
};