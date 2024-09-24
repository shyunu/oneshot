package com.project.oneshot.hr.employee;

import com.project.oneshot.command.BankVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.PositionVO;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Map<String, Object> getAllEmployees(int page, int size); // 사원목록
    List<EmployeeVO> getSearchEmployees(EmployeeVO vo);

    List<BankVO> getAllBank(); // 은행목록
    List<PositionVO> getAllPosition(); // 은행목록

    public int insertEmployee(EmployeeVO employeeVo); // 신규생성
    public int updateResignEmployee(List<EmployeeVO> employeeNos); // 비활성
    int updateEmployee(EmployeeVO employeeVo); //사원 수정

}