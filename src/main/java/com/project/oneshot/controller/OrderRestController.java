package com.project.oneshot.controller;

import com.project.oneshot.command.*;
import com.project.oneshot.sales.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity<Map<String, Object>> getProductPrice(@RequestParam("clientNo") int clientNo, @RequestParam("productNo") int productNo) {
        Map<String, Object> response = new HashMap<>();

        // 가격 정보 가져오기
        int contractPrice = orderService.getProductPrice(clientNo, productNo);

        // 재고 정보 가져오기 (새로 추가된 로직이라고 가정)
        int inventoryQuantity = orderService.getInventoryQuantity(productNo);

        // 결과를 Map에 담아서 반환
        response.put("contractPrice", contractPrice);
        response.put("inventoryQuantity", inventoryQuantity);

        return ResponseEntity.ok(response);  // JSON 형식으로 가격과 재고 정보 반환
    }


    @GetMapping("/getInventoryQuantity")
    public int getInventoryQuantity(@RequestParam("productNo") int productNo) {
        return orderService.getInventoryQuantity(productNo);
    }

    @GetMapping("/getOrderItems")
    public List<OrderItemVO> getOrderItems(@RequestParam("orderHeaderNo") int orderHeaderNo) {
        return orderService.getOrderItemsByOrderHeaderNo(orderHeaderNo);
    }

    @GetMapping("/getOrderItemCount")
    public int getOrderItemCount(@RequestParam("orderHeaderNo") int orderHeaderNo) {
        return orderService.getOrderItemCount(orderHeaderNo);
    }

}
