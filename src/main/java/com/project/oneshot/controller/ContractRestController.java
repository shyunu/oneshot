package com.project.oneshot.controller;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.sales.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class ContractRestController {

    @Autowired
    ContractService contractService;

    @GetMapping("/getContractClientList")
    public List<ClientVO> getClientList() {
        System.out.println("ContractRestController.getClientList");
        List<ClientVO> list = contractService.getClientList();

        System.out.println("list = " + list);
        return list;
    }

    @GetMapping("/getContractUpdateList")
    public ClientVO getContractUpdateList(@RequestParam("clientNo") int clientNo) {
        ClientVO updatelist = contractService.getContractUpdateList(clientNo);
        System.out.println("list = " + updatelist);
        return updatelist;
    }

}
