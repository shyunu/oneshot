import Draggable from "react-draggable";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ReactDOM from 'react-dom/client';
import './department.css';
import './popup.css';
import './delete.css';

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
            menus: [],
            departmentState: 'Y',
        });
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
        menus: [], // 선택된 메뉴들을 저장하는 배열
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
            const isSelected = prevValues.menus.includes(menuNo);
            if (isSelected) {
                // 선택된 경우 메뉴 제거
                return { ...prevValues, menus: prevValues.menus.filter((no) => no !== menuNo) };
            } else {
                // 선택되지 않은 경우 메뉴 추가
                return { ...prevValues, menus: [...prevValues.menus, menuNo] };
            }
        });
    };



    // 정렬 상태 관리
    const [sortConfig, setSortConfig] = useState({ key: 'departmentNo', direction: 'ascending' });

    useEffect(() => {
        fetchDepartments();
        fetchMenus();
    }, []);

    // 활성/비활성 팝업 열기
    const handleStatusClick = () => {
        if (selectedDepartments.length > 0) {
            setStatusPopupOpen(true);  // 팝업 열기
        } else {
            alert("활성/비활성 상태를 변경할 부서를 선택하세요.");
        }
    };

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
            if (response.data.length > 0) {
                setDepartments(response.data);  // 전체 부서 데이터를 저장
                setFilteredDepartments(response.data);  // 초기 상태로 필터된 부서도 전체 부서로 설정
            } else {
                setDepartments([]); // 부서 데이터가 없을 경우 빈 배열
                setFilteredDepartments([]); // 필터된 데이터도 빈 배열
            }
        } catch (err) {
            console.error('Error fetching departments:', err);
            setDepartments([]);
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
                ? department.departmentName.includes(filters.departmentName)
                : true;
            const matchAvailableMenu = filters.availableMenu
                ? department.menus.includes(parseInt(filters.availableMenu))
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
    useEffect(() => {
        console.log("Updated menuList:", menuList);
    }, [menuList]);
    // 페이지가 로드될 때 메뉴 목록을 불러옵니다.
    useEffect(() => {
        fetchMenus();
    }, []);

    // 부서 선택 시 호출되는 함수
    const handleCheckboxChange = (departmentNo) => {
        setSelectedDepartments((prevSelected) =>
            prevSelected.includes(departmentNo)
                ? prevSelected.filter((no) => no !== departmentNo)  // 선택된 부서가 이미 있으면 제거
                : [...prevSelected, departmentNo]  // 선택되지 않은 부서는 추가
        );
    };

    // 전체 체크박스 선택/해제 처리
    const handleAllCheckboxChange = () => {
        if (isAllChecked) {
            // 전체 해제: 선택된 부서 목록을 빈 배열로 설정
            setSelectedDepartments([]);
        } else {
            // 전체 선택: 모든 부서의 departmentNo 값을 선택된 부서 목록에 추가
            setSelectedDepartments(departments.map(department => department.departmentNo));
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
            menus: department.menus || [],
            departmentState: department.departmentState,
        });
        setDepartmentDetailPopupOpen(true);
    };


    // 부서 저장 함수
    const handleSave = async (e) => {
        e.preventDefault();

        const departmentNo = parseInt(formValues.departmentNo); // 기존 부서 번호는 읽기 전용이므로 수정 불가
        if (isNaN(departmentNo)) {
            alert("부서번호는 숫자여야 합니다.");
            return;
        }

        try {
            const requestData = {
                departmentNo, // 읽기 전용이므로 변경되지 않음
                departmentName: formValues.departmentName.trim(), // 부서명
                departmentState: formValues.departmentState, // 활성/비활성 여부
                menus: formValues.menus, // 선택된 메뉴 리스트
            };

            const response = await axios.put(`http://localhost:8181/hrm/updateDepartmentDetails`, requestData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.status === 200) {
                alert("부서 정보가 수정되었습니다.");
                setDepartmentDetailPopupOpen(false);
                fetchDepartments(); // 수정 후 부서 목록 새로고침
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
        menus: formValues.menus.join(','),  // 배열을 쉼표로 구분된 문자열로 변환
    };



    // 부서 등록 폼 제출 처리
    const formSubmit = async (e) => {
        e.preventDefault();
        setSubmitted(true);

        // 부서명 중복 확인 로직 추가
        try {
            const duplicateCheckResponse = await axios.get(`http://localhost:8181/hrm/checkDuplicateDepartmentName/${formValues.departmentName}`);
            if (duplicateCheckResponse.data) {
                alert('이미 중복된 부서명이 있습니다.');
                return; // 중복된 경우 등록을 중지
            }
        } catch (error) {
            console.error('부서명 중복 확인 중 오류 발생:', error);
        }

        if (!formValues.departmentNo.trim() || !formValues.departmentName.trim()) {
            setErrors({
                departmentNo: !formValues.departmentNo.trim(),
                departmentName: !formValues.departmentName.trim(),
            });
            return;
        }

        const departmentVO = {
            departmentNo: parseInt(formValues.departmentNo),
            departmentName: formValues.departmentName,
            menus: formValues.menus,
        };

        try {
            const response = await axios.post("http://localhost:8181/hrm/registDepartment", departmentVO, {
                headers: { 'Content-Type': 'application/json' },
            });

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

    const sortedDepartments = [...filteredDepartments].sort((a, b) => {
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

    return (
        <main className="wrapper">
            <div className="text-wrap">
                <p>부서리스트</p>
                <p>❉ 조회 또는 수정을 원하시면 부서명을 선택해주세요. </p>
            </div>

            <div className="order-title">
                <div className="filter-content">
                    <h3>상세내역검색</h3>
                    <div className="filter-main">
                        <button className="filter-button" onClick={handleSearch}>검색하기</button>
                    </div>

                    <table>
                        <tbody>
                            <tr>
                                <td><p>부서명</p></td>
                                <td>
                                    <select
                                      name="departmentName"
                                      value={filters.departmentName}
                                      onChange={handleInputChange}
                                    >
                                      <option value="">전체</option>
                                      {departments.length > 0 &&
                                        departments.map((department) => (
                                          <option key={department.departmentNo} value={department.departmentName}>
                                            {department.departmentName}
                                          </option>
                                        ))}
                                    </select>
                                </td>
                                <td><p>부서번호</p></td>
                                <td>
                                    <input type="number" name="departmentNo" value={filters.departmentNo} onChange={handleInputChange} />
                                </td>
                            </tr>
                            <tr>
                                <td><p>사용 가능 메뉴</p></td>
                                <td>
                                    <select
                                      name="availableMenu"
                                      value={filters.availableMenu}
                                      onChange={handleInputChange}
                                    >
                                      <option value="">선택</option>
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
                        </tbody>
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
                            <th onClick={() => requestSort('departmentNo')}>부서번호 {sortConfig.key === 'departmentNo' && (sortConfig.direction === 'ascending' ? '▲' : '▼')}</th>
                            <th onClick={() => requestSort('departmentName')}>부서명 {sortConfig.key === 'departmentName' && (sortConfig.direction === 'ascending' ? '▲' : '▼')}</th>
                            <th onClick={() => requestSort('menus')}>사용가능메뉴 {sortConfig.key === 'menus' && (sortConfig.direction === 'ascending' ? '▲' : '▼')}</th>
                            <th onClick={() => requestSort('departmentState')}>사용여부 {sortConfig.key === 'departmentState' && (sortConfig.direction === 'ascending' ? '▲' : '▼')}</th>
                        </tr>
                    </thead>
                    <tbody>
                        {sortedDepartments.length > 0 ? (
                            sortedDepartments.map((department) => (
                                <tr key={department.departmentNo} className="product_list">
                                    <td>
                                        <input
                                            type="checkbox"
                                            id={`check${department.departmentNo}`}
                                            checked={selectedDepartments.includes(department.departmentNo)}
                                            onChange={() => handleCheckboxChange(department.departmentNo)}
                                        />
                                        <label htmlFor={`check${department.departmentNo}`}></label>
                                    </td>
                                    <td>{department.departmentNo}</td>
                                    <td onClick={() => handleDepartmentClick(department)} style={{ cursor: 'pointer' }}>
                                        {department.departmentName}
                                    </td>
                                    <td>
                                        {department.menus && department.menus.length > 0
                                            ? department.menus.map((menuNo) => {
                                                const menu = menuList.find((m) => m.menu_no === menuNo);
                                                return menu ? <span key={menu.menu_no}>{menu.menu_name}</span> : '메뉴 없음';
                                              })
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

            <div className="wrapper-footer">
                <div className="flex" style={{justifyContent: "space-between"}}>
                    <button>Excel로 내보내기</button>
                    <div>
                        <button className="btn" style={{marginRight: "6px"}} onClick={() => setModalOpen(true)}>등록</button>
                        <button
                            className="btn"
                            disabled={selectedDepartments.length === 0}
                            onClick={handleStatusClick}
                        >
                            여부
                        </button>
                    </div>

                </div>
            </div>
            {statusPopupOpen && (
                <div className="popup" id="popupStatusConfirmation">
                    <div className="popup-content">
                        <div className="popup-header">
                            <span>부서 상태 변경</span>
                        </div>
                        <div className="popup-body">
                            <p>선택한 부서의 상태를 변경하시겠습니까?</p>
                            <div>
                                <label>
                                    <input
                                        type="radio"
                                        name="status"
                                        value="Y"
                                        onChange={() => setSelectedStatus('Y')}
                                    />
                                    활성화
                                </label>
                                <label>
                                    <input
                                        type="radio"
                                        name="status"
                                        value="N"
                                        onChange={() => setSelectedStatus('N')}
                                    />
                                    비활성화
                                </label>
                            </div>
                        </div>
                        <div className="popup-buttons">
                            <button type="button" className="btn-confirm" onClick={handleStatusConfirm}>확인</button>
                            <button
                                type="button"
                                className="btn-close"
                                onClick={() => setStatusPopupOpen(false)}
                            >
                                닫기
                            </button>
                        </div>
                    </div>
                </div>
            )}



        {/* 신규 등록 모달창 */}
                    {modalOpen && (
                        <div className="popup" id="contractPopup">
                            <Draggable bounds="body">
                                <div className="popup-content" id="draggablePopup">
                                    <div className="popup-header" id="popupHeader">
                                        <span>부서등록</span>
                                    </div>
                                    <form className="contract-form" onSubmit={formSubmit}>
                                        <div>
                                            <label htmlFor="departmentNo">부서번호</label>
                                            <input
                                                type="text"
                                                id="departmentNo"
                                                value={formValues.departmentNo}
                                                onChange={handleChange}
                                                placeholder="부서번호"
                                                className={submitted && errors.departmentNo ? 'input-error' : ''}
                                            />
                                            {submitted && errors.departmentNo && <div className="error-message">부서번호를 입력하세요.</div>}
                                        </div>
                                        <div>
                                            <label htmlFor="departmentName">부서명</label>
                                            <input
                                                type="text"
                                                id="departmentName"
                                                value={formValues.departmentName}
                                                onChange={handleChange}
                                                placeholder="부서명"
                                                className={submitted && errors.departmentName ? 'input-error' : ''}
                                                required
                                            />
                                        </div>
                                        <div>
                                            <label>사용 가능 메뉴:</label>
                                            {menuList.length > 0 ? (
                                                menuList.map((menu) => (
                                                    <div key={menu.menuNo}>
                                                        <input
                                                            type="checkbox"
                                                            id={`menu-${menu.menuNo}`}
                                                            value={menu.menuNo}
                                                            checked={formValues.menus.includes(menu.menuNo)}  // 선택된 상태를 반영
                                                            onChange={() => handleMenuChange(menu.menuNo)}  // 변경 시 상태 처리
                                                        />
                                                        <label htmlFor={`menu-${menu.menuNo}`}>{menu.menuName}</label>
                                                    </div>
                                                ))
                                            ) : (
                                                <p>사용할 수 있는 메뉴가 없습니다.</p>
                                            )}
                                        </div>



                                        <div className="popup-buttons">
                                            <button type="submit" className="popup-btn modify">저장</button>
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
                    <div className="popup-content">
                        <div className="popup-header">
                            <span>부서 상세 정보 수정</span>
                        </div>
                        <form className="popup-form" onSubmit={handleSave}>
                            <div>
                                <label htmlFor="departmentNo">부서번호</label>
                                <input
                                    type="text"
                                    id="departmentNo"
                                    value={formValues.departmentNo}
                                    readOnly // 부서번호는 수정 불가
                                />
                            </div>
                            <div>
                                <label htmlFor="departmentName">부서명</label>
                                <input
                                    type="text"
                                    id="departmentName"
                                    value={formValues.departmentName}
                                    onChange={(e) => setFormValues({ ...formValues, departmentName: e.target.value })}
                                />
                            </div>
                            <button type="button" className="popup-btn employee" onClick={handleEmployeePopup}>직원 목록 보기</button>
                            <div>
                                <label>사용가능메뉴</label>
                                <div className="menu-list">
                                    {menuList.length > 0 && menuList.map((menu) => (
                                        <span key={menu.menuNo}>
                                            <input
                                                type="checkbox"
                                                id={`menu-${menu.menuNo}`}
                                                checked={formValues.menus.includes(menu.menuNo)}
                                                onChange={() => handleMenuChange(menu.menuNo)}
                                            />
                                            <label htmlFor={`menu-${menu.menuNo}`}>{menu.menuName}</label>
                                        </span>
                                    ))}
                                </div>
                            </div>
                            <div>
                                <label>사용 여부</label>
                                <select
                                    value={formValues.departmentState}
                                    onChange={(e) => setFormValues({ ...formValues, departmentState: e.target.value })}
                                >
                                    <option value="Y">활성</option>
                                    <option value="N">비활성</option>
                                </select>
                            </div>
                            <div className="popup-buttons">
                                <button type="submit" className="popup-btn modify">저장</button>
                                <button type="button" className="popup-btn close" onClick={() => setDepartmentDetailPopupOpen(false)}>닫기</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {employeePopupOpen && (
                <div className="popup">
                    <div className="popup-content">
                        <h2>부서 내 직원 목록</h2>
                        {employees.length > 0 ? (
                            <ul>
                                {employees.map((employee) => (
                                    <li key={employee.employeeNo}>
                                        {employee.employeeName} - {employee.positionName}
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>등록된 직원이 없습니다.</p>
                        )}
                        <button onClick={() => setEmployeePopupOpen(false)}>닫기</button>
                    </div>
                </div>
            )}




        </main>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<Department />);
