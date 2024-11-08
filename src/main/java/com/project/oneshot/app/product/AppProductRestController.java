package com.project.oneshot.app.product;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.product.ProductCriteria;
import com.project.oneshot.inventory.product.ProductPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/productApp")
public class AppProductRestController {

    @Autowired
    AppProductService appProductService;

    @GetMapping("/displayImg/{productImg}")
    @CrossOrigin(origins = "*")
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
    @CrossOrigin(origins = "*")
    public List<SupplierVO> getAllSuppliers() {
        return appProductService.getSupplierList();
    }

    @GetMapping("getSupplierContent/{supplierNo}")
    @CrossOrigin(origins = "*")
    public SupplierVO getProductDetails(@PathVariable("supplierNo") int supplierNo) {
        return appProductService.getSupplierContent(supplierNo);
    }

    @GetMapping("getCategoryList")
    @CrossOrigin(origins = "*")
    public List<CategoryVO> getCategoryList() {
        return appProductService.getCategoryList();
    }

    @GetMapping("getProductContent")
    @CrossOrigin(origins = "*")
    public ProductVO getProductContent(@RequestParam("productNo") int productNo) {
        return appProductService.getProductContent(productNo);
    }

    @GetMapping("checkProductName")
    @CrossOrigin(origins = "*")
    public int checkProductName(@RequestParam("productName") String productName) {
        System.out.println("productName = " + productName);
        return appProductService.checkProductName(productName);
    }

    @GetMapping("/productList")
    @CrossOrigin(origins = "*")
    public String product(Model model) {
        List<ProductVO> productList = appProductService.getProductList();
        model.addAttribute("productList", productList);

        List<CategoryVO> categoryList = appProductService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        List<SupplierVO> supplierList = appProductService.getSupplierList();
        model.addAttribute("supplierList", supplierList);

        return "inventory/product";
    }

    @PostMapping("/postProduct")
    @CrossOrigin(origins = "*")
    public String postProduct(ProductVO vo, @RequestParam("file") MultipartFile file) {
        appProductService.postProduct(vo, file);
        return "redirect:/inventory/productList";
    }

    @PostMapping("/putProduct")
    @CrossOrigin(origins = "*")
    public String putProduct(ProductVO vo, @RequestParam("file") MultipartFile file) {
        System.out.println("vo = " + vo);
        appProductService.putProduct(vo, file);
        return "redirect:/inventory/productList";
    }
}
