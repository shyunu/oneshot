package com.project.oneshot.controller;

import com.project.oneshot.command.BankVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.PositionVO;
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
    @GetMapping("/getEmployee")
    public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
        List<EmployeeVO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    // 은행 목록 조회
    @GetMapping("/getBank")
    public ResponseEntity<List<BankVO>> getAllBank() {
        List<BankVO> banks = employeeService.getAllBank();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }
    // 직급 목록 조회
    @GetMapping("/getPosition")
    public ResponseEntity<List<PositionVO>> getAllPosition() {
        List<PositionVO> positions = employeeService.getAllPosition();
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    // 사원 등록
    @PostMapping("/registEmployee")
    public int insertOrUpdateEmployee(@RequestBody EmployeeVO vo) {
        return employeeService.insertEmployee(vo);
    }
    
    // 사원 비활성화
    @DeleteMapping("/updateEmployee")
    public ResponseEntity<String> updateEmployee(@RequestBody List<Integer> employeeNos) {
        try {
            int result = employeeService.updateEmployee(employeeNos);
            if (result > 0) {
                return ResponseEntity.ok("삭제 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제할 사원이 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
        }
    }

}