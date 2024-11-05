package com.project.oneshot.app.home;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.OrderItemVO;
import com.project.oneshot.command.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/homeApp")
public class AppHomeRestController {

    @Autowired
    AppHomeService appHomeService;

    // 오늘 계약 건수 조회
    @GetMapping("/contractCount")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> getContractCount() {
        int contractCount = appHomeService.getContractCount();
        return new ResponseEntity<>(contractCount, HttpStatus.OK);
    }

    // 오늘 판매 건수 조회
    @GetMapping("/salesCount")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> getSalesCount() {
        int salesCount = appHomeService.getSalesCount();
        return new ResponseEntity<>(salesCount, HttpStatus.OK);
    }
}
