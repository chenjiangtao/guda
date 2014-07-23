
var multiple = true;
var provinceJson = [];
$(document).ready(function(){
	var arg=window.dialogArguments;
	if(typeof(arg)!= "undefined" && arg != null ){
		var param = arg.param;
		var type = arg.type;
		if(typeof(type) != "undefined"){
			if(type == "person"){
				$("#stype option[value='person']").attr("selected", true);
				$("#stype").attr({"disabled":true});
				$("#main_head").html("人员选择");
			}
			if(type == "org"){
				$("#stype option[value='org']").attr("selected", true);
				$("#stype").attr({"disabled":true});
				$("#main_head").html("部门选择");
			}
			if(type == "group"){
				$("#stype option[value='group']").attr("selected", true);
				$("#stype").attr({"disabled":true});
				$("#main_head").html("群组选择");
			}

			if(type == "contact"){
				$("#stype option[value='contact']").attr("selected", true);
				$("#stype").attr({"disabled":true});
				$("#main_head").html("联系人选择");
			}
		}
		if(typeof(param) != "undefined"){
			for(var i=0;i< param.length;i++){
				var readyliHtml = "<span>"+param[i].name+"</span><input type=\"hidden\" value=\""+param[i].id+"\" name=\"id\" />";
				addReadyLi(param[i].id+"",readyliHtml);
			}
		}
		if(typeof(arg.multiple)!= "undefined"){
			multiple = arg.multiple;
		}
	}
	buildJsTree();
	$("#stype").change(function(){
		buildJsTree();
	});
	$("#searchUser").bind("keyup", searchUser);
});


function searchUser(){
	var query = $.trim($("#searchUser").val());
	var len = query.length;
	if(len>0){
		$("#waitChoose").html("查找结果");
		$.ajax( {
			cache : false,
			async : false,
			type : "POST",
			url : currenthost + "/msg/searchUser.ajax",
			data:{"q":query,"user":"user"},
			success : function(data) {
				$("#waitul").html("");
				$.each(data,function(i,item){
					if(!isReady(item.id)){
						var waitliHtml = "<span>"+item.name+"</span><input type=\"hidden\" value=\""+item.id+"\" name=\"id\"/><input type=\"hidden\" value=\""+""+"\" name=\"phone\"/>";
						addWaitLi(item.id,waitliHtml);
					}
				});
			}
		});
	}else{
		$("#waitul").html("");
	}
}


function buildJsTree(){
		var stype = $("#stype").val();
		var url = getFirstUrl(stype);
		var jsonData = getJsonData(url);
		var childrenUrl = getChildrenUrl(stype);
		createTree(jsonData,childrenUrl,stype);
}


function getFirstUrl(stype){
	//alert("获取url");
	var url = currenthost + "/selector/getFirstOrg.ajax";
	if(stype == "group"){
		url = currenthost + "/selector/getFirstGroup.ajax";
		$("#titleDiv").html("选择群组");
	}else if(stype == "person"){
		$("#titleDiv").html("选择人员");
	}else{
		url = currenthost + "/selector/getFirstContact.ajax";
		$("#titleDiv").html("选择联系人");
	}
	return url;
}

function getChildrenUrl(stype) {
	var url = currenthost + "/selector/getChildrenOrg.ajax";
	if (stype == "group") {
		url = currenthost + "/selector/getChildrenGroup.ajax";
	} else if (stype == "contact") {
		url = currenthost + "/selector/getChildrenContact.ajax";
	}
	return url;
}

function getJsonData(url){
	var jsonData = []
	$.ajax( {
		cache : false,
		async : false,
		type : "POST",
		dateType:"json",
		url : url,
		success : function(data) {
			jsonData = data;
		}
	});
	return jsonData;
}

