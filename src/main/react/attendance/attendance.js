import Draggable from "react-draggable";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ReactDOM from 'react-dom/client';
import './attendance.css';
import './attendancePopup.css';
import { DateRangePicker } from 'react-date-range';
import 'react-date-range/dist/styles.css'; // 기본
import 'react-date-range/dist/theme/default.css'; // 테마
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ko } from 'date-fns/locale';

function Attendance() {

        const [filters, setFilters] = useState({
            employeeNo: '',
            employeeName: '',
            leaveType: '',
            status: '',
            requestDate: null,
            approvalDate: null
        });


        const [leaveRequests, setLeaveRequests] = useState([]);  // 휴가 신청 목록
        const [modalOpen, setModalOpen] = useState(false);       // 신청 팝업 상태
        const [selectedRequests, setSelectedRequests] = useState([]);  // 선택된 요청들
        const [isAllChecked, setIsAllChecked] = useState(false);       // 전체 선택 체크박스

        // 초기 상태 설정
        const initialFormValues = {
            leaveTypeId: '',
            startDate: new Date(),
            endDate: new Date()
        };

        const initialDateRange = [
            {
                startDate: new Date(),
                endDate: new Date(),
                key: 'selection'
            }
        ];

        const [formValues, setFormValues] = useState(initialFormValues);
        const [selectedDateRange, setSelectedDateRange] = useState(initialDateRange);
        const [loggedInEmployeeNo, setLoggedInEmployeeNo] = useState(null);  // 로그인한 사용자 ID

        // 달력 변경 핸들러
        const handleDateChange = (ranges) => {
            const { startDate, endDate } = ranges.selection;
            setSelectedDateRange([ranges.selection]);
            setFormValues({
                ...formValues,
                startDate: new Date(startDate.getTime() - startDate.getTimezoneOffset() * 60000).toISOString().slice(0, 10),
                endDate: new Date(endDate.getTime() - endDate.getTimezoneOffset() * 60000).toISOString().slice(0, 10)
            });
        };

        // 팝업 닫기와 초기화 함수
        const handleClosePopup = () => {
            setFormValues(initialFormValues);      // 상태 초기화
            setSelectedDateRange(initialDateRange); // 날짜 범위 초기화
            setTimeout(() => setModalOpen(false), 0); // 상태 초기화 후 팝업 닫기
        };

        useEffect(() => {
            const fetchLoggedInUser = async () => {
                try {
                    const response = await axios.get('/hrm/attendance/auth/user');
                    setLoggedInEmployeeNo(response.data.employeeNo); // 로그인한 사용자의 사원번호 설정
                } catch (error) {
                    console.error('Error fetching logged in user info:', error);
                }
            };

            fetchLoggedInUser();
            fetchLeaveRequests();
        }, []);

        // 필터 변경 핸들러
        const handleInputChange = (e) => {
            const { name, value } = e.target;
            setFilters({
                ...filters,
                [name]: value
            });
        };

        // 검색하기 버튼 클릭 핸들러
        const handleSearch = () => {
            fetchLeaveRequests();
        };

        // 서버에서 휴가 신청 목록을 불러오기
        const fetchLeaveRequests = async () => {
            try {
                const filtersForServer = {
                    employeeNo: filters.employeeNo || '',
                    employeeName: filters.employeeName || '',
                    leaveType: filters.leaveType || '',
                    status: filters.status || '',
                    requestDate: filters.requestDate
                        ? new Date(filters.requestDate.getTime() - filters.requestDate.getTimezoneOffset() * 60000)
                              .toISOString()
                              .split('T')[0]
                        : null,
                    approvalDate: filters.approvalDate
                        ? new Date(filters.approvalDate.getTime() - filters.approvalDate.getTimezoneOffset() * 60000)
                              .toISOString()
                              .split('T')[0]
                        : null,
                };

                console.log("Filters being sent to server:", JSON.stringify(filtersForServer, null, 2));

                const response = await axios.get('/hrm/attendance/leaves', {
                    params: filtersForServer
                });
                setLeaveRequests(response.data);
            } catch (error) {
                console.error('Error fetching leave requests:', error);
            }
        };

        // 전체보기 버튼 클릭 핸들러
        const handleResetFilters = () => {
            setFilters({
                employeeNo: '',
                employeeName: '',
                leaveType: '',
                status: '',
                requestDate: null,
                approvalDate: null
            });
            fetchLeaveRequests();
        };

        useEffect(() => {
            fetchLeaveRequests();
        }, []);

        // 휴가 신청 클릭 시 팝업 열기
        const handleOpenLeaveApplyPopup = () => {
            setModalOpen(true);
        };

        // 신청 폼 제출 핸들러
        const formSubmit = async (e) => {
            e.preventDefault();

            if (loggedInEmployeeNo === null) {
                alert("로그인한 사용자 정보를 불러오고 있습니다. 잠시 후 다시 시도해 주세요.");
                return;
            }

            if (!formValues.leaveTypeId) {
                alert("휴가 종류를 선택하세요.");
                return;
            }

            try {
                await axios.post('/hrm/attendance/leaves', {
                    ...formValues,
                    employeeNo: loggedInEmployeeNo
                });
                handleClosePopup(); // 제출 후 팝업 초기화 및 닫기
                fetchLeaveRequests();
            } catch (error) {
                console.error('Error submitting leave request:', error);
                alert('서버 오류가 발생했습니다. 입력 내용을 확인하세요.');
            }
        };

        // 휴가 상태 업데이트 함수 (승인/거절)
        const updateLeaveStatus = async (leaveRequestId, newStatus) => {
            let remarks = '';

            if (newStatus === 'Rejected') {
                remarks = prompt("거절 사유를 입력하세요 (9글자 이하로 작성해 주세요):");

                if (remarks && remarks.length > 9) {
                    alert("거절 사유는 9글자 이하로 입력해 주세요.");
                    return;
                }
            }

            try {
                await axios.put(`/hrm/attendance/leaves/${leaveRequestId}/status`, {
                    newStatus,
                    remarks
                }, {
                    headers: { 'Content-Type': 'application/json' }
                });
                fetchLeaveRequests();
            } catch (error) {
                if (error.response && error.response.status === 400) {
                    alert(error.response.data);
                } else {
                    alert("거절 사유는 9글자 이하로 입력해 주세요.");
                }
                console.error('Error updating leave status:', error);
            }
        };

        // 체크박스 상태 관리 핸들러
        const handleCheckboxChange = (leaveRequestId) => {
            setSelectedRequests((prevSelected) =>
                prevSelected.includes(leaveRequestId)
                    ? prevSelected.filter((id) => id !== leaveRequestId)
                    : [...prevSelected, leaveRequestId]
            );
        };

        // 전체 체크박스 핸들러
        const handleAllCheckboxChange = () => {
          if (isAllChecked) {
            setSelectedRequests([]);
          } else {
            const selectableRequests = leaveRequests
              .filter((request) => request.status !== 'Rejected' && request.status !== 'Approved')
              .map((request) => request.leaveRequestId);

            setSelectedRequests(selectableRequests);
          }
          setIsAllChecked(!isAllChecked);
        };

        // 승인 및 거절 처리 핸들러
        const handleBulkAction = async (action) => {
            if (selectedRequests.length === 0) {
                alert("선택된 항목이 없습니다.");
                return;
            }

            const confirmMessage =
                action === "approve"
                    ? "선택된 항목을 승인하시겠습니까?"
                    : "선택된 항목을 거절하시겠습니까?";
            if (!window.confirm(confirmMessage)) return;

            const remarks = action === "reject" ? prompt("거절 사유를 입력하세요:") : "";

            try {
                await Promise.all(
                    selectedRequests.map((leaveRequestId) =>
                        axios.put(`/hrm/attendance/leaves/${leaveRequestId}/status`, {
                            newStatus: action === "approve" ? "Approved" : "Rejected",
                            remarks,
                        })
                    )
                );
                fetchLeaveRequests();
                setSelectedRequests([]);
            } catch (error) {
                console.error(`Error ${action}ing leave requests:`, error);
                alert("거절 사유는 9글자 이하로 입력해 주세요.");
            }
        };

        const [currentPage, setCurrentPage] = useState(1);
            const pageSize = 10;
            const totalPages = Math.ceil(leaveRequests.length / pageSize);
            const pagesToShow = 5; // 한 번에 보여줄 페이지 버튼 수

            const visiblePages = Array.from(
                { length: Math.min(pagesToShow, totalPages) },
                (_, i) => {
                    const page = Math.floor((currentPage - 1) / pagesToShow) * pagesToShow + i + 1;
                    return page <= totalPages ? page : null;
                }
            ).filter(Boolean);

            const handlePageChange = (page) => {
                setCurrentPage(page);
            };

            const handlePrevPages = () => {
                setCurrentPage((prev) => Math.max(prev - pagesToShow, 1));
            };

            const handleNextPages = () => {
                setCurrentPage((prev) => Math.min(prev + pagesToShow, totalPages));
            };

            const currentPageData = leaveRequests.slice(
                (currentPage - 1) * pageSize,
                currentPage * pageSize
            );

            /* 인사팀 소속 여부 */
            const [loggedInUserDepartment, setLoggedInUserDepartment] = useState(null);

            useEffect(() => {
                    // 로그인한 사용자의 부서 정보를 불러오는 함수
                    const fetchLoggedInUserDepartment = async () => {
                        try {
                            const response = await axios.get('/hrm/attendance/auth/user');
                            const departmentName = response.data.departmentName;
                            setLoggedInUserDepartment(departmentName);
                            console.log("Logged in user's department:", departmentName); // 로그 추가
                        } catch (error) {
                            console.error('Error fetching user department info:', error);
                        }
                    };

                    fetchLoggedInUserDepartment();
            }, []);
            // 승인 및 거절 버튼이 보이는지 확인
            console.log("Button visibility condition:", loggedInUserDepartment === "인사팀");

    return (
        <main className="wrapper">
            <div className="text-wrap">
                <p>근태관리</p>
                <p>❉ 원하는 항목을 선택하세요.</p>
            </div>

            <div className="order-title">
                <div className="filter-content">
                    <div className="filter-main">
                        <h3>상세 검색</h3>
                        <button className="filter-button" onClick={handleSearch}>검색하기</button>
                        <button className="filter-button" onClick={handleResetFilters}>전체보기</button>
                    </div>
                    <table>
                        <tbody>
                            <tr>
                                <td><p>사원명</p></td>
                                <td><input type="text" name="employeeName" value={filters.employeeName} onChange={handleInputChange}/></td>
                                <td><p>휴가 종류</p></td>
                                <td>
                                    <select name="leaveType" value={filters.leaveType || ''} onChange={handleInputChange}>
                                        <option value="">선택</option>
                                        <option value="1">연차</option>
                                        <option value="2">병가</option>
                                        <option value="3">보상</option>
                                        <option value="4">생리</option>
                                        <option value="5">출산</option>
                                        <option value="6">가족돌봄</option>
                                        <option value="7">경조사</option>
                                        <option value="8">예비군</option>
                                    </select>
                                </td>
                                <td><p>상태</p></td>
                                <td>
                                    <select name="status" value={filters.status} onChange={handleInputChange}>
                                        <option value="">전체</option>
                                        <option value="Pending">대기</option>
                                        <option value="Approved">승인</option>
                                        <option value="Rejected">거절</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td><p>신청일</p></td>
                                <td colSpan="2">
                                    <div style={{ display: 'inline-block', width: '100%' }}>
                                        <DatePicker
                                            selected={filters.requestDate}
                                            onChange={(date) => setFilters({ ...filters, requestDate: date })}
                                            dateFormat="yyyy-MM-dd"
                                            locale={ko}
                                            placeholderText="신청일 선택"
                                            inputClassName="custom-datepicker-input"
                                        />
                                    </div>
                                </td>
                                <td><p>승인일</p></td>
                                <td colSpan="2">
                                    <DatePicker
                                        selected={filters.approvalDate}
                                        onChange={(date) => setFilters({ ...filters, approvalDate: date })}
                                        dateFormat="yyyy-MM-dd"
                                        locale={ko}
                                        placeholderText="승인일 선택"
                                        className="custom-datepicker" // custom class 추가
                                    />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <article>
                <table id="leaveTable">
                    <thead>
                        <tr>
                            <th className="checkboxColumn">
                                <input
                                    type="checkbox"
                                    id="selectAllCheckbox"
                                    checked={isAllChecked}
                                    onChange={handleAllCheckboxChange}
                                />
                            </th>
                            <th>사원명</th>
                            <th>휴가 종류</th>
                            <th>시작일</th>
                            <th>종료일</th>
                            <th>상태</th>
                            <th>신청일</th>
                            <th>승인일</th>
                            <th>비고</th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentPageData.length > 0 ? (
                            currentPageData.map((request, index) => (
                                <tr key={index}>
                                    <td className="checkboxColumn">
                                        <input
                                            type="checkbox"
                                            className="attendanceCheckbox"
                                            checked={selectedRequests.includes(request.leaveRequestId)}
                                            onChange={() => handleCheckboxChange(request.leaveRequestId)}
                                            disabled={request.status === 'Rejected'}
                                        />
                                    </td>
                                    <td>{request.employeeName}</td>
                                    <td>{request.leaveTypeName}</td>
                                    <td>{request.startDate}</td>
                                    <td>{request.endDate}</td>
                                    <td>
                                        {request.status === 'Pending'
                                            ? '대기'
                                            : request.status === 'Approved'
                                            ? '승인'
                                            : request.status === 'Rejected'
                                            ? '거절'
                                            : request.status}
                                    </td>
                                    <td>{request.requestDate}</td>
                                    <td>
                                        {request.approvalDate
                                            ? new Date(new Date(request.approvalDate).getTime() - new Date().getTimezoneOffset() * 60000)
                                                .toISOString()
                                                .slice(0, 10)
                                            : ''}
                                    </td>
                                    <td>{request.remarks}</td>
                                </tr>
                            ))
                        ) : (
                            <tr><td colSpan="11">데이터가 없습니다.</td></tr>
                        )}
                    </tbody>
                </table>
            </article>

            <div className="button-container" style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
                <div style={{ marginRight: "auto" }}>
                    <button className="register" onClick={handleOpenLeaveApplyPopup}>
                        휴가 신청
                    </button>
                </div>

                <div className="pagination" style={{ margin: "0 auto", display: "flex", justifyContent: "center", flex: 1 }}>
                    {visiblePages.map(page => (
                        <button
                            key={page}
                            onClick={() => handlePageChange(page)}
                            className={`page-btn ${page === currentPage ? 'active' : ''}`}
                        >
                            {page}
                        </button>
                    ))}
                </div>

                <div style={{ display: "flex", marginLeft: "auto", gap: "6px", visibility: loggedInUserDepartment === "인사팀" ? "visible" : "hidden" }}>
                    <button className="btn" onClick={() => handleBulkAction("approve")}>승인</button>
                    <button className="btn" onClick={() => handleBulkAction("reject")}>거절</button>
                </div>
            </div>

            {modalOpen && (
                <div className="popup">
                    <Draggable positionOffset={{ x: '-50%', y: '-50%' }}>
                        <div className="popup-content" style={{ width: '400px !important' }}>
                            <div className="popup-header">
                                <span>휴가 신청</span>
                            </div>
                            <form onSubmit={formSubmit}>
                                <table style={{ borderCollapse: 'collapse', width: '100%' }}>
                                    <tbody>
                                        <tr>
                                            <td>휴가 종류</td>
                                            <td>
                                                <select name="leaveTypeId" value={formValues.leaveTypeId} onChange={(e) => setFormValues({ ...formValues, leaveTypeId: e.target.value })}>
                                                    <option value="">선택</option>
                                                    <option value="1">연차</option>
                                                    <option value="2">병가</option>
                                                    <option value="3">보상</option>
                                                    <option value="4">생리</option>
                                                    <option value="5">출산</option>
                                                    <option value="6">가족돌봄</option>
                                                    <option value="7">경조사</option>
                                                    <option value="8">예비군</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2" style={{ width: '700px' }}>
                                                <DateRangePicker
                                                    locale={ko}
                                                    ranges={selectedDateRange}
                                                    onChange={handleDateChange}
                                                    minDate={new Date()} // 오늘 날짜 이전을 선택할 수 없음
                                                    renderDayContents={(day) => (
                                                        <div style={{ width: '50px', height: '50px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                                                            {day.getDate()}
                                                        </div>
                                                    )}
                                                />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div className="popup-buttons" style={{ paddingRight: '20px' }}>
                                    <button type="submit">등록</button>
                                    <button type="button" onClick={handleClosePopup}>닫기</button>
                                </div>
                            </form>
                        </div>
                    </Draggable>
                </div>
            )}
        </main>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<Attendance />);
