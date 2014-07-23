var selectedNode;
var memberArray = [];
var externalMemberArray = [];
//var defaultTip = "搜索...";
var treeDemoIndex = '0'; // 判断当前所选是“我的群组”还是“搜索结果”选项卡
/**
 * 提供“我的工作台”->“发送消息”页面->"添加群组"按钮的弹出界面提供的js
 */
$(document).ready(function() {
	createTree();
	var height = $("#pRight_c").height();
	$("#zTreeDemoBackground").css({
		"height" : height + "px"
	});
	$("#scrollContentContainer").css({
		"height" : height + "px"
	});
	$("#span_close_btn").click(function() {
		$("#mask").hide();
		$("#memberContainer").hide();
		memberArray = [];
		externalMemberArray = [];
	});
	
	$("#span_closeAddMember_btn").click(function() {
		$("#secondMask").hide();
		$("#externalMember").hide();
	});
	
	$("#span_closeAddNewGroup_btn").click(function() {
		$("#secondMask").hide();
		$("#addNewGroup").hide();
	});
	
	$("#span_addMember_btn").click(function() {
		add();
	});
	
	$("#span_addNewGroup_btn").click(function() {
		addZtreeGroup();
	});
	
	var refreshBtn = $("#span_refresh_btn");
	refreshBtn.click(function() {
		createTree();	
		var zTreeSearch = $.fn.zTree.getZTreeObj("treeDemoSearch");
		if(zTreeSearch){
			zTreeSearch.destroy();
		}
		key.val('');
		$("#searchTip").show();
	});
	
//  增加群组
//	$("#addParent").click(function() {
//		addZtreeGroupDialog();
//	});
	
	// 搜索按钮	
	$("#searchButton").click(function() {
		searchNode();
	});
	
	$("#delBatchGroup").click(function() {
		delBatchGroup();// 批量删除
	});
	
	$("#sendGroupMsg").click(function() {
		sendGroupMsg();// 批量发送
	});
			
	$("#displayTip").mouseover(function(){
		  $("#addGroupDialog").show();
	});
	
	$("#displayTip").mouseout(function(){
		  $("#addGroupDialog").hide();
	});
	
	key = $("#key");
	key.bind("focus", focusKey)
	.bind("blur", blurKey)
	.bind("propertychange", searchNode)
	.bind("input", searchNode);
});


/**
 * 批量发送
 */
function sendGroupMsg() {
	if (treeDemoIndex == '0') {
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
		checkedNodes = zTree.getCheckedNodes(true);
	} else {
		zTree = $.fn.zTree
				.getZTreeObj("treeDemoSearch");
		checkedNodes = zTree.getCheckedNodes(true);
	}
	var isPerson = true;// 因为是按群组发送，如果当前仅勾选某些成员而没有勾选群组，则提示发送消息对象必须是群组
	if (checkedNodes && checkedNodes != '' && checkedNodes.length > 0) {
		for ( var i = 0; i < checkedNodes.length; i++) {
			if(checkedNodes[i].id == 0 || !(checkedNodes[i].isParent)){
				continue;
			}
			isPerson = false;
			var arg = new Object();
			arg.id = checkedNodes[i].id + "_group";		
			arg.name = checkedNodes[i].name;
			$("#receive_user_id_input_id").tokenInput("add", arg);	
		}
	}
	if(!isPerson){
		$("#mask").hide();
		$("#memberContainer").hide();
	}else{
		alert('请勾选需要发送消息的"群组"，再点击"发送消息"按钮来发送消息');
	}	
}

/**
 * 批量删除
 */
function delBatchGroup(){
	var zTree;
	var checkedNodes;
	if (treeDemoIndex == '0') {
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
		checkedNodes = zTree.getCheckedNodes(true);
	} else {
		zTree = $.fn.zTree
				.getZTreeObj("treeDemoSearch");
		checkedNodes = zTree.getCheckedNodes(true);
	}
//	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//	var checkedNodes = zTree.getCheckedNodes(true);
	if(checkedNodes == ''){
		alert('请勾选要删除的群组或群组成员再点击“批量删除”按钮');
		return;
	}
	var idStr = '';
	var nameStr = '';
	var rootNodeCheck = false;
	for(var i=0,l=checkedNodes.length;i<l;i++){
		var nodeId = checkedNodes[i].id;
		var nodeName = checkedNodes[i].name;
		if(nodeId == 0){
			rootNodeCheck = true;
			continue;
		}
		idStr += "," + nodeId;
		nameStr += "," + nodeName;
	}
	if(nameStr != ''){
		nameStr = nameStr.substring(1, nameStr.length);
	}
	var test = false;
	if(rootNodeCheck){
		test = confirm('确认删除 "所有的群组" 吗？');
	}else {
		test = confirm('确认批量删除 群组--("' + nameStr + '")吗？');
	}	
	if (test) {
		delBatchRel(idStr);
	}
}

