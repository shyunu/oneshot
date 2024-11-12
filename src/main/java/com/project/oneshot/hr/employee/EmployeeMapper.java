package com.project.oneshot.hr.employee;

import com.project.oneshot.command.BankVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.PositionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {

    // 모든 사원 조회
    public List<EmployeeVO> getAllEmployees(Map<String, Object> params);

    // 사원 총인원
    int getTotalEmployeeCount();

    // 사원 검색
    public List<EmployeeVO> getSearchEmployees(EmployeeVO employeeVO);

    // 사원 생성
    public int insertEmployee(EmployeeVO employeeVo);

    // 사원 수정
    public int updateEmployee(EmployeeVO employeeVo);

    // 은행 목록 조회
    public List<BankVO> getAllBank();
    // 직급 목록 조회
    public List<PositionVO> getAllPosition();

    // 직원 비활성화
    public int updateResignEmployee(List<EmployeeVO> employeeNos);

    // 인사팀 소속 직원 조회 (근태관리)
    List<EmployeeVO> getHrTeamEmployees();
    Integer getDepartmentNoByEmployeeNo(int employeeNo);

    // username으로 employeeNo 조회 (근태관리)
    EmployeeVO findEmployeeByUsername(String username);

}