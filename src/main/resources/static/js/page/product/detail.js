document.querySelector('#orderForm').addEventListener('submit', (event) => {
    event.preventDefault(); // 기본 제출 방지

    const colorSelect = document.querySelector('#color');
    const sizeSelect = document.querySelector('#size');

    // 동적으로 생성된 각 행에 대한 데이터 수집
    document.querySelectorAll('.option tbody tr').forEach((row, index) => {
        const itemTotalPrice = row.cells[2].textContent;
        const quantity = row.querySelector('.quantity-input').value;

        // 숨겨진 필드 생성 및 설정
        const inputItemTotalPrice = document.createElement('input');
        inputItemTotalPrice.type = 'hidden';
        inputItemTotalPrice.name = `items[${index}].itemTotalPrice`;
        inputItemTotalPrice.value = itemTotalPrice;
        event.target.appendChild(inputItemTotalPrice);

        const inputQuantity = document.createElement('input');
        inputQuantity.type = 'hidden';
        inputQuantity.name = `items[${index}].quantity`;
        inputQuantity.value = quantity;
        event.target.appendChild(inputQuantity);

        // 선택된 색상과 사이즈 값 추가
        // 색상 옵션이 선택되었는지 확인
        if (colorSelect && colorSelect.value !== 0) {
            const inputColor = document.createElement('input');
            inputColor.type = 'hidden';
            inputColor.name = `items[${index}].color`;
            inputColor.value = colorSelect.value;
            event.target.appendChild(inputColor);
        }

        // 사이즈 옵션이 선택되었는지 확인
        if (sizeSelect && sizeSelect.value !== 0) {
            const inputSize = document.createElement('input');
            inputSize.type = 'hidden';
            inputSize.name = `items[${index}].size`;
            inputSize.value = sizeSelect.value;
            event.target.appendChild(inputSize);
        }
    });
    // FormData 객체 생성
        let formData = new FormData(event.target);

        // FormData에 추가된 데이터를 콘솔에 출력
        for (let [key, value] of formData.entries()) {
            console.log(key, value);
        }
    // 숨겨진 필드를 포함한 폼 데이터 제출
//    event.target.submit();
});


document.addEventListener('DOMContentLoaded', () => {

    const colorSelect = document.querySelector('#color');
    const sizeSelect = document.querySelector('#size');
    const quantityInput = document.querySelector('.form-control-sm');
    const table = document.querySelector('.table');
    /*세일가가 없으면 일반 가격 선택*/
    let basePriceElement = document.querySelector('.sale');
    if (!basePriceElement) {
        basePriceElement = document.querySelector('.price');
    }

    const basePrice = parseInt(basePriceElement.innerText.replace(/,/g, ''));
    const productName = document.querySelector('.productName').innerText;
    const totalAmountSpan = document.querySelector('.text-end h4 span');

    const isRowAlreadyAdded = (selectedColor, selectedSize) => {
        return Array.from(table.querySelectorAll('tbody tr')).some(row => {
            const rowData = row.querySelector('td:first-child').textContent;
            const colorMatch = selectedColor ? rowData.includes(selectedColor) : true;
            const sizeMatch = selectedSize ? rowData.includes(selectedSize) : true;
            return colorMatch && sizeMatch;
        });
    }

    const updateTotalAmount = () => {
        const totalPriceCells = table.querySelectorAll('.total-price');
        let totalAmount = 0;
        totalPriceCells.forEach(cell => {
            totalAmount += parseInt(cell.textContent.replace(/,/g, '').replace(' 원', ''));
        });
        totalAmountSpan.textContent = totalAmount.toLocaleString();
    }

    let rowId = 0;
    const addTableRow = (quantity) => {
        const selectedColor = colorSelect ? colorSelect.options[colorSelect.selectedIndex].text : '';
        const selectedSize = sizeSelect ? sizeSelect.options[sizeSelect.selectedIndex].text : '';

        if ((selectedColor !== '색상' || !colorSelect) &&
            (selectedSize !== '사이즈' || !sizeSelect) &&
            !isRowAlreadyAdded(selectedColor, selectedSize)) {
            const productOptions = ` ${(selectedColor || '')} ${(selectedSize || '')}`.trim();
            const productNameWithOptions = productName + (productOptions ? ` (${productOptions})` : '');
            const totalPrice = basePrice * quantity;

            const newRow = document.createElement('tr');
            newRow.id = `row-${rowId++}`;
            newRow.innerHTML = `
                <td>${productNameWithOptions}</td>
                <td><input class="form-control form-control-sm quantity-input" type="number" value="${quantity}" min="1" max="100" style="width: 55px;"></td>
                <td class="total-price">${totalPrice.toLocaleString()} 원</td>
                <td><a class="delete"><i class="fa-regular fa-rectangle-xmark"></i></a></td>

            `;

            newRow.querySelector('.quantity-input').addEventListener('input', (event) => {
                const newQuantity = parseInt(event.target.value);
                const totalPriceCell = document.querySelector(`#${newRow.id} .total-price`);
                totalPriceCell.textContent = (basePrice * newQuantity).toLocaleString() + ' 원';
                updateTotalAmount();
            });

            newRow.querySelector('.delete').addEventListener('click', () => {
                newRow.remove();
                updateTotalAmount();
            });

            const tbody = table.querySelector('tbody');
            tbody.appendChild(newRow);
            updateTotalAmount();
        }
    }

    let initialQuantity = parseInt(quantityInput.value);
    initialQuantity = isNaN(initialQuantity) ? 1 : initialQuantity;

    // 옵션이 없는 경우에는 페이지 로드 시에 행을 추가
    if (!colorSelect && !sizeSelect) {
        addTableRow(initialQuantity);
    }

    const handleSelectChange = () => {
        const selectedColor = colorSelect && colorSelect.value;
        const selectedSize = sizeSelect && sizeSelect.value;

        // "색상"이나 "사이즈"가 선택되지 않았을 때만 addTableRow 함수 호출
        if ((selectedColor && selectedColor !== "색상") || (selectedSize && selectedSize !== "사이즈")) {
            addTableRow(initialQuantity);
        }
    }

    if (colorSelect) colorSelect.addEventListener('change', handleSelectChange);
    if (sizeSelect) sizeSelect.addEventListener('change', handleSelectChange);

    quantityInput.addEventListener('input', (event) => {
        const newQuantity = parseInt(event.target.value);
        const totalPriceCell = document.querySelector(`#row-0 .total-price`);
        if (totalPriceCell) {
            totalPriceCell.textContent = (basePrice * newQuantity).toLocaleString() + ' 원';
            updateTotalAmount();
        }
    });
});
