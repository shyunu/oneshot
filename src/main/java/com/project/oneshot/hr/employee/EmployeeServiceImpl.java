package com.project.oneshot.hr.employee;

import com.project.oneshot.entity.mybatis.EmployeeVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{
    @Override
    public List<EmployeeVO> getAllEmployees() {
        return List.of();
    }

    @Override
    public EmployeeVO createOrUpdateEmployee(EmployeeVO employeeVo) {
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {

    }
}