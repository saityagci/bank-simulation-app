package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountType;
import com.cydeo.exceptions.AccountOwnerShipException;
import com.cydeo.exceptions.BadRequestException;
import com.cydeo.exceptions.BalanceNotSufficientException;
import com.cydeo.exceptions.UnderConstructionException;

import com.cydeo.mapper.AccountMapper;
import com.cydeo.mapper.TransactionMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl  implements TransactionService {
    @Value("${under_construction}")
    private boolean underConstruction;
   private final  AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final AccountService accountService;


    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, AccountMapper accountMapper, TransactionMapper transactionMapper, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.accountMapper = accountMapper;
        this.transactionMapper = transactionMapper;
        this.accountService = accountService;
    }

    @Override
    public void makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {
        if (!underConstruction) {
            validateAccount(sender,receiver);
            checkAccountOwnerShip(sender,receiver);
            executeBalanceAndUpdateIfRequired(amount,sender,receiver);

            //after all validations are completed, and money is transferred
            TransactionDTO transactionDTO = new TransactionDTO(sender,receiver,amount,message,creationDate);
            transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));
        }else {
            throw new UnderConstructionException("App is under construction, try again later");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if(checkSenderBalance(sender,amount)){
            // make transaction
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
            /*
            get the dto from database for both sender and receiver, update balance and save it
            create accountService updateAccount method to save it
             */
            //retrieve the object from database for sender
            AccountDTO senderAcc=accountService.retrieveById(sender.getId());
            senderAcc.setBalance(sender.getBalance());
            // save again to database
            accountService.updateAccount(senderAcc);
            AccountDTO receiverAcc=accountService.retrieveById(receiver.getId());
            receiverAcc.setBalance(receiver.getBalance());
            accountService.updateAccount(receiverAcc);
        }else {
            //not enough balance
            throw new BalanceNotSufficientException("Balance is not enough  for this transfer");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        // verify that sender has enough balance
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO)>=0;
    }

    private void checkAccountOwnerShip(AccountDTO sender, AccountDTO receiver) {
        /*
        write an if statement that check if one of the account is saving and
        user if od sender or receiver is not the same, throw AccountOwnershipException
         */
        if ((sender.getAccountType().equals(AccountType.SAVING)||receiver.getAccountType().equals(AccountType.SAVING))
        &!sender.getUserId().equals(receiver.getUserId())) {
            throw new AccountOwnerShipException("One of the account is Savings. "+
                    "Transactions between savings and checking account are allowed same userId "+
                    "User Id's dont match");
        }

    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
        /*
            -if any of the account is null
            -if account ids are the same(same account)
            -if the account exist in the database(repository)
         */
        if (sender==null || receiver==null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
        if (sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver");
        }
        findAccountById(sender.getId());
        findAccountById(receiver.getId());
        

    }

    private AccountDTO findAccountById(Long id) {
        Account account= accountRepository.findById(id).get();
        return accountMapper.convertToDto(account);

    }

    @Override
    public List<TransactionDTO> findAllTransactions() {

        return transactionRepository.findAll().stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> lastTransactionList() {
        return transactionRepository.findLastTenTransactions().stream()
                .map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
       return  transactionRepository.findTransactionListById(id).stream()
               .map(transactionMapper::convertToDTO).collect(Collectors.toList());

    }
}
