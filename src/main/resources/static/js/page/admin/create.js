$(document).ready(function() {
    var imageCount = 1;

    $('#addImageBtn').on('click', function() {
        if (imageCount < 3) {
            var newImageInput = document.createElement('div');
            newImageInput.className = 'input-group';
            newImageInput.innerHTML = '<input type="file" class="form-control mt-1" id="inputGroupFile' + imageCount + '" aria-describedby="inputGroupFileAddon' + imageCount + '" aria-label="Upload">' +
                                      '<button type="button" class="btn btn-danger btn-remove" id="removeBtn' + imageCount + '">X</button>';
            $('#imageUploadSection').append(newImageInput);
            imageCount++;
        } else {
            alert('상품의 이미지는 총 3개의 이미지만 업로드할 수 있습니다.');
        }
    });

    // 이벤트 위임을 사용하여 동적으로 생성된 삭제 버튼에 이벤트 리스너 추가
    $('#imageUploadSection').on('click', '.btn-remove', function() {
        $(this).parent('.input-group').remove();
        imageCount--;
    });
});

// 카테고리 비동기 처리 함수
document.getElementById('parentCategorySelect').addEventListener('change', function() {
    var selectedCategory = this.value;
    // AJAX 요청 보내기
    fetch('/admin/product/children-categories?parentCategoryNo=' + selectedCategory)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
        console.log(data)
            var secondCategorySelect = document.getElementById('secondCategorySelect');
            secondCategorySelect.innerHTML = ''; // 기존 옵션 초기화
            data.forEach(function(category) {
                var option = document.createElement('option');
                option.value = category.no;
                option.textContent = category.name;
                secondCategorySelect.appendChild(option);
            });
        });
});

// 상품 이미지 s3 전송 함수
document.getElementById('createProductForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var thumbnail = document.getElementById('inputThumbnail').files[0];
    var productImages = document.querySelectorAll('[id^="inputGroupFile"]');
    var isEmpty = !thumbnail || Array.from(productImages).some(input => input.files.length === 0);

    if (isEmpty)  {
        alert('썸네일 이미지와 상품 이미지를 첨부해주세요.');
        return;
    }

    var formData = new FormData();

    formData.append('file', thumbnail); // 썸네일 이미지 추가
    // 나머지 이미지들을 FormData 객체에 추가
    productImages.forEach(function(fileInput, index) {
        if (fileInput.files.length > 0) {
            formData.append('file', fileInput.files[0]);
        }
    });

//    $('#createProductForm').submit();
    // 서버에 FormData 객체 전송
    fetch('/upload/product', {
        method: 'POST',
        body: formData
    }).then(function(response) {
        return response.json();
    }).then(function(data) {
        console.log(data);
        // 담긴 배열을 hidden input으로 form에 추가한다.
        data.forEach(function(item, index) {
            $('<input>').attr({ type: 'hidden', name: `originFileName[${index}]`, value: item.originFileName }).appendTo('#createProductForm');
            $('<input>').attr({ type: 'hidden', name: `serverFileName[${index}]`, value: item.serverFileName }).appendTo('#createProductForm');
            $('<input>').attr({ type: 'hidden', name: `fileSize[${index}]`, value: item.size }).appendTo('#createProductForm');
        });
        console.log("이미지 업로드 성공!");
        $('#createProductForm').submit();
    }).catch(function(error) {
        console.log(error);
    });
});
