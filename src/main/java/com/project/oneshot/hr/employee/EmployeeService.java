package com.project.oneshot.hr.employee;


import com.project.oneshot.entity.mybatis.EmployeeVO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeVO> getAllEmployees(); // 사원목록

    EmployeeVO createOrUpdateEmployee(EmployeeVO employeeVo); // 신규생성, 사원정보업데이트

    void deleteEmployee(Long id); // 사원삭제
}