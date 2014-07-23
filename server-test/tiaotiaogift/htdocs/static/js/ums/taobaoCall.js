function openService(obj){
  var code = obj.name;
  jQuery.ajax({
        type:'POST',
		url: currenthost + '/taobao/openService.json',
		data: {
			code: code
		},
		dataType: 'json',
		success: function(result) {
			if (result.success) {
				$.messager.alert('提示',$("#"+code+"-oper").val()+'成功');  
				if($("#"+code+"-state").html() == '开通'){
				    $("#"+code+"-state").html('关闭')
				}else{
				    $("#"+code+"-state").html('开通')
				}
				if($("#"+code+"-oper").val() == '开通'){
				    $("#"+code+"-oper").val('关闭')
				}else{
				    $("#"+code+"-oper").val('开通')
				}
				
			}else{
			   $.messager.alert('提示','服务器错误，请稍后再试');  
			}
			
		}
	});
}