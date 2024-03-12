package com.bank.account.service;

import com.bank.account.dto.CustomerDetailsDto;

public interface CustomerDetailsService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
