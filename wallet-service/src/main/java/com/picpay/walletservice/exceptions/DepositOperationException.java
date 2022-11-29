package com.picpay.walletservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DepositOperationException extends ResponseStatusException {

    private static final String MESSAGE = "Error performing deposit operation.";

    public DepositOperationException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
