package com.project.oneshot.controller;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.sales.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class OrderRestController {

    @Autowired
    OrderService orderService;

    @GetMapping("/getClientList")
    public List<ClientVO> getClientList() {

        List<ClientVO> list = orderService.getClientList();

        System.out.println("아무거나");

        return list;
    }



}
