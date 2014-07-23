
var showNum = function(){
	 var d = $(this);
	 var pos = d.offset();
	 var t = pos.top +  this.offsetHeight - 22; // 弹出框的下边位置
	 var l = pos.left  + this.offsetWidth ;  // 弹出框的右边位置
	 var num = this.value.length;
	 $("#changeNum").html(num);
	 //$("#changeNum").html(strlen(this.value));
	 $("#divShowNum").css({ "top": t, "left": l }).show();
}