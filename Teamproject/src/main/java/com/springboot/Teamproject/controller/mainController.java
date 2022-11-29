package com.springboot.Teamproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {

    //기본 접속 시 주소 매핑
    @GetMapping("/")
    public String root(){

        return "redirect:/product/list";
    }
}
