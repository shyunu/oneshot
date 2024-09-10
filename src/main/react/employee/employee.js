import React, { useState } from 'react';
import ReactDOM from 'react-dom/client';
import './employee.css';
import './one.css';
import './delete.css';
import Draggable from 'react-draggable';
import axios from 'axios';

function Employee() {
    const [showPopup, setShowPopup] = useState(false);
    const [showBankPopup, setShowBankPopup] = useState(false);
    const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
    const [showFinalWarning, setShowFinalWarning] = useState(false);
    const [newEmployee, setNewEmployee] = useState({
        id: '',
        joinDate: '',
        name: '',
        department: '',
        position: '',
        phone: '',
        emergencyContact: '',
        email: '',
        accountNumber: '',
        bank: '',
        address: '',
        addressDetail: ''
    });
    const [bankList, setBankList] = useState([]);

    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setNewEmployee(prev => ({ ...prev, [name]: value }));
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('/api/employees', newEmployee);
            alert('사원이 등록되었습니다.');
            setShowPopup(false);
        } catch (error) {
            console.error('Error submitting form:', error);
            alert('사원 등록에 실패했습니다.');
        }
    };

    const fetchBankList = async () => {
        try {
            const response = await axios.get('/api/banks'); // 은행 목록 API 호출
            setBankList(response.data);
        } catch (error) {
            console.error('Error fetching bank list:', error);
        }
    };

    const handleBankSelect = (bankCode, bankName) => {
        setNewEmployee(prev => ({ ...prev, bank: bankName }));
        setShowBankPopup(false);
    };

    const handleDeleteClick = () => {
        setShowDeleteConfirmation(true);
    };

    const handleNextClick = () => {
        setShowDeleteConfirmation(false);
        setShowFinalWarning(true);
    };

    const handleDelete = () => {
        // 실제 삭제 로직을 여기에 구현합니다.
        alert('항목이 삭제되었습니다.');
        setShowFinalWarning(false);
    };

    const handleBackClick = () => {
        setShowFinalWarning(false);
        setShowDeleteConfirmation(true);
    };

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
                        {/* 데이터 표시 부분 */}
                    </tbody>
                </table>
            </article>

            <div className="wrapper-footer flex">
                <div>
                    <button className="deleteBtn" onClick={handleDeleteClick}>선택삭제</button>
                    <button className="submitBtn" onClick={() => setShowPopup(true)}>신규등록</button>
                </div>
                <div>페이징 넣을곳</div>
            </div>

            {showPopup && (
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
                                        <input type="text" id="employeeName" name="name" value={newEmployee.name} onChange={handleFormChange} required />
                                    </div>
                                    <div>
                                        <label htmlFor="employeeNo">사원번호</label>
                                        <input type="text" id="employeeNo" name="id" value={newEmployee.id} onChange={handleFormChange} required />
                                    </div>
                                    <div>
                                        <label htmlFor="department">부서</label>
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
                                        <input type="date" id="employeeBirth" name="birthDate" onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="employeeEmail">이메일</label>
                                        <input type="email" id="employeeEmail" name="email" value={newEmployee.email} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="accountNumber">은행/계좌번호/예금주</label>
                                        <button type="button" id="selectBankBtn" className="btn attach-file" onClick={fetchBankList}>은행선택</button>
                                        <input type="text" id="accountBank" name="bank" placeholder="은행 선택" value={newEmployee.bank} readOnly />
                                        <input type="text" id="accountNumber" name="accountNumber" placeholder="계좌번호" value={newEmployee.accountNumber} onChange={handleFormChange} />
                                        <input type="text" id="accountHolder" name="accountHolder" placeholder="예금주" />
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
            )}

            {showBankPopup && (
                <div id="bankSelectPopup" className="popup">
                    <div className="popup-content">
                        <div className="popup-header">
                            <h3>은행 선택</h3>
                            <button className="btn close" onClick={() => setShowBankPopup(false)}>닫기</button>
                        </div>
                        <div className="bank-options">
                            {bankList.map(bank => (
                                <div key={bank.code} className="bank-item" onClick={() => handleBankSelect(bank.code, bank.name)}>
                                    <button className="bank-code-option">{bank.code}</button>
                                    <button className="bank-name-option">{bank.name}</button>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            )}

            {showDeleteConfirmation && (
                <div id="popupDeleteConfirmation" className="popup">
                    <div className="popup-content">
                        <div className="popup-header">
                            <span>삭제 여부 확인</span>
                        </div>
                        <div className="popup-body">
                            <table className="employee-table">
                                <thead>
                                    <tr>
                                        <th>이름</th>
                                        <th>사원번호</th>
                                        <th>부서명</th>
                                        <th>직급</th>
                                    </tr>
                                </thead>
                                <tbody id="employeeDataTable">
                                    {/* 삭제할 직원 데이터 */}
                                </tbody>
                            </table>
                        </div>
                        <div className="popup-buttons">
                            <button type="button" className="btn-next" id="buttonNext" onClick={handleNextClick}>다음</button>
                            <button type="button" className="btn-close" id="buttonClosePopup" onClick={() => setShowDeleteConfirmation(false)}>닫기</button>
                        </div>
                    </div>
                </div>
            )}

            {showFinalWarning && (
                <div id="popupFinalWarning" className="popup">
                    <div className="popup-content">
                        <div className="popup-header">
                            <span>삭제 확인</span>
                        </div>
                        <div className="popup-body">
                            <p>선택한 정보를 정말로 삭제하시겠습니까?</p>
                        </div>
                        <div className="popup-buttons">
                            <button type="button" className="btn-delete" id="buttonDelete" onClick={handleDelete}>삭제</button>
                            <button type="button" className="btn-back" id="buttonBack" onClick={handleBackClick}>뒤로</button>
                            <button type="button" className="btn-close" id="buttonFinalClose" onClick={() => setShowFinalWarning(false)}>닫기</button>
                        </div>
                    </div>
                </div>
            )}
        </main>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<Employee />);