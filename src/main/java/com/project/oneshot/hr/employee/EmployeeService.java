package com.project.oneshot.hr.employee;

import com.project.oneshot.command.BankVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.PositionVO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeVO> getAllEmployees(); // 사원목록


    List<BankVO> getAllBank(); // 은행목록
    List<PositionVO> getAllPosition(); // 은행목록

    int insertEmployee(EmployeeVO employeeVo); // 신규생성
    public int updateEmployee(List<Integer> employeeNos); // 비활성
}