/**
 * 批量删除所选群组
 * 
 * @param result
 * @param treeNode
 */
function delBatchRel(idStr) {
//	var memberId = treeNode.id;
//	var groupId = treeNode.pId;
//	var pNode = treeNode.getParentNode();
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/user/delBatchGroup.ajax",
		data : {
			"idStr" : idStr
		},
		success : function(data) {
			if (data) {
				if (data.info === "error") {
					result = false;
					alert("批量删除群组时系统异常！");
				} else {
					result = data;
					if (treeDemoIndex == '0') {
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						var nodes = treeObj.getCheckedNodes(true);
						for ( var i = 0, l = nodes.length; i < l; i++) {
							if(nodes[i].id == 0){
								continue;
							}
							treeObj.removeNode(nodes[i]);
						}
						var zTreeSearch = $.fn.zTree.getZTreeObj("treeDemoSearch");
						if(zTreeSearch){
							zTreeSearch.destroy();
						}
					} else {
						var zTreeSearch = $.fn.zTree
								.getZTreeObj("treeDemoSearch");
						var nodes = zTreeSearch.getCheckedNodes(true);
						for ( var i = 0, l = nodes.length; i < l; i++) {
							if(nodes[i].id == 0){
								continue;
							}
							zTreeSearch.removeNode(nodes[i]);
						}
						createTree();
					}
				}
			}
		}
	});
}

// 树的一些设置，例如编辑，选择等
var setting = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false,
		fontCss: getFontCss
	},
	check : {
		enable : true,
		nocheckInherit: false
	},
	edit : {
		enable : true,
		editNameSelectAll : true,
		showRenameBtn : setRenameBtn,
		showRemoveBtn : setRemoveBtn,
		removeTitle : setRemoveTitle,
		renameTitle : "修改该群组名称",
		drag : {
			isMove : false,
			isCopy : false
		}
	},
	async : {
		enable : true,
		url : currenthost + "/selector/getChildrenGroupZTree.ajax",
		autoParam : [ "id" ],
		dataFilter : filter
	},
	data : {
		simpleData : {
			enable : true
		},
		keep : {
			parent : true
		}
	},
	callback : {
		beforeRemove : beforeRemove,
		beforeRename : beforeRename,
		beforeClick : beforeClick
//		beforeExpand : beforeExpand
//		,
//		beforeCollapse : beforeCollapse
	}
};

/**
 * 群组(父节点)显示"编辑按钮"图标，群组成员(叶子节点)不显示"编辑按钮"图标
 * 
 * @param treeId
 * @param treeNode
 * @returns
 */
function setRenameBtn(treeId, treeNode) {
	return treeNode.isParent && treeNode.id != 0;
}

function setRemoveBtn(treeId, treeNode){
	return ((treeNode.isParent && treeNode.id != 0) || (!treeNode.isParent));
}
/**
 * 设置当鼠标移入编辑按钮时显示的title
 * 
 * @param treeId
 * @param treeNode
 * @returns
 */
function setRemoveTitle(treeId, treeNode) {
	return treeNode.isParent ? "删除该群组" : "删除该成员";
}

/**
 * 在群组后增加一个“添加按钮”
 * 
 * @param treeId
 * @param treeNode
 */
