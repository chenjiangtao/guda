$(document).ready(function(){
	
	$("#userName").blur(function(){
		nameCheck();
	});
	$("#phone").blur(function(){
		phoneCheck();
	});
	$("#email").blur(function(){
		emailCheck();
	});
	
	$("#targetNum").keydown(function(e) {
		if (e.keyCode == 13) {		
			$("#contactSearchForm").submit();
		}
	});
	
	$("#span_add_btn").click(function(){
		$("#nameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#phoneMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#emailMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#phone").val("");
		$("#userName").val("");
		$("#email").val("");
		$("#existMessage").html("")
		$("#userName").focus();
		$("#mask").show();
		$("#memberContainer").show();
	});

	$("#span_close_btn").click(function(){
		$("#mask").hide();
		$("#memberContainer").hide();
		$("#id").val("");
		$("#contactTitle").html("添加联系人");
	});

	$("#span_addContact_btn").click(function(){
		var test = true;
		$("#contactUserName").val($("#searchUserName").val());
		$("#contactPhone").val($("#searchPhone").val());
		$("#contactEmail").val($("#searchEmail").val());
		var userName = $.trim($("#userName").val());
		var phone = $.trim($("#phone").val());
		var email = $.trim($("#email").val());
		var id = $("#id").val();
		if (id == "") {
			$("#contactTitle").html("添加联系人");
		} else {
			$("#contactTitle").html("修改联系人");
		}
		if (!checkPhone(phone)) {
			$("#phoneMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			test = false;
		} else {
			$("#phoneMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
		if (!checkEmail(email)) {
			$("#emailMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			test = false;
		} else {
			$("#emailMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
		if (!checkName(userName)) {
			$("#nameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			test = false;
		} else {
			$("#nameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
		if (test) {
			$.ajax({
				cache : false,
				async : false,
				type : "POST",
				url : currenthost + "/user/addContact.ajax",
				data : {
					"userName" : userName,
					"email" : email,
					"phone" : phone,
					"id" : id
				},
				success : function(data) {
					if ("exist" === data) {
						test = false;
						$("#existMessage").html("联系人手机号已存在");
					}
				}
			});
		}
		if(test){
			$("#myContactForm").submit();
			$("#phone").val("");
			$("#userName").val("");
			$("#email").val("");
			$("#existMessage").html("");
			$("#id").val("");
		}
	});
});

function updateMyContact(id) {
	$("#contactTitle").html("修改联系人");
	$("#nameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	$("#phoneMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	$("#emailMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	$("#existMessage").html("")
	var userName = $("#name" + id).html();
	var phone = $("#phone" + id).html();
	var email = $("#email" + id).html();
	$("#id").val(id);
	$("#userName").val(userName);
	$("#phone").val(phone);
	$("#email").val(email);
	$("#userName").focus();
	$("#mask").show();
	$("#memberContainer").show();
}

function nameCheck() {
	var name = $.trim($("#userName").val());
	if (!checkName(name)) {
		$("#nameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	} else {
		$("#nameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function phoneCheck() {
	var phone = $.trim($("#phone").val());
	if (!checkPhone(phone)) {
		$("#phoneMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	} else {
		$("#phoneMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function emailCheck() {
	var email = $.trim($("#email").val());
	if (!checkEmail(email)) {
		$("#emailMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	} else {
		$("#emailMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
/**
 * 联系人姓名验证
 * 
 * @param userName
 * @returns {Boolean}
 */
function checkName(userName) {
	userName = $.trim(userName);
	if (strlen(userName) > 36) {
		return false;
	}
	return true;
}

/**
 * 联系人手机号验证
 * 
 * @param phone
 * @returns {Boolean}
 */
function checkPhone(phone) {
	phone = $.trim(phone);
	if (!isMobile(phone)) {
		return false;
	}
	return true;
}

/**
 * 联系人邮箱验证
 * 
 * @param email
 * @returns {Boolean}
 */
function checkEmail(email) {
	email = $.trim(email);
	if (email != '' && (!isEmail(email) || strlen(email) > 36)) {
		return false;
	}
	return true;
}
