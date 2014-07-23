$(document).ready(function(){
		sgipLocalHost();
	  $("#gatewayType").change(function(){
		  sgipLocalHost();
	  });

	  $('input').keydown(function(e){

			if(e.keyCode==13){
				$("#gateway").submit();
			}
		});
});

function sgipLocalHost(){
	 var gatewayType = $("select[name=type]").val();
	  if(gatewayType ==='SGIP'){
		  $("#localportspan").before("<span class='red' id='sgiplocalport'>*</span>");
		  var localHost = $("#localHost").val();
		  sgipLocalPortCheck();
	  }
	  else{
		  $("#sgiplocalport").remove();
		  localPortCheck();
	  }
}


function changeStatus(id,action){
	$.ajax( {
		cache : false,
		async : false,
		type : "POST",
		dateType:"json",
		url : currenthost+ "/gateway/status.ajax",
		data:{"id":id,"action":action},
		success : function(data) {
			if(data==="1"){
				$("#"+id).html("启用");
			}else if(data==="2"){
				$("#"+id).html("禁用");
			}else if(data==="3"){
				alert("操作失败,配置有误或者系统异常");
				$("#"+id).html("启用异常");
			}else{
				alert("操作失败,配置有误或者系统异常");
			}
		}
	});
}

//对服务器IP验证
function checkHost(host){
//	  var regexp = /^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[1-9])$/;
	  if(host.length===0||host.length>20){
		  return false;
	  }
	  return true;
}

//对本地主机IP验证
function checkLocalHost(localHost){
//	  var regexp = /^(((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\.){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[1-9]))$|(^\s*$)/;
	  if(localHost.length>20){
		  return false;
	  }
	  return true;
}

