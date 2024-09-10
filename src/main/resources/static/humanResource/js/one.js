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
        bankSelectPopup.style.display = "flex";
        bankSelectPopup.style.top = "50%";
        bankSelectPopup.style.left = "50%";
        bankSelectPopup.style.transform = "translate(-50%, -50%)";
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
        accountBankInput.value = bankName;
        bankSelectPopup.style.display = "none";
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
            draggablePopup.style.left = ${initialX + dx}px;
            draggablePopup.style.top = ${initialY + dy}px;
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
                profileImg.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    // 주소 검색 버튼 클릭 시 카카오 주소 API 호출
    searchAddressBtn.addEventListener("click", function () {
        new daum.Postcode({
            oncomplete: function (data) {
                let fullAddress = data.address;
                let extraAddress = '';

                if (data.addressType === 'R') {
                    if (data.bname !== '') extraAddress += data.bname;
                    if (data.buildingName !== '') {
                        extraAddress += (extraAddress !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    fullAddress += (extraAddress !== '' ? ' (' + extraAddress + ')' : '');
                }

                addressInput.value = fullAddress;
            }
        }).open();
    });

    // 페이지 로드 시 사원 목록을 불러옵니다.
    async function fetchEmployeeList() {
        try {
            const response = await fetch('/api/employees');
            const employees = await response.json();
            renderEmployeeList(employees);
        } catch (error) {
            console.error('Failed to fetch employees:', error);
        }
    }

    function renderEmployeeList(employees) {
        const employeeTableBody = document.querySelector('#employeeTable tbody');
        employeeTableBody.innerHTML = '';

        employees.forEach(employee => {
            const row = document.createElement('tr');
            row.innerHTML =
                <td><a href="#" class="employee-link" data-employee-id="${employee.employee_id}">${employee.employee_id}</a></td>
                <td><a href="#" class="employee-link" data-employee-id="${employee.employee_id}">${employee.name}</a></td>
                <td>${employee.department}</td>
            ;
            employeeTableBody.appendChild(row);
        });

        // 사원 링크 클릭 시 팝업 열기
        document.querySelectorAll('.employee-link').forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                const employeeId = this.dataset.employeeId;
                openEmployeePopup(employeeId);
            });
        });
    }

    async function openEmployeePopup(employeeId) {
        // 사원 상세정보를 가져오는 API 호출 (예시 URL)
        try {
            const response = await fetch(/api/employees/${employeeId});
            const employee = await response.json();
            // 팝업에 정보를 채우고 표시합니다.
            fillPopupWithEmployeeData(employee);
            contractPopup.style.display = 'block';
        } catch (error) {
            console.error('Failed to fetch employee details:', error);
        }
    }

    function fillPopupWithEmployeeData(employee) {
        document.getElementById('employeeName').value = employee.name;
        document.getElementById('employeeNo').value = employee.employee_id;
        document.getElementById('department').value = employee.department;
        document.getElementById('employeeRank').value = employee.rank;
        document.getElementById('employeeHiredate').value = employee.hire_date;
        document.getElementById('employeePhone').value = employee.phone;
        document.getElementById('emergencyPhone').value = employee.emergency_phone;
        document.getElementById('employeeBirth').value = employee.birth_date;
        document.getElementById('employeeEmail').value = employee.email;
        document.getElementById('accountBank').value = employee.bank_name;
        document.getElementById('accountNumber').value = employee.account_number;
        document.getElementById('accountHolder').value = employee.account_holder;
        document.getElementById('employeeAddress').value = employee.address;
        document.getElementById('employeeAddress1').value = employee.address_detail;
    }

    // 페이지 로드 시 사원 목록을 불러옵니다.
    fetchEmployeeList();

    // 은행 선택 팝업이 열리지 않도록 초기화
    bankSelectPopup.style.display = "none";
});