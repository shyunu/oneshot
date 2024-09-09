package com.project.oneshot.controller;

import com.project.oneshot.entity.ProductVO;
import com.project.oneshot.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
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

            if(!dir.exists()) {
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
}
