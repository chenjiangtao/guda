var memberArray = [];

Array.prototype.removeByIndex = function(index) {
	var i = 0, n = 0;
	var arrSize = this.length;
	for (i = 0; i < arrSize; i++) {
		if (this[i] != this[index]) {
			this[n++] = this[i];
		}
	}
	if (n < i) {
		this.length = n;
	}
};

$(document).ready(function() {
	$("#span_add_btn").click(function() {
		var type = $("input[type=radio][name=memberType]:checked").val();
		if (type == 0) {
			$("#memberNameMsg").html("双击选择人员");
		} else {
			$("#memberNameMsg").html("请输入成员姓名，多个以“,”隔开");
		}
		$("#memberDescMsg").html("请输入手机号码，多个以“,”隔开");
		$("#memberNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#memberDescMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#memberName").val("");
		$("#memberDesc").val("");
		$("#mask").show();
		$("#memberContainer").show();
		$("#memberName").focus();
	});

//	$("#import_group_sbtn").click(function(id,name) {
//		$("#importGroupName").html(name);
//		$("#mask").show();
//		$("#memberContainer").show();
//	});
//
	$("#span_importclose_btn").click(function() {
		$("#mask").hide();
		$("#memberContainer").hide();
	});

	$("#span_import_btn").click(function(){
//		var action = document.fileForm.attributes["action"].value;
//		var groupId = $("#groupId").val();
//		var type=$("#")
//		document.fileForm.attributes["action"].value = action+"?groupId="+groupId;
		var fileAdd = $("#fileAdd").val();
		var array = fileAdd.split(".");
		var len = array.length;
		var suffix =array[len-1];
		if(fileAdd.length==0&&('xls'!=suffix)){
			alert('必须选择以xls的excel文件！');
		}else{
			$("#fileForm").submit();//提交表单
		}
	});

	$("#span_close_btn").click(function() {
		$("#mask").hide();
		$("#memberContainer").hide();
	});

	$("input[type=radio][name=memberType]").click(function() {
		var type = $(this).val();
		if (type == "0") {
			$("#memberDescTr").hide();
			$("#requiredmember").show();
			$("#memberNameMsg").html("双击选择人员");
			$("#memberName").attr({
				"readonly" : true
			});
		} else {
			$("#memberDescTr").show();
			$("#requiredmember").hide();
			$("#memberName").removeAttr("readonly");
			$("#memberNameMsg").html("请输入成员姓名，多个以“,”隔开");
			$("#memberDescMsg").html("请输入手机号码，多个以“,”隔开");
		}
		$("#memberName").focus();
		$("#memberNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#memberDescMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#memberName").val("");
		$("#memberDesc").val("");

	});

	$("#span_addMember_btn").click(function() {
		var nameStr = $.trim($("#memberName").val());
		var userDescStr = $.trim($("#memberDesc").val());
		var phone = "";
		if ($(":radio:checked").val() === '1') {
			phone = $.trim($("#memberDesc").val());
		} else {
			phone = $.trim($("#memberPhone").val());
		}
		if (checkMember(nameStr, userDescStr)) {
			var userDescs = userDescStr.split(/,|，/);
			var phones = phone.split(/,|，/);
			if (nameStr) {
				var names = nameStr.split(/,|，/);
				for ( var i = 0; i < names.length; i++) {
					var u = $.trim(userDescs[i]);
					if (u.indexOf("_") != -1) {
						u = u.split("_")[0]
					}
					var member = {
						"userDesc" : $.trim(u),
						"comments" : $.trim(names[i]),
						"phone" : $.trim(phones[i])
					};
					addMemberItem(member);
				}
			}else{
				for ( var i = 0; i < userDescs.length; i++) {
					var u = userDescs[i];
					if (u.indexOf("_") != -1) {
						u = u.split("_")[0]
					}
					var member = {
						"userDesc" : $.trim(u),
						"comments" : $.trim(u),
						"phone" : $.trim(phones[i])
					};
					addMemberItem(member);
				}
			}
			$("#mask").hide();
			$("#memberContainer").hide();
		}

	});

	$("span[name=deleteSpan]").live("click", function() {
		var userDesc = $(this).parent().children("input[name=userDesc]").val();
		$(this).parent().remove();
		removeMember(userDesc);
	});


	$("#memberName").dblclick(function() {
		var type = $("input[type=radio][name=memberType]:checked").val();
		if (type == 0) {
			selectPerson("memberDesc", "memberName", true);
		}
	});

	$("#span_submit_btn").click(function() {
		if (checkGroupForm()) {
			var relJson = objectToJsonStr(memberArray);
			$("#relJson").val(relJson);
			$("#groupForm").submit();
		}

	});

	$('input').keydown(function(e) {

		if (e.keyCode == 13) {
			$("#myGroupForm").submit();
		}
	});

	$("#mask").height($("body").height());
	$("#mask").width($("#hd").width());
	$(window).resize(function() {
		$("#mask").height($("body").height());
		$("#mask").width($("#hd").width());
	});
});

