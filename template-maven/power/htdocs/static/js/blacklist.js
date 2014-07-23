
var defaultTip = "双击选择人员";

$(document).ready(function(){
	 if($.browser.msie) {
		 $("#srcoll").css("width","100%");
	 }
	 if($.browser.mozilla){
		 $("#srcoll").css("width","101.8%");
	 }
	
	$('input').keydown(function(e){
		if(e.keyCode==13){
			$("#form_book").submit();
		}	
	});
	$("#userName").val(defaultTip);
	$("#userName").mouseout(function(){
		var userName = $.trim($(this).val());
		if(!userName){
			$("#userName").val(defaultTip);
			$("#userName").css({"color":"#00764b"});
		}else{
			$("#userName").css({"color":"#000000"});
		}
		$("#tip1").hide();
	});
	
	$("#userName").mouseover(function(){
		var userName = $.trim($(this).val());
		var phoneNum = $.trim($("#phoneNum").val());
		if(userName == defaultTip){
			$("#userName").val("");
		}
		if(phoneNum){
			$("#tip1").show();
			$("#userName").val("您已经输入了手机号!");
		}
		$("#userName").css({"color":"#000000"});
	});
	
	
	$("#phoneNum").mouseover(function(){		
		var userName = $.trim($("#userName").val());
		if(userName!=defaultTip&&userName!="您已经输入了手机号!"&&userName!=null){		
			$("#tip2").show();
			$("#phoneNum").val("");			
		}
	});
	$("#phoneNum").mouseout(function(){		
		$("#tip2").hide();
	});
	
	
	
	
	$("#saveBlacklist").click(function(){	
		var phoneNum = $.trim($("#phoneNum").val()); 
		var regexp =/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/;
		if (strlen($("#userId").val()) == 0 && strlen($("#phoneNum").val()) == 0) {
			alert("请选择要新增的黑名单用户或手机号！");
			return false;
		}
		
		if(strlen($("#phoneNum").val())!=0 && !regexp.test(phoneNum)){
			alert("请输入正确的手机号!");
			$("#phoneNum").val("");
			return false;
		}	
		$("#form_book").submit();

	});
	


});

/**
 * 显示所屏蔽应用下拉框的title
 * 
 * @param content
 */
function showTitle(content){
    var obj = document.getElementById("appinfo");
    obj.title = content.innerHTML;
}