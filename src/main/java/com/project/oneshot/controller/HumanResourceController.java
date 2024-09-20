package com.project.oneshot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hrm")
public class HumanResourceController {
    @GetMapping("/{pageName}.do")
    public String humanResource(@PathVariable String pageName, Model model) {
        System.out.println("뷰이름:" + pageName);
        model.addAttribute("reactInclude", true);

        return "common/thymeleafView";
    }
}