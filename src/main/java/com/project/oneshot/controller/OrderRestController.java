package com.project.oneshot.controller;

import com.project.oneshot.command.*;
import com.project.oneshot.sales.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/getEmployeeContent")
    public EmployeeVO getEmployeeContent(@RequestParam("employeeNo") int employeeNo) {
        System.out.println("employeeNo = " + employeeNo);
        EmployeeVO vo = orderService.getEmployeeContent(employeeNo);
        return vo;
    }

    @GetMapping("/getClientList")
    public List<ClientVO> getClientList() {

        List<ClientVO> list = orderService.getClientList();
        return list;
    }

    @GetMapping("getClientContent")
    public ClientVO getClientContent(@RequestParam("clientNo") int clientNo) {
        ClientVO vo = orderService.getClientContent(clientNo);
        return vo;
    }


    @GetMapping("/getProductList")
    public List<ContractVO> getProductList(int clientNo) {
        List<ContractVO> list = orderService.getProductList(clientNo);
        return list;
    }

    @GetMapping("/getProductPrice")
    public int getProductPrice(@RequestParam("clientNo") int clientNo, @RequestParam("productNo") int productNo) {

        int result = orderService.getProductPrice(clientNo, productNo);
        return result;
    }

    @GetMapping("/getInventoryQuantity")
    public int getInventoryQuantity(@RequestParam("productNo") int productNo) {
        return orderService.getInventoryQuantity(productNo);
    }

//
//    @GetMapping("/getCategory")
//    public List<CategoryVO> getCategory(int productNo){
//        List<CategoryVO> list = orderService.getCategory(productNo);
//        return list;
//    }


    @GetMapping("/getOrderItems")
    public List<OrderItemVO> getOrderItems(@RequestParam("orderHeaderNo") int orderHeaderNo) {
        return orderService.getOrderItemsByOrderHeaderNo(orderHeaderNo);
    }

    @GetMapping("/getOrderItemCount")
    public int getOrderItemCount(@RequestParam("orderHeaderNo") int orderHeaderNo) {
        return orderService.getOrderItemCount(orderHeaderNo);
    }

    // 분기별 판매 총액
    @GetMapping("/getQuarterlyOrderAmount")
    public List<Map<String, Object>> getQuarterlyOrderAmount() {
        return orderService.getQuarterlyOrderAmount();
    }
}
