 $("#linkIcon").addClass("select");
 $("#memoIcon").removeClass("select");
 $("#profileIcon").removeClass("select");
 
 $("#write-btn").removeClass();
 $("#write-btn").addClass("write-link-btn");
 $("#write-btn").prop("href", "/api/links");
 $("#write-btn").html("새 링크 쓰기");
 
 loadLinkLeftNav();
 loadLinkContentNav();
 
 function loadLinkLeftNav(){
		var source = $("#link-left-nav").html();
		var template = Handlebars.compile(source);
		
		console.log(source);
				$(".main-category").append(template);
	}	
 
 
 function loadLinkContentNav(){
		var source = $("#link-content-nav").html();
		var template = Handlebars.compile(source);
		
		$.ajax({
			type : 'get',
			url : '/api/links/size',
			dataType : 'json',
			error : function(){
				alert("loadMemoContentNav에러");
			},
			success : function(data){
				console.log(data);
				$(".info").append(template({linkCount : data}));
			}
		});
	}