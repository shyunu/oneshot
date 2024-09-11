package com.project.oneshot.hr.employee;


import com.project.oneshot.entity.mybatis.EmployeeVO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeVO> getAllEmployees(); // 사원목록

    int insertEmployee(EmployeeVO employeeVo); // 신규생성

}