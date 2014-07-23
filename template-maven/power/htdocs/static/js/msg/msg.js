var menuStyle = true;

$(document).ready(function() {
    $("#receive_user_id_input_id").tokenInput(currenthost + "/msg/searchUser.ajax", {
        theme: "facebook"
    },function(){initCallback()});

    $(".mgsContent").bind("click",showNum).bind("keyup", showNum).bind("blur", function(){$("#divShowNum").hide();});
});

var showNum = function(){
	 var d = $(this);
	 var pos = d.offset();
	 var t = pos.top +  this.offsetHeight - 22; // 弹出框的下边位置
	 var l = pos.left  + this.offsetWidth ;  // 弹出框的右边位置
	 var num = this.value.length;
	 $("#changeNum").html(num);
	 //$("#changeNum").html(strlen(this.value));
	 $("#divShowNum").css({ "top": t, "left": l }).show();
}

$(function() {
	$("#span_send_btn").click(function() {
		if(checkMsgForm()){
			filDestName();
			$("#send_msg_form_id").submit();
		}
	});

	$("#save_draft_btn").click(function() {
		if(checkMsgForm()){
			$('#send_msg_form_id').attr('action',currenthost + '/msg/msgSave.htm');
			filDestName();
			$("#send_msg_form_id").submit();
		}
	});

});

function filDestName() {
	var receiveNames = $("#receive_user_id_input_id").tokenInput("get");
	var receiveName = "";
	for ( var i = 0, len = receiveNames.length; i < len; ++i) {
		receiveName = receiveName + receiveNames[i].name + ",";
	}
	if (receiveName.length > 0) {
		receiveName = receiveName.substring(0, receiveName.length - 1);
	}
	$("#receive_user_name_input_id").val(receiveName);
}

function chooseReceive(name) {
	$("#subReceiveMenu").hide();
	menuStyle = true;
	var arg = new Object();
	var param = [];
	arg.type = name;
	arg.param = param;
	arg.multiple = true;
	arg.provinceId = "1";

	var returnVal = window
			.showModalDialog(currenthost + "/selector/selectorView.htm", arg,
					"dialogWidth:760px;dialogHeight:540px;scroll:no;status:no;resizable:no;")

	if (typeof (returnVal) == "object" && returnVal && returnVal.length > 0) {
		var userIdValue = "";
		var userNameVal = "";
		param = [];
		for ( var i = 0; i < returnVal.length; i++) {
			param[i] = {
				"id" : returnVal[i].id,
				"name" : returnVal[i].name,
				"checkid" : returnVal[i].checkid
			};
			// alert(returnVal[i].id+" : "+returnVal[i].name+" :
			// "+returnVal[i].checkid)

			var arg = new Object();
			arg.id = returnVal[i].id;
			arg.name = returnVal[i].name;
			$("#receive_user_id_input_id").tokenInput("add", arg);
		}

	}
}

/**
 * 选择群组
 * 
 * @param name
 */
function chooseGroup(name) {
	$("#subReceiveMenu").hide();
	menuStyle = true;
	var arg = new Object();
	var param = [];
	arg.type = name;
	arg.type = "group";
	arg.param = param;
	arg.multiple = true;
	arg.provinceId = "1";
	
	var returnVal = window
			.showModalDialog(currenthost + "/selector/selectorView.htm", arg,
					"dialogWidth:760px;dialogHeight:540px;scroll:no;status:no;resizable:no;")

	if (typeof (returnVal) == "object" && returnVal.length > 0) {
		var userIdValue = "";
		var userNameVal = "";
		param = [];
		for ( var i = 0; i < returnVal.length; i++) {
			param[i] = {
				"id" : returnVal[i].id,
				"name" : returnVal[i].name,
				"checkid" : returnVal[i].checkid
			};
			// alert(returnVal[i].id+" : "+returnVal[i].name+" :
			// "+returnVal[i].checkid)

			var arg = new Object();
			arg.id = returnVal[i].id;
			arg.name = returnVal[i].name;
			$("#receive_user_id_input_id").tokenInput("add", arg);
		}

	}
}

function initCallback() {
	var names = $("#receive_user_name_input_id").val();
	var ids = document.getElementById("receive_user_id_input_id").title;
	if (ids && ids.length > 0 && names && names.length > 0) {

		var ns = names.split(",");
		var is = ids.split(",");
		for ( var i = 0, len = is.length; i < len; ++i) {
			var obj = new Object();
			obj.id = is[i];
			obj.name = ns[i];
			$("#receive_user_id_input_id").tokenInput("add", obj);
		}
	}
}

function checkMsgForm(){
	var dest = $("#receive_user_id_input_id").val();
	var content = $.trim($("#content").val());
	var validTime = $.trim($("#validTime").val());
	var validTimeReg = /^\d+$/;
	if(!dest){
		$("#errorMsg").html("接收方不能为空");
		return false;
	}else if(!content){
		$("#errorMsg").html("消息内容不能为空");
		return false;
	}else if(validTime && !validTimeReg.test(validTime)){
		$("#errorMsg").html("有效时间只能为数字");
		return false;
	}
	$("#errorMsg").html("");
	return true;
}