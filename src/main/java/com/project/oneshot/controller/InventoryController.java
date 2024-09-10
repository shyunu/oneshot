package com.project.oneshot.controller;

import com.project.oneshot.entity.CategoryVO;
import com.project.oneshot.entity.ProductVO;
import com.project.oneshot.entity.SupplierVO;
import com.project.oneshot.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public String product(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Page<ProductVO> list = inventoryService.getAllProducts(page, size);
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

        CategoryVO categoryVO = inventoryService.getCategoryById(categoryNo);
        SupplierVO supplierVO = inventoryService.getSupplierDetails(supplierNo);

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

        inventoryService.registerProduct(vo);
        return "redirect:/inventory/productList";
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
