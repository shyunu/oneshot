package com.project.oneshot.controller;

import com.project.oneshot.command.OrderVO;
import com.project.oneshot.sales.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class OrderController {

    @Autowired
    @Qualifier("orderService") //서비스연결
    private OrderService orderService;

    // ----- 판매내역 ----- //

    @GetMapping("/order")
    public String getOrderPage(Model model) {
        List<OrderVO> orderList = orderService.getList();
        model.addAttribute("list", orderList);
        return "sales/order";
    }


    @PostMapping("/orderForm")
    public String orderForm(@ModelAttribute OrderVO vo, RedirectAttributes ra) {
        int result = orderService.orderRegist(vo);

        if (result == 1) {
            ra.addFlashAttribute("msg", "정상 등록");
        } else {
            ra.addFlashAttribute("msg", "등록 실패");
        }

        return "redirect:/sales/order";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // 엄격한 날짜 형식 검사
        binder.registerCustomEditor(Date.class, new org.springframework.beans.propertyeditors.CustomDateEditor(dateFormat, true)); // Allow empty dates
    }

}
