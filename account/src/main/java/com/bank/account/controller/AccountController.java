package com.bank.account.controller;

import com.bank.account.dto.CustomerDto;
import com.bank.account.dto.ErrorResponseDto;
import com.bank.account.dto.ResponseDto;
import com.bank.account.service.AccountService;
import com.bank.account.util.AccountConstants;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST APIs for Account microservices",
        description = "REST APIs to CREATE, READ, UPDATE and DELETE account details"
)
@Validated
@RestController
@RequestMapping(value = "api/v1/account", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    @Operation(
            summary = "Create account REST API",
            description = "To create new customer and account"
    )
    @PostMapping(value = "/create")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customer) {
        accountService.createAccount(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(AccountConstants.STATUS_CREATED)
                        .statusDescription(AccountConstants.MESSAGE_CREATED)
                        .build());
    }

    @Operation(
            summary = "Fetch account REST API",
            description = "To fetch customer and account"
    )
    @GetMapping(value = "/fetch")
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
    @Retry(name = "fetchAccount", fallbackMethod = "fetchAccountFallback")
    public ResponseEntity<CustomerDto> fetchAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 numeric digits") String mobileNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.fetchAccount(mobileNumber));
    }

    public ResponseEntity<CustomerDto> fetchAccountFallback(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 numeric digits") String mobileNumber, Throwable throwable) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(null);
    }

    @Operation(
            summary = "Update account REST API",
            description = "To update customer and account"
    )
    @PutMapping(value = "/update")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Ok"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                    .statusCode(AccountConstants.STATUS_OK)
                    .statusDescription(AccountConstants.MESSAGE_OK)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto.builder()
                            .statusCode(AccountConstants.STATUS_EXPECTATION_FAILED)
                            .statusDescription(AccountConstants.MESSAGE_EXPECTATION_FAILED_UPDATE)
                            .build());
        }
    }

    @Operation(
            summary = "Delete account REST API",
            description = "To delete customer and account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Ok"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 numeric digits") String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                    .statusCode(AccountConstants.STATUS_OK)
                    .statusDescription(AccountConstants.MESSAGE_OK)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto.builder()
                    .statusCode(AccountConstants.STATUS_EXPECTATION_FAILED)
                    .statusDescription(AccountConstants.MESSAGE_EXPECTATION_FAILED_DELETE)
                    .build());
        }
    }
}
