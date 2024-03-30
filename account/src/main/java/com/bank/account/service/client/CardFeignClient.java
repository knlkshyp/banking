package com.bank.account.service.client;

import com.bank.account.dto.CardDto;
import com.bank.account.util.AccountConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card", fallback = CardFallback.class)
public interface CardFeignClient {

    @GetMapping(value = "api/v1/card/fetch")
    public ResponseEntity<CardDto> fetchCard(@RequestHeader(AccountConstants.CORRELATION_ID) String correlationId, @RequestParam String mobileNumber);
}
