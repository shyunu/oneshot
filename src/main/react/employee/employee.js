import React, {useState, useEffect} from 'react';
import ReactDOM from "react-dom/client";
import './employee.css'


function Employee(){
    return (
        <main className="wrapper">
            <div className="wrapper-title">
                <span>인사카드</span>
                <button className="btn">Search</button>
            </div>

            {/*상세 내용*/}
            <article>
                <table>
                    <tr id="attribute">
                        <td>
                            <input type="checkbox" id="checkAll"/>
                            <label htmlFor="checkAll"></label>
                        </td>
                        <td style={{width: '150px'}}>입사일자</td>
                        <td style={{width: '100px'}}>사원번호</td>
                        <td style={{width: '100px'}}>성명</td>
                        <td style={{width: '200px'}}>부서명</td>
                        <td style={{width: '100px'}}>직급</td>
                        <td style={{width: '200px'}}>전화번호</td>
                        <td style={{width: '200px'}}>비상연락처</td>
                        <td style={{width: '250px'}}>이메일</td>
                        <td style={{width: '250px'}}>계좌번호</td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check1"/>
                            <label htmlFor="check1"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                        사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check2"/>
                            <label htmlFor="check2"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check3"/>
                            <label htmlFor="check3"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check4"/>
                            <label htmlFor="check4"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check5"/>
                            <label htmlFor="check5"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check6"/>
                            <label htmlFor="check6"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check7"/>
                            <label htmlFor="check7"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check8"/>
                            <label htmlFor="check8"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check9"/>
                            <label htmlFor="check9"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check10"/>
                            <label htmlFor="check10"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check11"/>
                            <label htmlFor="check11"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="checkbox" id="check12"/>
                            <label htmlFor="check12"></label>
                        </td>
                        <td>
                            2021/06/15
                        </td>
                        <td>
                            0001
                        </td>
                        <td>
                            홍길동
                        </td>
                        <td>
                            영업팀
                        </td>
                        <td>
                            사원
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            010-1234-1234
                        </td>
                        <td>
                            test@test.com
                        </td>
                        <td>
                            123-456789-01-001
                        </td>
                    </tr>
                </table>
            </article>

            <div className="wrapper-footer flex">
                <div>
                    <button className="btn">선택삭제</button>
                    <button className="btn">신규등록</button>
                </div>
                <div>페이징 넣을곳</div>
            </div>
        </main>
    );
}
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Employee/>
);