function addHoverDom(treeId, treeNode) {
	if (treeNode.isParent && treeNode.id != 0) {// 子节点不显示添加按钮
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag
				|| $("#addInnerBtn_" + treeNode.id).length > 0) {
			return;
		}
		if (treeNode.editNameFlag
				|| $("#addExternalBtn_" + treeNode.id).length > 0) {
			return;
		}
		var addInner = "<span class='button add' id='addInnerBtn_" + treeNode.id
				+ "' title='添加门户用户' onfocus='this.blur();'></span>";
		var addExtenal = "<span class='button addExternal' id='addExternalBtn_" + treeNode.id
				+ "' title='直接输入手机号' onfocus='this.blur();'></span>";
		sObj.after(addExtenal);
		sObj.after(addInner);
		var innerBtn = $("#addInnerBtn_" + treeNode.id);
		var externalBtn = $("#addExternalBtn_" + treeNode.id);
		if (innerBtn)
			innerBtn.bind("click", function() {
				selectedNode = treeNode;
				chooseInnerMember();
				return false;
			});
		if (externalBtn)
			externalBtn.bind("click", function() {
				selectedNode = treeNode;
				$("#secondMask").show();
				$("#externalMember").show();
				return false;
			});
	}
	
	if(treeNode.isParent && treeNode.id == 0){
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag
				|| $("#addRootBtn_" + treeNode.id).length > 0) {
			return;
		}
		var addRoot = "<span class='button add' id='addRootBtn_" + treeNode.id
				+ "' title='添加群组' onfocus='this.blur();'></span>";
		sObj.after(addRoot);
		var rootBtn = $("#addRootBtn_" + treeNode.id);
		if (rootBtn)
			rootBtn.bind("click", function() {
				selectedNode = treeNode;
				addZtreeGroupDialog();
				return false;
			});
	}
}

function removeHoverDom(treeId, treeNode) {
	if (treeNode.id != 0) {
		// 因为这个增加按钮是我们这里js写的，所以我们要删掉这个按钮，不是封装里面的
		$("#addInnerBtn_" + treeNode.id).unbind().remove();
		$("#addExternalBtn_" + treeNode.id).unbind().remove();
	} else {
		$("#addRootBtn_" + treeNode.id).unbind().remove();
	}
}

/**
 * 在删除前的一些回调函数
 * 
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function beforeRemove(treeId, treeNode) {
	var zTree;
	if(treeDemoIndex == '0'){
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
	}else{
		zTree = $.fn.zTree.getZTreeObj("treeDemoSearch");
	}
	zTree.selectNode(treeNode);
	if (treeNode.isParent) {
		var test = confirm('确认删除 群组--("' + treeNode.name + '")吗？');
		if (!test) {
			return false;
		}
		del(test, treeNode);
		if (!test) {
			return false;
		}
		return true;
	}
	var pNode = treeNode.getParentNode();
	var testRel = confirm('确认在群组("' + pNode.name + '")中删除 成员--("'
			+ treeNode.name + '")吗？');
	if (!testRel) {
		return false;
	}
	delRel(testRel, treeNode);
	if (!testRel) {
		return false;
	}
	return true;
}

/**
 * 在重命名的一些回调函数
 * 
 * @param treeId
 * @param treeNode
 * @param newName
 * @returns
 */
function beforeRename(treeId, treeNode, newName) {
	var test = false;
	var zTree;
	if(treeDemoIndex == '0'){
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
	}else{
		zTree = $.fn.zTree.getZTreeObj("treeDemoSearch");
	}
	if (!checkGroupName(newName)) {
		alert("群组名称长度不能超过30个字且不能为空");
		//zTree.cancelEditName();
		return false;
	}
	var test = change(treeNode, newName);
	 if(test ==="groupNameExist"){
		alert("群组名称已存在");
	 	//zTree.cancelEditName();
	 	return false;
	 }
	memberArray = [];
	externalMemberArray = [];
	return test;
}

function beforeClick(treeId, treeNode, clickFlag) {
	var zTree;
	if (treeDemoIndex == '0') {
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
	} else {
		zTree = $.fn.zTree
				.getZTreeObj("treeDemoSearch");
	}
	zTree.reAsyncChildNodes(treeNode, "refresh");
	return (treeNode.click != false);
}

//function beforeExpand(treeId, treeNode) {
//	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//	zTree.reAsyncChildNodes(treeNode, "refresh");
//	return (treeNode.expand !== false);
//}

//function beforeCollapse(treeId, treeNode) {
//	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//	zTree.reAsyncChildNodes(treeNode, "refresh");
//	return (treeNode.collapse !== false);
//}

/**
 * 加载节点的时候，进行过滤，这里对名字进行去空白
 * 
 * @param treeId
 * @param parentNode
 * @param childNodes
 * @returns
 */
