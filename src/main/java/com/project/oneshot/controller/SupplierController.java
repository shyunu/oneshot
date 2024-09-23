package com.project.oneshot.controller;

import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.product.ProductPageVO;
import com.project.oneshot.inventory.supplier.SupplierCriteria;
import com.project.oneshot.inventory.supplier.SupplierPageVO;
import com.project.oneshot.inventory.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inventory")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplierList")
    public String supplier(Model model, SupplierCriteria cri) {
        System.out.println("cri = " + cri);
        List<SupplierVO> list = supplierService.getAllSuppliers(cri);
        System.out.println("list = " + list.toString());
        model.addAttribute("list", list);

        int totalSupplier = supplierService.getTotalSupplier(cri);
        SupplierPageVO pageVO = new SupplierPageVO(cri, totalSupplier);
        model.addAttribute("pageVO", pageVO);

        return "inventory/supplier";
    }

    @PostMapping("/registerSupplier")
    public String registerSupplier(@RequestParam("supplierName") String supplierName,
                                   @RequestParam("supplierAddress") String supplierAddress,
                                   @RequestParam("supplierBusinessNo") String supplierBusinessNo,
                                   @RequestParam("managerName") String managerName,
                                   @RequestParam("managerPhone") String managerPhone,
                                   @RequestParam("managerEmail") String managerEmail,
                                   @RequestParam("supplierFile") MultipartFile supplierFile) {

        String filename = null;
        try {
            filename = System.currentTimeMillis() + "_" + supplierFile.getOriginalFilename();
            String directoryPath = "/Users/narin/Desktop/첨부파일/"; // D:/file_repo/
            File dir = new File(directoryPath);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = directoryPath + filename;
            supplierFile.transferTo(new File(filePath));
        }
        catch (Exception e) {
            e.printStackTrace();
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

        supplierService.registerSupplier(supplier);
        return "redirect:/inventory/supplierList";
    }

    @GetMapping("/getSupplierByNo/{supplierNo}")
    public ResponseEntity<SupplierVO> getSupplierByNo(@PathVariable("supplierNo") long supplierNo) {
        SupplierVO supplier = supplierService.getSupplierByNo(supplierNo);
        if (supplier != null) {
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/modifySupplier")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> modifySupplier(@ModelAttribute SupplierVO supplierVO,
                                                              @RequestParam(value = "supplierFile", required = false) MultipartFile supplierFile) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (supplierFile != null && !supplierFile.isEmpty()) {
                String filename = System.currentTimeMillis() + "_" + supplierFile.getOriginalFilename();
                String directoryPath = "/Users/narin/Desktop/첨부파일/";
                File dir = new File(directoryPath);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String filePath = directoryPath + filename;
                supplierFile.transferTo(new File(filePath));
                supplierVO.setSupplierFile(filename);
            }
            boolean result = supplierService.modifySupplier(supplierVO);

            if (result) {
                response.put("success", true);
                response.put("message", "성공적으로 수정되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "수정에 실패했습니다.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/searchSuppliers")
    public String searchSuppliers(@RequestParam Map<String, Object> params, Model model) {
        List<SupplierVO> list = supplierService.searchSuppliers(params);
        model.addAttribute("list", list);

        return "inventory/supplier";
    }

    @GetMapping("/viewFile/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) throws IOException {
        String filePath = "/Users/narin/Desktop/첨부파일/" + fileName;
        File file = new File(filePath);

        if (file.exists()) {
            Path path = Paths.get(file.getAbsolutePath());
            Resource resource = new UrlResource(path.toUri());
            String contentType = Files.probeContentType(path);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        }
        return null;
    }
}
