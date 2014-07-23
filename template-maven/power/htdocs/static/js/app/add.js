
function save(){
	var test = true;
	var ip = $("#ip").val();
	if(!validateIps(ip)){
		$("#ipMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#ipMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var appName = $("#appName").val();
	if(!checkLength(appName,1,30)){
		$("#appNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#appNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var appId = $("#appId").val();
	if(!checkAppId(appId)){
		$("#appIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#appIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var port = $("#port").val();
	if(!checkPort(port)){
		$("#portMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#portMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var userName = $("#userName").val();
	if(!checkLength(userName,1,16)){
		$("#userNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#userNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var password = $("#password").val();
	if(!checkPassword(password)){
		$("#passwordMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#passwordMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var priority = $("#priority").val();
	if(!check0_999(priority)){
		$("#priorityMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#priorityMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var signName = $("#signName").val();
	if(!checkLength(signName,0,10)){
		$("#signNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#signNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var fee = $("#fee").val();
	if(!check0_99(fee)){
		$("#feeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#feeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var feeType = $("#feeType").val();
	if(!check0_99(feeType)){
		$("#feeTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#feeTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	var flowDay = $("#flowDay").val();
	var flowMonth = $("#flowMonth").val();
	test = checkFlowAll(flowDay, flowMonth,test);
	if(test){
		$("#form_book").submit();
	}

}

//验证IP
function validateIps(appAddr) {
	if(strlen(appAddr) == 0){
		return true;
	} else if (strlen(appAddr) > 50) {
		return false;
	} else if ("*" != appAddr && !isIPs(appAddr)) {
		return false;
	}
	return true;
}

function isIPs(s) {
	var regexp1 = /^((?:(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\.){3}(?:25[0-5]|2[0-4]\d|[01]?\d?\d))|((?:(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\.){3}(?:\\d\+))|((?:(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\.){2}(?:(\((?:25[0-5]|2[0-4]\d|[01]?\d?\d)\|(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\))\.)(?:25[0-5]|2[0-4]\d|[01]?\d?\d))|((?:(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\.){3}(?:(\((?:25[0-5]|2[0-4]\d|[01]?\d?\d)\-(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\))))$/;
	var regexp2 = /^((?:(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\.){3}(?:\\d\+))$/;
	var regexp3 = /^((?:(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\.){2}(?:(\((?:25[0-5]|2[0-4]\d|[01]?\d?\d)\|(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\))\.)(?:25[0-5]|2[0-4]\d|[01]?\d?\d))$/;
	var regexp4 = /^((?:(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\.){3}(?:(\((?:25[0-5]|2[0-4]\d|[01]?\d?\d)\-(?:25[0-5]|2[0-4]\d|[01]?\d?\d)\))))$/;
	if (regexp1.exec(s) || regexp2.exec(s) || regexp3.exec(s) || regexp4.exec(s)){
		return true;
	}
	return false;
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

//端口验证
function checkPort(port){
	 var regexp = /^([1][0][2][5-9]|[1][0][3-9][0-9]|[1][1-9][0-9]{2}|[2-9][0-9]{3}|[1-5][0-9]{4}|[6][0-4][0-9]{3}|[6][5][0-4][0-9]{2}|[6][5][5][0-2][0-9]|[6][5][5][3][0-5])?$/;
	  if(!regexp.exec(port)){
		  return false;
		  }
	  return true;
}

//应用ID验证
function checkAppId(appId){
	 var regexp = /^[0-9]{4}$/;
	  if(!regexp.exec(appId)){
		  return false;
		  }
	  return true;
}

//密码验证
function checkPassword(password){
	 var regexp = /^[0-9a-zA-Z]{4,20}$/;
	  if(!regexp.exec(password)){
		  return false;
		  }
	  return true;
}
//0-9数字验证,可以为空
function check0_9(num){
	 var regexp = /^[0-9]?$/;
	  if(!regexp.exec(num)){
		  return false;
		  }
	  return true;
}

//0-99数字验证,可以为空
function check0_99(num){
	 var regexp = /^[0-9]{0,2}$/;
	  if(!regexp.exec(num)){
		  return false;
		  }
	  return true;
}

//0-99数字验证,可以为空
function check0_999(num){
	 var regexp = /^[0-9]{0,3}$/;
	  if(!regexp.exec(num)){
		  return false;
		  }
	  return true;
}

//流量验证
function checkFlow(flow){
	  var regexp = /^[0-1]?[0-9]{0,8}$/;
	  if(!regexp.exec(flow)){
		  return false;
		  }
	  return true;
}

function checkFlowAll(day,month,test){
	if(!checkFlow(day)){
		$("#flowDayMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#flowDayMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	if(!checkFlow(month)){
		$("#flowMonthMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#flowMonthMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	if(test){
		if(day.length>0&&month.length>0){
			if(day-month>0){
				$("#flowMonthMsg").removeClass("tipMsg").addClass("tipErrorMsg");
				test=false;
			}else{
				$("#flowMonthMsg").removeClass("tipErrorMsg").addClass("tipMsg");
			}

		}
	}
	return test;
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


//失去焦点验证
//验证IP
function ipCheck(){
	var ip = $("#ip").val();
	if(!validateIps(ip)){
		$("#ipMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#ipMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//应用名称验证。
function appNameCheck(){
	var appName = $("#appName").val();
	if(!checkLength(appName,1,30)){
		$("#appNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#appNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//应用ID验证
function appIdCheck(){
	var appId = $("#appId").val();
	if(!checkAppId(appId)){
		$("#appIdMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#appIdMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//端口验证
function portCheck(){
	var port = $("#port").val();
	if(!checkPort(port)){
		$("#portMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#portMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//用户名验证
function userNameCheck(){
	var userName = $("#userName").val();
	if(!checkLength(userName,1,16)){
		$("#userNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#userNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//密码验证
function passwordCheck(){
	var password = $("#password").val();
	if(!checkPassword(password)){
		$("#passwordMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#passwordMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//优先级验证
function priorityCheck(){
	var priority = $("#priority").val();
	if(!check0_999(priority)){
		$("#priorityMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#priorityMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//短信签名验证
function signNameCheck(){
	var signName = $("#signName").val();
	if(!checkLength(signName,0,10)){
		$("#signNameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#signNameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//资费类别验证
function feeCheck(){
	var fee = $("#fee").val();
	if(!check0_99(fee)){
		$("#feeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#feeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//资费类型验证
function feeTypeCheck(){
	var feeType = $("#feeType").val();
	if(!check0_99(feeType)){
		$("#feeTypeMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#feeTypeMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}
//日流量验证
function flowDayCheck(){
	var flowDay = $("#flowDay").val();
	var flowMonth =$("#flowMonth").val();
	var test = true;
	if(!checkFlow(flowDay)){
		$("#flowDayMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test =false;
	}else{
		$("#flowDayMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	if(!checkFlow(flowMonth)){
		$("#flowMonthMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#flowMonthMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	if(test){
		if(flowDay.length>0&&flowMonth.length>0){
			if(flowDay-flowMonth>0){
				$("#flowMonthMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			}else{
				$("#flowMonthMsg").removeClass("tipErrorMsg").addClass("tipMsg");
			}
		}
	}
}
//月流量验证
function flowMonthCheck(){
	var flowDay = $("#flowDay").val();
	var flowMonth =$("#flowMonth").val();
	var test = true;
	if(!checkFlow(flowMonth)){
		$("#flowMonthMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}else{
		$("#flowMonthMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
	if(test){
		if(flowDay.length>0&&flowMonth.length>0){
			if(flowDay-flowMonth>0){
				$("#flowMonthMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			}else{
				$("#flowMonthMsg").removeClass("tipErrorMsg").addClass("tipMsg");
			}
		}
	}
}























