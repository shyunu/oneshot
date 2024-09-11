package com.project.oneshot.hr.employee;

import com.project.oneshot.entity.mybatis.BankVO;
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
    public List<BankVO> getAllBank() {
        return employeeMapper.getAllBank();
    }


    @Override
    public int insertEmployee(EmployeeVO vo) {
        return employeeMapper.insertEmployee(vo);
    }

}