function filter(treeId, parentNode, childNodes) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	if (!childNodes) {
		return null;
	}
	if(parentNode.checked){
		for ( var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].checked = true;
		}
	}
	return childNodes;
}

/**
 * 异步加载时刷新节点。也就是让他的子节点显示出来
 * 
 * @param e
 */
function refreshNode(e) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), type = e.data.type, silent = e.data.silent, nodes = zTree
			.getSelectedNodes();
	if (nodes.length == 0) {
		alert("请先选择一个父节点");
	}
	for ( var i = 0, l = nodes.length; i < l; i++) {
		zTree.reAsyncChildNodes(nodes[i], type, silent);
		if (!silent)
			zTree.selectNode(nodes[i]);
	}
}

/**
 * 页面初始加载的时候创建一棵树
 */
function createTree() {
	var zNodes;
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/selector/getFirstGroupZTree.ajax",
		success : function(data) {
			zNodes = data;
		}
	});
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	memberArray = [];
	externalMemberArray = [];
	// 树加载时候的一些配置
	$("#refreshNode").bind("click", {
		type : "refresh",
		silent : false
	}, refreshNode);
	$("#refreshNodeSilent").bind("click", {
		type : "refresh",
		silent : true
	}, refreshNode);
	$("#addNode").bind("click", {
		type : "add",
		silent : false
	}, refreshNode);
	$("#addNodeSilent").bind("click", {
		type : "add",
		silent : true
	}, refreshNode);

}

/**
 * 往指定的群组中增加成员
 */
function add() {
	var nameStr = $.trim($("#memberName").val());
	var userDescStr = $.trim($("#memberDesc").val());
	var phone =  $.trim($("#memberDesc").val());
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
				if (member) {
					externalMemberArray.push(member);
					addMembers(externalMemberArray);
				}
			}
		}else{
			for ( var i = 0; i < userDescs.length; i++) {
				var u = $.trim(userDescs[i]);
				if (u.indexOf("_") != -1) {
					u = u.split("_")[0]
				}
				var member = {
					"userDesc" : $.trim(u),
					"comments" : $.trim(u),
					"phone" : $.trim(phones[i])
				};
			//	addMemberItem(member);
				if (member) {
					externalMemberArray.push(member);
					addMembers(externalMemberArray);
				}
			}
		}
		$("#secondMask").hide();
		$("#externalMember").hide();
		memberArray = [];
		externalMemberArray = [];
	}
}
/**
 * 删除群组
 * 
 * @param result
 * @param treeNode
 */
function del(result, treeNode) {
	var groupId = treeNode.id;
// var pNode = treeNode.getParentNode();
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/user/delGroup.ajax",
		data : {
			"groupId" : groupId
		},
		success : function(data) {
			if (data) {
				if (data.info === "error") {
					result = false;
					alert("删除群组时系统异常！");
				} else {
					result = data;
					if(treeDemoIndex != '0'){
						createTree();
					}else{
						var zTreeSearch = $.fn.zTree.getZTreeObj("treeDemoSearch");
						if(zTreeSearch){
							zTreeSearch.destroy();
						}	
					}
//					$("#groupList").html("");
//					$("#groupList").append("");
				}
			}	
		}
	});
	memberArray = [];
	externalMemberArray = [];
}

/**
 * 在相应的群组中删除关联表中当前所选的成员的数据
 * 
 * @param result
 * @param treeNode
 */
function delRel(result, treeNode) {
	var memberId = treeNode.id;
	var groupId = treeNode.pId;
	var pNode = treeNode.getParentNode();
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/user/delGroupMember.ajax",
		data : {
			"memberId" : memberId,
			"groupId" : groupId
		},
		success : function(data) {
			if (data) {
				if (data.info === "error") {
					result = false;
					alert("在群组中删除成员时系统异常！");
				} else {
					result = data;
					if(treeDemoIndex == '0'){
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						zTree.reAsyncChildNodes(pNode, "refresh");
					}else{
						var zTreeSearch = $.fn.zTree.getZTreeObj("treeDemoSearch");
						zTreeSearch.reAsyncChildNodes(pNode, "refresh");
					}
				}
			}
		}
	});
	memberArray = [];
	externalMemberArray = [];
}

/**
 * 更新群组
 * 
 * @param treeNode
 * @param groupName
 * @returns {Boolean}
 */
