package com.project.oneshot.controller;

import com.project.oneshot.entity.ProductVO;
import com.project.oneshot.entity.SupplierVO;
import com.project.oneshot.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/productList")
    public String product(Model model) {
        List<ProductVO> list = inventoryService.getAllProducts();
        model.addAttribute("list", list);
        return "inventory/product";
    }

    @PostMapping("/registerProduct")
    public String registerProduct(@RequestParam("categoryNo") Long categoryNo,
                                  @RequestParam("supplierNo") Long supplierNo,
                                  @RequestParam("productName") String productName,
                                  @RequestParam("productContent") String productContent,
                                  @RequestParam("safetyQuantity") Long safetyQuantity,
                                  @RequestParam("productPrice") Long productPrice,
                                  @RequestParam("productImg") MultipartFile productImg,
                                  @RequestParam("productRemarks") String productRemarks) {
        String filename = null;
        try {
            filename = System.currentTimeMillis() + "_" + productImg.getOriginalFilename();
            String directoryPath = "D:/file_repo/";
            File dir = new File(directoryPath);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = directoryPath + filename;
            productImg.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ProductVO vo = new ProductVO();
        vo.setCategoryNo(categoryNo);
        vo.setSupplierNo(supplierNo);
        vo.setProductName(productName);
        vo.setProductContent(productContent);
        vo.setSafetyQuantity(safetyQuantity);
        vo.setProductPrice(productPrice);
        vo.setProductImg(filename);
        vo.setProductRemarks(productRemarks);

        inventoryService.registerProduct(vo);
        return "redirect:/inventory/product";
    }

    @GetMapping("/supplierList")
    public String supplier(Model model) {
        List<SupplierVO> list = inventoryService.getAllSuppliers();
        model.addAttribute("list", list);
        return "inventory/supplier";
    }

    @PostMapping("/registerSupplier")
    public ResponseEntity<SupplierVO> registerSupplier(
            @RequestParam("supplierName") String supplierName,
            @RequestParam("supplierAddress") String supplierAddress,
            @RequestParam("supplierBusinessNo") String supplierBusinessNo,
            @RequestParam("managerName") String managerName,
            @RequestParam("managerPhone") String managerPhone,
            @RequestParam("managerEmail") String managerEmail,
            @RequestParam("supplierFile") MultipartFile supplierFile) {
        String filename = null;
        try {
            filename = System.currentTimeMillis() + "_" + supplierFile.getOriginalFilename();
            String directoryPath = System.getProperty("user.dir") + "/file_repo/";
            File dir = new File(directoryPath);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = directoryPath + filename;
            supplierFile.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        SupplierVO supplier = SupplierVO.builder()
                .supplierName(supplierName)
                .supplierAddress(supplierAddress)
                .supplierBusinessNo(supplierBusinessNo)
                .managerName(managerName)
                .managerPhone(managerPhone)
                .managerEmail(managerEmail)
                .supplierFile(filename)
                .build();

        SupplierVO savedSupplier = inventoryService.registerSupplier(supplier);
        return ResponseEntity.ok(savedSupplier);
    }
}
