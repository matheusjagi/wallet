package com.picpay.movementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FinancialOperationException extends ResponseStatusException {

    private static final String MESSAGE = "Financial operation failed.";

    public FinancialOperationException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
