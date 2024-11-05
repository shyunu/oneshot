package com.project.oneshot.app.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productApp")
public class AppProductRestController {

    @Autowired
    AppProductService appProductService;
}
