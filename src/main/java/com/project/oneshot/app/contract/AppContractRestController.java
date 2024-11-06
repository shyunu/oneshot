package com.project.oneshot.app.contract;

import com.project.oneshot.command.AppContractVO;
import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public List<AppContractVO> getContractPriceByClientNoAndProductNo(@RequestParam("clientNo") int clientNo, @RequestParam("productNo") int productNo) {
        List<AppContractVO> list = appContractService.getContractPriceByClientNoAndProductNo(clientNo, productNo);
        return list;
    }

    @PostMapping("/contract")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> registerContract(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("clientNo") Integer clientNo,
                                                   @RequestParam("productNo") Integer productNo,
                                                   @RequestParam("selectedStartDate") String selectedStartDate,
                                                   @RequestParam("selectedEndDate") String selectedEndDate,
                                                   @RequestParam("contractPrice") BigDecimal contractPrice) throws Exception {

        System.out.println("file = " + file);
        System.out.println("clientNo = " + clientNo);
        System.out.println("productNo = " + productNo);
        System.out.println("selectedStartDate = " + selectedStartDate);
        System.out.println("selectedEndDate = " + selectedEndDate);
        System.out.println("contractPrice = " + contractPrice);

        byte[] contractFile = file.getBytes();

        // String을 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(selectedStartDate, DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(selectedEndDate, DateTimeFormatter.ISO_DATE);

        // LocalDate를 java.sql.Date로 변환
        Date contractSdate = Date.valueOf(startDate);
        Date contractEdate = Date.valueOf(endDate);

        AppContractVO vo = new AppContractVO();
        vo.setContractFile(contractFile);
        vo.setClientNo(clientNo);
        vo.setProductNo(productNo);
        vo.setContractSdate(contractSdate);
        vo.setContractEdate(contractEdate);
        vo.setContractPrice(contractPrice);

        appContractService.registerContract(vo);
        return new ResponseEntity<>("Contract registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getContractPriceList")
    public List<AppContractVO> getAllContracts() {
        System.out.println("전체 계약 리스트 요청");
        return appContractService.getAllContracts();
    }

    @GetMapping("/getContractPriceList/{search}")
    public List<AppContractVO> getFilteredContracts(@PathVariable String search) {
        System.out.println("search = " + search);
        return appContractService.getContractPriceList(search);
    }

    @GetMapping(value = "/getContractFile/{contractPriceNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
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
