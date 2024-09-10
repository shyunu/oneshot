import Draggable from "react-draggable";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ReactDOM from 'react-dom/client';
import './department.css';
import './popup.css';

function Department() {
    //모달창
    const [modalOpen, setModalOpen] = useState(false);
    //입력값
    const [formValues, setFormValues] = useState({
        departmentNo: '',
        departmentName: '',
        age: '',
    });
    //부서목록
    const [departments, setDepartments] = useState([]);

    //부서목록get
    useEffect(() => {
        (async () => {
            try {
                const response = await axios.get("http://localhost:8181/hrm/getDepartment");
                setDepartments(response.data);
            } catch (err) {
                console.error('Error fetching departments:', err);
            }
        })();
    }, []);
    //부서목록get
    const fetchDepartments = async () => {
        try {
            const response = await axios.get("http://localhost:8181/hrm/getDepartment");
            setDepartments(response.data);
        } catch (err) {
            console.error('Error fetching departments:', err);
        }
    };
    const [errors, setErrors] = useState({
        departmentNo: false,
        departmentName: false,
    });
    const [submitted, setSubmitted] = useState(false); //제출상태
    const validateForm = () => {
        let isValid = true;
        const newErrors = {
            departmentNo: !formValues.departmentNo.trim(),
            departmentName: !formValues.departmentName.trim(),
        };

        setErrors(newErrors);
        return (!Object.values(newErrors.departmentNo).includes(true))||(!Object.values(newErrors.departmentName).includes(true));
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

        const DepartmentVO = {
            departmentNo: formValues.departmentNo,
            departmentName: formValues.departmentName
        };

        try {
            const response = await axios.post(
                "http://localhost:8181/hrm/registDepartment",
                JSON.stringify(DepartmentVO),
                {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
            );
            setSubmitted(false); // 제출 성공 시, 오류 상태 초기화
            setModalOpen(false);
            alert("서버에 보내고 서버가 다시 보낸 데이터\n" + JSON.stringify(response.data));
            const data = await fetchDepartments();

        } catch (err) {
            if (err.response && err.response.data) {
                // 서버에서 반환한 JSON 형태의 유효성 검사 오류 메시지 처리
                const errorMessages = err.response.data;
                const newErrors = {
                    departmentNo: errorMessages.departmentNo || false,
                    departmentName: errorMessages.departmentName || false,
                };
                setErrors(newErrors);
            }
        }
    };


    return (
        <main className="wrapper">
            <div className="wrapper-title">
                <span>부서리스트</span>
                <button className="btn">Search</button>
            </div>

            {/* 상세 내용 */}
            <article>
                <table>
                    <thead>
                    <tr id="attribute">
                        <td>
                            <input type="checkbox" id="checkAll" />
                            <label htmlFor="checkAll"></label>
                        </td>
                        <td style={{ width: '150px' }}>부서번호</td>
                        <td style={{ width: '100px' }}>부서명</td>
                        <td style={{ width: '100px' }}>사용가능메뉴</td>
                        <td style={{ width: '200px' }}>사용여부</td>
                    </tr>
                    </thead>
                    <tbody>
                    {departments.map(department => (
                        <tr key={department.departmentNo}>
                            <td>
                                <input type="checkbox" id={`check${department.departmentNo}`} />
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
                    <button className="btn" onClick={() => setModalOpen(true)}>신규등록</button>
                    <button className="btn">선택삭제</button>
                </div>
            </div>

            {/* 모달창 */}
            {modalOpen &&

                    <div className="popup" id="contractPopup">
                        <Draggable bounds="body">
                        <div className="popup-content" id="draggablePopup">
                            <div className="popup-header" id="popupHeader">
                                <span>부서등록</span>
                            </div>
                            <form className="contract-form"  onSubmit={formSubmit}>
                                <div>
                                    <label htmlFor="departmentNo">부서번호</label>
                                    <input type="text" id="departmentNo" value={formValues.departmentNo} onChange={handleChange} placeholder="부서번호" className={submitted && errors.departmentNo ? 'input-error' : ''} />
                                </div>
                                <div>
                                    <label htmlFor="departmentName">부서명</label>
                                    <input type="text" id="departmentName" value={formValues.departmentName} onChange={handleChange} placeholder="부서명" className={submitted && errors.departmentName ? 'input-error' : ''}/>
                                </div>
                                <label htmlFor="department">사용가능메뉴</label>
                                <div className="flex">
                                    <span>
                                        <input type="checkbox" id="hrm"/>
                                        <label htmlFor="hrm">인사관리</label>
                                    </span>
                                    <span>
                                        <input type="checkbox" id="pm"/>
                                        <label htmlFor="pm">상품관리</label>
                                    </span>
                                    <span>
                                        <input type="checkbox" id="ssm"/>
                                        <label htmlFor="ssm">영업판매관리</label>
                                    </span>
                                    <span>
                                        <input type="checkbox" id="spm"/>
                                        <label htmlFor="spm">영업구매관리</label>
                                    </span>
                                </div>
                                <div className="popup-buttons">
                                    <button type="submit" className="popup-btn modify">저장</button>
                                    <button type="button" className="popup-btn close" id="closePopupButton"
                                            onClick={() => setModalOpen(false)}>닫기
                                    </button>
                                </div>
                            </form>
                        </div>
                        </Draggable>
                    </div>
            }
        </main>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Department/>
);
