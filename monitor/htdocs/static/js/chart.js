$(document).ready(function(){
    var ajaxbg = $("#background,#progressBar");
    ajaxbg.hide(); 

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
    
    var render = function(domId,host,t,key,startTime,endTime){
       var jsonurl = currenthost+"/refresh.json?host="+host+"&key="+key+"&start="+startTime+"&end="+endTime;
       var d = ajaxDataRenderer(jsonurl);
       $("#"+domId).empty();
       if(d==null){
         $("#"+domId).append("当前时间范围内，没有查询到"+key+"相关数据");
         return;
       }
        var plot1 = $.jqplot(domId, [d], {
        seriesColors: [ "#33CC66", "#FF0000","#33CC00","#CC0000"],
	    title: {
		text: t,
		show: true,
		fontFamily:'Helvetica',
		fontSize: '14pt'
		}, 
	    axes:{
	        xaxis:{
	          autoscale: true,
	          renderer: $.jqplot.DateAxisRenderer,
	          label: '时间',
		      labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
	          tickRenderer: $.jqplot.CanvasAxisTickRenderer,
	          labelOptions:{
	            fontFamily:'Helvetica',
	            fontSize: '12pt'
	          },
	          tickOptions:{formatString:'%m-%d %H:%M:%S',angle: -30}
	          },
	          labelOptions:{
	              fontFamily:'Helvetica',
	              fontSize: '12pt'
              },
	          yaxis: { }  
	    },
	    highlighter: {
         show: true,
        sizeAdjust: 7.5
     },
        cursor: {  show: false  }
        
	  });
      

    
    };
    

    var _host ;
    var _servlet;
    window.refresh = function(servlet,host){
        _host = host;
        _servlet = servlet;
        ajaxbg.show(); 
        queryKey(servlet,_host,callbackFun);

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
	             if(n.comment&&n.comment!=n.k){
	                com = n.comment+'('+n.k+')';
	             } 
	             
	            
	             var newDiv = $("<div></div>"); 
	             var tempK = n.k.replace(/[\s+\(\)\[\]\{\}\+\|\.\*\\\/\?\:]/g,"-");
	             newDiv.attr("id","chart-"+tempK);
	             newDiv.attr("class","contain-tab");
	             mainContain.append(newDiv);
	             render("chart-"+tempK,host,com,n.k,sTime,eTime);
	         });
    }
    
    var queryKey=function(servlet,currentIP,callbackFun){
          jQuery.ajax({
					url: currenthost + servlet,
					data: {
						ip: currentIP
					},
					dataType: 'json',
					success: function(result) {
					    ajaxbg.hide(); 
						if(result.length==0){
						  $.messager.alert('提示','无法查询到主机'+currentIP+'的相关监控指标');  
						}else{
						    callbackFun(currentIP,result)
						}
					},
					filure:function(){
					   ajaxbg.hide(); 
					   $.messager.alert('提示','无法查询到主机'+currentIP+'发生错误');
					}
		  });
		 
    }

    $("#search_btn").click(function() {
           if(!_host){
             $.messager.alert('提示','请先单击左侧服务器列表，选择服务器');
             return;
           }
			refresh(_servlet,_host);
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
			{field:'gmtCreated',title:'创建时间',width:100}
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