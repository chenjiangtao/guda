$(function() {
	$("#search-btn").click(function() {
		if($("#search-text").val() =="请输入联系人姓名或者手机号"){
		   $("#search-text").val('');
		}
			$("#contactForm").submit();
		
	});
});

function selectRecv(obj,phone){
  if(window.parent){
     if(obj.checked){
       window.parent.setRecv(phone);
     }else{
       window.parent.removeRecv(phone);
     }
  }
}