$(function() {
	$("#form-submit").click(function() {
		
			$("#contactForm").submit();
		
	});
});

$(function() {
	$("#password-form-submit").click(function() {
		
			$("#passwordForm").submit();
		
	});
	

	
	var tempForm = $('#tempForm').form();
	var loadGrid = function(){
	  var p = $('#template-table').datagrid({
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
			},'-',{
			    text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					removeRows();
				}
			},'-',{
			    text: '淘宝订单导入联系人',
				iconCls: 'icon-add',
				handler: function() {
					addTaobaoRows();
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
			url:currenthost+'/personal/contact.json',
			columns:[[
			    {field: 'id',checkbox: true},
				{field:'name',title:'联系人姓名',width:100},
				{field:'phone',title:'联系人电话',width:100},
				{field:'taobaoName',title:'淘宝昵称',width:100},
				{field:'taobaoId',title:'淘宝订单号',width:100},
				{field:'taobaoOrderStatus',title:'淘宝订单状态',width:100},
				{field:'paipaiName',title:'拍拍昵称',width:100},
				{field:'paipaiId',title:'拍拍订单号',width:100},
				{field:'paipaiOrderStatus',title:'拍拍订单状态',width:100},
				{field:'deliveryName',title:'物流公司名称',width:100},
				{field:'deliveryNo',title:'物流单号',width:100},
				{field:'address',title:'联系人地址',width:100}
				
			]] 
		});
	};

	loadGrid();	
	
	
	var editRows = function(){
      
        var rows = $("#template-table").datagrid('getSelections');
        if (rows.length != 1 ) {
            $.messager.alert('提示','请选中一条记录进行编辑');  
		} else if (rows.length > 1) {
		    $.messager.alert('提示','您选择了多条记录，只能选择一条记录进行编辑');  
		} else if (rows.length == 1) {
	        tempForm.form('clear');
	       
	        tempForm.form('load', {
					id: rows[0].id,
					phone: rows[0].phone,
					name: rows[0].name
			});

		    tempDialog.dialog('open');
		    
		  }
    };
    var addRows = function(){
        
         tempForm.form('clear');
            
         tempDialog.dialog('open');
           
         
       
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
							url: currenthost + '/personal/delContact.json',
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
    
    var	tempDialog = $('#tempDialog').show().dialog({
			modal: true,
			title: '联系人',
			
			buttons: [{
				text: '保存',
				handler: function() {
					$('#tempForm').form('submit', {
						url: currenthost + "/personal/saveContact.json",
						success: function(result) {
							var r = jQuery.parseJSON(result);
							if (r.success) {
								$('#tempDialog').dialog('close');
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
			   $('#tempDialog').dialog('close');
			 }
			}
			]
		}).dialog('close');
		
		var addTaobaoRows = function(){
		  $('#importTaobaoDialog').attr('src', currenthost+'/personal/importTaobao.htm');
		  $('#importTaobaoDialog').dialog('open');
		};
		
		var	importTaobaoDialog = $('#importTaobaoDialog').show().dialog({
			modal: true,
			title: '选择淘宝订单'

		}).dialog('close');
});

function doSearch(){  
    $('#template-table').datagrid('load',{  
        contactName: $('#contactName').val(),  
        orderStatus: $('#orderStatus').val()  
    });  
} 
    
function personalSearch(e){
   var e = e || window.event; 
	if(e.keyCode == 13){ 
	  doSearch();
	} 
} 