$(document).ready(function(){
    $("#create-btn").click(function() {
        var t = $('#key').val();
        var _validTime = $('#validTime').val();
        if (t == '' ) {
            $.messager.alert('提示','请输入key');  
		}else{
				jQuery.ajax({
					url: currenthost + '/createKey.json',
					data: {
						key: t,
						validTime:_validTime
					},
					dataType: 'json',
					success: function(result) {
						if (result.success) {
						    $('#newKey').val(result.msg);
							
						}else{
						   $.messager.alert('提示', result.msg);
						}
						
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
                              $.messager.alert( '提示', '服务器错误.'+errorThrown);
                    } 
				});
	    }
	});
	$("#del_agent_btn").click(function() {
    
			var t = $('#ip').val();
        if (t == '' ) {
            $.messager.alert('提示','请输入服务器ip');  
		}
		jQuery.messager.confirm('确认', '您是否要删除服务器的相关数据？',
		function(r) {
			if (r) {
				
				jQuery.ajax({
					url: currenthost + '/delAgent.json',
					data: {
						ip: t
					},
					dataType: 'json',
					success: function(result) {
					    
						if (result.success) {
							$.messager.alert( '提示', result.msg);
							
						}else{
						   $.messager.alert('提示', result.msg
							);
						}
						
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
                              $.messager.alert( '提示', '服务器错误.'+errorThrown);
                    } 
				});
			}
		});
	});
})