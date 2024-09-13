package com.project.oneshot.controller;

import com.project.oneshot.command.ContractVO;
import com.project.oneshot.sales.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sales")
public class OrderController {

    @Autowired
    @Qualifier("orderService") //서비스연결
    private OrderService orderService;

    // ----- 판매내역 ----- //
    @GetMapping("/order")
    public String order() { //--- 판매
        return "sales/order";
    }

    @PostMapping("/orderForm")
    public String orderForm(ContractVO vo, RedirectAttributes ra) { //--- 판매 등록하기
        return "redirect:/sales/order";
    }

}
