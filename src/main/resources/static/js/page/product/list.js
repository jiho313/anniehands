function changeSortOrder(sortValue) {
    var params = new URLSearchParams(window.location.search);
    var sortParams = sortValue.split(' '); // 공백으로 분리
    if (sortParams.length === 2) {
        var sortField = sortParams[0];
        var sortDirection = sortParams[1].toUpperCase(); // 'asc' 또는 'desc'로 변경
        params.set('sort', sortField + ',' + sortDirection);
    }
    window.location.search = params.toString();
}
