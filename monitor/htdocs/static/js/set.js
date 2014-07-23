var currentIP;

$(document).ready(function(){

  var warnForm = $('#fm').form();
  
  
  var loadGrid = function(){
	  var p = $('#dg').datagrid({
	        toolbar: [
	        {
	            iconCls: 'icon-add',
				text: '新增告警',
				handler: function(){
				  
				   addWarn();
				}
				
			},'-',{
				iconCls: 'icon-edit',
				text: '编辑',
				handler: function(){ 
				   
				   editWarn();
				}
			},'-',{
			    text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					removeWarn();
				}
			}
			],
			
	        loadMsg: "数据加载中，请稍后...",
	        
	        pageNumber: 1,
	        singleSelect: true,
	        pageSize: 20,
			pagination:true,
			checkOnSelect:true,
			selectOnCheck:true,
			url:currenthost+'/set.json',
			columns:[[
			    {field: 'id',checkbox: true},
				{field:'ip',title:'ip',width:100},
				{field:'k',title:'属性',width:50},
				{field:'condition',title:'条件',width:50},
				{field:'val',title:'告警阀值',width:80},
				{field:'warn',title:'告警方式',width:100},
				{field:'phone',title:'短信通知手机',width:300},
				{field:'email',title:'通知email',width:450}
				
			]] 
		});
	}
	
    window.loadSet = function(_ip){
         currentIP = _ip;
         loadGrid();
	     $('#dg').datagrid('load', {
			ip: _ip
		});
    };
    
    
    var editWarn = function(){
      
        var rows = $("#dg").datagrid('getSelections');
        if (rows.length != 1 ) {
            $.messager.alert('提示','请选中一条记录进行编辑');  
		} else if (rows.length > 1) {
		    $.messager.alert('提示','您选择了多条记录，只能选择一条记录进行编辑');  
		} else if (rows.length == 1) {
	        warnForm.form('clear');
	        var _k = $("#k_id");  
			 $.ajax({  
	                type: "POST",  
	                dataType: "json",  
	                url: currenthost+'/getKey.json',
	                data: { ip: currentIP },  
	                success: function (data) {  
	                    _k.empty();  
	                    $.each(data, function (i, n) {
	                      if(n.comment){
	                        _k.append("<option value=" + n.k + ">" + n.comment +"("+n.k+")</option>"); 
	                      }else{
	                        _k.append("<option value='" + n.k + "'>" + n.k +"</option>"); 
	                      } 
	                      
	                      warnForm.form('load', {
									id: rows[0].id,
									ip: rows[0].ip,
									
									val: rows[0].val,
									emailVal:rows[0].email,
									phoneVal:rows[0].phone,
							});
							if(rows[0].warn=='email'){
							   $("#email_box").attr("checked",'true');
							}
							if(rows[0].warn=='phone'){
							   $("#phone_box").attr("checked",'true');
							}
							if(rows[0].warn=='email,phone'){
							   $("#phone_box").attr("checked",'true');
							   $("#email_box").attr("checked",'true');
							}
							
							
						
						    
						    var count=$("#cond_id").get(0).options.length;
							for(var i=0;i<count;i++){
								if($("#cond_id").get(0).options[i].value == rows[0].condition)  {
								  $("#cond_id").get(0).options[i].selected = true;          
								  break;  
								}  
							}
							var count2=$("#k_id").get(0).options.length;
							for(var i=0;i<count2;i++){
								if($("#k_id").get(0).options[i].value == rows[0].k)  {
								  $("#k_id").get(0).options[i].selected = true;          
								  break;  
								}  
							}
						    warnDialog.dialog('open');
	                    });  
	                   
	                }  
	            });  
	        
		    
		  }
    };
    var addWarn = function(){
        
         warnForm.form('clear');
         $("#ip_id").val(currentIP);
        $("#cond_id").get(0).options[0].selected = true;    
        warnDialog.dialog('open');
           
         loadK();
       
    }
    
    var removeWarn = function(){
        var rows = $("#dg").datagrid('getSelections');
        if (rows.length == 0 ) {
            $.messager.alert('提示','请至少选中一条记录');  
		}
		jQuery.messager.confirm('确认', '您是否要删除当前选中的记录？',
		function(r) {
			if (r) {
				
				jQuery.ajax({
					url: currenthost + '/delWarn.json',
					data: {
						id: rows[0].id
					},
					dataType: 'json',
					success: function(result) {
						if (result.success) {
							$('#dg').datagrid('load');
							
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
    
    var	warnDialog = $('#dlg').show().dialog({
			modal: true,
			title: '告警信息',
			
			buttons: [{
				text: '保存',
				handler: function() {
					$('#fm').form('submit', {
						url: currenthost + "/saveWarn.json",
						success: function(result) {
							var r = jQuery.parseJSON(result);
							if (r.success) {
								$('#dlg').dialog('close');
								$('#dg').datagrid('load');
							}
							$.messager.alert('提示',r.msg);  
							 
						}
					});
				}
			},
			{
			 text: '取消',
			 handler: function() {
			   $('#dlg').dialog('close');
			 }
			}
			]
		}).dialog('close');
    
    var loadK = function(){
			var _k = $("#k_id");  
			 $.ajax({  
	                type: "POST",  
	                dataType: "json",  
	                url: currenthost+'/getKey.json',
	                data: { ip: currentIP },  
	                success: function (data) {  
	                    _k.empty();  
	                    $.each(data, function (i, n) {
	                      if(n.comment){
	                        _k.append("<option value=" + n.k + ">" + n.comment +"("+n.k+")</option>"); 
	                      }else{
	                        _k.append("<option value='" + n.k + "'>" + n.k +"</option>"); 
	                      } 
	                    });  
	                   
	                }  
	            });  
       };
       
       
       
       

	
});




