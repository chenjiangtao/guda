
var D ;
$(function() {
	$("#send_btn").click(function() {
		
			$("#send_msg_form_id").submit();
		
	});

	$("#save_btn").click(function() {

			$('#send_msg_form_id').attr('action',currenthost + '/msg/msgSave.htm');
			
			$("#send_msg_form_id").submit();
		
	});
	


    $("#choosePerson").click(function(event) {

        loadPersonalGrid();
        personalDialog.dialog('open');
    }); 
    
    
    $("#clearContent").click(function(event) {

        $("#msg_content").val('');
    }); 
    
    
     $("#msg_content").bind("click",showNum).bind("keyup", showNum);  
     
     	
     
    $("#chooseTemplate").click(function(event) {

        loadGrid();
        tempDialog.dialog('open');
    }); 
    
        var	tempDialog = $('#tempDialog').show().dialog({
			modal: true,
			title: '短信模版',
			width:700,
			height:400,
			buttons: [{
				text: '确定',
				handler: function() {
				    var rows = $("#template-table").datagrid('getSelections');
				     if (rows.length == 0 ) {
			            $.messager.alert('提示','请选中一条记录');  
					}else{
					    $('#msg_content').val(rows[0].content);
					    tempDialog.dialog('close');
					}
				}
			},
			{
			 text: '取消',
			 handler: function() {
			   $('#tempDialog').dialog('close');
			 }
			}
			]
		}).dialog('close');
		
		var loadGrid = function(){
		  var p = $('#template-table').datagrid({
		       toolbar: [
	        {
	            iconCls: 'icon-add',
				text: '新增模版',
				handler: function(){
				   addRows();
				}
				
			},'-',{
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
		       
		        pageNumber: 1,
		        singleSelect: false,
		        pageSize: 20,
				pagination:true,
				checkOnSelect:true,
				selectOnCheck:true,
				url:currenthost+'/ums/loadSmsTemplate.json',
				columns:[[
				    {field: 'id',checkbox: true},
					{field:'content',title:'模版内容',width:360},
					{field:'type',title:'模版类型',width:100},
					{field:'gmtModify',title:'最近修改时间',width:150}
					
				]] 
			});
		};
		
		var editRows = function(){
        var rows = $("#template-table").datagrid('getSelections');
        if (rows.length != 1 ) {
            $.messager.alert('提示','请选中一条记录进行编辑');  
		} else if (rows.length > 1) {
		    $.messager.alert('提示','您选择了多条记录，只能选择一条记录进行编辑');  
		} else if (rows.length == 1) {
	        addSmsTemplateForm.form('clear');
	        addSmsTemplateForm.form('load', {
					id: rows[0].id,
					content: rows[0].content
			});
		    addSmsTemplateDialog.dialog('open');
		  }
    };
    var addRows = function(){
         addSmsTemplateForm.form('clear'); 
         addSmsTemplateDialog.dialog('open');
    }
    
    var removeRows = function(){
        var rows = $("#template-table").datagrid('getSelections');
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
							url: currenthost + '/ums/delSmsTemplate.json',
							data: {
								id: ids
							},
							dataType: 'json',
							success: function(result) {
								if (result.success) {
									$('#template-table').datagrid('load');
									
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
		var	addSmsTemplateDialog = $('#addSmsTemplateDialog').show().dialog({
			modal: true,
			title: '短信模版',
			
			buttons: [{
				text: '保存',
				handler: function() {
					$('#addSmsTemplateForm').form('submit', {
						url: currenthost + "/ums/saveSmsTemplate.json",
						success: function(result) {
							var r = jQuery.parseJSON(result);
							if (r.success) {
								$('#addSmsTemplateDialog').dialog('close');
								$('#template-table').datagrid('load');
							}
							$.messager.alert('提示',r.msg);  
							 
						}
					});
				}
			},
			{
			 text: '取消',
			 handler: function() {
			   $('#addSmsTemplateDialog').dialog('close');
			 }
			}
			]
		}).dialog('close');
		var addSmsTemplateForm = $('#addSmsTemplateForm').form();
		var	personalDialog = $('#personalDialog').show().dialog({
			modal: true,
			title: '我的联系人',
			width:700,
			height:400,
			
			buttons: [{
				text: '确定',
				handler: function() {
				    var rows = $("#personal-table").datagrid('getSelections');
				     if (rows.length == 0 ) {
			            $.messager.alert('提示','请至少选中一条记录');  
					}else{
					    for(var i=0,len=rows.length;i<len;++i){
					       setRecv(rows[i].phone);
					    }
					    personalDialog.dialog('close');
					}
				}
			},
			{
			 text: '取消',
			 handler: function() {
			   $('#personalDialog').dialog('close');
			 }
			}
			]
		}).dialog('close');
		
		var loadPersonalGrid = function(){
		  var p = $('#personal-table').datagrid({
		       toolbar: [
	        {
	            iconCls: 'icon-add',
				text: '新增',
				handler: function(){
				  
				   addContactRows();
				}
				
			},'-',{
				iconCls: 'icon-edit',
				text: '编辑',
				handler: function(){ 
				   
				   editContactRows();
				}
			},'-',{
			    text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					removeContactRows();
				}
			}
			],
		        loadMsg: "数据加载中，请稍后...",
		         singleSelect:false,
		        pageNumber: 1,
		        pageSize: 20,
				pagination:true,
				checkOnSelect:true,
				selectOnCheck:true,
				url:currenthost+'/personal/contact.json',
				columns:[[
				    {field: 'id',checkbox: true},
					{field:'name',title:'联系人姓名',width:60},
					{field:'phone',title:'联系人电话',width:60},
					{field:'taobaoName',title:'淘宝昵称',width:50},
					{field:'taobaoId',title:'淘宝订单号',width:60},
					{field:'taobaoOrderStatus',title:'淘宝订单状态',width:70},
					{field:'paipaiName',title:'拍拍昵称',width:50},
					{field:'paipaiId',title:'拍拍订单号',width:60},
					{field:'paipaiOrderStatus',title:'拍拍订单状态',width:70},
					{field:'deliveryName',title:'物流公司名称',width:70},
				    {field:'deliveryNo',title:'物流单号',width:50},
					{field:'address',title:'联系人地址',width:70}
					
				]] 
			});
		};
		
		var addContactForm = $('#addContactForm').form();
		var editContactRows = function(){
      
        var rows = $("#personal-table").datagrid('getSelections');
        if (rows.length != 1 ) {
            $.messager.alert('提示','请选中一条记录进行编辑');  
		} else if (rows.length > 1) {
		    $.messager.alert('提示','您选择了多条记录，只能选择一条记录进行编辑');  
		} else if (rows.length == 1) {
	        addContactForm.form('clear');
	        addContactForm.form('load', {
					id: rows[0].id,
					phone: rows[0].phone,
					name: rows[0].name
			});

		    addContactDialog.dialog('open');
		    
		  }
    };
    var addContactRows = function(){ 
         addContactForm.form('clear');
         addContactDialog.dialog('open');
    }
    
    var removeContactRows = function(){
        var rows = $("#personal-table").datagrid('getSelections');
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
							url: currenthost + '/personal/delContact.json',
							data: {
								id: ids
							},
							dataType: 'json',
							success: function(result) {
								if (result.success) {
									$('#personal-table').datagrid('load');
									
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
    
    var	addContactDialog = $('#addContactDialog').show().dialog({
			modal: true,
			title: '联系人',
			
			buttons: [{
				text: '保存',
				handler: function() {
					$('#addContactForm').form('submit', {
						url: currenthost + "/personal/saveContact.json",
						success: function(result) {
							var r = jQuery.parseJSON(result);
							if (r.success) {
								$('#addContactDialog').dialog('close');
								$('#personal-table').datagrid('load');
							}
							$.messager.alert('提示',r.msg);  
							 
						}
					});
				}
			},
			{
			 text: '取消',
			 handler: function() {
			   $('#addContactDialog').dialog('close');
			 }
			}
			]
		}).dialog('close');
		
		
		
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
var recvs="";
function setRecv(phone){
	var phone = phone+";";
	  var start = recvs.indexOf(phone);
	  if(start>-1){
	    
	  }else{
	    recvs = recvs+phone;
	  }
	
	$("#input-recvs").val(recvs);
}

function removeRecv(phone){
  var start = recvs.indexOf(phone);
  if(start>-1){
    if(recvs.length == start+phone.length+1){
       recvs = recvs.substring(0,start);
    }else{
       var temp1 = recvs.substring(0,start);
       var temp2 = recvs.substring(start+phone.length+1,recvs.length);
       recvs = temp1+temp2;
    }
    
    $("#input-recvs").val(recvs);
  }
}



var showNum = function(){
	 var d = $(this);
	 var pos = d.offset();
	 var t = pos.top +  this.offsetHeight - 22; // 弹出框的下边位置
	 var l = pos.left  + this.offsetWidth ;  // 弹出框的右边位置
	 var num = this.value.length;
	 $("#changeNum").html(num);
	 
}

function doPersonalSearch(){  
        $('#personal-table').datagrid('load',{  
            contactName: $('#contactName').val(),  
            orderStatus: $('#orderStatus').val()  
        });  
}  

function personalSearch(e){
   var e = e || window.event; 
	if(e.keyCode == 13){ 
	  doPersonalSearch();
	} 
}