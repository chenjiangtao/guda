var selectedNode;
$(document).ready(function() {
	creatTree();
	$("#addParent").bind("click", {
		isParent : true
	}, addGatewayGroup);
	var height = $("#pRight_c").height();
	$("#zTreeDemoBackground").css({
		"height" : height + "px"
	});
	$("#scrollContentContainer").css({
		"height" : height + "px"
	});
	$("#span_close_btn").click(function() {
		$("[id^='gatewayGroup']").removeAttr("checked");
		$("#selectAll").removeAttr("checked");
		$("#mask").hide();
		$("#memberContainer").hide();
	});

	$("#span_addMember_btn").click(function() {
		add();
	});
});

// 树的一些设置，例如编辑，选择等
var setting = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false
	},
	edit : {
		enable : true,
		editNameSelectAll : true,
		showRenameBtn : setRenameBtn,
		removeTitle : setRemoveTitle,
		renameTitle : "修改该网关分组名称",
		drag : {
			isMove : false,
			isCopy : false
		}
	},
	async : {
		enable : true,
		url : currenthost + "/admin/getChildGatewayGroup.ajax",
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
		beforeClick : beforeClick,
		beforeExpand : beforeExpand,
		beforeCollapse : beforeCollapse
	}
};

/**
 * 网关分组(父节点)显示"编辑按钮"图标，网关(叶子节点)不显示"编辑按钮"图标
 * 
 * @param treeId
 * @param treeNode
 * @returns
 */
function setRenameBtn(treeId, treeNode) {
	return treeNode.isParent;
}

/**
 * 设置当鼠标移入编辑按钮时显示的title
 * 
 * @param treeId
 * @param treeNode
 * @returns
 */
function setRemoveTitle(treeId, treeNode) {
	return treeNode.isParent ? "删除该网关分组" : "删除该网关";
}

/**
 * 在网关分组后增加一个“添加按钮”
 * 
 * @param treeId
 * @param treeNode
 */
function addHoverDom(treeId, treeNode) {
	if (treeNode.isParent) {// 子节点网关不显示添加按钮
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.id
				+ "' title='新增网关' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.id);
		if (btn)
			btn.bind("click", function() {
				selectedNode = treeNode;
				addShow();
				return false;
			});

	}
}

function removeHoverDom(treeId, treeNode) {
	// 因为这个增加按钮是我们这里js写的，所以我们要删掉这个按钮，不是封装里面的
	$("#addBtn_" + treeNode.id).unbind().remove();
};

/**
 * 在删除前的一些回调函数
 * 
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function beforeRemove(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	if (treeNode.isParent) {
		var test = confirm('确认删除 网关分组--("' + treeNode.name + '")吗？');
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
	var testRel = confirm('确认在网关分组("' + pNode.name + '")中删除 网关--("'
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
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	if (!checkGatewayGroupName(newName)) {
		alert("网关分组长度不能超过30个字且不能为空");
		zTree.cancelEditName();
		return false;
	}
	var test = change(treeNode, newName);
	// if(test ==="areaNameExist"){
	// alert("同组织下单位名称都已存在");
	// zTree.cancelEditName();
	// return false;
	// }
	return test;
}

function beforeClick(treeId, treeNode, clickFlag) {
	var nodes = treeNode.children;
	$("#areaList").html("");
	var html = createTrHtml(treeNode,treeNode.getParentNode());
	$("#areaList").append(html);
//	if (nodes) {
//		$("#areaList").html("");
//		for ( var i = 0; i < nodes.length; i++) {
//			var node = nodes[i];
//			var html = createTrHtml(node,treeNode);
//			$("#areaList").append(html);
//		}
//	}
	return (treeNode.click != false);
}

function beforeExpand(treeId, treeNode) {
	var nodes = treeNode.children;
	$("#areaList").html("");
	var html = createTrHtml(treeNode,treeNode.getParentNode());
	$("#areaList").append(html);
//	if (nodes) {
//		$("#areaList").html("");
//		for ( var i = 0; i < nodes.length; i++) {
//			var node = nodes[i];
//			var html =createTrHtml(node,treeNode);
//			$("#areaList").append(html);
//		}
//	}
	return (treeNode.expand !== false);
}

function beforeCollapse(treeId, treeNode) {
	var nodes = treeNode.children;
	$("#areaList").html("");
	var html = createTrHtml(treeNode,treeNode.getParentNode());
	$("#areaList").append(html);
//	if (nodes) {
//		$("#areaList").html("");
//		for ( var i = 0; i < nodes.length; i++) {
//			var node = nodes[i];
//			var html =createTrHtml(node,treeNode);
//			$("#areaList").append(html);
//		}
//	}
	return (treeNode.collapse !== false);
}

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
	if (parentNode) {
		$("#areaList").html("");
		var html = createTrHtml(parentNode,parentNode.getParentNode());
		$("#areaList").append(html);
	}
	if (!childNodes) {
		return null;
	}
//	$("#areaList").html("");
//	for ( var i = 0, l = childNodes.length; i < l; i++) {
//		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
//		var node = childNodes[i];
//		var html =createTrHtml(node,parentNode);
//		$("#areaList").append(html);
//	}
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
function creatTree() {
	var zNodes;
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/admin/getFirstGatewayGroup.ajax",
		success : function(data) {
			zNodes = data;
		}
	});
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
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
 * 弹出增加网关分组成员的对话框
 */
