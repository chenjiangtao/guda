//
//$(document).ready(function(){
//	$("#appId").autocomplete({
//		source: function( request, response ) {
//			$.ajax({
//				url: currenthost + "/searchApps.ajax",
//				dataType: "json",
//				data: {"appName": request.term},
//				success: function( data ) {
//						response( $.map( data, function( item ) {
//							return {							
//								label: item.appName + "(" + item.appId+")",
//								value: item.appId
//							}
//						}));
//				}
//			});
//		},
//		minLength:1
//		,
//		open: function() {
//			$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
//		},
//		close: function() {
//			$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
//		}
//	});
//	$("#subAppId").autocomplete({		
//		source: function( request, response ) {
//			var appId = $("#appId").val();
//			if(!checkAppId(appId)){
//				$("#subAppId").val("");
//				alert("请先选择应用");				
//			}else{	
//			$.ajax({
//				url: currenthost + "/searchSubApps.ajax",
//				dataType: "json",
//				data: {"appId": appId,"appName": request.term},
//				success: function( data ) {
//						response( $.map( data, function( item ) {
//							return {							
//								label: item.appName + "(" + item.appId+")",
//								value: item.appId
//							}
//						}));
//				}
//			});
//		}
//		},
//		minLength:1
//		,
//		open: function() {
//			$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
//		},
//		close: function() {
//			$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
//		}
//	});
//});


$(document).ready(function(){
	$("#appId").change(function(){
		var appId = $("#appId").val();
		$("#subAppId option").remove();// 清除POST后回写的数据
		$("#subAppId").append("<option value=''> 请选择 </option>");// 重新添加首节点
		$.ajax( {
			cache : false,
			async : false,
			type : "POST",
			dateType:"json",
			url : currenthost + "/searchSubApps.ajax",
			data:{"appId": appId},
			success : function(data) {		
				if(data ==""){
					$("#subAppId option").remove();// 使为空的数据快速回显
					$("#subAppId").append("<option value=''> 请选择 </option>");
				}else{
					for(i=0;i<data.length;i++){			
						$("#subAppId").append("<option value="+ data[i].appSubId +">"+ data[i].appSubName + " (" + data[i].appSubId + ")" + "</option>");
					}
				}							
			}
		});
	});
	
	//$(".content").bind("click",showNum).bind("keyup", showNum).bind("blur", function(){$("#divShowNum").hide();});  	
	
});
	
function clearSubAppId(){
	$("#subAppId").val("");
}

