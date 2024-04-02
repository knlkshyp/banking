package com.bank.account.mapper;

import com.bank.account.dto.CustomerDto;
import com.bank.account.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .customerName(customer.getCustomerName())
                .emailId(customer.getEmailId())
                .mobileNumber(customer.getMobileNumber())
                .customerAddress(customer.getCustomerAddress())
                .build();
    }

    public static Customer mapToCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .customerName(customerDto.getCustomerName())
                .emailId(customerDto.getEmailId())
                .mobileNumber(customerDto.getMobileNumber())
                .customerAddress(customerDto.getCustomerAddress())
                .build();
    }

    public static void mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setEmailId(customerDto.getEmailId());
        customer.setMobileNumber(customerDto.getMobileNumber());
        customer.setCustomerAddress(customerDto.getCustomerAddress());
    }
}
