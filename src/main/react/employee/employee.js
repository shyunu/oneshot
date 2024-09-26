import React, {useState, useEffect, useRef} from 'react';
import ReactDOM from 'react-dom/client';
import './employee.css';
import './one.css';
import Draggable from 'react-draggable';
import axios from "axios";
import DaumPostcode from 'react-daum-postcode';
import { CSVLink } from 'react-csv';

function Employee() {
    const [showPopup, setShowPopup] = useState(false); //등록팝업
    const [selectedEmployees, setSelectedEmployees] = useState([]); // 선택된 직원 목록
    const [isAllChecked, setIsAllChecked] = useState(false); // 전체 선택 체크박스 상태
    const [departments, setDepartments] = useState([]); //가저온 부서목록
    const [banks, setBanks] = useState([]); //가져온 은행목록
    const [positions, setPositions] = useState([]); //가져온 직급목록
    const nodeRef = useRef(null); //Draggable 오류수정
    const [showMap, setShowMap] = useState(false); //도로명주소 입력창
    const [zodecode, setZonecode] = useState(''); //우편번호
    const [PhotoThumbnail, setPhotoThumbnail] = useState("../../common/img/default.png"); // 사진미리보기용
    const [employeePhoto, setEmployeePhoto] =useState(null); //사진전송용
    const [employees, setEmployees] = useState([]); //사원목록
    const [editMode, setEditMode] = useState(false); //등록, 수정 구분
    const [errors, setErrors] = useState({
        employeeEmail:'※ 유효한 이메일 주소를 입력해주세요',
        employeePhone: '※ 정확한 핸드폰번호를 입력해주세요: - 제외',
        emergencyPhone:'※ 정확한 핸드폰번호를 입력해주세요: - 제외'
    }); //유효성검사 에러메세지
    const [deletePopup1Open, setDeletePopup1Open ] =useState(false);
    const [deletePopup2Open, setDeletePopup2Open ] =useState(false);


    //페이징
    const [totalPages, setTotalPages] = useState(1);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize] = useState(10);

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
        departmentName: '',
        employeePhotoPath:''
    });

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        //유효성검사
        const newErrors = {};
        Object.keys(validators).forEach(field => {
            const errorMessage = validators[field](newEmployee[field]);
            if (errorMessage) {
                newErrors[field] = errorMessage;
            }
        });

        // 유효성 검사 통과 여부에 따라 폼 제출
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors); // 오류가 있을 경우 상태 업데이트
            return; // 폼 제출 중단
        }

        setErrors({
            employeeEmail:'※ 유효한 이메일 주소를 입력해주세요',
            employeePhone: '※ 정확한 핸드폰번호를 입력해주세요: - 제외',
            emergencyPhone:'※ 정확한 핸드폰번호를 입력해주세요: - 제외'
        });
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
        formData.append("employeePhotoPath",newEmployee.employeePhotoPath)

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
                departmentName: '',
                employeePhotoPath:''
            });
            console.log('폼 제출 완료 및 직원 생성:', response.data);
            fetchEmployees();
            setPhotoThumbnail("../../common/img/default.png");
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
    };

    const handleDepartmentSelect = (departmentNo, departmentName) => {
        setNewEmployee(prev => ({...prev, departmentNo: departmentNo, departmentName: departmentName}));
        newEmployee.positionName='';
        newEmployee.positionNo='';
    };

    const handlePositionSelect = (positionNo, positionName) => {
        setNewEmployee(prev => ({...prev, positionNo: positionNo, positionName: positionName}));
    };

    const handleEmployeeDelete = async () => {
        try {
            await axios.delete("http://localhost:8181/hrm/updateResignEmployee", {
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


    // 유효성 검사 함수들
    const validators = {
        employeeEmail: (value) => {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailRegex.test(value) ? '' : '※ 유효한 이메일 주소를 입력해주세요.';
        },
        employeePhone: (value) => {
            const phoneRegex =/^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/;
            return phoneRegex.test(value) ? '' : '※ 정확한 핸드폰번호를 입력해주세요: - 제외';
        },
        emergencyPhone:(value) => {
            const phoneRegex =/^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/;
            return phoneRegex.test(value) ? '' : '※ 정확한 핸드폰번호를 입력해주세요: - 제외';
        }
    };

    //사원 제출및 수정 값 변경
    const handleFormChange = (e) => {
        const { name, value } = e.target;

        // 해당 필드의 유효성 검사를 실행
        const errorMessage = validators[name] ? validators[name](value) : '';
        setErrors(prevErrors => ({
            ...prevErrors,
            [name]: errorMessage
        }));


        // departmentNo가 변경될 경우 positionNo와 positionName을 빈 문자열로 설정
        if (name === "departmentNo") {
            setNewEmployee(prev => ({
                ...prev,
                [name]: value,
                positionNo: "",       // positionNo을 빈 문자열로 설정
                positionName: ""      // positionName을 빈 문자열로 설정
            }));
        } else {
            setNewEmployee(prev => ({ ...prev, [name]: value }));
        }
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



    const handleCheckboxChange = (employee) => {
        setSelectedEmployees((prevSelected) =>
            prevSelected.includes(employee)
                ? prevSelected.filter((emp) => emp.employeeNo !== employee.employeeNo)
                : [...prevSelected, employee]
        );
    };
    const handleAllCheckboxChange = () => {
        if (isAllChecked) {
            setSelectedEmployees([]);
        } else {
            setSelectedEmployees(employees);
        }
        setIsAllChecked(!isAllChecked);
    };

    const handleImageChange = (e) => { //사진 미리보기 
        const {files} = e.target;
        setNewEmployee(prev => ({...prev, employeePhotoPath: "change"}));
        const thumbnail = files[0];
        setEmployeePhoto(thumbnail) //사진 전송용 설정
        const reader = new FileReader();
        reader.readAsDataURL(thumbnail);
        reader.onloadend = () => {
            setPhotoThumbnail(reader.result);
        }
    };

    const handleRetirementClick = () => { //퇴직직원 포함여부
        if (selectedEmployees.length === 0) {
            alert('선택한 직원이 없습니다.');
            setDeletePopup1Open(false);
            return; // 더 이상 진행하지 않도록 함수 종료
        }

        // selectedEmployees 중 employeeStatus가 'N'인 직원이 있는지 확인
        const hasInactiveEmployees = selectedEmployees.some(employee => employee.employeeStatus === 'n');

        if (hasInactiveEmployees) {
            alert('이미 퇴직한 직원이 포함되어 있습니다.');
            setDeletePopup1Open(false);
        }else {
            // 조건이 맞으면 삭제 팝업 열기
            setDeletePopup1Open(true);
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
            const response = await axios.get("http://localhost:8181/hrm/getActiveDepartments");
            setDepartments(response.data);
        } catch (err) {
            console.error('Error fetching departments:', err);
        }
    };
    // 행 클릭 핸들러
    const handleRowClick = (employee) => {
        setErrors({});
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
        setNewEmployee(employee); // 선택된 사원의 데이터로 초기화
        if(employee.employeePhotoPath !=='default'){
            setPhotoThumbnail(`http://localhost:8181/hrm/images/${employee.employeePhotoPath}`)
        }
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

    const handleCSVExport = (e) => {
        if (selectedEmployees.length === 0) {
            e.preventDefault();  // CSV 다운로드를 막음
            alert("선택된 직원이 없습니다.");
        }
    };
    //excel 내보내기
    const getExportData = () => {
        return selectedEmployees.map(employee => {
            return {
                입사일자: employee.employeeHiredate,
                사원번호: employee.employeeNo,
                성명: employee.employeeName,
                부서명: employee.departmentName,
                직급: employee.positionName,
                생일: employee.employeeBirth,
                전화번호: employee.employeePhone,
                비상연락처: employee.emergencyPhone,
                이메일: employee.employeeEmail,
                주소: employee.employeeAddress,
                상세주소: employee.employeeAddressDetail,
                은행: employee.bankName,
                계좌번호: employee.accountNumber,
                계좌주: employee.accountHolder,
                재직여부: employee.employeeStatus === 'y' ? '재직' : '퇴직'
            };
        });
    };

    useEffect(() => {
        fetchEmployees();
    }, [currentPage]);
    useEffect(() => {
        fetchPositions();
        fetchDepartments();
        fetchBanks();
    }, []);

    return (
        <main className="wrapper">

            <div className="text-wrap">
                <p>사원조회</p>
                <p>❉ 조회 또는 수정을 원하시면 원하는 항목을 선택해주세요. </p>
            </div>

            <div className="order-title">
                <div className="filter-content">
                    <div className="filter-main">
                        <h3>상세내역검색</h3>
                        <button className="filter-button" onClick={handleSearchBtn}>검색하기</button>
                        <button className="filter-button" onClick={() => {
                            fetchEmployees();
                            setSearch({ // 상태 초기화
                                employeeNo: '',
                                employeeName: '',
                                departmentNo: '',
                                employeePhone: '',
                                positionNo: '',
                                employeeStatus: ''
                            });
                        }}>전체보기</button>
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
                        <th className="checkboxColumn">
                            <input
                                type="checkbox"
                                id="selectAllCheckbox"
                                checked={isAllChecked}
                                onChange={handleAllCheckboxChange}
                            />
                            <label htmlFor="selectAllCheckbox"></label>
                        </th>
                        <th style={{minWidth: "130px"}}>
                            입사일자
                        </th>
                        <th  style={{minWidth: "110px"}}>
                            직원번호
                        </th>
                        <th style={{minWidth: "155px"}}>
                            성명
                        </th>
                        <th style={{minWidth: "155px"}}>
                            부서명
                        </th>
                        <th style={{minWidth: "105px"}}>
                            직급
                        </th>
                        <th style={{minWidth: "155px"}}>
                            전화번호
                        </th>
                        <th style={{minWidth: "170px"}}>
                            이메일
                        </th>
                        <th style={{minWidth: "110px"}}>
                            재직여부
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {employees && employees.length > 0 ? employees.map(employee => (
                        employee ? (
                            <tr key={employee.employeeNo} className="product_list" onClick={() => handleRowClick(employee)}
                                style={
                                    selectedEmployees.includes(employee)
                                        ? { backgroundColor: '#f7f5f2' } // 선택된 경우에만 배경색
                                        : undefined // 선택되지 않은 경우 스타일 적용 안 함
                                }
                            >
                                <td className="checkboxColumn" onClick={(e) => e.stopPropagation()}>
                                    <input
                                        type="checkbox"
                                        className="employeeCheckbox"
                                        id={`check${employee.employeeNo}`}
                                        checked={selectedEmployees.includes(employee)}
                                        onChange={() => handleCheckboxChange(employee)}
                                    />
                                    <label htmlFor={`check${employee.employeeNo}`}></label>
                                </td>
                                <td style={{minWidth: "130px"}}>{employee.employeeHiredate}</td>
                                <td style={{minWidth: "110px"}}>{employee.employeeNo}</td>
                                <td style={{minWidth: "155px"}}>{employee.employeeName}</td>
                                <td style={{minWidth: "155px"}}>{employee.departmentName}</td>
                                <td style={{minWidth: "105px"}}>{employee.positionName}</td>
                                <td style={{minWidth: "155px"}}>{employee.employeePhone}</td>
                                <td style={{minWidth: "170px"}}>{employee.employeeEmail}</td>
                                <td style={{minWidth: "110px"}}>{employee.employeeStatus === 'y' ? '재직' : '퇴직'}</td>
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

            <div className="wrapper-footer">
                <div className="flex" style={{justifyContent:"space-between"}}>
                    <button>
                        <CSVLink
                            data={getExportData()}
                            filename="selected_employees.csv"
                            className="btn"
                            target="_blank"
                            onClick={handleCSVExport}
                        >
                            Excel 내보내기
                        </CSVLink>
                    </button>
                    <div>
                        <button className="btn" style={{marginRight: "6px"}}
                                onClick={handleRetirementClick}>선택퇴직
                        </button>
                        <button className="btn" onClick={() => {
                            setShowPopup(true);
                            openRegistrationPopup();
                        }}>신규등록
                        </button>
                    </div>

                </div>
                <div className="flex" style={{justifyContent:"center"}}>
                    <button onClick={handlePrevPages} disabled={currentPage === 1} className="page-btn">
                        &lt;&lt;
                    </button>
                    {visiblePages.map(page => (
                        <button
                            key={page}
                            onClick={() => handlePageChange(page)}
                            className={`page-btn ${page === currentPage ? 'active' : ''}`}
                        >
                            {page}
                        </button>
                    ))}
                    <button onClick={handleNextPages} disabled={currentPage >= totalPages} className="page-btn">
                        &gt;&gt;
                    </button>
                </div>
            </div>

            {showPopup &&
                <div className="popup" id="contractPopup">
                    <Draggable nodeRef={nodeRef} onStart={handleStart} positionOffset={{x: '-50%', y: '-50%'}}>
                        <div className="popup-content" ref={nodeRef} id="draggablePopup" style={{width: "1150px"}}>
                            <div className="popup-header" id="popupHeader">
                                <span>{editMode ? '사원 수정' : '사원 등록'}</span>
                            </div>
                            <form onSubmit={handleFormSubmit} className="popup-form">
                                <table>
                                    <tbody>
                                    <tr className="left-row">
                                        <td rowSpan="2" colSpan="2">
                                        <p>사원사진</p>
                                            <input type="file" id="fileInput" accept="image/*" style={{display: "none"}}
                                                   onChange={handleImageChange}/>
                                            <label htmlFor="fileInput" className="btn attach-file" style={{position:"absolute", top:"70px", left:"380px", width:"100px",height:"100px"}}></label>
                                        </td>
                                        <td rowSpan="2" colSpan="2">
                                            <div className="profile-picture">
                                                <img src={PhotoThumbnail} alt="Profile Picture" id="profileImg"/>
                                            </div>
                                        </td>

                                        <td colSpan="2">
                                            <label htmlFor="employeeName">사원번호</label>

                                        </td>
                                        <td colSpan="2">
                                            <input type="text" id="employeeNo" name="employeeNo"
                                                   className="input-form"
                                                   value={newEmployee.employeeNo}
                                                   readOnly
                                                   onChange={handleFormChange}/>
                                        </td>

                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="employeeName">이름</label>

                                        </td>
                                        <td colSpan="2">
                                            <input type="text" id="employeeName" name="employeeName"
                                                   className="input-form"
                                                   value={newEmployee.employeeName}
                                                   onChange={handleFormChange}
                                                   autoComplete="off"
                                                   required
                                            />
                                        </td>

                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="employeeEmail">이메일</label>
                                        </td>
                                        <td colSpan="2">
                                            <input
                                                type="text"
                                                id="employeeEmail"
                                                name="employeeEmail"
                                                className="input-form"
                                                value={newEmployee.employeeEmail}
                                                onChange={handleFormChange}
                                            />
                                            {errors.employeeEmail && <p style={{ color: 'red', fontSize: '13px', textAlign:"left" }}>{errors.employeeEmail}</p>}
                                        </td>
                                        <td colSpan="2">
                                            <label htmlFor="department">부서번호</label>
                                        </td>
                                        <td colSpan="2">
                                            <select name="departmentNo" value={newEmployee.departmentNo} required
                                                    onChange={(e) => handleDepartmentSelect(e.target.value, e.target.selectedOptions[0].text)}>
                                                <option value="" disabled hidden>부서 선택</option>
                                                {departments.map(department => (
                                                    <option key={department.departmentNo}
                                                            value={department.departmentNo}>
                                                        {department.departmentName}
                                                    </option>
                                                ))}
                                            </select>
                                        </td>
                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="positionNo">직급</label>

                                        </td>
                                        <td colSpan="2">
                                            <select name="positionNo" value={newEmployee.positionNo} required
                                                    onChange={(e) => handlePositionSelect(e.target.value, e.target.selectedOptions[0].text)}>
                                                <option value="" disabled hidden>직급 선택</option>
                                                {positions
                                                    .filter(position =>
                                                        newEmployee.departmentName === "경영팀" || position.positionNo >= 8
                                                    )
                                                    .map(position => (
                                                    <option key={position.positionNo}
                                                            value={position.positionNo}>
                                                        {position.positionName}
                                                    </option>
                                                ))}
                                            </select>
                                        </td>
                                        <td colSpan="2">
                                            <label htmlFor="employeeHiredate">입사일</label>

                                        </td>
                                        <td colSpan="2">
                                            <input type="date" id="employeeHiredate" name="employeeHiredate"
                                                   className="input-form" required
                                                   value={newEmployee.employeeHiredate} onChange={handleFormChange}/>
                                        </td>
                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="employeePhone">전화번호</label>

                                        </td>
                                        <td colSpan="2">
                                            <input type="text" id="employeePhone" name="employeePhone"
                                                   className="input-form"
                                                   value={newEmployee.employeePhone}
                                                   onChange={handleFormChange}
                                            />
                                            {errors.employeePhone && <p style={{ color: 'red' , fontSize: '13px', textAlign:"left" }}>{errors.employeePhone}</p>}
                                        </td>
                                        <td colSpan="2">
                                            <label htmlFor="emergencyPhone">비상연락처</label>
                                        </td>
                                        <td colSpan="2">
                                            <input type="tel" id="emergencyPhone" name="emergencyPhone"
                                                   className="input-form"
                                                   value={newEmployee.emergencyPhone} onChange={handleFormChange}
                                            />
                                            {errors.emergencyPhone && <p style={{ color: 'red', fontSize: '13px', textAlign:"left" }}>{errors.emergencyPhone}</p>}
                                        </td>
                                    </tr>
                                    <tr className="left-row">
                                        <td colSpan="2">
                                            <label htmlFor="employeeBirth">생년월일</label>
                                        </td>
                                        <td colSpan="2">
                                            <input type="date" id="employeeBirth" name="employeeBirth"
                                                   className="input-form"
                                                   value={newEmployee.employeeBirth} onChange={handleFormChange}
                                                   required
                                            />
                                        </td>
                                        <td colSpan="2">
                                            <label htmlFor="accountHolder">계좌주</label>
                                        </td>
                                        <td colSpan="2">
                                            <input type="text" id="accountHolder" name="accountHolder" placeholder="계좌주" className="input-form"
                                                   value={newEmployee.accountHolder} onChange={handleFormChange}
                                                   autoComplete="off"
                                                   required
                                            />
                                        </td>
                                    </tr>
                                    <tr className="left-row">
                                    <td colSpan="2">
                                            <label htmlFor="accountBank">은행</label>
                                        </td>
                                        <td colSpan="2">
                                            <select name="bankNo" value={newEmployee.bankNo} required
                                                    onChange={(e) => handleBankSelect(e.target.value, e.target.selectedOptions[0].text)}>
                                                <option value="" disabled hidden>은행 선택</option>
                                                {banks.map(bank => (
                                                    <option key={bank.bankNo}
                                                            value={bank.bankNo}>
                                                        {bank.bankName}
                                                    </option>
                                                ))}
                                            </select>
                                        </td>
                                        <td colSpan="2">
                                            <label htmlFor="employeeEmail">계좌번호</label>
                                        </td>
                                        <td colSpan="2">
                                            <input type="text" id="accountNumber" name="accountNumber"
                                                   className="input-form"
                                                   placeholder="계좌번호" value={newEmployee.accountNumber}
                                                   onChange={handleFormChange}
                                                   autoComplete="off"
                                                   required
                                            />
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
                                                className="select-input-form"
                                                onClick={() => setShowMap(true)} // 주소 검색을 위한 지도 또는 검색창 표시
                                                readOnly
                                                required
                                            />
                                        </td>
                                        <td colSpan="2">
                                            <label htmlFor="employeeAddressDetail">상세주소</label>
                                        </td>
                                        <td colSpan="2">
                                            <input type="text" id="employeeAddressDetail" name="employeeAddressDetail"
                                                   className="input-form"
                                                   placeholder="상세주소를 입력하세요" value={newEmployee.employeeAddressDetail}
                                                   onChange={handleFormChange}
                                                   autoComplete="off"
                                                   required
                                            />
                                        </td>
                                    </tr>
                                    </tbody>

                                </table>
                                <div className="popup-buttons">
                                    <button type="submit" className="btn" style={{margin: "5px"}}>저장</button>
                                    <button type="button" className="btn close"
                                            onClick={() => {
                                                setShowPopup(false);
                                                setPhotoThumbnail("../../common/img/default.png");
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
                                                setErrors({
                                                    employeeEmail:'※ 유효한 이메일 주소를 입력해주세요',
                                                    employeePhone: '※ 정확한 핸드폰번호를 입력해주세요: - 제외',
                                                    emergencyPhone:'※ 정확한 핸드폰번호를 입력해주세요: - 제외'
                                                });
                                            }}>닫기
                                    </button>
                                </div>
                            </form>
                        </div>
                    </Draggable>
                </div>
            }
            {/* 도로명주소 입력 */}
            {showMap && (
                <div>
                    <button className="map-close-btn" onClick={() => setShowMap(false)}>닫기</button>
                    <DaumPostcode
                        onComplete={completeMap}
                        onClose={closeMap}
                        style={postCodeStyle}
                    />
                </div>
            )}

            {/* 첫 번째 팝업: 삭제 여부 확인 */}
            {deletePopup1Open && (
                <div className="popup">
                    <Draggable nodeRef={nodeRef} onStart={handleStart} positionOffset={{x: '-50%', y: '-50%'}}>
                        <div className="popup-content" ref={nodeRef}>
                            <div className="popup-header">
                                <span>퇴직 여부 확인</span>
                            </div>
                            <div className="popup-body">
                                <table className="employee-table">
                                    <thead>
                                    <tr>
                                        <th style={{minWidth: "110px"}}>사원번호</th>
                                        <th style={{minWidth: "155px"}}>이름</th>
                                        <th style={{minWidth: "155px"}}>부서명</th>
                                        <th style={{minWidth: "105px"}}>직급</th>
                                    </tr>
                                    </thead>
                                    <tbody id="employeeDataTable">
                                    {selectedEmployees.map(employee => (
                                        <tr>
                                            <td>{employee.employeeNo}</td>
                                            <td>{employee.employeeName}</td>
                                            <td>{employee.departmentName}</td>
                                            <td>{employee.positionName}</td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                            <div className="popup-buttons">
                                <button type="button" className="btn" style={{margin: "5px"}} onClick={()=>{
                                    setDeletePopup1Open(false);
                                    setDeletePopup2Open(true);
                                }}>다음</button>
                                <button type="button" className="btn" onClick={() =>{
                                    setSelectedEmployees([]);
                                    setDeletePopup1Open(false);
                                }}>닫기</button>
                            </div>
                        </div>
                    </Draggable>
                </div>
            )}

            {/* 두 번째 팝업: 삭제 경고 */}
            {deletePopup2Open && (
                <div className="popup">
                    <Draggable nodeRef={nodeRef} onStart={handleStart} positionOffset={{x: '-50%', y: '-50%'}}>
                        <div className="popup-content" style={{width: "500px"}}>
                            <div className="popup-header">
                                <span>퇴직 확인</span>
                            </div>
                            <div className="popup-body">
                                <p>선택한 사원을 정말로 퇴직 처리하시겠습니까?</p>
                            </div>
                            <div className="popup-buttons">
                                <button type="button" className="btn" style={{margin: "5px"}} onClick={() =>{
                                    handleEmployeeDelete();
                                    setDeletePopup2Open(false);
                                }}>퇴직</button>
                                <button type="button" className="btn" onClick={() =>{
                                    setSelectedEmployees([]);
                                    setDeletePopup2Open(false);
                                }}>닫기</button>
                            </div>
                        </div>
                    </Draggable>
                </div>
            )}
        </main>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Employee/>
);
