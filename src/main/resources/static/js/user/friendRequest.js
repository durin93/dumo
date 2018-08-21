 $("#profileIcon").addClass("select");
 $("#linkIcon").removeClass("select");
 $("#memoIcon").removeClass("select");

$(".link-friendRequest").addClass("select");
$(".link-friend").removeClass("select");
$(".link-update").removeClass("select");


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

//친구요청수락
$(document).on("click", ".accept-request", function(e){
	e.preventDefault();
	
	var acceptBtn = $(this);

	
	var url = $(".accept-request").attr("href");
	var requestId = $(".requestId").val();
	console.log("requestId : "+requestId);
	var senderId = $(".senderId").val();
	
	var queryString = "requestId="+requestId+"&senderId="+senderId;
	
	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : function(){
			alert("친구수락error");
		},
		success : function(data){
			var source = $("#friendList-template").html();
			var template = Handlebars.compile(source);
			$(".right-label-list-ul").append(template(data));
			
			acceptBtn.closest(".send-friend-request-div").remove();

			
		}
	});
	
});

