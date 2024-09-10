package com.project.oneshot.controller;

import com.project.oneshot.entity.jpa.CategoryVO;
import com.project.oneshot.entity.jpa.ProductVO;
import com.project.oneshot.entity.jpa.SupplierVO;
import com.project.oneshot.inventory.product.ProductService;
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
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    ProductService productService;

    @GetMapping("suppliers")
    public List<SupplierVO> getAllSuppliers() {
        List<SupplierVO> list = productService.getAllSuppliers();
        return list;
    }

    @GetMapping("supplier")
    public SupplierVO getSupplierDetails(@RequestParam("supplierNo") Long supplierNo) {
        SupplierVO vo = productService.getSupplierDetails(supplierNo);
        return vo;
    }

    @GetMapping("categories")
    public List<CategoryVO> getAllCategories() {
        List<CategoryVO> list = productService.getAllCategories();
        return list;
    }

    @GetMapping("product")
    public List<ProductVO> getProductDetails(@RequestParam("supplierNo") Long supplierNo) {
        List<ProductVO> list = productService.getProductDetails(supplierNo);
        System.out.println("list = " + list);
        return list;
    }

    @GetMapping("/display/{productImg}")
    public ResponseEntity<byte[]> display(@PathVariable("productImg") String productImg) {
        System.out.println("productImg = " + productImg);
        ResponseEntity<byte[]> result = null;
        String path = "D:/file_repo/" + productImg;
        File file = new File(path);

        try {
            byte[] arr = FileCopyUtils.copyToByteArray(file); //파일데이터의 byte배열값을 구해서 반환
            HttpHeaders header = new HttpHeaders();
            header.add("Content-type", Files.probeContentType(file.toPath() )); //해당 경로 파일에 mime타입을 구함
            result = new ResponseEntity<>(arr, header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
