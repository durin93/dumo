

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};


var $box = $('.one-link');
var $backupBtn = $(".backup-btn");
var $saveBtn = $('.save-btn');
var storedPos = localStorage.getItem('pos') || '[]';
var pos = JSON.parse(storedPos);
initDraggable();
$saveBtn.on('click', function(e) {
	$box.each(function(i, v) {
		pos[i] = {
			left : $(v).css('left'),
			top : $(v).css('top')
		}
	});
	localStorage.setItem('pos', JSON.stringify(pos));
});

$backupBtn.on('click', function(e) {
	$box.each(function(i, v) {
		pos[i] = {
				left : $(v).css('left auto'),
				top : $(v).css('top auto')
		}
	});
	localStorage.setItem('pos', JSON.stringify(pos));
	location.reload(true);
});

var css_test_idx = 10;

function initDraggable() {
	$box.draggable({
		containment : ".content-main"
	}).on("mousedown", function(){
		$(this).css('z-index', css_test_idx); 
        css_test_idx++;
	}).each(function(i, v) {
		$(v).css({
			left : pos[i] ? pos[i].left : 'auto',
			top : pos[i] ? pos[i].top : 'auto',
		});
	});
	$box.css("visibility","visible");
}


//북마크 추가
$(".write-link-btn").on("click" , 
		function(e) {
			e.preventDefault();


			var actionUrl = "/api/links";
			var title = "";
			var url = "https://www.google.co.kr";
			var content = "";

			console.log("북마크 추가");
			console.log("title="+title+" url="+url+" content="+content);


			$.ajax({
				type : 'post',
				url : actionUrl,
				dataType : 'json',
				data : JSON.stringify({
					title : title,
					content : content,
					url : url
				}),
				contentType : "application/json",
				error : function() {
					alert("링크추가에러");
				},
				success : function(data) {
					var linkTemplate = $("#linkTemplate").html();
					var template = linkTemplate.format(data.id, data.title,
							data.content, data.originalUrl, data.thumnailUrl, data.modifiedDate);
					var count = $(".link-count").text();
					count = Number(count)+1;
					$(".link-count").html(count);
					$(".content-main").prepend(template);
					$(".one-link").css("visibility","visible");
				},
				beforeSend : beforeSend,
				complete : function() {
					$("#div_ajax_load_image").hide();
				}
			});
		});

function toggleMemo(target, focused) {
	if(focused) {
		$(target).css("background-color","#77e2d3");
		$(target).find(".link-delete-img").css("visibility", "visible");
		$(target).find(".memo-cursor-img").css("visibility", "hidden");
	} else {
		$(target).css("background-color","#ffffea");
		$(target).find(".link-delete-img").css("visibility", "hidden");
		$(target).find(".memo-cursor-img").css("visibility", "visible");
	}
}

$(".content-main").on('click', ".link-title, .link-info, .link-textarea",function(e) {
	var thislink = $(this).parent(".one-link");
	e.stopPropagation();  
	toggleMemo('.one-link', false); 
	toggleMemo(thislink, true);
});

$(window).on('click', function() { //밖에 클릭하면 전부 클릭안한 스타일로
	toggleMemo('.one-link', false);
})


	
//삭제
$(".content-main").on("click",  ".link-delete", function(e){
	e.preventDefault();
	var deleteBtn = $(this);
	var oneLink = deleteBtn.parent();
	var url = deleteBtn.attr("href");
	var id = oneLink.find($(".link-id")).val();
	
	console.log("id : "+id);
	console.log("url : "+url);
	
	$.ajax({
		url : url,
		type : 'delete',
//		contentType: "application/json",
//		dataType : 'json',
		error : function() {
			console.log("삭제 실패");
		},
		success : function(data) {
			console.log(data);
			alert("삭제서공");
			var count = $(".link-count").text();
			count = Number(count)-1;
			$(".link-count").html(count);
			deleteBtn.closest(".one-link").remove();
		},
		beforeSend : beforeSend,
		complete : function() {
			$("#div_ajax_load_image").hide();
		}
	});
});	



//수정
$(".content-main").on("change",  ".link-textarea , .link-title, .link-info", function() {
	var onelink = $(this).closest(".one-link");
	var id = onelink.find(".link-id").val();
	var title = onelink.find(".link-title").val();
	var url = onelink.find(".link-info").val();
	var content = onelink.find(".link-textarea").val();
	var actionUrl = "/api/links/" + id;

	console.log("url : " + actionUrl);

	$.ajax({
		type : 'put',
		url : actionUrl,
		datatType : 'json',
		data : JSON.stringify({
			title : title,
			content : content,
			url : url
		}),
		contentType : "application/json",
		error : function() {
			console.log("수정실패");
		},
		success : function(data) {
			onelink.find(".link-thumnail-img").prop("src", data.thumnailUrl);
			onelink.find(".link-title").val(data.title);
			console.log(data);
		},
		beforeSend : beforeSend,
		complete : function() {
			$("#div_ajax_load_image").hide();
		}
	});
});

function beforeSend() {
	var width = 0;
	var height = 0;
	var left = 0;
	var top = 0;
	width = 50;
	height = 50;
	top = ($(window).height() - height) / 2 + $(window).scrollTop();
	left = ($(window).width() - width) / 2 + $(window).scrollLeft();
	if ($("#div_ajax_load_image").length != 0) {
		$("#div_ajax_load_image").css({
			"top" : top + "px",
			"left" : left + "px"
		});
		$("#div_ajax_load_image").show();
	} else {
		$('body')
				.append(
						'<div id="div_ajax_load_image" style="position:absolute; top:'
								+ top
								+ 'px; left:'
								+ left
								+ 'px; width:'
								+ width
								+ 'px; height:'
								+ height
								+ 'px; z-index:9999; filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="/image/content/loading.gif" style="width:100px; height:100px;"></div>');
	}
}