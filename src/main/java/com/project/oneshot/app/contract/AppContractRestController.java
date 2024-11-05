package com.project.oneshot.app.contract;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Base64;
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
        return clientList;
    }

    @GetMapping("/getProductList")
    @CrossOrigin(origins = "*")
    public List<ProductVO> getProductList() {
        List<ProductVO> productList= appContractService.getProductList();
        return productList;
    }

    @GetMapping("/getContractPriceByClientNoAndProductNo")
    @CrossOrigin(origins = "*")
    public List<ContractVO> getContractPriceByClientNoAndProductNo(@RequestParam("clientNo") int clientNo, @RequestParam("productNo") int productNo) {
        List<ContractVO> list = appContractService.getContractPriceByClientNoAndProductNo(clientNo, productNo);
        return list;
    }

    @PostMapping("/contract")
    public ResponseEntity<String> registerContract(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("clientNo") Integer clientNo,
                                                   @RequestParam("productNo") Integer productNo,
                                                   @RequestParam("selectedStartDate") String selectedStartDate,
                                                   @RequestParam("selectedEndDate") String selectedEndDate,
                                                   @RequestParam("contractPrice") BigDecimal contractPrice) throws Exception {
        byte[] contractFile = file.getBytes();
        Date contractSdate = Date.valueOf(selectedStartDate);
        Date contractEdate = Date.valueOf(selectedEndDate);

        ContractVO vo = new ContractVO();
        vo.setContractFile(contractFile);
        vo.setClientNo(clientNo);
        vo.setProductNo(productNo);
        vo.setContractSdate(contractSdate);
        vo.setContractEdate(contractEdate);
        vo.setContractPrice(contractPrice);

        appContractService.registerContract(vo);
        return new ResponseEntity<>("Contract registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getContractPriceList/{search}")
    public List<ContractVO> contract(@PathVariable String search) {
        System.out.println("search = " + search);
        List<ContractVO> list = appContractService.getContractPriceList(search);
        return list;
    }

    @GetMapping(value = "/getContractFile/{contractPriceNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getContractFile(@PathVariable Integer contractPriceNo) {
        String imageData = appContractService.getContractFile(contractPriceNo);

        if (imageData != null) {
            // 이미 Base64로 인코딩된 데이터라면 추가 변환 불필요
            return ResponseEntity.ok(imageData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }
}
