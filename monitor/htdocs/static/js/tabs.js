var centerTabs;

//初始化选项卡
$(function() {
    centerTabs = $('#centerTabs').tabs({
        border: false,
        fit:true
    });
    
    centerTabs.tabs('add', {
            title: '服务器信息',
            //也可以使用content方式进行载入,建议使用这种方式,href有时会出现样式不统一的现象
      //      content: '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>',
          href: currenthost+'/info.htm',
            closable: false
        });
      centerTabs.tabs('add', {
            title: '告警',
            //也可以使用content方式进行载入,建议使用这种方式,href有时会出现样式不统一的现象
      //      content: '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>',
          href: currenthost+'/set.htm',
            closable: false
        });
});



//更新当前选中的选项卡
function refreshTabFun() {
	centerTabs.tabs('getSelected').panel('refresh');
};

//关闭当前选中的选项卡
function closeTabFun() {
	var tab = centerTabs.tabs('getSelected');
	var index = centerTabs.tabs('getTabIndex', tab);
	centerTabs.tabs('close', index);
};



