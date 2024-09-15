package com.project.oneshot.controller;

import com.project.oneshot.command.ContractVO;
import com.project.oneshot.sales.contract.ContractService;
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
public class ContractController {

    @Autowired
    @Qualifier("contractService") //서비스연결
    private ContractService contractService;

    // ----- 계약가격내역 ----- //
    @GetMapping("/contract")
    public String contract(Model model) {

        List<ContractVO> list = contractService.getList();
        System.out.println("list = " + list);
        model.addAttribute("list", list);

        return "sales/contract";
    }

    @PostMapping("/registForm") //--- 계약 등록하기
    public String registForm(@RequestParam("contractProductName[]") List<String> contractProductNames,
                             @RequestParam("contractPrice[]") List<Integer> contractPrices,
                             ContractVO vo,
                             RedirectAttributes ra) {

        vo.setContractProductNames(contractProductNames);
        vo.setContractPrices(contractPrices);

        int result = contractService.contractRegist(vo);

        if(result == 1) {
            ra.addFlashAttribute("msg", "정상 등록");
        } else {
            ra.addFlashAttribute("msg", "등록 실패");
        }

        return "redirect:/sales/contract";
    }

    // ----- 날짜 데이터 변환 ----- //
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