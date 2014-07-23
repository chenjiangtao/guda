
function checkFlow(flow){
	  var regexp = /^[0-1]?[0-9]{0,8}$/;
	  if(!regexp.exec(flow)){
		  return false;
		  }
	  return true;
}

function check(){
	var day = $("#dayFlow").val();
	var month = $("#monthFlow").val();
	var test = true;
	if(!checkFlow(day)){
		$("#dayMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}
	if(!checkFlow(month)){
		$("#monthMsg").removeClass("tipMsg").addClass("tipErrorMsg");
		test=false;
	}
	if(test){
		if(day.length>0&&month.length>0){
			if(day-month>0){
				$("#monthMsg").removeClass("tipMsg").addClass("tipErrorMsg");
				test=false;
			}
		}
	}
	if(test){
		$("#form_book").submit();
	}
}
