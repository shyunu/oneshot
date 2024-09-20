package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeVO {
    private Integer employeeNo;           // 사원번호
    private String employeeName;          // 사원이름
    private String employeePassword;      // 사원비밀번호 - 초기값 생년월일 YYMMDD
    private Integer departmentNo;         // 부서번호
    private String departmentName;        // 부서명
    private Integer positionNo;           // 직급번호
    private String positionName;         // 직급명

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate employeeBirth;      // 사원생년월일
    private String employeePhone;         // 사원핸드폰번호
    private String emergencyPhone;        // 사원비상연락망
    private String employeeAddress;       // 사원집 주소
    private String employeeAddressDetail; // 사원집 상세주소
    private Integer bankNo;               // 은행코드
    private String accountNumber;         // 계좌번호
    private String accountHolder;         // 게좌주

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate employeeHiredate;   // 입사일
    private String employeeEmail;         // 사원이메일
    private String employeePhotoPath;     // 사원사진경로
    private String employeeStatus;        // 퇴사여부
}