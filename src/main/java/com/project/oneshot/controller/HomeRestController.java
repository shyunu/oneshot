package com.project.oneshot.controller;


import com.project.oneshot.command.DepartmentVO;
import com.project.oneshot.hr.department.DepartmentService;
import com.project.oneshot.sales.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/common")
public class HomeRestController {


    @Autowired
    @Qualifier("departmentService")
    private DepartmentService departmentService;

    @Autowired
    OrderService orderService;

    /**
     * 활성화된 부서 조회 API
     */
    @GetMapping("/getActiveDepartments")
    public List<DepartmentVO> getActiveDepartments() {
        return departmentService.getActiveDepartments();
    }

    /**
     * 활성화된 부서의 직급별 사원의 인원 조회 API
     */
    @GetMapping("/countDeptPosEmployees")
    public List<Map<String, Object>> countDeptPosEmployees() {
        return departmentService.countDeptPosEmployees();
    }

    // 분기별 판매 총액
    @GetMapping("/getQuarterlyOrderAmount")
    public List<Map<String, Object>> getQuarterlyOrderAmount() {
        return orderService.getQuarterlyOrderAmount();
    }
}
