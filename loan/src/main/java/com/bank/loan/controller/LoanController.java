package com.bank.loan.controller;

import com.bank.loan.dto.ErrorResponseDto;
import com.bank.loan.dto.LoanDto;
import com.bank.loan.dto.ResponseDto;
import com.bank.loan.entity.Loan;
import com.bank.loan.service.LoanService;
import com.bank.loan.util.LoanConstants;
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

import java.util.List;

@Tag(
        name = "REST APIs for Account microservices",
        description = "REST APIs to CREATE, READ, UPDATE and DELETE account details"
)
@Validated
@RestController
@RequestMapping(value = "api/v1/loan", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class LoanController {

    private LoanService loanService;

    @Operation(
            summary = "Create loan REST API",
            description = "To create new loan account"
    )
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
    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(LoanConstants.STATUS_CREATED)
                        .statusDescription(LoanConstants.MESSAGE_CREATED)
                        .build() );
    }

    @Operation(
            summary = "Fetch loan REST API",
            description = "To fetch loan account"
    )
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
    @GetMapping(value = "/fetch")
    public ResponseEntity<LoanDto> fetchLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanService.fetchLoan(mobileNumber));
    }

    @Operation(
            summary = "Update loan REST API",
            description = "To update loan account"
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
    @PutMapping(value = "/update")
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoanDto loanDto) {
        boolean isUpdated = loanService.updateLoan(loanDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .statusCode(LoanConstants.STATUS_OK)
                            .statusDescription(LoanConstants.MESSAGE_OK)
                            .build());
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto.builder()
                    .statusCode(LoanConstants.STATUS_EXPECTATION_FAILED)
                    .statusDescription(LoanConstants.MESSAGE_EXPECTATION_FAILED_UPDATE)
                    .build());
        }
    }

    @Operation(
            summary = "Delete laon REST API",
            description = "To delete loan account"
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
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                    .statusCode(LoanConstants.STATUS_OK)
                    .statusDescription(LoanConstants.MESSAGE_OK)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto.builder()
                    .statusCode(LoanConstants.STATUS_EXPECTATION_FAILED)
                    .statusDescription(LoanConstants.MESSAGE_EXPECTATION_FAILED_DELETE)
                    .build());
        }
    }
}
