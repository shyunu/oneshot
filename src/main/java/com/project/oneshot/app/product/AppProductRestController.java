package com.project.oneshot.app.product;

import com.fasterxml.jackson.databind.ObjectMapper;
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

//    @GetMapping("/productList")
//    public ResponseEntity<List<ProductVO>> productList(@RequestParam(required = false) String searchKeyword) {
//        List<ProductVO> productList = appProductService.getProductList(searchKeyword);
//        return new ResponseEntity<>(productList, HttpStatus.OK);
//    }
//
//
//    @GetMapping("/displayImg/{productNo}")
//    public ResponseEntity<Object> displayImg(@PathVariable("productNo") int productNo) {
//        try {
//            ProductVO product = appProductService.getProductContent(productNo);
//            if (product == null || product.getProductImgApp() == null) {
//                return new ResponseEntity<>(
//                        Collections.singletonMap("message", "No image found"),
//                        HttpStatus.NOT_FOUND
//                );
//            }
//            // 바이너리 데이터를 Base64로 변환하여 반환
//            String base64Image = Base64.getEncoder().encodeToString(product.getProductImgApp());
//            return new ResponseEntity<>(
//                    Collections.singletonMap("imageData", base64Image),
//                    HttpStatus.OK
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(
//                    Collections.singletonMap("error", e.getMessage()),
//                    HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }

    @GetMapping("/productList")
    public ResponseEntity<List<ProductVO>> productList(@RequestParam(required = false) String searchKeyword) {
        List<ProductVO> productList = appProductService.getProductList(searchKeyword);

        // 이미지 데이터 제외
        productList.forEach(product -> product.setProductImgApp(null));

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

            // 로그에 Base64 데이터 일부만 출력
            System.out.println("이미지 데이터 : " + base64Image.substring(0, 50) + "...");

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
    public ResponseEntity<String> postProduct(@RequestPart("vo") String productVOJson, @RequestPart("file") MultipartFile file) {
        try {
            // JSON 문자열을 ProductVO 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            ProductVO productVO = objectMapper.readValue(productVOJson, ProductVO.class);

            // Service를 호출하여 ProductVO와 MultipartFile을 함께 처리
            appProductService.postProduct(productVO, file);

            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}