function save(){
	var test = true;
	var tempId = $("#tempId").val();
	
	if(!checkTempId(tempId)){
		$("#tempIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#tempIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var appId = $("#appId").val();
	if(!checkAppId(appId)){
		$("#appIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#appIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var type = $("#type").val();
	type = $.trim(type);
	if(!checkType(type)){
		$("#typeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#typeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

//	var msgType = $("#msgType").val();
//	if(!checkMsgType(msgType)){
//		$("#msgTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
//		test = false;
//	}else{
//		$("#msgTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
//	}
	var priority = $("#priority").val();
	if(!check0_999(priority)){
		$("#priorityMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#priorityMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
//	var errorAction = $("#errorAction").val();
//	if(!checkErrorAction(errorAction)){
//		$("#errorActionMsg").removeClass("tipMsg").addClass("tipErrorMsg");
//		test = false;
//	}else{
//		$("#errorActionMsg").removeClass("tipErrorMsg").addClass("tipMsg");
//	}
	
	if(!checkValidTimeScope()){
		$("#validTimeScopeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#validTimeScopeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var recvComments = $("#recvComments").val();
	if(!checkLength(recvComments,0,160)){
		$("#recvCommentsMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#recvCommentsMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	
	var content = $("#content").val();
	content = $.trim(content);
	if(!checkLength(content,1,600)){
		$("#contentMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#contentMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var bizName = $("#bizName").val();
	bizName = $.trim(bizName);
	if(!checkLength(bizName,0,50)){
		$("#bizNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#bizNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var bizType = $("#bizType").val();
	bizType = $.trim(bizType);
	if(!checkLength(bizType,0,50)){
		$("#bizTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#bizTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var subBizType = $("#subBizType").val();
	subBizType = $.trim(subBizType);
	if(!checkLength(subBizType,0,50)){
		$("#subBizTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#subBizTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	if(test){
		$("#form_book").submit();
	}

}


function checkTempId(tempId){
	tempId = $.trim(tempId);
	 var regexp = /^[0-9a-zA-Z]{1,10}$/;
	  if(!regexp.exec(tempId)){
		  return false;
		  }
	  return true;
}

function checkValidTimeScope(validTimeScope){
	var validTimeScopeStart = $("#d4313").val();
	var validTimeScopeEnd = $("#d4314").val();
	var test = false;
	if(isEmpty(validTimeScopeStart)&&isEmpty(validTimeScopeEnd)){
		test = true;
	}
	if(!isEmpty(validTimeScopeStart)&&!isEmpty(validTimeScopeEnd)){
		test = true;
	}
	return test;
}


function checkMsgType(msgType){
	msgType = $.trim(msgType);
	 var regexp = /^[1-3]?$/;
	  if(!regexp.exec(msgType)){
		  return false;
		  }
	  return true;
}

function checkType(type){
	type = $.trim(type);
	 if(isEmpty(type)){
		 return false;
	 }
	  return true;
}

//应用ID验证
function checkAppId(appId){
	appId = $.trim(appId);
	 var regexp = /^[0-9]{4}$/;
	  if(!regexp.exec(appId)){
		  return false;
		  }
	  return true;
}

//子应用ID验证
function checkSubAppId(appId){
	appId = $.trim(appId);
	 var regexp = /^[0-9]{0,4}$/;
	  if(!regexp.exec(appId)){
		  return false;
		  }
	  return true;
}

//异常处理验证
function checkErrorAction(errorAction){
	errorAction = $.trim(errorAction);
	 var regexp = /^[1-2]?$/;
	  if(!regexp.exec(errorAction)){
		  return false;
		  }
	  return true;
}

//长度验证
function checkLength(str,min,max){
	str = $.trim(str);
	if(str.length>max||str.length<min){
		return false;
	}else{
		return true;
	}
}


//0-9数字验证,可以为空
function check0_9(num){
	num = $.trim(num);
	 var regexp = /^[0-9]?$/;
	  if(!regexp.exec(num)){
		  return false;
		  }
	  return true;
}

//0-99数字验证,可以为空
function check0_99(num){
	num = $.trim(num);
	 var regexp = /^[0-9]{0,2}$/;
	  if(!regexp.exec(num)){
		  return false;
		  }
	  return true;
}

//0-99数字验证,可以为空
function check0_999(num){
	num = $.trim(num);
	 var regexp = /^[0-9]{0,3}$/;
	  if(!regexp.exec(num)){
		  return false;
		  }
	  return true;
}


//取得字符串的字节长度
function strlen(str) {
    var i = 0;
    var len = 0;

    for (i = 0; i < str.length; i ++) {
        if (str.charCodeAt(i) > 255)  len += 2;  else len ++;
    }
    return len;
}

//应用名称验证。
function validTimeScopeCheck(){
	var validTimeScopeStart = $("#d4313").val();
	var validTimeScopeEnd = $("#d4314").val();
	var test = false;
	if(isEmpty(validTimeScopeStart)&&isEmpty(validTimeScopeEnd)){
		test = true;
	}
	if(!isEmpty(validTimeScopeStart)&&!isEmpty(validTimeScopeEnd)){
		test = true;
	}
	if(!test){
		$("#validTimeScopeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#validTimeScopeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}


function bizTypeCheck(){
	var bizType = $("#bizType").val();
	bizType = $.trim(bizType);
	if(!checkLength(bizType,0,20)){
		$("#bizTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#bizTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function bizNameCheck(){
	var bizName = $("#bizName").val();
	bizName = $.trim(bizName);
	if(!checkLength(bizName,0,20)){
		$("#bizNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#bizNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function subBizTypeCheck(){
	var subBizType = $("#subBizType").val();
	subBizType = $.trim(subBizType);
	if(!checkLength(subBizType,0,20)){
		$("#subBizTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#subBizTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

//应用名称验证。
function tempIdCheck(){
	var tempId = $("#tempId").val();
	tempId= $.trim(tempId);
	if(!checkTempId(tempId)){
		$("#tempIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#tempIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//应用ID验证
function appIdCheck(){
	var appId = $("#appId").val();
	appId = $.trim(appId);
	if(!checkAppId(appId)){
		$("#appIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#appIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//短信类型验证
function msgTypeCheck(){
	var msgType = $("#msgType").val();
	msgType = $.trim(msgType);
	if(!checkMsgType(msgType)){
		$("#msgTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#msgTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//模版类型验证
function typeCheck(){
	var type = $("#type").val();
	type = $.trim(type);
	if(!checkType(type)){
		$("#typeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#typeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}


//子应用Id验证
function subAppIdCheck(){
	var subAppId = $("#subAppId").val();
	subAppId = $.trim(subAppId);
	if(!checkSubAppId(subAppId)){
		$("#subAppIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#subAppIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

//异常处理验证
function errorActionCheck(){
	var errorAction = $("#errorAction").val();
	errorAction = $.trim(errorAction);
	if(!checkErrorAction(errorAction)){
		$("#errorActionMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#errorActionMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}


//优先级验证
function priorityCheck(){
	var priority = $("#priority").val();
	priority = $.trim(priority);
	if(!check0_999(priority)){
		$("#priorityMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#priorityMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

//优先级验证
function contentCheck(){
	var content = $("#content").val();
	content = $.trim(content);
	if(!checkLength(content,1,600)){
		$("#contentMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#contentMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}


function recvCommentsCheck(){
	var recvComments = $("#recvComments").val();
	recvComments = $.trim(recvComments);
	if(!checkLength(recvComments,0,160)){
		$("#recvCommentsMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#recvCommentsMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}




//取得字符串的字节长度
function strlen(str) {
    var i = 0;
    var len = 0;

    for (i = 0; i < str.length; i ++) {
        if (str.charCodeAt(i) > 255)  len += 3;  else len ++;
    }
    return len;
}

// 是否为空字符串
function isEmpty(str) {
	return strlen(str) == 0;
}

/**
 * 显示所属应用下拉框的title
 * 
 * @param content
 */
function showTitle(content){
    var obj = document.getElementById("appId");
    obj.title = content.innerHTML;
}

/**
 * 显示所属子应用下拉框的title
 * 
 * @param content
 */
function showSubTitle(content){
    var obj = document.getElementById("subAppId");
    obj.title = content.innerHTML;
}












