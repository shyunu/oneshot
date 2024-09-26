import Draggable from "react-draggable";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ReactDOM from 'react-dom/client';
import './department.css';
import './popup.css';
import {CSVLink} from "react-csv";

function Department() {
    // 검색 필터를 위한 상태 관리
    const [filters, setFilters] = useState({
        departmentNo: '',
        departmentName: '',
        availableMenu: '',
        departmentState: '', // 활성/비활성 상태
    });

    const resetForm = () => {
        setFormValues({
            departmentNo: '',
            departmentName: '',
            menuNo: [],
            departmentState: 'Y',
        });
        setErrors({
            departmentNo: false,
            departmentName: false,
        });
        setSubmitted(false);  // 폼 제출 상태도 초기화
    };

useEffect(() => {
    // 필터된 부서 목록을 확인하는 콘솔 로그
    console.log('Filtered Departments:', filteredDepartments);
    console.log('Total Items:', totalItems);
    console.log('Total Pages:', totalPages);
    console.log('Current Departments:', currentDepartments);
}, [filteredDepartments, currentPage]);

    const [currentPage, setCurrentPage] = useState(1); // 현재 페이지
    const itemsPerPage = 10;  // 페이지당 항목 수
    const pagesPerBlock = 5; // 한 번에 보여줄 페이지 개수
    const totalItems = (filteredDepartments || []).length;  // 필터링된 데이터의 총 개수
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentDepartments = (filteredDepartments || []).slice(indexOfFirstItem, indexOfLastItem);  // 현재 페이지의 부서 목록


    const totalPages = Math.ceil(totalItems / itemsPerPage);  // 총 페이지 수
    const totalBlocks = Math.ceil(totalPages / pagesPerBlock);
    const currentBlock = Math.ceil(currentPage / pagesPerBlock); // 현재 블록

    const startPage = (currentBlock - 1) * pagesPerBlock + 1; // 현재 블록의 첫 페이지
    const endPage = Math.min(currentBlock * pagesPerBlock, totalPages); // 현재 블록의 마지막 페이지

    // 페이지네이션 버튼 생성
    const paginationButtons = totalPages > 1 && Array.from({ length: totalPages }, (_, index) => (
        <button
            key={index + 1}
            onClick={() => setCurrentPage(index + 1)}
            className={currentPage === index + 1 ? 'active' : ''}
        >
            {index + 1}
        </button>
    ));

    // 다음 페이지 핸들러
    const handleNextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    };

    // 이전 페이지 핸들러
    const handlePrevPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    const [modalOpen, setModalOpen] = useState(false); // 부서 등록 팝업
    const [departmentDetailPopupOpen, setDepartmentDetailPopupOpen] = useState(false); // 부서 상세보기 팝업
    const [employeePopupOpen, setEmployeePopupOpen] = useState(false); // 사원 목록 팝업
    const [menuList, setMenuList] = useState([]); // 메뉴 목록 상태
    const [departments, setDepartments] = useState([]); // 전체 부서 데이터를 저장하는 상태
    const [filteredDepartments, setFilteredDepartments] = useState([]); // 필터된 데이터를 저장하는 상태
    const [formValues, setFormValues] = useState({
        departmentNo: '',
        departmentName: '',
        menuNo: [], // 선택된 메뉴들을 저장하는 배열
        departmentState: 'Y',
    });
    const [employees, setEmployees] = useState([]); // 부서 내 사원 목록
    const [selectedDepartment, setSelectedDepartment] = useState(null); // 선택된 부서 정보
    const [selectedDepartments, setSelectedDepartments] = useState([]); // 선택된 부서 목록
    const [isAllChecked, setIsAllChecked] = useState(false); // 전체 선택 체크박스 상태
    const [statusPopupOpen, setStatusPopupOpen] = useState(false); // 활성/비활성 상태 팝업
    const [selectedStatus, setSelectedStatus] = useState(''); // 선택된 활성/비활성 상태
    const [errors, setErrors] = useState({
        departmentNo: false,
        departmentName: false,
    });
    const [submitted, setSubmitted] = useState(false); // 폼 제출 상태

    const [lastDepartmentNo, setLastDepartmentNo] = useState(null);  // 마지막 부서번호 상태 추가

    // 페이지 변경 함수
    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    // 다음 블록으로 넘어가기 위한 함수
    const handleNextBlock = () => {
        if (currentBlock < totalBlocks) {
            setCurrentPage(startPage + pagesPerBlock); // 다음 블록으로 이동
        }
    };

    // 이전 블록으로 돌아가기 위한 함수
    const handlePrevBlock = () => {
        if (currentBlock > 1) {
            setCurrentPage(startPage - pagesPerBlock); // 이전 블록으로 이동
        }
    };


    // 팝업이 열릴 때 마지막 부서번호를 가져오는 API 호출
    useEffect(() => {
        if (modalOpen) {
            axios.get('http://localhost:8181/hrm/getLastDepartmentNo')
                .then(response => {
                    const nextDepartmentNo = response.data + 1;  // 마지막 부서번호 + 1
                    setFormValues(prevValues => ({
                        ...prevValues,
                        departmentNo: nextDepartmentNo
                    }));
                })
                .catch(error => {
                    console.error('Error fetching last department number:', error);
                });
        }
    }, [modalOpen]);

    // 필터 초기화 함수
    const handleResetFilters = () => {
        setFilters({
            departmentNo: '',
            departmentName: '',
            availableMenu: '',
            departmentState: '',
        });
        setFilteredDepartments(departments);  // 필터 초기화 후 전체 부서 데이터를 설정
    };



    // 필터 값을 변경하는 핸들러
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFilters({
            ...filters,
            [name]: value,
        });
    };


    const handleChange = (e) => {
        const { id, value } = e.target;
        setFormValues((prevState) => ({
            ...prevState,
            [id]: value,
        }));
    };

    // 메뉴 변경 시의 처리
    const handleMenuChange = (menuNo) => {
        setFormValues((prevValues) => {
            const isSelected = prevValues.menuNo.includes(menuNo);
            if (isSelected) {
                // 선택된 경우 메뉴 제거
                return { ...prevValues, menuNo: prevValues.menuNo.filter((no) => no !== menuNo) };
            } else {
                // 선택되지 않은 경우 메뉴 추가
                return { ...prevValues, menuNo: [...prevValues.menuNo, menuNo] };
            }
        });
    };



    // 정렬 상태 관리
    const [sortConfig, setSortConfig] = useState({ key: 'departmentNo', direction: 'ascending' });

    useEffect(() => {
        fetchDepartments();
        fetchMenus();
    }, []);



    // 활성화/비활성화 상태 변경 처리
    const handleStatusConfirm = async () => {
        try {
            const requests = selectedDepartments.map(departmentNo => ({
                departmentNo,
                departmentState: selectedStatus,  // 선택된 상태 (Y 또는 N)
            }));

            await axios.put("http://localhost:8181/hrm/updateDepartmentState", requests, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            // 상태 변경 후 부서 목록 새로고침
            setStatusPopupOpen(false);
            fetchDepartments();
            setSelectedDepartments([]);  // 선택된 부서 목록 초기화
            setIsAllChecked(false);  // 전체 선택 상태 해제
        } catch (err) {
            console.error('부서 상태 업데이트 중 오류 발생:', err);
        }
    };


    // 부서 목록을 가져오는 함수
    const fetchDepartments = async () => {
        try {
            const response = await axios.get("http://localhost:8181/hrm/getDepartment");
            console.log('Fetched departments:', response.data); // 데이터를 로그로 출력

            if (response.data.length > 0) {
                setDepartments(response.data);  // 전체 부서 데이터를 저장
                setFilteredDepartments(response.data);  // 초기 상태로 필터된 부서도 전체 부서로 설정
            } else {
                console.log('No departments found');
                setDepartments([]); // 부서 데이터가 없을 경우 빈 배열
                setFilteredDepartments([]); // 필터된 데이터도 빈 배열
            }
        } catch (err) {
            console.error('Error fetching departments:', err);
            setDepartments([]);  // 에러 발생 시 빈 배열로 초기화
            setFilteredDepartments([]);
        }
    };


    // 검색 필터를 적용하는 함수
    const handleSearch = () => {
        const filtered = departments.filter(department => {
            const matchDepartmentNo = filters.departmentNo
                ? department.departmentNo.toString().includes(filters.departmentNo)
                : true;
            const matchDepartmentName = filters.departmentName
                ? department.departmentName.toString().includes(filters.departmentName)
                : true;
            const matchAvailableMenu = filters.availableMenu === 'none'
                ? department.menuNo.length === 0 // 메뉴 없음 처리
                : filters.availableMenu
                ? department.menuNo.includes(parseInt(filters.availableMenu))
                : true;
            const matchDepartmentState = filters.departmentState
                ? department.departmentState === filters.departmentState
                : true;

            return matchDepartmentNo && matchDepartmentName && matchAvailableMenu && matchDepartmentState;
        });

        // 필터링된 결과를 상태로 저장
        setFilteredDepartments(filtered);
    };

    // 페이지가 처음 로드될 때 전체 데이터를 불러옴
    useEffect(() => {
        fetchDepartments();
    }, []);
    // 필터링된 데이터가 변경될 때마다 확인
    useEffect(() => {
        console.log('Filtered Departments:', filteredDepartments);
    }, [filteredDepartments]);

    // 메뉴 목록을 가져오는 함수
    const fetchMenus = async () => {
        try {
            const response = await axios.get("http://localhost:8181/hrm/getMenus");

            // 데이터가 제대로 오는지 확인하기 위한 로그
            console.log("Fetched menus:", response.data);

            setMenuList(response.data);
        } catch (err) {
            console.error('Error fetching menus:', err);
            setMenuList([]); // 에러 발생 시 빈 배열 설정
        }
    };

    // 페이지가 로드될 때 메뉴 목록을 불러옵니다.
    useEffect(() => {
        fetchMenus();
    }, []);

    // 부서 선택 시 호출되는 함수
    const handleCheckboxChange = (department) => {
        setSelectedDepartments((prevSelected) =>
            prevSelected.includes(department)
                ? prevSelected.filter((dep) => dep.departmentNo !== department.departmentNo)  // 선택된 부서가 이미 있으면 제거
                : [...prevSelected, department]  // 선택되지 않은 부서는 추가
        );
    };

    // 전체 체크박스 선택/해제 처리
    const handleAllCheckboxChange = () => {
        if (isAllChecked) {
            // 전체 해제: 선택된 부서 목록을 빈 배열로 설정
            setSelectedDepartments([]);
        } else {
            // 전체 선택: 모든 부서의 departmentNo 값을 선택된 부서 목록에 추가
            setSelectedDepartments(departments);
        }
        // 전체 선택 상태 반전
        setIsAllChecked(!isAllChecked);
    };

    useEffect(() => {
        // 모든 부서가 선택되면 전체 선택 체크박스를 체크, 그렇지 않으면 해제
        if (selectedDepartments.length === departments.length && departments.length > 0) {
            setIsAllChecked(true);
        } else {
            setIsAllChecked(false);
        }
    }, [selectedDepartments, departments]);



    // 부서를 클릭했을 때 부서 정보를 불러오고 모달창을 여는 함수
    const handleDepartmentClick = (department) => {
        setSelectedDepartment(department);
        setFormValues({
            departmentNo: department.departmentNo,
            oldDepartmentNo: department.departmentNo, // 기존 부서번호도 저장
            departmentName: department.departmentName,
            menuNo: department.menuNo || [],
            departmentState: department.departmentState,
        });
        console.log(formValues);
        setDepartmentDetailPopupOpen(true);
    };


    // 부서 저장 함수
    const handleSave = async (e) => {
        e.preventDefault();
        setSubmitted(true);

        // 부서명이 공백인지 확인하는 유효성 검사
        if (!formValues.departmentName.trim()) {
            setErrors({
                ...errors,
                departmentName: true,
            });
            return; // 공백일 경우 저장 중지
        } else {
            setErrors({
                ...errors,
                departmentName: false,
            });
        }

        try {
            const departmentNo = parseInt(formValues.departmentNo);
            const requestData = {
                departmentNo,
                departmentName: formValues.departmentName.trim(),
                departmentState: formValues.departmentState,
                menuNo: formValues.menuNo,
            };

            const response = await axios.put("http://localhost:8181/hrm/updateDepartmentDetails", requestData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.status === 200) {
                alert("부서 정보가 수정되었습니다.");
                setDepartmentDetailPopupOpen(false);
                fetchDepartments();  // 수정 후 부서 목록 새로고침
            } else {
                alert("부서 정보 수정에 실패했습니다.");
            }
        } catch (error) {
            console.error("Error updating department:", error);
            alert("부서 정보 수정 중 오류가 발생했습니다.");
        }
    };


    const departmentVO = {
        departmentNo: parseInt(formValues.departmentNo),
        departmentName: formValues.departmentName,
        menuNo: formValues.menuNo.join(','),  // 배열을 쉼표로 구분된 문자열로 변환
    };

    // 등록 버튼 클릭 시 모달 열기
    const handleOpenRegistrationPopup = () => {
        resetForm();  // 등록 팝업 열기 전에 입력 필드 초기화
        setModalOpen(true);
    };

    // 부서 등록 폼 제출 처리
    const formSubmit = async (e) => {
        e.preventDefault();
        setSubmitted(true);

        // 부서명이 공백인지 확인하는 유효성 검사
        if (!formValues.departmentName.trim()) {
            setErrors({
                departmentName: true,  // 공백일 경우 필수 항목 오류
                departmentNameOnlyNumbers: false,  // 공백일 때 숫자만 입력 오류 표시 안함
                departmentNameDuplicate: false, // 공백일 때 중복 오류도 표시 안함
            });
            return; // 공백일 경우 등록 중지
        }

        // 부서명이 숫자로만 입력되었는지 확인하는 유효성 검사
        const isOnlyNumbers = /^[0-9]+$/.test(formValues.departmentName);
        if (isOnlyNumbers) {
            setErrors({
                departmentName: false,  // 숫자만 있을 때 필수 항목 오류 표시 안함
                departmentNameOnlyNumbers: true,  // 숫자만 입력된 경우 오류
                departmentNameDuplicate: false, // 숫자만 있을 때 중복 오류 표시 안함
            });
            return; // 숫자로만 입력된 경우 등록 중지
        }

        // 유효성 검사 통과 시 오류 메시지 초기화
        setErrors({
            departmentName: false,
            departmentNameOnlyNumbers: false,
            departmentNameDuplicate: false,
        });

        // 부서명 중복 확인 로직
        try {
            const duplicateCheckResponse = await axios.get(`http://localhost:8181/hrm/checkDuplicateDepartmentName/${formValues.departmentName}`);
            if (duplicateCheckResponse.data) {
                setErrors({
                    departmentName: false,  // 중복 오류가 발생할 때는 다른 오류 표시 안함
                    departmentNameOnlyNumbers: false,
                    departmentNameDuplicate: true, // 중복된 경우 오류
                });
                return; // 중복된 경우 등록 중지
            }
        } catch (error) {
            console.error('부서명 중복 확인 중 오류 발생:', error);
        }

        // 서버로 POST 요청
        try {
            const departmentVO = {
                departmentNo: parseInt(formValues.departmentNo),
                departmentName: formValues.departmentName,
                menuNo: formValues.menuNo
            };
            const response = await axios.post("http://localhost:8181/hrm/registDepartment", departmentVO, {
                headers: { 'Content-Type': 'application/json' },
            });

            // 등록 후 부서 목록을 다시 불러오고, 입력 필드를 초기화
            fetchDepartments();
            setModalOpen(false);
            resetForm();  // 등록 후 입력 필드 초기화
            setSubmitted(false);
        } catch (err) {
            console.error('부서 등록 중 오류:', err);
        }
    };

    // 정렬 처리
    const requestSort = (key) => {
        let direction = 'ascending';
        if (sortConfig.key === key && sortConfig.direction === 'ascending') {
            direction = 'descending';
        }
        setSortConfig({ key, direction }); // 새로운 키와 방향을 설정
    };

    // 정렬 처리 - 기본값 빈 배열 추가
    const sortedDepartments = [...(filteredDepartments || [])].sort((a, b) => {
        let aValue = a[sortConfig.key];
        let bValue = b[sortConfig.key];

        // 부서 번호는 숫자이므로 비교 방식 처리
        if (sortConfig.key === 'departmentNo') {
            aValue = parseInt(aValue);
            bValue = parseInt(bValue);
        }

        // 부서 상태를 'Y'와 'N'으로 비교
        if (sortConfig.key === 'departmentState') {
            aValue = aValue === 'Y' ? 1 : 0;
            bValue = bValue === 'Y' ? 1 : 0;
        }

        // 정렬 방향에 따라 값 비교
        if (aValue < bValue) {
            return sortConfig.direction === 'ascending' ? -1 : 1;
        }
        if (aValue > bValue) {
            return sortConfig.direction === 'ascending' ? 1 : -1;
        }
        return 0;
    });



    // 직원 목록을 가져오는 함수
    const handleEmployeePopup = async () => {
        try {
            const response = await axios.get(`http://localhost:8181/hrm/getEmployees/${selectedDepartment.departmentNo}`);
            setEmployees(response.data || []); // 가져온 직원 데이터를 상태에 저장
            setEmployeePopupOpen(true); // 직원 팝업 열기
        } catch (error) {
            console.error("Error fetching employees:", error);
            setEmployees([]); // 에러 발생 시 빈 배열로 설정
        }
    };

    //excel 내보내기
    const handleCSVExport = (e) => {
        if (selectedDepartments.length === 0) {
            e.preventDefault();  // CSV 다운로드를 막음
            alert("선택된 부서가 없습니다.");
        }
    };
    const getExportData = () => {
        return selectedDepartments.map(department => {
            return {
                부서번호: department.departmentNo,
                부서명: department.departmentName,
                사용여부: department.departmentState
            };
        });
    };


    return (
        <main className="wrapper">

                <div className="text-wrap">
                    <p>부서조회</p>
                    <p>❉ 조회 또는 수정을 원하시면 원하는 항목을 선택해주세요. </p>
                </div>

                <div className="order-title">
                    <div className="filter-content">
                            <div className="filter-main">
                                <h3>상세내역검색</h3>
                                <button className="filter-button" onClick={handleSearch}>검색하기</button>
                                <button className="filter-button" onClick={handleResetFilters}>전체보기</button>
                            </div>
                        <table>
                                <tr>
                                    <td><p>부서명</p></td>
                                    <td>
                                        <input type="text" name="departmentName" value={filters.departmentName}
                                               onChange={handleInputChange}/>
                                    </td>
                                    <td><p>부서번호</p></td>
                                    <td>
                                    <input type="number" name="departmentNo" value={filters.departmentNo} onChange={handleInputChange} />
                                    </td>
                                </tr>
                                <tr>
                                    <td><p>사용가능메뉴</p></td>
                                    <td>
                                        <select
                                          name="availableMenu"
                                          value={filters.availableMenu}
                                          onChange={handleInputChange}
                                        >
                                          <option value="">선택</option>
                                          <option value="none">메뉴 없음</option>
                                          {menuList.length > 0 &&
                                            menuList.map((menu) => (
                                              <option key={menu.menuNo} value={menu.menuNo}>
                                                {menu.menuName}
                                              </option>
                                            ))}
                                        </select>
                                    </td>
                                    <td><p>부서 상태</p></td>
                                    <td>
                                        <select name="departmentState" value={filters.departmentState} onChange={handleInputChange}>
                                            <option value="">전체</option>
                                            <option value="Y">활성</option>
                                            <option value="N">비활성</option>
                                        </select>
                                    </td>
                                </tr>
                        </table>
                    </div>
                </div>

                <article>
                  <table id="supplierTable">
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
                        <th className="departmentNumberColumn">부서번호</th>
                        <th className="departmentNameColumn">부서명</th>
                        <th>사용가능메뉴</th>
                        <th>사용여부</th>
                      </tr>
                    </thead>
                    <tbody>
                      {sortedDepartments.length > 0 ? (
                        sortedDepartments.map((department) => (
                          <tr key={department.departmentNo} className="product_list" onClick={() => handleDepartmentClick(department)}
                            style={
                                selectedDepartments.includes(department)
                                    ? { backgroundColor: '#f7f5f2' }
                                    : undefined
                              }
                          >
                            <td className="checkboxColumn" onClick={(e) => e.stopPropagation()}>
                              <input
                                type="checkbox"
                                className="departmentCheckbox"
                                id={`check${department.departmentNo}`}
                                checked={selectedDepartments.includes(department)}
                                onChange={() => handleCheckboxChange(department)}
                              />
                              <label htmlFor={`check${department.departmentNo}`}></label>
                            </td>
                            <td className="departmentNumberColumn">{department.departmentNo}</td>
                            <td className="departmentNameColumn">{department.departmentName}</td>
                            <td>
                              {department.menuNo && department.menuNo.length > 0
                                ? department.menuNo
                                    .map((menuNo) => {
                                      const menu = menuList.find((m) => m.menuNo === menuNo);
                                      return menu ? menu.menuName : '메뉴 없음';
                                    })
                                    .join(', ')
                                : '메뉴 없음'}
                            </td>
                            <td>{department.departmentState === 'Y' ? '활성' : '비활성'}</td>
                          </tr>
                        ))
                      ) : (
                        <tr>
                          <td colSpan="5">등록된 부서가 없습니다.</td>
                        </tr>
                      )}
                    </tbody>
                  </table>
                </article>


            <div className="buttons">
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
                <div className="regist-right" style={{justifyContent: "end", marginBottom: "20px"}}>
                    <button className="register" onClick={handleOpenRegistrationPopup}>등록</button>
                </div>
            </div>


            {/* 신규 등록 모달창 */}
            {modalOpen && (
                <div className="popup" id="contractPopup">
                    <Draggable positionOffset={{x: '-50%', y: '-50%'}}>
                        <div className="popup-content" id="draggablePopup">
                            <div className="popup-header" id="popupHeader">
                                <span>부서등록</span>
                            </div>
                            <form className="contract-form" onSubmit={formSubmit}>
                              <table>
                                <tbody>
                                  <tr className="left-row">
                                    <td colSpan="2">
                                      <label htmlFor="departmentNo">부서번호</label>
                                    </td>
                                    <td colSpan="2">
                                      <input
                                        type="text"
                                        id="departmentNo"
                                        value={formValues.departmentNo}
                                        style={{textAlign:"center"}}
                                        readOnly
                                      />
                                    </td>
                                    <td colSpan="2">
                                      <label htmlFor="departmentName">부서명</label>
                                    </td>
                                    <td colSpan="2">
                                        <input
                                            type="text"
                                            id="departmentName"
                                            value={formValues.departmentName}
                                            onChange={handleChange}
                                            placeholder="부서명"
                                            style={{textAlign: "center"}}
                                        />
                                        {/* 부서명이 비어있을 때의 오류 메시지 */}
                                        {submitted && errors.departmentName && (
                                            <p style={{ color: 'red', textAlign: 'center', fontSize: '13px' }}>
                                                ※ 부서명은 필수 항목입니다
                                            </p>
                                        )}
                                        {/* 부서명이 숫자로만 입력되었을 때의 오류 메시지 */}
                                        {submitted && errors.departmentNameOnlyNumbers && (
                                            <p style={{ color: 'red', textAlign: 'center', fontSize: '13px' }}>
                                                ※ 숫자만 입력할 수 없습니다
                                            </p>
                                        )}
                                        {/* 부서명이 중복되었을 때의 오류 메시지 */}
                                        {submitted && errors.departmentNameDuplicate && (
                                                <p style={{ color: 'red', textAlign: 'center', fontSize: '13px' }}>
                                                    ※ 이미 중복된 부서명이 있습니다
                                                </p>
                                        )}
                                    </td>

                                  </tr>
                                  <tr className="left-row">
                                    <td colSpan="2">
                                      <label>사용가능메뉴</label>
                                    </td>
                                    <td colSpan="6">
                                      <div className="departmentCheck">
                                        {menuList.length > 0 ? (
                                          menuList.map((menu) => (
                                            <div key={menu.menuNo} className="departmentPopupCheckboxWrapper">
                                              <input
                                                type="checkbox"
                                                id={`menu-${menu.menuNo}`}
                                                className="departmentPopupCheckbox"
                                                value={menu.menuNo}
                                                checked={formValues.menuNo.includes(menu.menuNo)}
                                                onChange={() => handleMenuChange(menu.menuNo)}
                                              />
                                              <label htmlFor={`menu-${menu.menuNo}`}>{menu.menuName}</label>
                                            </div>
                                          ))
                                        ) : (
                                          <p>사용할 수 있는 메뉴가 없습니다.</p>
                                        )}
                                      </div>
                                    </td>
                                  </tr>
                                </tbody>
                              </table>
                              <div className="popup-buttons">
                                <button type="submit" className="popup-btn modify">등록</button>
                                <button
                                  type="button"
                                  className="popup-btn close"
                                  onClick={() => {
                                    setModalOpen(false);  // 모달창 닫기
                                    resetForm();  // 입력 필드 초기화
                                  }}
                                >
                                  닫기
                                </button>
                              </div>
                            </form>
                          </div>
                        </Draggable>
                      </div>
                    )}


            {departmentDetailPopupOpen && (
              <div className="popup" id="departmentDetailPopup">
                <Draggable positionOffset={{x: '-50%', y: '-50%'}}>
                  <div className="popup-content">
                    <div className="popup-header">
                      <span>부서 상세 정보 수정</span>
                    </div>
                    <form className="contract-form" onSubmit={handleSave}>
                      <table className="popup-table">
                        <tbody>
                          <tr className="left-row">
                            <td colSpan="2">
                              <label htmlFor="departmentNo">부서번호</label>
                            </td>
                            <td colSpan="2">
                              <input
                                type="text"
                                id="departmentNo"
                                value={formValues.departmentNo}
                                style={{textAlign:"center"}}
                                readOnly // 부서번호는 수정 불가
                              />
                            </td>
                            <td colSpan="2">
                              <label htmlFor="departmentName">부서명</label>
                            </td>
                            <td colSpan="2">
                              <input
                                type="text"
                                id="departmentName"
                                value={formValues.departmentName}
                                onChange={(e) => setFormValues({
                                  ...formValues,
                                  departmentName: e.target.value
                                })}
                                style={{ textAlign: "center" }}
                              />
                              {submitted && errors.departmentName && (
                                <p style={{ color: 'red', textAlign: 'center', fontSize: '13px' }}>
                                  ※ 부서명은 필수 항목입니다
                                </p>
                              )}
                            </td>
                          </tr>
                          <tr className="left-row">
                            <td colSpan="2">
                              <label>사용가능메뉴</label>
                            </td>
                            <td colSpan="6">
                              <div className="departmentDetailCheck">
                                {menuList.length > 0 && menuList.map((menu) => (
                                  <div key={menu.menuNo} className="departmentDetailCheckboxWrapper">
                                    <input
                                      type="checkbox"
                                      id={`menu-${menu.menuNo}`}
                                      className="departmentDetailCheckbox"
                                      checked={formValues.menuNo.includes(menu.menuNo)}
                                      onChange={() => handleMenuChange(menu.menuNo)}
                                    />
                                    <label htmlFor={`menu-${menu.menuNo}`}>{menu.menuName}</label>
                                  </div>
                                ))}
                              </div>
                            </td>
                          </tr>
                          <tr className="left-row">
                            <td colSpan="2"><label>사용 여부</label></td>
                            <td colSpan="6">
                              <select
                                value={formValues.departmentState}
                                onChange={(e) => setFormValues({
                                  ...formValues,
                                  departmentState: e.target.value
                                })}
                              >
                                <option value="Y">활성</option>
                                <option value="N">비활성</option>
                              </select>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                      <div className="popup-buttons">
                        <button type="button" className="popup-btn employee" id="employeeListBtn" onClick={handleEmployeePopup}>
                          직원
                        </button>
                        <button type="submit" className="popup-btn modify">저장</button>
                        <button
                          type="button"
                          className="popup-btn close"
                          onClick={() => {
                            setDepartmentDetailPopupOpen(false);
                            setErrors({
                              departmentNo: false,
                              departmentName: false,
                            });
                            setSubmitted(false);
                          }}
                        >
                          닫기
                        </button>
                      </div>
                    </form>
                  </div>
                </Draggable>
              </div>
            )}


            {employeePopupOpen && (
                <div className="popup" style={{zIndex: "999"}}>
                    <Draggable positionOffset={{x: '-50%', y: '-50%'}}>
                        <div className="popup-content" style={{minWidth:"550", minHeight:"300" }} id="employee-list-popup">
                            <div className="popup-header">
                                <span>부서 내 직원 목록</span>
                            </div>
                            <div className="popup-body">
                                    {employees.length > 0 ? (
                                        <table className="employee-table">
                                            <thead>
                                            <tr>
                                                <th style={{minWidth: "155px"}}>이름</th>
                                                <th style={{minWidth: "105px"}}>직급</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {employees.map((employee) => (
                                                <tr key={employee.employeeNo}>
                                                    <td>{employee.positionName}</td>
                                                    <td>{employee.employeeName}</td>
                                                </tr>
                                            ))}
                                            </tbody>
                                        </table>
                                    ) : (
                                        <p>등록된 직원이 없습니다.</p>
                                    )}
                            </div>
                            <div style={{ marginTop: "auto", display:"flex", justifyContent:"end", marginBottom:"10px"}}>
                                <button className="popup-btn close" style={{marginRight:"10px"}}
                                        onClick={() => setEmployeePopupOpen(false)}>닫기
                                </button>
                            </div>
                        </div>
                    </Draggable>
                </div>
            )}


        </main>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<Department/>);
