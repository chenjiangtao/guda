$(document).ready(function() {
	
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
	
	$('input').keydown(function(e) {
		if (e.keyCode == 13) {		
			$("#appInfoForm").submit();
		}
	});
	
});

function changeStatus(id){
	$.ajax( {
		cache : false,
		async : false,
		type : "POST",
		dateType:"json",
		url : currenthost+ "/app/status.ajax",
		data:{"id":id},
		success : function(data) {
			if(data==="1"){
				$("#"+id).html("启用");
				$("#"+"change"+id).html("禁用");
			}else if(data==="2"){
				$("#"+id).html("禁用");
				$("#"+"change"+id).html("启用");
			}else{
				alert("配置有误或者系统异常！");
			}
		}
	});
}