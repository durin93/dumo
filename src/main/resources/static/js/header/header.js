/* $(".update-btn").on("click",function(e){
		e.preventDefault();
		var url = $(this).attr("href");
		$.ajax({
			type : 'get',
			url : url,
			dataType : 'json',
			error : function(){
				alert("수정폼에러");
			},
			success : function(data){
//				location.href="/users/update";
				var a = data.name + "님";
				$(".id").val(data.name);
				console.log(data.name);
				$("a.update-btn").html(a);
			}
		});
});*/

$(".logout-btn").on("click",function(e){
	e.preventDefault();

	var url = $(this).attr("href");
	console.log("로그아웃"+url);

	$.ajax({
		type : 'get',
		url : url,
		dataType : 'json',
		error : function(){
			alert("로그아웃에러");
		},
		success : function(data){
			console.log("로그아웃성공")
			location.href=data.url;
		}
		
	});
	
});