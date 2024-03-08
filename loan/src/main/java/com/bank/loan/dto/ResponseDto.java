package com.bank.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(
        name = "Response",
        description = "Schema for response"
)
@Getter
@Setter
@Builder
public class ResponseDto {

    @Schema(
            description = "Status code for response"
    )
    private String statusCode;

    @Schema(
            description = "Status description for response"
    )
    private String statusDescription;
}