package com.cydeo.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AccountController {

    @GetMapping("/index")
    public String getIndex(Model model){

        return "account/index";
    }
}
