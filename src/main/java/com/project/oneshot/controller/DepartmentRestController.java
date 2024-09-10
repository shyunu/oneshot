package com.project.oneshot.controller;

import com.project.oneshot.entity.mybatis.DepartmentVO;
import com.project.oneshot.hr.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hrm")
public class DepartmentRestController {

    @Autowired
    @Qualifier("humanResourceService")
    private DepartmentService humanResourceService;

    @PostMapping("/registDepartment")
    public String registDepartment(@RequestBody DepartmentVO vo) {

        int result = humanResourceService.departmentInsert(vo);
        return String.valueOf(result);
    }

}
