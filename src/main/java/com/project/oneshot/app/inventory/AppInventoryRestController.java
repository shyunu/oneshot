package com.project.oneshot.app.inventory;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;

@RestController
@RequestMapping("/inventoryApp")
public class AppInventoryRestController {

    @Autowired
    AppInventoryService appInventoryService;

    //코드 작성
    @GetMapping("/getSuppliers")
    @CrossOrigin(origins = "*")
    public List<SupplierVO> getSuppliers() {
        System.out.println("도착!");
        return appInventoryService.getAllSuppliers();
    }

    @GetMapping("/getSupplier")
    @CrossOrigin(origins = "*")
    public SupplierVO getSupplier(@RequestParam("supplierNo") int supplierNo) {
        return appInventoryService.getSupplier(supplierNo);
    }

    @GetMapping("/getCategories")
    @CrossOrigin(origins = "*")
    public List<CategoryVO> getCategories(@RequestParam("supplierNo") int supplierNo) {
        return appInventoryService.getCategories(supplierNo);
    }

    @GetMapping("/getProducts")
    @CrossOrigin(origins = "*")
    public List<ProductVO> getProducts(@RequestParam("supplierNo") int supplierNo, @RequestParam("categoryNo") int categoryNo) {
        return appInventoryService.getProducts(supplierNo, categoryNo);
    }

    @GetMapping("/getQuantity")
    @CrossOrigin(origins = "*")
    public ProductVO getQuantity(@RequestParam("productNo") int productNo) {
        System.out.println("productNo = " + productNo);
        ProductVO quantity = appInventoryService.getQuantity(productNo);
        System.out.println("quantity = " + quantity);
        return quantity;
    }

    @GetMapping("/getProductsByCategory")
    @CrossOrigin(origins = "*")
    public List<ProductVO> getProductsByCategory(@RequestParam Long categoryNo) {
        return appInventoryService.getProductsByCategory(categoryNo);
    }

    @GetMapping("/getEmployees")
    @CrossOrigin(origins = "*")
    public List<EmployeeVO> getEmployees() {
        return appInventoryService.getAllEmployees();
    }

}
