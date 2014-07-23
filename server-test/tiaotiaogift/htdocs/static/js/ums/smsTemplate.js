$(document).ready(function(){
    var tempForm = $('#tempForm').form();
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
				{field:'content',title:'模版内容',width:700},
				{field:'type',title:'模版类型',width:100},
				{field:'gmtModify',title:'最近修改时间',width:150}
				
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
					content: rows[0].content
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
    
    var	tempDialog = $('#tempDialog').show().dialog({
			modal: true,
			title: '短信模版',
			
			buttons: [{
				text: '保存',
				handler: function() {
					$('#tempForm').form('submit', {
						url: currenthost + "/ums/saveSmsTemplate.json",
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
		
		
});

  function insertString(tbid, str){
	    var tb = document.getElementById(tbid);
	    tb.focus();
	    if (document.all){
	        var r = document.selection.createRange();
	        document.selection.empty();
	        r.text = str;
	        r.collapse();
	        r.select();
	    }
	    else{
	        var newstart = tb.selectionStart+str.length;
	        tb.value=tb.value.substr(0,tb.selectionStart)+str+tb.value.substring(tb.selectionEnd);
	        tb.selectionStart = newstart;
	        tb.selectionEnd = newstart;
	    }
	}
	
   function addTag(id,str){
       str = '${'+str+'}';
	   insertString(id,str);
	}