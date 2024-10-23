package com.project.oneshot.app.sales;

import com.project.oneshot.command.ClientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salesApp")
public class AppSalesRestController {

    @Autowired
    AppSalesService appSalesService;

    //코드 작성
}
