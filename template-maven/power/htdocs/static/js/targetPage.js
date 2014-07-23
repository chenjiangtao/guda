$(document).ready(function(){
		$("#targetNum").keyup(function(){
			var num = $("#targetNum").val();
			var regexp = /^[0-9]+$/;
			if(!regexp.exec(num)){
				$("#targetNum").val("");
			}
		});
		$("#targetBtn").click(function(){
			var num = $("#targetNum").val();
			if(num.length===0){
				alert("请输入页码！");
				return;
			}
			window.location.href=$("#searchUrl").val()+"?pageId="+$("#targetNum").val()+"&"+$("#param").val();
		});	
});