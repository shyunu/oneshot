package com.project.oneshot.app.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.oneshot.command.AppProductVO;
import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/productApp")
@CrossOrigin(origins = "*")
public class AppProductRestController {

    @Autowired
    AppProductService appProductService;

    @GetMapping("getSupplierList")
    public List<SupplierVO> getAllSuppliers() {
        return appProductService.getSupplierList();
    }

    @GetMapping("getSupplierContent/{supplierNo}")
    public SupplierVO getProductDetails(@PathVariable("supplierNo") int supplierNo) {
        return appProductService.getSupplierContent(supplierNo);
    }

    @GetMapping("getCategoryList")
    public List<CategoryVO> getCategoryList() {
        return appProductService.getCategoryList();
    }

    @GetMapping("getProductContent")
    public ProductVO getProductContent(@RequestParam("productNo") int productNo) {
        return appProductService.getProductContent(productNo);
    }

    @GetMapping("checkProductName")
    public int checkProductName(@RequestParam("productName") String productName) {
        System.out.println("productName = " + productName);
        return appProductService.checkProductName(productName);
    }

    @GetMapping("/productList")
    public ResponseEntity<List<ProductVO>> productList(@RequestParam(required = false) String searchKeyword) {
        List<ProductVO> productList = appProductService.getProductList(searchKeyword);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


    @GetMapping("/displayImg/{productNo}")
    public ResponseEntity<Object> displayImg(@PathVariable("productNo") int productNo) {
        try {
            ProductVO product = appProductService.getProductContent(productNo);
            if (product == null || product.getProductImgApp() == null) {
                return new ResponseEntity<>(
                        Collections.singletonMap("message", "No image found"),
                        HttpStatus.NOT_FOUND
                );
            }

            // 바이너리 데이터를 Base64로 변환하여 반환
            String base64Image = Base64.getEncoder().encodeToString(product.getProductImgApp());
            return new ResponseEntity<>(
                    Collections.singletonMap("imageData", base64Image),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    Collections.singletonMap("error", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/postProduct")
    public ResponseEntity<String> postProduct(
            @RequestPart("vo") String voData,
            @RequestPart("file") MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductVO vo = mapper.readValue(voData, ProductVO.class);

            if (file != null && !file.isEmpty()) {
                byte[] imageBytes = file.getBytes();
                vo.setProductImgApp(imageBytes);  // byte[]로 저장
            } else {
                System.out.println("이미지가 전달되지 않았습니다.");
            }

            appProductService.postProduct(vo);
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add product: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}