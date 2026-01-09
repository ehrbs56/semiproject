package com.example.demo.controller;


import com.example.demo.dto.LoginDto;
import com.example.demo.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;


    @PostMapping("/login")
    public String login(@RequestParam String id,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        Map<String, Object> map=new HashMap<>();
        map.put("login_id",id);
        map.put("password",password);
        LoginDto logindto=loginService.login(map);
        System.out.println("loginDto: " + logindto);
        if(logindto==null) {
            ra.addFlashAttribute("msg","아이디 또는 비밀번호가 틀렸습니다");
            return "redirect:/home";
        }
        session.setAttribute("loginDto",logindto);
        return logindto.getRole() == 0 ? "redirect:/hq/home" : "redirect:/branch/orders";
    }
}
