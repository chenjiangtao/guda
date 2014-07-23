$(document).ready(function(){


    var ajaxDataRenderer = function(url) {
        var ret = null;
        $.ajax({
           
            async: false,
            url: url,
            dataType: "json",
            success: function(data) {
                ret = data;
            },
            error:function(){
               
            }
        });
        return ret;
    };
    
    var renderTab = function(domId,host,t,key,startTime,endTime){
       var jsonurl = currenthost+"/refreshTab.json?host="+host+"&key="+key+"&start="+startTime+"&end="+endTime;
       
       loadGrid(domId,jsonurl,t);
    
    };
    
    var loadGrid = function(_id,_url,_title){
	  var p = $('#'+_id).datagrid({	
	        loadMsg: "数据加载中，请稍后...",
	        pageNumber: 1,
	        title:_title,
	        singleSelect: true,
	        pageSize: 10,
			pagination:true,
			checkOnSelect:true,
			selectOnCheck:true,
			width:400,
			url:_url,
			columns:[[
				{field:'k',title:'属性',width:100},
				{field:'valText',title:'值',width:150},
				{field:'gmtCreated',title:'记录时间',width:150}
				
			]] 
		});
	}
    

    var _host ;
    var _servlet ;
    window.refresh = function(host){
        _host = host;
        _servlet = ;
        queryKey(_host,callbackFun);

    };
    
    var callbackFun = function(host,keyList){
       var sTime = $("#startTime").val();
		    var eTime= $("#endTime").val();
		    if(sTime == ''||eTime == ''){
			    var end = new Date();
			    var start = new Date();
			    var hour = end.getHours()-3;
			    start.setHours(hour);
			    sTime = (start.format("yyyy-MM-dd hh:mm:ss"));
			    eTime = (end.format("yyyy-MM-dd hh:mm:ss"));
			    $("#startTime").val(sTime);
		       $("#endTime").val(eTime);
		     }
		  var mainContain = $("#main-contain");
	             mainContain.empty();
		    $.each(keyList, function (i, n) {
		        
		         var com = n.k;
	             if(n.comment&& n.comment!=n.k){
	                com = n.comment+'('+n.k+')';
	             } 
	             
	             
	             var newDiv = $("<table></table>"); 
	             var tempK = n.k.replace(/[\s+\(\)\[\]\{\}\+\|\.\*\\\/\?\:]/g,"-");
	             
	             newDiv.attr("id","table-"+tempK);
	             newDiv.attr("class","easyui-datagrid");
	             mainContain.append(newDiv);
	             
	             renderTab("table-"+tempK,host,com,n.k,sTime,eTime);
	         });
    }
    
    var queryKey=function(currentIP,callbackFun){
          jQuery.ajax({
					url: currenthost + '/getDbKey.json',
					data: {
						ip: currentIP
					},
					dataType: 'json',
					success: function(result) {
						if(result.length==0){
						  $.messager.alert('提示','无法查询到主机'+currentIP+'的相关监控指标');  
						}else{
						    callbackFun(currentIP,result)
						}
					},
					filure:function(){
					   $.messager.alert('提示','无法查询到主机'+currentIP+'发生错误');
					}
		  });
		 
    }

    $("#search_btn").click(function() {
    
			refresh(_host);
	});
	
	//refresh(_host);
	
	
	
	
	//set
	var p = $('#dg').datagrid({
        toolbar: [{
			iconCls: 'icon-edit',
			handler: function(){ 
			   $('#dlg').dialog('open').dialog('setTitle','创建告警');  
			}
		},'-',{
			iconCls: 'icon-help',
			handler: function(){alert('help')}
		}],
		
        loadMsg: "数据加载中，请稍后...",
      
        pageNumber: 1,
        singleSelect: true,
        pageSize: 20,
		pagination:true,
		checkOnSelect:true,
		selectOnCheck:true,
		url:currenthost+'/set.json',
		columns:[[
			{field:'ip',title:'ip',width:100},
			{field:'k',title:'属性',width:100},
			{field:'condition',title:'条件',width:100},
			{field:'val',title:'告警阀值',width:100},
			{field:'warn',title:'告警方式',width:100},
			{field:'phone',title:'短信通知手机',width:300},
			{field:'email',title:'通知email',width:430},
			{field:'gmtCreated',title:'创建时间',width:100,formatter: function(value, rec, index){
				    if (value == undefined) {
					     return "";
					}
				    return utcToDate(value);
					        
				}
			}
		]] 
	});
	
	$(p).pagination({
            pageSize: 20,//每页显示的记录条数，默认为10  
            pageList: [20, 30, 40],//可以设置每页记录条数的列表  
            beforePageText: '第',//页数文本框前显示的汉字  
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            /*onBeforeRefresh:function(){ 
                $(this).pagination('loading'); 
                alert('before refresh'); 
                $(this).pagination('loaded'); 
            }*/
        });
    window.loadSet = function(_ip){
	     $('#dg').datagrid('load', {
			ip: _ip
		});
    };
	//set end
	
	
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



Date.prototype.format = function(format){
 /*
  * eg:format="YYYY-MM-dd hh:mm:ss";
  */
 var o = {
  "M+" :  this.getMonth()+1,  //month
  "d+" :  this.getDate(),     //day
  "h+" :  this.getHours(),    //hour
  "m+" :  this.getMinutes(),  //minute
  "s+" :  this.getSeconds(), //second
  "q+" :  Math.floor((this.getMonth()+3)/3),  //quarter
  "S"  :  this.getMilliseconds() //millisecond
}
 
   if(/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
   }
 
   for(var k in o) {
    if(new RegExp("("+ k +")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    }
   }
 return format;
}