function change(treeNode, groupName) {
	var update = false;
	var groupId = treeNode.id;
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/user/updateGroupName.ajax",
		data : {
			"groupName" : groupName,
			"groupId" : groupId,
			"parentId" : treeNode.pId
		},
		success : function(data) {
			if(data){
				if (data.result === true) {
					update = true;
					if(treeDemoIndex == '0'){
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						zTree.reAsyncChildNodes(treeNode, "refresh");
					}else{
						var zTreeSearch = $.fn.zTree.getZTreeObj("treeDemoSearch");
						zTreeSearch.reAsyncChildNodes(treeNode, "refresh");
					}
				} else if (data.info === "groupNameExist") {
					update = data.info;
				} else {
					update = false;
					alert("修改群组名称时出现系统异常！");
				}
			}
		}
	});
	return update;
}

function checkGroupName(groupName) {
	var groupName = $.trim(groupName);
	if (groupName.length > 30 || groupName == 0) {
		return false;
	} else {
		return true;
	}
}

//function createTrHtml(node,pNode) {
//	var umsGroupUserRel = new Array();
//	var ajaxResult;
//	$.ajax({
//		cache : false,
//		async : false,
//		type : "POST",
//		data : {
//			"id" : node.id
//		},
//		url : currenthost + "/user/queryGroupsById.ajax",
//		success : function(data) {
//			ajaxResult = data;
//		}
//	});
//	var html = "";
//
//	if(ajaxResult){
//		umsGroupUserRel = ajaxResult.resultObject;
//			if (umsGroupUserRel && ajaxResult.info == "umsGroupUserRel") {
//				html += "<tr>" 
//					+ "<td title=" + pNode.name + ">" + pNode.name + "</td>"
//					+ "<td title=" + umsGroupUserRel.comments + ">" + umsGroupUserRel.comments + "</td>"
//					+ "<td title=" + new Date(umsGroupUserRel.gmtCreated).format('yyyy-MM-dd hh:mm:ss') + ">" + new Date(umsGroupUserRel.gmtCreated).format('yyyy-MM-dd hh:mm:ss') + "</td>"
//					+ "</tr>";
//			} else if(umsGroupUserRel && ajaxResult.info == "umsGroupUserRelList") {
//				for ( var i = 0; i < umsGroupUserRel.length; i++) {	
//					html += "<tr>"; 
//					if(i == 0){
//						html += "<td title='" + node.name + "' rowspan="+umsGroupUserRel.length+">" + node.name + "</td>"
//					}
//					html +=	"<td title=" + umsGroupUserRel[i].comments + ">" + umsGroupUserRel[i].comments + "</td>"
//						+ "<td title=" + new Date(umsGroupUserRel[i].gmtCreated).format('yyyy-MM-dd hh:mm:ss') + ">" + new Date(umsGroupUserRel[i].gmtCreated).format('yyyy-MM-dd hh:mm:ss') + "</td>" 
//						+ "</tr>";
//				}
//			} else if(ajaxResult.info == "noGroupMembers"){
//				html += "<tr>"
//					 + "<td title=" + node.name + ">" + node.name + "</td>"
//					 + "<td title='该群组中还没有添加成员' colspan='2' style='padding-left:85px;color:green;'>该群组中还没有添加成员</td>"
//					 + "</tr>";
//			}
//	}
//	return html;
//}

