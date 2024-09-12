package com.project.oneshot.controller;

import com.project.oneshot.command.ContractVO;
import com.project.oneshot.sales.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    @Qualifier("salesService") //서비스연결
    private SalesService salesService;

    @GetMapping("/contract")
    public String contract(Model model) {

        List<ContractVO> list = salesService.getList();
        System.out.println("list.toString() = " + list.toString());
        model.addAttribute("list", list);



        return "sales/contract";
    }

    @PostMapping("/registForm")
    public String registForm(ContractVO vo,
                             RedirectAttributes ra) { //--- 계약 등록하기
        System.out.println("-------------");
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


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // 엄격한 날짜 형식 검사
        binder.registerCustomEditor(Date.class, new org.springframework.beans.propertyeditors.CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitForm(ContractVO contractVO) {
        // Your logic here
        System.out.println("contractEdate: " + contractVO.getContractEdate());
        return "result";
    }





}