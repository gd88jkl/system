var contextPath;

//$(document).ready(function(){
//	$.ajax({
//		type : 'POST',
//		url : contextPath + "/auth/menuTree",
//		dataType : 'json',
//		cache : false,
//		success : function(data) {
////			alert(JSON.stringify(data));
//			$("#menu").sidebarMenu(data);
////			menu();
//		}
//	});
//	
//	$("#menu").sidebarMenu({
//		data : [{id : '1',text : '系统设置',icon : 'fa-laptop',url : '/index3'},
//		        {id : '2',	text : '基础数据',	icon : 'fa-book',url : '',
//					menus : [{id : '21',text : '基础特征',icon : '',url : '/index4'}, 
//				         {id : '22',text : '特征管理',icon : '',url : '/index3'} 
//						]},
//				{id : '2',	text : '基础数据',	icon : 'fa-book',url : '',
//					menus : [ {id : '21',text : '基础特征',icon : '',url : '/index4'}, 
//					          {id : '22',text : '特征管理',icon : '',url : '/index3'}]}
//				]
//	});
//});

(function($) {
	$.fn.sidebarMenu = function(options) {
		options = $.extend({}, $.fn.sidebarMenu.defaults, options || {});
		var target = $(this);
		if (options.data) {
			init(target, options.data);
		} else {
			if (!options.url)
				return;
			$.getJSON(options.url, options.param, function(data) {
				init(target, data);
			});
		}
		var url = window.location.pathname;
		function init(target, data) {
			$.each(data, function(i, item) {
				var li = $('<li></li>');
				var a = $('<a></a>');
				if(item.spriteCssClass != null && item.spriteCssClass != '' && item.spriteCssClass != undefined) {
					var spriteCssClass = $('<i></i>');
					spriteCssClass.addClass('fa');
					spriteCssClass.addClass(item.spriteCssClass);
					a.append(spriteCssClass);
				}
				if(target.selector == "#menu") {
					var text = $('<span></span>');
					text.text(item.text);
				} else {
					var text = item.text;
				}
				if (item.menus && item.menus.length > 0) {
					li.addClass('menu-list');
					a.append(text);
					a.attr('href', '');
					li.append(a);
					var menus = $('<ul></ul>');
					menus.addClass('sub-menu-list');
					init(menus, item.menus);
					li.append(menus);
				} else {
					var href = 'javascript:addTabs({id:\'' + item.id
							+ '\',title: \'' + item.text
							+ '\',close: true,url: \'' + item.url + '\'});';
					a.attr('href', href);
					a.append(text);
					li.addClass('sub-menu-item');
					li.append(a);
				}
				target.append(li);
			});
		}
	}

	$.fn.sidebarMenu.defaults = {
		url : null,
		param : null,
		data : null
	};
})(jQuery);

$("#menu").on("click",".sub-menu-item",function(e){
	$("ul#menu .active").removeClass("active");
	$("ul#menu .nav-active").removeClass("nav-active");
	var parentMenu = $(e.currentTarget).parent().parent();
	if("LI" == parentMenu.get(0).tagName) {
		parentMenu.addClass("nav-active");
	} else {
		$("ul#menu .sub-menu-list").attr("style","display: none;");
	}
	$(e.currentTarget).addClass("active");
});

var addTabs = function(url) {
	$.ajax({
		type : 'POST',
		url : contextPath + url,
		dataType : 'html',
		cache : false,
		success : function(data) {
			$("div .page-body").html(data);
		}
	});
};

