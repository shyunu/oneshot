package com.project.oneshot.app.sales;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String orderForm(@ModelAttribute OrderVO vo) {
        appSalesService.orderRegist(vo);
        return "redirect:/salesApp";
    }

}
