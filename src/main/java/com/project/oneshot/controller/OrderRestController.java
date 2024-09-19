package com.project.oneshot.controller;

import com.project.oneshot.command.*;
import com.project.oneshot.sales.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/updateOrder")
//    public ResponseEntity<String> updateOrder(@RequestBody OrderVO orderVO) {
//        try {
//            orderService.updateOrder(orderVO);  // 서비스에서 업데이트 로직 처리
//            return ResponseEntity.ok("성공적으로 업데이트되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 중 오류 발생");
//        }
//    }

}
