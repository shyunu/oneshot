package com.project.oneshot.app.sales;

import com.project.oneshot.command.*;
import com.project.oneshot.sales.order.OrderCriteria;
import com.project.oneshot.sales.order.OrderPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/salesApp")
public class AppSalesRestController {

    @Autowired
    AppSalesService appSalesService;

    //고객사리스트 가져오기
    @GetMapping("/getClientList")
    @CrossOrigin(origins = "*")
    public List<ClientVO> getClientList() {
        List<ClientVO> clientList = appSalesService.getClientList();
        System.out.println("clientList = " + clientList);
        return clientList;
    }

    //고객사정보 가져오기
    @GetMapping("/getClientContent/{clientNo}")
    public ClientVO getClientContent(@PathVariable("clientNo") int clientNo) {
        ClientVO vo = appSalesService.getClientContent(clientNo);
        return vo;
    }

    //계약체결된 상품리스트 가져오기
    @GetMapping("/getProductList/{clientNo}")
    @CrossOrigin(origins = "*")
    public List<ContractVO> getProductList(@PathVariable("clientNo") int clientNo) {
        List<ContractVO> list = appSalesService.getProductList(clientNo);
        return list;
    }

    //계약가격 가져오기
    @GetMapping("/getProductPrice/{clientNo}/{productNo}")
    public ResponseEntity<Map<String, Object>> getProductPrice(@PathVariable("clientNo") int clientNo, @PathVariable("productNo") int productNo) {
        Map<String, Object> response = new HashMap<>();

        // 가격 정보 가져오기
        int contractPrice = appSalesService.getProductPrice(clientNo, productNo);

        // 재고 정보 가져오기 (새로 추가된 로직이라고 가정)
        int inventoryQuantity = appSalesService.getInventoryQuantity(productNo);

        // 결과를 Map에 담아서 반환
        response.put("contractPrice", contractPrice);
        response.put("inventoryQuantity", inventoryQuantity);

        return ResponseEntity.ok(response);  // JSON 형식으로 가격과 재고 정보 반환
    }

    //등록하기
    @PostMapping("/orderForm")
    public ResponseEntity<String> orderForm(@RequestBody OrderVO vo) {
        try {
            appSalesService.orderRegist(vo);
            return ResponseEntity.ok("등록성공!!");
        } catch (Exception e) {
            e.printStackTrace(); // 예외 스택 추적을 로그에 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("등록 실패: " + e.getMessage());
        }
    }
    //날짜변환(string -> date)
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // 엄격한 날짜 형식 검사
        binder.registerCustomEditor(Date.class, new org.springframework.beans.propertyeditors.CustomDateEditor(dateFormat, true)); // Allow empty dates
    }

    //조회하기
    @GetMapping("/order")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<OrderVO>> orderList() {
        // 모든 주문 내역 조회
        List<OrderVO> orderList = appSalesService.getList(); // 모든 주문을 가져오는 서비스 메소드
        return ResponseEntity.ok(orderList); // JSON 형태로 응답
    }

    @GetMapping("/items/{orderHeaderNo}")
    public List<OrderItemVO> getItems(@PathVariable("orderHeaderNo") int orderHeaderNo) {
        return appSalesService.getItems(orderHeaderNo);
    }

}
