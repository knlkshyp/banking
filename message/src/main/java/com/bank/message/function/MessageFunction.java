package com.bank.message.function;

import com.bank.message.dto.AccountMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Configuration
public class MessageFunction {

    @Bean
    public Function<AccountMessageDto, AccountMessageDto> fetchAccountMessage() {
        return accountMessageDto -> {
            log.info("Received account message : " + accountMessageDto.toString());
            return accountMessageDto;
        };
    }

    @Bean
    public Function<AccountMessageDto, Long> fetchAccountNumber() {
        return accountMessageDto -> {
            log.info("Sending account number : " + accountMessageDto.accountNumber());
            return accountMessageDto.accountNumber();
        };
    }
}
