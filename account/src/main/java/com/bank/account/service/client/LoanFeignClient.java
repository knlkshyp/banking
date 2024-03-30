package com.bank.account.service.client;

import com.bank.account.dto.LoanDto;
import com.bank.account.util.AccountConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loan", fallback = LoanFallback.class)
public interface LoanFeignClient {

    @GetMapping(value = "api/v1/loan/fetch")
    public ResponseEntity<LoanDto> fetchLoan(@RequestHeader(AccountConstants.CORRELATION_ID) String correlationId, @RequestParam String mobileNumber);
}
