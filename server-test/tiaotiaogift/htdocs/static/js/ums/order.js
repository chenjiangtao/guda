$(document).ready(function(){
  var tempForm = $('#tempForm').form();
  var loadGrid = function(){
	  var p = $('#admin-order-table').datagrid({
	        toolbar: [
	        {
	            iconCls: 'icon-add',
				text: '新增',
				handler: function(){
				   addRows();
				}
				
			},'-',{
				iconCls: 'icon-edit',
				text: '编辑',
				handler: function(){ 
				   editRows();
				}
			}
			],
	        loadMsg: "数据加载中，请稍后...",
	        title: "用户列表",
	        pageNumber: 1,
	        singleSelect: false,
	        pageSize: 20,
			pagination:true,
			checkOnSelect:true,
			selectOnCheck:true,
			url:currenthost+'/ums/admin/loadOrder.json',
			columns:[[
			    {field: 'id',checkbox: true},
				{field:'userId',title:'用户id',width:100},
				{field:'amount',title:'密码',width:100},
				{field:'sum',title:'手机号',width:100},
				{field:'status',title:'邮箱',width:150},
				{field:'gmtCreated',title:'状态',width:250},
				{field:'gmtModify',title:'等级',width:250}
			]] 
		});
	};

	loadGrid();	
	
	var editRows = function(){
      
        var rows = $("#admin-order-table").datagrid('getSelections');
        if (rows.length != 1 ) {
            $.messager.alert('提示','请选中一条记录进行编辑');  
		} else if (rows.length > 1) {
		    $.messager.alert('提示','您选择了多条记录，只能选择一条记录进行编辑');  
		} else if (rows.length == 1) {
	        tempForm.form('clear');
	       
	        tempForm.form('load', {
					id: rows[0].id,
					userId: rows[0].userId,
					amount: rows[0].amount,
					sum: rows[0].sum,
					status: rows[0].status
			});

		    tempDialog.dialog('open');
		    
		  }
    };
    var addRows = function(){
        
         tempForm.form('clear');
            
         tempDialog.dialog('open');
           
         
       
    }
    
   
    
    var	tempDialog = $('#tempDialog').show().dialog({
			modal: true,
			title: '订单',
			
			buttons: [{
				text: '保存',
				handler: function() {
					$('#tempForm').form('submit', {
						url: currenthost + "/ums/admin/saveOrder.json",
						success: function(result) {
							var r = jQuery.parseJSON(result);
							if (r.success) {
								$('#tempDialog').dialog('close');
								$('#admin-order-table').datagrid('load');
							}
							$.messager.alert('提示',r.msg);  
							 
						}
					});
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
	

	
	
	
});