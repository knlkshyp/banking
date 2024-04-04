package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(
        name = "Account",
        description = "Schema for account"
)
@Getter
@Setter
@Builder
public class AccountDto {

    @Schema(
            description = "Account type of customer", example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;

    @Schema(
            description = "Branch address of bank", example = "Times Square"
    )
    @NotEmpty(message = "Branch address cannot be null or empty")
    private String branchAddress;
}
