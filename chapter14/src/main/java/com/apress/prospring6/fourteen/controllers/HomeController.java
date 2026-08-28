package com.apress.prospring6.fourteen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(path = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("message", "Spring Boot Thymeleaf Example!!");
        return "home";
    }
}
