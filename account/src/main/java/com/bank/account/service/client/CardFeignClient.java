package com.bank.account.service.client;

import com.bank.account.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("card")
public interface CardFeignClient {

    @GetMapping(value = "api/v1/card/fetch")
    public ResponseEntity<CardDto> fetchCard(@RequestParam String mobileNumber);
}
