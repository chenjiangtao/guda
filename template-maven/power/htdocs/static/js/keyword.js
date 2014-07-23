$(document).ready(function(){
	if($.browser.msie) {
		 $("#srcoll").css("width","100%");
	 }
	 if($.browser.mozilla){
		 if($("#srcoll").height()>=370){
			 $("#srcoll").css("width","101.7%");
		 }	
	 }
	
	$('input').keydown(function(e){
		
		if(e.keyCode==13){
			$("#form_book").submit();
		}	
	});
	
	$("#saveApp").click(function(){
		var flag = false;
		if (strlen($("#keyword").val()) == 0 || strlen($("#keyword").val()) > 12) {
			alert("请输入正确的关键字！不能为空，最大长度12");
			return false;
		}
		
		$("#form_book").submit();
		
	});
	
	$("#savekeywordExcel").click(function(){
		var flag = false;
		if (strlen($("#excelfile").val()) == 0 ) {
			alert("文件不能为空");
			return false;
		}
		
		$("#form_excel").submit();
		
	});
	
	$("#deleteCheck").click(function(){
		var dels = new Array();
		$('input[name="getKey"]:checked').each(function(){
			 var id = $(this).val();
			 dels.push(id);
		 });
		 if(dels.length<1){
			 alert("请选择至少一项需要删除的关键字");
			 return ;
		 }
		 var d = dels.join(",");
		 var url = currenthost+"/admin/keyword/keywordDelete.ajax";
		 if(confirm("确认删除选择的关键字？")){
			 $.ajax({
				  type: "POST",
				  url: url,
				  data: { ids: d }
			}).done(function( msg ) {
				window.location.href = currenthost+"/admin/keyword/keywordlist.htm";
			});
		 }
		
	});
	
	
	  //全选或全不选
    $("#chkAll").click(function () {//当点击全选框时
        var flag = $("#chkAll").attr("checked");//判断全选按钮的状态
        if(flag=="checked"){
        	$("[id$='Item']").each(function () {//查找每一个Id以Item结尾的checkbox
                $(this).attr("checked", flag);//选中或者取消选中
            });
        }else{
        	$("[id$='Item']").each(function () {//查找每一个Id以Item结尾的checkbox
                $(this).removeAttr("checked");//选中或者取消选中
            });
        }
        
    });
    //如果全部选中勾上全选框，全部选中状态时取消了其中一个则取消全选框的选中状态
    $("[id$='Item']").each(function () {
        $(this).click(function () {
            if ($("[id$='Item']:checked").length == $("[id$='Item']").length) {
                $("#chkAll").attr("checked", "checked");
            }
            else $("#chkAll").removeAttr("checked");
        });

    });
	
});

/**
 * 显示所属应用下拉框的title
 * 
 * @param content
 */
function showTitle(content){
    var obj = document.getElementById("appinfo");
    obj.title = content.innerHTML;
}