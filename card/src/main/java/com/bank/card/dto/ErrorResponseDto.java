package com.bank.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Schema for error response"
)
@Getter
@Setter
@Builder
public class ErrorResponseDto {

    @Schema(
            description = "API path of error response"
    )
    private String apiPath;

    @Schema(
            description = "Error code of error response"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message of error response"
    )
    private String errorMessage;

    @Schema(
            description = "Error time corresponding to error response"
    )
    private LocalDateTime errorTime;
}
