package com.project.oneshot.app.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventoryApp")
public class AppInventoryRestController {

    @Autowired
    AppInventoryService appInventoryService;

    //코드 작성
}
