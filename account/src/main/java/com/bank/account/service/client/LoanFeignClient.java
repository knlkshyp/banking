package com.bank.account.service.client;

import com.bank.account.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loan")
public interface LoanFeignClient {

    @GetMapping(value = "api/v1/loan/fetch")
    public ResponseEntity<LoanDto> fetchLoan(@RequestParam String mobileNumber);
}
