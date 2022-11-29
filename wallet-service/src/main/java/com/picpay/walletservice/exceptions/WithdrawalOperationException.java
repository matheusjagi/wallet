package com.picpay.walletservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WithdrawalOperationException extends ResponseStatusException {

    private static final String MESSAGE = "Error performing withdrawal operation.";

    public WithdrawalOperationException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
