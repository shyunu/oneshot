package com.project.oneshot.hr.employee;

import com.project.oneshot.command.BankVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.PositionVO;
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
    public List<PositionVO> getAllPosition() {
        return employeeMapper.getAllPosition();
    }

    @Override
    public int insertEmployee(EmployeeVO vo) {
        return employeeMapper.insertEmployee(vo);
    }

    @Override
    public int updateEmployee(List<Integer> employeeNos) {
        return employeeMapper.updateEmployee(employeeNos);
    }


}