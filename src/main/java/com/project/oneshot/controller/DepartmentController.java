package com.project.oneshot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hrm")
public class DepartmentController {
    @GetMapping("/{pageName}.do")
    public String humanResource(@PathVariable String pageName) {
        System.out.println("뷰이름:" + pageName);
        return "humanResource/view";
    }
}
