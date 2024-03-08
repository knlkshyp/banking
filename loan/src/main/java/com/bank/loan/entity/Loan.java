package com.bank.loan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan extends BaseEntity {

    @Id
    @Column(name = "loan_id")
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Column(name = "mobile_number")
    @NotNull
    @Size(max = 15)
    private String mobileNumber;

    @Column(name = "loan_number")
    @NotNull
    @Size(max = 100)
    private String loanNumber;

    @Column(name = "loan_type")
    @NotNull
    @Size(max = 100)
    private String loanType;

    @Column(name = "total_loan_amount")
    @NotNull
    private Long totalLoanAmount;

    @Column(name = "amount_paid")
    @NotNull
    private Long amountPaid;

    @Column(name = "amount_outstanding")
    @NotNull
    private Long amountOutstanding;
}