function createTree(jsonData,url,stype){
	$("#waitul").html("");
	$("#treediv").jstree({
		"json_data" : {
			"data" : jsonData,
			"ajax":{
				"url" : url,
				"data" : function (n) {
					return { 'parentId' : n.attr ? n.attr("treeid") : 0 ,'type':stype };
				}

			}
		} ,
		"plugins" : [ "themes", "json_data", "ui" ]
	}).bind("select_node.jstree", function (e, tree_obj) {
			$("#treediv").jstree("toggle_node", "#"+tree_obj.rslt.obj.attr("id"));
			var parentId = tree_obj.rslt.obj.attr("treeid");
			var data = $.trim(tree_obj.rslt.obj.children("a").text());
			$("#searchUser").val("");
			$("#waitChoose").html("可选值");
			if(stype == "person"){
				getUserList(parentId);
			}else if(stype == "org"){
				getOrgList(parentId,data);
			}else if(stype == "group"){
				getGroupList(parentId,data);
			}else if(stype == "contact"){
				getContactList(parentId,data);
			}
		}
	);
}

function getOrgList(parentId,parentName){
	$("#waitul").html("");
	if( typeof(parentName) != "undefined" && !isReady(parentId)){
		var waitliHtml = "<span>"+parentName+"</span><input type=\"hidden\" value=\""+parentId+"\" name=\"id\" />";
		addWaitLi(parentId+"",waitliHtml);
	}
	$.ajax( {
		cache : false,
		async : false,
		type : "POST",
		url :currenthost+ "/selector/getOrgs.ajax",
		data:{"parentId":parentId},
		success : function(data) {
			$.each(data,function(i,item){
				if(!isReady(item.id+"_org")){
					var waitliHtml = "<span>"+item.orgName+"</span><input type=\"hidden\" value=\""+item.id+"_org"+"\" name=\"id\" />";
					addWaitLi(item.id+"_org",waitliHtml);
				}
			});
		}
	});
}

function getUserList(orgId){
	$.ajax( {
		cache : false,
		async : false,
		type : "POST",
		url : currenthost+"/selector/getOrgUsers.ajax",
		data:{"orgId":orgId},
		success : function(data) {
			$("#waitul").html("");
			$.each(data,function(i,item){
				if(!isReady(item.id+"_person")){
					var waitliHtml = "<span>"+item.userName+"</span><input type=\"hidden\" value=\""+item.id+"_person"+"\" name=\"id\"/><input type=\"hidden\" value=\""+item.phone+"\" name=\"phone\"/>";
					addWaitLi(item.id+"_person",waitliHtml);
				}
			});
		}
	});
}

function getGroupList(groupId,groupName){
	$("#waitul").html("");
	if( typeof(groupName) != "undefined" && !isReady(groupId)){
		var waitliHtml = "<span>"+groupName+"</span><input type=\"hidden\" value=\""+groupId+"\" name=\"id\" />";
		addWaitLi(groupId+"",waitliHtml);
	}
//	$.ajax( {
//		cache : false,
//		async : false,
//		type : "POST",
//		url : currenthost+"/selector/getGroupUsers.ajax",
//		data:{"groupId":groupId},
//		success : function(data) {
//			$.each(data,function(i,item){
//				if(!isReady(item.id)){
//					var waitliHtml = "<span>"+item.userName+"</span><input type=\"hidden\" value=\""+item.id+"_person"+"\" name=\"id\" />";
//					addWaitLi(item.id+"_person",waitliHtml);
//				}
//			});
//		}
//	});

}

function getContactList(contactId,name){
	$.ajax( {
		cache : false,
		async : false,
		type : "POST",
		url : currenthost+"/selector/getContact.ajax",
		data:{"contactId":contactId},
		success : function(data) {
			$("#waitul").html("");
			if( typeof(name) != "undefined" && !isReady(contactId)){
				var waitliHtml = "<span>"+name+"</span><input type=\"hidden\" value=\""+contactId+"\" name=\"id\" /><input type=\"hidden\" value=\""+data.phone+"\" name=\"phone\"/>";
				addWaitLi(contactId+"",waitliHtml);
			}
		}
	});
}

