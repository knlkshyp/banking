package com.bank.loan.service.impl;

import com.bank.loan.dto.LoanDto;
import com.bank.loan.entity.Loan;
import com.bank.loan.exception.LoanAlreadyExistsException;
import com.bank.loan.exception.ResourceNotFoundException;
import com.bank.loan.mapper.LoanMapper;
import com.bank.loan.repository.LoanRepository;
import com.bank.loan.service.LoanService;
import com.bank.loan.util.LoanConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
        if (optionalLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobile number : " + mobileNumber);
        }
        loanRepository.save(initialiseLoan(mobileNumber));
    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Loan", "mobile Number", mobileNumber));
        return LoanMapper.mapToLoanDto(loan);
    }

    @Override
    public boolean updateLoan(LoanDto loanDto) {
        Loan loan = loanRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(() ->
                new ResourceNotFoundException("Loan", "loan number", loanDto.getLoanNumber()));
        LoanMapper.mapToLoan(loanDto, loan);
        loanRepository.save(loan);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Loan", "mobile number", mobileNumber));
        loanRepository.deleteById(loan.getLoanId());
        return true;
    }

    private Loan initialiseLoan(String mobileNumber) {
        long randomLoanNumber = 10000000000L + new Random().nextInt(90000000);
        return Loan.builder()
                .loanNumber(Long.toString(randomLoanNumber))
                .mobileNumber(mobileNumber)
                .loanType(LoanConstants.HOME_LOAN)
                .totalLoanAmount(LoanConstants.LOAN_LIMIT)
                .amountPaid(0L)
                .amountOutstanding(LoanConstants.LOAN_LIMIT)
                .build();
    }
}
