

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};



$(document).on("click", ".link-memo", function(e){
	e.preventDefault();
	var url = $(this).attr("href");
	$("#write-btn").prop("href", url);

	var source = $("#apimemo-template").html();
	var template = Handlebars.compile(source);
	$.ajax({
		type : 'get',
		url : url,
		dataType : 'json',
		error : function(){
			alert("에러");
		},
		success : function(data){
			$(".content-main .one-memo").remove();
			$(".content-main").append(template(data));
			$(".pagination ul").html(data.pagination);
			$(".one-memo").css("visibility","visible"); 
			initDraggable();
		}
	});
});


//라벨 추가
$(document).on("click", ".make-label", function(e){
	e.preventDefault();

	var title = "새라벨";
	var url = "/api/labels";
	
	$.ajax({
		type : 'post',
		url : url,
		contentType : "application/json",
		dataType : 'json',
		data : JSON.stringify({
			title : title
		}),
		success : function(data){
			var labelTemplate = $("#labelTemplate").html();
			var template = labelTemplate.format(data.id, data.title);
			$(".label-list-ul").append(template);
			alert("라벨등록성공");
		},
		beforeSend : beforeSend,
		complete : function() {
			$("#div_ajax_load_image").hide();
		}
	});
});

$(window).on('click', function() { //밖에 클릭하면 전부 클릭안한 스타일로
	toggleLabel('.label-title', false);
})

$(document).on("click",  ".label-title", function(e){
	e.stopPropagation();  
	toggleLabel('.label-title', false); 
	toggleLabel(this, true);
});


function toggleLabel(target, focused) {
	if(focused) {
		$(target).siblings(".label-delete").css("visibility", "visible");
	} else {
		$(target).siblings(".label-delete").css("visibility", "hidden");
	}
}

//라벨수정
$(document).on("change keyup",  ".label-title" ,function(e) {
	e.preventDefault();
	var thisLabel = $(this).closest(".one-label");
	var url = thisLabel.find(".label-action").val();
	var title = thisLabel.find(".label-title").val();
	console.log("url : " + url);

	$.ajax({
		type : 'put',
		url : url,
		datatType : 'json',
		data : JSON.stringify({
			title : title,
		}),
		contentType : "application/json",
		error : function() {
			console.log("수정실패");
		},
		success : function(data) {
			console.log(data);
//			alert("성공");
		},
		beforeSend : beforeSend,
		complete : function() {
			$("#div_ajax_load_image").hide();
		}
	});
});


//라벨삭제
$(document).on("click", ".label-delete", function(e){
	e.preventDefault();
	var deleteBtn = $(this);
	var oneLabel = deleteBtn.parent();
	var url = deleteBtn.attr("href");
	console.log("url : "+url);
	
	$.ajax({
		url : url,
		type : 'delete',
//		contentType: "application/json",
		dataType : 'json',
		error : function() {
			console.log("삭제 실패");
		},
		success : function(data) {
			if(data.errorPart=="id"){
				alert(data.errorMessage);
			}
			else{
			alert("삭제서공");
			deleteBtn.closest(".one-label").remove();
			}
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