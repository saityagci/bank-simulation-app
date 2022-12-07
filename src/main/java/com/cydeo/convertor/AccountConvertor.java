package com.cydeo.convertor;

import com.cydeo.dto.AccountDTO;
import com.cydeo.service.AccountService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class AccountConvertor implements Converter<String, AccountDTO> {
    private final AccountService accountService;

    public AccountConvertor(@Lazy AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountDTO convert(String source) {
        if (source==null || source.equals(" ")){
            return null;
        }
        return accountService.retrieveById(Long.parseLong(source));
    }
}
