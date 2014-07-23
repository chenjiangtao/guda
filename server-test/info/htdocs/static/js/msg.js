$(function() {
	$("#msg-submit").click(function() {
		
			$("#msg-Form").submit();
		
	});
});

$(document).ready(function(){
		
	$('#msg-Form').keydown(function(e){
		if(e.keyCode==13){	
			$("#msg-Form").submit();//提交表单
		}
	});
	initFocus();
});