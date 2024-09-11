package com.project.oneshot.entity.mybatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class EmployeeVO {

    private Integer employeeNo;           // 사원번호
    private Integer departmentNo;         // 부서번호
    private Integer rankNo;               // 직급번호
    private String employeeName;         // 사원이름
    private LocalDate employeeBirth;        // 사원생년월일
    private String employeePhone;        // 사원핸드폰번호
    private String emergencyPhone;       // 사원비상연락망
    private String employeeAddress;      // 사원집주소
    private Integer bankNo;
    private String accountNumber;        // 계좌번호
    private LocalDate employeeHiredate;     // 입사일
    private String employeeStatus;       // 퇴사여부
    private String employeeEmail;        // 사원이메일
    private String employeePhotoPath;    // 사원사진경로
    private String accountHolder;
}