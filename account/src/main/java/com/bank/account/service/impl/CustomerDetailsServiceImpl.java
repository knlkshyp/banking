package com.bank.account.service.impl;

import com.bank.account.dto.CardDto;
import com.bank.account.dto.CustomerDetailsDto;
import com.bank.account.dto.LoanDto;
import com.bank.account.entity.Account;
import com.bank.account.entity.Customer;
import com.bank.account.exception.ResourceNotFoundException;
import com.bank.account.mapper.AccountMapper;
import com.bank.account.mapper.CustomerMapper;
import com.bank.account.repository.AccountRepository;
import com.bank.account.repository.CustomerRepository;
import com.bank.account.service.CustomerDetailsService;
import com.bank.account.service.client.CardFeignClient;
import com.bank.account.service.client.LoanFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private CardFeignClient cardFeignClient;

    private LoanFeignClient loanFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() ->
                new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer);
        customerDetailsDto.setAccountDto(AccountMapper.mapToAccountDto(account));
        ResponseEntity<CardDto> cardDtoResponseEntity = cardFeignClient.fetchCard(correlationId, mobileNumber);
        ResponseEntity<LoanDto> loanDtoResponseEntity = loanFeignClient.fetchLoan(correlationId, mobileNumber);
        customerDetailsDto.setCardDto(cardDtoResponseEntity.getBody());
        customerDetailsDto.setLoanDto(loanDtoResponseEntity.getBody());
        return customerDetailsDto;
    }
}
