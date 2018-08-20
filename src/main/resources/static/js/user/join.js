
$(".join-form input[type=submit]").on("click", function(e) {
	e.preventDefault();

//	var userId = $(".id").val();
//	var name = $(".name").val();
//	var password = $(".password").val();
//	var oauthId = $(".oauthId").val();
//	var profileImg = $(".profileImg").val();
//	var division =  "dumo";
//	if(oauthId!=null){
//		 division ="kakao";
//	}
//	
	var queryString = $(".join-form").serialize();
	
	var url = $(".join-form").attr("action");
	console.log("url : " + url);
	console.log("회원가입 클릭");

	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : function() {
			alert("error");
		},
		success : function(data) {
				if(data.errorPart=="id"){
					console.log("id 유효성오류"+data.errorMessage);
					$(".errorMessage").html("");
					$(".errorId").html(data.errorMessage);
				}
				else if(data.errorPart=="password"){
					console.log("password 유효성오류"+data.errorMessage);
					$(".errorMessage").html("");
					$(".errorPassword").html(data.errorMessage);
				}
				else{
				console.log(data.url);
				location.href = data.url;
				}
			}
	});
	
});


$(".oauth-join-form input[type=submit]").on("click", function(e) {
	e.preventDefault();
	var queryString = $(".oauth-join-form").serialize();
	
	var url = $(".oauth-join-form").attr("action");
	console.log("url : " + url);
	console.log("회원가입 클릭");
	
	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
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



