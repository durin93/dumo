$("#profileIcon").addClass("select");
$("#linkIcon").removeClass("select");
$("#memoIcon").removeClass("select");

$(".link-update").addClass("select");
$(".link-friend").removeClass("select");

$(".update-form input[type=submit]").on("click", function(e) {
	e.preventDefault();

	var userId = $(".id").val();
	var name = $(".name").val();
	var newPassword = $(".newPassword").val();
	var newPassword2 = $(".newPassword2").val();
	var password = $(".password").val();

	var file = new FormData($(".update-form")[0]);

	console.log("userId :" + userId);
	console.log("name :" + name);
	console.log("password :" + password);
	console.log("newPassword :" + newPassword);

	if (password == "") {
		$(".password").focus();
		$(".errorMessage").html("");
		$(".errorPassword").html("비밀번호를 입력해주세요.");
	} else if (newPassword != newPassword2) {
		$(".errorMessage").html("");
		$(".errorNewPassword2").html("비밀번호가 일치하지 않습니다.");
		$(".newPassword2").focus();
	}

	else {
		var url = $(".update-form").attr("action");
		console.log("url : " + url);
		console.log("정보수정 클릭");

		$.ajax({
			type : 'put',
			contentType : "application/json",
			url : url,
			data : JSON.stringify({
				userId : userId,
				name : name,
				password : password,
				newPassword : newPassword
			}),
			dataType : 'json',
			error : function() {
				alert("error");
			},
			success : function(data) {
				if (!data.url) {
					$(".errorMessage").html("");
					$(".errorPassword").html(data.errorMessage);
				} else {
					console.log(data.url);
					location.href = data.url;
				}
			}
		});

		if (file != null) {
			$.ajax({
				type : 'put',
				url : "/api/attachments",
				data : file,
				dataType : 'json',
				processData : false,
				contentType : false,
				success : function(data) {
					console.log(data);
					alert("성공적으로 등록");
				},
				error : function(error) {
					if (error.status == 500) {
						alert("파일을 선택해주세요.");
					} else if (error.status = 403) {
						location.href = loginUrl;
					}
				}
			});
		}

	}
});
