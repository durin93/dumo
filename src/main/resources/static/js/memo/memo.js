

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};


var $box = $('.one-memo');
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


//글 추가
$(".write-memo-btn").on(
		"click",
		function() {
			var url = "/api/memo";
			var title = "제목";
			var content = "";

			$.ajax({
				type : 'post',
				url : url,
				dataType : 'json',
				data : JSON.stringify({
					title : title,
					content : content
				}),
				contentType : "application/json",
				error : function() {
					alert("메모추가에러");
				},
				success : function(data) {
					var memoTemplate = $("#memoTemplate").html();
					var template = memoTemplate.format(data.id, data.title,
							data.content, data.modifiedDate);
					var count = $(".memo-count").text();
					count = Number(count)+1;
					$(".memo-count").html(count);
					$(".content-main").prepend(template);
					$(".one-memo").css("visibility","visible");
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
		$(target).find(".memo-delete").css("visibility", "visible");
		$(target).find(".memo-cursor-img").css("visibility", "hidden");
	} else {
		$(target).css("background-color","#ffffea");
		$(target).find(".memo-delete").css("visibility", "hidden");
		$(target).find(".memo-cursor-img").css("visibility", "visible");
	}
}

//토글
$(".content-main").on("click", ".memo-title , .memo-textarea", function(e) {
	var thismemo = $(this).parent(".one-memo");
	e.stopPropagation();  
	toggleMemo('.one-memo', false); 
	toggleMemo(thismemo, true);
});

$(window).on('click', function() { //밖에 클릭하면 전부 클릭안한 스타일로
	toggleMemo('.one-memo', false);
})


	
//삭제
$(".content-main").on("click", ".memo-delete", function(e){
	e.preventDefault();
	var deleteBtn = $(this);
	var oneMemo = deleteBtn.parent();
	var url = deleteBtn.attr("href");
	var id = oneMemo.find($(".memo-id")).val();
	
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
			var count = $(".memo-count").text();
			count = Number(count)-1;
			$(".memo-count").html(count);
			deleteBtn.closest(".one-memo").remove();
		},
		beforeSend : beforeSend,
		complete : function() {
			$("#div_ajax_load_image").hide();
		}
	});
});	



//수정
$(".content-main").on("change keydown", ".memo-textarea , .memo-title",function() {
	var onememo = $(this).closest(".one-memo");
	var id = onememo.find(".memo-id").val();
	var title = onememo.find(".memo-title").val();
	var content = onememo.find(".memo-textarea").val();
	var url = "/api/memo/" + id;

	console.log("url : " + url);

	$.ajax({
		type : 'put',
		url : url,
		datatType : 'json',
		data : JSON.stringify({
			title : title,
			content : content
		}),
		contentType : "application/json",
		error : function() {
			console.log("수정실패");
		},
		success : function(data) {
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