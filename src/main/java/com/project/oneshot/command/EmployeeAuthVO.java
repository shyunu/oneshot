package com.project.oneshot.command;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data // getter, setter, toString, equals, hashCode 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 생성
public class EmployeeAuthVO {
    private int employeeNo;
    private String employeePassword;  // 사원비밀번호 - 초기값 생년월일 YYMMDD
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate passwordUpdatedAt;
    
    private String employeeName;          // 사원이름
    private Integer departmentNo;         // 부서번호
    private String departmentName;        // 부서명
    private int positionNo;               // 직급번호  
    private String positionName;          // 직급명  
    
    private String positionNameEnglish; //직급 권한
    private List<String> menuNameEnglish; //소속한 부서에 해당하는 사용가능한 메뉴

}
