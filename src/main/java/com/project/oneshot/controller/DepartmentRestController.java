package com.project.oneshot.controller;

import com.project.oneshot.entity.mybatis.DepartmentVO;
import com.project.oneshot.hr.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

}
