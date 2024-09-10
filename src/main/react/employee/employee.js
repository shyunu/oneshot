import React, { useState } from 'react';
import ReactDOM from 'react-dom/client';
import './employee.css';
import './one.css';
import './delete.css';
import Draggable from 'react-draggable';

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

    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setNewEmployee(prev => ({ ...prev, [name]: value }));
    };

    const handleFormSubmit = (e) => {
        e.preventDefault();
        console.log('Form submitted:', newEmployee);
        setShowPopup(false);
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

            {showPopup &&
                <div className="popup" id="contractPopup">
                    <Draggable bounds="body">
                        <div className="popup-content" id="draggablePopup">
                            <div className="popup-header" id="popupHeader">
                                <span>사원 상세정보</span>
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
                                        <input type="text" id="employeeName" name="name" placeholder="선우수현" value={newEmployee.name} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="employeeNo">사원번호</label>
                                        <input type="text" id="employeeNo" name="id" placeholder="074" value={newEmployee.id} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="department">부서</label>
                                        <input type="text" id="department" name="department" placeholder="개발팀" value={newEmployee.department} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="employeeRank">직급</label>
                                        <input type="text" id="employeeRank" name="position" placeholder="대리" value={newEmployee.position} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="employeeHiredate">입사일</label>
                                        <input type="date" id="employeeHiredate" name="joinDate" value={newEmployee.joinDate} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="employeePhone">전화번호</label>
                                        <input type="text" id="employeePhone" name="phone" placeholder="010-5484-4454" value={newEmployee.phone} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="emergencyPhone">비상연락망</label>
                                        <input type="text" id="emergencyPhone" name="emergencyContact" placeholder="02-784-1141" value={newEmployee.emergencyContact} onChange={handleFormChange} />
                                    </div>
                                </div>
                                <div className="formBox2">
                                    <div>
                                        <label htmlFor="employeeBirth">생년월일</label>
                                        <input type="text" id="employeeBirth" name="birthDate" placeholder="1947-4-1" />
                                    </div>
                                    <div>
                                        <label htmlFor="employeeEmail">이메일</label>
                                        <input type="text" id="employeeEmail" name="email" placeholder="skatngus7@naver.com" value={newEmployee.email} onChange={handleFormChange} />
                                    </div>
                                    <div>
                                        <label htmlFor="accountNumber">은행/계좌번호/예금주</label>
                                        <button type="button" id="selectBankBtn" className="btn attach-file" onClick={() => setShowBankPopup(true)}>은행선택</button>
                                        <input type="text" id="accountBank" name="bank" placeholder="은행 선택" value={newEmployee.bank} readOnly />
                                        <input type="text" id="accountNumber" name="accountNumber" placeholder="817745184522" value={newEmployee.accountNumber} onChange={handleFormChange} />
                                        <input type="text" id="accountHolder" name="accountHolder" placeholder="선우수현" />
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
                                    <tr>
                                        <td>김철수</td>
                                        <td>001</td>
                                        <td>개발팀</td>
                                        <td>대리</td>
                                    </tr>
                                    <tr>
                                        <td>박영희</td>
                                        <td>002</td>
                                        <td>인사과</td>
                                        <td>과장</td>
                                    </tr>
                                    {/* 추가 직원 데이터 */}
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
