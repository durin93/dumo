// $("#profileIcon").addClass("select");
// $("#linkIcon").removeClass("select");
// $("#memoIcon").removeClass("select");
//
//$(".link-friend").addClass("select");
//$(".link-update").removeClass("select");

$(".search-friend-div").on("click", ".addFriend-btn" , function(e){
	e.preventDefault();
	
	var receiverId = $(".userId").val();
	console.log("receiverId : "+receiverId);
	
	var queryString = "receiverId="+receiverId;
	
	$.ajax({
		type :'post',
		url : '/api/users/addFriend',
		data : queryString,
		dataType :'json',
		error: function(){
			alert("add firend request error");
		},
		success : function(data){
			var source = $("#sendRequestFriend-template").html();
			var template = Handlebars.compile(source);
			$(".send-frined-request-wrap").append(template(data));
		}
	});
});

$(document).on("click", ".cancel-request" , function(e){
	e.preventDefault();
	var cancelBtn = $(this);

	var url = cancelBtn.attr("href");
	console.log("url :"+url);
	$.ajax({
		type :'get',
		url : url,
		error: function(){
			alert("cancel friend request error");
		},
		success : function(){
			cancelBtn.closest(".send-friend-request-div").remove();
		}
	});
});

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
