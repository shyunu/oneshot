package com.project.oneshot.hr.employee;

import com.project.oneshot.command.BankVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.PositionVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EmployeeMapper {

    // 모든 활성화된 사원 조회
    public List<EmployeeVO> getAllEmployees();

    // 사원 생성
    public int insertEmployee(EmployeeVO employeeVo);

    // 은행 목록 조회
    public List<BankVO> getAllBank();
    // 직급 목록 조회
    public List<PositionVO> getAllPosition();

    // 직원 비활성화
    public int updateEmployee(List<Integer> employeeNos);

}