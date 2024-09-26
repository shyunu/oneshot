package com.project.oneshot.controller;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.product.ProductCriteria;
import com.project.oneshot.inventory.product.ProductPageVO;
import com.project.oneshot.inventory.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productList")
    public String product(ProductCriteria cri, Model model) {
        List<ProductVO> productList = productService.getProductList(cri);
        model.addAttribute("productList", productList);

        int totalProductCount = productService.getTotalProductCount(cri); //전체 게시글 수
        ProductPageVO pageVO = new ProductPageVO(cri, totalProductCount); //페이지네이션
        model.addAttribute("pageVO", pageVO);

        List<CategoryVO> categoryList = productService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        List<SupplierVO> supplierList = productService.getSupplierList();
        model.addAttribute("supplierList", supplierList);

        return "inventory/product";
    }

    @PostMapping("/postProduct")
    public String postProduct(ProductVO vo, @RequestParam("file") MultipartFile file) {
        productService.postProduct(vo, file);
        return "redirect:/inventory/productList";
    }

    @PostMapping("/putProduct")
    public String putProduct(ProductVO vo, @RequestParam("file") MultipartFile file) {
        System.out.println("vo = " + vo);
        productService.putProduct(vo, file);
        return "redirect:/inventory/productList";
    }
}
