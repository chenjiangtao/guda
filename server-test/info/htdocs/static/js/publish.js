$(function() {

  $('#file_upload').uploadify({
				'formData'     : {
					'timestamp' : new Date()
				},
				'debug' : false,
				 'auto': true,
				 'queueID':'queue',
				 fileSizeLimit : '2MB',  
				 'fileTypeDesc':'支持的格式：jpg,jpeg,gif,png',
                 'fileTypeExts': '*.jpg;*.jpeg;*.gif;*.png', 
                  'queueSizeLimit' : 10,      
				'swf': currenthost+'/static/uploadify/uploadify.swf',
				'uploader' : currenthost+'/prod/upload.htm;jsessionid='+_jid,
				'onUploadSuccess':function(file, data, response){
			            if(data){ 
			                var dataObj=eval("("+data+")");
	
			                $.each(dataObj,function(i, item){

	                            var temp = $("<img/>");
	                            temp.attr("src",currenthost+item.path);
	                            
				                
				                
				                var _span = $("<span></span>");
				                _span.attr("class","span_box");
				                _span.append(temp);
				                
				                var _a = $("<a></a>");
				                _a.attr("class","a_close");
				                 _a.attr("id",item.imgId);
				                _a.attr("href","javascript:void(0);");
				                
				                var _div = $("<div></div>");
				                _div.attr("class","img_box");
				                _div.attr("id",item.imgId+"-div");
				                _div.append(_a);
				                _div.append(_span);
				                
				                $("#image").append(_div);
				                
				          
							          $("#"+item.imgId).bind("click",function(event){
							             event.preventDefault();  
								         $.ajax({
											 type:'post',
											 url:currenthost+'/delImg.json',
											 dataType:'json',
											 data:{'id':$(this).attr("id")},
											 success: function (data) {
											    $('#'+item.imgId+'-div').remove();
											   
											  }
											});
								    
							    });
				                
				            });
			            } 
			        } 
			});

    


	$("#classify-id").bind("change",function(){
		
			$("#sub-classify-id").empty();
			$.ajax({
			 type:'post',
			 url:currenthost+'/loadSubClassify.json',
			 dataType:'json',
			 data:{'classifyId':$("#classify-id").val()},
			 success: function (data) {
			   $("#queue").remove();
			   $.each(data,function(i, item){
	                
	                $("#sub-classify-id").append($("<option></option>").attr("value",item.id).html(item.name));
	            });
			 }
			});
			
	         
		
	});
	
	$("#province-id").bind("change",function(){
		
			$("#city-id").empty();
			$.ajax({
			 type:'post',
			 url:currenthost+'/loadCity.json',
			 dataType:'json',
			 data:{'provinceId':$("#province-id").val()},
			 success: function (data) {
			   $.each(data,function(i, item){
	                
	                $("#city-id").append($("<option></option>").attr("value",item.id).html(item.name));
	            });
			 }
			});
			
	         
		
	});
});

function uploadFile(){
   var temp = $("<div></div>");
   temp.attr("id","queue");
   $("#image").append(temp);
}