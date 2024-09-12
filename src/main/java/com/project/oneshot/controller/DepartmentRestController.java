package com.project.oneshot.controller;

import com.project.oneshot.command.DepartmentVO;
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
    @Qualifier("humanResourceService")
    private DepartmentService departmentService;

    @PostMapping("/registDepartment")
    public String registDepartment(@RequestBody DepartmentVO vo) {
        System.out.println(vo.toString());
        int result = departmentService.insertDepartment(vo);
        return String.valueOf(result);
    }

    @GetMapping("/getDepartment")
    public List<DepartmentVO> getDepartment() {
        System.out.println("실행");
        List<DepartmentVO> list=departmentService.selectDepartment();
        for(DepartmentVO vo:list){
            System.out.println(vo.toString());
        }
        return list;
    }

    // 삭제
    @DeleteMapping("/deleteDepartments")
    public ResponseEntity<String> deleteDepartments(@RequestBody List<Integer> departmentNos) {
        try {
            int result = departmentService.deleteDepartments(departmentNos);
            if (result > 0) {
                return ResponseEntity.ok("삭제 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제할 부서가 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
        }
    }



}