function addZtreeGroup(e) {
	var groupName = $.trim($("#groupName").val());
	if (checkGroupForm(groupName)) {
		var userGroup;
		$.ajax({
			cache : false,
			async : false,
			type : "POST",
			url : currenthost + "/user/createNewGroup.ajax",
			data : {
				"groupName" : groupName
			},
			success : function(data) {
				userGroup = data;
			}
		});
		var addSuccess = false;
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), isParent = true, nodes = zTree
				.getSelectedNodes(), treeNode = nodes[0];
		if (userGroup && userGroup !== "error" && userGroup !== "exist") {
			addSuccess = true;
			if (selectedNode.children.length == 0) {
				zTree.reAsyncChildNodes(selectedNode, "refresh");
			} else {
				if (!treeNode) {
					zTree.addNodes(selectedNode, {
						id : (userGroup.id),
						pId : 0,
						isParent : isParent,
						name : userGroup.groupName,
						checked : selectedNode.checked
					});
				}
			}
//			else {
//				zTree = $.fn.zTree.getZTreeObj("treeDemo"),
//						isParent = e.data.isParent, nodes = null,
//						treeNode = null;
//				treeNode = zTree.addNodes(selectedNode, {
//					id : (userGroup.id),
//					pId : 0,
//					isParent : isParent,
//					name : userGroup.groupName,
//					checked : selectedNode.checked
//				});
//			}
//			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//			zTree.reAsyncChildNodes(null, "refresh");
		} else if(userGroup === "exist"){
			$("#groupNameMsg").html("群组名称已存在");
			$("#groupNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		} else {
			$("#groupNameMsg").html("添加群组时出现系统异常");
			$("#groupNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		}
		
		if(addSuccess){
			$("#groupName").val('');
			$("#secondMask").hide();
			$("#addNewGroup").hide();
		}
	}
};

function checkGroupForm(groupName) {
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
	return isValid;
}

/**
 * 点击全选可以选中所有的多选按钮框
 */
function allOnClick() {
	var isChecked = $("#selectAll").is(":checked");// 判断【全选按钮】是否被选中
	$("[id^='gatewayGroup']").attr("checked", isChecked);
}

/**
 * 如果全部选中勾上全选框，全部选中状态时取消了其中一个则取消全选框的选中状态
 */
function selectedCheckBox() {
	var gatewayInfoSize = $("#gatewayInfoSize").val();
	var chooseGatewaySize = $("[id^='gatewayGroup']:checked").length;// 获取所选的集合
	if (gatewayInfoSize == chooseGatewaySize) {
		$("#selectAll").attr("checked", "checked");
	} else {
		$("#selectAll").removeAttr("checked");
	}
}

/**
 * 点击“增加群组”按钮，弹出添加群组对话框
 */
function addGroupDialog(){
	$("#mask").show();
	$("#memberContainer").show();
}

/**
 * 点击“添加群组”按钮，弹出添加群组对话框
 */
function addZtreeGroupDialog(){
	$("#secondMask").show();
	$("#addNewGroup").show();
}

///**
// * 将date类型转换成指定日期格式
// */
//Date.prototype.format = function(format) {
//	var o = {
//		"M+" : this.getMonth() + 1, // month
//		"d+" : this.getDate(), // day
//		"h+" : this.getHours(), // hour
//		"m+" : this.getMinutes(), // minute
//		"s+" : this.getSeconds(), // second
//		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
//		"S" : this.getMilliseconds()
//	// millisecond
//	}
//	if (/(y+)/.test(format))
//		format = format.replace(RegExp.$1, (this.getFullYear() + "")
//				.substr(4 - RegExp.$1.length));
//	for ( var k in o)
//		if (new RegExp("(" + k + ")").test(format))
//			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
//					: ("00" + o[k]).substr(("" + o[k]).length));
//	return format;
//}

/**
 * 点击“添加门户用户”按钮时的弹出框
 */
function chooseInnerMember() {
	memberArray = [];
	$("#subReceiveMenu").hide();
	menuStyle = true;
	var arg = new Object();
	var param = [];
	//arg.type = "person";
	arg.param = param;
	arg.multiple = true;
	arg.provinceId = "1";
	var membersIdStr = '';
	var returnVal = window
			.showModalDialog(currenthost + "/selector/groupSelectorView.htm", arg,
					"dialogWidth:760px;dialogHeight:540px;scroll:no;status:no;resizable:no;")
	if (typeof (returnVal) == "object" && returnVal && returnVal.length > 0) {
		for ( var i = 0; i < returnVal.length; i++) {
			var userDesc = returnVal[i].id;
			if (returnVal[i].id.indexOf("_") != -1) {
				userDesc = userDesc.split("_")[0]
			}
			var member = {
					"userDesc" : userDesc,
					"comments" : returnVal[i].name
				};
			memberArray.push(member);
		}
		addMembers(memberArray);
	}
//	console.info(memberArray);
//	$.ajax({
//		cache : false,
//		async : false,
//		type : "POST",
//		url : currenthost + "/selector/getChildrenGroupZTree.ajax",
//		data : {
//			"id" : selectedNode.id
//		},
//		success : function(data) {
//			zNodes = data;
//		}
//	});
//	console.info(zNodes);
//	var nodes = selectedNode.children;
//	for ( var i = 0; i < memberArray.length; i++) {
//		if (memberArray[i].userDesc == userDesc) {
//			return true;
//		}
//	}
	
	
}

/**
 * 添加成员的ajax请求
 * 
 * @param memberArray
 */
function addMembers(memberArray){
	var relJson = objectToJsonStr(memberArray);
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/user/addMembers.ajax",
		data : {
			"relJson" : relJson,
			"groupId" : selectedNode.id
		},
		success : function(data) {
			$("#memberName").val('');
			$("#memberDesc").val('');
			$("#memberNameMsg").html("请输入成员姓名，多个以“,”隔开");
			$("#memberNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
			$("#memberDescMsg").html("请输入手机号码，多个以“,”隔开");
			$("#memberDescMsg").removeClass("tipErrorMsg").addClass("tipMsg");
			if(treeDemoIndex == '0'){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.reAsyncChildNodes(selectedNode, "refresh");
			}else{
				var zTreeSearch = $.fn.zTree.getZTreeObj("treeDemoSearch");
				zTreeSearch.reAsyncChildNodes(selectedNode, "refresh");
			}
		}
	});
	memberArray = [];
	externalMemberArray = [];
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

function checkMember(nameStr, userDescStr) {
	var isValid = true;
	if (nameStr) {
		var nameArray = nameStr.split(/,|，/);
		for ( var i = 0; i < nameArray.length; i++) {
			if (strlen($.trim(nameArray[i])) > 36) {
				$("#memberNameMsg").html("成员姓名长度不能超过36个字符");
				$("#memberNameMsg").removeClass("tipMsg").addClass(
						"tipErrorMsg");
				isValid = false;
				break;
			}
		}
		if (isValid) {
			$("#memberNameMsg").html("请输入成员姓名，多个以“,”隔开");
			$("#memberNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
	}

	// 外部成员
	if (!userDescStr) {
		$("#memberDescMsg").html("请输入手机号码，多个以“,”隔开");
		$("#memberDescMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		isValid = false;
	} else {
		var userDescs = userDescStr.split(/,|，/);
		var names = '';
		if (nameStr) {// 如果成员姓名非空
			names = nameStr.split(/,|，/);
		}
		if (nameStr && (names != '' && names.length != userDescs.length)) {
			$("#memberDescMsg").html("成员姓名与手机号码数量不一致");
			$("#memberDescMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			isValid = false;
		} else {
			for ( var i = 0; i < userDescs.length; i++) {
				userDescs[i] = $.trim(userDescs[i]);
				if (!isMobile(userDescs[i])) {
					$("#memberDescMsg").html("手机号码【" + userDescs[i] + "】格式不正确");
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
	return isValid;
}

/**
 * 判断所添加的手机号是否已存在
 * 
 * @param userDesc
 * @param array
 * @returns {Boolean}
 */
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

function isInArray(userDesc) {
	for ( var i = 0; i < externalMemberArray.length; i++) {
		if (externalMemberArray[i].userDesc == userDesc) {
			return true;
		}
	}
	return false;
}

function focusKey(e) {
	$("#searchTip").hide();
	var keyValue = $.trim(key.val());
//	if (keyValue == defaultTip) {
//		key.val('');
//	}
	key.removeClass("inputBox_s");
	key.addClass("emptyClick");
}

function blurKey(e) {
	if (key.get(0).value === "") {
		$("#searchTip").show();
	}
	key.removeClass("emptyClick");
	key.addClass("emptyBlur");
}
var lastValue = "", nodeList = [], fontCss = {};
function searchNode(e) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var value = $.trim(key.get(0).value);
		var keyType = "name";
		if (key.hasClass("inputBox_s")) {
			value = "";
		}
		if (lastValue === value) return;
		lastValue = value;
		if (value === "") {
			var zTree =$.fn.zTree.getZTreeObj("treeDemoSearch");
			zTree.destroy();
			$("a[id^='treeDemo_']").css({
				color : "#333","font-weight" : "normal"
			});
			return;
		}
		updateNodes(false);
		nodeList = zTree.getNodesByParamFuzzy(keyType, value);
		searchResult();
		updateNodes(true);
}

function treeDemoSearchFilter(treeId, parentNode, childNodes){
	if(parentNode){
		var zTree = $.fn.zTree.getZTreeObj("treeDemoSearch");
		if (!childNodes) {
			return null;
		}
		if(parentNode.checked){
			for ( var i = 0, l = childNodes.length; i < l; i++) {
				childNodes[i].checked = true;
			}
		}
		return childNodes;
	}
}

var settingSearch = {
		view : {
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			selectedMulti : false
		},
		check : {
			enable : true,
			nocheckInherit : false
		},
		edit : {
			enable : true,
			editNameSelectAll : true,
			showRenameBtn : setRenameBtn,
			showRemoveBtn : setRemoveBtn,
			removeTitle : setRemoveTitle,
			renameTitle : "修改该群组名称",
			drag : {
				isMove : false,
				isCopy : false
			}
		},
		async : {
			enable : true,
			url : currenthost + "/selector/getChildrenGroupZTree.ajax",
			autoParam : [ "id" ],
			dataFilter : treeDemoSearchFilter
		},
		data : {
			simpleData : {
				enable : true
			},
			keep : {
				parent : true
			}
		},
		callback : {
			beforeRemove : beforeRemove,
			beforeRename : beforeRename,
			beforeClick : beforeClick
		}
}

/**
 * 对搜索结果去重
 */
Array.prototype.uniquelize = function() {
	var ra = '';
	var nodes = new Array();
	var parentIds = '';
	var parentNode = [];
	for ( var i = 0; i < this.length; i++) {
		if(this[i].id == 0){// 如果根节点(所有群组)也被选上了，根节点不放入搜索结果中
			continue;
		}
		if(this[i].pId == 0){// 如果模糊匹配到群组名，则搜索结果放入群组
			parentNode = this[i];
		}else {
			parentNode = this[i].getParentNode();// 如果模糊匹配到群组成员，则搜索结果放入该成员所在的群组
		}
		if (ra.indexOf(this[i].id) == -1) {
			if(parentNode && parentNode.id != ''){
				nodes.push(parentNode);
			}else {
				nodes.push(this[i]);
			}
		}
		ra += this[i].id + ",";
	}
	var parentNodes = new Array();
	for(var i = 0; i < nodes.length; i++){
		if(parentIds.indexOf(nodes[i].id) == -1){
			parentNodes.push(nodes[i]);
		}
		parentIds += nodes[i].id + ",";
	}

	return parentNodes;
};

/**
 * 将群组“搜索结果”放入搜索结果选项卡中
 */
function searchResult(){
	var nodes = nodeList.uniquelize();
	$.fn.zTree.init($("#treeDemoSearch"), settingSearch, nodes);
}
function updateNodes(highlight) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	for( var i=0, l=nodeList.length; i<l; i++) {
		nodeList[i].highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}

function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

var key;


/**
 * 切换“我的群组”和“搜索结果”选项卡时切换样式
 */
var cursel_0 = 0;
function setTab(name, cursel) {
	cursel_0 = cursel;
	var menu = '';
	var menudiv = '';
	if (cursel_0 == 0) {
		treeDemoIndex = '0';
		menu = document.getElementById("one0");	
		menudiv = document.getElementById("treeDemo");
		//$("#one0").css({"border-bottom" : "","color": "#00764b"});
		if (menu != null) {
			menu.className = "current";
			menu = document.getElementById("one1");
			if (menu != null) {
				menu.className = "";
			}
			//$("#one1").css({"border-bottom" : "#A8C29F solid 1px","color": ""});
		}
		if (menudiv != null) {
			menudiv.style.display = "block";
			menudiv = document.getElementById("treeDemoSearch");
			if (menudiv != null) {
				menudiv.style.display = "none";
			}
		}
//		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//		zTree.refresh();
	} else {
		treeDemoIndex = '1';
		menu = document.getElementById("one1");
		menudiv = document.getElementById("treeDemoSearch");
		//$("#one1").css({"border-bottom" : "","color": "#00764b"});
		if (menu != null) {
			menu.className = "current";
			menu = document.getElementById("one0");
			if (menu != null) {
				menu.className = "";
			}
			//$("#one0").css({"border-bottom" : "#A8C29F solid 1px","color": ""});
		}
		if (menudiv != null) {
			menudiv.style.display = "block";
			menudiv = document.getElementById("treeDemo");
			if (menudiv != null) {
				menudiv.style.display = "none";
			}
		}
//		var zTreeSearch = $.fn.zTree.getZTreeObj("treeDemoSearch");
//		zTreeSearch.reAsyncChildNodes(n, "refresh");
	}
}