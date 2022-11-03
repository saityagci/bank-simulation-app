package com.cydeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {
    @GetMapping("/transaction")
    public String makeTransfer(){
        return "transaction/make-transfer";
    }
}
