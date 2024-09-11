package com.project.oneshot.hr.employee;

import com.project.oneshot.entity.mybatis.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EmployeeMapper {

    // 모든 사원 조회
    public List<EmployeeVO> getAllEmployees();

    // 사원 생성
    public int insertEmployee(EmployeeVO employeeVo);

}