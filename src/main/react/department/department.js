import Draggable from "react-draggable";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ReactDOM from 'react-dom/client';
import './department.css';
import './popup.css';
import './delete.css';


function Department() {

    const [modalOpen, setModalOpen] = useState(false);
    const [formValues, setFormValues] = useState({
        departmentNo: '',
        departmentName: '',
    });
    const [departments, setDepartments] = useState([]);
    const [selectedDepartments, setSelectedDepartments] = useState([]); // 선택된 부서 목록
    const [isAllChecked, setIsAllChecked] = useState(false); // 전체 선택 체크박스 상태
    const [deletePopupOpen, setDeletePopupOpen] = useState(false); // 삭제 확인 팝업 상태
    const [finalWarningPopupOpen, setFinalWarningPopupOpen] = useState(false); // 최종 삭제 경고 팝업 상태
    const [errors, setErrors] = useState({
        departmentNo: false,
        departmentName: false,
    });
    const [submitted, setSubmitted] = useState(false); // 폼 제출 상태

    // 정렬
    const [sortConfig, setSortConfig] = useState({ key: 'departmentNo', direction: 'ascending' });

    useEffect(() => {
        fetchDepartments();
    }, []);

    const fetchDepartments = async () => {
        try {
            const response = await axios.get("http://localhost:8181/hrm/getDepartment");
            setDepartments(response.data);
        } catch (err) {
            console.error('Error fetching departments:', err);
        }
    };

    const handleCheckboxChange = (departmentNo) => {
        setSelectedDepartments((prevSelected) =>
            prevSelected.includes(departmentNo)
                ? prevSelected.filter((no) => no !== departmentNo)
                : [...prevSelected, departmentNo]
        );
    };

    const handleAllCheckboxChange = () => {
        if (isAllChecked) {
            setSelectedDepartments([]);
        } else {
            setSelectedDepartments(departments.map((department) => department.departmentNo));
        }
        setIsAllChecked(!isAllChecked);
    };

    const handleDeleteClick = () => {
        if (selectedDepartments.length > 0) {
            setDeletePopupOpen(true);
        }
    };

    const handleNextClick = () => {
        setDeletePopupOpen(false);
        setFinalWarningPopupOpen(true);
    };

    const handleDeleteConfirm = async () => {
        try {
            await axios.delete("http://localhost:8181/hrm/deleteDepartments", {
                data: selectedDepartments, // delete 메서드에서는 data 속성을 사용해야 함
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            setFinalWarningPopupOpen(false);
            fetchDepartments(); // 삭제 후 부서 목록 새로고침
            setSelectedDepartments([]);
            setIsAllChecked(false);
        } catch (err) {
            console.error('Error deleting departments:', err);
        }
    };

    const validateForm = () => {
        const newErrors = {
            departmentNo: !formValues.departmentNo.trim(),
            departmentName: !formValues.departmentName.trim(),
        };
        setErrors(newErrors);
        return !Object.values(newErrors).includes(true);
    };

    const handleChange = (e) => {
        const { id, value } = e.target;
        setFormValues((prevValues) => ({
            ...prevValues,
            [id]: value,
        }));
        setErrors((prevErrors) => ({
            ...prevErrors,
            [id]: false,
        }));
    };

    const formSubmit = async (e) => {
        e.preventDefault();
        setSubmitted(true); // 폼 제출 시 submitted 상태를 true로 설정

        if (!validateForm()) {
            return; // 유효성 검사를 통과하지 못하면 폼 제출을 중단
        }

        const departmentVO = {
            departmentNo: formValues.departmentNo,
            departmentName: formValues.departmentName,
        };

        try {
            const response = await axios.post(
                "http://localhost:8181/hrm/registDepartment",
                JSON.stringify(departmentVO),
                {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );
            alert("부서가 등록되었습니다.");
            fetchDepartments(); // 신규 등록 후 부서 목록 새로고침
            setModalOpen(false);
            setSubmitted(false); // 제출 성공 시, 오류 상태 초기화
        } catch (err) {
            if (err.response && err.response.data) {
                // 서버에서 반환한 유효성 검사 오류 메시지 처리
                const errorMessages = err.response.data;
                const newErrors = {
                    departmentNo: errorMessages.departmentNo || false,
                    departmentName: errorMessages.departmentName || false,
                };
                setErrors(newErrors);
            } else {
                console.error('Error submitting department:', err);
            }
        }
    };

    // 정렬
    const sortedDepartments = [...departments].sort((a, b) => {
        let aValue = a[sortConfig.key];
        let bValue = b[sortConfig.key];

        // 부서번호
        if (sortConfig.key === 'departmentNo') {
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
        let direction = 'ascending';
        if (sortConfig.key === key && sortConfig.direction === 'ascending') {
            direction = 'descending';
        }
        setSortConfig({ key, direction });
    }

    return (
        <main className="wrapper">
            <div className="wrapper-title">
                <span>부서리스트</span>
                <button className="btn">Search</button>
            </div>

            {/* 리스트화면 */}
            <article>
                <table>
                    <thead>
                        <tr id="attribute">
                            <td>
                                <input
                                    type="checkbox"
                                    id="checkAll"
                                    checked={isAllChecked}
                                    onChange={handleAllCheckboxChange}
                                />
                                <label htmlFor="checkAll"></label>
                            </td>
                            <td style={{ width: '150px' }} onClick={() => requestSort('departmentNo') }>
                            부서번호 { sortConfig.key === 'departmentNo' && ( sortConfig.direction === 'ascending' ? '▲' : '▼')}
                            </td>
                            <td style={{ width: '100px' }} onClick={() => requestSort('departmentName') }>
                            부서명 { sortConfig.key === 'departmentName' && ( sortConfig.direction === 'ascending' ? '▲' : '▼')}
                            </td>
                            <td style={{ width: '100px' }} onClick={() => requestSort('menu') }>
                            사용가능메뉴 { sortConfig.key === 'menu' && ( sortConfig.direction === 'ascending' ? '▲' : '▼')}
                            </td>
                            <td style={{ width: '200px' }} onClick={() => requestSort('isActive') }>
                            사용여부 { sortConfig.key === 'isActive' && ( sortConfig.direction === 'ascending' ? '▲' : '▼')}
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                        {sortedDepartments.map((department) => (
                            <tr key={department.departmentNo}>
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
                                <td>{department.departmentName}</td>
                                <td>인사관리</td>
                                <td>YES</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </article>

            <div className="wrapper-footer flex">
                <div className="btns">
                    <button
                        className="btn"
                        disabled={selectedDepartments.length === 0}
                        onClick={handleDeleteClick}
                    >
                        삭제
                    </button>
                    <button className="btn">여부</button>
                    <button className="btn" onClick={() => setModalOpen(true)}>등록</button>
                </div>
            </div>

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
                                    />
                                    {submitted && errors.departmentName && <div className="error-message">부서명을 입력하세요.</div>}
                                </div>
                                <label htmlFor="department">사용가능메뉴</label>
                                <div className="flex">
                                    <span>
                                        <input type="checkbox" id="hrm" />
                                        <label htmlFor="hrm">인사관리</label>
                                    </span>
                                    <span>
                                        <input type="checkbox" id="pm" />
                                        <label htmlFor="pm">상품관리</label>
                                    </span>
                                    <span>
                                        <input type="checkbox" id="ssm" />
                                        <label htmlFor="ssm">영업판매관리</label>
                                    </span>
                                    <span>
                                        <input type="checkbox" id="spm" />
                                        <label htmlFor="spm">영업구매관리</label>
                                    </span>
                                </div>
                                <div className="popup-buttons">
                                    <button type="submit" className="popup-btn modify">저장</button>
                                    <button
                                        type="button"
                                        className="popup-btn close"
                                        id="closePopupButton"
                                        onClick={() => setModalOpen(false)}
                                    >
                                        닫기
                                    </button>
                                </div>
                            </form>
                        </div>
                    </Draggable>
                </div>
            )}

            {/* 첫 번째 삭제 확인 팝업 */}
            {deletePopupOpen && (
                <div className="popup" id="popupDeleteConfirmation">
                    <div className="popup-content">
                        <div className="popup-header">
                            <span>삭제 여부 확인</span>
                        </div>
                        <div className="popup-body">
                            <table className="employee-table">
                                <thead>
                                    <tr>
                                        <th>부서번호</th>
                                        <th>부서명</th>
                                    </tr>
                                </thead>
                                <tbody id="employeeDataTable">
                                    {departments
                                        .filter((dept) => selectedDepartments.includes(dept.departmentNo))
                                        .map((dept) => (
                                            <tr key={dept.departmentNo}>
                                                <td>{dept.departmentNo}</td>
                                                <td>{dept.departmentName}</td>
                                            </tr>
                                        ))}
                                </tbody>
                            </table>
                        </div>
                        <div className="popup-buttons">
                            <button type="button" className="btn-next" onClick={handleNextClick}>다음</button>
                            <button
                                type="button"
                                className="btn-close"
                                onClick={() => setDeletePopupOpen(false)}
                            >
                                닫기
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/* 두 번째 삭제 경고 팝업 */}
            {finalWarningPopupOpen && (
                <div className="popup" id="popupFinalWarning">
                    <div className="popup-content">
                        <div className="popup-header">
                            <span>삭제 확인</span>
                        </div>
                        <div className="popup-body">
                            <p>선택한 정보를 정말로 삭제하시겠습니까?</p>
                        </div>
                        <div className="popup-buttons">
                            <button type="button" className="btn-delete" onClick={handleDeleteConfirm}>삭제</button>
                            <button
                                type="button"
                                className="btn-back"
                                onClick={() => {
                                    setFinalWarningPopupOpen(false);
                                    setDeletePopupOpen(true);
                                }}
                            >
                                뒤로
                            </button>
                            <button
                                type="button"
                                className="btn-close"
                                onClick={() => setFinalWarningPopupOpen(false)}
                            >
                                닫기
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </main>
    );
}


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<Department />);