//对服务器端口验证
function checkPort(port){
	  var regexp = /^([1][0][2][5-9]|[1][0][3-9][0-9]|[1][1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|[6][0-4][0-9]{3}|[6][5][0-4][0-9]{2}|[6][5][5][0-2][0-9]|[6][5][5][3][0-5])$/;
	  if(!regexp.exec(port)){
		  return false;
	  }
	  return true;
}

//对本地端口验证
function checkLocalPort(localPort){
	  var regexp = /^([1][0][2][5-9]|[1][0][3-9][0-9]|[1][1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|[6][0-4][0-9]{3}|[6][5][0-4][0-9]{2}|[6][5][5][0-2][0-9]|[6][5][5][3][0-5])$|(^\s*$)/;
	  if(!regexp.exec(localPort)){
		  return false;
	  }
	  return true;
}

//对本地端口验证
function checkSgipLocalPort(localPort){
	  var regexp = /^([1][0][2][5-9]|[1][0][3-9][0-9]|[1][1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|[6][0-4][0-9]{3}|[6][5][0-4][0-9]{2}|[6][5][5][0-2][0-9]|[6][5][5][3][0-5])$/;
	  if(!regexp.exec(localPort)){
		  return false;
	  }
	  return true;
}

//对时间间隔，心跳次数等验证
function checkTime(time){
	  var regexp = /^([0-9]{1,3})$/;
	  if(!regexp.exec(time)){
		  return false;
	  }
	  return true;
}

//对是否调试验证
function checkDebug(debug){
	  var regexp = /^[0-1]?$/;
	  if(!regexp.exec(debug)){
		  return false;
	  }
	  return true;
}

//对用户名验证
function checkSourceAddr(sourceAddr){
	  var regexp = /^[0-9a-zA-Z]{1,20}$/;
	  if(!regexp.exec(sourceAddr)){
		  return false;
	  }
	  return true;
}

//对密码验证
function checkSharedSecret(sharedSecret){
	  var regexp = /^[0-9a-zA-Z]{4,20}$/;
	  if(!regexp.exec(sharedSecret)){
		  return false;
	  }
	  return true;
}

//对服务号码验证
function checkSpNumber(spNumber){
	  var regexp = /^[0-9]{1,20}$/;
	  if(!regexp.exec(spNumber)){
		  return false;
	  }
	  return true;
}

//对表单各项进行验证
function check(){
	//获取个各表单的值
    var gatewayType = $("select[name=type]").val();
	var gatewayName = $("#gatewayName").val();
	var host = $("#host").val();
	var localHost = $("#localHost").val();
	var port = $("#port").val();
	var localPort = $("#localPort").val();
	var sourceAddr = $("#sourceAddr").val();
	var sharedSecret = $("#sharedSecret").val();
	var heartbeatInterval = $("#heartbeatInterval").val();
	var heartbeatNoresponseout = $("#heartbeatNoresponseout").val();
	var readTimeout = $("#readTimeout").val();
	var reconnectInterval = $("#reconnectInterval").val();
	var transactionTimeout = $("#transactionTimeout").val();
	var spNumber = $("#spNumber").val();
	var debug = $("#debug").val();
	var version = $("#version").val();
	var corpId = $("#corpId").val();
	var test = true;

	//判断是否满足验证条件来添加或删除样式
	if(gatewayType == ''){
		$("#gatewayTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#gatewayTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!(gatewayName.length>0 && gatewayName.length<=12)){
		$("#gatewayNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#gatewayNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkHost(host)){
		$("#hostMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#hostMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkLocalHost(localHost)){
		$("#localHostMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#localHostMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkPort(port)){
		$("#portMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#portMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	 if(gatewayType ==='SGIP'){
		 if(!checkSgipLocalPort(localPort)){
				$("#localPortMsg").removeClass("tipMsg").addClass("tipErrorMsg");
				test=false;
			}else{
				$("#localPortMsg").removeClass("tipErrorMsg").addClass("tipMsg");
			}

	 }else{
		 if(!checkLocalPort(localPort)){
				$("#localPortMsg").removeClass("tipMsg").addClass("tipErrorMsg");
				test=false;
			}else{
				$("#localPortMsg").removeClass("tipErrorMsg").addClass("tipMsg");
			}
	 }


	if(!checkSourceAddr(sourceAddr)){
		$("#sourceAddrMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#sourceAddrMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkSharedSecret(sharedSecret)){
		$("#sharedSecretMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#sharedSecretMsg").addClass("tipMsg").removeClass("tipErrorMsg");
	}

	if(!checkTime(heartbeatInterval)){
		$("#heartbeatIntervalMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#heartbeatIntervalMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkTime(heartbeatNoresponseout)){
		$("#heartbeatNoresponseoutMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#heartbeatNoresponseoutMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkTime(readTimeout)){
		$("#readTimeoutMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#readTimeoutMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkTime(reconnectInterval)){
		$("#reconnectIntervalMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#reconnectIntervalMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkTime(transactionTimeout)){
		$("#transactionTimeoutMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#transactionTimeoutMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkSpNumber(spNumber)){
		$("#spNumberMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#spNumberMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkDebug(debug)){
		$("#debugMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#debugMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!checkTime(version)){
		$("#versionMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#versionMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(!(corpId.length<=6)){
		$("#corpIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#corpIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}

	if(test){
		$("#form_book").submit();//都通过验证条件才可提交表单
	}
}


//失去焦点验证




function gatewayTypeCheck(){
	var gatewayType = $("select[name=type]").val();
	if(gatewayType == ''){
		$("#gatewayTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#gatewayTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function gatewayNameCheck(){
	var gatewayName = $.trim($("#gatewayName").val());
	if(!(gatewayName.length>0 && gatewayName.length<=12)){
		$("#gatewayNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#gatewayNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function hostCheck(){
	var host = $("#host").val();
	if(!checkHost(host)){
		$("#hostMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#hostMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function localHostCheck(){
	var localHost = $("#localHost").val();
	if(!checkLocalHost(localHost)){
		$("#localHostMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#localHostMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function portCheck(){
	var port = $("#port").val();
	if(!checkPort(port)){
		$("#portMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#portMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function localPortCheck(){
	var localPort = $("#localPort").val();
	var gatewayType = $("select[name=type]").val();
	if(gatewayType ==='SGIP'){
		if(!checkSgipLocalPort(localPort)){
			$("#localPortMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		}else{
			$("#localPortMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
	}else{
		if(!checkLocalPort(localPort)){
			$("#localPortMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		}else{
			$("#localPortMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
	}

}

function sgipLocalPortCheck(){
	var localPort = $("#localPort").val();
	if(!checkPort(localPort)){
		$("#localPortMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#localPortMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function sourceAddrCheck(){
	var sourceAddr = $("#sourceAddr").val();
	if(!checkSourceAddr(sourceAddr)){
		$("#sourceAddrMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#sourceAddrMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function sharedSecretCheck(){
	var sharedSecret = $("#sharedSecret").val();
	if(!checkSharedSecret(sharedSecret)){
		$("#sharedSecretMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#sharedSecretMsg").addClass("tipMsg").removeClass("tipErrorMsg");
	}
}
function heartbeatIntervalCheck(){
	var heartbeatInterval = $("#heartbeatInterval").val();
	if(!checkTime(heartbeatInterval)){
		$("#heartbeatIntervalMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#heartbeatIntervalMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function heartbeatNoresponseoutCheck(){
	var heartbeatNoresponseout = $("#heartbeatNoresponseout").val();
	if(!checkTime(heartbeatNoresponseout)){
		$("#heartbeatNoresponseoutMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#heartbeatNoresponseoutMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}







function readTimeoutCheck(){
	var readTimeout = $("#readTimeout").val();
	if(!checkTime(readTimeout)){
		$("#readTimeoutMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#readTimeoutMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function reconnectIntervalCheck(){
	var reconnectInterval = $("#reconnectInterval").val();
	if(!checkTime(reconnectInterval)){
		$("#reconnectIntervalMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#reconnectIntervalMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function transactionTimeoutCheck(){
	var transactionTimeout = $("#transactionTimeout").val();
	if(!checkTime(transactionTimeout)){
		$("#transactionTimeoutMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#transactionTimeoutMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function spNumberCheck(){
	var spNumber = $("#spNumber").val();
	if(!checkSpNumber(spNumber)){
		$("#spNumberMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#spNumberMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function debugCheck(){
	var debug = $("#debug").val();
	if(!checkDebug(debug)){
		$("#debugMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#debugMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function versionCheck(){
	var version = $("#version").val();
	if(!checkTime(version)){
		$("#versionMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#versionMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
function corpIdCheck(){
	var corpId = $("#corpId").val();
	if(!(corpId.length<=6)){
		$("#corpIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#corpIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}