function relJsonToArray() {
	var relJson = $("#relJson").val()
	if (relJson) {
		return eval("(" + relJson + ")");
	}
	return [];
}

function init() {
	var array = relJsonToArray();
	for ( var i = 0; i < array.length; i++) {
		addMemberItem(array[i]);
	}
}

function addMemberItem(member) {
	if (member) {
		if (!isInArray(member.userDesc)) {
			memberArray.push(member);
			var memberStr = "<div class=\"member_item\">"
				memberStr += "<p name=\"comments\" title='" + member.phone + "'>"
				+ member.comments + "</p>";
			memberStr += "<span name=\"deleteSpan\">×</span>";
			memberStr += "<input type=\"hidden\" value=\"" + member.userDesc
					+ "\" name=\"userDesc\" />"
			memberStr += "</div>";
			$("#memberDiv").append(memberStr);
		}
	}
}

function removeMember(userDesc) {
	for ( var i = 0; i < memberArray.length; i++) {
		if (memberArray[i].userDesc == userDesc) {
			memberArray.removeByIndex(i);
			break;
		}
	}
}

function isInArray(userDesc) {
	for ( var i = 0; i < memberArray.length; i++) {
		if (memberArray[i].userDesc == userDesc) {
			return true;
		}
	}
	return false;
}

//
function isExist(userDesc, array) {
	var j = 0;
	for ( var i = 0; i < array.length; i++) {
		if (array[i] == userDesc) {
			j = j + 1;
			if (j > 1) {
				return true;
			}
		}
	}
	return false;
}

