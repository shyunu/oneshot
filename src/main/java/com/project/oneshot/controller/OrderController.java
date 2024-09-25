package com.project.oneshot.controller;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.OrderItemVO;
import com.project.oneshot.command.OrderVO;
import com.project.oneshot.sales.order.OrderCriteria;
import com.project.oneshot.sales.order.OrderPageVO;
import com.project.oneshot.sales.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sales")
public class OrderController {

    @Autowired
    @Qualifier("orderService") //서비스연결
    private OrderService orderService;

    //조회하기
    @GetMapping("/order")
    public String orderList(OrderCriteria cri, Model model) {
        System.out.println("cri = " + cri);
        List<OrderVO> orderList = orderService.getList(cri);
        model.addAttribute("list", orderList);

        int totalCount = orderService.getTotalCount(cri);
        OrderPageVO pageVO = new OrderPageVO(cri, totalCount);
        model.addAttribute("pageVO", pageVO);

        List<ClientVO> clientList = orderService.getClientList();
        model.addAttribute("clientList", clientList);

        return "sales/order";
    }

    //등록하기
    @PostMapping("/orderForm")
    public String orderForm(@ModelAttribute OrderVO vo, RedirectAttributes ra) {
        orderService.orderRegist(vo);
        return "redirect:/sales/order";
    }

    //수정하기
    @PostMapping("/updateOrder")
    public String updateOrder(@ModelAttribute OrderVO vo) {
        orderService.updateStatus(vo);

        for(OrderItemVO item : vo.getOrderItems()) {
            item.setOrderHeaderNo(vo.getOrderHeaderNo());
            orderService.updateItem(item);
        }
        return "redirect:/sales/order";
    }

    //날짜변환
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // 엄격한 날짜 형식 검사
        binder.registerCustomEditor(Date.class, new org.springframework.beans.propertyeditors.CustomDateEditor(dateFormat, true)); // Allow empty dates
    }


}
