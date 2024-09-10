package com.project.oneshot.controller;

import com.project.oneshot.entity.CategoryVO;
import com.project.oneshot.entity.ProductVO;
import com.project.oneshot.entity.SupplierVO;
import com.project.oneshot.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

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

            if(!dir.exists()) {
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
}
