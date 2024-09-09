document.addEventListener("DOMContentLoaded", function () {
    const contractPopup = document.getElementById("contractPopup");
    const closePopupButton = document.getElementById("closePopupButton");
    const draggablePopup = document.getElementById("draggablePopup");
    const popupHeader = document.getElementById("popupHeader");
    const fileInput = document.getElementById("fileInput");
    const profileImg = document.getElementById("profileImg");

    // 은행 선택 관련 요소
    const selectBankBtn = document.getElementById("selectBankBtn");
    const bankSelectPopup = document.getElementById("bankSelectPopup");
    const closeBankPopupButton = document.querySelector("#bankSelectPopup .btn.close");
    const accountBankInput = document.getElementById("accountBank");

    // 주소 입력 관련 요소
    const addressInput = document.getElementById("employeeAddress");
    const searchAddressBtn = document.getElementById("searchAddressBtn");

    // 메인 팝업 닫기
    closePopupButton.addEventListener("click", function () {
        contractPopup.style.display = "none";
    });

    // 은행 선택 버튼 클릭 시 은행 선택 팝업 열기
    selectBankBtn.addEventListener("click", function () {
        bankSelectPopup.style.display = "flex"; // 은행 선택 팝업 열기
        bankSelectPopup.style.top = "50%";
        bankSelectPopup.style.left = "50%";
        bankSelectPopup.style.transform = "translate(-50%, -50%)"; // 팝업 중앙 정렬
    });

    // 은행 선택 팝업 닫기
    closeBankPopupButton.addEventListener("click", function () {
        bankSelectPopup.style.display = "none";
    });

    // 은행 코드 및 이름 버튼 클릭 시
    document.querySelectorAll(".bank-item").forEach(item => {
        const codeButton = item.querySelector(".bank-code-option");
        const nameButton = item.querySelector(".bank-name-option");

        // 은행 코드 버튼 클릭 시
        codeButton.addEventListener("click", function () {
            updateBankInput(nameButton);
        });

        // 은행 이름 버튼 클릭 시
        nameButton.addEventListener("click", function () {
            updateBankInput(nameButton);
        });
    });

    function updateBankInput(nameButton) {
        const bankName = nameButton.textContent;
        accountBankInput.value = bankName; // 선택한 은행 이름만 입력 필드에 반영
        bankSelectPopup.style.display = "none"; // 은행 선택 팝업 닫기
    }

    // 드래그 기능 추가
    let isDragging = false;
    let startX, startY, initialX, initialY;

    popupHeader.addEventListener("mousedown", function (event) {
        isDragging = true;
        startX = event.clientX;
        startY = event.clientY;
        const rect = draggablePopup.getBoundingClientRect();
        initialX = rect.left;
        initialY = rect.top;
        event.preventDefault();
    });

    document.addEventListener("mousemove", function (event) {
        if (isDragging) {
            const currentX = event.clientX;
            const currentY = event.clientY;
            const dx = currentX - startX;
            const dy = currentY - startY;
            draggablePopup.style.left = `${initialX + dx}px`;
            draggablePopup.style.top = `${initialY + dy}px`;
        }
    });

    document.addEventListener("mouseup", function () {
        isDragging = false;
    });

    // 파일 선택 시 프로필 사진 미리보기 기능
    fileInput.addEventListener("change", function (event) {
        const file = fileInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                profileImg.src = e.target.result; // 이미지 src를 변경하여 미리보기 반영
            };
            reader.readAsDataURL(file);
        }
    });

    // 주소 검색 버튼 클릭 시 카카오 주소 API 호출
    searchAddressBtn.addEventListener("click", function () {
        new daum.Postcode({
            oncomplete: function (data) {
                // 주소 선택 완료 시 해당 주소를 입력란에 삽입
                let fullAddress = data.address; // 기본 주소
                let extraAddress = ''; // 참고 항목 (지번, 건물명 등)

                // 참고 항목 추가
                if (data.addressType === 'R') {
                    if (data.bname !== '') extraAddress += data.bname;
                    if (data.buildingName !== '') {
                        extraAddress += (extraAddress !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    fullAddress += (extraAddress !== '' ? ' (' + extraAddress + ')' : '');
                }

                // 주소 입력 필드에 주소 반영
                addressInput.value = fullAddress;
            }
        }).open();
    });

    // 페이지 로드 시 은행 선택 팝업이 열리지 않도록 초기화
    bankSelectPopup.style.display = "none";
});
