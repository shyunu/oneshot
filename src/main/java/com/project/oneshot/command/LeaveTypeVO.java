package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LeaveTypeVO {
    private Integer leaveTypeId; // 휴가 종류 ID
    private String leaveName;    // 휴가 이름 (연차, 병가 등)
}
