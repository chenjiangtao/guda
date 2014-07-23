$(document).ready(function() {
	$("#userPhone").autocomplete({
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


});
