package com.project.oneshot.controller;

import com.project.oneshot.command.*;
import com.project.oneshot.inventory.purchase.PurchaseCriteria;
import com.project.oneshot.inventory.purchase.PurchasePageVO;
import com.project.oneshot.inventory.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/purchaseList")
    public String purchase(Model model, PurchaseCriteria cri) {
        List<PurchaseVO> list = purchaseService.getAllPurchase(cri);
        System.out.println("list :" + list.toString());
        model.addAttribute("list", list);

        List<SupplierVO> supplierList = purchaseService.getAllSuppliers();
        model.addAttribute("supplierList", supplierList);

        List<CategoryVO> categoryList = purchaseService.getAllCategories();
        model.addAttribute("categoryList", categoryList);

        List<EmployeeVO> employeeList = purchaseService.getAllEmployees();
        model.addAttribute("employeeList", employeeList);

        int totalPurchase = purchaseService.getTotalPurchase(cri);
        PurchasePageVO pageVO = new PurchasePageVO(cri, totalPurchase);
        model.addAttribute("pageVO", pageVO);

        return "inventory/purchase";
    }

    @PostMapping("/registerPurchase")
    public String registerPurchase(PurchaseVO purchase) {
        purchaseService.registerPurchase(purchase);
        return "redirect:/inventory/purchaseList";
    }
}
