
$(document).ready(function(){
	//setDatePick();
	
	$('input').keydown(function(e){
		if(e.keyCode==13){
			if(!checkDateArea()){		
				return;
			}
			$("#myAcceptMsgForm").submit();
		}
	});
	
	$("#searchLog_sbtn").click(function(){
		if(!checkDateArea()){
			return;
		}
		$("#myAcceptMsgForm").submit();
	});
});

function checkDateArea(){
	if ($("#d4312").val() == "" || $("#d4311").val() == "") {
		alert("所选时间不能为空");
		return false;
	}
	var date1 = Date.parse($("#d4311").val().split("-").join("\/"));
	var date2 = Date.parse($("#d4312").val().split("-").join("\/"));
	var diff = (date2-date1)/(1000*3600*24);
	if(diff>60){
		alert("时间段差不能超过60天！");
		return false;
	}
	return true;
}

function setDatePick(){
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	if(month < 10){
		month = "0"+month;
	}
	var day = date.getDate();
	if(day < 10){
		day = "0"+day;
	}
	if($("#d4312").val()==""){
		$("#d4312").val(year+"-"+month+"-"+day);
	}
	date.setDate(date.getDate()-1);
	year = date.getFullYear();
	month = date.getMonth()+1;
	if(month < 10){
		month = "0"+month;
	}
	day = date.getDate();
	if(day < 10){
		day = "0"+day;
	}
	if($("#d4311").val()==""){
		$("#d4311").val(year+"-"+month+"-"+day);
	}
}