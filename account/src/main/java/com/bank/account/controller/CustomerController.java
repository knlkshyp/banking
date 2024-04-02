package com.bank.account.controller;

import com.bank.account.dto.CustomerDto;
import com.bank.account.dto.ErrorResponseDto;
import com.bank.account.service.CustomerService;
import com.bank.account.util.AccountConstants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST APIs for Customer ",
        description = "REST APIs for customer account, card, loan"
)
@Slf4j
@Validated
@RestController
@RequestMapping(value = "api/v1/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @Operation(
            summary = "Fetch customer REST API",
            description = "To fetch customer account, card, loan"
    )
    @GetMapping(value = "/fetchCustomer")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @RateLimiter(name = "fetchCustomer", fallbackMethod = "fetchCustomerFallback")
    public ResponseEntity<CustomerDto> fetchCustomer(@RequestHeader(AccountConstants.CORRELATION_ID) String correlationId,
                                                    @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 numeric digits") String mobileNumber) {
        log.debug("Account controller found correlation id : {}", correlationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.fetchCustomer(mobileNumber, correlationId));
    }

    public ResponseEntity<CustomerDto> fetchCustomerFallback(@RequestHeader(AccountConstants.CORRELATION_ID) String correlationId,
                                                             @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
                                                                     message = "Mobile number must be 10 numeric digits") String mobileNumber,
                                                             Throwable throwable) {
        return ResponseEntity
                .status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
                .body(null);
    }
}
