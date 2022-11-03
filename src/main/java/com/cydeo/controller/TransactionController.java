package com.cydeo.controller;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.UUID;

@Controller
public class TransactionController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("make-transfer")
    public String makeTransfer(Model model){
        model.addAttribute("accounts",accountService.listAllAccount());
        model.addAttribute("transaction", Transaction.builder().build());
        model.addAttribute("lastTransactions",transactionService.lastTransactionList());
        return "transaction/make-transfer";
    }
    @PostMapping("/transfer")
    public String postMakeTransfer(@ModelAttribute("transaction")Transaction transaction,Model model){
        Account sender=accountService.retrieveById(transaction.getSender());
        Account receiver=accountService.retrieveById(transaction.getReceiver());


        transactionService.makeTransfer(sender,receiver,transaction.getAmount(),new Date(),transaction.getMessage());


        return "redirect:/make-transfer";
    }
    @GetMapping("/transaction/{id}")
    public String getTransactionList(@PathVariable("id") UUID id,Model model){
        System.out.println(id);
        return "transaction/transactions" ;
    }
}
