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
    
    var render = function(domId,n){
        var plot5 = $.jqplot(domId, [n.arrays], {
        seriesColors: [ "#33CC66", "#FF0000","#00FF00","#CC0000"],
        title: {
		text: '服务器('+n.ip+')',
		show: true,
		fontFamily:'Helvetica',
		fontSize: '12pt'
		}, 
        grid: {
            drawBorder: false,
            drawGridlines: false,
            background: '#ffffff',
            shadow:false
        },
        axesDefaults: {
             
        },
        seriesDefaults:{
            renderer:$.jqplot.PieRenderer,
            rendererOptions: {
                showDataLabels: true
            }
        },
        legend: {
            show: true
        }
        });   
    };
    


    window.refresh = function(){
        ajaxbg.show(); 
        queryKey(callbackFun);

    };
    
    var callbackFun = function(keyList){
      
		   var mainContain = $("#main-contain");
	       mainContain.empty();
		    $.each(keyList, function (i, n) {
		        
		        
	             var newDiv = $("<div></div>"); 
	             var tempK = n.ip.replace(/[\s+\(\)\[\]\{\}\+\|\.\*\\\/\?\:]/g,"-");
	             newDiv.attr("id","chart-"+tempK);
	             newDiv.attr("class","contain-all-tab");
	             mainContain.append(newDiv);
	             if(n.arrays==null){
			        // $("#"+"chart-"+tempK).append("最近半小时内，服务器"+n.ip+"没有统计数据<br/><img src='"+currenthost+"/static/images/red-error.jpg' width='300' height='200'");
			         var tipDiv = $("<img></img>"); 
	                tipDiv.attr("src",currenthost+"/static/images/red-error.jpg");
	                tipDiv.attr("width","300");
	                tipDiv.attr("height","200");
	                var pDiv = $("<p></p>"); 
	                pDiv.attr("style","align:center");
	                pDiv.append("最近半小时内，服务器"+n.ip+"没有统计数据<br/>");
	                pDiv.append(tipDiv);
	               $("#"+"chart-"+tempK).append(pDiv);
	               createContextMenu("chart-"+tempK,n.ip);
			     }else{
	                render("chart-"+tempK,n);
	                
	                createContextMenu("chart-"+tempK,n.ip);
	             }
	         });
    }
    
    var createContextMenu=function(id,ip){

         $('#'+id).bind('contextmenu',function(e){
            $('#dbHref').attr('href',currenthost+"/indexDbDetail.htm?ip="+ip);
            $('#serverHref').attr('href',currenthost+"/indexServerDetail.htm?ip="+ip);
			e.preventDefault();
			$('#mm3').menu('show', {
			left: e.pageX,
			top: e.pageY
		  });
	    });
        
        
    }
    
    var queryKey=function(callbackFun){
          jQuery.ajax({
					url: currenthost + '/indexAll.json',
					
					dataType: 'json',
					success: function(result) {
					    ajaxbg.hide(); 
						if(result.length==0){
						  $.messager.alert('提示','无法查询到服务器概况数据');  
						}else{
						    callbackFun(result)
						}
					},
					filure:function(){
					   ajaxbg.hide(); 
					   $.messager.alert('提示','查询发生错误');
					}
		  });
		 
    }


	
	refresh();
	
	

    
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