function addShow() {
	$("[id^='gatewayGroup']").removeAttr("checked");
	$("#selectAll").removeAttr("checked");
	var gatewayGroupId = selectedNode.id;
	$("#message").html("");
	// 将本网关分组中已添加的网关默认勾选上
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/admin/beforeAddgateGroup.ajax",
		data : {
			"gatewayGroupId" : gatewayGroupId
		},
		success : function(data) {
			if (data) {
				if (data.length <= 0) {
					$("[id^='gatewayGroup']").removeAttr("checked");
					$("#selectAll").removeAttr("checked");
				} else {
					var gatewayInfoSize = $("#gatewayInfoSize").val();
					if (data.length == gatewayInfoSize) {
						$("#selectAll").attr("checked", true);
					}
					for ( var i = 0; i < data.length; i++) {
						$("#gatewayGroup" + data[i].gatewayId).attr("checked",
								true);
					}
				}
				$("#gatewayGroupName").html('  ("'+selectedNode.name+'")');
				$("#mask").show();
				$("#memberContainer").show();
			} else {
				$("#gatewayGroupName").html("");
				$("#mask").hide();
				$("#memberContainer").hide();
				// TODO 提示消息 msg
			}
		}
	});
}

/**
 * 往指定的网关分组中增加网关
 */
function add() {
	var gatewayGroupId = selectedNode.id;
	var gatewayIdStr = "";
	var subList = $("[id^='gatewayGroup']:checked");
	if (subList != null && subList.length > 0) {
		for ( var j = 0; j < subList.length; j++) {
			gatewayIdStr += "," + $(subList[j]).val();
		}
	}
	if (gatewayIdStr != "") {
		if (gatewayIdStr.indexOf("on") > 0) {// 判断是否包含字符串"on"
			// 点击全选按钮时checkbox选项底层自动封装了"on"，
			// 利用截取字符串形式将字符串【"on",】去除
			gatewayIdStr = gatewayIdStr.substring(4, gatewayIdStr.length);
		} else {
			// 否则集合为,利用截取字符串形式将字符串【",】去除
			gatewayIdStr = gatewayIdStr.substring(1, gatewayIdStr.length);
		}
	}
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/admin/addGatewayGroup.ajax",
		data : {
			"gatewayGroupId" : gatewayGroupId,
			"gatewayIdStr" : gatewayIdStr
		},
		success : function(data) {
			if (data.result) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.reAsyncChildNodes(selectedNode, "refresh");
				$("[id^='gatewayGroup']").removeAttr("checked");
				$("#selectAll").removeAttr("checked");
				$("#message").html("");
				$("#mask").hide();
				$("#memberContainer").hide();
			}
		}
	});
}
/**
 * 删除网关分组
 * 
 * @param result
 * @param treeNode
 */
function del(result, treeNode) {
	var gatewayGroupId = treeNode.id;
	var pNode = treeNode.getParentNode();
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/admin/delGatewayGroup.ajax",
		data : {
			"gatewayGroupId" : gatewayGroupId
		},
		success : function(data) {
			if (data === "error") {
				result = false;
				alert("删除网关分组时系统异常！");
			} else {
				result = data;
				$("#areaList").html("");
				$("#areaList").append("");
			}
		}
	});
}

/**
 * 在相应的网关分组中删除关联表中当前所选网关的数据
 * 
 * @param result
 * @param treeNode
 */
function delRel(result, treeNode) {
	var gatewayId = treeNode.id;
	var gatewayGroupId = treeNode.pId;
	var pNode = treeNode.getParentNode();
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/admin/delGatewayGroupRel.ajax",
		data : {
			"gatewayId" : gatewayId,
			"gatewayGroupId" : gatewayGroupId
		},
		success : function(data) {
			if (data === "error") {
				result = false;
				alert("在网关分组中删除网关时系统异常！");
			} else {
				result = data;
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.reAsyncChildNodes(pNode, "refresh");
			}
		}
	});
}

