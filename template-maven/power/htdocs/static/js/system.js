$(function(){
	timeCount()
});
function timeCount(){
	  var count = 5;
	  countdown = setInterval(function(){
	    $("#timeCount").html(count + "秒");
	    if (count == 0) {
	     clearInterval(countdown);
	     $("#system").click(function(){
//	    	 javascript:goUrl(currenthost+"/admin/system/system.htm");
	    	 window.location.href=currenthost+"/admin/system/system.htm";
	     });
	     timedMsg()
	     $("#timeCount").html("");
	    }
	    count--;
	  }, 1000);
}



function timedMsg()
{
$("#system").addClass("green")
}