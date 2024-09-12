package com.project.oneshot.controller;

import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.hr.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hrm")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    // 사원 목록 조회
    @GetMapping
    public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
        List<EmployeeVO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // 사원 등록
    @PostMapping("/registEmployee")
    public int insertOrUpdateEmployee(@RequestBody EmployeeVO vo) {
        return employeeService.insertEmployee(vo);
    }

}