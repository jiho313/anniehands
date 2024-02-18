$(function(){
	// 정렬 변경
	$("select[name=sort]").on("change", function(){
		let sort = $("select[name=sort]").val();
	    updateAndSubmit("sort", sort, false);
	});
});

// 필드 값 설정 후 폼 제출 함수
function updateAndSubmit(name, value, resetPage = true) {
    const currentOpt = $("select[name=opt]").val();
    const currentKeyword = $("input[name=keyword]").val();

    $("input[name=" + name + "]").val(value);
    $("input[name=opt]").val(currentOpt);
    $("input[name=keyword]").val(currentKeyword);

    if (resetPage) {
        $("input[name=page]").val(0);
    }
    $("#form-admin-product-search").submit();
}

// 검색 로직을 별도의 함수로 분리
function performSearch() {
    let keyword = $("input[name=keyword]").val();
    if (keyword.trim() === "") {
        alert("키워드를 입력하세요!");
        $("input[name=keyword]").focus();
        return;
    }
    $("input[name=page]").val(0);
    updateAndSubmit("keyword", keyword, true);
}

// 검색 버튼 클릭 이벤트 핸들러
$("#outline-btn").on("click", function() {
    performSearch();
});

// 엔터 키 이벤트 핸들러
$("input[name='keyword']").keypress(function(e) {
    if (e.which == 13) { // 13은 엔터 키의 키코드
        e.preventDefault(); // 기본 엔터 키 이벤트 방지
        performSearch();
    }
});

// 페이지 변경
function changePage(e, page) {
   e.preventDefault();
   updateAndSubmit("page", page, false);
}

$(".deleteButton").on("click", function(e){
    e.preventDefault();
    var deleteUrl = $(this).attr('href');
    if(confirm('정말 삭제하시겠습니까?')) {
            // 사용자가 '확인'을 클릭하면 삭제 처리를 합니다.
            window.location.href = deleteUrl;
        }
});