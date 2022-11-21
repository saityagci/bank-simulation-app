package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;
import com.cydeo.service.AccountService;
import com.cydeo.service.impl.TransactionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {
    private final AccountService accountService;
    private final TransactionServiceImpl transactionServiceImpl;

    public AccountController(AccountService accountService, TransactionServiceImpl transactionServiceImpl) {
        this.accountService = accountService;
        this.transactionServiceImpl = transactionServiceImpl;
    }


    @GetMapping("/index")
    public String getIndex(Model model){
        model.addAttribute("accountList",accountService.listAllAccount());
        return "account/index";
    }
    @GetMapping("/create-form")
    public String getCreateForm(Model model){
        model.addAttribute("account", new AccountDTO());
        model.addAttribute("accountType", AccountType.values());

        return "account/create-account";
    }
    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("account") AccountDTO accountDTO, BindingResult bindingResult, Model model){
       if (bindingResult.hasErrors()){
           model.addAttribute("accountTypes",AccountType.values());
           return "account/create-account";
       }
        accountService.createNewAccount(accountDTO.getBalance(),new Date(),
               accountDTO.getAccountType(), accountDTO.getUserId());

        return "redirect:/index";
    }
    @GetMapping("/delete/{id}")
    public String getDeleteAccount(@PathVariable("id") UUID id){
        accountService.deleteAccount(id);
        return "redirect:/index";
    }

}
