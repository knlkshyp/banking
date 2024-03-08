package com.bank.loan.mapper;

import com.bank.loan.dto.LoanDto;
import com.bank.loan.entity.Loan;

public class LoanMapper {

    public static LoanDto mapToLoanDto(Loan loan) {
        return LoanDto.builder()
                .loanNumber(loan.getLoanNumber())
                .loanType(loan.getLoanType())
                .mobileNumber(loan.getMobileNumber())
                .totalLoanAmount(loan.getTotalLoanAmount())
                .amountPaid(loan.getAmountPaid())
                .amountOutstanding(loan.getAmountOutstanding())
                .build();
    }

    public static Loan mapToLoan(LoanDto loanDto) {
        return Loan.builder()
                .loanNumber(loanDto.getLoanNumber())
                .loanType(loanDto.getLoanType())
                .mobileNumber(loanDto.getMobileNumber())
                .totalLoanAmount(loanDto.getTotalLoanAmount())
                .amountPaid(loanDto.getAmountPaid())
                .amountOutstanding(loanDto.getAmountOutstanding())
                .build();
    }

    public static void mapToLoan(LoanDto loanDto, Loan loan) {
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setAmountOutstanding(loanDto.getAmountOutstanding());
    }
}
