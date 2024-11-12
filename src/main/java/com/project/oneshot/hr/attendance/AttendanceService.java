package com.project.oneshot.hr.attendance;

import com.project.oneshot.command.LeaveRequestVO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AttendanceService {
    List<LeaveRequestVO> getLeaveRequests();
    void submitLeaveRequest(LeaveRequestVO leaveRequest);
    void updateLeaveStatus(int leaveRequestId, String newStatus, Integer approverNo, String remarks, LocalDate approvalDate);
    boolean isUserInHRDepartment(int employeeNo);

    List<LeaveRequestVO> getFilteredLeaveRequests(String employeeName, Integer leaveType, String status, String requestDate, String approvalDate);

    // 승인된 휴가 목록을 반환하는 메서드
    List<LeaveRequestVO> getApprovedLeaves();

}
