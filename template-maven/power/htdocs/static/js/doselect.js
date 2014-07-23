var selectUrl = currenthost+"/selector/selectorView.htm";
function selectPerson(f_uid, f_name, isMulti){
	var sz_id = $("#"+f_uid).val();
	var sz_name = $("#"+f_name).val();

	var arg = new Object();
	var i = 0;
	if (sz_id.length > 0) {
		var ids = sz_id.split(",");
		var names = sz_name.split(",");
		arg.param = [];
		for (i = 0; i < ids.length; i += 1) {
			var id = ids[i];
			var name = names[i];
			arg.param.push({"id":id, "name":name});
		}
	}

	arg.type = "person";
	if(isMulti!= undefined){
		arg.multiple = isMulti;
	}else{
		arg.multiple = false;
	}


	var returnVal = window.showModalDialog(selectUrl, arg, "dialogWidth:760px;dialogHeight:480px;scroll:no;status:no;resizable:no;");
	if (typeof(returnVal) == "object") {
		if (returnVal.length > 0) {
			sz_id = "";
			sz_name = "";
			for (i = 0; i < returnVal.length; i += 1) {
				var obj = returnVal[i];
				sz_id += "," + obj.id;
				sz_name += "," + obj.name;
			}
			sz_id = sz_id.substring(1);
			sz_name = sz_name.substring(1);
			$("#"+f_uid).val(sz_id);
			$("#"+f_name).val(sz_name);
		} else {
			$("#"+f_uid).val("");
			$("#"+f_name).val("");
		}
	}
}