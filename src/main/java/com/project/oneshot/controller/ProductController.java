package com.project.oneshot.controller;

import com.project.oneshot.entity.jpa.CategoryVO;
import com.project.oneshot.entity.jpa.ProductVO;
import com.project.oneshot.entity.jpa.SupplierVO;
import com.project.oneshot.inventory.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productList")
    public String product(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Page<ProductVO> list = productService.getAllProducts(page, size);
        model.addAttribute("list", list.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("size", size);
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

        CategoryVO categoryVO = productService.getCategoryById(categoryNo);
        SupplierVO supplierVO = productService.getSupplierDetails(supplierNo);

        ProductVO vo = ProductVO.builder()
                .categoryVO(categoryVO)
                .supplierVO(supplierVO)
                .productName(productName)
                .productContent(productContent)
                .safetyQuantity(safetyQuantity)
                .productPrice(productPrice)
                .productImg(filename)
                .productRemarks(productRemarks)
                .build();

        productService.registerProduct(vo);
        return "redirect:/product/productList";
    }

    @GetMapping("/supplierList")
    public String supplier(Model model) {
        List<SupplierVO> list = productService.getAllSuppliers();
        model.addAttribute("list", list);
        return "product/supplier";
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

        SupplierVO savedSupplier = productService.registerSupplier(supplier);
        return ResponseEntity.ok(savedSupplier);
    }
}