/**
 * 更新网关分组
 * 
 * @param treeNode
 * @param gatewayGroupName
 * @returns {Boolean}
 */
function change(treeNode, gatewayGroupName) {
	var update = false;
	var gatewayGroupId = treeNode.id;
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/admin/updateGatewayGroup.ajax",
		data : {
			"gatewayGroupName" : gatewayGroupName,
			"gatewayGroupId" : gatewayGroupId,
			"parentId" : treeNode.pId
		},
		success : function(data) {
			if (data) {
				update = data;
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.reAsyncChildNodes(treeNode, "refresh");
			} else {
				update = false;
				alert("修改网关分组名称时出现系统异常！");
			}
		}
	});
	return update;
}

function checkGatewayGroupName(gatewayGroupName) {
	var gatewayGroupName = $.trim(gatewayGroupName);
	if (gatewayGroupName.length > 30 || gatewayGroupName == 0) {
		return false;
	} else {
		return true;
	}
}

function createTrHtml(node,pNode) {
	var gatewayInfo = new Array();
	var ajaxResult;
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		data : {
			"id" : node.id
		},
		url : currenthost + "/admin/queryGatewayInfoById.ajax",
		success : function(data) {
			ajaxResult = data;
		}
	});
	var html = "";
	if(ajaxResult){
			gatewayInfo = ajaxResult.resultObject;
			if (gatewayInfo && ajaxResult.info == "umsGateWayInfo") {
				html += "<tr>" 
					+ "<td title=" + pNode.name + ">" + pNode.name + "</td>"
					+ "<td title=" + gatewayInfo.gatewayName + ">" + gatewayInfo.gatewayName + "</td>"
					+ "<td title=" + gatewayInfo.type + ">" + gatewayInfo.type + "</td>" 
					+ "<td title=" + gatewayInfo.spNumber + ">" + gatewayInfo.spNumber + "</td>"
					+ "<td title=" + gatewayInfo.host + ">" + gatewayInfo.host + "</td>"
					+ "<td title=" + gatewayInfo.port + ">" + gatewayInfo.port + "</td>"
					+ "<td title=" + gatewayInfo.sourceAddr + ">" + gatewayInfo.sourceAddr + "</td>"
					+ "</tr>";
			} else if(gatewayInfo && ajaxResult.info == "umsGateWayInfos") {
				for(var i=0;i<gatewayInfo.length;i++) {	
					html += "<tr>"; 
					if(i == 0){
						html += "<td title='" + node.name + "' rowspan="+gatewayInfo.length+">" + node.name + "</td>"
					}
					html +=	"<td title=" + gatewayInfo[i].gatewayName + ">" + gatewayInfo[i].gatewayName + "</td>"
						+ "<td title=" + gatewayInfo[i].type + ">" + gatewayInfo[i].type + "</td>" 
						+ "<td title=" + gatewayInfo[i].spNumber + ">" + gatewayInfo[i].spNumber + "</td>"
						+ "<td title=" + gatewayInfo[i].host + ">" + gatewayInfo[i].host + "</td>"
						+ "<td title=" + gatewayInfo[i].port + ">" + gatewayInfo[i].port + "</td>"
						+ "<td title=" + gatewayInfo[i].sourceAddr + ">" + gatewayInfo[i].sourceAddr + "</td>"
						+ "</tr>";
				}
			} else if(ajaxResult.info == "noGroupMembers"){
				html += "<tr>"
					 + "<td title=" + node.name + ">" + node.name + "</td>"
					 + "<td title='该网关分组中还没有添加网关' colspan='6' style='padding-left:85px;color:green;'>该网关分组中还没有添加网关</td>"
					 + "</tr>";
			}
	}
	return html;
}

function addGatewayGroup(e) {
	var gatewayGroup;
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/admin/createGatewayGroupId.ajax",
		success : function(data) {
			gatewayGroup = data;
		}
	});
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), isParent = e.data.isParent, nodes = zTree
			.getSelectedNodes(), treeNode = nodes[0];
	if (gatewayGroup) {
		if (!treeNode) {
			treeNode = zTree.addNodes(null, {
				id : (gatewayGroup.id),
				pId : 0,
				isParent : isParent,
				name : gatewayGroup.gatewayGroupName
			});
		} else {
			zTree = $.fn.zTree.getZTreeObj("treeDemo"),
					isParent = e.data.isParent, nodes = null, treeNode = null;
			treeNode = zTree.addNodes(null, {
				id : (gatewayGroup.id),
				pId : 0,
				isParent : isParent,
				name : gatewayGroup.gatewayGroupName
			});
		}
	}
};

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