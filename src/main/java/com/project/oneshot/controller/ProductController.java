package com.project.oneshot.controller;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/inventory")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product.do")
    public String product(Model model) {
        List<ProductVO> list = productService.getProductList();
        model.addAttribute("list", list);
        return "inventory/product";
    }

    /*
    @PostMapping("/registerProduct")
    public String registerProduct(ProductVO vo, @RequestParam("file") MultipartFile file, RedirectAttributes ra) {
        String filename = null;

        String contentType = file.getContentType();
        System.out.println("contentType = " + contentType);
        if(!contentType.contains("image")) {
            ra.addFlashAttribute("msg", "png, jpg, jpeg 형식만 등록가능합니다");
            return "redirect:/product/productList";
        }

        productService.registerProduct(vo, file);
        return "redirect:/product/productList";
    }

    /*

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
    */
}
