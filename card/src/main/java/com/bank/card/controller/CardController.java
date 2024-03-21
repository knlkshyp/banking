package com.bank.card.controller;

import com.bank.card.dto.CardDto;
import com.bank.card.dto.ErrorResponseDto;
import com.bank.card.dto.ResponseDto;
import com.bank.card.service.CardService;
import com.bank.card.util.CardConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST APIs for Card microservices",
        description = "REST APIs to CREATE, READ, UPDATE and DELETE card details"
)
@Slf4j
@Validated
@RestController
@RequestMapping(path = "api/v1/card", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CardController {

    private CardService cardService;

    @Operation(
            summary = "Create card REST API",
            description = "To create new card"
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
    public ResponseEntity<ResponseDto> createCard(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 numeric digits") String mobileNumber) {
        cardService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(CardConstants.STATUS_CREATED)
                        .statusDescription(CardConstants.MESSAGE_CREATED)
                        .build());
    }

    @Operation(
            summary = "Fetch card REST API",
            description = "To fetch card"
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
    public ResponseEntity<CardDto> fetchCard(@RequestHeader(CardConstants.CORRELATION_ID) String correlationId,
                                             @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 numeric digits") String mobileNumber) {
        log.debug("Account controller found correlation id : {}", correlationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardService.fetchCard(mobileNumber));
    }

    @Operation(
            summary = "Update card REST API",
            description = "To update card"
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
    public ResponseEntity<ResponseDto> updateCard(@Valid @RequestBody CardDto cardDto) {
        boolean isUpdated = cardService.updateCard(cardDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                    .statusCode(CardConstants.STATUS_OK)
                    .statusDescription(CardConstants.MESSAGE_OK)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto.builder()
                    .statusCode(CardConstants.STATUS_EXPECTATION_FAILED)
                    .statusDescription(CardConstants.MESSAGE_EXPECTATION_FAILED_UPDATE)
                    .build());
        }
    }

    @Operation(
            summary = "Delete card REST API",
            description = "To delete card"
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
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 numeric digits") String mobileNumber) {
        boolean isDeleted = cardService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                    .statusCode(CardConstants.STATUS_OK)
                    .statusDescription(CardConstants.MESSAGE_OK)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDto.builder()
                    .statusCode(CardConstants.STATUS_EXPECTATION_FAILED)
                    .statusDescription(CardConstants.MESSAGE_EXPECTATION_FAILED_DELETE)
                    .build());
        }
    }
}
