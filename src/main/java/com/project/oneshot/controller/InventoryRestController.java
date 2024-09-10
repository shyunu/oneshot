package com.project.oneshot.controller;

import com.project.oneshot.entity.CategoryVO;
import com.project.oneshot.entity.ProductVO;
import com.project.oneshot.entity.SupplierVO;
import com.project.oneshot.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/inventory")
public class InventoryRestController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("suppliers")
    public List<SupplierVO> getAllSuppliers() {
        List<SupplierVO> list = inventoryService.getAllSuppliers();
        return list;
    }

    @GetMapping("supplier")
    public SupplierVO getSupplierDetails(@RequestParam("supplierNo") Long supplierNo) {
        SupplierVO vo = inventoryService.getSupplierDetails(supplierNo);
        return vo;
    }

    @GetMapping("categories")
    public List<CategoryVO> getAllCategories() {
        List<CategoryVO> list = inventoryService.getAllCategories();
        return list;
    }

    @GetMapping("product")
    public List<ProductVO> getProductDetails(@RequestParam("supplierNo") Long supplierNo) {
        List<ProductVO> list = inventoryService.getProductDetails(supplierNo);
        return list;
    }

}
