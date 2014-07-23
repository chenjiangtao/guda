$(document).ready(function() {
//	if ($.browser.msie) {
//		$("#srcoll").css("width", "100%");
//	}
//	if ($.browser.mozilla) {
//		if ($("#srcoll").height() >= 370) {
//			$("#srcoll").css("width", "101.7%");
//		}
//	}
	
	
	$("#searchError_sbtn").click(function() {
		$("#error_form").submit();
	});
	
	$("#searchLog_sbtn").click(function() {
		if (!checkDateArea()) {
			return;
		}
		$("#form_book").submit();
	});

	$("#exportmsg_sbtn").click(function() {
		if (!checkDateArea()) {
			return;
		}
		$("#form_book").attr('action',currenthost + '/stat/exportExcel.htm');
		$("#form_book").submit();
		$("#form_book").attr("action",currenthost + '/stat/searchmsg.htm');
		$("#msgSrc").attr("autocomplete",'off');
		$("#recvId").attr("autocomplete",'off');
		$("#appName").attr("autocomplete",'off');
	});
	
	$("#export_data_msg_sbtn").click(function() {
		if (!checkDateArea()) {
			return;
		}
		$("#form_book").attr('action',currenthost + '/stat/exportDataMsgExcel.htm');
		$("#form_book").submit();
		$("#form_book").attr("action",currenthost + '/stat/searchData.htm');
		$("#msgSrc").attr("autocomplete",'off');
		$("#recvId").attr("autocomplete",'off');
		$("#appName").attr("autocomplete",'off');
	});
	
	$("#export_msgIn_sbtn").click(function() {
		if (!checkDateArea()) {
			return;
		}
		$("#form_book").attr('action',currenthost + '/stat/exportMsgInExcel.htm');
		$("#form_book").submit();
		$("#form_book").attr("action",currenthost + '/stat/searchMsgIn.htm');
		$("#msgSrc").attr("autocomplete",'off');
		$("#recvId").attr("autocomplete",'off');
		$("#appName").attr("autocomplete",'off');
	});
	
	 $("#export_gate_stat_sbtn").click(function() {
		if (!checkDateArea()) {
			return;
		}
		$("#form_book").attr('action',currenthost + '/stat/exportGateStatExcel.htm');
		$("#form_book").submit();
		$("#form_book").attr("action",currenthost + '/stat/statGate.htm');
		$("#msgSrc").attr("autocomplete",'off');
		$("#recvId").attr("autocomplete",'off');
		$("#appName").attr("autocomplete",'off');
	});
	$("#appName").autocomplete({
		source: function( request, response ) {
			$.ajax({
				url: currenthost + "/searchApps.ajax",
				dataType: "json",
				data: {"appName": $.trim(request.term)},
				success: function( data ) {
					response( $.map( data, function( item ) {
						return {
							label: item.appName + "(" + item.appId+")",
							value: item.appName
						}
					}));
				}
			});
		},
		minLength:1
		,
		open: function() {
			$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
		},
		close: function() {
			$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
		}
	});
	
	$("#msgSrc").autocomplete({
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
	
	

	$('input').keydown(function(e) {
		if (e.keyCode == 13) {
			$("#error_form").submit();
			if (!checkDateArea()) {
				return;
			}
			$("#form_book").submit();
		}
	});
});

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