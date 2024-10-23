package com.project.oneshot.app.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contractApp")
public class AppContractRestController {

    @Autowired
    AppContractService appContractService;

    //코드 작성
}
