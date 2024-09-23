package com.project.oneshot.controller;

import com.project.oneshot.command.DepartmentVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.MenuVO;
import com.project.oneshot.hr.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hrm")
public class DepartmentRestController {

    @Autowired
    @Qualifier("departmentService")
    private DepartmentService departmentService;

    /**
     * 부서 등록 API
     */
    @PostMapping("/registDepartment")
    public ResponseEntity<String> registDepartment(@RequestBody DepartmentVO vo) {
        System.out.println(vo.toString());
        if (departmentService.isDuplicateDepartment(vo.getDepartmentNo(), vo.getDepartmentName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("부서번호 또는 부서명이 이미 존재합니다.");
        }

        int result = departmentService.insertDepartment(vo); // 서비스에 부서 등록을 요청
        return result == 1 ? ResponseEntity.ok("부서 등록 성공") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("부서 등록 실패");
    }

    /**
     * 부서명 중복 확인 API
     */
    @GetMapping("/checkDuplicateDepartmentName/{departmentName}")
    public ResponseEntity<Boolean> checkDuplicateDepartmentName(@PathVariable String departmentName) {
        boolean isDuplicate = departmentService.isDuplicateDepartmentName(departmentName);
        return ResponseEntity.ok(isDuplicate);
    }

    /**
     * 부서번호 자동
     */
    @GetMapping("/getLastDepartmentNo")
    public ResponseEntity<Integer> getLastDepartmentNo() {
        Integer lastDepartmentNo = departmentService.getLastDepartmentNo();
        return ResponseEntity.ok(lastDepartmentNo != null ? lastDepartmentNo : 0);
    }

    /**
     * 부서 목록 조회 API
     */
    @GetMapping("/getDepartment")
    public List<DepartmentVO> getDepartment() {
        return departmentService.selectDepartment();
    }

    /**
     * 부서별 사원 목록 조회 API
     */
    @GetMapping("/getEmployees/{departmentNo}")
    public ResponseEntity<List<EmployeeVO>> getEmployeesByDepartment(@PathVariable("departmentNo") int departmentNo) {
        List<EmployeeVO> employees = departmentService.getEmployeesByDepartment(departmentNo);
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(employees);
    }

    /**
     * 부서 활성화/비활성화 상태 업데이트
     */
    @PutMapping("/updateDepartmentState")
    public ResponseEntity<String> updateDepartmentState(@RequestBody List<DepartmentVO> departments) {
        for (DepartmentVO department : departments) {
            boolean isUpdated = departmentService.updateDepartmentState(department.getDepartmentNo(), department.getDepartmentState());
            if (!isUpdated) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("부서 상태 업데이트 실패");
            }
        }
        return ResponseEntity.ok("부서 상태가 업데이트되었습니다.");
    }

    /**
     * 부서 상세 정보 업데이트 (부서번호는 수정되지 않음)
     */
    @PutMapping("/updateDepartmentDetails")
    public ResponseEntity<String> updateDepartmentDetails(@RequestBody DepartmentVO department) {
        // 부서번호는 수정하지 않음
        boolean isUpdated = departmentService.updateDepartmentDetails(department);
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("부서 정보 업데이트 실패");
        }
        return ResponseEntity.ok("부서 정보가 업데이트되었습니다.");
    }

    /**
     * 메뉴 목록 조회 API
     */
    @GetMapping("/getMenus")
    public List<MenuVO> getMenus() {
        return departmentService.selectMenus();
    }
}