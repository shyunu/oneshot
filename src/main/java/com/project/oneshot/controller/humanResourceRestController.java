package com.project.oneshot.controller;


import com.project.oneshot.entity.DepartmentVO;
import com.project.oneshot.humanResource.HumanResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;

@RestController
@RequestMapping("/hrm")
public class humanResourceRestController {

    @Autowired
    @Qualifier("humanResourceService")
    private HumanResourceService humanResourceService;

    @PostMapping("/registDepartment")
    public String registDepartment(@RequestBody DepartmentVO vo) {

        int result = humanResourceService.departmentInsert(vo);
        return String.valueOf(result);
    }

}
