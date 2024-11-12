package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LeaveBalanceVO {
    private Integer employeeNo;       // 사원 번호 (employee 테이블과 연결)
    private String employeeName;      // 사원 이름

    private Integer leaveTypeId;      // 휴가 종류 ID (leave_type 테이블과 연결)
    private String leaveTypeName;     // 휴가 종류 이름 (연차, 병가 등)

    private Integer totalLeaves;      // 총 휴가 일수
    private Integer usedLeaves;       // 사용한 휴가 일수
    private Integer remainingLeaves;  // 남은 휴가 일수
}
