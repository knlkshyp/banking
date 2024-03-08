package com.bank.account.mapper;

import com.bank.account.dto.AccountDto;
import com.bank.account.entity.Account;

public class AccountMapper {

    public static AccountDto mapToAccountDto(Account account) {
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .build();
    }

    public static Account mapToAccount(AccountDto accountDto) {
        return Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .accountType(accountDto.getAccountType())
                .branchAddress(accountDto.getBranchAddress())
                .build();
    }

    public static void mapToAccount(AccountDto accountDto, Account account) {
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
    }
}
