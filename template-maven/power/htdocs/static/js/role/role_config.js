$(document).ready(function() {
	$(".input_text").keydown(function(e) {
		if (e.keyCode == 13) {
			$("#form_book").submit();
		}
	});

	$("[id$='Item']").each(function() {
		$(this).click(function() {
			$("[id$='Item']").removeAttr("checked");
			$(this).attr("checked", "checked");
		});
	});
	
	$("#org_admin").each(function() {
		$(this).click(function() {
		if($("#org_admin").is(":checked") == true){
			$("#customOrgNameSpan").show();
		}else{
		    $("#customOrgNameSpan").hide();
		    $("#customOrgName").val("");
		}
		});
	});

	chooseCheckBox();
	setTab('one', '1');// 默认拼音检索的标签样式设置在“ABCD”上
	$("#save_btn").click(function() {
		giveRole();// 单击保存按钮调用分配具体角色的方法
	});

	var curRoleName = $("#curRoleName").val();
	if (curRoleName == 'ROLE_APP') {
		$("#" + "role_ROLE_APP").attr("checked", "checked");// 如果当前用户角色是应用管理员，则应用管理员单选按钮默认为选中状态
		$("#appmain").show();// 如果是应用管理员，显示应用名
	} else if (curRoleName == 'ROLE_ADMIN') {
		$("#" + "role_ROLE_ADMIN").attr("checked", "checked");// 如果当前用户角色是系统管理员，则系统管理员单选按钮默认为选中状态
		$("#appmain").hide();// 如果是系统管理员，隐藏应用名
	} else if (curRoleName == 'ROLE_QUERY'){
		$("#" + "role_ROLE_QUERY").attr("checked", "checked");// 如果当前用户角色是短信查询，则短信查询单选按钮默认为选中状态
		$("#appmain").hide();// 如果是短信查询，隐藏应用名
	} else{
		$("#" + "role_ROLE_NORMAL").attr("checked", "checked");// 如果当前用户角色是普通员工，则普通员工单选按钮默认为选中状态
		$("#appmain").hide();// 否则就是普通员工，隐藏应用名
	}
});

/**
 * 点击返回按钮时返回当前页
 * 
 * @param pageId
 */
function selectCurPageId(pageId) {
	window.location.href = currenthost + "/admin/role/roleconfig.htm?pageId="
			+ pageId;
}

/**
 * 切换收缩工具栏
 * 
 * @param index
 *            全选下标
 * @param type
 *            全选类型为app
 */
function toggle(index, type) {
	var display = $("#" + type + "itembox" + index).css("display");
	if (display == "none") {
		$("#" + type + "itembox" + index).slideDown(300);// 收缩工具栏【应用选择】
	} else {
		$("#" + type + "itembox" + index).slideUp(300);// 展开工具栏【应用选择】
	}
}

/**
 * 点击全选可以选中所有的多选按钮框
 */
function allOnClick() {
	var isChecked = $("#allapp0").is(":checked");// 判断【全选按钮】是否被选中
	$("[id^='appCheck']").attr("checked", isChecked);

	// $("input[name="+type+index+"]").attr("checked",isChecked);
}

/**
 * 如果全部选中勾上全选框，全部选中状态时取消了其中一个则取消全选框的选中状态
 */
function selectedCheckBox() {
	var appInfoSize = $("#app_info_size").val();
	var chooseAppSize = $("[id^='appCheckAll']:checked").length;// 获取所选的集合
	if (appInfoSize == chooseAppSize) {
		$("#allapp0").attr("checked", "checked");
	} else {
		$("#allapp0").removeAttr("checked");
	}
}

/**
 * 如果全部应用中某个节点勾上，则其他的字母集合与全部应用中相同id的这个节点也勾上
 * 如果全部应用中某个节点取消，则其他的字母集合与全部应用中相同id的这个节点也取消
 */
