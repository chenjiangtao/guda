$(document).ready(function(){
  var tempForm = $('#tempForm').form();
  var loadGrid = function(){
	  var p = $('#admin-user-table').datagrid({
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
				iconCls: 'icon-add',
				text: '充值',
				handler: function(){ 
				   chargeRows();
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
	        title: "用户列表",
	        pageNumber: 1,
	        singleSelect: false,
	        pageSize: 20,
			pagination:true,
			checkOnSelect:true,
			selectOnCheck:true,
			url:currenthost+'/ums/admin/loadUser.json',
			columns:[[
			    {field: 'id',checkbox: true},
				{field:'userId',title:'登录id',width:100},
				{field:'password',title:'密码',width:100},
				{field:'phone',title:'手机号',width:100},
				{field:'email',title:'邮箱',width:150},
				{field:'status',title:'状态',width:50},
				{field:'grade',title:'等级',width:50},
				{field:'balance',title:'账户余额',width:100},
				{field:'balanceLock',title:'冻结余额',width:100},
				
				{field:'gmtCreated',title:'创建时间',width:250,formatter: function(value, rec, index){
				    if (value == undefined) {
					     return "";
					}
				    return utcToDate(value);
					        
				}}
			]] 
		});
	};

	loadGrid();	
	
	var editRows = function(){
      
        var rows = $("#admin-user-table").datagrid('getSelections');
        if (rows.length != 1 ) {
            $.messager.alert('提示','请选中一条记录进行编辑');  
		} else if (rows.length > 1) {
		    $.messager.alert('提示','您选择了多条记录，只能选择一条记录进行编辑');  
		} else if (rows.length == 1) {
	        tempForm.form('clear');
	       
	        tempForm.form('load', {
					id: rows[0].id,
					userId: rows[0].userId,
					password: rows[0].password,
					phone: rows[0].phone,
					email: rows[0].email,
					status: rows[0].status,
					grade: rows[0].grade,
					balance: rows[0].balance,
					balanceLock: rows[0].balanceLock
			});

		    tempDialog.dialog('open');
		    
		  }
    };
    var addRows = function(){
         tempForm.form('clear');
         tempDialog.dialog('open'); 
       
    }
    
    var removeRows = function(){
        var rows = $("#admin-user-table").datagrid('getSelections');
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
							url: currenthost + '/ums/admin/delUser.json',
							data: {
								id: ids
							},
							dataType: 'json',
							success: function(result) {
								if (result.success) {
									$('#admin-user-table').datagrid('load');
									
								}
								jQuery.messager.show({
									title: '提示',
									msg: result.msg
								});
							},
							error:function() { alert("error"); }
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
						url: currenthost + "/ums/admin/saveUser.json",
						success: function(result) {
							var r = jQuery.parseJSON(result);
							if (r.success) {
								$('#tempDialog').dialog('close');
								$('#admin-user-table').datagrid('load');
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
		
		
	var chargeForm = $('#chargeForm').form();
	var chargeRows = function(){
	   var rows = $("#admin-user-table").datagrid('getSelections');
        if (rows.length != 1 ) {
            $.messager.alert('提示','请选中一条记录进行编辑');  
		} else if (rows.length > 1) {
		    $.messager.alert('提示','您选择了多条记录，只能选择一条记录进行编辑');  
		} else if (rows.length == 1) {
	        chargeForm.form('clear');
	       
	        chargeForm.form('load', {
					userId: rows[0].id,
					userName: rows[0].userId
			});

		    chargeDialog.dialog('open');
		    
		  }
    }
	 var	chargeDialog = $('#chargeDialog').show().dialog({
			modal: true,
			title: '充值',
			
			buttons: [{
				text: '保存',
				handler: function() {
					$('#chargeForm').form('submit', {
						url: currenthost + "/ums/admin/chargeUser.json",
						success: function(result) {
							var r = jQuery.parseJSON(result);
							if (r.success) {
								$('#chargeDialog').dialog('close');
								$('#admin-user-table').datagrid('load');
							}
							$.messager.alert('提示',r.msg);  
							 
						}
					});
				}
			},
			{
			 text: '取消',
			 handler: function() {
			   $('#chargeDialog').dialog('close');
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