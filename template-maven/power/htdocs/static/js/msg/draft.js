

$(document).ready(function(){
	$('input').keydown(function(e){
		if(e.keyCode==13){			
			$("#myDraftForm").submit();
		}	
	});
});

function deleteDraft(draftId,curpage){
	if(confirm("确定要删除该草稿吗？")){
		window.location.href = currenthost + "/msg/msgDraftDel.htm?draftId="+draftId+"&pageId="+curpage;
	}
}