package com.picpay.walletservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InsufficientBalanceException extends ResponseStatusException {

    private static final String MESSAGE = "Insufficient balance to carry out the transaction.";

    public InsufficientBalanceException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
