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
	
	$("#operatorName").click(function(){
		checkPerson("operatorId", "operatorName");
	});
	
	$("#msgdestName").click(function(){
		checkPerson("msgDest", "msgdestName");
	});
	
	$("#msgsrcName").click(function(){
		checkPerson("msgSrc", "msgsrcName");
	});
	
	$("#checkName").click(function(){
		checkPerson("checkId", "checkName");
	});
	
	$("#orgName").click(function(){
		checkOrg("orgId", "orgName");
	});
	
});

var param = []
/**
 * 传入人员text的2个id 一个显示姓名的 一个存uid的
 * @param id
 * @param name
 */
function checkPerson(id,name){
	var arg = new Object();
	arg.type = "person";
	arg.param = param;
	arg.multiple = false;
	arg.provinceId = "1";
	var returnVal = window.showModalDialog(currenthost+"/selector/selectorView.htm",arg,"dialogWidth:760px;dialogHeight:590px;scroll:no;status:no;resizable:no;") 

	if( typeof(returnVal) == "object" && returnVal.length > 0){
		var textValue = "";
		param = [];
		for(var i=0;i<returnVal.length;i++){
			param[i] = {"id":returnVal[i].id,"name":returnVal[i].name,"checkid":returnVal[i].checkid};
			//alert(returnVal[i].id+" : "+returnVal[i].name+" : "+returnVal[i].checkid)
			$("#"+name+"").val(returnVal[i].name);
			$("#"+id+"").val(returnVal[i].id);
		}
	}
	else{
		$("#"+name+"").val("");
		$("#"+id+"").val("");
	}
}

function checkOrg(id,name){
	var arg = new Object();
	arg.type = "org";
	arg.param = param;
	arg.multiple = false;
	arg.provinceId = "1";
	var returnVal = window.showModalDialog(currenthost+"/selector/selectorView.htm",arg,"dialogWidth:760px;dialogHeight:590px;scroll:no;status:no;resizable:no;") 

	if( typeof(returnVal) == "object" && returnVal.length > 0){
		var textValue = "";
		param = [];
		for(var i=0;i<returnVal.length;i++){
			param[i] = {"id":returnVal[i].id,"name":returnVal[i].name,"checkid":returnVal[i].checkid};
			//alert(returnVal[i].id+" : "+returnVal[i].name+" : "+returnVal[i].checkid)
			$("#"+name+"").val(returnVal[i].name);
			$("#"+id+"").val(returnVal[i].id);
		}
	}
	else{
		$("#"+name+"").val("");
		$("#"+id+"").val("");
	}
}

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
