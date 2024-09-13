package com.project.oneshot.controller;

import com.project.oneshot.sales.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class ContractRestController {

    @Autowired
    ContractService contractService;
}
