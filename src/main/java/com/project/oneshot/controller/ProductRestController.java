package com.project.oneshot.controller;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.product.ProductService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class ProductRestController {

    @Autowired
    ProductService productService;

    @GetMapping("/displayImg/{productImg}")
    public ResponseEntity<byte[]> display(@PathVariable("productImg") String productImg) {
        ResponseEntity<byte[]> result = null;
        String path = "D:/file_repo/" + productImg;
        File file = new File(path);

        try {
            byte[] arr = FileCopyUtils.copyToByteArray(file); //파일데이터의 byte배열값을 구해서 반환
            HttpHeaders header = new HttpHeaders();
            header.add("Content-type", Files.probeContentType(file.toPath() )); //해당 경로 파일에 mime타입을 구함
            result = new ResponseEntity<>(arr, header, HttpStatus.OK);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return result;
    }

    @GetMapping("getSupplierList")
    public List<SupplierVO> getAllSuppliers() {
        List<SupplierVO> list = productService.getSupplierList();
        return list;
    }

    @GetMapping("getSupplierContent")
    public SupplierVO getProductDetails(@RequestParam("supplierNo") int supplierNo) {
        SupplierVO vo = productService.getSupplierContent(supplierNo);
        return vo;
    }

    @GetMapping("getCategoryList")
    public List<CategoryVO> getCategoryList() {
        List<CategoryVO> list = productService.getCategoryList();
        return list;
    }

    @GetMapping("getProductContent")
    public ProductVO getProductContent(@RequestParam("productNo") int productNo) {
        ProductVO vo = productService.getProductContent(productNo);
        return vo;
    }

    @GetMapping("checkProductName")
    public int checkProductName(@RequestParam("productName") String productName) {
        System.out.println("productName = " + productName);
        return productService.checkProductName(productName);
    }
}
