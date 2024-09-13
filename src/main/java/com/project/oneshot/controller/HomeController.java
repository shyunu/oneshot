package com.project.oneshot.controller;

import com.project.oneshot.command.HomeVO;
import com.project.oneshot.common.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/common")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/login")
    public String login() {
        return "common/login";
    }

    @PostMapping("/loginForm")
    public String login(HomeVO vo, RedirectAttributes redirectAttributes) {

        int result = homeService.login(vo);
        System.out.println("result = " + result);

        if(result == 1) {
            System.out.println("로그인 성공");
            return null;
        } else {
            System.out.println("로그인 실패");
            redirectAttributes.addFlashAttribute("msg", "로그인에 실패했습니다. 아이디 비밀번호를 확인하세요.");
            return "redirect:/common/login";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "common/home";
    }
}
