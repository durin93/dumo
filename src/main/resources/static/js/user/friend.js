 $("#profileIcon").addClass("select");
 $("#linkIcon").removeClass("select");
 $("#memoIcon").removeClass("select");

$(".link-friend").addClass("select");
$(".link-friendRequest").removeClass("select");
$(".link-update").removeClass("select");

//친구요청
$(".search-friend-div").on("click", ".addFriend-btn" , function(e){
	e.preventDefault();
	
	var receiverId = $(".userId").val();
	console.log("receiverId : "+receiverId);
	
	var queryString = "receiverId="+receiverId;
	
	$.ajax({
		type :'post',
		url : '/api/relations/request',
		data : queryString,
		dataType :'json',
		error: function(){
			alert("add friend request error");
		},
		success : function(data){
			if(data.status == false){
					alert("이미 보낸 요청입니다");
			}
			else{
			var source = $("#sendRequestFriend-template").html();
			var template = Handlebars.compile(source);
			$(".send-frined-request-wrap").append(template(data));
		}
	}
	});
});

//친구요청취소 및 거절
$(document).on("click", ".cancel-request" , function(e){
	e.preventDefault();
	var cancelBtn = $(this);

	var url = cancelBtn.attr("href");
	console.log("url :"+url);
	$.ajax({
		type :'delete',
		url : url,
		error: function(){
			alert("cancel friend request error");
		},
		success : function(){
			cancelBtn.closest(".send-friend-request-div").remove();
		}
	});
});


//친구검색
$(document).on("click", ".search-btn", function(e) {
	e.preventDefault();

	var userId = $(".user-search").val();
	console.log("userId :" + userId);

	queryString = "userId="+userId;
	
	if (userId == "") {
		$(".user-search").focus();
	} else {

		$.ajax({
			type : 'get',
			url : "/api/users/search",
			data : queryString,
			dataType : 'json',
			error : function() {
				alert("search user error");
			},
			success : function(data) {
				$(".search-friend-div").empty();
				if(data.userId=="x"){
					var source = $("#searchFail-template").html();
					var template = Handlebars.compile(source);
					$(".search-friend-div").append(template);
				}
				else{
					console.log(data.saveFileName);
				var source = $("#searchUser-template").html();
				var template = Handlebars.compile(source);
				$(".search-friend-div").append(template(data));
			}
				}
		});
	}
});
