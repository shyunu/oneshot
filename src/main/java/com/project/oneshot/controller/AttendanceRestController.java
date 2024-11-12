package com.project.oneshot.controller;

import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.LeaveRequestVO;
import com.project.oneshot.hr.attendance.AttendanceService;
import com.project.oneshot.hr.employee.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hrm/attendance")
public class AttendanceRestController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeMapper employeeMapper;

    // 휴가 신청 목록 조회, 필터 기능
    @GetMapping("/leaves")
    public List<LeaveRequestVO> getLeaveRequests(
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) String leaveType, // 여전히 String으로 받아, Integer로 변환
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String requestDate,
            @RequestParam(required = false) String approvalDate) {
        try {
            Integer leaveTypeId = null;
            if (leaveType != null && !leaveType.isEmpty()) {
                try {
                    leaveTypeId = Integer.parseInt(leaveType); // String을 Integer로 변환
                } catch (NumberFormatException e) {
                    System.out.println("Invalid leaveType format: " + leaveType);
                    throw new IllegalArgumentException("휴가 종류가 잘못되었습니다.");
                }
            }
            return attendanceService.getFilteredLeaveRequests(employeeName, leaveTypeId, status, requestDate, approvalDate);
        } catch (Exception e) {
            System.out.println("Error fetching leave requests with filters: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch leave requests with filters", e);
        }
    }

    // 휴가 신청 등록
    @PostMapping("/leaves")
    public void submitLeaveRequest(@RequestBody LeaveRequestVO leaveRequest) {
        if (leaveRequest.getEmployeeNo() == null || leaveRequest.getLeaveTypeId() == null) {
            throw new IllegalArgumentException("사원 번호와 휴가 종류는 필수입니다.");
        }

        // 요청 데이터가 올바르게 도착하는지 확인
        System.out.println("Request received for leave: " + leaveRequest);

        leaveRequest.setRequestDate(LocalDate.now()); // 신청일 설정
        attendanceService.submitLeaveRequest(leaveRequest);
    }

    // 휴가 상태 업데이트
    @PutMapping("/leaves/{id}/status")
    public ResponseEntity<String> updateLeaveStatus(
            @PathVariable("id") int leaveRequestId,
            @RequestBody Map<String, Object> payload) {

        String newStatus = (String) payload.get("newStatus");
        String remarks = (String) payload.get("remarks");

        // 거절 사유가 9글자를 넘으면 명확한 오류 메시지를 반환
        if ("Rejected".equals(newStatus) && remarks != null && remarks.length() > 9) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("거절 사유는 9글자 이하로 입력해 주세요.");
        }

        LocalDate approvalDate = LocalDate.now();

        try {
            attendanceService.updateLeaveStatus(leaveRequestId, newStatus, null, remarks, approvalDate);
            return ResponseEntity.ok("상태가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 처리 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/api/auth/user")
    public ResponseEntity<EmployeeVO> getLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        EmployeeVO employee = employeeMapper.findEmployeeByUsername(username);  // 로그인한 사용자의 정보 조회
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/auth/user")
    public ResponseEntity<?> getAuthenticatedUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        EmployeeVO employee = employeeMapper.findEmployeeByUsername(userDetails.getUsername());

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("employeeNo", employee.getEmployeeNo());
        userInfo.put("username", userDetails.getUsername());
        userInfo.put("departmentName", employee.getDepartmentName()); // departmentName 추가
        userInfo.put("authorities", userDetails.getAuthorities());

        return ResponseEntity.ok(userInfo);
    }

    // 승인된 휴가 목록 반환 API
    @GetMapping("/approvedLeaves")
    public ResponseEntity<List<LeaveRequestVO>> getApprovedLeaves() {
        List<LeaveRequestVO> approvedLeaves = attendanceService.getApprovedLeaves();
        System.out.println("Approved leaves: " + approvedLeaves);
        return ResponseEntity.ok(approvedLeaves);
    }

}
