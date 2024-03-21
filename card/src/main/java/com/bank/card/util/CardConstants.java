package com.bank.card.util;

public class CardConstants {

    private CardConstants() {}

    public static final String CREDIT_CARD = "Credit Card";
    public static final Integer NEW_CREDIT_LIMIT = 1_000_000;

    public static final String STATUS_OK = "200";
    public static final String MESSAGE_OK = "Request processed successfully";
    public static final String STATUS_CREATED = "201";
    public static final String MESSAGE_CREATED = "Card created successfully";
    public static final String STATUS_EXPECTATION_FAILED = "417";
    public static final String MESSAGE_EXPECTATION_FAILED_UPDATE = "Update operation failed";
    public static final String MESSAGE_EXPECTATION_FAILED_DELETE = "Delete operation failed";

    public static final String CORRELATION_ID = "bank-correlation-id";
}
