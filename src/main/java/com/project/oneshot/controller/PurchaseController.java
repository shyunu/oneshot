package com.project.oneshot.controller;

import com.project.oneshot.command.PurchaseVO;
import com.project.oneshot.inventory.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/purchaseList")
    public String purchase(Model model) {
        List<PurchaseVO> list = purchaseService.getAllPurchase();
        model.addAttribute("list", list);
        return "inventory/purchase";
    }
}
