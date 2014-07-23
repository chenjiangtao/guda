var selectedNode;
var userId;
$(document).ready(function() {
	userId = $("#userId").val();
	creatTree();
	setCheck();
	//	countNodes();
	var height = $("#treeDemo").height();
	$("#treeDemo").css({
		"height" : height + "px"
	});
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
});

function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), type = {
		"Y" : "ps",
		"N" : "ps"
	};// 设置父子节点不关联
	zTree.setting.check.chkboxType = type;// 把该不关联的规则类型赋予zTree生成树
}

// 加载节点的时候，进行过滤，这里对名字进行去空白
function filter(treeId, parentNode, childNodes) {
	if (!childNodes) {
		return null;
	}
	if(parentNode){
		$("#areaList").html("");
		var html = createTrHtml(parentNode.id,parentNode.name);
		$("#areaList").append(html);
	}
	for ( var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		var node = childNodes[i];
		var html = createTrHtml(node.id,node.name);
		$("#areaList").append(html);
		if (parentNode.checked) {
			childNodes[i].checked = true;
		}
	}
	return childNodes;
}

function createTrHtml(id,name){
	var html = "<tr>"+"<td title="+id+">"+id+"</td>"+"<td title="+name+">"+name+"</td>"+"</tr>";
	return html;
}

// 异步加载时刷新节点。也就是让他的子节点显示出来。
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

function getChildOnClick(event, treeId, treeNode) {
	$.fn.zTree.init($("#treeDemo"), {
		check : {
			enable : true
		},
		async : {
			enable : true,
			url : currenthost + "/admin/getAssignChildArea.ajax",
			autoParam : [ "id" ],
			otherParam : [ "userId", userId ],
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
			onCheck : zTreeOnCheck,
			beforeClick: beforeClick,
			beforeExpand: beforeExpand,
			beforeCollapse: beforeCollapse
		}
	}, treeNode);
}

//加载根节点的时候，进行过滤，这里对名字进行去空白
function rootFilter(treeId, parentNode, childNodes) {
	for ( var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		if (childNodes[i].pId == '0' && childNodes[i].checked) {
			$("#selectAll").attr("checked", true);
		}
	}
	return childNodes;
}

function creatTree() {
	var zNodes;
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/admin/getAssignFirstArea.ajax",
		data : {
			"userId" : userId
		},
		success : function(data) {
			zNodes = data;	
			var checkedLength = 0;
			for ( var i = 0; i < data.length; i++) {
				if (data[i].checked) {
					checkedLength += 1;
				}
			}
			if (data.length == checkedLength) {
				$("#selectAll").attr("checked", true);
			} else {
				$("#selectAll").attr("checked", false);
			}
		}
	});
	
	$.fn.zTree.init($("#treeDemo"), {
		callback : {
			onNodeCreated : getChildOnClick
		},
		view : {
			showLine : true
		},
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true,
				parent : true
			}
		}
	},zNodes);

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

	$("#save_btn").click(function() {
		getNodes();
	});
}

/*
 * 获取当前被勾选中的节点
 */
function getNodes() {
	var userId = $("#userId").val();
	var userName = $("#userName").html();
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	if (nodes == null || nodes.length <= 0) {
		$("#giveAreaError").html("请在下方勾选具体要分配的单位,再点击保存按钮");
		$("#giveAreaError").show();
		return;
	}
	var areaCodeStr = '';
	for ( var i = 0, l = nodes.length; i < l; i++) {
		var parentNode = nodes[i].getParentNode();
		if (parentNode != null && parentNode.checked) {
			continue;
		}
		areaCodeStr += nodes[i].id + ",";// 获取所有节点的id值(此时存的是单位编码areaCode)，拼成字符串，用逗号分隔
	}
	$.ajax({
		cache : false,
		async : false,
		type : "POST",
		url : currenthost + "/admin/role/giveArea.ajax",
		data : {
			"userName" : userName,
			"areaCodeStr" : areaCodeStr,
			"userId" : userId
		},
		success : function(data) {
			$("#giveAreaError").html(data.info);
			$("#giveAreaError").show();
		}
	});
}

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
 * 点击【全选】全部选中，否则全部取消选中状态
 */
function allOnClick() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var isChecked = $("#selectAll").is(":checked");// 判断【全选】是否被选中
	if (isChecked) {
		treeObj.checkAllNodes(true);
	} else {
		treeObj.checkAllNodes(false);
	}
	//countNodes();
}

/**
 * 当点击生成树checkbox时触发的事件
 * 
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnCheck(event, treeId, treeNode) {
	//	countNodes();
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var checkedNodes = treeObj.getCheckedNodes(true);// 获取选中的节点
	var nodes = treeObj.transformToArray(treeObj.getNodes());// 获取全部节点
	if (checkedNodes.length == nodes.length) {
		$("#selectAll").attr("checked", true);
	} else {
		$("#selectAll").attr("checked", false);
	}
}

function beforeClick(treeId, treeNode, clickFlag){
	var nodes = treeNode.children;
	$("#areaList").html("");
	var html = createTrHtml(treeNode.id,treeNode.name);
	$("#areaList").append(html);
	if(nodes){		
		for(var i = 0;i< nodes.length;i++){
			var node = nodes[i];
			var html = createTrHtml(node.id,node.name);
			$("#areaList").append(html);
		}
	}
	return (treeNode.click != false);
}


function beforeExpand(treeId, treeNode) {
	var nodes = treeNode.children;
	$("#areaList").html("");
	var html = createTrHtml(treeNode.id,treeNode.name);
	$("#areaList").append(html);
	if(nodes){		
		for(var i = 0;i< nodes.length;i++){
			var node = nodes[i];
			var html = createTrHtml(node.id,node.name);
			$("#areaList").append(html);
		}
	}
	return (treeNode.expand !== false);
}

function beforeCollapse(treeId, treeNode) {
	var nodes = treeNode.children;
	$("#areaList").html("");
	var html = createTrHtml(treeNode.id,treeNode.name);
	$("#areaList").append(html);
	if(nodes){		
		for(var i = 0;i< nodes.length;i++){
			var node = nodes[i];
			var html = createTrHtml(node.id,node.name);
			$("#areaList").append(html);
		}
	}
	return (treeNode.collapse !== false);
}

///**
// * 统计当前被勾选的节点数
// */
//function countNodes() {
//	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//	var checkCount = zTree.getCheckedNodes(true).length;
//	$("#checkCount").text(checkCount);
//}
