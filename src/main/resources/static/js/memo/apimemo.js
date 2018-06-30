 $("#memoIcon").addClass("select");
 $("#linkIcon").removeClass("select");

$("#write-btn").removeClass();
$("#write-btn").addClass("write-memo-btn");
$("#write-btn").prop("href", "/api/memos");
$("#write-btn").html("새 메모쓰기");




loadMemoContentNav()
loadMemoLeftNav();
loadMemoDefaultList();


function loadMemoContentNav(){
	var source = $("#memo-content-nav").html();
	var template = Handlebars.compile(source);
	$.ajax({
		type : 'get',
		url : '/api/memos/size',
		dataType : 'json',
		error : function(){
			alert("loadMemoContentNav에러");
		},
		success : function(data){
			console.log(data);
			$(".info").append(template({memoCount : data}));
		}
	});
}

function loadMemoLeftNav(){
	var source = $("#memo-left-nav").html();
	var template = Handlebars.compile(source);
	$.ajax({
		type : 'get',
		url : '/api/labels',
		dataType : 'json',
		error : function(){
			alert("에러");
		},
		success : function(data){
			console.log(data);
			$(".main-category").append(template(data));
		}
	});
}	

function loadMemoDefaultList(){
	var source = $("#apimemo-template").html();
	var template = Handlebars.compile(source);
	$.ajax({
		type : 'get',
		url : '/api/memos',
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
}	

//페이징
$(document).on("click" , ".pagination ul li a", function(e){
	e.preventDefault();
	var source = $("#apimemo-template").html();
	var template = Handlebars.compile(source);
	var url = $(this).attr("href");
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
