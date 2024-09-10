package com.project.oneshot.hr.employee;

import com.project.oneshot.entity.mybatis.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeVO> getAllEmployees() {
        return employeeMapper.getAllEmployees();
    }

    @Override
    public EmployeeVO insertOrUpdateEmployee(EmployeeVO employeeVo) {
        employeeMapper.insertOrUpdateEmployee(employeeVo);
        return employeeVo;
    }

}