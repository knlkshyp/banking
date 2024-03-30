package com.bank.account.service.client;

import com.bank.account.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanFallback implements LoanFeignClient {

    @Override
    public ResponseEntity<LoanDto> fetchLoan(String correlationId, String mobileNumber) {
        return null;
    }
}
