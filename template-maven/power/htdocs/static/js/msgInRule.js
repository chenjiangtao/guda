$(document).ready(
		function() {
			$("#appInfo").change(
					function() {
						$("#appInfoError").hide();
						var appId = $("#appInfo").val();
						if (appId == '') {
							$("#appInfoCheck").show();
						} else {
							$("#appInfoCheck").hide();
						}
						$("#subAppInfo option").remove();// 清除POST后回写的数据
						$("#subAppInfo").append(
								"<option value=''> 请选择 </option>");// 重新添加首节点
						$.ajax({
							cache : false,
							async : false,
							type : "POST",
							dateType : "json",
							url : currenthost + "/searchSubApps.ajax",
							data : {
								"appId" : appId
							},
							success : function(data) {
								if (data == "") {
									$("#subAppInfo option").remove();// 使为空的数据快速回显
									$("#subAppInfo").append(
											"<option value=''> 请选择 </option>");
								} else {
									for (i = 0; i < data.length; i++) {
										$("#subAppInfo").append(
												"<option value="
														+ data[i].appSubId
														+ ">"
														+ data[i].appSubName +" (" +data[i].appSubId +")"
														+ "</option>");
									}
								}
							}
						});
					});

			$('input').keydown(function(e) {
				if (e.keyCode == 13) {
					save();
				}
			});
		});

/**
 * 当提交表单时触发的事件
 */
function save() {
	var test = true;
	var word = $.trim($("#word").val());
	if (!checkWordAction(word)) {
		test = false;
	}
	var appId = $("#appInfo").val();
	if (!checkAppIdAction(appId)) {
		test = false;
	}
	if (test) {
		$("#msgIn_rule_form_id").submit();
	}
}

/**
 * 当提交表单时验证应用ID
 * 
 * @param appId
 * @returns {Boolean}
 */
function checkAppIdAction(appId){
	$("#appInfoError").hide();
	var appId = $("#appInfo").val();
	if (appId == '') {
		$("#appInfoCheck").show();
		return false;
	}
	$("#appInfoCheck").hide();
	return true;
}

/**
 * 当提交表单时验证关键字内容
 * 
 * @param word
 * @returns {Boolean}
 */
function checkWordAction(word) {
	$("#wordErrorTips").hide();
	$("#wordErrorMsg").hide();
	if (word == '') {
		$("#wordMaxLengthTips").hide();
		$("#wordNotEmptyTips").show();
		return false;
	}
	if (strlen(word) > 60) {
		$("#wordMaxLengthTips").show();
		$("#wordNotEmptyTips").hide();
		return false;
	}
	$("#wordMaxLengthTips").hide();
	$("#wordNotEmptyTips").hide();
	return true;
}

/**
 * 当鼠标移开时验证关键字内容
 */
function checkWord(){
	var word = $.trim($("#word").val());
	$("#wordErrorTips").hide();
	$("#wordErrorMsg").hide();
	if (word == '') {
		$("#wordMaxLengthTips").hide();
		$("#wordNotEmptyTips").show();
	}
	if (strlen(word) > 60) {
		$("#wordMaxLengthTips").show();
		$("#wordNotEmptyTips").hide();
	}	
	if(word !='' && strlen(word) <= 60){
		$("#wordMaxLengthTips").hide();
		$("#wordNotEmptyTips").hide();
	}
}

/**
 * 显示所属应用下拉框的title
 * 
 * @param content
 */
function showTitle(content){
    var obj = document.getElementById("appInfo");
    obj.title = content.innerHTML;
}

/**
 * 显示所属子应用下拉框的title
 * 
 * @param content
 */
function showSubTitle(content){
    var obj = document.getElementById("subAppInfo");
    obj.title = content.innerHTML;
}