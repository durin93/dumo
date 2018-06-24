$(".join-form input[type=submit]").on("click", function(e) {
	e.preventDefault();

	var userId = $(".id").val();
	var name = $(".name").val();
	var password = $(".password").val();
	
	
	var url = $(".join-form").attr("action");
	console.log("url : " + url);
	console.log("회원가입 클릭");

	$.ajax({
		type : 'post',
		url : url,
		contentType: "application/json",
		data : JSON.stringify({
			userId : userId,
			name : name,
			password : password
		}),
		dataType : 'json',
		error : function() {
			alert("error");
		},
		success : function(data) {
				if(data.errorPart=="id"){
					$(".errorMessage").html("");
					$(".errorId").html(data.errorMessage);
				}
				else{
				console.log(data.url);
				location.href = data.url;
				}
			}
	});
	
});
