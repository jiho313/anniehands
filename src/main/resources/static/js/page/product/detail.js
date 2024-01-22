document.addEventListener('DOMContentLoaded', () => {
    const colorSelect = document.querySelector('.color');
    const sizeSelect = document.querySelector('.size');
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
            const selectedColor = colorSelect && colorSelect.value !== '색상' ? colorSelect.value : null;
            const selectedSize = sizeSelect && sizeSelect.value !== '사이즈' ? sizeSelect.value : null;

            if (selectedColor || selectedSize) {
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
