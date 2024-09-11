import React, { useState, useEffect } from 'react';
import ReactDOM from 'react-dom/client';
import './employee.css';
import './one.css';
import './delete.css';
import Draggable from 'react-draggable';
import axios from "axios";

function Employee() {
    const [showPopup, setShowPopup] = useState(false);
    const [selectedEmployees, setSelectedEmployees] = useState([]); // 선택된 직원 목록
    const [isAllChecked, setIsAllChecked] = useState(false); // 전체 선택 체크박스 상태
    const [detail, setDetail] =useState(false)
    const [showDepartmentPopup, setShowDepartmentPopup] = useState(false);
    const [showBankPopup, setShowBankPopup] = useState(false);
    const [departments, setDepartments] = useState([]); //가저온 부서목록
    const [banks, setBanks] = useState([]); //가져온 은행목록

    const [newEmployee, setNewEmployee] = useState({
        joinDate: '',
        name: '',
        departmentNo: '',
        departmentName: '',
        position: '',
        phone: '',
        emergencyContact: '',
        email: '',
        accountNumber: '',
        bankNo: '',
        bankName:'',
        accountHolder:'',
        address: '',
        addressDetail: ''
    });
    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setNewEmployee(prev => ({ ...prev, [name]: value }));
    };


    const [employees, setEmployees] = useState([]);


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
    const handleFormSubmit = async (e) => {
        e.preventDefault();

        const employeeVO = {
            employeeName: newEmployee.name,
            employeeBirth: newEmployee.joinDate,
            departmentNo: newEmployee.departmentNo,
            employeePhone: newEmployee.phone,
            emergencyPhone: newEmployee.emergencyContact,
            employeeAddress: newEmployee.address,
            accountNumber: newEmployee.accountNumber,
            employeeHiredate: newEmployee.joinDate,
            employeeEmail: newEmployee.email,
            bankNo: newEmployee.bankNo,
            accountHolder:  newEmployee.accountHolder
        };

        try {
            const response = await axios.post(
                "http://localhost:8181/hrm/registEmployee",
                employeeVO,
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            console.log('폼 제출 완료 및 직원 생성:', response.data);
        } catch (error) {
            console.error('폼 제출 실패:', error);
        }
    };




    const fetchEmployees = async () => {
        try {
            const response = await fetch('/hrm/getEmployee');

            if (!response.ok) {
                // 응답이 성공적이지 않을 경우 에러 메시지 로깅
                const errorMessage = await response.text(); // 또는 response.json()을 사용할 수 있습니다.
                console.error('직원불러오기실패:', errorMessage);
                return; // 오류를 처리한 후 함수 종료
            }

            // JSON 응답 데이터 처리
            const data = await response.json();
            setEmployees(data); // 상태 업데이트
        } catch (error) {
            console.error('직원불러오기Error:', error); // 네트워크 요청 실패 및 다른 예외 처리
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

    useEffect(() => {
        fetchEmployees();
    }, []);

    const handleBankSelect = (bankNo, backName) => {
        setNewEmployee(prev => ({ ...prev, bankNo: bankNo }));
        setNewEmployee(prev => ({ ...prev, backName: backName }));
        setShowBankPopup(false);
    };

    const handleDepartmentSelect = (departmentNo, departmentName) => {
        setNewEmployee(prev => ({ ...prev, departmentNo: departmentNo }));
        setNewEmployee(prev => ({ ...prev, departmentName: departmentName }));
        setShowDepartmentPopup(false);
    };


    // 디버깅: employees 상태 출력
    useEffect(() => {
        console.log('Employees:', employees);
    }, [employees]);

    return (
        <main className="wrapper">
            <div className="wrapper-title">
                <span>인사카드</span>
                <button className="searchBtn">검색</button>
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
                            <th style={{width: '150px'}}>입사일자</th>
                            <th style={{width: '100px'}}>사원번호</th>
                            <th style={{width: '100px'}}>성명</th>
                            <th style={{width: '200px'}}>부서명</th>
                            <th style={{width: '100px'}}>직급</th>
                            <th style={{ width: '200px' }}>전화번호</th>
                            <th style={{ width: '200px' }}>비상연락처</th>
                            <th style={{ width: '250px' }}>이메일</th>
                            <th style={{ width: '250px' }}>계좌번호</th>
                        </tr>
                    </thead>
                    <tbody>
                        {employees && employees.length > 0 ? employees.map(employee => (
                            employee ? (
                                <tr key={employee.employeeNo}>
                                    <td>
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
                                    <td>{employee.rankName}</td>
                                    <td>{employee.emergencyPhone}</td>
                                    <td>{employee.emergencyPhone}</td>
                                    <td>{employee.employeeEmail}</td>
                                    <td>{employee.accountNumber}</td>
                                    {/* 필요한 모든 데이터 표시 */}
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
                    <button className="btn" style={{marginRight: "10px"}} >선택삭제</button>
                    <button className="btn" onClick={() =>{
                        setShowPopup(true);
                        setDetail(true);}}>신규등록</button>
                </div>
            </div>

            {showPopup &&
                <div className="popup" id="contractPopup">
                    <Draggable bounds="body">
                        <div className="popup-content" id="draggablePopup">
                            <div className="popup-header" id="popupHeader">
                                <span>사원 등록</span>
                            </div>
                            <form className="contract-form" onSubmit={handleFormSubmit}>
                                <div className="formBox1">
                                    <div className="profile-picture-container">
                                        <div className="profile-picture">
                                            <img src="ddung2.png" alt="Profile Picture" id="profileImg" />
                                        </div>
                                        <div className="file-input-container">
                                            <input type="file" id="fileInput" accept="image/*" />
                                            <label htmlFor="fileInput" className="btn attach-file">사진변경</label>
                                        </div>
                                    </div>
                                    <div>
                                        <label htmlFor="employeeName">이름</label>
                                        <input type="text" id="employeeName" name="name" value={newEmployee.name} onChange={handleFormChange} />
                                    </div>
                                    <div className={ detail ? 'hidden': ''} >
                                        <label htmlFor="employeeNo">사원번호</label>
                                        <input type="text" id="employeeNo" name="id" value={newEmployee.id} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="department">부서번호</label>
                                        <button type="button" id="selectDepartmentBtn" className="btn"
                                                onClick={() => {
                                                    fetchDepartments();
                                                    setShowDepartmentPopup(true);
                                                }}>부서선택
                                        </button>
                                        <input type="text" id="department" name="department"
                                               value={newEmployee.departmentName} style={{ marginTop: "4px" }} readOnly/>
                                    </div>
                                    <div>
                                        <label htmlFor="employeeRank">직급</label>
                                        <input type="text" id="employeeRank" name="position" value={newEmployee.position} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="employeeHiredate">입사일</label>
                                        <input type="date" id="employeeHiredate" name="joinDate" value={newEmployee.joinDate} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="employeePhone">전화번호</label>
                                        <input type="text" id="employeePhone" name="phone" value={newEmployee.phone} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="emergencyPhone">비상연락망</label>
                                        <input type="text" id="emergencyPhone" name="emergencyContact" value={newEmployee.emergencyContact} onChange={handleFormChange} />
                                    </div>
                                </div>
                                <div className="formBox2">
                                    <div>
                                        <label htmlFor="employeeBirth">생년월일</label>
                                        <input type="text" id="employeeBirth" name="birthDate" />
                                    </div>
                                    <div>
                                        <label htmlFor="employeeEmail">이메일</label>
                                        <input type="text" id="employeeEmail" name="email" value={newEmployee.email} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="accountNumber">은행/계좌번호/예금주</label>
                                        <button type="button" id="selectBankBtn" className="btn attach-file" onClick={() => {setShowBankPopup(true); fetchBanks();}}>은행선택</button>
                                        <input type="text" id="accountBank" name="bank" placeholder="은행 선택" value={newEmployee.backName} readOnly />
                                        <input type="text" id="accountNumber" name="accountNumber" placeholder="계좌번호" value={newEmployee.accountNumber} onChange={handleFormChange} />
                                        <input type="text" id="accountHolder" name="accountHolder" placeholder="계좌주" value={newEmployee.accountHolder} onChange={handleFormChange}/>
                                    </div>
                                    <div>
                                        <label htmlFor="employeeAddress">주소</label>
                                        <button type="button" id="searchAddressBtn" className="btn attach-file">주소검색</button>
                                        <input type="text" id="employeeAddress" name="address" placeholder="주소를 입력하세요" value={newEmployee.address} onChange={handleFormChange} />
                                        <input type="text" id="employeeAddress1" name="addressDetail" placeholder="상세주소를 입력하세요" value={newEmployee.addressDetail} onChange={handleFormChange} />
                                    </div>
                                </div>
                                <div className="popup-buttons">
                                    <button type="submit" className="btn">등록</button>
                                    <button type="button" className="btn close" onClick={() => setShowPopup(false)}>닫기</button>
                                </div>
                            </form>
                        </div>
                    </Draggable>
                </div>
            }
            {/* 은행선택 팝업 */}
            {showBankPopup && (
                <div id="SelectPopup" className="popup">
                    <div className="popup-content">
                        <div className="popup-header">
                            <h3>은행 선택</h3>
                            <button className="btn close" onClick={() => setShowBankPopup(false)}>닫기</button>
                        </div>
                        <div className="options">
                            {banks.map((bank) => (
                                <div key={bank.bankNo} className="item" onClick={() => handleBankSelect(bank.bankNo, bank.bankName)}>
                                    <button className="no-option">{bank.bankNo}</button>
                                    <button className="name-option">{bank.bankName}</button>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            )}

            {/* 부서선택 팝업 */}
            {showDepartmentPopup && (
                <div id="SelectPopup" className="popup">
                    <div className="popup-content">
                        <div className="popup-header">
                            <h3>계좌 선택</h3>
                            <button className="btn close" onClick={() => setShowDepartmentPopup(false)}>닫기</button>
                        </div>
                        <div className="options">
                            {departments.map((department) => (
                                <div key={department.departmentNo} className="item" onClick={() => handleDepartmentSelect(department.departmentNo, department.departmentName)}>
                                    <button className="no-option">{department.departmentNo}</button>
                                    <button className="name-option">{department.departmentName}</button>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            )}

        </main>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Employee />
);
