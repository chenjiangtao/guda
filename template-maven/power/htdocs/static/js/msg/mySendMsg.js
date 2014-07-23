

$(document).ready(function(){

	//setDatePick();
	
	/*$("#recvName").dblclick(function(){
		$("#recvId").val("");
		$("#recvName").val("");
		selectPerson("recvId","recvName",false);
	});*/
	$("#recvName").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: currenthost + "/msg/searchUsers.ajax",
					dataType: "json",
					data: {"recvId": $.trim(request.term)},
					success: function( data ) {
						response( $.map( data, function( item ) {
							return {
								label: item.userName + "(" + item.phone+")",
								value: item.phone
							}
						}));
					}
				});
			},
			minLength:3//输入3个字符长度以后才会按手机号进行搜索
			,
			open: function() {
				$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
			},
			close: function() {
				$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
			}
		});
	$('input').keydown(function(e){
		if(e.keyCode==13){
			if(!checkDateArea()){
				return;
			}
			if(!checkAcceptor()){
				alert("接收人：双击选择人员或输入手机号");
				return;
			}
			var recvName = $.trim($("#recvName").val());
			if(recvName){
				if(isMobile(recvName)){
					$("#recvId").val(recvName);
				}else{
					var recvId = $("#recvId").val();
					if(recvId && recvId.indexOf("_")){
						$("#recvId").val(recvId.split("_")[0]);
					}else{
						$("#recvId").val(recvName);
					}
				}
			}else{
				$("#recvId").val("");
			}
			$("#mySendMsgForm").submit();
		}	
	});
	
	$("#searchLog_sbtn").click(function(){
		if(!checkDateArea()){
			return;
		}
		if(!checkAcceptor()){
			alert("手机号码不正确");
			return;
		}
		var recvName = $.trim($("#recvName").val());
		if(recvName){
			if(isMobile(recvName)){
				$("#recvId").val(recvName);
			}else{
				var recvId = $("#recvId").val();
				if(recvId && recvId.indexOf("_")){
					$("#recvId").val(recvId.split("_")[0]);
				}else{
					$("#recvId").val(recvName);
				}
			}
		}else{
			$("#recvId").val("");
		}
		$("#mySendMsgForm").submit();
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
	if(diff>30){
		alert("时间段差不能超过30天！");
		return false;
	}
	return true;
}

function checkAcceptor(){
	var recvName = $.trim($("#recvName").val());
	if(recvName && !isMobile(recvName)){
		/*if(!isMobile(recvName) && (!$("#recvId").val() || isMobile($("#recvId").val()))){
			return false;
		}*/
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