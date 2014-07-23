/**
* @author louguodong
* 应用选择 jquery插件
**/

$(function(){
	$.fn.appSelect = function(options) {
		var defaults = {		
			url : "/appAdmin/getAppLikeName.ajax",
			isAdmin : true
		};
		
		var options = $.extend(defaults,options);
		var $text = $(this);
		var $textID = $("#s_appId");
		
		var showbox = $.fn.appSelect.showbox({
			text:$text,
			textId:$textID
		});
		
		$text.keyup(
				function(e) {
					if (e.keyCode == 13) {// enter 键除外
						showbox.getenter();
					} else if (e.keyCode == 38) {
						showbox.prev();
					} else if (e.keyCode == 40) {
						showbox.next();
					} else {
						var appname = $(this).val();
						if (appname != "") {
							$.ajax({
										cache : false,
										async : false,
										type : "POST",
										dateType : "json",
										url : options.url,
										data : {
											"q" : appname,
											"isadmin" : options.isAdmin
										},
										success : function(data) {
											data = eval(data);
											var html = '';
											if(data == ""){//如果模糊匹配查询不到应用
												$("#s_appId").val("");//把appId置为空，以避免影响下一次查询结果
											}
											for ( var i = 0; i < data.length; i++) {
												html += "<li data='"
														+ data[i].appId + "'>"
														+ data[i].appName
														+ "</li>";
											}
											showbox.display(html);
											
										}
									});
						} else {
							showbox.hideshow(null);
						}
					}

				});
		
		
		
		
	};
	
	$.fn.appSelect.showbox = function(options){
		var defaults = {
				text:null,
				textId:null
			};
		var options = $.extend(defaults,options);
		var $te = $(options.text);
		var $teId = $(options.textId);
		var SELECTCLASS = "app_show_over";
		var $showBox = $("#app_show");
		var $active = 0;
		var $resultUL = $("#app_result");
		var $listLI ;
		
		function fillList(html){
			$active = 0;
			$resultUL.html(html);
			$listLI = $resultUL.find("li");
			$listLI.die();//移除live
			$listLI.each(
							function(n) {
								if (n == 0) {
									$(this)
											.addClass(SELECTCLASS);
								}
							});
			$listLI.live({
				mouseenter : function() {
					var li = $(this);
					$active = li.index();
					li.parent().find("li").removeClass(SELECTCLASS);
					li.addClass(SELECTCLASS);
				},
				click : function() {
					hideBox($(this));
				}
			});
			$showBox.show();
		}
		
		
		
		function hideBox(obj){
			if(obj!=null){
				obj = $(obj);
				var name = "";
				var id = "";
				name = obj.html();
				id = obj.attr("data");
				$te.val(name);
				$teId.val(id);
				$resultUL.html("");
				$showBox.hide();
			}else{
				$te.val("");
				$teId.val("");
				$resultUL.html("");
				$showBox.hide();
			}
			$active = 0;
		}
		/**
		 * 移动的位置
		 */
		function movePosition(step) {
			$active += step;
			if ($active < 0) {
				$active = $listLI.size() - 1;
			} else if ($active >= $listLI.size()) {
				$active = 0;
			}
		}
		/**
		 * 移动步数
		 */
		function moveSelect(step) {
			$listLI.slice($active, $active + 1).removeClass(SELECTCLASS);
			movePosition(step);
	        var activeItem = $listLI.slice($active, $active + 1).addClass(SELECTCLASS);
		}
		
		function enterkey(){
			$(".app_show_over").each(function(n) {
				if (n == 0) {
					hideBox($(this));
				}
			});
		}
		
		
		return {
			display: function(html){
				fillList(html);
			},
			next: function() {
				moveSelect(1);
			},
			prev: function() {
				moveSelect(-1);
			},
			hideshow: function(obj){
				hideBox(obj);
			},
			getenter:function(){
				enterkey();
			}
		}
	};
	
});