function comparedAllCheckBox(appId) {
	var isChecked = $("#appCheckAll" + appId).is(":checked");
	$("#appCheckABCD" + appId).attr("checked", isChecked);
	$("#appCheckEFGH" + appId).attr("checked", isChecked);
	$("#appCheckIJK" + appId).attr("checked", isChecked);
	$("#appCheckLMN" + appId).attr("checked", isChecked);
	$("#appCheckOPQR" + appId).attr("checked", isChecked);
	$("#appCheckSTUV" + appId).attr("checked", isChecked);
	$("#appCheckWXYZ" + appId).attr("checked", isChecked);
	$("#appCheckOthers" + appId).attr("checked", isChecked);
	selectedCheckBox();
}
/**
 * 勾选ABCD集合时，全部应用集合与以ABCD开头的应用集合中相同id的这个节点也勾上
 * 取消ABCD集合时，全部应用集合与以ABCD开头的应用集合中相同id的这个节点也取消
 */
function comparedABCDCheckBox(appId) {
	var isChecked = $("#appCheckABCD" + appId).is(":checked");
	$("#appCheckAll" + appId).attr("checked", isChecked);
	selectedCheckBox();
}
function comparedEFGHCheckBox(appId) {
	var isChecked = $("#appCheckEFGH" + appId).is(":checked");
	$("#appCheckAll" + appId).attr("checked", isChecked);
	selectedCheckBox();
}
function comparedIJKCheckBox(appId) {
	var isChecked = $("#appCheckIJK" + appId).is(":checked");
	$("#appCheckAll" + appId).attr("checked", isChecked);
	selectedCheckBox();
}
function comparedLMNCheckBox(appId) {
	var isChecked = $("#appCheckLMN" + appId).is(":checked");
	$("#appCheckAll" + appId).attr("checked", isChecked);
	selectedCheckBox();
}
function comparedOPQRCheckBox(appId) {
	var isChecked = $("#appCheckOPQR" + appId).is(":checked");
	$("#appCheckAll" + appId).attr("checked", isChecked);
	selectedCheckBox();
}
function comparedSTUVCheckBox(appId) {
	var isChecked = $("#appCheckSTUV" + appId).is(":checked");
	$("#appCheckAll" + appId).attr("checked", isChecked);
	selectedCheckBox();
}
function comparedWXYZCheckBox(appId) {
	var isChecked = $("#appCheckWXYZ" + appId).is(":checked");
	$("#appCheckAll" + appId).attr("checked", isChecked);
	selectedCheckBox();
}
function comparedOthersCheckBox(appId) {
	var isChecked = $("#appCheckOthers" + appId).is(":checked");
	$("#appCheckAll" + appId).attr("checked", isChecked);
	selectedCheckBox();
}

/**
 * 默认加载页面时，如果应用管理员所管理的应用个数与数据库中所有的应用个数相同，那么全选的checkbox默认勾选上，否则默认不勾选
 */
function chooseCheckBox() {
	var appInfoSize = $("#app_info_size").val();// 获取全部应用的个数
	var selectAppSizes = $("#select_apps_size").val();// 获取当前应用管理员可获得的应用的个数
	if (appInfoSize == selectAppSizes) {
		$("#allapp0").attr("checked", "checked");
	} else {
		$("#allapp0").removeAttr("checked");
	}
}

/**
 * 根据所选角色判断是否显示应用
 * 
 * @param roleName
 *            角色名
 */
function roletoggle(roleName) {
	if (roleName == "ROLE_APP") {// 如果是应用管理员，显示应用名
		$("#appmain").show();
	} else {
		$("#appmain").hide();// 否则隐藏应用名
	}
}

/**
 * 分配具体角色
 */
function giveRole() {
	var userId = $("#userId").val();
	var obj = getManageResult();// 获取需要分配角色的应用结果集
	if (obj) {
		if (obj.isAppRole == "roleApp" && !obj.appId) {// 如果是应用管理员并且没有选择应用
			$("#giveRoleError").html("请在下方的【应用选择】中选择具体所需管理的应用,再点击保存按钮");
			$("#giveRoleError").show();
			return;
		}
		$.ajax({
			cache : false,
			async : false,
			type : "POST",
			dateType : "json",
			url : currenthost + "/admin/role/giveRole.ajax",
			data : {
				"userId" : userId,
				"appIdStr" : obj.appId,
				"isAppRole" : obj.isAppRole,
				"isOrgAdmin" : obj.orgAdmin,
				"customOrgName" : obj.customOrgName
			},
			success : function(data) {
				$("#giveRoleError").html(data.info);
				$("#giveRoleError").show();
				if (data.result) {
					$("#cur_role_name").html(data.curUserRole);
					$("#cur_role_name").attr("title", data.curUserRole);
				}
			}
		});
	} else {
		$("#giveRoleError").html('无法获取角色信息，请联系管理员');
		$("#giveRoleError").show();
		// $.messager.alert('提示',"无法获取角色信息，请联系管理员",'info');
		return;
	}
}

