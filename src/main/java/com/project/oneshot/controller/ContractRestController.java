package com.project.oneshot.controller;

import com.project.oneshot.command.*;
import com.project.oneshot.sales.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
public class ContractRestController {

    @Autowired
    ContractService contractService;

    @GetMapping("/getContractClientList")
    public List<ClientVO> getClientList() {
        System.out.println("ContractRestController.getClientList");
        List<ClientVO> list = contractService.getClientList();

        return list;
    }

    @GetMapping("/getContractUpdateList")
    public ClientVO getContractUpdateList(@RequestParam("clientNo") int clientNo) {
        ClientVO updatelist = contractService.getContractUpdateList(clientNo);
        return updatelist;
    }

    @GetMapping("/getContractProductList")
    public List<ProductVO> getContractProductList() {
        List<ProductVO> list = contractService.getContractProductList();
        return list;
    }

    @GetMapping("/getContractDetails")
    public List<ContractVO> getContractDetails(@RequestParam("contractPriceNo") int contractPriceNo) {

        List<ContractVO> result = contractService.getContractDetails(contractPriceNo);
        return result;
    }

}
