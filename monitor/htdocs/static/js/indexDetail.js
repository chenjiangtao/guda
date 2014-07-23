String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1,"gm"),s2);
}
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

	    var render = function(domId,host,t,key,startTime,endTime,isWarn){
	       var jsonurl="";
	       if(isWarn == 1){
	          var jsonurl = currenthost+"/refresh.json?host="+host+"&key="+key+"&isWarn="+isWarn+"&start="+startTime+"&end="+endTime;
	       
	       }else{
	          var jsonurl = currenthost+"/refresh.json?host="+host+"&key="+key+"&start="+startTime+"&end="+endTime;
	       }
	       var d = ajaxDataRenderer(jsonurl);
	       $("#"+domId).empty();
	       if(d==null){
	         $("#"+domId).append("当前时间范围内，没有查询到"+key+"相关数据");
	         return;
	       }
            var renderVal = [d];
            if(d[0][0][0]!=2){
               renderVal = d;
            }
	        var plot1 = $.jqplot(domId, renderVal, {
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
    
  
      var  renderTab = function(domId,host,t,key,startTime,endTime,isWarn){
	       var jsonurl = currenthost+"/refreshTab.json?host="+host+"&key="+key+"&isWarn="+isWarn+"&start="+startTime+"&end="+endTime;
	       
	       loadGrid(domId,jsonurl,t);
	    
	    };
	    
	    var loadGrid = function(_id,_url,_title){
	       var tempId='#'+_id;
		  var p = $('#'+_id).datagrid({	
		        loadMsg: "数据加载中，请稍后...",
		        pageNumber: 1,
		        title:_title,
		        singleSelect: true,
		        pageSize: 10,
				pagination:true,
				checkOnSelect:true,
				selectOnCheck:true,
				width:500,
				url:_url,
				rowStyler: function(index,row){
					if (row.warn){
						return 'background-color:red;';
					}
				},
				toolbar : [{
					text:'导出excel',
					iconCls:'icon-export',
					handler:function(){
					  var kk = ($('#'+_id).attr('name'));
					  var url = currenthost+'/exportXls.htm?k='+kk+'&host='+_host+'&startTime='+$('#startTime').attr('value')+'&endTime='+$('#endTime').attr('value');

					  $('#export-xls-iframe').attr('src',url);
					}
					}],
            view: detailview,
			detailFormatter:function(index,row){
			  return '<div id="ddv-' + index + '" style="padding:5px 0">'+row.valText+'</div>';
			},
		    onExpandRow: function(index,row){  
		    
		         $('#ddv-'+index).panel({
					height:80,
					width:450,
					border:false,
					cache:false,
					content:row.valText
				});
				},
				columns:[[
					{field:'k',title:'属性',width:100},
					{field:'valText',title:'值',width:150,formatter: function(value, rec, index){
				    if (value == undefined) {
					     return "";
					}
				    return value.replaceAll('<br/>','');
					        
				    }
				   },
					{field:'gmtCreated',title:'记录时间',width:150}
					
				]]
			});
			
			
			
		}
		var cardview = $.extend({}, $.fn.datagrid.defaults.view, {
		    renderRow: function (target, fields, frozen, rowIndex, rowData) {
		        
		        var opts = $.data(target, 'datagrid').options;
		        var cc = [];
		        if (frozen && opts.rownumbers) {
		            var rownumber = rowIndex + 1;
		            if (opts.pagination) {
		                rownumber += (opts.pageNumber - 1) * opts.pageSize;
		            }
		            cc.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">' + rownumber + '</div></td>');
		        }
		        for(var i=0; i<fields.length; i++){
				var field = fields[i];
				var col = $(target).datagrid('getColumnOption', field);
				if (col){
					var value = rowData[field];	// the field value
					// get the cell style attribute
					var styleValue = col.styler ? (col.styler(value, rowData, rowIndex)||'') : '';
					var style = col.hidden ? 'style="display:none;' + styleValue + '"' : (styleValue ? 'style="' + styleValue + '"' : '');
					
					cc.push('<td  field="' + field + '" ' + style  +' title="' + value + '">');
					
					if (col.checkbox){
						var style = '';
					} else if (col.expander){
						style = "text-align:center;height:16px;";
					} else {
						var style = styleValue;
	//					style += 'text-align:' + (col.align || 'left') + ';';
						if (col.align){style += ';text-align:' + col.align + ';'}
						if (!opts.nowrap){
							style += ';white-space:normal;height:auto;';
						} else if (opts.autoRowHeight){
							style += ';height:auto;';
						}
					}
					
					cc.push('<div style="' + style + '" ');
					if (col.checkbox){
						cc.push('class="datagrid-cell-check ');
					} else {
						cc.push('class="datagrid-cell ' + col.cellClass);
					}
					cc.push('">');
					
					if (col.checkbox){
						cc.push('<input type="checkbox" name="' + field + '" value="' + (value!=undefined ? value : '') + '"/>');
					} else if (col.expander) {
						//cc.push('<div style="text-align:center;width:16px;height:16px;">');
						cc.push('<span class="datagrid-row-expander datagrid-row-expand" style="display:inline-block;width:16px;height:16px;cursor:pointer;" />');
						//cc.push('</div>');
					} else if (col.formatter){
						cc.push(col.formatter(value, rowData, rowIndex));
					} else {
						cc.push(value);
					}
					
					cc.push('</div>');
					cc.push('</td>');
				}
			}
			return cc.join('');
			  },
			  bindEvents: function(target){
			var state = $.data(target, 'datagrid');
			var dc = state.dc;
			var opts = state.options;
			var body = dc.body1.add(dc.body2);
			var clickHandler = ($.data(body[0],'events')||$._data(body[0],'events')).click[0].handler;
			body.unbind('click').bind('click', function(e){
				var tt = $(e.target);
				var tr = tt.closest('tr.datagrid-row');
				if (!tr.length){return}
				if (tt.hasClass('datagrid-row-expander')){
					var rowIndex = parseInt(tr.attr('datagrid-row-index'));
					if (tt.hasClass('datagrid-row-expand')){
						$(target).datagrid('expandRow', rowIndex);
					} else {
						$(target).datagrid('collapseRow', rowIndex);
					}
					$(target).datagrid('fixRowHeight');
					
				} else {
					clickHandler(e);
				}
				e.stopPropagation();
			});
		},
		});
     
    var _host ;
    var _isInit;

  var _servlet ;
  var _type='chart';
  var _currentId;
  window.choose = function(obj){
     var type = obj.id;
     $('#header_server_chart').attr("style","");
     $('#header_db_chart').attr("style","");
     $('#header_db_grid').attr("style","");
     $('#header_server_grid').attr("style","");
     if(type == 'header_server_chart'){
        _type='chart';
        _servlet = '/getServerFloatKey.json';
        _currentId = 'header_server_chart';
     }else if(type =='header_db_chart'){
         _type='chart';
         _servlet = '/getDbFloatKey.json';
         _currentId = 'header_db_chart';
     }else if(type =='header_db_grid'){
         _type='grid';
         _servlet = '/getDbKey.json';
         _currentId = 'header_db_grid';
     }else if(type =='header_server_grid'){
         _type='grid';
         _servlet = '/getServerKey.json';
          _currentId = 'header_server_grid';
     }
      $('#'+type).attr("style","background-color:#2E5E79;color:#fff;");
      if(_host){
         refresh(_servlet,_type,0);
     }else{
         var nodes = $('#tt').tree('getChildren');
         if(nodes.length>1){
           $('#tt').tree('select', nodes[1].target);
           _host = nodes[1].id;
           refresh(_servlet,_type,0);
         }
         //jQuery.messager.alert('提示','请先单击左侧服务器列表，选择服务器');  
     }
  }
    window.refreshInit = function(host,targetId){
       
        _host = host;
        if(!_servlet){
          if(targetId == 'header_server_chart'){
            _servlet = '/getServerFloatKey.json';
            $('#header_server_chart').attr("style","background-color:#2E5E79;color:#fff;");
          }else if(targetId == 'header_db_chart'){
             _servlet = '/getDbFloatKey.json';
            $('#header_db_chart').attr("style","background-color:#2E5E79;color:#fff;");
          }
        }
        ajaxbg.show(); 
        queryKey(_servlet,host,callbackFun,_type,0);
        
        

    };
    
    window.refresh = function(servlet,type,_isWarn){
        
        ajaxbg.show(); 
        queryKey(servlet,_host,callbackFun,_type,_isWarn);

    };
    
    var callbackFun = function(host,keyList,type,isWarn){
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
	             newDiv.attr("name",n.k);
	             newDiv.attr("class","contain-tab");
	             mainContain.append(newDiv);
	             if(type=='chart'){
	               render("chart-"+tempK,host,com,n.k,sTime,eTime,isWarn);
	             }else{
	               renderTab("chart-"+tempK,host,com,n.k,sTime,eTime,isWarn);
	             }
	         });
    }
    
    var queryKey=function(servlet,currentIP,callbackFun,type,_isWarn){
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
						    callbackFun(currentIP,result,type,_isWarn)
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
			refresh(_servlet,_type,0);
	});
	 $("#search_warn_btn").click(function() {
           if(!_host){
             $.messager.alert('提示','请先单击左侧服务器列表，选择服务器');
             return;
           }
			refresh(_servlet,_type,1);
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