package com.project.oneshot.app.inventory;

import com.project.oneshot.command.*;
import com.project.oneshot.inventory.purchase.PurchaseCriteria;
import com.project.oneshot.inventory.purchase.PurchasePageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inventoryApp")
public class AppInventoryRestController {

    @Autowired
    AppInventoryService appInventoryService;

    // 공급업체 목록
    @GetMapping("/getSuppliers")
    @CrossOrigin(origins = "*")
    public List<SupplierVO> getSuppliers() {
        System.out.println("도착!");
        return appInventoryService.getAllSuppliers();
    }

    // 공급업체 정보 가져오기
    @GetMapping("/getSupplierInfo/{supplierNo}")
    @CrossOrigin(origins = "*")
    public SupplierVO getSupplierInfo(@PathVariable("supplierNo") int supplierNo) {
        return appInventoryService.getSupplierInfo(supplierNo);
    }

    // 카테고리 목록
    @GetMapping("/getCategories")
    @CrossOrigin(origins = "*")
    public List<CategoryVO> getCategories(@RequestParam("supplierNo") int supplierNo) {
        System.out.println("도착!");
        return appInventoryService.getCategories(supplierNo);
    }

    // 상품 목록
    @GetMapping("/getProducts")
    @CrossOrigin(origins = "*")
    public List<ProductVO> getProducts(@RequestParam("supplierNo") int supplierNo, @RequestParam("categoryNo") int categoryNo) {
        return appInventoryService.getProducts(supplierNo, categoryNo);
    }

    // 수량
    @GetMapping("/getQuantity")
    @CrossOrigin(origins = "*")
    public ProductVO getQuantity(@RequestParam("productNo") int productNo) {
        System.out.println("productNo = " + productNo);
        ProductVO quantity = appInventoryService.getQuantity(productNo);
        System.out.println("quantity = " + quantity);
        return quantity;
    }

    // 카테고리별 상품 목록
    @GetMapping("/getProductsByCategory")
    @CrossOrigin(origins = "*")
    public List<ProductVO> getProductsByCategory(@RequestParam Long categoryNo) {
        return appInventoryService.getProductsByCategory(categoryNo);
    }

    // 사원 목록
    @GetMapping("/getEmployees")
    @CrossOrigin(origins = "*")
    public List<EmployeeVO> getEmployees() {
        return appInventoryService.getAllEmployees();
    }

    // 등록
//    @PostMapping("/registerPurchase")
//    @CrossOrigin(origins = "*")
//    public ResponseEntity<String> registerPurchase(@RequestParam("productNo") List<Integer> productNo,
//                                                   @RequestParam("purchasePrice") List<Integer> purchasePrice,
//                                                   @RequestParam("purchaseQuantity") List<Integer> purchaseQuantity,
//                                                   @RequestParam("employeeNo") int employeeNo) {
//        List<PurchaseVO> list = new ArrayList<>();
//
//        System.out.println("Received productNo: " + productNo);
//        System.out.println("Received purchasePrice: " + purchasePrice);
//        System.out.println("Received purchaseQuantity: " + purchaseQuantity);
//        System.out.println("Received employeeNo: " + employeeNo);
//
//        // 리스트의 크기를 확인하여 모든 값이 올바르게 전달되었는지 확인
//        if (productNo.size() != purchasePrice.size() || productNo.size() != purchaseQuantity.size()) {
//            return ResponseEntity.badRequest().body("모든 리스트의 크기가 일치해야 합니다.");
//        }
//
//        for (int i = 0; i < productNo.size(); i++) {
//            PurchaseVO vo = new PurchaseVO();
//            vo.setProductNo(productNo.get(i));
//            vo.setPurchasePrice(purchasePrice.get(i));
//            vo.setPurchaseQuantity(purchaseQuantity.get(i));
//            vo.setEmployeeNo(employeeNo);
//            list.add(vo);
//        }
//        appInventoryService.registerPurchase(list);
//        System.out.println("등록 완료");
//        return ResponseEntity.ok("구매 등록이 완료되었습니다.");
//    }

//    @PostMapping("/registerPurchase")
//    @CrossOrigin(origins = "*")
//    public ResponseEntity<String> registerPurchase(@RequestBody List<PurchaseVO> purchaseList) {
//        System.out.println("Received purchaseList: " + purchaseList);
//        appInventoryService.registerPurchase(purchaseList);
//        System.out.println("등록 완료");
//        return ResponseEntity.ok("구매 등록이 완료되었습니다.");
//    }

    @PostMapping("/registerPurchase")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> registerPurchase(@RequestBody List<PurchaseVO> purchaseList) {
        System.out.println("Received purchaseList: " + purchaseList);
        appInventoryService.registerPurchase(purchaseList);
        System.out.println("등록 완료");
        return ResponseEntity.ok("구매 등록이 완료되었습니다.");
    }


    // 전체 목록 조회
    @GetMapping("/purchaseList")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<PurchaseVO>> purchaseList(@RequestParam(required = false) String searchKeyword) {
        List<PurchaseVO> list = appInventoryService.getAllPurchase(searchKeyword);
        return ResponseEntity.ok(list);

    }
}
