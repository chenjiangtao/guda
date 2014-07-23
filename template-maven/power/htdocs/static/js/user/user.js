$(function() {
	$("#span_submit_btn").click(function() {
		if(checkUserForm()){
			$("#userForm").submit();
		}
	});
	
	$("#span_pwd_submit_btn").click(function() {
		if(checkPwd()){
			$("#passwordForm").submit();
		}
	});
})

function checkUserForm(){
	$("#errorMsg").html("");
	$("#errorMsg").removeClass("success").addClass("error");
	var phone = $.trim($("#phone").val());
	if(!phone){
		$("#errorMsg").html("请输入手机号码");
		return false;
	}else if(!isMobile(phone)){
		$("#errorMsg").html("手机号码格式不正确");
		return false;
	}else{
		$("#errorMsg").html("");
		return true;
	}
	
}

function checkPwd(){
	$("#errorMsg").html("");
	$("#errorMsg").removeClass("success").addClass("error");
	var oldPassword = $.trim($("#oldPassword").val());
	var newPassword = $.trim($("#newPassword").val());
	var rePassword = $.trim($("#rePassword").val());
	
	var passwordReg = /^[a-zA-Z0-9]{4,20}$/;
	var isValid = true;
	
	if(!oldPassword){
		$("#oldPwdMsg").html("请输入原密码");
		$("#oldPwdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		isValid = false;
	}else{
		$("#oldPwdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	
	if(!newPassword){
		$("#newPwdMsg").html("请输入新密码，密码由4-20位字母或数字组成");
		$("#newPwdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		isValid = false;
	}else if(!passwordReg.test(newPassword)){
		$("#newPwdMsg").html("新密码必须由4-20位的字母或数字组成");
		$("#newPwdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		isValid = false;
	}else {
		$("#newPwdMsg").html("请输入新密码，密码由4-20位字母或数字组成");
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










