$(document).ready(function(){

	setMinHeight();
	setMinWidth();

	$(window).resize(function(){
		setMinHeight();
		setMinWidth();
	});

	function setMinHeight(){
		var headerHeight = 106;
		var footHeight = 50;
		var bdPadding = 10;
		var randHeight = 5;
		var selfPadding = 15;
		var validHeight = document.documentElement.clientHeight;
		var height = validHeight - headerHeight - footHeight - 2*bdPadding - 2*randHeight - 2*selfPadding;

		var browser =  $.browser;
		//加不上!important
		if(browser.version == "6.0"){
			$("div .pRight_c").css({"height":height+"px"});
		}
		$("div .pRight_c").css({"min-height":height+"px"});

	}

});

function setMinWidth(){
	var width = document.documentElement.clientWidth;
	if(width>960){
		$("body").css({"width":width+"px"});
	}else{
		$("body").css({"width":960+"px"});
	}
}

function loginOut(){
	 $.cookie("remember_box", "false", { expires: -1 ,path:"/"});
	 $.cookie("userName", '', { expires: -1,path:"/" });
     $.cookie("passWord", '', { expires: -1,path:"/" });
	window.location.href = currenthost+"/user/loginOut.htm";	
}