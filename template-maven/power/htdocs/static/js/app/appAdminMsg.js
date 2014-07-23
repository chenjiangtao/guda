$(document).ready(function() {

	$('input').keydown(function(e) {
		if (e.keyCode == 13) {
			$("#search_form").submit();
			if (!checkDateArea()) {
				return;
			}
			$("#form_book").submit();
		}
	});

	$("#searchLog_sbtn").click(function() {
		if (!checkDateArea()) {
			return;
		}
		$("#form_book").submit();
	});

	$("#msgSrc").autocomplete({
		source : function(request, response) {
			$.ajax({
				url : currenthost + "/msg/searchUsers.ajax",
				dataType : "json",
				data : {
					"recvId" : $.trim(request.term)
				},
				success : function(data) {
					response($.map(data, function(item) {
						return {
							label : item.userName + "(" + item.phone + ")",
							value : item.phone
						}
					}));
				}
			});
		},
		minLength : 3// 输入3个字符长度以后才会按手机号进行搜索
		,
		open : function() {
			$(this).removeClass("ui-corner-all").addClass("ui-corner-top");
		},
		close : function() {
			$(this).removeClass("ui-corner-top").addClass("ui-corner-all");
		}
	});
});

function searchStatus() {
	$("#search_form").submit();
}

function checkDateArea() {
	if ($("#d4312").val() == "" || $("#d4311").val() == "") {
		alert("所选时间不能为空");
		return false;
	}
	var date1 = Date.parse($("#d4311").val().split("-").join("\/"));
	var date2 = Date.parse($("#d4312").val().split("-").join("\/"));
	var diff = (date2 - date1) / (1000 * 3600 * 24);
	if (diff > 60) {
		alert("时间段差不能超过60天！");
		return false;
	}
	return true;
}

// $(function() {
//
// $("#s_app").appSelect({
// url : currenthost + "/appAdmin/getAppLikeName.ajax",
// isAdmin : false
//	});
//});

/**
 * 显示所属应用下拉框的title
 * 
 * @param content
 */
function showTitle(content){
    var obj = document.getElementById("appinfo");
    obj.title = content.innerHTML;
}