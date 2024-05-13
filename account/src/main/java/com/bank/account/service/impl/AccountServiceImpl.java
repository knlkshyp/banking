package com.bank.account.service.impl;

import com.bank.account.dto.AccountDto;
import com.bank.account.dto.AccountMessageDto;
import com.bank.account.dto.CustomerDto;
import com.bank.account.entity.Account;
import com.bank.account.entity.Customer;
import com.bank.account.exception.CustomerAlreadyExistsException;
import com.bank.account.exception.ResourceNotFoundException;
import com.bank.account.mapper.AccountMapper;
import com.bank.account.mapper.CustomerMapper;
import com.bank.account.repository.AccountRepository;
import com.bank.account.repository.CustomerRepository;
import com.bank.account.service.AccountService;
import com.bank.account.util.AccountConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private StreamBridge streamBridge;

    private static final String BINDING_NAME = "sendAccountMessage-out-0";

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with the given mobile number : " + customerDto.getMobileNumber());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        Account savedAccount = accountRepository.save(initialiseAccount(savedCustomer));
        sendAccountMessage(savedAccount, savedCustomer);
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() ->
                new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer);
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto != null) {
            Account account = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(() ->
                    new ResourceNotFoundException("Account", "accountNumber", accountDto.getAccountNumber().toString()));
            AccountMapper.mapToAccount(accountDto, account);
            account = accountRepository.save(account);
            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer", "customerId", customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Account initialiseAccount(Customer customer) {
        return Account.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(1000000000L + new Random().nextLong(9000000000L))
                .accountType(AccountConstants.SAVINGS)
                .branchAddress(AccountConstants.BRANCH_ADDRESS)
                .build();
    }

    private void sendAccountMessage(Account account, Customer customer) {
        var accountMessageDto = new AccountMessageDto(account.getAccountNumber(), customer.getCustomerName(),
                customer.getEmailId(), customer.getMobileNumber());
        log.info("Sending account message {}", accountMessageDto);
        var result = streamBridge.send(BINDING_NAME, accountMessageDto);
        log.info("Request send successfully : {}", result);
    }

    @Override
    public boolean updateAcknowledgement(Long accountNumber) {
        boolean isUpdated = false;
        if (Objects.nonNull(accountNumber)) {
            var account = accountRepository.findById(accountNumber).orElseThrow(() ->
                    new ResourceNotFoundException("Account", "Account Number", accountNumber.toString())
            );
            account.setAcknowledgement(true);
            accountRepository.save(account);
            isUpdated = true;
        }
        return isUpdated;
    }
}
