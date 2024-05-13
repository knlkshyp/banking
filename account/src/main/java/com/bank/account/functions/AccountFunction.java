package com.bank.account.functions;

import com.bank.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class AccountFunction {

    @Bean
    public Consumer<Long> updateAcknowledgement(AccountService accountService) {
        return accountNumber -> {
            log.info("Updating acknowledgement status for the account number {}", accountNumber.toString());
            accountService.updateAcknowledgement(accountNumber);
        };
    }
}
