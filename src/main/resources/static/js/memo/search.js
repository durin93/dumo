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
	location.href="/memos/search?labelId="+labelId+"&search="+search+"&value="+searchVal;
});