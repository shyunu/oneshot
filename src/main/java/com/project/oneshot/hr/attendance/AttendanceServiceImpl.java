package com.project.oneshot.hr.attendance;

import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.LeaveRequestVO;
import com.project.oneshot.hr.employee.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;


import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<LeaveRequestVO> getLeaveRequests() {
        return attendanceMapper.getLeaveRequests();
    }

    // 휴가 신청 등록
    @Override
    public void submitLeaveRequest(LeaveRequestVO leaveRequest) {
        leaveRequest.setRequestDate(LocalDate.now());  // 신청일을 현재 날짜로 설정
        attendanceMapper.insertLeaveRequest(leaveRequest);
    }

    @Override
    public void updateLeaveStatus(int leaveRequestId, String newStatus, Integer approverEmployeeNo, String remarks, LocalDate approvalDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("leaveRequestId", leaveRequestId);
        params.put("newStatus", newStatus);
        params.put("approvalDate", approvalDate);
        params.put("approverNo", approverEmployeeNo); // 현재 로그인한 사용자의 employee_no
        if ("Rejected".equals(newStatus)) {
            params.put("remarks", remarks);
        }

        attendanceMapper.updateLeaveStatus(params);
    }

    @Override
    public boolean isUserInHRDepartment(int employeeNo) {
        List<EmployeeVO> hrTeamEmployees = employeeMapper.getHrTeamEmployees();
        return hrTeamEmployees.stream()
                .anyMatch(employee -> employee.getEmployeeNo() == employeeNo);
    }

    @Override
    public List<LeaveRequestVO> getFilteredLeaveRequests(String employeeName, Integer leaveType, String status, String requestDate, String approvalDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("employeeName", employeeName);
        params.put("leaveType", leaveType);  // Integer로 저장
        params.put("status", status);
        params.put("requestDate", requestDate);
        params.put("approvalDate", approvalDate);
        return attendanceMapper.getFilteredLeaveRequests(params);
    }

    // 승인된 휴가 목록을 가져오는 메서드
    @Override
    public List<LeaveRequestVO> getApprovedLeaves() {
        return attendanceMapper.getApprovedLeaves();
    }

}

