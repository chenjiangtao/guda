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
	
});