package com.project.oneshot.controller;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class PurchaseRestController {

    @Autowired
    PurchaseService purchaseService;

    @GetMapping("/getSuppliers")
    public List<SupplierVO> getSuppliers() {
        return purchaseService.getAllSuppliers();
    }

    @GetMapping("/getSupplierInfo")
    public SupplierVO getSupplierInfo(@RequestParam("supplierNo") int supplierNo) {
        return purchaseService.getSupplierInfo(supplierNo);
    }

    @GetMapping("/getCategories")
    public List<CategoryVO> getCategories(@RequestParam("supplierNo") int supplierNo) {
        return purchaseService.getCategories(supplierNo);
    }

    @GetMapping("/getProducts")
    public List<ProductVO> getProducts(@RequestParam("supplierNo") int supplierNo, @RequestParam("categoryNo") int categoryNo) {
        return purchaseService.getProducts(supplierNo, categoryNo);
    }

    @GetMapping("/getQuantity")
    public ProductVO getQuantity(@RequestParam("productNo") int productNo) {
        System.out.println("productNo = " + productNo);
        ProductVO quantity = purchaseService.getQuantity(productNo);
        System.out.println("quantity = " + quantity);
        return quantity;
    }

    @GetMapping("/getProductsByCategory")
    public List<ProductVO> getProductsByCategory(@RequestParam Long categoryNo) {
        return purchaseService.getProductsByCategory(categoryNo);
    }

    @GetMapping("/getEmployees")
    public List<EmployeeVO> getEmployees() {
        return purchaseService.getAllEmployees();
    }
}
