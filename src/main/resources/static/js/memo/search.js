$(".search-btn").on("click", function(e){
	e.preventDefault();
	var labelId = $(".range-select option:selected").val();
	var search = $(".search-select option:selected").val();
	var searchVal = $(".input-search").val();
	var url = $(".search-btn").attr("href")+"/"+labelId;
	
	console.log("labelId :" + labelId);
	console.log("search :" + search);
	console.log("val :" + searchVal);
	console.log("url :" + url);
	
//	location.href="/memos/"+labelId+"/"+search+"/"+searchVal;
//	location.href="/memos/search?labelId="+labelId+"&search="+search+"&value="+searchVal;
	
	var source = $("#apimemo-template").html();
	var template = Handlebars.compile(source);
	var queryString = "labelId="+labelId+"&search="+search+"&value="+searchVal;
	$.ajax({
		type : 'get',
		url : '/api/memos/search',
		data : queryString,
		dataType : 'json',
		error : function(){
			alert("에러");
		},
		success : function(data){
			console.log(data);
			$(".content-main .one-memo").remove();
			$(".content-main").append(template(data));
			$(".one-memo").css("visibility","visible"); 
		}
	});
	
});