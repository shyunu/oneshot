package com.project.oneshot.controller;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractItemVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.sales.contract.ContractCriteria;
import com.project.oneshot.sales.contract.ContractPageVO;
import com.project.oneshot.sales.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public String contract(ContractCriteria cri, Model model) {
        System.out.println("cri = " + cri);
        List<ContractVO> list = contractService.getList(cri);
        model.addAttribute("list", list);

        int totalCount = contractService.getTotalCount(cri);
        ContractPageVO pageVO = new ContractPageVO(cri, totalCount);
        model.addAttribute("pageVO", pageVO);

        List<ClientVO> clientList = contractService.getClientList();
        model.addAttribute("clientList", clientList);

        return "sales/contract";
    }

    @PostMapping("/registForm") //--- 계약 등록하기
    public String contractRegist(@RequestParam("productNo") List<Integer> productNo,
                                 @RequestParam("employeeNo") int employeeNo,
                                 @RequestParam("clientNo") int clientNo,
                                 @RequestParam("contractSdate") Date contractSdate,
                                 @RequestParam("contractEdate") Date contractEdate,
                                 @RequestParam("contractPrice") List<Integer> contractPrice
    ) {
        Integer no = contractService.getContractPriceNo();
        if(no == null) {
            no = 1;
        } else {
            no = no + 1;
        }

        List<ContractVO> list = new ArrayList<>();

        for(int i = 0; i < productNo.size(); i++) {
            ContractVO vo = new ContractVO();
            vo.setContractPriceNo(no);
            vo.setProductNo(productNo.get(i));
            vo.setEmployeeNo(employeeNo);
            vo.setClientNo(clientNo);
            vo.setContractSdate(contractSdate);
            vo.setContractEdate(contractEdate);
            vo.setContractPrice(contractPrice.get(i));
            list.add(vo);
        }

        contractService.contractRegist(list);
        return "redirect:/sales/contract";
    }

    @PostMapping("/modifyForm") //--- 계약 수정
    public String modifyForm(ContractVO vo) {
        System.out.println("vo = " + vo);

        for(ContractItemVO item : vo.getContractItems()) {
            System.out.println("ContractController.modifyForm");
            ContractVO contractVO = new ContractVO();

            contractVO.setContractPriceNo(vo.getContractPriceNo());
            contractVO.setContractSdate(vo.getContractSdate());
            contractVO.setContractEdate(vo.getContractEdate());
            contractVO.setContractPriceStatus(vo.getContractPriceStatus());
            contractVO.setProductNo(item.getProductNo());
            contractVO.setProductName(item.getProductName());
            contractVO.setContractPrice(item.getContractPrice());
            contractVO.setContractPriceNo(vo.getContractPriceNo());

            System.out.println("contractVO = " + contractVO);
            
            contractService.contractModify(contractVO);
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
        return "result";
    }



}