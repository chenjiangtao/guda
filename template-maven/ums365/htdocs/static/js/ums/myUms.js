$(document).ready(function(){

  var loadGrid = function(){
	  var p = $('#account-log-table').datagrid({
	        
	        loadMsg: "数据加载中，请稍后...",
	        title: "账户变更记录",
	        pageNumber: 1,
	        singleSelect: false,
	        pageSize: 20,
			pagination:true,
			checkOnSelect:true,
			selectOnCheck:true,
			url:currenthost+'/ums/loadAccountLog.json',
			columns:[[
			   
				{field:'sum',title:'账户变更短信条数',width:200},
				{field:'action',title:'变更动作',width:200},
				{field:'amountBefore',title:'变更前账户余额',width:150},
				{field:'amountAfter',title:'变更后账户余额',width:150},
			
				{field:'gmtCreated',title:'变更时间',width:250,formatter: function(value, rec, index){
				    if (value == undefined) {
					     return "";
					}
				    return utcToDate(value);
					        
				}}
			]] 
		});
	};

	loadGrid();	
	
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
               var ho = parseInt(temp[0]);
               if(ho == 12){
                 time = ho+':'+temp[1]+":"+temp[2];
               }else{
                 time = ho+12+':'+temp[1]+":"+temp[2];
               }
            }
            date = date + month[str[0]] + "-" + str[1] + " " + time;
            return date;
        }
	
	
	
});

function doSearch(){  
    $('#account-log-table').datagrid('load',{  
        startTime: $('#startTime').val(),  
        endTime: $('#endTime').val()  
    });  
} 