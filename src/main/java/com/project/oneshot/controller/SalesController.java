package com.project.oneshot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @GetMapping("/contract")
    public String contract() {
        return "sales/contract";
    }

    @GetMapping("/order")
    public String order() {
        return "sales/order";
    }

}
