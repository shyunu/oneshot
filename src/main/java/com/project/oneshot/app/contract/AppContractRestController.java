package com.project.oneshot.app.contract;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getContractList")
    @CrossOrigin(origins = "*")
    public List<ContractVO> getContractList(@RequestParam("clientNo") int clientNo, @RequestParam("productNo") int productNo) {
        List<ContractVO> contractList = appContractService.getContractList(clientNo, productNo);
        return contractList;
    }

    @PostMapping("/contract")
    public ResponseEntity<String> registerContract(@RequestBody ContractVO vo) {
        appContractService.registerContract(vo);
        return new ResponseEntity<>("Contract registered successfully", HttpStatus.CREATED);
    }
}
