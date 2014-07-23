
var selectedNode;

$(document).ready(function(){
			creatTree();
			var height = $("#pRight_c").height();			
			$("#zTreeDemoBackground").css({"height":height+"px"});
			$("#scrollContentContainer").css({"height":height+"px"});			
			$("#span_close_btn").click(function(){
				$("#memberContainer").hide();
			});
			
			$("#span_addMember_btn").click(function(){
				add();
			});
		});


//树的一些设置，例如编辑，选择等
var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				removeTitle: "删除单位",
				renameTitle: "修改单位",
				drag:{
						isMove:false,
						isCopy:false
					}
			},
			async: {
				enable: true,
				url:currenthost + "/admin/getChildArea.ajax",
				autoParam:["id"],
				dataFilter: filter
			},
			data: {
				simpleData: {
					enable: true
				},
			keep:{
					parent:true
				}
			},
			callback: {
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				beforeClick: beforeClick,
				beforeExpand: beforeExpand,
				beforeCollapse: beforeCollapse
			}
		};


//用于编辑，增加一个节点
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.id
		+ "' title='增加单位' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.id);
	if (btn) btn.bind("click", function(){
	selectedNode = treeNode;
	addShow();
	return false;
	});
}

//删除一个节点
function removeHoverDom(treeId, treeNode) {
	//因为这个增加按钮是我们这里js写的，所以我们要删掉这个按钮，不是封装里面的
	$("#addBtn_"+treeNode.id).unbind().remove();
};

//在删除前的一些回调函数。
function beforeRemove(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	var test = confirm("确认删除 单位-- " + treeNode.name + " 及其以下的所有单位吗？");
	if(!test){
		return false;
	}
	del(test,treeNode);
	if(!test){
		return false;
	}
	return true;
	 
}

function beforeRename(treeId, treeNode, newName) {
	var test = false;
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	if(!checkAreaName(newName)){
		alert("长度不能超过12且不能为空");
		zTree.cancelEditName();
		return false;
	}
	var test = change(treeNode,newName);
	if(test ==="areaNameExist"){
		alert("同组织下单位名称都已存在");
		zTree.cancelEditName();
		return false;
	}
	return test;
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

//加载节点的时候，进行过滤，这里对名字进行去空白
function filter(treeId, parentNode, childNodes) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");	
		if(parentNode){
			$("#areaList").html("");
			var html = createTrHtml(parentNode.id,parentNode.name);
			$("#areaList").append(html);
		}	
		if (!childNodes){
				return null;
		} 		
		for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				var node = childNodes[i];
				var html = createTrHtml(node.id,node.name);
				$("#areaList").append(html);
		} 
		return childNodes;
		}

//异步加载时刷新节点。也就是让他的子节点显示出来。
function refreshNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			type = e.data.type,
			silent = e.data.silent,
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				alert("请先选择一个父节点");
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.reAsyncChildNodes(nodes[i], type, silent);
				if (!silent) zTree.selectNode(nodes[i]);
			}
		}

//页面初始加载的时候创建一棵树
function creatTree(){
	var zNodes;
	$.ajax( {
		cache : false,
		async : false,
		type : "POST",
		url :currenthost+ "/admin/getFirstArea.ajax",
		success : function(data) {
			zNodes = data;
		}
	});
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	//树加载时候的一些配置
	$("#refreshNode").bind("click", {type:"refresh", silent:false}, refreshNode);
	$("#refreshNodeSilent").bind("click", {type:"refresh", silent:true}, refreshNode);
	$("#addNode").bind("click", {type:"add", silent:false}, refreshNode);
	$("#addNodeSilent").bind("click", {type:"add", silent:true}, refreshNode);

}

//跳出增加单位的对话框
function addShow(){
	$("#areaNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	$("#areaCodeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	$("#areaName").val("");
	$("#areaCode").val("");
	$("#message").html("");
	$("#memberContainer").show();	
}


//增加单位
function add(){
	var parentId = selectedNode.id;
	var areaCode = $("#areaCode").val();
	var areaName = $("#areaName").val();
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var childrenNodes = zTree.getNodesByParam ("pId",parentId, selectedNode);
	var test = true;
	if(!checkAreaCode(areaCode)){
		$("#areaCodeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#areaCodeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	if(!checkAreaName(areaName)){
		$("#areaNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#areaNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	if(test){
		$.ajax( {
			cache : false,
			async : false,
			type : "POST",
			url :currenthost+ "/admin/area/add.ajax",
			data:{"parentId":parentId,"areaCode":areaCode,"areaName":areaName},
			success : function(data) {
				if(data === "exist"){
					$("#message").html("单位号已经存在");
				}else if(data==="success"){
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//					zTree.addNodes(selectedNode, {id:areaCode, pId:selectedNode.id, name:areaName,isParent:true});
					zTree.reAsyncChildNodes(selectedNode, "refresh")
					$("#areaName").val("");
					$("#areaName").val("");
					$("#appCode").val("");
					$("#message").html("");
					$("#memberContainer").hide();						
				}else if(data==="error"){
					$("#message").html("增加单位时出现系统异常！");
				}else if(data==="allExist"){
					$("#message").html("单位号与同组织下单位名称都已存在");
				}else if(data==="areaNameExist"){
					$("#message").html("同组织下单位名称都已存在");
				}else {
					$("#message").html("添加失败");
				}	
			}
		});	
	}
	
	
}
//删除单位
function del(result,treeNode){
	var areaCode = treeNode.id;
	$.ajax( {
		cache : false,
		async : false,
		type : "POST",
		url :currenthost+ "/admin/area/del.ajax",
		data:{"areaCode":areaCode},
		success : function(data) {
			if(data==="error"){
				result = false;
				alert("删除单位时系统异常！");
			}else{
				result = data;	
			}					
		}
	});		
}


//更新单位
function change(treeNode,areaName){
	
	var update = false;
	var areaCode = treeNode.id;
	$.ajax( {
		cache : false,
		async : false,
		type : "POST",
		url :currenthost +"/admin/area/update.ajax",
		data:{"areaName":areaName,"areaCode":areaCode,"parentId":treeNode.pId},
		success : function(data) {
			if(data==="error"){
				update = false;
				alert("修改单位名称时出现系统异常！");
			}else if(data==="areaNameExist"){
				update=data;
				
			}else if(data){
				update = data
			}else{
				update = false;
				alert("由于系统或者其他原因导致修改失败");
			}
		}
	});
	return update;
}


function checkAreaName(areaName){
	var areaName = $.trim(areaName);
	if(areaName.length>12||areaName==0){
		return false;
	}else{
		return true;
	}
}

function checkAreaCode(areaCode){
	areaCode = $.trim(areaCode);
	 var regexp = /^[0-9]{1,36}$/;
	  if(!regexp.exec(areaCode)){
		  return false;
		  }
	  return true;
}

function createTrHtml(id,name){
	var html = "<tr>"+"<td title="+id+">"+id+"</td>"+"<td title="+name+">"+name+"</td>"+"</tr>";
	return html;
}

function areaCodeCheck(){
	var areaCode = $("#areaCode").val();
	if(!checkAreaCode(areaCode)){
		$("#areaCodeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#areaCodeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function areaNameCheck(){
	var areaName = $("#areaName").val();
	if(!checkAreaName(areaName)){
		$("#areaNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#areaNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}


















