
var memberArray = [];

$(document).ready(function(){
	
	$("#appSubName").blur(function(){
		nameCheck();
	});
	$("#priority").blur(function(){
		priorityCheck();
	});
	$("#appSubId").blur(function(){
		idCheck();
	});
	
	$("#span_add_btn").click(function(){
		$("#nameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#idMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#priorityMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		$("#appSubName").val("");
		$("#appSubId").val("");
		$("#priority").val("");
		$("#existMessage").html("")
		$("#appSubName").focus();
		$("#mask").show();
		$("#memberContainer").show();
	});

	$("#span_close_btn").click(function(){
		$("#mask").hide();
		$("#memberContainer").hide();
	});

	$("#span_addMember_btn").click(function(){
		var test = true;
		$("#subTitle").html("添加子应用");
		var name = $.trim($("#appSubName").val());
		var subAppId = $.trim($("#appSubId").val());
		var id = $.trim($("#id").val());
		var priority= $.trim($("#priority").val());
		if(!checkId(subAppId)){
			$("#idMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			test = false;
		}else{
			$("#idMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
		if(!checkPriority(priority)){
			$("#priorityMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			test = false;
		}else{
			$("#priorityMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
		if(!checkName(name)){
			$("#nameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
			test = false;
		}else{
			$("#nameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
		}
		var appId = $("#appId").val();
		$.ajax( {
			cache : false,
			async : false,
			type : "POST",
			url :currenthost+ "/subApp/exist.ajax",
			data:{"appId":appId,"subAppId":subAppId,"id":id},
			success : function(data) {
				if("exist"===data){
					test = false;
					$("#existMessage").html("子应用ID已存在");
				}
			}
		});
		if(test){
			$("#subAppForm").submit();
			$("#appSubName").val("");
			$("#appSubId").val("");
			$("#priority").val("");
			$("#existMessage").html("")
		}

	});
});


function checkName(subAppName){
	subAppName = $.trim(subAppName);
	if(subAppName.length>16){
		return false;
	}
	return true;
}



function checkId(subAppId){
  var regexp = /^[0-9]{2}$/;
  if(!regexp.exec(subAppId)){
	  return false;
	  }
  return true;
}

function checkPriority(priority){
	  var regexp = /^[0-9]{0,3}$/;
	  if(!regexp.exec(priority)){
		  return false;
		  }

	  return true;
	}

//失去焦点验证

function nameCheck(){
	var name = $.trim($("#appSubName").val());
	if(!checkName(name)){
		$("#nameMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#nameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}

function idCheck(){
	var subAppId = $.trim($("#appSubId").val());
	if(!checkId(subAppId)){
		$("#idMsg").removeClass("tipMsg").addClass("tipErrorMsg");
	}else{
		$("#idMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}


function priorityCheck(){
	var priority= $.trim($("#priority").val());
	if(!checkPriority(priority)){
		$("#priorityMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test = false;
	}else{
		$("#priorityMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	}
}




function editSubApp(id){
	$("#subTitle").html("修改子应用");
	$("#nameMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	$("#idMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	$("#priorityMsg").removeClass("tipErrorMsg").addClass("tipMsg");
	$("#existMessage").html("")
	var subName = $("#name"+id).html();
	var subId = $("#id"+id).html();
	var subPriority = $("#priority"+id).html();
	$("#id").val(id);
	$("#appSubName").val(subName);
	$("#appSubId").val(subId);
	$("#priority").val(subPriority);
	$("#appSubName").focus();
	$("#mask").show();
	$("#memberContainer").show();
}