function addOne(){
	if(!multiple && $("li[name=readyli]").length > 0){
		alert("只允许选择一条记录");
		return;
	}
	var li = $("li[name=waitli].li_mouse_click");
	if(typeof($(li).attr("id")) !="undefined" && typeof($(li).attr("id")) != null){
		if(!isReady($(li).attr("id"))){
			addReadyLi($(li).attr("id"),$(li).html());
		}
		$(li).remove();
	}else{
		alert("请选择一条记录");
	}
}

function addAll(){
	if(!multiple && ($("li[name=readyli]").length > 0 || $("li[name=waitli]").length > 1)){
		alert("只允许选择一条记录");
		return;
	}
	var lis = $("li[name=waitli]");
	for(var i=0;i<lis.length;i++){
		if(!isReady($(lis[i]).attr("id"))){
			addReadyLi($(lis[i]).attr("id"),$(lis[i]).html());
		}
		$(lis[i]).remove();
	}
}

function removeOne(){

	var li = $("li[name=readyli].li_mouse_click");
	if(typeof($(li).attr("id")) !="undefined" && typeof($(li).attr("id")) != null){
		if(!isWait(li.attr("id"))){
			addWaitLi(li.attr("id"),li.html());
		}
		$(li).remove();
	}else{
		alert("请选择一条记录");
	}
}

function removeAll(){
	var lis = $("li[name=readyli]");
	for(var i=0;i<lis.length;i++){
		if(!isWait($(lis[i]).attr("id"))){
			addWaitLi($(lis[i]).attr("id"),$(lis[i]).html());
		}
		$(lis[i]).remove();
	}
}

function addReadyLi(id,value){
	var readyliHtml = "<li name=\"readyli\" id=\""+id+"\" onmouseover=\"limouseover('"+id+"')\" onmouseout=\"limouseout('"+id+"')\" onclick=\"liclick('"+id+"')\" ondblclick=\"readylidblclick()\">"+value+"</li>";
	$("#readyul").append(readyliHtml);
}

function addWaitLi(id,value){
	var waitliHtml = "<li name=\"waitli\" id=\""+id+"\" onmouseover=\"limouseover('"+id+"')\" onmouseout=\"limouseout('"+id+"')\" onclick=\"liclick('"+id+"')\" ondblclick=\"waitlidblclick()\">"+value+"</li>";
	$("#waitul").append(waitliHtml);
}


function limouseover(id){
	if(id.indexOf('.') >=0){
		id = id.replace(/\./g,"\\.");
	}
	if($("#"+id).attr("class") != "li_mouse_click"){
		$("#"+id).removeClass().addClass("li_mouse_over");
	}
}

function limouseout(id){
	if(id.indexOf('.') >=0){
		id = id.replace(/\./g,"\\.");
	}
	if($("#"+id).attr("class") != "li_mouse_click"){
		$("#"+id).removeClass();
	}
}

function liclick(id){
	if(id.indexOf('.') >=0){
		id = id.replace(/\./g,"\\.");
	}
	$("li[name=waitli],li[name=readyli]").removeClass();
	$("#"+id).removeClass().addClass("li_mouse_click");
}

function waitlidblclick(){
	addOne();
}

function readylidblclick(){
	removeOne();
}

function isReady(id){
	var lis = $("li[name=readyli]");
	for(var i=0;i<lis.length;i++){
		if(id == $(lis[i]).children("input[name=id]").val()){
			return true;
		}
	}
	return false;
}

function isWait(id){
	var lis = $("li[name=waitli]");
	for(var i=0;i<lis.length;i++){
		if(id == $(lis[i]).children("input[name=id]").val()){
			return true;
		}
	}
	return false;
}

function btnSure(){
	var lis = $("li[name=readyli]");
	var retValue = [];
	for(var i=0;i<lis.length;i++){
		var ret = {name:$(lis[i]).children("span").html(),id:$(lis[i]).children("input[name=id]").val(),phone:$(lis[i]).children("input[name=phone]").val()};
		retValue.push(ret);
	}
	window.returnValue=retValue;
	window.close();
}