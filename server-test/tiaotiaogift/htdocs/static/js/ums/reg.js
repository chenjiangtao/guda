
$(function() {
	$("#reg_btn").click(function() {
		
			$("#regForm").submit();
		
	});

	$("#reset_btn").click(function() {
			
			$("#regForm")[0].reset();
		
	});
	
});