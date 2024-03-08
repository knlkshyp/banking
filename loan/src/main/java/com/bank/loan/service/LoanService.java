package com.bank.loan.service;

import com.bank.loan.dto.LoanDto;
import com.bank.loan.entity.Loan;

import java.util.List;

public interface LoanService {


    void createLoan(String mobileNumber);

    LoanDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoanDto loanDto);

    boolean deleteLoan(String mobileNumber);
}
