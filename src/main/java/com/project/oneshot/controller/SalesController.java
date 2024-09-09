package com.project.oneshot.controller;

import com.project.oneshot.sales.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    @Qualifier("SalesService") //서비스연결
    private SalesService salesService;

    @GetMapping("/contract")
    public String contract() { //--- 계약
        return "sales/contract";
    }

    @PostMapping("/registForm")
    public String registForm() { //--- 계약 등록하기

        return "redirect:/sales/contract";
    }






    ///////////////////////
    @GetMapping("/order")
    public String order() { //--- 판매
        return "sales/order";
    }

}
