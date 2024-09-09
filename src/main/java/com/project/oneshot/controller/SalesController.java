package com.project.oneshot.controller;

import com.project.oneshot.entity.ContractVO;
import com.project.oneshot.sales.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    @Qualifier("salesService") //서비스연결
    private SalesService salesService;

    @GetMapping("/contract")
    public String contract() {
        return "sales/contract";
    }

    @PostMapping("/registForm")
    public String registForm(ContractVO vo,
                             RedirectAttributes ra) { //--- 계약 등록하기
        int result = salesService.contractRegist(vo);
        if(result == 1) {
            ra.addFlashAttribute("msg", "정상 등록되었습니다");
        } else {
            ra.addFlashAttribute("msg", "등록에 실패했습니다.");
        }

        return "redirect:/sales/contract";
    }



    ///////////////////////
    @GetMapping("/order")
    public String order() { //--- 판매
        return "sales/order";
    }

}
