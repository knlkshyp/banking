package com.bank.message.dto;

public record AccountMessageDto(Long accountNumber, String name, String email, String mobileNumber) {
}
