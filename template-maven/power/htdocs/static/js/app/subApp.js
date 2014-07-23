$(document).ready(function(){
	if($.browser.msie) {
		 $("#srcoll").css("width","100%");
	 }
	 if($.browser.mozilla){
		 if( $("#srcoll").height()>=370){
			 $("#srcoll").css("width","101.7%");
		 }
	 }
	
	$("#appcategory").CascadingDropDown("#appinfo", currenthost+ '/appAdmin/getAppSub.ajax', {
		postData: function () {
			    return { appId: $('#appinfo').val()};
		} 
	});	
	
	$("#exportmsg_sbtn").click(function(){
		var url = $(this).attr("url");
		var oldurl = $("#form_book").attr("action");
		$("#form_book").attr({"action":url});
		$("#form_book").submit();
		$("#form_book").attr({"action":oldurl});
	});
});