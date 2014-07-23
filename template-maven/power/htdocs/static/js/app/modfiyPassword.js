$(document).ready(function(){
	if($.browser.msie) {
		 $("#srcoll").css("width","100%");
	 }
	 if($.browser.mozilla){
		 if( $("#srcoll").height()>=370){
			 $("#srcoll").css("width","101.7%");
		 }		 
	 }
	
	 
	$("#span_pwd_submit_btn").click(function() {
		if(checkPwd()){
			$("#pwd_form").submit();
		}
	});
	
});


/**
 * 点击返回按钮时返回当前页
 * 
 * @param pageId
 */
function selectCurPageId(pageId){
	window.location.href = currenthost+"/appAdmin/appList.htm?pageId="+pageId;				
}

 

function checkPwd(){
	$("#errorMsg").html("");
	$("#errorMsg").removeClass("success").addClass("error");
	var newPassword = $.trim($("#newPassword").val());
	var rePassword = $.trim($("#rePassword").val());
	
//	var passwordReg = /^[a-zA-Z0-9]{4,20}$/;
	var isValid = true;
	
	
	if(!newPassword){
		$("#newPwdMsg").html("请输入新密码");
		$("#newPwdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		isValid = false;
	}else {
		$("#newPwdMsg").html("请输入新密码");
		$("#newPwdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	
	if(!rePassword){
		$("#rePwdMsg").html("请输入确认密码");
		$("#rePwdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		isValid = false;
	}else if( newPassword && newPassword != rePassword){
		$("#rePwdMsg").html("新密码和确认密码不一致");
		$("#rePwdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		isValid =  false;
	}else{
		$("#rePwdMsg").html("请输入确认密码");
		$("#rePwdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	return isValid;
}
