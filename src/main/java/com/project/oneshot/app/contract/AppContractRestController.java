package com.project.oneshot.app.contract;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contractApp")
public class AppContractRestController {

    @Autowired
    AppContractService appContractService;

    @GetMapping("/getClientList")
    @CrossOrigin(origins = "*")
    public List<ClientVO> getClientList() {
        List<ClientVO> clientList = appContractService.getClientList();
        System.out.println("clientList = " + clientList);
        return clientList;
    }

    @GetMapping("/getProductList")
    @CrossOrigin(origins = "*")
    public List<ProductVO> getProductList() {
        List<ProductVO> productList= appContractService.getProductList();
        return productList;
    }
}
