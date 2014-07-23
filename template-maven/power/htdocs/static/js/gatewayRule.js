$(document).ready(function(){
	  $("#receiveRegx").blur(function(){
		  receiveRegxCheck();
	  });
	  $("#appIdRegx").blur(function(){
		  appIdRegxCheck();
	  });
	  $("#contentRegx").blur(function(){
		  contentRegxCheck();
	  });
	  $("#gatewayId").blur(function(){
		  gatewayIdCheck();
	  });
	  $("#span_send_btn").click(function(){
		  check();
	  });
	  
	  $('input').keydown(function(e){
			
			if(e.keyCode==13){
				$("#gatewayRule").submit();
			}	
		});
	 
});
 
//对表单各项进行验证
function check(){
	var test =true;
	var receiveRegx = $.trim($("#receiveRegx").val());
	if(receiveRegx.length>100){
		$("#receiveRegxMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#receiveRegxMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var gatewayId = $.trim($("#gatewayId").val());
	if(!gatewayId.length>0){
		$("#gatewayIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#gatewayIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var appIdRegx = $("#appIdRegx").val();
	if(appIdRegx.length>500){
		$("#appIdRegxMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test =false;
	}else{
		$("#appIdRegxMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	
	var contentRegx = $("#contentRegx").val();
	if(contentRegx.length>200){
		$("#contentRegxMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#contentRegxMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(test){
		$("#form_book").submit();//都通过验证条件才可提交表单
	}
}




function receiveRegxCheck(){
	var receiveRegx = $.trim($("#receiveRegx").val());
	if(receiveRegx.length>100){
		$("#receiveRegxMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#receiveRegxMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function gatewayIdCheck(){
	var gatewayId = $.trim($("#gatewayId").val());
	if(!gatewayId.length>0){
		$("#gatewayIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#gatewayIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function appIdRegxCheck(){
	var appIdRegx = $.trim($("#appIdRegx").val());
	if(appIdRegx.length>500){
		$("#appIdRegxMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#appIdRegxMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function contentRegxCheck(){
	var contentRegx = $.trim($("#contentRegx").val());
	if(contentRegx.length>200){
		$("#contentRegxMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#contentRegxMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}