/**
 * 获取需要分配角色的应用结果集
 * 
 * @return obj
 */
function getManageResult() {
	var obj = new Object;
	var type = isAppRole();// 判断是否是应用管理员角色
	var appId = getSelectAppId(type);// 获取所选的应用
	var orgAdmin = getOrgAdmin();// 获取是否是部门管理员
	var customOrgName  = getCustomOrgName();
	obj.appId = appId;
	obj.isAppRole = type;
	obj.orgAdmin = orgAdmin;
	obj.customOrgName = customOrgName;
	return obj;
}

/**
 * 判断所选角色是否是应用管理员
 * 
 * @return
 */
function isAppRole() {
	var roleList = $("input[name=roleselect]:checked");
	if (roleList != null && roleList.length > 0) {
		for ( var i = 0; i < roleList.length; i++) {
			var roleId = $(roleList[i]).attr("id");
			if (roleId == 'role_ROLE_APP') {// 'roleApp'代表应用管理员
				return "roleApp";
			} else if (roleId == 'role_ROLE_NORMAL') {// 'roleNormal'代表普通员工
				return "roleNormal";
			} else if(roleId == 'role_ROLE_QUERY') {// 'roleQuery'代表短信查询
				return "roleQuery";
			}
		}
	}
	return "roleAdmin";
}

/**
 * 获取所选择的应用appId
 * 
 * @param type
 *            角色类型
 * @return appId 应用ID
 */
function getSelectAppId(type) {
	if (!type || type == "roleAdmin") {// 如果是系统管理员
		return "roleAdmin";
	}
	if (!type || type == "roleNormal") {// 如果是普通员工
		return "roleNormal";
	}
	if (!type || type == "roleQuery") {// 如果是短信查询
		return "roleQuery";
	}
	var appId = "";
	var subList = $("[id^='appCheckAll']:checked");// 获取所选中的应用
	if (subList != null && subList.length > 0) {
		for ( var j = 0; j < subList.length; j++) {
			appId += "," + $(subList[j]).val();
		}
	}
	if (appId != "") {
		if (appId.indexOf("on") > 0) {// 判断是否包含字符串"on"
			// 点击全选按钮时checkbox选项底层自动封装了"on"，
			// 如取到集合为"on","appId1","appId2","appId3"，利用截取字符串形式将字符串【"on",】去除
			appId = appId.substring(4, appId.length);
		} else {
			// 否则集合为,"appId1","appId2","appId3"，利用截取字符串形式将字符串【",】去除
			appId = appId.substring(1, appId.length);
		}
	}
	return appId;
}

/**
 * 获取是否是部门管理员
 * 
 * @return
 */
function getOrgAdmin() {
	var isChecked = $("#org_admin").is(":checked");// 判断【部门管理员】是否被选中
	if (isChecked == false) {
		return "0"//"0"表示不是部门管理员
	}
	return "1";//"1"表示是部门管理员
}

function getCustomOrgName() {
	return $("#customOrgName").val();
}

/**
 * 切换按拼音检索时切换样式
 */
var name_0 = 'one';
var cursel_0 = 1;
function setTab(name, cursel) {
	cursel_0 = cursel;
	for ( var i = 0; i <= 8; i++) {
		var menu = document.getElementById(name + i);
		var menudiv = document.getElementById("con_" + name + "_" + i);
		if (i == cursel) {
			if (menu != null) {
				if (cursel != 0) {
					menu.className = "off";
				} else {
					menu.className = "offAll";
				}
			}
			if (menudiv != null) {
				menudiv.style.display = "block";
			}
		} else {
			if (menu != null) {
				menu.className = "index_color";
			}
			if (menudiv != null) {
				menudiv.style.display = "none";
			}
		}
	}
}
