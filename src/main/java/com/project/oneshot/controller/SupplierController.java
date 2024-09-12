package com.project.oneshot.controller;

import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.supplier.SupplierService;
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
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplierList")
    public String supplier(Model model) {
        List<SupplierVO> list = supplierService.getAllSuppliers(); // SupplierService 사용
        System.out.println("list = " + list.toString());
        model.addAttribute("list", list);
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
            String directoryPath = "D:/file_repo/";
            File dir = new File(directoryPath);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = directoryPath + filename;
            supplierFile.transferTo(new File(filePath));
        } catch (Exception e) {
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
}
