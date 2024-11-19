package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LeaveRequestVO {
    private Integer leaveRequestId;   // 휴가 신청 ID
    private Integer employeeNo;       // 사원 번호 (employee 테이블과 연결)
    private String employeeName;      // 사원 이름
    private Integer leaveTypeId;      // 휴가 종류 ID (leave_type 테이블과 연결)
    private String leaveTypeName;     // 휴가 종류 이름 (연차, 병가 등)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;      // 휴가 시작일

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;        // 휴가 종료일

    private LocalDate requestDate;  // 휴가 신청일
    private String status;              // 신청 상태 (대기 중, 승인됨, 거절됨)

    private Integer approverNo;         // 승인자 번호 (employee 테이블과 연결)
    private String approverName;        // 승인자 이름
    private Date approvalDate;          // 승인 날짜
    private Integer remainingDays;      // 잔여 휴가 일수
    private String remarks;             // 비고

}
