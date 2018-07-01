$(".logout-btn").on("click",function(e){
	e.preventDefault();
	
	var url = $(this).attr("href");
	
	$.ajax({
		type : 'get',
		url : url,
		dataType : 'json',
		error : function(){
			alert("로그아웃에러");
		},
		success : function(data){
			location.href=data.url;
		}
		
	});
	
});