
$(function() {
	$("#login_btn").click(function() {
		
			$("#loginForm").submit();
		
	});
});

$(document).ready(function(){
		
	/*
	 * 给登陆窗口添加一个回车事件
	 */
	$('#loginForm').keydown(function(e){
		if(e.keyCode==13){	
			$("#loginForm").submit();//提交表单
		}
	});
	initFocus();
});

function initFocus(){
    if($('#userName').val()==''){
      $('#userName').focus();
    }else{
      $('#password').focus();
    }
} 