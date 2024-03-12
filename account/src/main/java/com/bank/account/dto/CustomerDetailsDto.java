package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(
        name = "CustomerDetails",
        description = "Schema for customer account, card and loan details"
)
@Getter
@Setter
@Builder
public class CustomerDetailsDto {

    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    @Schema(
            description = "Name of the customer", example = "Tony Stark"
    )
    @NotEmpty(message = "Customer name cannot be null or empty")
    private String customerName;

    @Email(message = "Email id should be valid")
    @Schema(
            description = "Email id of the customer", example = "ironman@starkindustries.com"
    )
    @NotEmpty(message = "Email id cannot be null or empty")
    private String emailId;

    @Schema(
            description = "Mobile number of the customer", example = "1777000999"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 numeric digits")
    private String mobileNumber;

    @Schema(
            description = "Customer address of the customer", example = "Stark Tower"
    )
    @NotEmpty(message = "Customer address cannot be null or empty")
    private String customerAddress;

    @Schema(
            description = "Account of the customer"
    )
    private AccountDto accountDto;

    @Schema(
            description = "Card of the customer"
    )
    private CardDto cardDto;

    @Schema(
            description = "Loan of the customer"
    )
    private LoanDto loanDto;
}