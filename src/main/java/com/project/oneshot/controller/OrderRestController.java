package com.project.oneshot.controller;

import com.project.oneshot.command.*;
import com.project.oneshot.sales.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class OrderRestController {

    @Autowired
    OrderService orderService;

    @GetMapping("/getEmployeeList")
    public List<EmployeeVO> getEmployeeList() {

        List<EmployeeVO> list = orderService.getEmployeeList();
        return list;
    }


    @GetMapping("/getClientList")
    public List<ClientVO> getClientList() {

        List<ClientVO> list = orderService.getClientList();
        return list;
    }

    @GetMapping("getClientContent")
    public ClientVO getClientContent(@RequestParam("clientNo") int clientNo) {
        ClientVO vo = orderService.getClientContent(clientNo);
//        System.out.println("vo = " + vo);
        return vo;
    }

    @GetMapping("/getProductList")
    public List<ContractVO> getProductList(@RequestParam("clientNo") int clientNo) {

        List<ContractVO> list = orderService.getProductList(clientNo);
        return list;
    }

}
