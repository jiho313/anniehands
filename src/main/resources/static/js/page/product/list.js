function changeSortOrder(sortValue) {
    var params = new URLSearchParams(window.location.search);
    params.set('sort', sortValue);
    window.location.search = params.toString();
}
