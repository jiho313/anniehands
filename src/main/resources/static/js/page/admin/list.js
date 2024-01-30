function changeSortOrder(sortValue) {
    var params = new URLSearchParams(window.location.search);
    params.set('sort', sortValue);
    window.location.search = params.toString();
}

// 필드 값 설정 후 폼 제출 함수
function updateAndSubmit(name, value, resetPage = true) {
	$(`input[name=${name}]`).val(value);
    if (resetPage) {
        $("input[name=page]").val(1);
    }
    $("#form-letsparty-search").submit();
}

$(function(){
	// 카테고리 변경
	$("#letsparty-categorys .nav-link").on("click", function(e){
	    e.preventDefault();
	    let categoryNo = $(this).data("value");
	    updateAndSubmit("categoryNo", categoryNo);
	});

	// 정렬 변경
	$("select[name=sort]").on("change", function(){
		let sort = $("select[name=sort]").val();
	    updateAndSubmit("sort", sort);
	});

	// 검색
	$("#outline-btn").on("click", function(){
		let keyword = $("input[name=keyword]").val();
	    if (keyword.trim() === "") {
	        alert("키워드를 입력하세요!");
	        $("input[name=keyword]").focus();
	        return;
	    }
	    $("input[name=page]").val(1);
	    updateAndSubmit("keyword", keyword);
	});

});

// 페이지 변경
function changePage(e, page) {
   e.preventDefault();
   updateAndSubmit("page", page, false);
}