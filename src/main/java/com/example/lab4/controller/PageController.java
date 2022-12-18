package com.example.lab4.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping({"/", "/login", "/table", "/register"})
    public String reroute() {
        return "/public/index.html";
    }
}