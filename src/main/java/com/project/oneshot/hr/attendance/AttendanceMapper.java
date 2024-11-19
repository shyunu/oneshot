package com.project.oneshot.hr.attendance;

import com.project.oneshot.command.LeaveRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AttendanceMapper {
    List<LeaveRequestVO> getLeaveRequests();
    void insertLeaveRequest(LeaveRequestVO leaveRequest);

    void updateLeaveStatus(Map<String, Object> params);  // Map을 사용해 여러 파라미터 전달

    List<LeaveRequestVO> getFilteredLeaveRequests(Map<String, Object> params);

    // 승인된 휴가 목록을 반환하는 메서드
    List<LeaveRequestVO> getApprovedLeaves();
}
