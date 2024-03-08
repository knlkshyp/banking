package com.bank.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(
        name = "Loan",
        description = "Schema for loan"
)
@Getter
@Setter
@Builder
public class LoanDto {

    @Schema(
            description = "Mobile number of customer", example = "8570093691"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @NotEmpty(message = "Mobile number cannot be null or empty")
    private String mobileNumber;

    @Schema(
            description = "Account number of customer", example = "8570093691"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    @NotEmpty(message = "Account number cannot be null or empty")
    private String loanNumber;

    @Schema(
            description = "Type of loan", example = "Home Loan"
    )
    @NotEmpty(message = "Loan type cannot be null or empty")
    private String loanType;

    @Schema(
            description = "Total loan amount", example = "3000000"
    )
    @Positive(message = "Total loan amount should be greater than zero")
    private long totalLoanAmount;

    @Schema(
            description = "Total loan amount paid", example = "1000000"
    )
    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    private long amountPaid;

    @Schema(
            description = "Total loan amount outstanding", example = "2000000"
    )
    @PositiveOrZero(message = "Total loan amount outstanding should be equal or greater than zero")
    private long amountOutstanding;
}
