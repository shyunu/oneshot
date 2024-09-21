import React, {useState, useEffect, useRef} from 'react';
import ReactDOM from 'react-dom/client';
import './employee.css';
import './one.css';
import Draggable from 'react-draggable';
import axios from "axios";
import DaumPostcode from 'react-daum-postcode';

function Employee() {
    const [showPopup, setShowPopup] = useState(false); //등록팝업
    const [selectedEmployees, setSelectedEmployees] = useState([]); // 선택된 직원 목록
    const [isAllChecked, setIsAllChecked] = useState(false); // 전체 선택 체크박스 상태
    const [departments, setDepartments] = useState([]); //가저온 부서목록
    const [banks, setBanks] = useState([]); //가져온 은행목록
    const [positions, setPositions] = useState([]); //가져온 은행목록
    const nodeRef = useRef(null); //Draggable 오류수정
    const [showMap, setShowMap] = useState(false); //도로명주소 입력창
    const [zodecode, setZonecode] = useState(''); //우편번호
    const [currentPopup, setCurrentPopup] = useState(null); // 선택팝업 'bank', 'department', 'position'
    const [PhotoThumbnail, setPhotoThumbnail] = useState(""); // 사진미리보기용
    const [employeePhoto, setEmployeePhoto] =useState(null); //사진전송용
    const [employees, setEmployees] = useState([]); //사원목록
    const [sortConfig, setSortConfig] = useState({ key: 'employeeNo', direction: 'ascending' }); //사원 정렬
    const [editMode, setEditMode] = useState(false);
    const [totalPages, setTotalPages] = useState(1);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize] = useState(2); //페이징
    const handleStart = (e) => {
        // 드래그 대상이 input인 경우 드래그를 막는다
        if (e.target.tagName === 'INPUT') {
            return false;
        }
    };
    const [search, setSearch] = useState({ // 검색
        employeeNo: '',
        employeeName: '',
        departmentNo: '',
        employeePhone: '',
        positionNo: '',
        employeeStatus: ''
    });
    const [newEmployee, setNewEmployee] = useState({ //사원 등록
        departmentNo: '',
        employeeNo:'',
        positionNo: '',
        positionName: '',
        employeeName: '',
        employeeBirth: '',
        employeePhone: '',
        emergencyPhone: '',
        employeeAddress: '',
        employeeAddressDetail: '',
        employeeEmail: '',
        accountNumber: '',
        bankNo: '',
        bankName: '',
        employeeHiredate: '',
        accountHolder: '',
        departmentName: ''
    });

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        console.log(newEmployee);
        const formData = new FormData();
        if(employeePhoto){
            formData.append("employeePhoto", employeePhoto);
        }
        formData.append("employeeNo", newEmployee.employeeNo);
        formData.append("employeeName", newEmployee.employeeName);
        formData.append("employeeBirth", newEmployee.employeeBirth);
        formData.append("departmentNo", newEmployee.departmentNo);
        formData.append("employeePhone", newEmployee.employeePhone);
        formData.append("emergencyPhone", newEmployee.emergencyPhone);
        formData.append("employeeAddress", newEmployee.employeeAddress);
        formData.append("employeeAddressDetail", newEmployee.employeeAddressDetail);
        formData.append("accountNumber", newEmployee.accountNumber);
        formData.append("employeeHiredate", newEmployee.employeeHiredate);
        formData.append("employeeEmail", newEmployee.employeeEmail);
        formData.append("bankNo", newEmployee.bankNo);
        formData.append("accountHolder", newEmployee.accountHolder);
        formData.append("positionNo", newEmployee.positionNo);


        const url = editMode
            ? "http://localhost:8181/hrm/updateEmployee"
            : "http://localhost:8181/hrm/registEmployee";
        try {
            const response = await axios.post(
                url,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            );

            // 폼 제출 후 초기화
            setShowPopup(false);
            setEmployeePhoto(null);
            setNewEmployee({
                departmentNo: '',
                employeeNo:'',
                positionNo: '',
                positionName: '',
                employeeName: '',
                employeeBirth: '',
                employeePhone: '',
                emergencyPhone: '',
                employeeAddress: '',
                employeeAddressDetail: '',
                employeeEmail: '',
                accountNumber: '',
                bankNo: '',
                bankName: '',
                employeeHiredate: '',
                accountHolder: '',
                departmentName: ''
            });
            console.log('폼 제출 완료 및 직원 생성:', response.data);
            fetchEmployees();
        } catch (error) {
            console.error('폼 제출 실패:', error);
        }
    };
    const postCodeStyle = {
        display: "block",
        position: "absolute",
        top: '50%',
        left: '50%',
        transform: 'translate(-50%,-50%)',
        width: "600px",
        height: "600px",
        border: "2px solid #666",
        zIndex: "999999"
    };
    const handleSearchChange = (e) => {
        const {name, value} = e.target;
        setSearch(prev => ({...prev, [name]: value}));
    };
    const handleSearchBtn = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(
                "http://localhost:8181/hrm/searchEmployee",
                search,
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            setEmployees(response.data); // 상태 업데이트
            console.log('검색폼 제출 완료 및 검색결과 생성:', response.data);
        } catch (error) {
            console.error('검색폼 제출 실패:', error);
        }
    };

    const handleBankSelect = (bankNo, bankName) => {
        setNewEmployee(prev => ({...prev, bankNo: bankNo, bankName: bankName}));
        setCurrentPopup(null);
    };

    const handleDepartmentSelect = (departmentNo, departmentName) => {
        setNewEmployee(prev => ({...prev, departmentNo: departmentNo, departmentName: departmentName}));
        newEmployee.positionName='';
        newEmployee.positionNo='';
        setCurrentPopup(null);
    };

    const handlePositionSelect = (positionNo, positionName) => {
        setNewEmployee(prev => ({...prev, positionNo: positionNo, positionName: positionName}));
        setCurrentPopup(null);
    };

    const handleEmployeeDelete = async () => {
        try {
            await axios.delete("http://localhost:8181/hrm/updateEmployee", {
                data: selectedEmployees, // delete 메서드에서는 data 속성을 사용해야 함
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            fetchEmployees(); // 삭제 후 직원 목록 새로고침
            setSelectedEmployees([]);
            setIsAllChecked(false);
        } catch (err) {
            console.error('Error updating employees:', err);
        }
    };

    const handleFormChange = (e) => {
        const {name, value} = e.target;
        setNewEmployee(prev => ({...prev, [name]: value}));
    };

    const closeMap = (state) => {
        if (state === 'FORCE_CLOSE') {
            setShowMap(false);
        } else if (state === 'COMPLETE_CLOSE') {
            setShowMap(false);
        }
    };

    const completeMap = (data) => {
        const {address, zonecode} = data;
        setZonecode(zonecode);
        setNewEmployee(prev => ({...prev, employeeAddress: address}));
    };



    const handleCheckboxChange = (employeeNo) => {
        setSelectedEmployees((prevSelected) =>
            prevSelected.includes(employeeNo)
                ? prevSelected.filter((no) => no !== employeeNo)
                : [...prevSelected, employeeNo]
        );
    };
    const handleAllCheckboxChange = () => {
        if (isAllChecked) {
            setSelectedEmployees([]);
        } else {
            setSelectedEmployees(employees.map((employee) => employee.employeeNo));
        }
        setIsAllChecked(!isAllChecked);
    };

    const handleImageChange = (e) => { //사진 미리보기 
        const {files} = e.target;
        const thumbnail = files[0];
        setEmployeePhoto(thumbnail) //사진 전송용 설정
        const reader = new FileReader();
        reader.readAsDataURL(thumbnail);
        reader.onloadend = () => {
            setPhotoThumbnail(reader.result);
        }
    };



    const fetchEmployees = async () => {
        try {
            const response = await axios.get(`/hrm/getEmployee?page=${currentPage}&size=${pageSize}`);

            // 상태 코드가 2xx가 아닌 경우 처리
            if (response.status < 200 || response.status >= 300) {
                // 응답 상태 코드가 성공적이지 않을 경우 에러 메시지 로깅
                console.error('직원불러오기 실패: 응답 상태 코드', response.status);
                return;
            }

            // 성공적인 응답일 경우 데이터 설정
            setEmployees(response.data.employees);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            // 네트워크 요청 실패 및 다른 예외 처리
            console.error('직원불러오기 오류:', error.message || error);
        }
    };

    const fetchPositions = async () => {
        try {
            const response = await fetch('/hrm/getPosition');

            if (!response.ok) {
                // 응답이 성공적이지 않을 경우 에러 메시지 로깅
                const errorMessage = await response.text(); // 또는 response.json()을 사용할 수 있습니다.
                console.error('직급불러오기실패:', errorMessage);
                return; // 오류를 처리한 후 함수 종료
            }

            // JSON 응답 데이터 처리
            const data = await response.json();
            setPositions(data); // 상태 업데이트
        } catch (error) {
            console.error('직급불러오기Error:', error); // 네트워크 요청 실패 및 다른 예외 처리
        }
    };
    const fetchBanks = async () => {
        try {
            const response = await fetch('/hrm/getBank');

            if (!response.ok) {
                // 응답이 성공적이지 않을 경우 에러 메시지 로깅
                const errorMessage = await response.text(); // 또는 response.json()을 사용할 수 있습니다.
                console.error('은행불러오기실패:', errorMessage);
                return; // 오류를 처리한 후 함수 종료
            }

            // JSON 응답 데이터 처리
            const data = await response.json();
            setBanks(data); // 상태 업데이트
        } catch (error) {
            console.error('은행불러오기Error:', error); // 네트워크 요청 실패 및 다른 예외 처리
        }
    };

    const fetchDepartments = async () => {
        try {
            const response = await axios.get("http://localhost:8181/hrm/getDepartment");
            setDepartments(response.data);
        } catch (err) {
            console.error('Error fetching departments:', err);
        }
    };

    // 정렬
    const sortedEmployees = [...employees].sort((a, b) => {
        let aValue = a[sortConfig.key];
        let bValue = b[sortConfig.key];

        // 부서번호
        if (sortConfig.key === 'employeeNo') {
            aValue = parseInt(aValue);
            bValue = parseInt(bValue);
        }

        // 사용여부
        if (sortConfig.key === 'isActive') {
            aValue = aValue === 'YES' ? 1 : 0;
            bValue = bValue === 'YES' ? 1 : 0;
        }
        if (aValue < bValue) {
            return sortConfig.direction === 'ascending' ? -1 : 1;
        }
        if (aValue > bValue) {
            return sortConfig.direction === 'ascending' ? 1 : -1;
        }
        return 0;
    });

    const requestSort = (key) => {
        let direction = 'descending';
        if (sortConfig.key === key && sortConfig.direction === 'descending') {
            direction = 'ascending';
        }
        setSortConfig({ key, direction });
    }
    // 행 클릭 핸들러
    const handleRowClick = (employee) => {
        openEditPopup(employee); // 클릭한 사원의 정보를 상태에 저장
    };

    // 등록팝업
    const openRegistrationPopup = () => {
        setEditMode(false);  // 등록 모드
        setNewEmployee({ //초기화
            departmentNo: '',
            employeeNo:'',
            positionNo: '',
            positionName: '',
            employeeName: '',
            employeeBirth: '',
            employeePhone: '',
            emergencyPhone: '',
            employeeAddress: '',
            employeeAddressDetail: '',
            employeeEmail: '',
            accountNumber: '',
            bankNo: '',
            bankName: '',
            employeeHiredate: '',
            accountHolder: '',
            departmentName: ''
        });// 빈 데이터로 초기화
        setShowPopup(true);
    };

    //수정팝업
    const openEditPopup = (employee) => {
        setEditMode(true);   // 수정 모드
        console.log(employee);
        setNewEmployee(employee); // 선택된 사원의 데이터로 초기화
        setShowPopup(true);
    };

    //페이지네이션
    const getVisiblePages = (current, total) => {
        const pages = [];
        for (let i = Math.max(1, current - 2); i <= Math.min(total, current + 2); i++) {
            pages.push(i);
        }
        return pages;
    };

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    const handleNextPages = () => {
        setCurrentPage(prevPage => {
            const newPage = Math.min(prevPage + 5, totalPages);
            return newPage;
        });
    };

    const handlePrevPages = () => {
        setCurrentPage(prevPage => {
            const newPage = Math.max(prevPage - 5, 1);
            return newPage;
        });
    };
    const visiblePages = getVisiblePages(currentPage, totalPages);


    useEffect(() => {
        fetchEmployees();
    }, [currentPage]);
    useEffect(() => {
        fetchPositions();
        fetchDepartments();
    }, []);
    // 디버깅: employees 상태 출력
    useEffect(() => {
        console.log('Employees:', employees);
    }, [employees]);



    return (
        <main className="wrapper">

            <div class="text-wrap">
                <p>계약가격내역</p>
                <p>❉ 조회 또는 수정을 원하시면 원하는 항목을 선택해주세요. </p>
            </div>

            <div class="order-title">
                <div class="filter-content">
                    <div class="filter-main">
                        <h3>상세내역검색</h3>
                        <button class="filter-button" onClick={handleSearchBtn}>검색하기</button>
                    </div>

                    <table>
                        <tr>
                            <td>
                                <p>사원번호</p>
                            </td>
                            <td>
                                <input type="number" name="employeeNo" value={search.employeeNo} onChange={handleSearchChange}/>
                            </td>
                            <td>
                                <p>사원명</p>
                            </td>
                            <td>
                                <input type="text" name="employeeName" value={search.employeeName} onChange={handleSearchChange}/>
                            </td>
                            <td>
                                <p>부서명</p>
                            </td>
                            <td>
                                <select name="departmentNo" value={search.departmentNo} onChange={handleSearchChange}>
                                    <option value="" disabled hidden>부서 선택</option>
                                    <option value="-1">전체</option>
                                    {departments.map(department => (
                                        <option key={department.departmentNo} value={department.departmentNo}>
                                            {department.departmentName}
                                        </option>
                                    ))}
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p>전화번호</p>
                            </td>
                            <td>
                                <input type="number" name="employeePhone" value={search.employeePhone}
                                       onChange={handleSearchChange}/>
                            </td>
                            <td>
                                <p>직급</p>
                            </td>
                            <td>
                                <select name="positionNo" value={search.positionNo} onChange={handleSearchChange}>
                                    <option value="" disabled hidden>직급 선택</option>
                                    <option value="-1">전체</option>
                                    {positions.map(position => (
                                        <option key={position.positionNo} value={position.positionNo}>
                                            {position.positionName}
                                        </option>
                                    ))}
                                </select>
                            </td>
                            <td>
                                <p>재직여부</p>
                            </td>
                            <td>
                                <select name="employeeStatus" value={search.employeeStatus}
                                        onChange={handleSearchChange}>
                                    <option value="" disabled hidden>재직여부 선택</option>
                                    <option value="a">전체</option>
                                    <option value="y">재직</option>
                                    <option value="n">퇴직</option>
                                </select>
                            </td>

                        </tr>
                    </table>
                </div>
            </div>


            <article>
                <table>
                    <thead>
                    <tr id="attribute">
                        <th>
                            <input
                                type="checkbox"
                                id="checkAll"
                                checked={isAllChecked}
                                onChange={handleAllCheckboxChange}
                            />
                            <label htmlFor="checkAll"></label>
                        </th>
                        <th onClick={() => requestSort('employeeHiredate')}>
                            입사일자 {sortConfig.key === 'employeeHiredate' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                        <th onClick={() => requestSort('employeeNo')}>
                            사원번호 {sortConfig.key === 'employeeNo' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                        <th onClick={() => requestSort('employeeName')}>
                            성명 {sortConfig.key === 'employeeName' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                        <th onClick={() => requestSort('departmentName')}>
                            부서명 {sortConfig.key === 'departmentName' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                        <th onClick={() => requestSort('positionNo')}>
                            직급 {sortConfig.key === 'positionNo' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                        <th onClick={() => requestSort('employeePhone')}>
                            전화번호 {sortConfig.key === 'employeePhone' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                        <th onClick={() => requestSort('emergencyPhone')}>
                            비상연락처 {sortConfig.key === 'emergencyPhone' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                        <th onClick={() => requestSort('employeeEmail')}>
                            이메일 {sortConfig.key === 'employeeEmail' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                        <th onClick={() => requestSort('accountNumber')}>
                            계좌번호 {sortConfig.key === 'accountNumber' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                        <th onClick={() => requestSort('employeeStatus')}>
                            재직여부 {sortConfig.key === 'employeeStatus' ? (sortConfig.direction === 'ascending' ? '▼' : '▲') : '▼'}
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {employees && employees.length > 0 ? sortedEmployees.map(employee => (
                        employee ? (
                            <tr key={employee.employeeNo} className="product_list" onClick={() => handleRowClick(employee)}
                                style={
                                    selectedEmployees.includes(employee.employeeNo)
                                        ? { backgroundColor: '#f7f5f2' } // 선택된 경우에만 배경색
                                        : undefined // 선택되지 않은 경우 스타일 적용 안 함
                                }
                            >
                                <td onClick={(e) => e.stopPropagation()}>
                                    <input
                                        type="checkbox"
                                        id={`check${employee.employeeNo}`}
                                        checked={selectedEmployees.includes(employee.employeeNo)}
                                        onChange={() => handleCheckboxChange(employee.employeeNo)}
                                    />
                                    <label htmlFor={`check${employee.employeeNo}`}></label>
                                </td>
                                <td>{employee.employeeHiredate}</td>
                                <td>{employee.employeeNo}</td>
                                <td>{employee.employeeName}</td>
                                <td>{employee.departmentName}</td>
                                <td>{employee.positionName}</td>
                                <td>{employee.employeePhone}</td>
                                <td>{employee.emergencyPhone}</td>
                                <td>{employee.employeeEmail}</td>
                                <td>{employee.accountNumber}</td>
                                <td>{employee.employeeStatus === 'y' ? '재직' : '퇴직'}</td>
                            </tr>
                        ) : null
                    )) : (
                        <tr>
                            <td colSpan="10">등록된 사원이 없습니다.</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </article>

            <div className="wrapper-footer flex">
                <div>
                    <button className="btn" style={{marginRight: "6px"}}
                            onClick={() => handleEmployeeDelete()}>선택퇴직
                    </button>
                    <button className="btn" onClick={() => {
                        setShowPopup(true);
                        openRegistrationPopup();
                    }}>신규등록
                    </button>
                    <div className="pagination">
                        <button onClick={handlePrevPages} disabled={currentPage === 1}>
                            &lt;&lt;
                        </button>
                        {visiblePages.map(page => (
                            <button
                                key={page}
                                onClick={() => handlePageChange(page)}
                                className={page === currentPage ? 'active' : ''}
                            >
                                {page}
                            </button>
                        ))}
                        <button onClick={handleNextPages} disabled={currentPage >= totalPages}>
                            &gt;&gt;
                        </button>
                    </div>
                </div>
            </div>

            {showPopup &&
                <div className="popup" id="contractPopup">
                    <Draggable nodeRef={nodeRef} onStart={handleStart} positionOffset={{x: '-50%', y: '-50%'}}>
                        <div className="popup-content" ref={nodeRef} id="draggablePopup">
                            <div className="popup-header" id="popupHeader">
                                <span>{editMode ? '사원 수정' : '사원 등록'}</span>
                            </div>
                            <form onSubmit={handleFormSubmit} className="popup-form">
                                <table>
                                    <tbody>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <p>사원사진</p>
                                            <input type="file" id="fileInput" accept="image/*" style={{display: "none"}}
                                                   onChange={handleImageChange}/>
                                            <label htmlFor="fileInput" className="btn attach-file">사진변경</label>
                                        </td>
                                        <td colSpan="2">
                                            <div className="profile-picture">
                                                <img src={PhotoThumbnail} alt="Profile Picture" id="profileImg"/>
                                            </div>

                                        </td>
                                    </tr>

                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="employeeName">이름</label>

                                        </td>
                                        <td colSpan="2">
                                            <input type="text" id="employeeName" name="employeeName"
                                                   value={newEmployee.employeeName}
                                                   onChange={handleFormChange}/>
                                        </td>
                                        <td colSpan="3">
                                            <label htmlFor="department">부서번호</label>
                                        </td>
                                        <td colSpan="3">
                                            <input
                                                type="text"
                                                id="departmentName"
                                                name="departmentName"
                                                value={newEmployee.departmentName}
                                                readOnly
                                                onClick={() => {
                                                    fetchDepartments();
                                                    setCurrentPopup('department');
                                                }}
                                            />
                                        </td>
                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="positionNo">직급</label>

                                        </td>
                                        <td colSpan="2">
                                            <input
                                                type="text"
                                                id="positionName"
                                                name="positionNo"
                                                value={newEmployee.positionName}
                                                readOnly
                                                onClick={() => {
                                                    fetchPositions(); // 직급 목록 가져오기 함수
                                                    setCurrentPopup('position'); // 팝업 상태 설정
                                                }}
                                                onChange={handleFormChange}

                                            />
                                        </td>
                                        <td colSpan="3">
                                            <label htmlFor="employeeHiredate">입사일</label>

                                        </td>
                                        <td colSpan="3">
                                            <input type="date" id="employeeHiredate" name="employeeHiredate"
                                                   value={newEmployee.employeeHiredate} onChange={handleFormChange}/>
                                        </td>
                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="employeePhone">전화번호</label>

                                        </td>
                                        <td colSpan="2">
                                            <input type="text" id="employeePhone" name="employeePhone"
                                                   value={newEmployee.employeePhone}
                                                   onChange={handleFormChange}/>
                                        </td>
                                        <td colSpan="3">
                                            <label htmlFor="emergencyPhone">비상연락처</label>
                                        </td>
                                        <td colSpan="3">
                                            <input type="text" id="emergencyPhone" name="emergencyPhone"
                                                   value={newEmployee.emergencyPhone} onChange={handleFormChange}/>
                                        </td>
                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="employeeBirth">생년월일</label>
                                        </td>
                                        <td colSpan="2">
                                            <input type="date" id="employeeBirth" name="employeeBirth"
                                                   value={newEmployee.employeeBirth} onChange={handleFormChange}/>
                                        </td>
                                        <td colSpan="3">
                                            <label htmlFor="employeeEmail">이메일</label>
                                        </td>
                                        <td colSpan="3">
                                            <input type="text" id="employeeEmail" name="employeeEmail"
                                                   value={newEmployee.employeeEmail}
                                                   onChange={handleFormChange}/>
                                        </td>
                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="accountBank">은행</label>
                                        </td>
                                        <td colSpan="2">
                                            <input
                                                type="text"
                                                id="accountBank"
                                                name="bank"
                                                placeholder="은행 선택"
                                                value={newEmployee.bankName}
                                                onClick={() => {
                                                    fetchBanks(); // 은행 목록 가져오기 함수
                                                    setCurrentPopup('bank'); // 팝업 상태 설정
                                                }}
                                                readOnly
                                            />
                                        </td>
                                        <td colSpan="3">
                                            <label htmlFor="employeeEmail">계좌번호</label>
                                        </td>
                                        <td colSpan="3">
                                            <input type="text" id="accountNumber" name="accountNumber"
                                                   placeholder="계좌번호" value={newEmployee.accountNumber}
                                                   onChange={handleFormChange}/>
                                        </td>
                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="employeeAddress">주소</label>
                                        </td>
                                        <td colSpan="2">
                                            <input
                                                type="text"
                                                id="employeeAddress"
                                                name="employeeAddress"
                                                placeholder="주소를 입력하세요"
                                                value={newEmployee.employeeAddress}
                                                onClick={() => setShowMap(true)} // 주소 검색을 위한 지도 또는 검색창 표시
                                                readOnly
                                            />
                                        </td>
                                        <td colSpan="3">
                                            <label htmlFor="employeeAddressDetail">상세주소</label>
                                        </td>
                                        <td colSpan="3">
                                            <input type="text" id="employeeAddressDetail" name="employeeAddressDetail"
                                                   placeholder="상세주소를 입력하세요" value={newEmployee.employeeAddressDetail}
                                                   onChange={handleFormChange}/>
                                        </td>
                                    </tr>
                                    </tbody>

                                </table>
                                <div className="popup-buttons">
                                    <button type="submit" className="btn" style={{margin: "5px"}}>저장</button>
                                    <button type="button" className="btn close"
                                            onClick={() => {
                                                setShowPopup(false);
                                                setNewEmployee({
                                                    departmentNo: '',
                                                    employeeNo:'',
                                                    positionNo: '',
                                                    positionName: '',
                                                    employeeName: '',
                                                    employeeBirth: '',
                                                    employeePhone: '',
                                                    emergencyPhone: '',
                                                    employeeAddress: '',
                                                    employeeAddressDetail: '',
                                                    employeeEmail: '',
                                                    accountNumber: '',
                                                    bankNo: '',
                                                    bankName: '',
                                                    employeeHiredate: '',
                                                    accountHolder: '',
                                                    departmentName: ''
                                                });
                                            }}>닫기
                                    </button>
                                </div>
                            </form>
                        </div>
                    </Draggable>
                </div>
            }
            {/*선택팝업창*/}
            {currentPopup && (
                <div id="SelectPopup" className="popup">
                    <div className="popup-content">
                        <div className="popup-header">
                            <h3>
                                {currentPopup === 'bank' && '은행 선택'}
                                {currentPopup === 'department' && '부서 선택'}
                                {currentPopup === 'position' && '직책 선택'}
                            </h3>
                            <button className="btn close" onClick={() => setCurrentPopup(null)}>닫기</button>
                        </div>
                        <div className="options">
                            {currentPopup === 'bank' && banks.map(bank => (
                                <div key={bank.bankNo} className="select-item"
                                     onClick={() => handleBankSelect(bank.bankNo, bank.bankName)}>
                                    <button className="no-option">{bank.bankNo}</button>
                                    <button className="name-option">{bank.bankName}</button>
                                </div>
                            ))}
                            {currentPopup === 'department' && departments.map(department => (
                                <div key={department.departmentNo} className="select-item"
                                     onClick={() => handleDepartmentSelect(department.departmentNo, department.departmentName)}>
                                    <button className="no-option">{department.departmentNo}</button>
                                    <button className="name-option">{department.departmentName}</button>
                                </div>
                            ))}
                            {currentPopup === 'position' && positions
                                .filter(position =>
                                    newEmployee.departmentName === "경영팀" || position.positionNo >= 8
                                )
                                .map(position => (
                                    <div key={position.positionNo} className="select-item"
                                         onClick={() => handlePositionSelect(position.positionNo, position.positionName)}>
                                        <button className="no-option">{position.positionNo}</button>
                                        <button className="name-option">{position.positionName}</button>
                                    </div>
                                ))
                            }
                        </div>
                    </div>
                </div>
            )}
            {/* 도로명주소 입력 */}
            {showMap && (
                <div>
                    <DaumPostcode
                        onComplete={completeMap}
                        onClose={closeMap}
                        style={postCodeStyle}
                    />
                </div>
            )}
        </main>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Employee/>
);
