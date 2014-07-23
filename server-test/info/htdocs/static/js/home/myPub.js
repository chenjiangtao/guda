$(document).ready(function(){
  
  var loadGrid = function(){
	  var p = $('#my-pub-table').datagrid({
	        toolbar: [
	        {
				iconCls: 'icon-edit',
				text: '编辑',
				handler: function(){ 
				   editRows();
				}
			},'-',{
			    text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					removeRows();
				}
			}
			],
	        loadMsg: "数据加载中，请稍后...",
	        title: "我发布信息列表",
	        pageNumber: 1,
	        singleSelect: false,
	        pageSize: 20,
			pagination:true,
			checkOnSelect:true,
			selectOnCheck:true,
			url:currenthost+'/home/loadMyPub.json',
			columns:[[
			    {field: 'id',checkbox: true},
				{field:'title',title:'标题',width:200},
				{field:'content',title:'内容',width:400},
				{field:'price',title:'价格(元)',width:50},
				{field:'contactUser',title:'联系人',width:50},
				{field:'contactInfo',title:'联系电话',width:100},
				
				
				{field:'gmtCreated',title:'创建时间',width:250,formatter: function(value, rec, index){
				    if (value == undefined) {
					     return "";
					}
				    return utcToDate(value);
					        
				}}
			]] 
		});
	};

	loadGrid();	
	
	var editRows = function(){
      
        var rows = $("#my-pub-table").datagrid('getSelections');
        
		window.open(currenthost+"/prod/publish.htm?id="+rows[0].id);
					
		    
		  
    };
    
    
    var removeRows = function(){
        var rows = $("#my-pub-table").datagrid('getSelections');
        if (rows.length == 0 ) {
            $.messager.alert('提示','请至少选中一条记录');  
            
		}else{
		        jQuery.messager.confirm('确认', '您是否要删除当前选中的记录？',
				function(r) {
					if (r) {
					    var ids = '';
						for(var i=0;i<rows.length;++i){
						   ids=ids+rows[i].id+',';
						}
						jQuery.ajax({
							url: currenthost + '/home/delMyPub.json',
							data: {
								id: ids
							},
							dataType: 'json',
							type: 'POST',
							success: function(result) {
								if (result.success) {
									$('#my-pub-table').datagrid('load');
									
								}
								jQuery.messager.show({
									title: '提示',
									msg: result.msg
								});
							}
						});
					}
			
		     });
		   }
    }
    

   
	 
	var dateFomatter  = function(value, rec, index){
	    if (value == undefined) {
		     return "";
		}
	    return utcToDate(value);
		        
	}
	
     function utcToDate(utcCurrTime) {
            utcCurrTime = utcCurrTime + "";
            var date = "";
            var month = new Array();
            month["Jan"] = 1;
            month["Feb"] = 2;
            month["Mar"] = 3;
            month["Apr"] = 4;
            month["May"] = 5;
            month["Jun"] = 6;
            month["Jul"] = 7;
            month["Aug"] = 8;
            month["Sep"] = 9;
            month["Oct"] = 10;
            month["Nov"] = 11;
            month["Dec"] = 12;
            var week = new Array();
            week["Mon"] = "一";
            week["Tue"] = "二";
            week["Wed"] = "三";
            week["Thu"] = "四";
            week["Fri"] = "五";
            week["Sat"] = "六";
            week["Sun"] = "日";
            str = utcCurrTime.split(" ");
            date = str[2] + "-";
            var time = str[3];
            if(str[4]=='PM'){
               var temp = str[3].split(":");
               time = parseInt(temp[0])+12+':'+temp[1]+":"+temp[2];
            }
            date = date + month[str[0]] + "-" + str[1] + " " + time;
            return date;
        }
	
	
	
});