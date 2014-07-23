$(document).ready(function(){
	
	$("#searchLog_sbtn").click(function(){
		if ($("#d4312").val() == "" || $("#d4311").val() == "") {
			alert("所选时间不能为空");
			return ;
		}
		var date1 = Date.parse($("#d4311").val().split("-").join("\/"));
		var date2 = Date.parse($("#d4312").val().split("-").join("\/"));
		var diff = (date2-date1)/(1000*3600*24);
		if(diff>60){
			alert("时间段差不能超过60天！");
			return ;
		}
		$("#mySendMsgForm").submit();
	});
	
	$('input').keydown(function(e){
		if(e.keyCode==13){
			if(!checkDateArea()){		
				return;
			}
			$("#mySendMsgForm").submit();
		}
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