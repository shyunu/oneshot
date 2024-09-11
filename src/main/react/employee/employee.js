import React, { useState, useEffect } from 'react';
import ReactDOM from 'react-dom/client';
import './employee.css';
import './one.css';
import './delete.css';
import Draggable from 'react-draggable';
import axios from "axios";

function Employee() {
    const [showPopup, setShowPopup] = useState(false);
    const [detail, setDetail] =useState(false)
    const [showBankPopup, setShowBankPopup] = useState(false);
    const [newEmployee, setNewEmployee] = useState({
        joinDate: '',
        name: '',
        department: '',
        position: '',
        phone: '',
        emergencyContact: '',
        email: '',
        accountNumber: '',
        bankNo: '',
        accountHolder:'',
        address: '',
        addressDetail: '',
        backName:''
    });
    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setNewEmployee(prev => ({ ...prev, [name]: value }));
    };

    const [employees, setEmployees] = useState([]);


    const handleFormSubmit = async (e) => {
        e.preventDefault();

        const employeeVO = {
            employeeName: newEmployee.name,
            employeeBirth: newEmployee.joinDate,
            departmentNo: newEmployee.department,
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
                JSON.stringify(employeeVO),
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            const result = await response.json();
            console.log('폼 제출 완료 및 직원 생성:', result);
        } catch (error) {
            console.error('폼 제출 실패:', error);
        }
    };




    const fetchEmployees = async () => {

        try {
            const response = await fetch('/hrm');
            if (response.ok) {
                const data = await response.json();
                setEmployees(data);
            } else {
                console.error('Failed to fetch employees');
            }
        } catch (error) {
            console.error('Error:', error);
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
                                <input type="checkbox" id="checkAll" />
                                <label htmlFor="checkAll"></label>
                            </th>
                            <th style={{ width: '150px' }}>입사일자</th>
                            <th style={{ width: '100px' }}>사원번호</th>
                            <th style={{ width: '100px' }}>성명</th>
                            <th style={{ width: '200px' }}>부서명</th>
                            <th style={{ width: '100px' }}>직급</th>
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
                                    <td>{employee.employeeNo}</td>
                                    <td>{employee.employeeName}</td>
                                    <td>{employee.departmentNo}</td>
                                    <td>{employee.rankNo}</td>
                                    <td>{employee.employeePhone}</td>
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
                    <button className="deleteBtn" >선택삭제</button>
                    <button className="submitBtn" onClick={() =>{
                        setShowPopup(true);
                        setDetail(true);}}>신규등록</button>
                </div>
                <div>페이징 넣을곳</div>
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
                                        <input type="text" id="department" name="department" value={newEmployee.department} onChange={handleFormChange} />
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
                                        <button type="button" id="selectBankBtn" className="btn attach-file" onClick={() => setShowBankPopup(true)}>은행선택</button>
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
                                    <button type="submit" className="btn modify">등록</button>
                                    <button type="button" className="btn close" onClick={() => setShowPopup(false)}>닫기</button>
                                </div>
                            </form>
                        </div>
                    </Draggable>
                </div>
            }

            {showBankPopup && (
                <div id="bankSelectPopup" className="popup">
                    <div className="popup-content">
                        <div className="popup-header">
                            <h3>은행 선택</h3>
                            <button className="btn close" onClick={() => setShowBankPopup(false)}>닫기</button>
                        </div>
                        <div className="bank-options">
                            <div className="bank-item" onClick={() => handleBankSelect('001', '한국은행')}>
                                <button className="bank-code-option">001</button>
                                <button className="bank-name-option">한국은행</button>
                            </div>
                            <div className="bank-item" onClick={() => handleBankSelect('002', '국민은행')}>
                                <button className="bank-code-option">002</button>
                                <button className="bank-name-option">국민은행</button>
                            </div>
                            {/* 추가 은행 항목들 */}
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
