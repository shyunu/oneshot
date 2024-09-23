package com.project.oneshot.controller;

import com.project.oneshot.command.HomeVO;
import com.project.oneshot.common.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/common")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "err", required = false) String err,Model model) {
        if(err != null) {
            model.addAttribute("msg", "아이디 비밀번호를 확인하세요");
        }
        return "common/login";
    }

    @GetMapping("/loginTest")
    public String loginTest() {
        return "common/loginTest";
    }

    @GetMapping("/home")
    public String home() {
        return "common/home";
    }
}
