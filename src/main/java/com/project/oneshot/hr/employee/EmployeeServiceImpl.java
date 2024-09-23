package com.project.oneshot.hr.employee;

import com.project.oneshot.command.BankVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.PositionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Map<String, Object> getAllEmployees(int page, int size) {
        int offset = (page-1) * size;
        Map<String, Object> params = new HashMap<>();
        params.put("limit", size);
        params.put("offset", offset);
        List<EmployeeVO> employees =  employeeMapper.getAllEmployees(params);

        int totalEmployeeCount = employeeMapper.getTotalEmployeeCount();
        int totalPages = (int) Math.ceil((double) totalEmployeeCount / size);

        Map<String, Object> result = new HashMap<>();
        result.put("employees", employees);
        result.put("totalPages", totalPages);
        return result;
    }

    @Override
    public List<EmployeeVO> getSearchEmployees(EmployeeVO vo) {return  employeeMapper.getSearchEmployees(vo);}

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
    public int updateEmployee(EmployeeVO vo) {
        System.out.println(vo.toString());
        return employeeMapper.updateEmployee(vo);
    }

    @Override
    public int updateResignEmployee(List<EmployeeVO> employeeNos) {
        return employeeMapper.updateResignEmployee(employeeNos);
    }


}