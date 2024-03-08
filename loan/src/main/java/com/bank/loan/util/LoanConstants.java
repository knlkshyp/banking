package com.bank.loan.util;

public class LoanConstants {

    private LoanConstants() {}

    public static final String STATUS_OK = "200";
    public static final String MESSAGE_OK = "Request processed successfully";
    public static final String STATUS_CREATED = "201";
    public static final String MESSAGE_CREATED = "Loan account created successfully";
    public static final String STATUS_EXPECTATION_FAILED = "417";
    public static final String MESSAGE_EXPECTATION_FAILED_UPDATE = "Update operation failed";
    public static final String MESSAGE_EXPECTATION_FAILED_DELETE = "Delete operation failed";

    public static final String HOME_LOAN = "Home Loan";
    public static final long LOAN_LIMIT = 3_000_000;
}
