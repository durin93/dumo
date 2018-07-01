$(".join-btn").on("click", function(e){
	e.preventDefault();
	
	var url = $(this).attr("href");
	
	$.ajax({
		type : 'get',
		url : url ,
		dataType : 'json',
		error : function(){
			alert("로그인폼에러");
		},
		success : function(data){
			location.href = data.url;
		}
	});
});

$(".login-form input[type=submit]").on("click", function(e) {
	e.preventDefault();

	var userId = $(".login-form input[type=text]").val();
	var password = $(".login-form input[type=password]").val();
	var url = $(".login-form").attr("action");
	console.log("url : " + url);
	console.log("userId : " + userId);
	console.log("password : " + password);
	console.log("로그인 클릭");

	$.ajax({
		type : 'post',
		url : url,
		contentType : "application/json",
		data : JSON.stringify({
			userId : userId,
			password : password
		}),
		dataType : 'json',
		error : function() {
			alert("error");
		},
		success : function(data) {
			if (!data.url) {
				if(data.errorPart=="id"){
					$(".errorMessage").html("");
					$(".errorId").html(data.errorMessage);
				}
				else{
					$(".errorMessage").html("");
					$(".errorPassword").html(data.errorMessage);
				}
			} else {
				console.log(data.url);
				location.href = data.url;
			}
		}
	});
});

/*$("a.logout-btn").on("click", function(e) {
	e.preventDefault();
	var url = $(".logout-btn").attr("href");
	console.log("url : " + url);
	$.ajax({
		type : 'get',
		error : function() {
			console.log("logout error");
		},
		success : function() {
			location.href = "/";
		}
	});
});
*/