function checkMember(nameStr, userDescStr) {

	var type = $("input[type=radio][name=memberType]:checked").val();
	var isValid = true;
	if (!nameStr) {
		if (type == 0) {
			$("#memberNameMsg").html("双击选择人员");
			$("#memberNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			isValid = false;
		}
	} else {
		var nameArray = nameStr.split(/,|，/);
		if (type == 1) {
			for ( var i = 0; i < nameArray.length; i++) {
				if (strlen(nameArray[i]) > 36) {
					$("#memberNameMsg").html("成员姓名长度不能超过36个字符");
					$("#memberNameMsg").removeClass("tipMsg").addClass(
							"tipErrorMsg");
					isValid = false;
					break;
				}
			}
		}
		if (isValid) {
			if (type == 0) {
				$("#memberNameMsg").html("双击选择人员");
			} else {
				$("#memberNameMsg").html("请输入成员姓名，多个以“,”隔开");
			}
			$("#memberNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
	}

	if (type != 0) {// 外部成员
		if (!userDescStr) {
			$("#memberDescMsg").html("请输入手机号码，多个以“,”隔开");
			$("#memberDescMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			isValid = false;
		} else {
			var userDescs = userDescStr.split(/,|，/);
			if (nameStr) {// 如果成员姓名非空
				var names = nameStr.split(/,|，/);
			}
			if (nameStr && (names.length != userDescs.length)) {
				$("#memberDescMsg").html("成员姓名与手机号码数量不一致");
				$("#memberDescMsg").removeClass("tipMsg").addClass(
						"tipErrorMsg");
				isValid = false;
			} else {
				for ( var i = 0; i < userDescs.length; i++) {
					userDescs[i] = $.trim(userDescs[i]);
					if (!isMobile(userDescs[i])) {
						$("#memberDescMsg").html(
								"手机号码【" + userDescs[i] + "】格式不正确");
						$("#memberDescMsg").removeClass("tipMsg").addClass(
								"tipErrorMsg");
						isValid = false;
						break;
					}
				}
				if (isValid) {
					for ( var i = 0; i < userDescs.length; i++) {
						userDescs[i] = $.trim(userDescs[i]);
						if (isExist(userDescs[i], userDescs)) {
							$("#memberDescMsg").html(
									"手机号码【" + userDescs[i] + "】存在多个");
							$("#memberDescMsg").removeClass("tipMsg").addClass(
									"tipErrorMsg");
							isValid = false;
							break;
						}
					}
				}
				if (isValid) {
					for ( var i = 0; i < userDescs.length; i++) {
						userDescs[i] = $.trim(userDescs[i]);
						if (isInArray(userDescs[i])) {
							$("#memberDescMsg").html(
									"手机号码【" + userDescs[i] + "】已添加");
							$("#memberDescMsg").removeClass("tipMsg").addClass(
									"tipErrorMsg");
							isValid = false;
							break;
						}
					}
				}
				if (isValid) {
					$("#memberDescMsg").html("请输入手机号码，多个以“,”隔开");
					$("#memberDescMsg").removeClass("tipErrorMsg").addClass(
							"tipMsg");
				}
			}
		}
	}
	return isValid;
}

function checkUserDescIsMoblie(userDescs) {
	var isValid = true;
	for ( var i = 0; i < userDescs.length; i++) {
		if (!isMobile(userDescs[i])) {
			isValid = false;
			break;
		}
	}
	return isValid;
}

function checkGroupForm() {
	var groupName = $.trim($("#groupName").val());
	var isValid = true;
	if (!groupName) {
		$("#groupNameMsg").html("请输入群组名称");
		$("#groupNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		isValid = false;
	} else if (strlen(groupName) > 90) {
		$("#groupNameMsg").html("群组名称长度不能超过90个字符");
		$("#groupNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		isValid = false;
	} else {
		$("#groupNameMsg").html("请输入群组名称");
		$("#groupNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

//	if (!$("#memberDiv .member_item")
//			|| $("#memberDiv .member_item").length == 0) {
//		$("#groupMemberMsg").html("请添加群组成员");
//		$("#groupMemberMsg").removeClass("tipMsg").addClass("tipErrorMsg");
//		isValid = false;
//	} else {
//		$("#groupMemberMsg").html("请添加群组成员");
//		$("#groupMemberMsg").removeClass("tipErrorMsg").addClass("tipMsg");
//	}
	return isValid;
}

function deleteGroup(id, groupName, param, page) {
	if (confirm("确定要删除群组“" + groupName + "”吗？")) {
		window.location.href = currenthost + "/user/deleteGroup.htm?id=" + id
				+ "&=" + groupName + "&curPage=" + page;
	}
}

function exportGroup(id) {
		window.location.href = currenthost + "/user/exportGroup.htm?id=" + id;
}

function importGroup(id,name){
	$("#importGroupName").html(name);
	$("#groupId").val(id);
	$("#mask").show();
	$("#memberContainer").show();
}

function objectToJsonStr(o) {
	var result = "";
	var tempResult = [];
	if (o instanceof Array) {
		for ( var i = 0; i < o.length; i++) {
			tempResult.push(objectToJsonStr(o[i]));
		}
		result = '[' + tempResult.join(',') + ']';
	} else {
		for ( var key in o) {
			if (o[key] instanceof Array)
				tempResult.push("\"" + key + "\":" + objectToJsonStr(o[key]));
			else
				tempResult.push("\"" + key + "\":" + "\"" + o[key] + "\"");
		}
		result = '{' + tempResult.join(',') + '}'
	}
	return result;
}