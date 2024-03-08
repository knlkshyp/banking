package com.bank.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(
        name = "Card",
        description = "Schema for card"
)
@Getter
@Setter
@Builder
public class CardDto {

    @Schema(
            description = "Card number of customer", example = "123456789010"
    )
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Card number must be 12 digits")
    @NotEmpty(message = "Card number cannot be null or empty")
    private String cardNumber;

    @Schema(
            description = "Mobile number of customer", example = "9988776655"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @NotEmpty(message = "Mobile number cannot be null or empty")
    private String mobileNumber;

    @Schema(
            description = "Type of card", example = "Credit Card"
    )
    @NotEmpty(message = "Card type cannot be null or empty")
    private String cardType;

    @Schema(
            description = "Total limit of card", example = "1000000"
    )
    @Positive(message = "Total card limit should be greater than zero")
    private Integer totalLimit;

    @Schema(
            description = "Amount used of card", example = "100000"
    )
    @PositiveOrZero(message = "Amount used should be equal or greater than zero")
    private Integer amountUsed;

    @Schema(
            description = "Available amount of card", example = "900000"
    )
    @PositiveOrZero(message = "Available amount should be equal or greater than zero")
    private Integer availableAmount;
}
