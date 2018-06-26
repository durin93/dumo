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
	
	location.href="/memo/list/"+labelId+"/"+search+"/"+searchVal;
	
	/*$.ajax({
		type: 'post',
		url : url,
		contentType: "application/json",
		data: JSON.stringify({
		search : search,
		searchVal : searchVal
		}),
		error : function(){
			console.log("error");
		}
	});*/
	
	
});