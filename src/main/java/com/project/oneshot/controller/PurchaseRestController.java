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

    @GetMapping("/getCategories")
    public List<CategoryVO> getCategories() {
        return purchaseService.getAllCategories();
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
