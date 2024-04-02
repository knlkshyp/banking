package com.bank.account.service;

import com.bank.account.dto.CustomerDto;

public interface CustomerService {

    CustomerDto fetchCustomer(String mobileNumber, String correlationId);
}
